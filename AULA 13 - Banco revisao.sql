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
