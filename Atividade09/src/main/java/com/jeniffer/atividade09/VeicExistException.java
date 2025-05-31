package com.jeniffer.atividade09;

public class VeicExistException extends Exception {

    public VeicExistException() {
        System.out.println("\nGerou um objeto VeicExistException");
    }

    public void impErroExist() {
        System.out.println("\nJá existe um veículo com esta placa");
    }

}
