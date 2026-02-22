package local.redes;

import java.rmi.Naming;

public class Cliente {

    public static void main(String[] args) {
        try {
            Calculadora stub =
                    (Calculadora) Naming.lookup("rmi://127.0.0.1/metodoRMI");

            long a = 20;
            long b = 5;

            System.out.println("adição(" + a + "," + b + ") = " + stub.add(a, b));
            System.out.println("subtração(" + a + "," + b + ") = " + stub.sub(a, b));
            System.out.println("multiplicação(" + a + "," + b + ") = " + stub.mul(a, b));
            System.out.println("divisão(" + a + "," + b + ") = " + stub.div(a, b));


        } catch (Exception e) {
            System.err.println("Erro no cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
