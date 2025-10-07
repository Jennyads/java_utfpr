package local.redes;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

// @author Jeniffer Cristina Freitas Ramos

public class Servidor {

    public static void main(String[] args) {
        final int PORTA = 50000;

        try (ServerSocket server = new ServerSocket(PORTA)) {
            System.out.println("Servidor iniciado na porta " + PORTA + " (aguardando conexão)...");
            try (Socket socket = server.accept()) {
                System.out.println("Cliente conectado: " + socket.getInetAddress());


                try (ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
                     ObjectOutputStream saida  = new ObjectOutputStream(socket.getOutputStream())) {

                    Object recebido = entrada.readObject();
                    if (recebido instanceof Pessoa p) {
                        System.out.println("Nome: " + p.getNome() + "  Idade: " + p.getIdade());
                        String confirmacao = "Dados recebidos com sucesso! Nome=" + p.getNome() + ", Idade=" + p.getIdade();
                        saida.writeObject(confirmacao);
                        saida.flush();
                    } else {
                        System.out.println("Objeto inválido recebido: " + recebido);
                        saida.writeObject("Objeto inválido recebido.");
                        saida.flush();
                    }
                }
            }

            System.out.println("Conexão encerrada. Servidor finalizado.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
