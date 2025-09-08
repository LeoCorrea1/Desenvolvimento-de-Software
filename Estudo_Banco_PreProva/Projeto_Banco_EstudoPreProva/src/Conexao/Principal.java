package conexao;

import Beans.Cliente;
import DAO.ClientesDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            
            /*Cliente cliente = new Cliente();
            cliente.setNome("Leonardo V Correa");
            cliente.setIdade(20);
            cliente.setSaldo(200);
            
           ClientesDAO clienteDAO = new ClientesDAO();
           clienteDAO.inserir(cliente);*/
           
           
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        
    }
}
