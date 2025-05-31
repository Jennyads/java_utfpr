/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jeniffer.provajava1;

/**
 *
 * @author jenny
 */
public final class PessoaFisica extends ClienteBanco{
    private int cpf = 0;

    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }
    
    @Override
    public void verifDoc() {
        if (cpf >= 10 && cpf <= 20) {
            System.out.println("CPF válido");
        } else {
            System.out.println("CPF inválido");
        }
    }
    
    @Override
    public void validar() {
        if (getNumeroConta() % 2 == 0) {
            System.out.println("Número da conta é PAR");
        } else {
            System.out.println("Número da conta é ÍMPAR");
        }
    }
    
}
