DROP DATABASE IF EXISTS EduTech;
CREATE DATABASE IF NOT EXISTS EduTech;
USE EduTech;

CREATE TABLE diretoria(
	idDiretoria INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(45) NOT NULL
);

CREATE TABLE escola(
	idEscola INT UNIQUE NOT NULL,
    nome VARCHAR(45) NULL,
    logradouro VARCHAR(45) NULL,
    numLogradouro INT NULL,
    fkDiretoria INT NOT NULL,
    FOREIGN KEY (fkDiretoria) REFERENCES diretoria(idDiretoria),
    PRIMARY KEY (idEscola, fkDiretoria)
);

CREATE TABLE turma(
	idTurma INT NOT NULL,
    serie INT NOT NULL,
    fkEscola INT NOT NULL,
    fkDiretoria INT NOT NULL,
    FOREIGN KEY (fkEscola, fkDiretoria) REFERENCES escola(idEscola, fkDiretoria),
    PRIMARY KEY(idTurma, fkEscola, fkDiretoria)
);


CREATE TABLE aluno(
	idAluno VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(45) NOT NULL,
    idade INT,
    sexo CHAR(1),
    proficienciaMT INT,
    proficienciaLP INT,
    nivel INT,
    fkTurma INT NOT NULL,
    fkEscola INT NOT NULL,
    fkDiretoria INT NOT NULL,
    FOREIGN KEY (fkTurma, fkEscola, fkDiretoria) REFERENCES turma(idTurma,fkEscola,fkDiretoria),
    PRIMARY KEY (idAluno, fkTurma)
);

CREATE TABLE materia(
	idMateria INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(45) NOT NULL
);

CREATE TABLE cargo(
	idCargo INT PRIMARY KEY AUTO_INCREMENT,
    descricao VARCHAR(20)
);

CREATE TABLE usuario(
	idUsuario INT AUTO_INCREMENT UNIQUE,
    nome VARCHAR(45) NOT NULL,
    email VARCHAR(256) NOT NULL,
    senha VARCHAR(256) NOT NULL,
    telefone CHAR(14),
    dtCadastro DATETIME NOT NULL,
    fkCargo INT NOT NULL,
    fkEscola INT,
    fkDiretoria INT,
    fkMateria INT,
    FOREIGN KEY (fkCargo) REFERENCES cargo(idCargo),
    FOREIGN KEY (fkEscola, fkDiretoria) REFERENCES escola(idEscola, fkDiretoria),
    FOREIGN KEY (fkMateria) REFERENCES materia(idMateria),
    PRIMARY KEY (idUsuario, fkCargo),
    CONSTRAINT secretaria CHECK (fkCargo != 1 OR (fkMateria IS NULL AND fkEscola IS NULL)), -- Se for do secretaria, fkEscola e fkMatéria são nulas
    CONSTRAINT diretorTemEscola CHECK (fkCargo != 2 OR (fkMateria IS NULL AND fkEscola IS NOT NULL)), -- Se for diretor, fkEscola não pode ser nula
    CONSTRAINT ProfessorTemMateria CHECK (fkCargo != 3 OR (fkMateria IS NOT NULL AND fkEscola IS NOT NULL)) -- Se for professor, fkEscola e fkMateria não podem ser nulas
);

CREATE TABLE questao(
	idQuestao INT NOT NULL UNIQUE,
    bloco INT NOT NULL,
    numero INT NOT NULL,
    respostaCorreta CHAR(1) NOT NULL,
    descritor VARCHAR(3) NOT NULL,
    fkMateria INT NOT NULL,
    FOREIGN KEY (fkMateria) REFERENCES materia(idMateria),
    PRIMARY KEY (idQuestao, fkMateria)
);

CREATE TABLE respostaAluno(
	idRespostaAluno INT NOT NULL AUTO_INCREMENT,
    resposta CHAR(1),
    fkAluno VARCHAR(20) NOT NULL,
    fkQuestao INT NOT NULL,
    FOREIGN KEY (fkAluno) REFERENCES aluno(idAluno),
    FOREIGN KEY (fkQuestao) REFERENCES questao(idQuestao),
	PRIMARY KEY (idRespostaAluno, fkAluno, fkQuestao)
);

CREATE TABLE contato(
	idContato INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(45) NOT NULL,
    sobrenome VARCHAR(45) NOT NULL,
    email VARCHAR(256) NOT NULL,
    nomeInstituicao VARCHAR(100) NOT NULL,
    mensagem VARCHAR(1000) NOT NULL
);

CREATE TABLE alerta (
	idAlerta INT PRIMARY KEY AUTO_INCREMENT,
    dataAlerta DATETIME,
    mensagemAlerta VARCHAR(100),
    idUsuario INT,
    FOREIGN KEY (idUsuario) REFERENCES usuario(idUsuario)
);

CREATE TABLE sugestoes(
	idSugestao INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(100),
    descricaoBreve VARCHAR(500),
    descricaoCompleta VARCHAR(5000),
    fkUsuario INT,
    fkTurma INT,
    FOREIGN KEY(fkUsuario) REFERENCES usuario(idUsuario),
    FOREIGN KEY(fkTurma) REFERENCES turma(idTurma)
);

CREATE TABLE professorTurma(
	fkTurma int,
    fkProfessor int,
    FOREIGN KEY(fkTurma) REFERENCES turma(idTurma),
    FOREIGN KEY(fkProfessor) REFERENCES usuario(idUsuario)
);

-- Inserts de exemplo

insert into cargo(descricao)
values ("Secretaria"), ("Diretor"), ("Professor");


insert into diretoria(nome)
values ("Centro 1"),("Centro 2"),("Centro 3");

insert into materia(nome)
values ("MT"),("LP");

insert into escola
values (1, "Escola teste 1","00000000", 1, 1),(2, "Escola teste 2", "00000000", 2, 1),(3, "Escola teste 3","00000000", 3, 1);

insert into usuario
values (default, "Secretário Fulano","secfulano@saopaulo.com","123","11999999999",NOW(),1,null,null,null),(default, "Diretor Ciclano","dirciclano@escolaa.com","123","11999999999",NOW(),2,1,1,null),(default, "Professor Beltrano","profbeltrano@escolaa.com","123","11999999999",NOW(),3,1,1,1);

INSERT INTO alerta (dataAlerta, mensagemAlerta, idUsuario)
VALUES 
    ('2024-10-29 10:15:00', 'Alerta: notas mais baixas em Geometria no último ano.', 1),
    ('2024-10-30 09:00:00', 'Atenção: desempenho abaixo da média em Matemática.', 2),
    ('2024-11-01 14:30:00', 'Aviso: alunos com dificuldades em Português detectados.', 3);

select (select count(idEscola)-3 from escola) qtdEscolas,(select count(idTurma) from turma) qtdTurmas, (select count(idAluno) from aluno) qtdAlunos,(select count(idQuestao) from questao) qtdQuestoes,count(idRespostaAluno) qtdRespostas from respostaAluno;

SELECT * FROM alerta;
SELECT a.mensagemAlerta AS 'Mensagem Apresentada' , u.nome AS 'Nome de quem recebeu', a.dataAlerta AS 'Data que foi entregue' FROM alerta AS a JOIN usuario AS u ON u.idUsuario = a.idUsuario;

use EduTech;
select * from respostaAluno;

select * from turma;
select count(idAluno), idTurma from aluno join Turma on fkTurma = idTurma group by fkturma order by count(idAluno) desc;
select * from usuario;

insert into usuario values
(default, "Professor teste", "profT1@escolaa.com", "123", "11999999999", NOW(), 3, 1, 1, 1);

insert into professorTurma values
(1289313, 3),
(1361594, 3),
(1448100, 3);

insert into professorTurma values
(1361414, 4),
(1455999, 4),
(1328874, 4);

select * from materia;
select * from professorTurma;
select * from aluno;
select * from usuario;
select * from turma;

-- Cards Turmas
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
        WHERE fkProfessor = 4 GROUP BY professorTurma.fkTurma;


select * from aluno where fkTurma = 1361414;

select * from respostaAluno 
	join questao ON fkQuestao = idQuestao
where fkAluno = 47108790 order by fkMateria;


select * from questao;

SELECT 
    t.idTurma,
    a.idAluno AS idAluno,
    a.nome AS nomeAluno,
    r.resposta,
   -- r.fkQuestao,
    --  q.idQuestao,
   --  q.bloco,
   --  q.numero,
    q.respostaCorreta,
    q.descritor,
    q.fkMateria
FROM 
    respostaAluno r
	JOIN aluno a ON r.fkAluno = a.idAluno
	JOIN turma t ON a.fkTurma = t.idTurma AND a.fkEscola = t.fkEscola AND a.fkDiretoria = t.fkDiretoria
	JOIN escola e ON t.fkEscola = e.idEscola
	JOIN diretoria d ON t.fkDiretoria = d.idDiretoria
	JOIN questao q ON r.fkQuestao = q.idQuestao
WHERE 
	t.idTurma = 1361414  AND fkMateria = 1
ORDER BY a.nome, r.fkQuestao;


SELECT 
    q.idQuestao,
    q.numero,
    q.descritor,
    q.respostaCorreta,
    COUNT(*) AS quantidadeErros
FROM 
    respostaAluno r
    JOIN aluno a ON r.fkAluno = a.idAluno
    JOIN turma t ON a.fkTurma = t.idTurma AND a.fkEscola = t.fkEscola AND a.fkDiretoria = t.fkDiretoria
    JOIN escola e ON t.fkEscola = e.idEscola
    JOIN diretoria d ON t.fkDiretoria = d.idDiretoria
    JOIN questao q ON r.fkQuestao = q.idQuestao
WHERE 
    t.idTurma = 1361414 AND 
    q.fkMateria = 1 AND 
    r.resposta != q.respostaCorreta
GROUP BY 
    q.idQuestao, q.numero, q.descritor, q.respostaCorreta
ORDER BY 
    quantidadeErros DESC
LIMIT 1;


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
    t.idTurma = 1361414 AND 
    q.fkMateria = 1 AND 
    r.resposta != q.respostaCorreta
GROUP BY 
    q.descritor
ORDER BY 
    quantidadeErros DESC
LIMIT 1;

