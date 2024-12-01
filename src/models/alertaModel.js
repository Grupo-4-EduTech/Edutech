var database = require("../database/config");

function buscarAlertas(fkCargo) {
    var instrucaoSql = `SELECT dataAlerta,
	     mensagemAlerta,
       tipoAlerta
       FROM alerta
       WHERE fkCargo = ${fkCargo} OR fkturma = 0;`
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

function puxarAlertasDiretor(fkCargo, fkEscola) {
  var instrucaoSql = `
    SELECT dataAlerta,
      mensagemAlerta,
        tipoAlerta
        FROM alerta
        JOIN turma ON fkturma = idTurma
        WHERE turma.fkEscola = ${fkEscola} AND fkCargo = ${fkCargo} OR fkCargo IS NULL;
 `;
  console.log("Executando a instrução SQL: \n" + instrucaoSql);
  return database.executar(instrucaoSql);
}
function puxarAlertasProfessor(fkCargo, fkUsuario) {
  var instrucaoSql = `
    SELECT dataAlerta,
       mensagemAlerta,
       tipoAlerta
        FROM alerta
        LEFT JOIN professorTurma ON alerta.fkturma = professorTurma.fkTurma
        WHERE professorTurma.fkProfessor = ${fkUsuario} AND alerta.fkCargo = ${fkCargo} OR alerta.fkCargo IS NULL;
 `;
  console.log("Executando a instrução SQL: \n" + instrucaoSql);
  return database.executar(instrucaoSql);
}


module.exports = {
    buscarAlertas,
    buscarUltimoRegistro,
    puxarAlertasDiretor,
    puxarAlertasProfessor
}