package DAO;

import beans.Aluno;
import conexao.Conexao;
import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    
    public List<Aluno> getTodosAlunos() {
    List<Aluno> lista = new ArrayList<>();
    String sql = "SELECT * FROM alunos";
    try {
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Aluno a = new Aluno();
            a.setId(rs.getInt("id"));
            a.setNome(rs.getString("nome"));
            a.setCurso(rs.getString("curso"));
            a.setIdade(rs.getInt("idade"));
            lista.add(a);
        }
    } catch (SQLException ex) {
        System.out.println("Erro ao consultar alunos: " + ex.getMessage());
    }
    return lista;
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
    
    
    public List<Aluno> getAlunosPorNome(String nome) {
    List<Aluno> lista = new ArrayList<>();
    String sql = "SELECT * FROM alunos WHERE nome LIKE ?";
    try {
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, "%" + nome + "%");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Aluno a = new Aluno();
            a.setId(rs.getInt("id"));
            a.setNome(rs.getString("nome"));
            a.setCurso(rs.getString("curso"));
            a.setIdade(rs.getInt("idade"));
            lista.add(a);
        }
    } catch (SQLException ex) {
        System.out.println("Erro ao consultar alunos por nome: " + ex.getMessage());
    }
    return lista;
}

}
