package local.redes;

// @author Jeniffer Cristina Freitas Ramos

import java.io.DataInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    private static boolean entradaValida(String cpf) {
        String limpo = cpf.replaceAll("[^0-9]", "");
        return limpo.length() == 11 && !limpo.matches("(\\d)\\1{10}");
    }

    private static boolean digitosConferem(String cpf) {
        String c = cpf.replaceAll("[^0-9]", "");

        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (c.charAt(i) - '0') * (10 - i);
        }
        int resto = soma % 11;
        int d1 = (resto < 2) ? 0 : 11 - resto;

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += (c.charAt(i) - '0') * (11 - i);
        }
        resto = soma % 11;
        int d2 = (resto < 2) ? 0 : 11 - resto;

        return d1 == (c.charAt(9) - '0') && d2 == (c.charAt(10) - '0');
    }

    public static void main(String[] args) {
        final int PORTA = 50000;

        try (ServerSocket servidor = new ServerSocket(PORTA)) {
            System.out.println("Servidor iniciado na porta " + PORTA + "...");

            Socket cliente = servidor.accept();
            System.out.println("Conexão estabelecida com " + cliente.getInetAddress());

            DataInputStream entrada = new DataInputStream(cliente.getInputStream());
            PrintWriter saida = new PrintWriter(cliente.getOutputStream(), true);


            String cpf = entrada.readUTF();
            System.out.println("CPF recebido do cliente: " + cpf);

            boolean valido = entradaValida(cpf) && digitosConferem(cpf);

            if (valido) {
                saida.println("Este CPF é válido.");
                System.out.println("Validação concluída: CPF válido.");
            } else {
                saida.println("Este CPF é inválido.");
                System.out.println("Validação concluída: CPF inválido.");
            }

            cliente.close();
            System.out.println("Conexão encerrada.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
