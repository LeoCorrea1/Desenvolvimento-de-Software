package Servidor;

import Beans.Produtos;
import DAO.ProdutosDAO;
import java.io.*;
import java.net.*;

import java.util.List;

public class ServidorSocket {
    private static final int PORTA = 33;
    
   
    public void iniciaServidor() {
        ProdutosDAO dao = new ProdutosDAO(); 

        try (ServerSocket servidor = new ServerSocket(PORTA)) {
            System.out.println("Servidor iniciado na porta " + PORTA);

            while (true) {
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado: " + cliente.getInetAddress());

                new Thread(new ClienteHandler(cliente, dao)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClienteHandler implements Runnable {
    private Socket cliente;
    private ProdutosDAO dao;

    public ClienteHandler(Socket cliente, ProdutosDAO dao) {
        this.cliente = cliente;
        this.dao = dao;
    }
//    @Override
//    public void run() {
//        try (BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
//             PrintWriter saida = new PrintWriter(cliente.getOutputStream(), true)) {
//
//            String idProduto;
//            while ((idProduto = entrada.readLine()) != null) {
//                if (idProduto.equalsIgnoreCase("sair")) {
//                    saida.println("Conexão encerrada pelo servidor.");
//                    break;
//                }
//                
//               int idProdutoint = Integer.parseInt(idProduto);
//
//                List<Produtos> produtos = dao.getProdutoID(idProdutoint);
//                if (!produtos.isEmpty()) {
//                    for (Produtos prod : produtos) {
//                        saida.println("nome : " + prod.getNome());
//                         saida.println("preco : " + prod.getPreco());
//                          saida.println("saldo : " + prod.getSaldo());
//                    }
//                } else {
//                    saida.println("Nenhum Produto encontrado para o id: " + idProduto);
//                }
//            }
//            
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                cliente.close();
//                System.out.println("Cliente desconectado.");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    
    @Override
    public void run() {
        try (BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
             PrintWriter saida = new PrintWriter(cliente.getOutputStream(), true)) {

            String nomeProduto;
            while ((nomeProduto = entrada.readLine()) != null) {
                if (nomeProduto.equalsIgnoreCase("sair")) {
                    saida.println("Conexão encerrada pelo servidor.");
                    break;
                }

                List<Produtos> produtos = dao.getProdutosNome(nomeProduto);
                if (!produtos.isEmpty()) {
                    for (Produtos prod : produtos) {
                        saida.println("nome : " + prod.getNome() + ", preco : " + prod.getPreco() + ", saldo : " + prod.getSaldo() );
                    }
                } else {
                    saida.println("Nenhum Produto encontrado com o nome: " + nomeProduto);
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                cliente.close();
                System.out.println("Cliente desconectado.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


