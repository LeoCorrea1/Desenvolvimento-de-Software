package DAO;

import Beans.Produtos;
import Conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutosDAO {
    
     private Conexao conexao;
    private Connection conn;

    public ProdutosDAO() {
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }

    // Inserir professor
    public void inserirProduto(Produtos produto) {
        String sql = "INSERT INTO produtos (nome, preco,saldo) VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setFloat(2, produto.getPreco());
            stmt.setInt(3, produto.getSaldo());
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir produto: " + ex.getMessage());
        }
    }

    public List<Produtos> getProdutoID(int id) {
        List<Produtos> lista = new ArrayList<>();

        
        String sql = "SELECT * FROM produtos WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            Produtos p = new Produtos();
            if (rs.first()) {
                p.setNome(rs.getString("nome"));
                p.setPreco(rs.getFloat("preco"));
                p.setSaldo(rs.getInt("saldo"));
            }
            return lista;
            
            
        } catch (SQLException ex) {
            System.out.println("Erro ao consultar produto por id: " + ex.getMessage());
            return null;
        }
    }

    // Listar todos produtos
    public List<Produtos> getTodosProdutos() {
        List<Produtos> lista = new ArrayList<>();
        String sql = "SELECT * FROM produtos";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Produtos p = new Produtos();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setPreco(rs.getFloat("preco"));
                p.setSaldo(rs.getInt("saldo"));
                lista.add(p);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao consultar produtos: " + ex.getMessage());
        }
        return lista;
    }
    
    public List<Produtos> getProdutosNome(String nome) {
        List<Produtos> lista = new ArrayList<>();
        String sql = "SELECT * FROM produtos WHERE nome LIKE ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,nome );
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Produtos p = new Produtos();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setPreco(rs.getFloat("preco"));
                p.setSaldo(rs.getInt("saldo"));
                lista.add(p);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao consultar produto por nome: " + ex.getMessage());
        }
        return lista;
    }
   
}