var database = require("../database/config");

function mediaMaterias(){
    var instrucaoSql = "SELECT SUBSTRING(avg(proficienciaLP),1,4)/10 mediaLP, SUBSTRING(avg(proficienciaMT),1,4)/10 mediaMT from aluno";

    return database.executar(instrucaoSql);
}

function porcentagemAbaixoMediaLP(){
    var instrucaoSql = "SELECT CAST((COUNT(*)*100/(SELECT COUNT(*) FROM aluno)) AS DECIMAL) porcentagem FROM aluno WHERE SUBSTRING(proficienciaLP, 1, 4) / 10 < 325 OR proficienciaLP IS NULL";

    return database.executar(instrucaoSql)
}

function porcentagemAbaixoMediaMT() {
    var instrucaoSql = "SELECT CAST((COUNT(*)*100/(SELECT COUNT(*) FROM aluno)) AS DECIMAL) porcentagem FROM aluno WHERE SUBSTRING(proficienciaMT, 1, 4) / 10 < 325 OR proficienciaMT IS NULL";

    return database.executar(instrucaoSql)
}

function comparacaoRegiao(){
    var instrucaoSql = "SELECT idRegiao regiao, SUBSTRING(AVG(proficienciaLP), 1,4)/10 mediaLP, SUBSTRING(AVG(proficienciaMT),1,4)/10 mediaMT FROM escola JOIN aluno ON fkEscola = idEscola GROUP BY idRegiao";

    return database.executar(instrucaoSql)
}

function rankingEscolas(){
    var instrucaoSql = "SELECT escola.nome nome, SUBSTRING((AVG(proficienciaLP)+AVG(proficienciaMT))/2,1,4)/10 media FROM escola JOIN aluno ON fkEscola = idEscola GROUP BY escola.nome ORDER BY media DESC";

    return database.executar(instrucaoSql)
}

function escolas(){
    var instrucaoSql = "SELECT escola.nome escola, usuario.nome diretor, idRegiao, SUBSTRING((AVG(proficienciaLP)+AVG(proficienciaMT))/2,1,4)/10 media FROM escola LEFT JOIN usuario ON idEscola=usuario.fkEscola AND fkCargo = 2 LEFT JOIN aluno ON idEscola=aluno.fkEscola GROUP BY escola,diretor,idRegiao";

    return database.executar(instrucaoSql)
}

function diretores(){
    var instrucaoSql = "SELECT usuario.nome diretor, escola.nome escola, email FROM usuario JOIN escola ON idEscola=fkEscola AND fkCargo=2;";

    return database.executar(instrucaoSql)
}

function cadastroEscola(nome, logradouro, numero, regiao){
    var instrucaoSql = `INSERT INTO escola (nome, logradouro, numLogradouro, idRegiao, fkDiretoria) VALUES ('${nome}','${logradouro}',${numero},${regiao},1)`;

    return database.executar(instrucaoSql)
}

function escolasSemDiretor(){
    var instrucaoSql = "SELECT idEscola, escola.nome FROM escola LEFT JOIN usuario ON idEscola = fkEscola AND fkCargo = 2 WHERE idUsuario IS NULL";

    return database.executar(instrucaoSql)
}

function cadastrarDiretor(nome, email, senha, telefone, fkEscola){
    var instrucaoSql = `INSERT INTO usuario (nome, email, senha, telefone, dtCadastro, fkCargo, fkEscola, fkDiretoria) VALUES ('${nome}','${email}','${senha}','${telefone}',NOW(),2,${fkEscola},1)`;

    return database.executar(instrucaoSql)
}

module.exports = {
    mediaMaterias,
    porcentagemAbaixoMediaLP,
    porcentagemAbaixoMediaMT,
    comparacaoRegiao,
    rankingEscolas,
    escolas,
    diretores,
    cadastroEscola,
    escolasSemDiretor,
    cadastrarDiretor,
}