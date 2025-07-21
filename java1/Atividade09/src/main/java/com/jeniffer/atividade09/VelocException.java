package com.jeniffer.atividade09;

public class VelocException extends Exception {

    public VelocException() {
        System.out.println("\nGerou um objeto da classe VelocException");

    }

    public void impErroVeloc() {
        System.out.println("\nA velocidade máxima está fora dos limites brasileiros");
    }

    public void impErroVelocPadrao() {
        System.out.println("\nErro ao ajustar velocidade padrão");
    }
}
