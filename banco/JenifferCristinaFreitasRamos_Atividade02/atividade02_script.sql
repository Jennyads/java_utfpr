SHOW DATABASES;

-- 2) Crie um banco de dados no SGBD com base no DER criado.
CREATE DATABASE empresa;
USE empresa;


CREATE TABLE departamentos (
    codigo INT PRIMARY KEY,
    nome VARCHAR(100)
);

CREATE TABLE funcionarios (
    codigo INT PRIMARY KEY,
    nome VARCHAR(100),
    qtd_dependentes INT,
    salario DECIMAL(10, 2),
    cargo VARCHAR(50),
    departamento_codigo INT,
    FOREIGN KEY (departamento_codigo) REFERENCES departamentos(codigo)
);

SHOW TABLES;

-- 3) Popule o banco de dados criado

INSERT INTO departamentos (codigo, nome) VALUES
(1, 'DIRETORIA GERAL'),
(2, 'DIRETORIA FINANCEIRA'),
(3, 'RECURSOS HUMANOS'),
(4, 'TI'),
(5, 'MARKETING');

SELECT * FROM departamentos;

INSERT INTO funcionarios (codigo, nome, qtd_dependentes, salario, cargo, departamento_codigo) VALUES
(1, 'Ana Silva', 2, 8500.00, 'Analista', 4),
(2, 'Carlos Souza', 0, 12000.00, 'Gerente', 2),
(3, 'Beatriz Costa', 1, 9500.00, 'Analista', 2),
(4, 'Daniel Lima', 3, 15000.00, 'Diretor', 1),
(5, 'Eduardo Ramos', 0, 7000.00, 'Assistente', 3),
(6, 'Fernanda Rocha', 0, 16000.00, 'Gerente', 1),
(7, 'Gustavo Nunes', 2, 8700.00, 'Desenvolvedor', 4),
(8, 'Helena Martins', 0, 7200.00, 'Assistente', 5),
(9, 'Igor Melo', 1, 7500.00, 'Analista', 5),
(10, 'Juliana Lopes', 0, 6200.00, 'Estagiária', 3),
(11, 'Lucas Ramos', 2, 1000.00, 'Desenvolvedor', 3),
(12, 'Jeniffer Ramos', 1, 11000.00, 'Desenvolvedora', 2),
(13, 'Matheus Cristian', 1, 1000.00, 'Estagiário', 2),
(14, 'Gabriel Cristian', 0, 1000.00, 'Estagiário', 2);


SELECT * FROM funcionarios;

-- 4) Faça uma VISÃO (view) para cada uma das seguintes consultas:
-- a. Mostre o nome do departamento que tem o maior número de funcionários, mostrando também a quantidade de funcionários desse departamento.

-- DROP VIEW IF EXISTS departamento_mais_funcionarios;

CREATE VIEW departamento_mais_funcionarios AS
SELECT d.nome AS nome_departamento, COUNT(f.codigo) AS total_funcionarios
FROM departamentos d
JOIN funcionarios f ON d.codigo = f.departamento_codigo
GROUP BY d.nome
HAVING COUNT(f.codigo) = (
    SELECT MAX(total)
    FROM (
        SELECT COUNT(f.codigo) AS total
        FROM departamentos d
        JOIN funcionarios f ON d.codigo = f.departamento_codigo
        GROUP BY d.nome
    ) AS subconsulta
);

SELECT * FROM departamento_mais_funcionarios;


-- b. Mostre o nome do departamento que tem a menor quantidade de funcionários sem dependentes, com a quantidade de funcionários.

CREATE VIEW departamento_menos_sem_dependentes AS
SELECT d.nome AS nome_departamento, COUNT(f.codigo) AS total_funcionarios_sem_dependentes
FROM departamentos d
JOIN funcionarios f ON d.codigo = f.departamento_codigo
WHERE f.qtd_dependentes = 0
GROUP BY d.nome
HAVING COUNT(f.codigo) = (
    SELECT MIN(total)
    FROM (
        SELECT COUNT(f.codigo) AS total
        FROM departamentos d
        JOIN funcionarios f ON d.codigo = f.departamento_codigo
        WHERE f.qtd_dependentes = 0
        GROUP BY d.nome
    ) AS subconsulta
);

SELECT * FROM departamento_menos_sem_dependentes;


-- c. Mostre o nome do departamento com os nomes dos seus respectivos funcionários de todos os departamentos onde o NOME DO DEPARTAMENTO começa com “DIR”.

CREATE VIEW funcionarios_departamentos_dir AS
SELECT d.nome AS nome_departamento, f.nome AS nome_funcionario
FROM departamentos d
JOIN funcionarios f ON d.codigo = f.departamento_codigo
WHERE d.nome LIKE 'DIR%';

SELECT * FROM funcionarios_departamentos_dir;

-- d. Mostre o nome do funcionário e do departamento ao qual pertence o funcionário com o maior salário.

CREATE VIEW funcionario_maior_salario AS
SELECT f.nome AS nome_funcionario, d.nome AS nome_departamento, f.salario
FROM funcionarios f
JOIN departamentos d ON f.departamento_codigo = d.codigo
WHERE f.salario = (
    SELECT MAX(salario)
    FROM funcionarios
);

SELECT * FROM funcionario_maior_salario;

-- e. Mostre o nome do departamento e do funcionário de cada departamento que tem o cargo de “Gerente”.

CREATE VIEW gerentes_por_departamento AS
SELECT d.nome AS nome_departamento, f.nome AS nome_funcionario
FROM funcionarios f
JOIN departamentos d ON f.departamento_codigo = d.codigo
WHERE f.cargo = 'Gerente';

SELECT * FROM gerentes_por_departamento;