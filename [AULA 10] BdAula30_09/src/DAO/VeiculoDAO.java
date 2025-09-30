/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import beans.Pessoa;
import beans.Veiculo;
import conexao.Conexao;
import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author laboratorio
 */
public class VeiculoDAO {
    
    private Conexao conexao;
    private Connection conn;
    
    public VeiculoDAO(){
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
       
    }
    
//    public List<Pessoa> getPessoasNome(String nome, String sexo){
//        
//        String sql = "SELECT * FROM PESSOA WHERE nome LIKE ? and sexo LIKE ?";
//        try{
//            
//            PreparedStatement stmt = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
//            
//            stmt.setString(1,"%"+nome+"%");
//            stmt.setString(2,"%"+sexo+"%");
//            
//            ResultSet rs = stmt.executeQuery();
//            List<Pessoa> listaPessoas = new ArrayList<>();
//            
//            while (rs.next()){
//                
//                listaPessoas.add(new Pessoa(
//                                            rs.getInt("id"),
//                                            rs.getString("nome"),
//                                            rs.getString("sexo"),
//                                            rs.getString("idioma")
//                                            )
//                                );
//                
//            }
//            return listaPessoas;
//        
//        } catch(SQLException ex){
//            
//            System.out.println("Erro "+ex.getMessage());
//            return null;
//            
//        }
//        
//    }
    
   public List<Veiculo> getVeiculos() {
     String sql = "SELECT * FROM veiculo";
        try {
          PreparedStatement stmt = conn.prepareStatement(sql, 
                    ResultSet.TYPE_SCROLL_INSENSITIVE, 
                   ResultSet.CONCUR_UPDATABLE);

         ResultSet rs = stmt.executeQuery();
         List<Veiculo> listaVeiculos = new ArrayList<>();

         while (rs.next()) {
             Veiculo v = new Veiculo();
               v.setId(rs.getInt("id"));
               v.setModelo(rs.getString("modelo"));
               v.setPlaca(rs.getString("placa"));

               PessoaDAO pDAO = new PessoaDAO();
               Pessoa p = pDAO.getPessoa(rs.getInt("id_pessoa"));
            
               v.setPessoa(p);
               listaVeiculos.add(v);
            }
         return listaVeiculos;
     } catch (SQLException ex) {
            System.out.println("Erro ao consultar todas as pessoas: " + ex.getMessage());
            return null;
     }
}

    
    public Pessoa getPessoa(int id){
        
        String sql = "SELECT * FROM pessoa WHERE id = ?";
        
        try{
            
            PreparedStatement stmt = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            Pessoa p = new Pessoa();
            rs.first();
            p.setId(id);
            p.setNome(rs.getString("nome"));
            p.setSexo(rs.getString("sexo"));
            p.setIdioma(rs.getString("idioma"));
            
            return p;
            
        }
        catch(SQLException ex){
            
            System.out.println("Falha ao consultar veiculo: " + ex.getMessage());
            return null;
            
        }
        
    }
    
 public void editar(Veiculo veiculo) {
    try {
        String sql = "UPDATE VEICULO set modelo = ?, placa = ?, id_pessoa = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, veiculo.getModelo());
        stmt.setString(2, veiculo.getPlaca());
        stmt.setInt(3, veiculo.getPessoa().getId()); // CORRIGIDO AQUI
        stmt.setInt(4, veiculo.getId());
        stmt.execute();
    } catch(SQLException ex) {
        System.out.println("Erro ao atualizar veiculo: " + ex.getMessage());
    }
}

    
    public void excluir(int id){
        
        try{
            
            String sql = "delete from VEICULO where id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            
        }
        catch(SQLException ex){
            
            System.out.println("Erro ao atualizar veiculo: " + ex.getMessage());
            
        }
        
    }
    
      public void inserir(Veiculo veiculo) {
    String sql = "INSERT INTO VEICULO (modelo,placa,id_pessoa) VALUES (?,?,?)";
    try {
        PreparedStatement stmt = this.conn.prepareStatement(sql);
        stmt.setString(1, veiculo.getModelo());
        stmt.setString(2, veiculo.getPlaca());
        stmt.setInt(3, veiculo.getPessoa().getId()); // CORRIGIDO AQUI

        stmt.execute();
    } catch(SQLException ex) {
        System.out.println("Erro ao inserir veiculo: " + ex.getMessage());
    }
}
      
      
      
public Veiculo getVeiculo(int id) {
    String sql = "SELECT * FROM veiculo WHERE id = ?";

    try {
        PreparedStatement stmt = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);

        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        Veiculo v = new Veiculo();

        rs.first(); // Move para o primeiro registro

        v.setId(rs.getInt("id"));
        v.setModelo(rs.getString("modelo"));
        v.setPlaca(rs.getString("placa"));

        // Buscando a pessoa associada ao veículo
        PessoaDAO pDAO = new PessoaDAO();
        Pessoa p = pDAO.getPessoa(rs.getInt("id_pessoa"));
        v.setPessoa(p); // ← Aqui estava v.setPessoaid(p), que está incorreto

        return v;

    } catch (SQLException ex) {
        System.out.println("Erro ao consultar pessoa: " + ex.getMessage());
        return null;
    }
}


}
