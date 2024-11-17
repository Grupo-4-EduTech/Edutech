var express = require("express");
var router = express.Router();

var sugestoesController = require("../controllers/sugestoesController");

router.get("/listar/:idUsuario", function (req, res) {
    sugestoesController.listar(req, res);
});

module.exports = router;