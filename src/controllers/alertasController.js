var alertasModel = require("../models/alertaModel");

function buscarAlertas(req, res) {
    var idUsuario = req.params.idUsuario;
    alertasModel.buscarAlertas(idUsuario).then((resultado => {
            res.status(200).json(resultado);
        }
    ))
}

function buscarUltimoRegistro(req, res) {
    var idUsuario = req.params.id;
    alertasModel.buscarUltimoRegistro(idUsuario).then((resultado => {
        res.status(200).json(resultado);
    }))
}

function cadastrarAlerta(req, res) {
    var idUsuario = req.params.id;
    var mensagemUsuario = req.body.mensagemServer;

    alertasModel.cadastrarAlertas(idUsuario, mensagemUsuario).then((resultado => {
        res.status(200).json(resultado);
    }))
}

module.exports = {
    buscarAlertas,
    buscarUltimoRegistro,
    cadastrarAlerta
}