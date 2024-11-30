var diretorModel = require("../models/diretorModel");

function rank(req,res){
    var fkEscola = req.params.fkEscola;
    diretorModel.rank(fkEscola).then((resultado) => {
        res.status(200).json(resultado);
    });
}

function porcentagemAbaixoMediaLP(req,res){
    var fkEscola = req.params.fkEscola;
    diretorModel.porcentagemAbaixoMediaLP(fkEscola).then((resultado) => {
        res.status(200).json(resultado);
    });
}

function porcentagemAbaixoMediaMT(req, res) {
    var fkEscola = req.params.fkEscola;
    diretorModel.porcentagemAbaixoMediaMT(fkEscola).then((resultado) => {
        res.status(200).json(resultado);
    });
}

function turmaMaisDificuldade(req,res){
    var fkEscola = req.params.fkEscola;
    diretorModel.turmaMaisDificuldade(fkEscola).then((resultado) => {
        res.status(200).json(resultado);
    });
}

function turmasProficiencia(req,res){
    var fkEscola = req.params.fkEscola;
    diretorModel.turmasProficiencia(fkEscola).then((resultado) => {
        res.status(200).json(resultado);
    });
}

function professores(req, res) {
    var fkEscola = req.params.fkEscola;
    diretorModel.professores(fkEscola).then((resultado) => {
        res.status(200).json(resultado);
    });
}

function infoProfessor(req, res) {
    var idUsuario = req.params.idUsuario;
    diretorModel.infoProfessor(idUsuario).then((resultado) => {
        res.status(200).json(resultado);
    });
}

function turmas(req, res) {
    var fkEscola = req.params.fkEscola;
    diretorModel.turmas(fkEscola).then((resultado) => {
        res.status(200).json(resultado);
    });
}

function conteudoMaisDificuldade(req, res) {
    var fkTurma = req.params.fkTurma;
    diretorModel.conteudoMaisDificuldade(fkTurma).then((resultado) => {
        res.status(200).json(resultado);
    })
}

function cadastrarProfessor(req, res) {
    var nome = req.body.nomeServer;
    var email = req.body.emailServer;
    var senha = req.body.senhaServer;
    var telefone = req.body.telefoneServer;
    var fkEscola = req.body.fkEscolaServer;
    var fkMateria = req.body.fkMateriaServer;
    diretorModel.cadastrarProfessor(nome, email, senha, telefone, fkEscola, fkMateria).then(function (resultado) {
        res.status(201).json({id: resultado.id});
    }).catch(function (erro) {
        console.log(erro);
        console.log(erro.sqlMessage);
        res.status(500).json(erro.sqlMessage);
    });
}

function turmasSemProfessor(req, res) {
    var fkEscola = req.params.fkEscola;
    var fkMateria = req.params.fkMateria;
    diretorModel.turmasSemProfessor(fkEscola, fkMateria).then((resultado) => {
        res.status(200).json(resultado);
    });
}

function atribuirTurmas(req, res) {
    var fkProfessor = req.body.fkProfessorServer
    var fkTurma = req.body.fkTurmaServer
    diretorModel.atribuirTurmas(fkProfessor, fkTurma).then((resultado) => {
        res.status(200).json(resultado);
    });
}

module.exports = {
    rank,
    porcentagemAbaixoMediaLP,
    porcentagemAbaixoMediaMT,
    turmaMaisDificuldade,
    turmasProficiencia,
    professores,
    infoProfessor,
    turmas,
    conteudoMaisDificuldade,
    cadastrarProfessor,
    turmasSemProfessor,
    atribuirTurmas,
}