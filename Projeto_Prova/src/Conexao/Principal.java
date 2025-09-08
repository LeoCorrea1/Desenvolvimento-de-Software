package conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Principal {
    public static void main(String[] args) {
        try {
            // conecta
            Conexao c = new Conexao();
            Connection conn = c.getConexao();

            // consulta simples
            String sql = "SELECT * FROM clientes";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            // mostra resultados
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " - " + rs.getString("nome"));
            }

            conn.close();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        
    }
}
