package local.redes;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

// @author Jeniffer Cristina Freitas Ramos

public class Cliente {
    public static void main(String[] args) {
        final String SERVIDOR = "127.0.0.1";
        final int PORTA = 50000;

        try (Socket conexao = new Socket(SERVIDOR, PORTA)) {
            System.out.println("Conectado ao servidor em " + SERVIDOR + ":" + PORTA);


            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Digite um CPF para verificação: ");
            String cpf = teclado.readLine();


            DataOutputStream saida = new DataOutputStream(conexao.getOutputStream());
            saida.writeUTF(cpf);


            BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            String resposta = entrada.readLine();
            System.out.println("Resposta do servidor: " + resposta);

            saida.close();
            conexao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
