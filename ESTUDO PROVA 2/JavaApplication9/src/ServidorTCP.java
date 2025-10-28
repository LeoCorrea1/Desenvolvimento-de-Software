import java.io.*;
import java.net.*;

public class ServidorTCP {
    public static void main(String[] args) {
        int porta = 5000;

        try (ServerSocket servidor = new ServerSocket(porta)) {
            System.out.println("Servidor iniciado na porta " + porta);

            while (true) {
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado: " + cliente.getInetAddress());

                // Aqui cria uma nova Thread para cada cliente
                Threads clienteThread = new Threads(cliente);
                clienteThread.start(); // inicia a thread
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
