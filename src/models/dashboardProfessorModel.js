var database = require("../database/config");

function listarTurmas(idUsuario, condicao) {
    const instrucaoSql = `
        SELECT professorTurma.fkTurma AS idTurma,
                turma.nome AS nomeTurma,
		        materia.nome AS materia,
                CONCAT(SUBSTRING(avg(proficienciaMT),1, 3), ',',
                        SUBSTRING(avg(proficienciaMT),3, 1)) AS mediaMT,
                CONCAT(SUBSTRING(avg(proficienciaLP),1, 3), ',',
                        SUBSTRING(avg(proficienciaLP),3, 1)) AS mediaLP
                FROM professorTurma 
                    JOIN usuario ON fkProfessor = idUsuario
                    JOIN materia ON fkMateria = idMateria
                    JOIN aluno ON aluno.fkTurma = professorTurma.fkTurma
                    JOIN turma ON professorTurma.fkTurma = turma.idTurma
                WHERE fkProfessor = ${idUsuario} GROUP BY professorTurma.fkTurma, turma.nome ORDER BY  
                CASE 
                    WHEN avg(proficienciaMT) IS NULL THEN 1 
                    ELSE 0 
                END,
                CASE 
                    WHEN avg(proficienciaLP) IS NULL THEN 1 
                    ELSE 0 
                END,
                ${condicao};;
                `;
    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

function listarDificuldadePorTurma(idTurma, idMateria) {
    const instrucaoSql = `
        SELECT 
            q.descritor,
            COUNT(*) AS quantidadeErros
        FROM 
            respostaAluno r
            JOIN aluno a ON r.fkAluno = a.idAluno
            JOIN turma t ON a.fkTurma = t.idTurma AND a.fkEscola = t.fkEscola AND a.fkDiretoria = t.fkDiretoria
            JOIN escola e ON t.fkEscola = e.idEscola
            JOIN diretoria d ON t.fkDiretoria = d.idDiretoria
            JOIN questao q ON r.fkQuestao = q.idQuestao
        WHERE 
            t.idTurma = ${idTurma} AND 
            q.fkMateria = ${idMateria} AND  
            r.resposta != q.respostaCorreta  
        GROUP BY 
            q.descritor 
        ORDER BY 
            quantidadeErros DESC 
        LIMIT 1;  

    `;
    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}


function listarDesempenhoPorTurma(idUsuario, idTurma) {
    const instrucaoSql = `
        SELECT professorTurma.fkTurma AS idTurma,
		        materia.nome AS materia,
                CONCAT(SUBSTRING(avg(proficienciaMT),1, 3), ',',
                        SUBSTRING(avg(proficienciaMT),3, 1)) AS mediaMT,
                CONCAT(SUBSTRING(avg(proficienciaLP),1, 3), ',',
                        SUBSTRING(avg(proficienciaLP),3, 1)) AS mediaLP
                FROM professorTurma 
                    JOIN usuario ON fkProfessor = idUsuario
                    JOIN materia ON fkMateria = idMateria
                    JOIN aluno ON aluno.fkTurma = professorTurma.fkTurma
                WHERE fkProfessor = ${idUsuario} AND professorTurma.fkTurma = ${idTurma}
                GROUP BY professorTurma.fkTurma;
    `;
    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

function listarMediaAlunosPorTurma(idUsuario, idTurma, idMateria) {
    const instrucaoSql = `
       SELECT 
            idAluno,
            a.fkTurma,
            a.nome,
            t.nome AS nomeTurma, 
            m.nome AS materia,
            IFNULL(CONCAT(SUBSTRING(avg(proficienciaMT), 1, 3), ',', SUBSTRING(avg(proficienciaMT), 3, 1)), 'N/A') AS mediaMT,
            IFNULL(CONCAT(SUBSTRING(avg(proficienciaLP), 1, 3), ',', SUBSTRING(avg(proficienciaLP), 3, 1)), 'N/A') AS mediaLP
        FROM 
            aluno AS a
            JOIN professorTurma AS p ON a.fkTurma = p.fkTurma
            JOIN usuario ON fkProfessor = idUsuario
            JOIN materia AS m ON fkMateria = idMateria
            JOIN turma AS t ON idTurma = p.fkTurma
        WHERE 
            fkProfessor = ${idUsuario} AND a.fkTurma = ${idTurma}
        GROUP BY 
            idAluno, t.nome
        ORDER BY 
            CASE WHEN avg(proficienciaMT) IS NULL THEN 1 ELSE 0 END,  -- Coloca os registros com mediaMT NULL por último
            CASE WHEN avg(proficienciaLP) IS NULL THEN 1 ELSE 0 END,  -- Coloca os registros com mediaLP NULL por último
            ${idMateria};  -- Ordenação adicional por matéria
    `;
    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

function obterDadosgrafico(idMateria, idTurma) {
    const instrucaoSql = `
       SELECT 
            t.idTurma,
            q.descritor,
            COUNT(*) AS quantidadeErros
        FROM 
            respostaAluno r
            JOIN aluno a ON r.fkAluno = a.idAluno
            JOIN turma t ON a.fkTurma = t.idTurma AND a.fkEscola = t.fkEscola AND a.fkDiretoria = t.fkDiretoria
            JOIN escola e ON t.fkEscola = e.idEscola
            JOIN diretoria d ON t.fkDiretoria = d.idDiretoria
            JOIN questao q ON r.fkQuestao = q.idQuestao
        WHERE 
            t.idTurma = ${idTurma} AND 
            q.fkMateria = ${idMateria} AND 
            r.resposta != q.respostaCorreta
        GROUP BY 
            q.descritor
        ORDER BY 
            quantidadeErros DESC;
    `;
    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

function listarAlunosIndividualmente(idMateria, idTurma, ordenacao) {
    const instrucaoSql = `
        SELECT
            a.idAluno,
            a.nome,
            t.nome AS nomeTurma,
            IFNULL(CONCAT(SUBSTRING(avg(proficienciaMT), 1, 3), ',', SUBSTRING(avg(proficienciaMT), 3, 1)), 'N/A') AS mediaMT,
            IFNULL(CONCAT(SUBSTRING(avg(proficienciaLP), 1, 3), ',', SUBSTRING(avg(proficienciaLP), 3, 1)), 'N/A') AS mediaLP,
            COUNT(r.idRespostaAluno) AS quantidadeErros,  -- Contando apenas respostas erradas
            (SELECT q.descritor
            FROM respostaAluno r2
            JOIN questao q ON r2.fkQuestao = q.idQuestao
            WHERE r2.fkAluno = a.idAluno
            AND r2.resposta != q.respostaCorreta
            AND q.fkMateria = ${idMateria}
            GROUP BY q.descritor
            ORDER BY COUNT(*) DESC
            LIMIT 1) AS dificuldade
        FROM
            aluno a
            LEFT JOIN respostaAluno r ON r.fkAluno = a.idAluno AND r.resposta != (SELECT respostaCorreta FROM questao WHERE idQuestao = r.fkQuestao AND fkMateria = 1)
            LEFT JOIN turma t ON a.fkTurma = t.idTurma AND a.fkEscola = t.fkEscola AND a.fkDiretoria = t.fkDiretoria
            LEFT JOIN escola e ON t.fkEscola = e.idEscola
            LEFT JOIN diretoria d ON t.fkDiretoria = d.idDiretoria
            LEFT JOIN questao q ON r.fkQuestao = q.idQuestao AND q.fkMateria = ${idMateria}
        WHERE
            t.idTurma = ${idTurma} 
        GROUP BY
            a.idAluno, a.nome
        ORDER BY
            ${ordenacao};
                        `;
    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

function pesquisarAluno(idMateria, idTurma, pesquisa) {
    const instrucaoSql = `
        SELECT
            a.idAluno,
            a.nome,
            IFNULL(CONCAT(SUBSTRING(AVG(proficienciaMT), 1, 3), ',', SUBSTRING(AVG(proficienciaMT), 3, 1)), 'N/A') AS mediaMT,
            IFNULL(CONCAT(SUBSTRING(AVG(proficienciaLP), 1, 3), ',', SUBSTRING(AVG(proficienciaLP), 3, 1)), 'N/A') AS mediaLP, 
            COUNT(r.idRespostaAluno) AS quantidadeErros,  -- Contando apenas respostas erradas
            (SELECT q.descritor
            FROM respostaAluno r2
            JOIN questao q ON r2.fkQuestao = q.idQuestao
            WHERE r2.fkAluno = a.idAluno
            AND r2.resposta != q.respostaCorreta
            AND q.fkMateria = ${idMateria}
            GROUP BY q.descritor
            ORDER BY COUNT(*) DESC
            LIMIT 1) AS dificuldade
        FROM
            aluno a
            LEFT JOIN respostaAluno r ON r.fkAluno = a.idAluno AND r.resposta != (SELECT respostaCorreta FROM questao WHERE idQuestao = r.fkQuestao AND fkMateria = 1)
            LEFT JOIN turma t ON a.fkTurma = t.idTurma AND a.fkEscola = t.fkEscola AND a.fkDiretoria = t.fkDiretoria
            LEFT JOIN escola e ON t.fkEscola = e.idEscola
            LEFT JOIN diretoria d ON t.fkDiretoria = d.idDiretoria
            LEFT JOIN questao q ON r.fkQuestao = q.idQuestao AND q.fkMateria = ${idMateria}
        WHERE
            t.idTurma = ${idTurma} AND a.nome like "%${pesquisa}%"
        GROUP BY
            a.idAluno, a.nome;
                        `;
    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

module.exports = {
    listarTurmas,
    listarDificuldadePorTurma,
    listarDesempenhoPorTurma,
    listarMediaAlunosPorTurma,
    obterDadosgrafico,
    listarAlunosIndividualmente,
    pesquisarAluno
};

