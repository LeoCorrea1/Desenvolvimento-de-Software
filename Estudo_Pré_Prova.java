//beans → Contém as classes modelo (atributos e métodos) que representam os dados da aplicação.

//conexao → Contém as classes responsáveis pela conexão com o banco de dados.

//DAO (Data Access Object) → Contém as classes que fazem operações no banco (CRUD: inserir, consultar, atualizar, excluir).

//Form → Contém as classes de interface gráfica (telas/forms) usadas para interação com o usuário.

//1. Conexão com o banco de dados
import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
    public static Connection getConnection() {
        try {
            // Exemplo para MySQL
            String url = "jdbc:mysql://localhost:3306/nomedobanco";
            String user = "root";
            String password = "1234";

            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

//2. Inserir (INSERT)
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Inserir {
    public void inserirAluno(int id, String nome) {
        String sql = "INSERT INTO aluno (id, nome) VALUES (?, ?)";

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.setString(2, nome);
            stmt.executeUpdate();
            System.out.println("Aluno inserido!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//3. Consultar (SELECT)
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Consultar {
    public void listarAlunos() {
        String sql = "SELECT * FROM aluno";

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                System.out.println(rs.getInt("id") + " - " + rs.getString("nome"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//4. Atualizar (UPDATE)
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Atualizar {
    public void atualizarNome(int id, String novoNome) {
        String sql = "UPDATE aluno SET nome = ? WHERE id = ?";

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, novoNome);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Nome atualizado!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//5. Excluir (DELETE)
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Excluir {
    public void excluirAluno(int id) {
        String sql = "DELETE FROM aluno WHERE id = ?";

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Aluno excluído!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


//👉 Esses são os códigos básicos do JDBC que quase sempre caem em prova:

//DriverManager.getConnection() → abrir conexão.

//PreparedStatement → executar comandos SQL.

//ResultSet → ler resultados de consultas.

//executeUpdate() → usado para INSERT, UPDATE e DELETE.

executeQuery() → usado para SELECT.
