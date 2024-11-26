var database = require("../database/config");

function buscarAlertas() {
    var instrucaoSql = `SELECT a.tipoAlerta, a.mensagemAlerta, u.nome, a.dataAlerta FROM alerta AS a JOIN usuario AS u ON u.idUsuario = a.idUsuario;`;
  console.log("Executando a instrução SQL: \n" + instrucaoSql);
  return database.executar(instrucaoSql);
}

module.exports = {
    buscarAlertas
}