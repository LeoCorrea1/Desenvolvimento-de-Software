/*
 * ColaCompleta.java
 *
 * Arquivo único (single-file) que reúne exemplos e comentários cobrindo:
 *  - Model / Beans: Pessoa, Veiculo
 *  - Util JDBC: DBUtil (DriverManager, Connection)
 *  - DAOs: PessoaDAO, VeiculoDAO (CRUD, PreparedStatement, ResultSet)
 *  - Swing: Formulário de cadastro e janela de relatório (JFrame, JTable, JComboBox)
 *  - Sockets: ServerSocket (Servidor), Socket (Cliente), Object streams
 *  - Threads: Cliente tratado em thread separada no servidor
 *  - Serialização: Message, envio de objetos Pessoa/Veiculo
 *
 * Objetivo: ser uma "cola" explicativa com trechos e comentários completos.
 *
 * Obs: O código mistura várias peças em um único arquivo para estudo; ele NÃO é
 * um projeto pronto para produção. Ajustes (pacotes, imports, dependências JDBC)
 * são necessários para executar.
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import java.util.List;

/**
 * Classe principal que contém todas as classes internas para a cola.
 */
public class ColaCompleta {

    // -----------------------------
    // 1) MODELS / BEANS
    // -----------------------------
    /**
     * Bean que representa a tabela Pessoa do banco.
     * - Contém id, nome, idade, sexo.
     * - Implementa Serializable para poder ser enviado por sockets.
     * - toString/equals/hashCode para uso em componentes Swing (JComboBox/JTable).
     */
    public static class Pessoa implements Serializable {
        private static final long serialVersionUID = 1L;
        private Integer id;
        private String nome;
        private Integer idade;
        private String sexo;

        public Pessoa() {}

        public Pessoa(Integer id, String nome, Integer idade, String sexo) {
            this.id = id; this.nome = nome; this.idade = idade; this.sexo = sexo;
        }

        // Getters/Setters
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
        public Integer getIdade() { return idade; }
        public void setIdade(Integer idade) { this.idade = idade; }
        public String getSexo() { return sexo; }
        public void setSexo(String sexo) { this.sexo = sexo; }

        // toString: útil para exibir no JComboBox (mostra id e nome)
        @Override
        public String toString() {
            return (id == null ? "0" : id) + " - " + nome;
        }

        // equals/hashCode baseados em id (bom quando usamos objetos em listas/combos)
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pessoa)) return false;
            Pessoa pessoa = (Pessoa) o;
            return Objects.equals(getId(), pessoa.getId());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId());
        }
    }

    /**
     * Bean Veiculo: representa um veículo que pertence a uma Pessoa.
     * - Contém id, modelo, placa, e referência ao dono (Pessoa).
     */
    public static class Veiculo implements Serializable {
        private static final long serialVersionUID = 1L;
        private Integer id;
        private String modelo;
        private String placa;
        private Pessoa dono; // relacionamento: veiculo.pessoa_id -> pessoa.id

        public Veiculo() {}
        public Veiculo(Integer id, String modelo, String placa, Pessoa dono) {
            this.id = id; this.modelo = modelo; this.placa = placa; this.dono = dono;
        }

        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        public String getModelo() { return modelo; }
        public void setModelo(String modelo) { this.modelo = modelo; }
        public String getPlaca() { return placa; }
        public void setPlaca(String placa) { this.placa = placa; }
        public Pessoa getDono() { return dono; }
        public void setDono(Pessoa dono) { this.dono = dono; }

        @Override
        public String toString() {
            return modelo + " (" + placa + ")";
        }
    }

    // -----------------------------
    // 2) UTIL JDBC (DBUtil)
    // -----------------------------
    /**
     * DBUtil centraliza a criação de conexões JDBC.
     * - Exemplifica Class.forName (driver), DriverManager.getConnection.
     * - Ajuste URL/USER/PASS para seu ambiente MySQL.
     *
     * Observações:
     * - Em drivers modernos, Class.forName pode não ser obrigatório, mas ainda é
     *   mostrado para fins didáticos (períodos legados / compatibilidade).
     * - Use try-with-resources ao consumir Connection/PreparedStatement/ResultSet.
     */
    public static class DBUtil {
        // ATENÇÃO: modifique para o seu banco
        private static final String URL = "jdbc:mysql://localhost:3306/seubanco?useTimezone=true&serverTimezone=UTC";
        private static final String USER = "seu_usuario";
        private static final String PASS = "sua_senha";

        static {
            // Carrega driver JDBC (pode lançar ClassNotFoundException)
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                System.err.println("Driver JDBC não encontrado: " + e.getMessage());
            }
        }

        // Retorna nova conexão. Lembre-se de fechar após uso.
        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASS);
        }
    }

    // -----------------------------
    // 3) DAOs: PessoaDAO e VeiculoDAO
    // -----------------------------
    /**
     * PessoaDAO: exemplo de CRUD usando PreparedStatement e try-with-resources.
     * - inserir, listarTodos, buscarPorNome, atualizar, deletar
     * - mostra uso de Statement.RETURN_GENERATED_KEYS para recuperar id autoincrement
     */
    public static class PessoaDAO {

        // Inserir Pessoa no banco e setar id gerado
        public void inserir(Pessoa p) throws SQLException {
            String sql = "INSERT INTO pessoa (nome, idade, sexo) VALUES (?, ?, ?)";
            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, p.getNome());
                ps.setInt(2, p.getIdade());
                ps.setString(3, p.getSexo());
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) p.setId(keys.getInt(1)); // recupera id autoincrement
                }
            }
        }

        // Listar todas as pessoas
        public List<Pessoa> listarTodos() throws SQLException {
            List<Pessoa> lista = new ArrayList<>();
            String sql = "SELECT id, nome, idade, sexo FROM pessoa";
            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Pessoa p = new Pessoa(rs.getInt("id"), rs.getString("nome"), rs.getInt("idade"), rs.getString("sexo"));
                    lista.add(p);
                }
            }
            return lista;
        }

        // Buscar por nome (filtro LIKE)
        public List<Pessoa> buscarPorNome(String nomeLike) throws SQLException {
            List<Pessoa> lista = new ArrayList<>();
            String sql = "SELECT id, nome, idade, sexo FROM pessoa WHERE nome LIKE ?";
            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, "%" + nomeLike + "%");
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Pessoa p = new Pessoa(rs.getInt("id"), rs.getString("nome"), rs.getInt("idade"), rs.getString("sexo"));
                        lista.add(p);
                    }
                }
            }
            return lista;
        }

        // Atualizar pessoa
        public void atualizar(Pessoa p) throws SQLException {
            String sql = "UPDATE pessoa SET nome = ?, idade = ?, sexo = ? WHERE id = ?";
            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, p.getNome());
                ps.setInt(2, p.getIdade());
                ps.setString(3, p.getSexo());
                ps.setInt(4, p.getId());
                ps.executeUpdate();
            }
        }

        // Deletar por id
        public void deletar(Integer id) throws SQLException {
            String sql = "DELETE FROM pessoa WHERE id = ?";
            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        }
    }

    /**
     * VeiculoDAO: demonstra relacionamento veiculo -> pessoa através de pessoa_id (FK)
     * - inserir (usa pessoa.getId()), listarComDono (JOIN)
     */
    public static class VeiculoDAO {

        public void inserir(Veiculo v) throws SQLException {
            String sql = "INSERT INTO veiculo (modelo, placa, pessoa_id) VALUES (?, ?, ?)";
            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, v.getModelo());
                ps.setString(2, v.getPlaca());
                ps.setInt(3, v.getDono().getId()); // FK para pessoa
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) v.setId(keys.getInt(1));
                }
            }
        }

        // Listar veículos e construir objeto Pessoa como dono (JOIN)
        public List<Veiculo> listarComDono() throws SQLException {
            String sql = "SELECT v.id as vid, v.modelo, v.placa, p.id as pid, p.nome, p.idade, p.sexo " +
                         "FROM veiculo v LEFT JOIN pessoa p ON v.pessoa_id = p.id";
            List<Veiculo> lista = new ArrayList<>();
            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Pessoa dono = new Pessoa(rs.getInt("pid"), rs.getString("nome"), rs.getInt("idade"), rs.getString("sexo"));
                    Veiculo v = new Veiculo(rs.getInt("vid"), rs.getString("modelo"), rs.getString("placa"), dono);
                    lista.add(v);
                }
            }
            return lista;
        }
    }

    // -----------------------------
    // 4) SERIALIZAÇÃO: Message (protocolo simples)
    // -----------------------------
    /**
     * Message: wrapper serializável para comunicação entre cliente/servidor.
     * - Type indica o tipo da mensagem (TEXT, PERSON, VEHICLE, ACK, ERROR).
     * - payload carrega objetos (Pessoa, Veiculo) ou Strings.
     *
     * Protocolo simples demonstrado nos slides: cliente envia Message com payload Pessoa
     * e servidor insere no BD e responde com ACK + Pessoa (com id).
     */
    public static class Message implements Serializable {
        private static final long serialVersionUID = 1L;

        public enum Type { TEXT, PERSON, VEHICLE, ACK, ERROR }

        private Type type;
        private String text;
        private Object payload;

        public Message() {}
        public Message(Type type, String text, Object payload) { this.type = type; this.text = text; this.payload = payload; }

        public Type getType() { return type; }
        public void setType(Type type) { this.type = type; }
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
        public Object getPayload() { return payload; }
        public void setPayload(Object payload) { this.payload = payload; }
    }

    // -----------------------------
    // 5) SOCKETS (SERVER & CLIENT handler)
    // -----------------------------
    /**
     * ServerMain: servidor que escuta uma porta e cria uma nova thread para cada cliente.
     * - Usa ServerSocket.accept() (bloqueante) e instancia ClientHandler para tratar.
     * - ClientHandler usa ObjectInputStream/ObjectOutputStream para trocar Message.
     *
     * Observação importante: ObjectOutputStream deve ser criado antes do ObjectInputStream
     * no lado que escrever primeiro (boilerplate de Java IO serializado).
     */
    public static class ServerMain {

        public static final int PORT = 12345; // porta de exemplo (ajustar se necessário)

        // Método que inicia o servidor: fica em loop aceitando conexões
        public static void startServer() {
            // Em produção, trate exceções e permita desligamento controlado
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                System.out.println("Servidor iniciado. Aguardando conexões na porta " + PORT);
                while (true) {
                    Socket clientSocket = serverSocket.accept(); // bloqueia até conexão
                    System.out.println("Cliente conectado: " + clientSocket.getRemoteSocketAddress());
                    // Cria e inicia thread para cada cliente
                    Thread t = new Thread(new ClientHandler(clientSocket));
                    t.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ClientHandler: trata comunicação com um cliente em uma thread separada.
     * - Lê Message via ObjectInputStream, processa (ex.: inserir Pessoa via DAO),
     *   e responde com outra Message via ObjectOutputStream.
     * - Demonstra tratamento de EOFException (cliente fechou conexão).
     */
    public static class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            // NUNCA faça operações longas na thread principal do servidor sem threads
            try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
                // Loop: processa mensagens até o cliente desconectar
                Object obj;
                while ((obj = ois.readObject()) != null) {
                    if (!(obj instanceof Message)) {
                        oos.writeObject(new Message(Message.Type.ERROR, "Formato inválido", null));
                        continue;
                    }
                    Message in = (Message) obj;
                    System.out.println("Recebido do cliente: " + in.getType() + " / " + in.getText());

                    switch (in.getType()) {
                        case PERSON:
                            if (in.getPayload() instanceof Pessoa) {
                                Pessoa p = (Pessoa) in.getPayload();
                                try {
                                    PessoaDAO dao = new PessoaDAO();
                                    dao.inserir(p); // insere no BD e seta id
                                    // Responde com ACK e retorna a Pessoa inserida (com id)
                                    oos.writeObject(new Message(Message.Type.ACK, "Pessoa inserida", p));
                                } catch (Exception ex) {
                                    oos.writeObject(new Message(Message.Type.ERROR, "Erro DB: " + ex.getMessage(), null));
                                }
                            } else {
                                oos.writeObject(new Message(Message.Type.ERROR, "Payload inválido para PERSON", null));
                            }
                            break;
                        case VEHICLE:
                            if (in.getPayload() instanceof Veiculo) {
                                Veiculo v = (Veiculo) in.getPayload();
                                try {
                                    VeiculoDAO dao = new VeiculoDAO();
                                    dao.inserir(v);
                                    oos.writeObject(new Message(Message.Type.ACK, "Veículo inserido", v));
                                } catch (Exception ex) {
                                    oos.writeObject(new Message(Message.Type.ERROR, "Erro DB: " + ex.getMessage(), null));
                                }
                            } else {
                                oos.writeObject(new Message(Message.Type.ERROR, "Payload inválido para VEHICLE", null));
                            }
                            break;
                        case TEXT:
                        default:
                            // Echo simples
                            oos.writeObject(new Message(Message.Type.TEXT, "Eco: " + in.getText(), null));
                            break;
                    }
                }
            } catch (EOFException eof) {
                // O cliente fechou o stream - comportamento normal
                System.out.println("Cliente desconectou: " + socket.getRemoteSocketAddress());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try { socket.close(); } catch (IOException ignored) {}
            }
        }
    }

    // -----------------------------
    // 6) CLIENTE SWING que usa sockets
    // -----------------------------
    /**
     * ClientAppSwing: exemplo de aplicação Swing que age como cliente.
     * - Form de cadastro de Pessoa (nome, idade, sexo).
     * - Ao clicar "Salvar", cria um Message(Type.PERSON, payload=Pessoa) e envia ao servidor.
     * - Aguarda resposta (ACK ou ERROR) e atualiza tabela local.
     *
     * Observações de usabilidade:
     * - Em aplicações reais, NUNCA execute operações de rede na Event Dispatch Thread (EDT)
     *   do Swing — use SwingWorker ou uma Thread separada. Aqui o envio é feito em Thread.
     */
    public static class ClientAppSwing {
        private JFrame frame;
        private JTextField txtNome, txtIdade;
        private JComboBox<String> cbSexo;
        private JButton btnSalvar;
        private DefaultTableModel tableModel;
        private JTable tabela;

        // Host/porta do servidor (ajuste conforme ambiente)
        private String serverHost = "localhost";
        private int serverPort = ServerMain.PORT;

        public ClientAppSwing() {
            initComponents();
        }

        private void initComponents() {
            frame = new JFrame("Cliente - Cadastro de Pessoa (Exemplo)");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(700, 400);
            frame.setLayout(new BorderLayout());

            // Painel superior: formulário
            JPanel form = new JPanel(new FlowLayout());
            form.add(new JLabel("Nome:"));
            txtNome = new JTextField(15);
            form.add(txtNome);

            form.add(new JLabel("Idade:"));
            txtIdade = new JTextField(3);
            form.add(txtIdade);

            form.add(new JLabel("Sexo:"));
            cbSexo = new JComboBox<>(new String[]{"M", "F", "Outro"});
            form.add(cbSexo);

            btnSalvar = new JButton("Salvar (enviar ao servidor)");
            form.add(btnSalvar);

            frame.add(form, BorderLayout.NORTH);

            // Tabela central: exibe pessoas "inseridas" (mock/local)
            tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Idade", "Sexo"}, 0);
            tabela = new JTable(tableModel);
            frame.add(new JScrollPane(tabela), BorderLayout.CENTER);

            // Evento: enviar ao servidor (em thread para não travar UI)
            btnSalvar.addActionListener(e -> {
                String nome = txtNome.getText();
                String idadeStr = txtIdade.getText();
                if (nome.isEmpty() || idadeStr.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Preencha nome e idade.");
                    return;
                }
                int idade;
                try { idade = Integer.parseInt(idadeStr); } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Idade inválida.");
                    return;
                }
                Pessoa p = new Pessoa(null, nome, idade, (String) cbSexo.getSelectedItem());
                // Envia em thread separada
                new Thread(() -> sendPerson(p)).start();
            });

            frame.setVisible(true);
        }

        // Envia Pessoa ao servidor e processa resposta
        private void sendPerson(Pessoa p) {
            // Conectar -> criar ObjectOutputStream -> enviar Message -> ler resposta
            try (Socket socket = new Socket(serverHost, serverPort);
                 ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

                Message msg = new Message(Message.Type.PERSON, "Cadastrar pessoa", p);
                oos.writeObject(msg);
                // Lê resposta
                Object respObj = ois.readObject();
                if (respObj instanceof Message) {
                    Message resp = (Message) respObj;
                    if (resp.getType() == Message.Type.ACK && resp.getPayload() instanceof Pessoa) {
                        Pessoa inserida = (Pessoa) resp.getPayload();
                        // Atualiza tabela na EDT
                        SwingUtilities.invokeLater(() -> tableModel.addRow(new Object[]{inserida.getId(), inserida.getNome(), inserida.getIdade(), inserida.getSexo()}));
                        JOptionPane.showMessageDialog(frame, resp.getText());
                    } else if (resp.getType() == Message.Type.ERROR) {
                        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, "Erro: " + resp.getText(), "Erro", JOptionPane.ERROR_MESSAGE));
                    } else {
                        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, resp.getText()));
                    }
                }
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, "Falha na conexão: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE));
            }
        }
    }

    // -----------------------------
    // 7) EXEMPLOS USANDO OS SLIDES (ATIVIDADES)
    // -----------------------------
    /**
     * Métodos utilitários demonstrativos similares às atividades dos slides:
     * - montarMenuPrincipal(): mostra como integrar telas (cadastro/relatório) via JMenu
     * - exemploCriarTabelaJDBC(): exemplo de SQL para criar tabelas Pessoa e Veiculo
     *
     * Esses são trechos práticos que aparecem nos slides: criação de tabela,
     * associações, formulários e atividades solicitadas.
     */
    public static class SlidesExamples {

        // SQL de exemplo para criar tabelas (copiada/adaptada dos slides)
        public static final String SQL_CREATE_PESSOA =
            "CREATE TABLE pessoa (\n" +
            "  id INT AUTO_INCREMENT PRIMARY KEY,\n" +
            "  nome VARCHAR(100) NOT NULL,\n" +
            "  idade INT,\n" +
            "  sexo VARCHAR(10)\n" +
            ");";

        public static final String SQL_CREATE_VEICULO =
            "CREATE TABLE veiculo (\n" +
            "  id INT AUTO_INCREMENT PRIMARY KEY,\n" +
            "  modelo VARCHAR(100),\n" +
            "  placa VARCHAR(20),\n" +
            "  pessoa_id INT,\n" +
            "  FOREIGN KEY (pessoa_id) REFERENCES pessoa(id)\n" +
            ");";

        // Método que imprime as consultas mais importantes (como uma "cola" rápida)
        public static void imprimirComandosImportantes() {
            System.out.println("=== COMANDOS SQL IMPORTANTES ===");
            System.out.println("Criar tabela Pessoa:\n" + SQL_CREATE_PESSOA);
            System.out.println("\nCriar tabela Veiculo:\n" + SQL_CREATE_VEICULO);
            System.out.println("\nExemplos de CRUD (JDBC):");
            System.out.println("INSERT: INSERT INTO pessoa (nome, idade, sexo) VALUES (?, ?, ?);");
            System.out.println("SELECT: SELECT id, nome, idade, sexo FROM pessoa WHERE nome LIKE ?;");
            System.out.println("UPDATE: UPDATE pessoa SET nome=?, idade=?, sexo=? WHERE id=?;");
            System.out.println("DELETE: DELETE FROM pessoa WHERE id=?;");
        }

        // Exemplo de montar um menu principal (integra telas Swing)
        public static JMenuBar montarMenuPrincipal(JFrame frame) {
            JMenuBar menuBar = new JMenuBar();
            JMenu menuCadastro = new JMenu("Cadastro");
            JMenuItem miPessoa = new JMenuItem("Pessoa");
            JMenuItem miVeiculo = new JMenuItem("Veículo");
            JMenu menuRelatorio = new JMenu("Relatórios");
            JMenuItem miRelPessoas = new JMenuItem("Relatório de Pessoas");

            // ActionListeners podem abrir janelas (ex.: new CadastroPessoaFrame().setVisible(true));
            miPessoa.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Abrir tela de cadastro de Pessoa (exemplo)"));
            miVeiculo.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Abrir tela de cadastro de Veículo (exemplo)"));
            miRelPessoas.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Abrir relatório de pessoas (exemplo)"));

            menuCadastro.add(miPessoa);
            menuCadastro.add(miVeiculo);
            menuRelatorio.add(miRelPessoas);
            menuBar.add(menuCadastro);
            menuBar.add(menuRelatorio);
            return menuBar;
        }
    }

    // -----------------------------
    // 8) MAIN de demonstração (não obrigatório)
    // -----------------------------
    /**
     * Exemplo de uso:
     * - Imprime comandos importantes (como cola)
     * - Opcional: inicia servidor (em thread) e abre cliente Swing (em EDT)
     *
     * ATENÇÃO: rodar o servidor e cliente na mesma JVM aqui é apenas para demonstração;
     * em prática execute o server e client em processos separados.
     */
    public static void main(String[] args) {
        // Imprime "cola rápida" (comandos SQL e dicas)
        SlidesExamples.imprimirComandosImportantes();

        // (OPCIONAL) Iniciar servidor em thread separada - apenas se desejar testar localmente
        // ATENÇÃO: se você quiser realmente executar, ajuste DBUtil com credenciais válidas e certifique-se do driver JDBC no classpath.
        Thread serverThread = new Thread(() -> {
            System.out.println("Iniciando servidor (thread interna) ...");
            ServerMain.startServer(); // este método é bloqueante (fica em loop)
        });
        // Para evitar iniciar automaticamente ao estudar, deixei comentado por padrão.
        // Descomente se quer iniciar o servidor nesta JVM:
        // serverThread.start();

        // (OPCIONAL) Abrir o cliente Swing (apenas a UI, sem servidor real conectando)
        // Inicia a aplicação Swing no Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            // Aqui abrimos a UI de exemplo (cliente). A UI tenta conectar no servidor se você tiver startado.
            new ClientAppSwing();
        });

        // Observação final impressa:
        System.out.println("\nFIM da cola. Use o arquivo como referência para provas e consultas rápidas.");
        System.out.println("Lembrete: para testar a comunicação cliente-servidor, inicie ServerMain.startServer() em outro processo,");
        System.out.println("ou descomente serverThread.start() acima e ajuste DBUtil para ter o JDBC driver e dados corretos.");
    }

} // fim da classe ColaCompleta
