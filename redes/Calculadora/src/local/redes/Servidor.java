package local.redes;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Servidor {

    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            System.out.println("[LOG] Registry RMI criado na porta 1099.");

            Calculadora calc = new CalculadoraImpl();

            Naming.rebind("rmi://127.0.0.1/metodoRMI", calc);

            System.out.println("Servidor de Calculadora RMI está pronto!");
            System.out.println("Aguardando chamadas dos clientes...");

        } catch (Exception e) {
            System.err.println("Erro ao iniciar o servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
