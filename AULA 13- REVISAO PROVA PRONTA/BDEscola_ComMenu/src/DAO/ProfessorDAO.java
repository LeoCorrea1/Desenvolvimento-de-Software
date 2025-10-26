package DAO;

import beans.Professor;
import conexao.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO {

    private Conexao conexao;
    private Connection conn;

    public ProfessorDAO() {
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }
    
 

    // Inserir professor
    public void inserirProfessor(Professor professor) {
        String sql = "INSERT INTO professores (nome, email) VALUES (?, ?)";
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, professor.getNome());
            stmt.setString(2, professor.getEmail());
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir professor: " + ex.getMessage());
        }
    }

    // Consultar professor pelo ID
    public Professor getProfessor(int id) {
        String sql = "SELECT * FROM professores WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            Professor p = new Professor();
            if (rs.first()) {
                p.setId(id);
                p.setNome(rs.getString("nome"));
                p.setEmail(rs.getString("email"));
            }
            return p;
        } catch (SQLException ex) {
            System.out.println("Erro ao consultar professor: " + ex.getMessage());
            return null;
        }
    }

    // Atualizar professor
    public void editarProfessor(Professor professor) {
        try {
            String sql = "UPDATE professores SET nome=?, email=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, professor.getNome());
            stmt.setString(2, professor.getEmail());
            stmt.setInt(3, professor.getId());
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar professor: " + ex.getMessage());
        }
    }

    // Excluir professor
    public void excluirProfessor(int id) {
        try {
            String sql = "DELETE FROM professores WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir professor: " + ex.getMessage());
        }
    }

    // Listar todos professores
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
                p.setEmail(rs.getString("email"));
                lista.add(p);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao consultar professores: " + ex.getMessage());
        }
        return lista;
    }

    // Buscar professores por nome
    public List<Professor> getProfessoresPorNome(String nome) {
        List<Professor> lista = new ArrayList<>();
        String sql = "SELECT * FROM professores WHERE nome LIKE ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Professor p = new Professor();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setEmail(rs.getString("email"));
                lista.add(p);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao consultar professores por nome: " + ex.getMessage());
        }
        return lista;
    }


}
