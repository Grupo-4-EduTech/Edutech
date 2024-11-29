var express = require("express");
var router = express.Router();

var alertaController = require("../controllers/alertasController");

router.get("/puxarAlertas/:idUsuario", function (req, res) {

    alertaController.buscarAlertas(req, res);
});

router.get("/puxarUltimoAlerta/:id", function(req,res) {
    alertaController.buscarUltimoRegistro(req,res);
});

router.post("/cadastrarAlerta/:id", function(req, res) {
    alertaController.cadastrarAlerta(req, res);
})

module.exports = router;