var secretariaModel = require("../models/secretariaModel");

function mediaMaterias(req,res){
    secretariaModel.mediaMaterias().then((resultado) => {
        res.status(200).json(resultado);
    });
}

function porcentagemAbaixoMediaLP(req,res){
    secretariaModel.porcentagemAbaixoMediaLP().then((resultado) => {
        res.status(200).json(resultado);
    });
}

function porcentagemAbaixoMediaMT(req, res) {
    secretariaModel.porcentagemAbaixoMediaMT().then((resultado) => {
        res.status(200).json(resultado);
    });
}

function comparacaoRegiao(req, res){
    secretariaModel.comparacaoRegiao().then((resultado) => {
        res.status(200).json(resultado);
    });
}

function rankingEscolas(req,res){
    secretariaModel.rankingEscolas().then((resultado) => {
        res.status(200).json(resultado);
    });
}

function escolas(req,res){
    secretariaModel.escolas().then((resultado) => {
        res.status(200).json(resultado);
    });
}

function escolasFiltro(req,res){
    let regiao = req.params.regiao;
    secretariaModel.escolasFiltro(regiao).then((resultado) => {
        res.status(200).json(resultado);
    });
}

function escolasPesquisa(req,res){
    let pesquisa = req.params.pesquisa;
    secretariaModel.escolasPesquisa(pesquisa).then((resultado) => {
        res.status(200).json(resultado);
    });
}

function diretores(req,res){
    secretariaModel.diretores().then((resultado) => {
        res.status(200).json(resultado);
    });
}

function diretoresPesquisa(req, res) {
    let pesquisa = req.params.pesquisa;
    secretariaModel.diretoresPesquisa(pesquisa).then((resultado) => {
        res.status(200).json(resultado);
    });
}

function cadastroEscola(req,res){
    var nome = req.body.nomeServer;
    var logradouro = req.body.logradouroServer;
    var numero = req.body.numeroServer;
    var regiao = req.body.regiaoServer;
    secretariaModel.cadastroEscola(nome, logradouro, numero, regiao).then((resultado) => {
        res.status(200).json(resultado);
    });
}

function escolasSemDiretor(req,res){
    secretariaModel.escolasSemDiretor().then((resultado) => {
        res.status(200).json(resultado);
    });
}

function cadastrarDiretor(req,res){
    var nome = req.body.nomeServer;
    var email = req.body.emailServer;
    var telefone = req.body.telefoneServer;
    var senha = req.body.senhaServer;
    var instituicao = req.body.instituicaoServer;
    secretariaModel.cadastrarDiretor(nome, email, senha, telefone, instituicao).then((resultado) => {
        res.status(200).json(resultado);
    });
}

function confirmarOperacao(req, res) {
    var idUsuario = req.params.idUsuario;
    var senha = req.params.senha;
    secretariaModel.confirmarOperacao(idUsuario, senha).then((resultado) => {
        if (resultado.length == 1) {
            res.status(200).json(resultado);
        } else {
            res.status(403).send("Senha inválida");
        }
    });
}

function escola(req,res){
    var idEscola = req.params.idEscola;
    secretariaModel.escola(idEscola).then((resultado) => {
        res.status(200).json(resultado);
    });
}

function editarEscola(req,res){
    var nome = req.body.nomeServer;
    var regiao = req.body.regiaoServer;
    var logradouro = req.body.logradouroServer;
    var numero = req.body.numeroServer;
    var idEscola = req.params.idEscola;
    secretariaModel.editarEscola(nome, regiao, logradouro, numero, idEscola).then((resultado) => {
        res.status(200).json(resultado);
    });
}

function excluirEscola(req,res){
    var idEscola = req.params.idEscola;
    secretariaModel.excluirEscola(idEscola).then((resultado) => {
        res.status(200).json(resultado);
    })
}

function diretor(req, res) {
    var idUsuario = req.params.idUsuario;
    secretariaModel.diretor(idUsuario).then((resultado) => {
        res.status(200).json(resultado);
    });
}

function editarDiretor(req, res) {
    var nome = req.body.nomeServer;
    var email = req.body.emailServer;
    var telefone = req.body.telefoneServer;
    var instituicao = req.body.instituicaoServer;
    var idUsuario = req.params.idUsuario;
    secretariaModel.editarDiretor(nome, email, telefone, instituicao, idUsuario).then((resultado) => {
        res.status(200).json(resultado);
    });
}

function excluirDiretor(req, res) {
    var idUsuario = req.params.idUsuario;
    secretariaModel.excluirDiretor(idUsuario).then((resultado) => {
        res.status(200).json(resultado);
    })
}

module.exports = {
    mediaMaterias,
    porcentagemAbaixoMediaLP,
    porcentagemAbaixoMediaMT,
    comparacaoRegiao,
    rankingEscolas,
    escolas,
    escolasFiltro,
    escolasPesquisa,
    diretores,
    diretoresPesquisa,
    cadastroEscola,
    escolasSemDiretor,
    cadastrarDiretor,
    confirmarOperacao,
    escola,
    editarEscola,
    excluirEscola,
    diretor,
    editarDiretor,
    excluirDiretor,
}