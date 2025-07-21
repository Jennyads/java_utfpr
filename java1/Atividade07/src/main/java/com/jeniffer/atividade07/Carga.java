/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jeniffer.atividade07;

/**
 *
 * @author jenny
 */
public final class Carga extends Veiculo implements Calcular{
    
    private int cargaMax;
    private int tara;

    public Carga() {
        super();
        this.cargaMax = 0;
        this.tara = 0;
    }

    public int getCargaMax() {
        return cargaMax;
    }

    public final void setCargaMax(int cargaMax) {
        this.cargaMax = cargaMax;
    }

    public int getTara() {
        return tara;
    }

    public final void setTara(int tara) {
        this.tara = tara;
    }
    
    @Override
    public float calcVel(float velocMax) {
        return velocMax * 100000;
    }
    
    @Override
    public int calcular() {
        int total = 0;
        total += (int) getVelocMax(); 
        total += getQtdRodas();
        total += getMotor().getQtdPist();
        total += getMotor().getPotencia();
        total += getCargaMax();
        total += getTara();
        return total;
    }

    
    
}
