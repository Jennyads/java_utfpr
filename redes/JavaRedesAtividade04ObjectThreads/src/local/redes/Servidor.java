package local.redes;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

// @author Jeniffer Cristina Freitas Ramos

public class Servidor {


    private static class AtendeCliente extends Thread {
        private final Socket socket;

        AtendeCliente(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (Socket s = socket) {

                try (ObjectInputStream entrada = new ObjectInputStream(s.getInputStream());
                     ObjectOutputStream saida  = new ObjectOutputStream(s.getOutputStream())) {

                    Object obj = entrada.readObject();
                    if (obj instanceof Pessoa p) {
                        System.out.println("[Servidor] Recebido de " + s.getInetAddress()
                                + " -> Nome: " + p.getNome() + "  Idade: " + p.getIdade());

                        String confirmacao = "Dados recebidos com sucesso! Nome=" + p.getNome()
                                + ", Idade=" + p.getIdade();
                        saida.writeObject(confirmacao);
                        saida.flush();
                    } else {
                        System.out.println("[Servidor] Objeto inválido recebido: " + obj);
                        saida.writeObject("Objeto inválido recebido.");
                        saida.flush();
                    }
                }
            } catch (Exception e) {
                System.err.println("[Servidor] Erro ao atender cliente: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        final int PORTA = 50000;
        System.out.println("[Servidor] Iniciando na porta " + PORTA + "...");
        try (ServerSocket server = new ServerSocket(PORTA)) {
            System.out.println("[Servidor] Aguardando conexões (bloqueante)...");

            while (true) {
                Socket conexao = server.accept();
                System.out.println("[Servidor] Cliente conectado: " + conexao.getInetAddress());
                new AtendeCliente(conexao).start();
            }
        } catch (Exception e) {
            System.err.println("[Servidor] Erro no servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
