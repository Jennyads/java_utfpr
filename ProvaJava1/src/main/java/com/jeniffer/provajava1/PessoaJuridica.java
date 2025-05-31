/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jeniffer.provajava1;

/**
 *
 * @author jenny
 */
public class PessoaJuridica extends ClienteBanco{
    
    private int cnpj = 0;
    private PessoaFisica responsavel = new PessoaFisica();

    public int getCnpj() {
        return cnpj;
    }

    public void setCnpj(int cnpj) {
        this.cnpj = cnpj;
    }

    public PessoaFisica getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(PessoaFisica responsavel) {
        this.responsavel = responsavel;
    }
    
    @Override
    public void verifDoc() {
        String nomeResponsavel = responsavel.getNome();
        if (nomeResponsavel.length() > 30) {
            System.out.println("Nome inválido para Responsável");
        } else {
            System.out.println("Nome válido para Responsável");
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
