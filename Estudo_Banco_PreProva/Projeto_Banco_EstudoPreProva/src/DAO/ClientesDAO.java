/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Beans.Cliente;
import conexao.Conexao;
import java.sql.*;

/**
 *
 * @author Administrador
 */
public class ClientesDAO {
    
    private Conexao conexao;
    private Connection conn;
    
    public ClientesDAO(){
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }
    
 public void inserir(Cliente cliente){
        String sql = "INSERT INTO clientes (nome, idade, saldo) VALUES (?, ?, ?)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setInt(2, cliente.getIdade());
            stmt.setFloat(3, cliente.getSaldo());
            stmt.execute();
        } catch(SQLException ex){
            System.out.println("Erro ao inserir cliente: " + ex.getMessage()); 
        }
    }
    
 
    public Cliente getCliente(int id){
        String sql = "SELECT * FROM clientes WHERE id = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            Cliente c = new Cliente();
            if(rs.first()){
                c.setId(id);
                c.setNome(rs.getString("nome"));
                c.setIdade(rs.getInt("idade"));
                c.setSaldo(rs.getFloat("saldo"));
            }
            return c;
        } catch(SQLException ex ){
            System.out.println("Erro ao consultar cliente: " + ex.getMessage());
            return null;
        }
    }
    
    
     public void editar(Cliente cliente){
        try {
            String sql = "UPDATE clientes SET nome=?, idade=?, saldo=? WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, cliente.getNome());  
        stmt.setInt(2, cliente.getIdade());    
        stmt.setFloat(3, cliente.getSaldo());  
        stmt.setInt(4, cliente.getId());       
        stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar cliente: " + ex.getMessage());
        }
    }
     
      public void excluir(int id){
        try {
            String sql = "DELETE FROM clientes WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir cliente: " + ex.getMessage());
        }
    
     }
    
}
