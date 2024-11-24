var dashboardProfessorModel = require("../models/dashboardProfessorModel");

function listarTurmas(req, res) {
    const idUsuario = req.params.idUsuario;

    dashboardProfessorModel.listarTurmas(idUsuario)
        .then(function (resultado) {
            if (resultado.length > 0) {
                res.status(200).json(resultado);
            } else {
                res.status(204).send("Nenhum resultado encontrado!")
            }
    }).catch(function (erro) {
        console.log(erro);
        console.log("Houve um erro ao buscar os avisos: ", erro.sqlMessage);
        res.status(500).json(erro.sqlMessage);
    });
}

function listarDificuldadePorTurma(req, res) {
    const idTurma = req.params.idTurma;
    const idMateria = req.params.idMateria;

    dashboardProfessorModel.listarDificuldadePorTurma(idTurma, idMateria)
        .then(function (resultado) {
            if (resultado.length > 0) {
                res.status(200).json(resultado);
            } else {
                res.status(204).send("Nenhum resultado encontrado!")
            }
    }).catch(function (erro) {
        console.log(erro);
        console.log("Houve um erro ao buscar os avisos: ", erro.sqlMessage);
        res.status(500).json(erro.sqlMessage);
    });
}

function listarDesempenhoPorTurma(req, res) {
    const idUsuario = req.params.idUsuario;
    const idTurma = req.params.idTurma;
   
    dashboardProfessorModel.listarDesempenhoPorTurma(idUsuario, idTurma)
        .then(function (resultado) {
            if (resultado.length > 0) {
                res.status(200).json(resultado);
            } else {
                res.status(204).send("Nenhum resultado encontrado!")
            }
    }).catch(function (erro) {
        console.log(erro);
        console.log("Houve um erro ao buscar os avisos: ", erro.sqlMessage);
        res.status(500).json(erro.sqlMessage);
    });
}

function listarMediaAlunosPorTurma(req, res) {
    const idUsuario = req.params.idUsuario;
    const idTurma = req.params.idTurma;
   
    dashboardProfessorModel.listarMediaAlunosPorTurma(idUsuario, idTurma)
        .then(function (resultado) {
            if (resultado.length > 0) {
                res.status(200).json(resultado);
            } else {
                res.status(204).send("Nenhum resultado encontrado!")
            }
    }).catch(function (erro) {
        console.log(erro);
        console.log("Houve um erro ao buscar os avisos: ", erro.sqlMessage);
        res.status(500).json(erro.sqlMessage);
    });
}

function obterDadosgrafico(req, res) {
    const idMateria = req.params.idMateria;
    const idTurma = req.params.idTurma;
   
    dashboardProfessorModel.obterDadosgrafico(idMateria, idTurma)
        .then(function (resultado) {
            if (resultado.length > 0) {
                res.status(200).json(resultado);
            } else {
                res.status(204).send("Nenhum resultado encontrado!")
            }
    }).catch(function (erro) {
        console.log(erro);
        console.log("Houve um erro ao buscar os avisos: ", erro.sqlMessage);
        res.status(500).json(erro.sqlMessage);
    });
}


module.exports = {
    listarTurmas,
    listarDificuldadePorTurma,
    listarDesempenhoPorTurma,
    listarMediaAlunosPorTurma,
    obterDadosgrafico
}