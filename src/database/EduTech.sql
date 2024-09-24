DROP DATABASE IF EXISTS EduTech;
CREATE DATABASE IF NOT EXISTS EduTech;
USE EduTech;

CREATE TABLE diretoria(
	idDiretoria INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(45) NOT NULL
);

CREATE TABLE escola(
	idEscola INT AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    posicao INT NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    logradouro VARCHAR(100) NOT NULL,
    numLog INT NOT NULL,
    fkDiretoria INT,
    FOREIGN KEY (fkDiretoria) REFERENCES diretoria(idDiretoria),
    PRIMARY KEY (idEscola, fkDiretoria)
);

CREATE TABLE aluno(
	ra VARCHAR(20) NOT NULL,
    nome VARCHAR(45) NOT NULL,
    dtNasc DATE NOT NULL,
    dtMatricula DATE NOT NULL,
    fkEscola INT NOT NULL,
    FOREIGN KEY (fkEscola) REFERENCES escola(idEscola),
    PRIMARY KEY (ra, fkEscola)
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
	idUsuario INT AUTO_INCREMENT,
    nome VARCHAR(45) NOT NULL,
    email VARCHAR(256) NOT NULL,
    senha VARCHAR(256) NOT NULL,
    dtCadastro DATETIME NOT NULL,
    fkCargo INT NOT NULL,
    fkEscola INT,
    fkMateria INT,
    FOREIGN KEY (fkCargo) REFERENCES cargo(idCargo),
    FOREIGN KEY (fkEscola) REFERENCES escola(idEscola),
    FOREIGN KEY (fkMateria) REFERENCES materia(idMateria),
    PRIMARY KEY (idUsuario, fkCargo),
    CONSTRAINT ministerio CHECK (fkCargo != 1 OR (fkMateria IS NULL AND fkEscola IS NULL)), -- Se for do ministério, fkEscola e fkMatéria são nulas
    CONSTRAINT diretorTemEscola CHECK (fkCargo != 2 OR (fkMateria IS NULL AND fkEscola IS NOT NULL)), -- Se for diretor, fkEscola não pode ser nula
    CONSTRAINT ProfessorTemMateria CHECK (fkCargo != 3 OR (fkMateria IS NOT NULL AND fkEscola IS NOT NULL)) -- Se for professor, fkEscola e fkMateria não podem ser nulas
);

CREATE TABLE questao(
	idQuestao INT AUTO_INCREMENT,
    numero INT NOT NULL,
    valor DOUBLE(4,2) NOT NULL,
    respostaCorreta CHAR(1) NOT NULL,
    descritor VARCHAR(3) NOT NULL,
    fkMateria INT NOT NULL,
    FOREIGN KEY (fkMateria) REFERENCES materia(idMateria),
    PRIMARY KEY (idQuestao, fkMateria)
);

CREATE TABLE prova(
	idProva INT AUTO_INCREMENT,
    dtProva DATE NOT NULL,
    nota DECIMAL(5,2) NOT NULL, -- valor provisório
    bloco INT NOT NULL,
	fkMateria INT NOT NULL,
    fkQuestao INT NOT NULL,
    fkAluno VARCHAR(20) NOT NULL,
    FOREIGN KEY (fkMateria) REFERENCES materia(idMateria),
    FOREIGN KEY (fkQuestao) REFERENCES questao(idQuestao),
    FOREIGN KEY (fkAluno) REFERENCES aluno(ra),
    PRIMARY KEY (idProva, fkMateria, fkQuestao, fkAluno)
);

CREATE TABLE desempenho(
	idDesempenho INT AUTO_INCREMENT,
	notaFinal DECIMAL(4,1) NOT NULL, -- valor provisório
    fkAluno VARCHAR(20) NOT NULL,
    fkMateria INT NOT NULL,
    FOREIGN KEY (fkAluno) REFERENCES aluno(ra),
    FOREIGN KEY (fkMateria) REFERENCES materia(idMateria),
    PRIMARY KEY (idDesempenho, fkAluno, fkMateria)
);

CREATE TABLE contato(
	idContato INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(45) NOT NULL,
    sobrenome VARCHAR(45) NOT NULL,
    email VARCHAR(256) NOT NULL,
    nomeInstituicao VARCHAR(100) NOT NULL,
    mensagem VARCHAR(1000) NOT NULL
);

-- Inserts de exemplo

insert into cargo(descricao)
values ("Ministerio"), ("Diretor"), ("Professor");

insert into diretoria(nome)
values ("Centro 1"),("Centro 2"),("Centro 3");

insert into materia(nome)
values ("Matemática"),("Português");

insert into escola
values (default, "Escola teste 1", 1, "São Paulo", "Rua Haddock Lobo", 1, 1),(default, "Escola teste 2", 2, "São Paulo", "Rua Haddock Lobo", 2, 1),(default, "Escola teste 3", 3, "São Paulo", "Rua Haddock Lobo", 3, 1);

insert into usuario
values (default, "Ministro Fulano","minfulano@saopaulo.com","123",NOW(),1,null,null),(default, "Diretor Ciclano","dirciclano@escolaa.com","123",NOW(),2,1,null),(default, "Professor Beltrano","profbeltrano@escolaa.com","",NOW(),3,1,1);