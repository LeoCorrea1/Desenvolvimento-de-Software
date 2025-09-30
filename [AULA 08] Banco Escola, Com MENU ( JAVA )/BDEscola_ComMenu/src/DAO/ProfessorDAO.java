package DAO;

import beans.Professor;
import conexao.Conexao;
import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO {
    
    private Conexao conexao;
    private Connection conn;
    
    public ProfessorDAO(){
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }
    
    public void inserir(Professor professor){
        String sql = "INSERT INTO professores (nome, disciplina, idade) VALUES (?, ?, ?)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, professor.getNome());
            stmt.setString(2, professor.getDisciplina());
            stmt.setInt(3, professor.getIdade());
            stmt.execute();
        } catch(SQLException ex){
            System.out.println("Erro ao inserir professor: " + ex.getMessage()); 
        }
    }
    
    public Professor getProfessor(int id){
       
        String sql = "SELECT * FROM professores WHERE id = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            Professor p = new Professor();
            if(rs.first()){
                p.setId(id);
                p.setNome(rs.getString("nome"));
                p.setDisciplina(rs.getString("disciplina"));
                p.setIdade(rs.getInt("idade"));
            }
            return p;
        } catch(SQLException ex ){
            System.out.println("Erro ao consultar professor: " + ex.getMessage());
            return null;
        }
    }
    
    public void editar(Professor professor){
        try {
            String sql = "UPDATE professores SET nome=?, disciplina=?, idade=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, professor.getNome());
            stmt.setString(2, professor.getDisciplina());
            stmt.setInt(3, professor.getIdade());
            stmt.setInt(4, professor.getId());
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar professor: " + ex.getMessage());
        }
    }
    
    public void excluir(int id){
        try {
            String sql = "DELETE FROM professores WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir professor: " + ex.getMessage());
        }
        
        
    }
    
    public List<Professor> getTodosProfessores() {
    List<Professor> lista = new ArrayList<>();
    String sql = "SELECT * FROM professores";
    try {
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Professor p = new Professor();
            p.setId(rs.getInt("id"));
            p.setNome(rs.getString("nome"));
            p.setDisciplina(rs.getString("disciplina"));
            p.setIdade(rs.getInt("idade"));
            lista.add(p);
        }
    } catch (SQLException ex) {
        System.out.println("Erro ao consultar professores: " + ex.getMessage());
    }
    return lista;
}
    
}


