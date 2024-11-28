var express = require("express");
var router = express.Router();

var secretariaController = require("../controllers/secretariaController");

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
    
module.exports = router;