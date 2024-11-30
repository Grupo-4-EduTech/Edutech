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

function diretores(req,res){
    secretariaModel.diretores().then((resultado) => {
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