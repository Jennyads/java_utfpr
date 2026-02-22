package local.redes;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

// @author Jeniffer Cristina Freitas Ramos

public interface Urna extends Remote {

    void registrarCandidato(Candidato candidato) throws RemoteException;

    void computarVotos(int numeroCandidato, int quantidade) throws RemoteException;

    List<Candidato> listarCandidatos() throws RemoteException;
}
