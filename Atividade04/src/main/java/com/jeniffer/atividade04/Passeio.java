/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jeniffer.atividade04;

/**
 *
 * @author jenny
 */
public final class Passeio extends Veiculo{
    private int qtdPassageiros;

    public Passeio() {
        super();
        this.qtdPassageiros = 0;
    }

    public int getQtdPassageiros() {
        return qtdPassageiros;
    }

    public final void setQtdPassageiros(int qtdPassageiros) {
        this.qtdPassageiros = qtdPassageiros;
    }
    
    @Override
    public float calcVel(float velocMax) {
        return velocMax * 1000;
    }
    
}
