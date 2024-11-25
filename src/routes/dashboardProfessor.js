var express = require("express");
var router = express.Router();

var dashboardProfessorController = require("../controllers/dashboardProfessorController");

router.get("/listarTurmas/:idUsuario", function (req, res) {
    dashboardProfessorController.listarTurmas(req, res);
});

router.get("/listarDificuldadePorTurma/:idTurma/:idMateria", function (req, res) {
    dashboardProfessorController.listarDificuldadePorTurma(req, res);
});

router.get("/listarDesempenhoPorTurma/:idUsuario/:idTurma", function (req, res) {
    dashboardProfessorController.listarDesempenhoPorTurma(req, res);
});

router.get("/listarMediaAlunosPorTurma/:idUsuario/:idTurma", function (req, res) {
    dashboardProfessorController.listarMediaAlunosPorTurma(req, res);
});

router.get("/obterDadosgrafico/:idMateria/:idTurma", function (req, res) {
    dashboardProfessorController.obterDadosgrafico(req, res);
});


module.exports = router;