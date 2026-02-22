package local.redes;


// @author Jeniffer Cristina Freitas Ramos

import java.io.Serializable;

public class Candidato implements Serializable {

    private static final long serialVersionUID = 1L;

    private int numero;
    private String nome;

    public Candidato(int numero, String nome) {
        this.numero = numero;
        this.nome = nome;
    }

    public int getNumero() {
        return numero;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return numero + " - " + nome;
    }
}