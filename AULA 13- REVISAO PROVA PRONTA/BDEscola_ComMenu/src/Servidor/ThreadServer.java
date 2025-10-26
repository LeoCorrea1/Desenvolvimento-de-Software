package Servidor;

import DAO.ProfessorDAO;
import beans.Professor;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ThreadServer extends Thread {
    private Socket clienteSocket;

    public ThreadServer(Socket clienteSocket) {
        this.clienteSocket = clienteSocket;
    }

    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(clienteSocket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(clienteSocket.getInputStream())) {

            int id = in.readInt();
            System.out.println("ID recebido: " + id);

            // Simule a obtenção de uma Pessoa a partir do ID
            ProfessorDAO pdao = new ProfessorDAO();
            Professor p = pdao.getProfessor(id);

            out.writeObject(p);

        } catch (IOException ex) {
            System.out.println("Erro ao lidar com o cliente");
        }
    }
}