
package Conexao;

import java.sql.Connection;
import java.sql.DriverManager;


public class Conexao {
    public Connection getConexao(){
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prova2?useTimezone=true&serverTimezone=UTC","root","laboratorio");
            System.out.println("Conexao realizada com sucesso! ");
            return conn;
        }
        catch(Exception e){
            System.out.println("Erro ao conectar ao BD "+e.getMessage());
            return null;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}