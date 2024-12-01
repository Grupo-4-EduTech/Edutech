var alertasModel = require("../models/alertaModel");

function buscarAlertas(req, res) {
    var fkCargo = req.params.fkCargo;
    alertasModel.buscarAlertas(fkCargo).then((resultado => {
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

function puxarAlertasDiretor(req, res) {
    var fkEscola = req.params.fkEscola;
    var fkCargo = req.params.fkCargo;

    alertasModel.puxarAlertasDiretor(fkCargo, fkEscola).then((resultado => {
        res.status(200).json(resultado);
    }))
}

function puxarAlertasProfessor(req, res) {
    var fkUsuario = req.params.fkUsuario;
    var fkCargo = req.params.fkCargo;

    alertasModel.puxarAlertasProfessor(fkCargo, fkUsuario).then((resultado => {
        res.status(200).json(resultado);
    }))
}

module.exports = {
    buscarAlertas,
    buscarUltimoRegistro,
    puxarAlertasDiretor,
    puxarAlertasProfessor
}