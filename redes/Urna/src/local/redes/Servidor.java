package local.redes;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

// @author Jeniffer Cristina Freitas Ramos

public class Servidor {

    public static void main(String[] args) {
        try {
            System.out.println("================================");
            System.out.println(" Eleição - Servidor Central RMI ");
            System.out.println("================================");

            LocateRegistry.createRegistry(1099);

            UrnaImpl service = new UrnaImpl();

            Naming.rebind("rmi://127.0.0.1/UrnaService", service);

            System.out.println("Servidor RMI iniciado na porta 1099.");
            System.out.println("Aguardando votos das urnas...");

            Timer timer = new Timer(true);
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    imprimirApuracao(service);
                }
            }, 0, 5000);

            while (true) {
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {

                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao iniciar o servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void imprimirApuracao(UrnaImpl service) {
        System.out.println();
        System.out.println("Eleição");
        System.out.println("--------");
        System.out.println("Votos apurados:");

        Map<Candidato, Integer> apuracao = service.obterApuracao();

        if (apuracao.isEmpty()) {
            System.out.println("Nenhum candidato cadastrado ou nenhum voto computado ainda.");
        } else {
            for (Map.Entry<Candidato, Integer> entry : apuracao.entrySet()) {
                Candidato c = entry.getKey();
                int total = entry.getValue();
                System.out.println(
                        String.format("%2d %-12s --- %4d votos",
                                c.getNumero(), c.getNome(), total));
            }
        }
        System.out.println();
    }
}
