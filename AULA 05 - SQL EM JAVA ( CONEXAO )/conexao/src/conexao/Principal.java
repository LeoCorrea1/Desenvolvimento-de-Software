/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexao;

import DAO.PessoaDAO;
import beans.Pessoa;

/**
 *
 * @author laboratorio
 */
public class Principal {
    public static void main(String[] args) {
        /*Conexao con = new Conexao();
        con.getConexao();*/
        
        Pessoa p = new Pessoa();
        p.setNome("Leonardo Correa");
        p.setSexo("M");
        p.setIdioma("Portugues");
        
        PessoaDAO pDAO = new PessoaDAO();
        pDAO.inserir(p);
    }
    
}
