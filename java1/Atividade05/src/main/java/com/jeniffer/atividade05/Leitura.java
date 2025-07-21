/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jeniffer.atividade05;

import java.util.Scanner;
/**
 *
 * @author jenny
 */
public class Leitura {
    private final Scanner scanner = new Scanner(System.in);

    public String entDados(String texto) {
        System.out.print(texto);
        return scanner.nextLine();
    }
    
}
