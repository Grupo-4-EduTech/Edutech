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

router.get("/diretores", function (req,res) {
    secretariaController.diretores(req,res);
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
    
module.exports = router;