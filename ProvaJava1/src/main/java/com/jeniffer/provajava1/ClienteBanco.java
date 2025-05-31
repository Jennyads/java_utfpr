/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jeniffer.provajava1;

/**
 *
 * @author jenny
 */
public abstract class ClienteBanco implements Verifica {
    private int numeroConta = 0;
    private String nome = " ";
    private Endereco ender = new Endereco();

    public int getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(int numeroConta) throws NumException{
        if (numeroConta > 0) {
            this.numeroConta = numeroConta;
        } else {
            throw new NumException();
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Endereco getEnder() {
        return ender;
    }

    public void setEnder(Endereco ender) {
        this.ender = ender;
    }
    
    public abstract void verifDoc();
    
    
    
}
