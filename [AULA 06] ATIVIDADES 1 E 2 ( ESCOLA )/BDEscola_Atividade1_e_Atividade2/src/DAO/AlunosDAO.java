package DAO;

import beans.Aluno;
import conexao.Conexao;
import java.sql.Connection;
import java.sql.*;

public class AlunosDAO {
    
    private Conexao conexao;
    private Connection conn;
    
    public AlunosDAO(){
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }
    
    public void inserir(Aluno aluno){
        String sql = "INSERT INTO alunos (nome, curso, idade) VALUES (?, ?, ?)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getCurso());
            stmt.setInt(3, aluno.getIdade());
            stmt.execute();
        } catch(SQLException ex){
            System.out.println("Erro ao inserir aluno: " + ex.getMessage()); 
        }
    }
    
    public Aluno getAluno(int id){
        String sql = "SELECT * FROM alunos WHERE id = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            Aluno a = new Aluno();
            if(rs.first()){
                a.setId(id);
                a.setNome(rs.getString("nome"));
                a.setCurso(rs.getString("curso"));
                a.setIdade(rs.getInt("idade"));
            }
            return a;
        } catch(SQLException ex ){
            System.out.println("Erro ao consultar aluno: " + ex.getMessage());
            return null;
        }
    }
    
    public void editar(Aluno aluno){
        try {
            String sql = "UPDATE alunos SET nome=?, curso=?, idade=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getCurso());
            stmt.setInt(3, aluno.getIdade());
            stmt.setInt(4, aluno.getId());
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar aluno: " + ex.getMessage());
        }
    }
    
    public void excluir(int id){
        try {
            String sql = "DELETE FROM alunos WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir aluno: " + ex.getMessage());
        }
    }
}
