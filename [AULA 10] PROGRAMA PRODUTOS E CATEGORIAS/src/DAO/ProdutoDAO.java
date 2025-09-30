package DAO;

import Beans.Categoria;
import Beans.Produto;
import Conexao.Conexao;
import DAO.CategoriaDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    private Connection conn;

    public ProdutoDAO() {
        this.conn = new Conexao().getConexao();
    }

    public void inserir(Produto produto) {
        String sql = "INSERT INTO produtos (nome, preco, quantidade, categoria_id) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getQuantidade());
            stmt.setInt(4, produto.getCategoria().getId());
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir produto: " + ex.getMessage());
        }
    }

    public List<Produto> getProdutos() {
        String sql = "SELECT p.*, c.nome AS categoria_nome FROM produtos p INNER JOIN categorias c ON p.categoria_id = c.id";
        List<Produto> lista = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setPreco(rs.getDouble("preco"));
                p.setQuantidade(rs.getInt("quantidade"));

                Categoria c = new Categoria();
                c.setId(rs.getInt("categoria_id"));
                c.setNome(rs.getString("categoria_nome"));

                p.setCategoria(c);
                lista.add(p);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao listar produtos: " + ex.getMessage());
        }

        return lista;
    }

   public Produto getProduto(int id) {
    String sql = "SELECT * FROM produtos WHERE id = ?";
    try {
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {  // <-- aqui estava rs.first()
            Produto p = new Produto();
            p.setId(rs.getInt("id"));
            p.setNome(rs.getString("nome"));
            p.setPreco(rs.getDouble("preco"));
            p.setQuantidade(rs.getInt("quantidade"));

            CategoriaDAO cDAO = new CategoriaDAO();
            Categoria c = cDAO.getCategoria(rs.getInt("categoria_id"));
            p.setCategoria(c);

            return p;
        }

    } catch (SQLException ex) {
        System.out.println("Erro ao buscar produto: " + ex.getMessage());
    }

    return null;
}

    public void editar(Produto produto) {
        String sql = "UPDATE produtos SET nome = ?, preco = ?, quantidade = ?, categoria_id = ? WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getQuantidade());
            stmt.setInt(4, produto.getCategoria().getId());
            stmt.setInt(5, produto.getId());
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar produto: " + ex.getMessage());
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM produtos WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir produto: " + ex.getMessage());
        }
    }

}
