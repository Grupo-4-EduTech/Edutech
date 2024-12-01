var database = require("../database/config");

function mediaMaterias(){
    var instrucaoSql = "SELECT SUBSTRING(avg(proficienciaLP),1,4)/10 mediaLP, SUBSTRING(avg(proficienciaMT),1,4)/10 mediaMT from aluno";

    return database.executar(instrucaoSql);
}

function porcentagemAbaixoMediaLP(){
    var instrucaoSql = "SELECT CAST((COUNT(*)*100/(SELECT COUNT(*) FROM aluno)) AS DECIMAL) porcentagem FROM aluno WHERE SUBSTRING(proficienciaLP, 1, 4) / 10 < 325 OR proficienciaLP IS NULL";

    return database.executar(instrucaoSql)
}

function porcentagemAbaixoMediaMT() {
    var instrucaoSql = "SELECT CAST((COUNT(*)*100/(SELECT COUNT(*) FROM aluno)) AS DECIMAL) porcentagem FROM aluno WHERE SUBSTRING(proficienciaMT, 1, 4) / 10 < 325 OR proficienciaMT IS NULL";

    return database.executar(instrucaoSql)
}

function comparacaoRegiao(){
    var instrucaoSql = "SELECT idRegiao regiao, SUBSTRING(AVG(proficienciaLP), 1,4)/10 mediaLP, SUBSTRING(AVG(proficienciaMT),1,4)/10 mediaMT FROM escola JOIN aluno ON fkEscola = idEscola GROUP BY idRegiao";

    return database.executar(instrucaoSql)
}

function rankingEscolas(){
    var instrucaoSql = "SELECT escola.nome nome, SUBSTRING((AVG(proficienciaLP)+AVG(proficienciaMT))/2,1,4)/10 media FROM escola JOIN aluno ON fkEscola = idEscola GROUP BY escola.nome ORDER BY media DESC";

    return database.executar(instrucaoSql)
}

function escolas(){
    var instrucaoSql = "SELECT idEscola, escola.nome escola, usuario.nome diretor, idRegiao, SUBSTRING((AVG(proficienciaLP)+AVG(proficienciaMT))/2,1,4)/10 media FROM escola LEFT JOIN usuario ON idEscola=usuario.fkEscola AND fkCargo = 2 LEFT JOIN aluno ON idEscola=aluno.fkEscola GROUP BY idEscola,escola,diretor,idRegiao";

    return database.executar(instrucaoSql)
}

function escolasFiltro(idRegiao){
    var instrucaoSql = `SELECT idEscola, escola.nome escola, usuario.nome diretor, idRegiao, SUBSTRING((AVG(proficienciaLP)+AVG(proficienciaMT))/2,1,4)/10 media FROM escola LEFT JOIN usuario ON idEscola=usuario.fkEscola AND fkCargo = 2 LEFT JOIN aluno ON idEscola=aluno.fkEscola WHERE idRegiao = ${idRegiao} GROUP BY idEscola,escola,diretor,idRegiao`;
    
    return database.executar(instrucaoSql);
}

function escolasPesquisa(pesquisa) {
    var instrucaoSql = `SELECT idEscola, escola.nome escola, usuario.nome diretor, idRegiao, SUBSTRING((AVG(proficienciaLP)+AVG(proficienciaMT))/2,1,4)/10 media FROM escola LEFT JOIN usuario ON idEscola=usuario.fkEscola AND fkCargo = 2 LEFT JOIN aluno ON idEscola=aluno.fkEscola WHERE escola.nome LIKE '%${pesquisa}%' GROUP BY idEscola,escola,diretor,idRegiao`;

    return database.executar(instrucaoSql);
}

function diretores(){
    var instrucaoSql = "SELECT idUsuario, usuario.nome diretor, escola.nome escola, email FROM usuario JOIN escola ON idEscola=fkEscola AND fkCargo=2 ORDER BY diretor;";

    return database.executar(instrucaoSql)
}

function diretoresPesquisa(pesquisa) {
    var instrucaoSql = `SELECT idUsuario, usuario.nome diretor, escola.nome escola, email FROM usuario JOIN escola ON idEscola=fkEscola AND fkCargo=2 WHERE usuario.nome LIKE '%${pesquisa}%' ORDER BY diretor;`;

    return database.executar(instrucaoSql)
}

function cadastroEscola(nome, logradouro, numero, regiao){
    var instrucaoSql = `INSERT INTO escola (nome, logradouro, numLogradouro, idRegiao, fkDiretoria) VALUES ('${nome}','${logradouro}',${numero},${regiao},1)`;

    return database.executar(instrucaoSql)
}

function escolasSemDiretor(){
    var instrucaoSql = "SELECT idEscola, escola.nome FROM escola LEFT JOIN usuario ON idEscola = fkEscola AND fkCargo = 2 WHERE idUsuario IS NULL";

    return database.executar(instrucaoSql)
}

function cadastrarDiretor(nome, email, senha, telefone, fkEscola){
    var instrucaoSql = `INSERT INTO usuario (nome, email, senha, telefone, dtCadastro, fkCargo, fkEscola, fkDiretoria) VALUES ('${nome}','${email}','${senha}','${telefone}',NOW(),2,${fkEscola},1)`;

    return database.executar(instrucaoSql)
}

function confirmarOperacao(idUsuario, senha) {
    var instrucaoSql = `SELECT * FROM usuario WHERE idUsuario = ${idUsuario} AND senha = '${senha}'`;

    return database.executar(instrucaoSql);
}

function escola(idEscola){
    var instrucaoSql = `SELECT nome, logradouro, numLogradouro, idRegiao FROM escola WHERE idEscola = ${idEscola}`;

    return database.executar(instrucaoSql);
}

function editarEscola(nome, regiao, logradouro, numero, idEscola){
    var instrucaoSql = `UPDATE escola SET nome = '${nome}', idRegiao = '${regiao}', logradouro = '${logradouro}', numLogradouro = ${numero} WHERE idEscola = ${idEscola}`;

    return database.executar(instrucaoSql);
}

function excluirEscola(idEscola){
    var instrucaoSql = `DELETE FROM escola WHERE idEscola = ${idEscola}`;

    return database.executar(instrucaoSql);
}

function diretor(idUsuario) {
    var instrucaoSql = `SELECT usuario.nome nomeDiretor, email, telefone, fkEscola, escola.nome nomeEscola FROM usuario JOIN escola ON fkEscola = idEscola WHERE idUsuario = ${idUsuario}`;

    return database.executar(instrucaoSql);
}

function editarDiretor(nome, email, telefone, fkEscola, idUsuario) {
    var instrucaoSql = `UPDATE usuario SET nome = '${nome}', email = '${email}', telefone = '${telefone}', fkEscola = ${fkEscola} WHERE idUsuario = ${idUsuario}`;

    return database.executar(instrucaoSql);
}

function excluirDiretor(idUsuario) {
    var instrucaoSql = `DELETE FROM usuario WHERE idUsuario = ${idUsuario}`;

    return database.executar(instrucaoSql);
}

module.exports = {
    mediaMaterias,
    porcentagemAbaixoMediaLP,
    porcentagemAbaixoMediaMT,
    comparacaoRegiao,
    rankingEscolas,
    escolas,
    escolasFiltro,
    escolasPesquisa,
    diretores,
    diretoresPesquisa,
    cadastroEscola,
    escolasSemDiretor,
    cadastrarDiretor,
    confirmarOperacao,
    escola,
    editarEscola,
    excluirEscola,
    diretor,
    editarDiretor,
    excluirDiretor,
}