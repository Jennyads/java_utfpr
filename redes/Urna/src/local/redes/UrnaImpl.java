package local.redes;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// @author Jeniffer Cristina Freitas Ramos

public class UrnaImpl extends UnicastRemoteObject implements Urna {

    private static final long serialVersionUID = 1L;

    private final Map<Integer, Candidato> candidatos;
    private final Map<Integer, Integer> votos;

    public UrnaImpl() throws RemoteException {
        super();
        this.candidatos = new LinkedHashMap<>();
        this.votos = new LinkedHashMap<>();
    }

    @Override
    public synchronized void registrarCandidato(Candidato candidato) throws RemoteException {
        if (candidato == null) {
            throw new RemoteException("Candidato inválido.");
        }
        if (candidato.getNome() == null || candidato.getNome().trim().isEmpty()) {
            throw new RemoteException("Nome do candidato não pode ser vazio.");
        }
        if (candidato.getNumero() <= 0) {
            throw new RemoteException("Número do candidato deve ser maior que zero.");
        }

        candidatos.put(candidato.getNumero(), candidato);
        if (!votos.containsKey(candidato.getNumero())) {
            votos.put(candidato.getNumero(), 0);
        }
        System.out.println("Candidato cadastrado: " + candidato);
    }

    @Override
    public synchronized void computarVotos(int numeroCandidato, int quantidade) throws RemoteException {
        if (!candidatos.containsKey(numeroCandidato)) {
            throw new RemoteException("Candidato não cadastrado: " + numeroCandidato);
        }
        if (quantidade <= 0) {
            throw new RemoteException("Quantidade de votos deve ser maior que zero.");
        }

        int atual = votos.getOrDefault(numeroCandidato, 0);
        votos.put(numeroCandidato, atual + quantidade);

        Candidato c = candidatos.get(numeroCandidato);
        System.out.println("Computados " + quantidade + " votos para " + c);
    }

    @Override
    public synchronized List<Candidato> listarCandidatos() throws RemoteException {
        return new ArrayList<>(candidatos.values());
    }

    public synchronized Map<Candidato, Integer> obterApuracao() {
        Map<Candidato, Integer> apuracao = new LinkedHashMap<>();
        for (Map.Entry<Integer, Candidato> entry : candidatos.entrySet()) {
            Candidato c = entry.getValue();
            int total = votos.getOrDefault(c.getNumero(), 0);
            apuracao.put(c, total);
        }
        return apuracao;
    }
}
