var express = require("express");
var router = express.Router();

var alertaController = require("../controllers/alertasController");

router.get("/puxarAlertas/:fkCargo", function (req, res) {

    alertaController.buscarAlertas(req, res);
});

router.get("/puxarUltimoAlerta/:id", function(req,res) {
    alertaController.buscarUltimoRegistro(req,res);
});

router.get("/puxarAlertasDiretor/:fkCargo/:fkEscola", function(req,res) {
    alertaController.puxarAlertasDiretor(req,res);
})

router.get("/puxarAlertasProfessor/:fkCargo/:fkUsuario", function(req,res) {
    alertaController.puxarAlertasProfessor(req,res);
})

module.exports = router;