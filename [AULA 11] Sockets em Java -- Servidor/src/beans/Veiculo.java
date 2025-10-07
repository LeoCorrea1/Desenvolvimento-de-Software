/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

import DAO.PessoaDAO;
import java.sql.*;

/**
 *
 * @author laboratorio
 */
public class Veiculo {
    
    private int id;
    private String modelo;
    private String placa;
    private Pessoa pessoaId;

    public Veiculo(){};
    
    public Veiculo(int id, String modelo, String placa){
        
        this.id = id;
        this.modelo = modelo;
        this.placa = placa;
        
    }
    
    public Veiculo(String modelo, String placa, Pessoa pessoaId){
        
        this.modelo = modelo;
        this.placa = placa;
        this.pessoaId = pessoaId;
        
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Pessoa getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Pessoa pessoaId) {
        this.pessoaId = pessoaId;
    }
    
    private Pessoa pessoa;

public Pessoa getPessoa() {
    return pessoa;
}

public void setPessoa(Pessoa pessoa) {
    this.pessoa = pessoa;
}


    
}
