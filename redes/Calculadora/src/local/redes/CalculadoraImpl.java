package local.redes;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalculadoraImpl extends UnicastRemoteObject implements Calculadora {

    public CalculadoraImpl() throws RemoteException {
        super();
    }

    @Override
    public long add(long a, long b) throws RemoteException {
        long resultado = a + b;
        System.out.println("[LOG] Cliente chamou add(" + a + ", " + b + ") = " + resultado);
        return resultado;
    }

    @Override
    public long sub(long a, long b) throws RemoteException {
        long resultado = a - b;
        System.out.println("[LOG] Cliente chamou sub(" + a + ", " + b + ") = " + resultado);
        return resultado;
    }

    @Override
    public long mul(long a, long b) throws RemoteException {
        long resultado = a * b;
        System.out.println("[LOG] Cliente chamou mul(" + a + ", " + b + ") = " + resultado);
        return resultado;
    }

    @Override
    public long div(long a, long b) throws RemoteException {
        if (b == 0) {
            System.out.println("[LOG] Cliente tentou dividir por zero: div(" + a + ", " + b + ")");
            throw new RemoteException("Divisão por zero não permitida.");
        }
        long resultado = a / b;
        System.out.println("[LOG] Cliente chamou div(" + a + ", " + b + ") = " + resultado);
        return resultado;
    }
}
