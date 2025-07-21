package com.jeniffer.atividade01;

import java.util.Scanner;

public class Teste {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Veiculo[] veiculos = new Veiculo[5];

            for (int i = 0; i < veiculos.length; i++) {
                System.out.println("\nCadastro do Veículo " + (i + 1) + ":");
                veiculos[i] = new Veiculo();

                System.out.print("Placa: ");
                veiculos[i].setPlaca(scanner.nextLine());
                
                System.out.print("Marca: ");
                veiculos[i].setMarca(scanner.nextLine());
                
                System.out.print("Modelo: ");
                veiculos[i].setModelo(scanner.nextLine());
                
                System.out.print("Cor: ");
                veiculos[i].setCor(scanner.nextLine());
                
                System.out.print("Velocidade Máxima: ");
                veiculos[i].setVelocMax(scanner.nextFloat());
                
                System.out.print("Quantidade de Rodas: ");
                veiculos[i].setQtdRodas(scanner.nextInt());
                
                System.out.print("Quantidade de Pistões do Motor: ");
                veiculos[i].getMotor().setQtdPist(scanner.nextInt());
                
                System.out.print("Potência do Motor: ");
                veiculos[i].getMotor().setPotencia(scanner.nextInt());
                
                scanner.nextLine(); 
            }
            
            System.out.println("\nDados dos Veículos Cadastrados:");
            for (Veiculo v : veiculos) {
                System.out.println("---------------------------");
                System.out.println("Placa: " + v.getPlaca());
                System.out.println("Marca: " + v.getMarca());
                System.out.println("Modelo: " + v.getModelo());
                System.out.println("Cor: " + v.getCor());
                System.out.println("Velocidade Máxima: " + v.getVelocMax());
                System.out.println("Quantidade de Rodas: " + v.getQtdRodas());
                System.out.println("Motor - Pistões: " + v.getMotor().getQtdPist());
                System.out.println("Motor - Potência: " + v.getMotor().getPotencia());
            }
            
        }
          
    }
}
