package com.jeniffer.atividade07;

public class BDVeiculos {

    private Passeio[] listaPasseio = new Passeio[5];
    private Carga[] listaCarga = new Carga[5];


    public Passeio[] getListaPasseio() {
        return listaPasseio;
    }

    public void setListaPasseio(Passeio[] listaPasseio) {
        this.listaPasseio = listaPasseio;
    }

    public Carga[] getListaCarga() {
        return listaCarga;
    }

    public void setListaCarga(Carga[] listaCarga) {
        this.listaCarga = listaCarga;
    }

    public boolean adicionarCarga(Carga c) throws VeicExistException {
        int contador = 0;
        for (Carga veiculo : listaCarga) {
            if (veiculo != null) contador++;
        }
        if (contador >= listaCarga.length) {
            System.out.println("Limite de veículos de carga atingido!");
            return false;
        }

        for (Carga veiculo : listaCarga) {
            if (veiculo != null && veiculo.getPlaca().equalsIgnoreCase(c.getPlaca())) {
                throw new VeicExistException();
            }
        }

        for (int i = 0; i < listaCarga.length; i++) {
            if (listaCarga[i] == null) {
                listaCarga[i] = c;
                return true;
            }
        }
        return false;
    }

    public boolean adicionarPasseio(Passeio p) throws VeicExistException {
        int contador = 0;
        for (Passeio veiculo : listaPasseio) {
            if (veiculo != null) contador++;
        }
        if (contador >= listaPasseio.length) {
            System.out.println("Limite de veículos de passeio atingido!");
            return false;
        }

        for (Passeio veiculo : listaPasseio) {
            if (veiculo != null && veiculo.getPlaca().equalsIgnoreCase(p.getPlaca())) {
                throw new VeicExistException();
            }
        }

        for (int i = 0; i < listaPasseio.length; i++) {
            if (listaPasseio[i] == null) {
                listaPasseio[i] = p;
                return true;
            }
        }
        return false;
    }





}
