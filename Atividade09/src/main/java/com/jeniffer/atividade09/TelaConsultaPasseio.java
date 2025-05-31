package com.jeniffer.atividade09;

import javax.swing.*;
import java.awt.*;

public class TelaConsultaPasseio extends JFrame {
    private BDVeiculos bd;

    private JTextField placaField, passageirosField, marcaField, modeloField, corField, rodasField, velMaxField, pistoesField, potenciaField;

    public TelaConsultaPasseio(BDVeiculos bd) {
        this.bd = bd;

        setTitle("Consultar/Excluir Veículo de Passeio");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(450, 450);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        Font fonte = new Font("Arial", Font.BOLD, 12);


        JLabel placaLabel = new JLabel("Informe a Placa:");
        placaLabel.setFont(fonte);
        placaLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(placaLabel, gbc);

        placaField = criarCampo(panel, gbc, fonte);

        passageirosField = criarLinha(panel, gbc, "Qtd. Passageiros:", fonte);
        marcaField = criarLinha(panel, gbc, "Marca:", fonte);
        modeloField = criarLinha(panel, gbc, "Modelo:", fonte);
        corField = criarLinha(panel, gbc, "Cor:", fonte);
        rodasField = criarLinha(panel, gbc, "Qtd. Rodas:", fonte);
        velMaxField = criarLinha(panel, gbc, "Velocidade Máx.:", fonte);
        pistoesField = criarLinha(panel, gbc, "Qtd. Pistões:", fonte);
        potenciaField = criarLinha(panel, gbc, "Potência:", fonte);

        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton consultarButton = new JButton("Consultar");
        JButton excluirButton = new JButton("Excluir");
        JButton sairButton = new JButton("Sair");

        JButton[] botoes = {consultarButton, excluirButton, sairButton};
        for (JButton b : botoes) {
            b.setFont(fonte);
            if (b == consultarButton || b == excluirButton) {
                b.setBackground(Color.YELLOW);
            } else {
                b.setBackground(Color.LIGHT_GRAY);
            }
            panelBotoes.add(b);
        }

        consultarButton.addActionListener(e -> consultarPasseio());
        excluirButton.addActionListener(e -> excluirPasseio());
        sairButton.addActionListener(e -> {
            new TelaPasseio(bd);
            dispose();
        });

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(panelBotoes, gbc);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JTextField criarCampo(JPanel panel, GridBagConstraints gbc, Font fonte) {
        JTextField field = new JTextField(10);
        field.setFont(fonte);
        field.setBackground(new Color(230, 230, 230));
        field.setPreferredSize(new Dimension(150, 25));

        gbc.gridx = 1;
        panel.add(field, gbc);
        return field;
    }

    private JTextField criarLinha(JPanel panel, GridBagConstraints gbc, String labelText, Font fonte) {
        JLabel label = new JLabel(labelText);
        label.setFont(fonte);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(label, gbc);

        JTextField field = new JTextField(10);
        field.setFont(fonte);
        field.setEditable(false);
        field.setBackground(new Color(230, 230, 230));
        field.setPreferredSize(new Dimension(150, 25));

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(field, gbc);

        return field;
    }

    private void consultarPasseio() {
        String placa = placaField.getText();
        for (Passeio p : bd.getListaPasseio()) {
            if (p.getPlaca().equalsIgnoreCase(placa)) {
                passageirosField.setText(String.valueOf(p.getQtdPassageiros()));
                marcaField.setText(p.getMarca());
                modeloField.setText(p.getModelo());
                corField.setText(p.getCor());
                rodasField.setText(String.valueOf(p.getQtdRodas()));
                velMaxField.setText(p.calcVel(p.getVelocMax()) + " M/h");
                pistoesField.setText(String.valueOf(p.getMotor().getQtdPist()));
                potenciaField.setText(String.valueOf(p.getMotor().getPotencia()));
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Veículo de passeio não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
    }

    private void excluirPasseio() {
        String placa = placaField.getText();
        boolean removido = bd.removerPasseioPorPlaca(placa);
        if (removido) {
            JOptionPane.showMessageDialog(this, "Veículo de passeio removido com sucesso!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Veículo de passeio não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
