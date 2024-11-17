
// var ambiente_processo = 'producao';
var ambiente_processo = 'desenvolvimento';

var caminho_env = ambiente_processo === 'producao' ? '.env' : '.env.dev';
// Acima, temos o uso do operador ternário para definir o caminho do arquivo .env
// A sintaxe do operador ternário é: condição ? valor_se_verdadeiro : valor_se_falso

require("dotenv").config({ path: caminho_env });

var express = require("express");
var cors = require("cors");
var path = require("path");
var PORTA_APP = process.env.APP_PORT;
var HOST_APP = process.env.APP_HOST;


const axios = require('axios');


var app = express();

var indexRouter = require("./src/routes/index");
var usuarioRouter = require("./src/routes/usuarios");
var jogo_interesse = require("./src/routes/jogo_interesse");
var sugestoesRouter = require("./src/routes/sugestoes");
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(express.static(path.join(__dirname, "public")));

app.use(cors());

app.use("/", indexRouter);
app.use("/usuarios", usuarioRouter);
app.use("/jogo_interesse", jogo_interesse)
app.use("/sugestoes", sugestoesRouter)

app.listen(PORTA_APP, function () {
    console.log(`
    ##   ##  ######   #####             ####       ##     ######     ##              ##  ##    ####    ######  
    ##   ##  ##       ##  ##            ## ##     ####      ##      ####             ##  ##     ##         ##  
    ##   ##  ##       ##  ##            ##  ##   ##  ##     ##     ##  ##            ##  ##     ##        ##   
    ## # ##  ####     #####    ######   ##  ##   ######     ##     ######   ######   ##  ##     ##       ##    
    #######  ##       ##  ##            ##  ##   ##  ##     ##     ##  ##            ##  ##     ##      ##     
    ### ###  ##       ##  ##            ## ##    ##  ##     ##     ##  ##             ####      ##     ##      
    ##   ##  ######   #####             ####     ##  ##     ##     ##  ##              ##      ####    ######  
    \n\n\n                                                                                                 
    Servidor do seu site já está rodando! Acesse o caminho a seguir para visualizar .: http://${HOST_APP}:${PORTA_APP} :. \n\n
    Você está rodando sua aplicação em ambiente de .:${process.env.AMBIENTE_PROCESSO}:. \n\n
    \tSe .:desenvolvimento:. você está se conectando ao banco local. \n
    \tSe .:producao:. você está se conectando ao banco remoto. \n\n
    \t\tPara alterar o ambiente, comente ou descomente as linhas 1 ou 2 no arquivo 'app.js'\n\n`);
});

//API Valorant

// Endpoint para obter dados dos agentes
app.get('/api/agents', async (req, res) => {
    try {
        const response = await axios.get('https://valorant-api.com/v1/agents?language=pt-BR');
        res.json(response.data);
    } catch (error) {
        res.status(500).send('Erro ao obter dados dos agentes');
    }
});

// Endpoint para obter dados dos mapas
app.get('/api/maps', async (req, res) => {
    try {
        const response = await axios.get('https://valorant-api.com/v1/maps');
        res.json(response.data);
    } catch (error) {
        res.status(500).send('Erro ao obter dados dos mapas');
    }
});

// Endpoint para obter dados das armas
app.get('/api/weapons', async (req, res) => {
    try {
        const response = await axios.get('https://valorant-api.com/v1/weapons');
        res.json(response.data);
    } catch (error) {
        res.status(500).send('Erro ao obter dados das armas');
    }
});

// Rota para servir o arquivo HTML específico
app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'public', 'valorant.html'));
});


const {
    GoogleGenerativeAI,
    HarmCategory,
    HarmBlockThreshold,
} = require("@google/generative-ai");

const genAI = new GoogleGenerativeAI('AIzaSyDxsWFO6JXuGOod7BTGg7j6CahDw1uTpaw');

const model = genAI.getGenerativeModel({
    model: "gemini-1.5-pro",
});

const generationConfig = {
    temperature: 1,
    topP: 0.95,
    topK: 40,
    maxOutputTokens: 8192,
    responseMimeType: "text/plain",
};

let textoCompleto  = "";
let materiaDificuldade = "Geometria";
const fkUsuario = 2;

async function run() {
    const chatSession = model.startChat({
        generationConfig,
        history: [
        ],
    });

    const prompt = `Como um especialista na área da educação, me dê 4 sugestões de melhorias para professores conseguirem identificar as dificuldades dos alunos relacionados à matéria: ${materiaDificuldade}. Separe dessa forma: Título: Breve Explicação: Explicação completa: (faça 2 parágrafos)`;

    const result = await chatSession.sendMessage(prompt);
    console.log(result.response.text());

    textoCompleto  = result.response.text();
    let dadosExtraidos =  extrairDados(textoCompleto);
    console.log(dadosExtraidos);


    const database = require("./src/database/config");
    for(let i = 0; i< 4; i++){
        const titulo = dadosExtraidos[i].titulo;
        const descricaoBreve = dadosExtraidos[i].breveExplicacao;
        const descricaoCompleta = formatarQuebrasDeLinha(dadosExtraidos[i].explicacaoCompleta);
       

        const instrucaoSql = `
        INSERT INTO sugestoes (titulo, descricaoBreve, descricaoCompleta, fkUsuario) VALUES ('${titulo}', '${descricaoBreve}', '${descricaoCompleta}','${fkUsuario}');
    `;
    console.log("Executando a instrução SQL: \n" + instrucaoSql);
     database.executar(instrucaoSql);
    }
   
    return false;
}


verificarSugestoes();


function verificarSugestoes(){
    const database = require("./src/database/config");

    const instrucaoSql = `
    SELECT * FROM sugestoes WHERE fkUsuario = ${fkUsuario}
`;

    return database.executar(instrucaoSql).then(result => {
        // Verifica se a consulta retornou algum resultado
        if (result.length === 0) {
            console.log('Nenhuma sugestão encontrada.');
            run();
        } else {
            console.log('Sugestões encontradas:', result);
            return result;  // Retorna os resultados encontrados
        }
    })
    .catch(error => {
        console.error('Erro ao executar a consulta:', error);
        return [];  // Retorna um array vazio em caso de erro
    });
}

function extrairDados(texto) {
    // Expressão regular para capturar os 4 blocos de título, breve explicação e explicação completa
    const regex = /## (\d+)\. Título: ([^\n]+)\n\n\*\*Breve Explicação:\*\*([\s\S]+?)\n\n\*\*Explicação completa:\*\*([\s\S]+?)(?=\n##|\n$)/g;
    
    let resultado;
    const dados = [];
  
    // Enquanto houver correspondências
    while ((resultado = regex.exec(texto)) !== null) {
      dados.push({
        numero: resultado[1],  // Número do item (1, 2, 3, etc.)
        titulo: resultado[2],  // Título
        breveExplicacao: resultado[3].trim(),  // Breve explicação
        explicacaoCompleta: resultado[4].trim()  // Explicação completa
      });
    }
  
    return dados;
  }


  function formatarQuebrasDeLinha(texto) {
    return texto.replace(/\n/g, '<br>');
}

