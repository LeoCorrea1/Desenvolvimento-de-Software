/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Beans;

/**
 *
 * @author Administrador
 */
public class Funcionario {
    
    public String nome;
    public int idade;
    public int clientesAtendidos;

    
    public Funcionario() {
    }
    
    
    public Funcionario(String nome, int idade, int clientesAtendidos) {
        this.nome = nome;
        this.idade = idade;
        this.clientesAtendidos = clientesAtendidos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public int getClientesAtendidos() {
        return clientesAtendidos;
    }

    public void setClientesAtendidos(int clientesAtendidos) {
        this.clientesAtendidos = clientesAtendidos;
    }
    
    
    
}
