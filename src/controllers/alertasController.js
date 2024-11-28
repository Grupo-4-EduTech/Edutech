var alertasModel = require("../models/alertaModel");

function buscarAlertas(req, res) {
    var idUsuario = req.params.id;
    alertasModel.buscarAlertas(idUsuario).then((resultado => {
            res.status(200).json(resultado);
        }
    ))}

module.exports = {
    buscarAlertas
}