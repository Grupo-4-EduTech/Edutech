var database = require("../database/config");

function buscarAlertas(idUsuario) {
    var instrucaoSql = `SELECT a.idUsuario, a.tipoAlerta, a.mensagemAlerta, u.nome, a.dataAlerta 
        FROM alerta AS a
        JOIN usuario AS u ON u.idUsuario = a.idUsuario
        WHERE a.idUsuario = '${idUsuario}'
        ORDER BY a.dataAlerta DESC;`
  console.log("Executando a instrução SQL: \n" + instrucaoSql);
  return database.executar(instrucaoSql);
}

function buscarUltimoRegistro(idUsuario) {
  var instrucaoSql = `
  SELECT a.idAlerta, a.idUsuario, a.dataAlerta, u.nome
  FROM alerta AS a
  JOIN usuario AS u ON u.idUsuario = a.idUsuario
  WHERE a.idUsuario = '${idUsuario}'
  ORDER BY a.dataAlerta DESC, a.idAlerta DESC
  LIMIT 1;`;
  console.log("Executando a instrução SQL: \n" + instrucaoSql);
  return database.executar(instrucaoSql);
}

module.exports = {
    buscarAlertas,
    buscarUltimoRegistro
}