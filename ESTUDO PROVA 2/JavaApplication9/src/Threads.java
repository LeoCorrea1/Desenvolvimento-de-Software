import java.io.*;
import java.net.*;

public class Threads extends Thread {
    private Socket cliente;

    public Threads(Socket cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(
                new InputStreamReader(cliente.getInputStream())
            );
            PrintWriter saida = new PrintWriter(cliente.getOutputStream(), true);

            String mensagem;
            while ((mensagem = entrada.readLine()) != null) {
                System.out.println("Cliente " + cliente.getInetAddress() + ": " + mensagem);
                saida.println("Servidor recebeu: " + mensagem);

                if (mensagem.equalsIgnoreCase("sair")) {
                    break;
                }
            }

            cliente.close();
            System.out.println("Cliente desconectado: " + cliente.getInetAddress());

        } catch (IOException e) {
            System.out.println("Erro no cliente: " + e.getMessage());
        }
    }
}
