package DAO;

import Beans.Categoria;
import Conexao.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {
    private Connection conn;

    public CategoriaDAO() {
        this.conn = new Conexao().getConexao();
    }

    public void inserir(Categoria categoria) {
        String sql = "INSERT INTO categorias (nome) VALUES (?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, categoria.getNome());
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir categoria: " + ex.getMessage());
        }
    }

    public List<Categoria> getCategorias() {
        String sql = "SELECT * FROM categorias";
        List<Categoria> lista = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Categoria c = new Categoria();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                lista.add(c);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao listar categorias: " + ex.getMessage());
        }

        return lista;
    }

    public Categoria getCategoria(int id) {
        String sql = "SELECT * FROM categorias WHERE id = ?";
        try {
            // Alterado para usar o tipo TYPE_SCROLL_INSENSITIVE
            PreparedStatement stmt = conn.prepareStatement(sql, 
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            // Usar rs.first() para buscar o primeiro registro (isso só funciona com ResultSet de scroll)
            if (rs.first()) {
                Categoria c = new Categoria();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                return c;
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao buscar categoria: " + ex.getMessage());
        }
        return null;
    }

    public void editar(Categoria categoria) {
        String sql = "UPDATE categorias SET nome = ? WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, categoria.getNome());
            stmt.setInt(2, categoria.getId());
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar categoria: " + ex.getMessage());
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM categorias WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir categoria: " + ex.getMessage());
        }
    }
}
