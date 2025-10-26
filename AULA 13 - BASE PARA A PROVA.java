/*
 * RevisaoPA2_Base.java
 * 
 * Sistema base para Revisão PA2 — Laboratório de Desenvolvimento de Software
 * 
 * Abrange:
 *  - JDBC (MySQL)
 *  - DAO
 *  - Swing (cadastro, listagem, filtros, JComboBox)
 *  - Relacionamento 1-N (Professor ↔ Disciplina)
 *  - Menu Principal
 *  - Cliente/Servidor com Sockets e Threads
 *
 * Estrutura pronta para completar durante a prova.
 */

import java.sql.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

/* ===========================================================
 * 1️⃣ Conexão com o Banco de Dados
 * =========================================================== */
public class Conexao {
    public Connection getConexao(){
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/revisao?useTimezone=true&serverTimezone=UTC","root","leo231105");
            System.out.println("Conexao realizada com sucesso! ");
            return conn;
        }
        catch(Exception e){
            System.out.println("Erro ao conectar ao BD "+e.getMessage());
            return null;
        }
    }

/* ===========================================================
 * 2️⃣ Modelo — Professor e Disciplina
 * =========================================================== */
class Professor implements Serializable {
    private int id;
    private String nome;
    private String email;

    public Professor() {}
    public Professor(int id, String nome, String email) {
        this.id = id; this.nome = nome; this.email = email;
    }

    // Getters e Setters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setEmail(String email) { this.email = email; }

    // Para exibir corretamente no JComboBox
    public String toString() { return nome; }
}

class Disciplina implements Serializable {
    private int id;
    private String nome;
    private int cargaHoraria;
    private Professor professor; // relacionamento 1-N

    public Disciplina() {}
    public Disciplina(int id, String nome, int cargaHoraria, Professor professor) {
        this.id = id; this.nome = nome; this.cargaHoraria = cargaHoraria; this.professor = professor;
    }

    // Getters e Setters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public int getCargaHoraria() { return cargaHoraria; }
    public Professor getProfessor() { return professor; }
    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCargaHoraria(int cargaHoraria) { this.cargaHoraria = cargaHoraria; }
    public void setProfessor(Professor professor) { this.professor = professor; }
}

/* ===========================================================
 * 3️⃣ DAO (Data Access Object)
 * =========================================================== */
class ProfessorDAO {
    public void inserir(Professor p) throws SQLException {
        Connection c = ConnectionFactory.getConnection();
        PreparedStatement ps = c.prepareStatement("INSERT INTO professores(nome,email) VALUES(?,?)");
        ps.setString(1, p.getNome());
        ps.setString(2, p.getEmail());
        ps.executeUpdate();
        c.close();
    }

    public List<Professor> listar() throws SQLException {
        List<Professor> lista = new ArrayList<>();
        Connection c = ConnectionFactory.getConnection();
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM professores");
        while (rs.next()) {
            lista.add(new Professor(rs.getInt("id"), rs.getString("nome"), rs.getString("email")));
        }
        c.close();
        return lista;
    }

    public Professor buscarPorDisciplina(int disciplinaId) throws SQLException {
        Connection c = ConnectionFactory.getConnection();
        String sql = """
            SELECT p.* FROM professores p
            JOIN disciplinas d ON p.id = d.professor_id
            WHERE d.id = ?
        """;
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, disciplinaId);
        ResultSet rs = ps.executeQuery();
        Professor p = null;
        if (rs.next())
            p = new Professor(rs.getInt("id"), rs.getString("nome"), rs.getString("email"));
        c.close();
        return p;
    }
}

/* ===========================================================
 * 6️⃣ Servidor e Cliente Socket
 * =========================================================== */

// Servidor: escuta requisições e responde nome/email do professor
class ServidorSocket {
    public static void main(String[] args) throws Exception {
        ServerSocket servidor = new ServerSocket(12345);
        System.out.println("Servidor ativo na porta 12345...");
        ProfessorDAO dao = new ProfessorDAO();

        while (true) {
            Socket cliente = servidor.accept();
            new Thread(() -> {
                try (ObjectInputStream in = new ObjectInputStream(cliente.getInputStream());
                     ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream())) {
                    int idDisciplina = (int) in.readObject();
                    Professor prof = dao.buscarPorDisciplina(idDisciplina);
                    out.writeObject(prof);
                } catch (Exception e) { e.printStackTrace(); }
            }).start();
        }
    }
}

// Cliente: envia código da disciplina e mostra professor
class ClienteSocket {
    public static void main(String[] args) throws Exception {
        int cod = Integer.parseInt(JOptionPane.showInputDialog("Código da disciplina:"));
        Socket s = new Socket("localhost", 12345);
        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(s.getInputStream());
        out.writeObject(cod);
        Professor prof = (Professor) in.readObject();
        JOptionPane.showMessageDialog(null, "Professor: " + prof.getNome() + "\nEmail: " + prof.getEmail());
        s.close();
    }
}
