var express = require("express");
var router = express.Router();

var diretorController = require("../controllers/diretorController");

router.get("/rank/:fkEscola", function(req,res){
    diretorController.rank(req,res);
});

router.get("/porcentagemAbaixoMediaLP/:fkEscola", function (req,res){
    diretorController.porcentagemAbaixoMediaLP(req,res);
});

router.get("/porcentagemAbaixoMediaMT/:fkEscola", function (req, res) {
    diretorController.porcentagemAbaixoMediaMT(req, res);
});

router.get("/turmaMaisDificuldade/:fkEscola", function (req,res){
    diretorController.turmaMaisDificuldade(req,res);
});

router.get("/turmasProficiencia/:fkEscola", function (req,res){
    diretorController.turmasProficiencia(req,res);
});

router.get("/professores/:fkEscola", function (req,res) {
    diretorController.professores(req,res);
});

router.get("/infoProfessor/:idUsuario", function (req, res) {
    diretorController.infoProfessor(req, res);
});

router.get("/turmas/:fkEscola", function (req,res){
    diretorController.turmas(req, res);
});

router.get("/conteudoMaisDificuldade/:fkTurma", function (req, res){
    diretorController.conteudoMaisDificuldade(req, res);
});

module.exports = router;