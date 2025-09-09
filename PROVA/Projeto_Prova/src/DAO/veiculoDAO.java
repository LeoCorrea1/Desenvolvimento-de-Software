/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Beans.Veiculo;
import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author laboratorio
 */
public class veiculoDAO {
    
    private Conexao conexao;
    private Connection conn;
    
    public veiculoDAO(){
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }
    
    public void inserir(Veiculo veiculo){
        String sql = "INSERT INTO veiculo (placa, marca, modelo, ano,  cor) VALUES (?, ?, ?, ?, ?)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(2, veiculo.getMarca());
            stmt.setString(3, veiculo.getModelo());
            stmt.setInt(4, veiculo.getAno());
            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(5, veiculo.getCor());
            
            stmt.execute();
        } catch(SQLException ex){
            System.out.println("Erro ao inserir veiculo: " + ex.getMessage()); 
        }
    }
    
    public Veiculo getVeiculo(int placa){
        String sql = "SELECT * FROM veiculo WHERE placa like %?";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, placa);
            ResultSet rs = stmt.executeQuery();
            Veiculo v = new Veiculo();
            if(rs.first()){
                v.setMarca(rs.getString("marca"));
                v.setModelo(rs.getString("modelo"));
                v.setAno(rs.getInt(rs.getInt("ano")));
                v.setPlaca(rs.getString("placa"));
                v.setCor(rs.getString("cor"));
            }
            return v;
            
        } catch(SQLException ex ){
            System.out.println("Erro ao consultar aluno: " + ex.getMessage());
            return null;
        }
    }
    
}
