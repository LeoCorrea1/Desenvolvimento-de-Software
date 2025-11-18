package com.example.projetofxcombd.conexao;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;

public class Db {
    private static final String URL = "jdbc:mysql://localhost:3306/BDAula01?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "laboratorio"; // ajuste aqui

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}

/*
CREATE DATABASE IF NOT EXISTS bdaula01;
CREATE TABLE IF NOT EXISTS pessoa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    sexo VARCHAR(1) NOT NULL,
    idioma VARCHAR(10) NOT NULL
);
 */