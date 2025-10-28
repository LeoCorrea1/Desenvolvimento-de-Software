package Servidor;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteSocket {
    public static void main(String[] args) {
        
        String host = "localhost";
        int porta = 33;

        try (Socket socket = new Socket(host, porta);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter saida = new PrintWriter(socket.getOutputStream(), true);
             Scanner teclado = new Scanner(System.in)) {

            System.out.println("Conectado ao servidor. Digite 'sair' para encerrar.");

            String codigo;
            while (true) {
                System.out.print("Digite o nome do produto para consultar : ");
                codigo = teclado.nextLine();
                saida.println(codigo);
                
                

                if (codigo.equalsIgnoreCase("sair")) break;

                String resposta = entrada.readLine();
                System.out.println(resposta);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

