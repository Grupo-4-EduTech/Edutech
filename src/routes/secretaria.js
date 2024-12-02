var express = require("express");
var router = express.Router();

var secretariaController = require("../controllers/secretariaController");
const { escolas } = require("../models/secretariaModel");

router.get("/mediaMaterias", function(req,res){
    secretariaController.mediaMaterias(req,res);
});

router.get("/porcentagemAbaixoMediaLP", function (req,res){
    secretariaController.porcentagemAbaixoMediaLP(req,res);
});

router.get("/porcentagemAbaixoMediaMT", function (req,res) {
    secretariaController.porcentagemAbaixoMediaMT(req,res);
});

router.get("/comparacaoRegiao", function (req,res){
    secretariaController.comparacaoRegiao(req,res);
})

router.get("/rankingEscolas", function (req,res){
    secretariaController.rankingEscolas(req,res);
});

router.get("/escolas", function (req,res){
    secretariaController.escolas(req,res);
});

router.get("/escolasFiltro/:regiao", function (req,res){
    secretariaController.escolasFiltro(req,res);
});

router.get("/escolasPesquisa/:pesquisa", function (req,res){
    secretariaController.escolasPesquisa(req,res);
});

router.get("/diretores", function (req,res) {
    secretariaController.diretores(req,res);
});

router.get("/diretoresPesquisa/:pesquisa", function (req, res) {
    secretariaController.diretoresPesquisa(req, res);
});

router.post("/cadastroEscola", function (req,res){
    secretariaController.cadastroEscola(req,res);
});

router.get("/escolasSemDiretor", function (req,res){
    secretariaController.escolasSemDiretor(req,res);
});

router.post("/cadastrarDiretor", function (req,res){
    secretariaController.cadastrarDiretor(req,res);
});

router.get("/confirmarOperacao/:idUsuario/:senha", function (req, res) {
    secretariaController.confirmarOperacao(req, res);
});

router.get("/escola/:idEscola", function (req,res) {
    secretariaController.escola(req, res);
});

router.put("/editarEscola/:idEscola", function (req, res) {
    secretariaController.editarEscola(req, res);
});

router.delete("/excluirEscola/:idEscola", function (req, res) {
    secretariaController.excluirEscola(req, res);
});

router.get("/diretor/:idUsuario", function (req, res) {
    secretariaController.diretor(req, res);
});

router.put("/editarDiretor/:idUsuario", function (req, res) {
    secretariaController.editarDiretor(req, res);
});

router.delete("/excluirDiretor/:idUsuario", function (req, res) {
    secretariaController.excluirDiretor(req, res);
});

module.exports = router;