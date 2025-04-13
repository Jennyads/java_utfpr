package com.jeniffer.atividade04;

import java.util.Scanner;

public class Teste {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Passeio[] passeios = new Passeio[5];
            Carga[] cargas = new Carga[5];
            
            
            for (int i = 0; i < passeios.length; i++) {
                System.out.println("\nCadastro de Veículos de Passeio: " + (i + 1) + ":");
                passeios[i] = new Passeio();

                System.out.print("Placa: ");
                passeios[i].setPlaca(scanner.nextLine());
                
                System.out.print("Marca: ");
                passeios[i].setMarca(scanner.nextLine());
                
                System.out.print("Modelo: ");
                passeios[i].setModelo(scanner.nextLine());
                
                System.out.print("Cor: ");
                passeios[i].setCor(scanner.nextLine());
                
                System.out.print("Velocidade Máxima (km/h): ");
                float velocidade = scanner.nextFloat();
                passeios[i].setVelocMax(velocidade);
                
                System.out.print("Quantidade de Rodas: ");
                passeios[i].setQtdRodas(scanner.nextInt());
                
                System.out.print("Quantidade de Pistões do Motor: ");
                passeios[i].getMotor().setQtdPist(scanner.nextInt());
                
                System.out.print("Potência do Motor: ");
                passeios[i].getMotor().setPotencia(scanner.nextInt());
                
                scanner.nextLine(); 
            }
            
            
            for (int i = 0; i < cargas.length; i++) {
                System.out.println("\nCadastro de Veículos de Carga: " + (i + 1) + ":");
                cargas[i] = new Carga();
                
                
                System.out.print("Placa: ");
                cargas[i].setPlaca(scanner.nextLine());
                
                System.out.print("Marca: ");
                cargas[i].setMarca(scanner.nextLine());
                
                System.out.print("Modelo: ");
                cargas[i].setModelo(scanner.nextLine());
                
                System.out.print("Cor: ");
                cargas[i].setCor(scanner.nextLine());
                
                System.out.print("Velocidade Máxima em (km/h): ");
                float velocidade = scanner.nextFloat();
                cargas[i].setVelocMax(velocidade);
                
                System.out.print("Quantidade de Rodas: ");
                cargas[i].setQtdRodas(scanner.nextInt());
                
                System.out.print("Quantidade de Pistões do Motor: ");
                cargas[i].getMotor().setQtdPist(scanner.nextInt());
                
                System.out.print("Potência do Motor: ");
                cargas[i].getMotor().setPotencia(scanner.nextInt());
                
                System.out.print("Carga máxima: ");
                cargas[i].setCargaMax(scanner.nextInt());
                
                System.out.print("Tara: ");
                cargas[i].setTara(scanner.nextInt());
                
                scanner.nextLine(); 
                
               
            }
            
            System.out.println("\n=== Dados dos Veículos de Passeio ===");
            for (Passeio p : passeios) {
                System.out.println("---------------------------");
                System.out.println("Placa: " + p.getPlaca());
                System.out.println("Marca: " + p.getMarca());
                System.out.println("Modelo: " + p.getModelo());
                System.out.println("Cor: " + p.getCor());
                System.out.println("Velocidade Máxima: " + p.getVelocMax() + " km/h");
                System.out.println("Velocidade Convertida: " + p.calcVel(p.getVelocMax()) + " m/h");
                System.out.println("Quantidade de Rodas: " + p.getQtdRodas());
                System.out.println("Motor - Pistões: " + p.getMotor().getQtdPist());
                System.out.println("Motor - Potência: " + p.getMotor().getPotencia());
                System.out.println("Passageiros: " + p.getQtdPassageiros());
            }

      
            System.out.println("\n=== Dados dos Veículos de Carga ===");
            for (Carga c : cargas) {
                System.out.println("---------------------------");
                System.out.println("Placa: " + c.getPlaca());
                System.out.println("Marca: " + c.getMarca());
                System.out.println("Modelo: " + c.getModelo());
                System.out.println("Cor: " + c.getCor());
                System.out.println("Velocidade Máxima: " + c.getVelocMax() + " km/h");
                System.out.println("Velocidade Convertida: " + c.calcVel(c.getVelocMax()) + " cm/h");
                System.out.println("Quantidade de Rodas: " + c.getQtdRodas());
                System.out.println("Motor - Pistões: " + c.getMotor().getQtdPist());
                System.out.println("Motor - Potência: " + c.getMotor().getPotencia());
                System.out.println("Carga Máxima: " + c.getCargaMax());
                System.out.println("Tara: " + c.getTara());
            }

            scanner.close();
        }       
          
    }
}
