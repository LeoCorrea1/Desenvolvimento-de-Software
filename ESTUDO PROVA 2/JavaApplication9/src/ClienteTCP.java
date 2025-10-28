import java.io.*;
import java.net.*;

public class ClienteTCP {
    public static void main(String[] args) {
        String host = "localhost";
        int porta = 5000;

        try (Socket socket = new Socket(host, porta);
             BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter saida = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Conectado ao servidor em " + host + ":" + porta);

            String mensagem;
            while (true) {
                System.out.print("Mensagem: ");
                mensagem = teclado.readLine();
                saida.println(mensagem); // Envia mensagem ao servidor

                String resposta = entrada.readLine(); // Recebe resposta do servidor
                System.out.println("Servidor: " + resposta);

                if (mensagem.equalsIgnoreCase("sair")) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
