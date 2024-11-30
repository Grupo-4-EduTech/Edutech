var database = require("../database/config");

function rank(fkEscola){
    var instrucaoSql = `SELECT ranking FROM (SELECT RANK() OVER(ORDER BY SUBSTRING((AVG(proficienciaLP)+AVG(proficienciaMT))/2,1,4)/10 DESC) ranking, idEscola, (SUBSTRING((AVG(proficienciaLP)+AVG(proficienciaMT))/2,1,4)/10) media FROM escola JOIN aluno ON aluno.fkEscola = idEscola GROUP BY idEscola) escola WHERE idEscola = ${fkEscola}`;

    return database.executar(instrucaoSql);
}

function porcentagemAbaixoMediaLP(fkEscola) {
    var instrucaoSql = `SELECT CAST((COUNT(*)*100/(SELECT COUNT(*) FROM aluno WHERE fkEscola = ${fkEscola})) AS DECIMAL) porcentagem FROM aluno WHERE fkEscola = ${fkEscola} AND (CAST(SUBSTRING(proficienciaMT, 1, 4) / 10 AS DECIMAL) < 325 OR proficienciaMT IS NULL)`;

    return database.executar(instrucaoSql);
}

function porcentagemAbaixoMediaMT(fkEscola) {
    var instrucaoSql = `SELECT CAST((COUNT(*)*100/(SELECT COUNT(*) FROM aluno WHERE fkEscola = ${fkEscola})) AS DECIMAL) porcentagem FROM aluno WHERE fkEscola = ${fkEscola} AND (CAST(SUBSTRING(proficienciaLP, 1, 4) / 10 AS DECIMAL) < 325 OR proficienciaLP IS NULL)`;

    return database.executar(instrucaoSql);
}

function turmaMaisDificuldade(fkEscola) {
    var instrucaoSql = `SELECT turma.nome, (SUBSTRING((AVG(proficienciaLP)+AVG(proficienciaMT))/2,1,4)/10) media FROM turma JOIN aluno ON fkTurma = idTurma WHERE turma.fkEscola = ${fkEscola} GROUP BY turma.nome ORDER BY media LIMIT 1`;

    return database.executar(instrucaoSql);
}

function turmasProficiencia(fkEscola) {
    var instrucaoSql = `SELECT turma.nome, (SUBSTRING((AVG(proficienciaLP)+AVG(proficienciaMT))/2,1,4)/10) media FROM turma JOIN aluno ON fkTurma = idTurma WHERE turma.fkEscola = ${fkEscola} GROUP BY turma.nome ORDER BY media`;

    return database.executar(instrucaoSql);
}

function professores(fkEscola) {
    var instrucaoSql = `SELECT idUsuario, usuario.nome, fkMateria, GROUP_CONCAT(turma.nome SEPARATOR ', ') turmas  FROM usuario LEFT JOIN professorTurma ON fkProfessor = idUsuario LEFT JOIN turma ON professorTurma.fkTurma = idTurma WHERE usuario.fkEscola = ${fkEscola} AND fkCargo = 3 GROUP BY idUsuario`;

    return database.executar(instrucaoSql);
}

function infoProfessor(idUsuario) {
    var instrucaoSql = `SELECT usuario.nome, email, telefone, fkMateria, GROUP_CONCAT(turma.nome SEPARATOR ', ') turmas FROM usuario LEFT JOIN professorTurma ON fkProfessor = idUsuario LEFT JOIN turma ON professorTurma.fkTurma = idTurma WHERE idUsuario = ${idUsuario}`;

    return database.executar(instrucaoSql);
}

function turmas(fkEscola) {
    var instrucaoSql = `SELECT idTurma, turma.nome, (SUBSTRING(AVG(proficienciaLP),1,4)/10) mediaLP, (SUBSTRING(AVG(proficienciaMT),1,4)/10) mediaMT from turma JOIN aluno ON fkTurma = idTurma WHERE turma.fkEscola = ${fkEscola} GROUP BY idTurma, turma.nome`;

    return database.executar(instrucaoSql);
}

function conteudoMaisDificuldade(fkTurma) {
    var instrucaoSql = `SELECT SUM(CASE WHEN resposta!=respostaCorreta THEN 1 ELSE 0 END) erros, fkMateria, descritor FROM respostaAluno JOIN questao ON fkQuestao = idQuestao JOIN aluno ON fkAluno = idAluno WHERE fkTurma = ${fkTurma} GROUP BY descritor, fkMateria ORDER BY erros DESC LIMIT 1`;

    return database.executar(instrucaoSql)
}

function cadastrarProfessor(nome, email, senha, telefone, fkEscola, fkMateria){
    var instrucaoSql = `
        INSERT INTO usuario (nome, email, senha, telefone, dtCadastro, fkCargo, fkEscola, fkDiretoria, fkMateria)
        VALUES ('${nome}','${email}','${senha}','${telefone}',NOW(),3,${fkEscola},1,${fkMateria})
    `

    return database.executar(instrucaoSql).then(result => {
        return { id: result.insertId };
    });
}

function turmasSemProfessor(fkEscola, fkMateria){
    var instrucaoSql = `
        SELECT t.idTurma, t.nome FROM turma t
        LEFT JOIN (SELECT pt.fkTurma, u.fkMateria FROM professorTurma pt INNER JOIN usuario u ON pt.fkProfessor = u.idUsuario)
        turmasProfessores ON t.idTurma = turmasProfessores.fkTurma AND turmasProfessores.fkMateria = ${fkMateria}
        WHERE t.fkEscola = ${fkEscola} AND turmasProfessores.fkMateria IS NULL;
    `;

    return database.executar(instrucaoSql);
}

function atribuirTurmas(fkProfessor, fkTurma){
    var instrucaoSql = `INSERT INTO professorTurma (fkProfessor, fkTurma) VALUES (${fkProfessor},${fkTurma})`;

    return database.executar(instrucaoSql);
}

module.exports = {
    rank,
    porcentagemAbaixoMediaLP,
    porcentagemAbaixoMediaMT,
    turmaMaisDificuldade,
    turmasProficiencia,
    professores,
    infoProfessor,
    turmas,
    conteudoMaisDificuldade,
    cadastrarProfessor,
    turmasSemProfessor,
    atribuirTurmas,
}