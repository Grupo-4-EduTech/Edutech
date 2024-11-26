var express = require("express");
var router = express.Router();

var alertaController = require("../controllers/alertasController");

router.get("/puxarAlertas", function (req, res) {
    alertaController.buscarAlertas(req, res);
});

module.exports = router;