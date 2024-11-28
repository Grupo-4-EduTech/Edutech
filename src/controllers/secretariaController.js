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

module.exports = {
    mediaMaterias,
    porcentagemAbaixoMediaLP,
    porcentagemAbaixoMediaMT,
    comparacaoRegiao,
    rankingEscolas,
    escolas,
    diretores,
}