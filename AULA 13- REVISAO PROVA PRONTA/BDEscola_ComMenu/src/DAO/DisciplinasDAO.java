package DAO;

import beans.Disciplina;
import beans.Professor;
import conexao.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DisciplinasDAO {

    private Conexao conexao;
    private Connection conn;

    public DisciplinasDAO() {
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }

 public void inserirDisciplina(Disciplina d) {
    String sql = "INSERT INTO disciplinas (nome, carga_horaria, professor_id) VALUES (?, ?, ?)";
    try {
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, d.getNome());
        stmt.setInt(2, d.getCargaHoraria());
        stmt.setInt(3, d.getProfessorId());
        stmt.execute();
    } catch (SQLException ex) {
        System.out.println("Erro ao inserir disciplina: " + ex.getMessage());
    }
}


    // Consultar disciplina pelo ID
    public Disciplina getDisciplina(int id) {
        String sql = "SELECT * FROM disciplinas WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            Disciplina d = new Disciplina();
            if (rs.first()) {
                d.setId(id);
                d.setNome(rs.getString("nome"));
                d.setCargaHoraria(rs.getInt("carga_horaria"));
                d.setProfessorId(rs.getInt("professor_id"));
            }
            return d;
        } catch (SQLException ex) {
            System.out.println("Erro ao consultar disciplina: " + ex.getMessage());
            return null;
        }
    }

    // Listar todas disciplinas
    public List<Disciplina> getTodasDisciplinas() {
        List<Disciplina> lista = new ArrayList<>();
        String sql = "SELECT * FROM disciplinas";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Disciplina d = new Disciplina();
                d.setId(rs.getInt("id"));
                d.setNome(rs.getString("nome"));
                d.setCargaHoraria(rs.getInt("carga_horaria"));
                d.setProfessorId(rs.getInt("professor_id"));
                lista.add(d);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao consultar disciplinas: " + ex.getMessage());
        }
        return lista;
    }

    // Atualizar disciplina
    public void editarDisciplina(Disciplina d) {
        try {
            String sql = "UPDATE disciplinas SET nome=?, carga_horaria=?, professor_id=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, d.getNome());
            stmt.setInt(2, d.getCargaHoraria());
            stmt.setInt(3, d.getProfessorId());
            stmt.setInt(4, d.getId());
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar disciplina: " + ex.getMessage());
        }
    }

    // Excluir disciplina
    public void excluirDisciplina(int id) {
        try {
            String sql = "DELETE FROM disciplinas WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir disciplina: " + ex.getMessage());
        }
    }

    // Buscar disciplinas por nome
    public List<Disciplina> getDisciplinasPorNome(String nome) {
        List<Disciplina> lista = new ArrayList<>();
        String sql = "SELECT * FROM disciplinas WHERE nome LIKE ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Disciplina d = new Disciplina();
                d.setId(rs.getInt("id"));
                d.setNome(rs.getString("nome"));
                d.setCargaHoraria(rs.getInt("carga_horaria"));
                d.setProfessorId(rs.getInt("professor_id"));
                lista.add(d);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao consultar disciplinas por nome: " + ex.getMessage());
        }
        return lista;
    }

    // Listar todos professores (para preencher o comboBox)
    public List<Professor> listarProfessores() {
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
            System.out.println("Erro ao listar professores: " + ex.getMessage());
        }
        return lista;
    }
}
