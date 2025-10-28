create database BDAula01;
use BDAula01;

show databases;

CREATE TABLE PESSOA (
     id int AUTO_INCREMENT PRIMARY KEY,
     nome varchar(50) NOT NULL,
     sexo varchar(1) NOT NULL,
     idioma varchar(10) NOT NULL
     );

show tables;
desc PESSOA;

  INSERT INTO PESSOA (nome,sexo,idioma)
     VALUES
    ("Ricardo","M","Portugues"),
    ("Leonardo","M","Portugues"),
    ("Britany","F","Ingles"),
    ("Britany","F","Ingles"),
    ("Gerald","M","Ingles"),
    ("william","M","Ingles"),
    ("Umberto","M","Espanhol"),
    ("Jostein","M","Alemao"),
    ("Stephen","M","Holandes");
  

select * from PESSOA;

CREATE TABLE veiculo (
     id int AUTO_INCREMENT PRIMARY KEY,
     modelo varchar(50) NOT NULL,
     placa varchar(1) NOT NULL,
     id_pessoa int NOT NULL,
     foreign key(id_pessoa) references pessoa(id)
     );

INSERT INTO veiculo (modelo,placa,id_pessoa)
     VALUES
    ("teste123","213CA32","2"),
    ("carroRicardo","Tsd234","1");


-- Tabela de Categorias
CREATE TABLE categorias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

INSERT INTO Categorias (nome) VALUES
("Eletrônicos"),
("Alimentos"),
("Roupas");


-- Tabela de Produtos
CREATE TABLE produtos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    quantidade INT NOT NULL,
    categoria_id INT,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);


--NOVO BANCO DE DADOS ( SERA USADO NAS PROXIMAS AULAS )

CREATE DATABASE escola;
use escola;

 create table ALUNOS(
    id int AUTO_INCREMENT PRIMARY KEY,
    nome varchar(50) NOT NULL,
    idade int NOT NULL,
    curso varchar(50) NOT NULL
    );

INSERT INTO ALUNOS(nome,idade,curso)
     VALUES
     ('Joao',20,'Matematica'),
     ('Maria',22,'Historia'),
     ('Pedro',21,'Ciencia da Computaçao'),
     ('Ana',19,'Biologia'),
     ('Carlos',23,'Economia');

create table Professores(
    id int AUTO_INCREMENT PRIMARY KEY,
    nome varchar(50) NOT NULL,
    idade int NOT NULL,
    disciplina varchar(50) NOT NULL
    );

INSERT INTO professores(nome,idade,disciplina)
     VALUES
     ('Ricardo',33,'Desenvolvimento de Software'),
     ('Zamberlan',39,'Pesquisa e Ordenaçao'),
     ('Andre',27,'Modelagem e SImulaçao');

create table Matriculas(
    id int AUTO_INCREMENT PRIMARY KEY,
    id_aluno INT,
    id_professor INT, 
    data_matricula DATE,
    FOREIGN KEY (id_aluno) REFERENCES ALUNOS(id),
    FOREIGN KEY (id_professor) REFERENCES Professores(id) 
    );

INSERT INTO Matriculas(id_aluno,id_professor,data_matricula)
    VALUES
    (1,1,'2023-01-15'),
    (2,2,'2023-02-20'),
    (3,3,'2023-03-10'),
    (4,1,'2023-04-05'),
    (5,2,'2023-05-12');

select * from matriculas;

select nome, curso FROM alunos;

select nome , disciplina from professores;

--NOVO BANCO PRODUTOS

-- Tabela de Categorias



-- Tabela de Produtos

create database Vendas;
use vendas;

CREATE TABLE categorias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

INSERT INTO Categorias (nome) VALUES
("Eletrônicos"),
("Alimentos"),
("Roupas");

CREATE TABLE produtos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    quantidade INT NOT NULL,
    categoria_id INT,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);



--banco REVISAO PROVA 2

CREATE DATABASE revisao;
use revisao;

CREATE TABLE  professores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(60) NOT NULL,
    email VARCHAR(80) NOT NULL UNIQUE
);

CREATE TABLE disciplinas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(80) NOT NULL,
    carga_horaria INT NOT NULL,
    professor_id INT NOT NULL,
    FOREIGN KEY (professor_id) REFERENCES professores(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

INSERT INTO professores (nome, email) VALUES
('Ana Silva', 'ana.silva@email.com'),
('Carlos Souza', 'carlos.souza@email.com');

INSERT INTO disciplinas (nome, carga_horaria, professor_id) VALUES
('Matemática', 60, 1),
('Física', 80, 1),
('Química', 70, 2);


    



