/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jeniffer.provajava1;

/**
 *
 * @author jenny
 */
public class TstConta {

    public static void main(String[] args) {
        PessoaJuridica pj = new PessoaJuridica();
        
        try {
            pj.setNumeroConta(1234);
        } catch (NumException e) {
            e.impMsg();
        }
        System.out.println("Número da Conta: " + pj.getNumeroConta());
        pj.validar();
        
        pj.setCnpj(404320);
        System.out.println("CNPJ: " + pj.getCnpj());
        
        pj.getEnder().setRua("Rua Elizabetha Gaubatz Rodhe");
        System.out.println("Rua: " + pj.getEnder().getRua());
        
        PessoaFisica responsavel = pj.getResponsavel();
        
        responsavel.setCpf(19);
        System.out.println("CPF do Responsável: " + responsavel.getCpf());
        responsavel.verifDoc();
        
        responsavel.setNome("Jeniffer Crstina Freitas Ramos");
        System.out.println("Nome do Responsável: " + responsavel.getNome());
        pj.verifDoc();  
        
    }
}
