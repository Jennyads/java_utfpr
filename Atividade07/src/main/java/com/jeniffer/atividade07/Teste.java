package com.jeniffer.atividade07;

public class Teste {

    public static void main(String[] args) {
        Leitura leitura = new Leitura();
        BDVeiculos bd = new BDVeiculos();
        while (true) {
            System.out.println("\nSistema de Gestão de Veículos - Menu Inicial");
            System.out.println("1. Cadastrar Veículo de Passeio");
            System.out.println("2. Cadastrar Veículo de Carga");
            System.out.println("3. Imprimir Todos os Veículos de Passeio");
            System.out.println("4. Imprimir Todos os Veículos de Carga");
            System.out.println("5. Imprimir Veículo de Passeio pela Placa");
            System.out.println("6. Imprimir Veículo de Carga pela Placa");
            System.out.println("7. Sair do Sistema");

            String opcao = leitura.entDados("Escolha uma opção: ");
            switch (opcao) {
                case "1" -> {
                    while (true) {
                        Passeio p = new Passeio();
                        p.setPlaca(leitura.entDados("Placa: "));
                        p.setMarca(leitura.entDados("Marca: "));
                        p.setModelo(leitura.entDados("Modelo: "));
                        p.setCor(leitura.entDados("Cor: "));

                        try {
                            float vel = Float.parseFloat(leitura.entDados("Velocidade Máxima (km/h): "));
                            p.setVelocMax(vel);
                        } catch (VelocException e) {
                            e.impErroVeloc();
                            try {
                                p.setVelocMax(100);
                                System.out.println("Velocidade ajustada para 100 km/h (Passeio).");
                            } catch (VelocException ex) {
                                ex.impErroVelocPadrao();
                            }
                        }


                        p.setQtdRodas(Integer.parseInt(leitura.entDados("Quantidade de Rodas: ")));
                        p.getMotor().setQtdPist(Integer.parseInt(leitura.entDados("Qtd de Pistões: ")));
                        p.getMotor().setPotencia(Integer.parseInt(leitura.entDados("Potência: ")));
                        p.setQtdPassageiros(Integer.parseInt(leitura.entDados("Qtd de Passageiros: ")));

                        try {
                            boolean sucesso = bd.adicionarPasseio(p);
                            if (!sucesso) break;
                        } catch (VeicExistException e) {
                            e.impErroExist();
                            break;
                        }


                        String continuar = leitura.entDados("Cadastrar outro passeio? (S/N): ");
                        if (!continuar.equalsIgnoreCase("S")) break;
                    }
                }

                case "2" -> {
                    while (true) {
                        Carga c = new Carga();
                        c.setPlaca(leitura.entDados("Placa: "));
                        c.setMarca(leitura.entDados("Marca: "));
                        c.setModelo(leitura.entDados("Modelo: "));
                        c.setCor(leitura.entDados("Cor: "));

                        try {
                            float vel = Float.parseFloat(leitura.entDados("Velocidade Máxima (km/h): "));
                            c.setVelocMax(vel);
                        } catch (VelocException e) {
                            e.impErroVeloc();
                            try {
                                c.setVelocMax(90);
                                System.out.println("Velocidade ajustada para 90 km/h (Carga).");
                            } catch (VelocException ex) {
                                ex.impErroVelocPadrao();
                            }

                        }

                        c.setQtdRodas(Integer.parseInt(leitura.entDados("Quantidade de Rodas: ")));
                        c.getMotor().setQtdPist(Integer.parseInt(leitura.entDados("Qtd de Pistões: ")));
                        c.getMotor().setPotencia(Integer.parseInt(leitura.entDados("Potência: ")));
                        c.setCargaMax(Integer.parseInt(leitura.entDados("Carga Máxima: ")));
                        c.setTara(Integer.parseInt(leitura.entDados("Tara: ")));

                        try {
                            boolean sucesso = bd.adicionarCarga(c);
                            if (!sucesso) break;
                        } catch (VeicExistException e) {
                            e.impErroExist();
                            break;
                        }

                        String continuar = leitura.entDados("Cadastrar outro carga? (S/N): ");
                        if (!continuar.equalsIgnoreCase("S")) break;
                    }
                }

                case "3" -> {
                    System.out.println("\n Veículos de Passeio ");
                    boolean temPasseio = false;
                    for (Passeio p : bd.getListaPasseio()) {
                        if (p != null) {
                            imprimirPasseio(p);
                            temPasseio = true;
                        }
                    }
                    if (!temPasseio) {
                        System.out.println("Ainda não foi registrado nenhum veículo de passeio.");
                    }
                }

                case "4" -> {
                    System.out.println("\n Veículos de Carga ");
                    boolean temCarga = false;
                    for (Carga c : bd.getListaCarga()) {
                        if (c != null) {
                            imprimirCarga(c);
                            temCarga = true;
                        }
                    }
                    if (!temCarga) {
                        System.out.println("Ainda não foi registrado nenhum veículo de carga.");
                    }
                }

                case "5" -> {
                    String placa = leitura.entDados("Informe a placa do veículo de passeio: ");
                    boolean encontrado = false;
                    for (Passeio p : bd.getListaPasseio()) {
                        if (p != null && p.getPlaca().equalsIgnoreCase(placa)) {
                            imprimirPasseio(p);
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) System.out.println("Veículo de passeio não encontrado.");
                }

                case "6" -> {
                    String placa = leitura.entDados("Informe a placa do veículo de carga: ");
                    boolean encontrado = false;
                    for (Carga c : bd.getListaCarga()) {
                        if (c != null && c.getPlaca().equalsIgnoreCase(placa)) {
                            imprimirCarga(c);
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) System.out.println("Veículo de carga não encontrado.");
                }

                case "7" -> {
                    System.out.println("Encerrando o sistema...");
                    return;
                }

                default -> System.out.println("Opção inválida.");
            }
        }
    }

    public static void imprimirPasseio(Passeio p) {
        System.out.println("-------------------------------");
        System.out.println("Placa: " + p.getPlaca());
        System.out.println("Marca: " + p.getMarca());
        System.out.println("Modelo: " + p.getModelo());
        System.out.println("Cor: " + p.getCor());
        System.out.println("Velocidade Máxima: " + p.getVelocMax() + " km/h");
        System.out.println("Velocidade Convertida: " + p.calcVel(p.getVelocMax()) + " m/h");
        System.out.println("Qtd Rodas: " + p.getQtdRodas());
        System.out.println("Motor - Pistões: " + p.getMotor().getQtdPist());
        System.out.println("Motor - Potência: " + p.getMotor().getPotencia());
        System.out.println("Qtd Passageiros: " + p.getQtdPassageiros());
        System.out.println("Resultado de calcular(): Total de caracteres nos atributos String = " + p.calcular());
    }

    public static void imprimirCarga(Carga c) {
        System.out.println("-------------------------------");
        System.out.println("Placa: " + c.getPlaca());
        System.out.println("Marca: " + c.getMarca());
        System.out.println("Modelo: " + c.getModelo());
        System.out.println("Cor: " + c.getCor());
        System.out.println("Velocidade Máxima: " + c.getVelocMax() + " km/h");
        System.out.println("Velocidade Convertida: " + c.calcVel(c.getVelocMax()) + " cm/h");
        System.out.println("Qtd Rodas: " + c.getQtdRodas());
        System.out.println("Motor - Pistões: " + c.getMotor().getQtdPist());
        System.out.println("Motor - Potência: " + c.getMotor().getPotencia());
        System.out.println("Carga Máxima: " + c.getCargaMax());
        System.out.println("Tara: " + c.getTara());
        System.out.println("Resultado de calcular(): Soma dos valores numéricos = " + c.calcular());
    }
}
