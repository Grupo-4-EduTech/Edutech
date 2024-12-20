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

router.get("/turmasProfessores/:fkEscola", function (req,res){
    diretorController.turmasProfessores(req,res);
});

router.get("/professores/:fkEscola", function (req,res) {
    diretorController.professores(req,res);
});

router.get("/professorPesquisa/:fkEscola/:pesquisa", function (req,res) {
    diretorController.professorPesquisa(req,res);
});

router.get("/professorFiltroMateria/:fkEscola/:fkMateria", function (req, res) {
    diretorController.professorFiltroMateria(req,res);
});

router.get("/professorFiltroAlfabetica/:fkEscola", function (req, res) {
    diretorController.professorFiltroAlfabetica(req,res);
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

router.post("/cadastrarProfessor", function (req, res) {
    diretorController.cadastrarProfessor(req, res);
});

router.get("/turmasSemProfessor/:fkEscola/:fkMateria", function (req,res){
    diretorController.turmasSemProfessor(req,res);
});

router.post("/atribuirTurmas", function (req,res) {
    diretorController.atribuirTurmas(req,res);
});

router.get("/confirmarOperacao/:idUsuario/:senha", function (req,res){
    diretorController.confirmarOperacao(req,res);
});

router.put("/editarProfessor/:idUsuario", function(req,res){
    diretorController.editarProfessor(req,res);
});

router.delete("/excluirProfessor/:idProfessor", function (req, res) {
    diretorController.excluirProfessor(req, res);
});

module.exports = router;