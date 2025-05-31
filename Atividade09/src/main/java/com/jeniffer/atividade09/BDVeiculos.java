package com.jeniffer.atividade09;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class BDVeiculos {

    private List<Passeio> listaPasseio = new ArrayList<>();
    private List<Carga> listaCarga = new ArrayList<>();

    public List<Passeio> getListaPasseio() {
        return listaPasseio;
    }

    public void setListaPasseio(List<Passeio> listaPasseio) {
        this.listaPasseio = listaPasseio;
    }

    public List<Carga> getListaCarga() {
        return listaCarga;
    }

    public void setListaCarga(List<Carga> listaCarga) {
        this.listaCarga = listaCarga;
    }

    public boolean adicionarPasseio(Passeio p) throws VeicExistException {
        for (Passeio veiculo : listaPasseio) {
            if (veiculo.getPlaca().equalsIgnoreCase(p.getPlaca())) {
                throw new VeicExistException();
            }
        }

        listaPasseio.add(p);
        return true;
    }

    public boolean adicionarCarga(Carga c) throws VeicExistException {
        for (Carga veiculo : listaCarga) {
            if (veiculo.getPlaca().equalsIgnoreCase(c.getPlaca())) {
                throw new VeicExistException();
            }
        }

        listaCarga.add(c);
        return true;
    }

    public boolean removerPasseioPorPlaca(String placa) {
        Iterator<Passeio> iterator = listaPasseio.iterator();
        while (iterator.hasNext()) {
            Passeio veiculo = iterator.next();
            if (veiculo.getPlaca().equalsIgnoreCase(placa)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public boolean removerCargaPorPlaca(String placa) {
        Iterator<Carga> iterator = listaCarga.iterator();
        while (iterator.hasNext()) {
            Carga veiculo = iterator.next();
            if (veiculo.getPlaca().equalsIgnoreCase(placa)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

}
