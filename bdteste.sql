create database if not exists teste;
create schema if not exists teste;


CREATE TABLE if not exists produtos (
    id_produto INT PRIMARY KEY,
    nome_produto VARCHAR(255) NOT NULL,
    quantidade_produto INT,
    preco_produto DECIMAL(10, 2)
);