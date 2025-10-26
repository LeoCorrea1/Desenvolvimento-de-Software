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
class ConnectionFactory {
    private static final String URL = "jdbc:mysql://localhost:3306/escola";
    private static final String USER = "root";
    private static final String PASS = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            System.out.println("Driver JDBC não encontrado.");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
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
 * 4️⃣ Interfaces Swing
 * =========================================================== */

// Tela de cadastro de professores
class TelaProfessor extends JFrame {
    private JTextField txtNome, txtEmail;
    private JTable tabela;
    private ProfessorDAO dao = new ProfessorDAO();

    public TelaProfessor() {
        setTitle("Cadastro de Professores");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel painel = new JPanel(new GridLayout(3, 2));
        painel.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        painel.add(txtNome);
        painel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        painel.add(txtEmail);

        JButton btnSalvar = new JButton("Salvar");
        painel.add(btnSalvar);
        add(painel, BorderLayout.NORTH);

        tabela = new JTable(new DefaultTableModel(new Object[]{"ID","Nome","Email"}, 0));
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        btnSalvar.addActionListener(e -> {
            try {
                Professor p = new Professor(0, txtNome.getText(), txtEmail.getText());
                dao.inserir(p);
                JOptionPane.showMessageDialog(this, "Professor salvo!");
                atualizarTabela();
            } catch (Exception ex) { ex.printStackTrace(); }
        });
    }

    private void atualizarTabela() throws SQLException {
        DefaultTableModel m = (DefaultTableModel) tabela.getModel();
        m.setRowCount(0);
        for (Professor p : dao.listar())
            m.addRow(new Object[]{p.getId(), p.getNome(), p.getEmail()});
    }
}

/* ===========================================================
 * 5️⃣ Menu Principal
 * =========================================================== */
class MenuPrincipal extends JFrame {
    public MenuPrincipal() {
        setTitle("Menu Principal");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JMenuBar barra = new JMenuBar();
        JMenu menuCadastro = new JMenu("Cadastros");
        JMenuItem itemProf = new JMenuItem("Professores");
        JMenuItem itemDisc = new JMenuItem("Disciplinas");

        itemProf.addActionListener(e -> new TelaProfessor().setVisible(true));
        menuCadastro.add(itemProf);
        menuCadastro.add(itemDisc);
        barra.add(menuCadastro);
        setJMenuBar(barra);
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

/* ===========================================================
 * 7️⃣ Início do Sistema
 * =========================================================== */
class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }
}
