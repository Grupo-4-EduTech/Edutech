var alertasModel = require("../models/alertaModel");

function buscarAlertas(req, res) {
    alertasModel.buscarAlertas().then((resultado => {
            res.status(200).json(resultado);
        }
    ))}

module.exports = {
    buscarAlertas
}