package com.jeniffer.atividade09;

import javax.swing.*;
import java.awt.*;

public class TelaConsultaCarga extends JFrame {
    private BDVeiculos bd;

    private JTextField placaField, marcaField, modeloField, corField, rodasField, velMaxField, pistoesField, potenciaField, cargaMaxField, taraField;

    public TelaConsultaCarga(BDVeiculos bd) {
        this.bd = bd;

        setTitle("Consultar/Excluir Veículo de Carga");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

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

        criarLinha(panel, gbc, "Marca:", marcaField = new JTextField(), fonte);
        criarLinha(panel, gbc, "Modelo:", modeloField = new JTextField(), fonte);
        criarLinha(panel, gbc, "Cor:", corField = new JTextField(), fonte);
        criarLinha(panel, gbc, "Qtd. Rodas:", rodasField = new JTextField(), fonte);
        criarLinha(panel, gbc, "Velocidade Máx.:", velMaxField = new JTextField(), fonte);
        criarLinha(panel, gbc, "Qtd. Pistões:", pistoesField = new JTextField(), fonte);
        criarLinha(panel, gbc, "Potência:", potenciaField = new JTextField(), fonte);
        criarLinha(panel, gbc, "Carga Máxima:", cargaMaxField = new JTextField(), fonte);
        criarLinha(panel, gbc, "Tara:", taraField = new JTextField(), fonte);

        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton consultarButton = new JButton("Consultar");
        JButton excluirButton = new JButton("Excluir");
        JButton sairButton = new JButton("Sair");

        consultarButton.setBackground(Color.YELLOW);
        excluirButton.setBackground(Color.YELLOW);
        sairButton.setBackground(Color.LIGHT_GRAY);

        consultarButton.setFont(fonte);
        excluirButton.setFont(fonte);
        sairButton.setFont(fonte);

        botoesPanel.add(consultarButton);
        botoesPanel.add(excluirButton);
        botoesPanel.add(sairButton);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(botoesPanel, gbc);

        add(panel);

        consultarButton.addActionListener(e -> consultarCarga());
        excluirButton.addActionListener(e -> excluirCarga());
        sairButton.addActionListener(e -> {
            new TelaCarga(bd);
            dispose();
        });

        setVisible(true);
    }

    private void criarLinha(JPanel panel, GridBagConstraints gbc, String labelText, JTextField field, Font fonte) {
        JLabel label = new JLabel(labelText);
        label.setFont(fonte);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(label, gbc);

        field.setFont(fonte);
        field.setEditable(false);
        field.setBackground(new Color(230, 230, 230));
        field.setPreferredSize(new Dimension(150, 25));

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(field, gbc);
    }


    private JTextField criarCampo(JPanel panel, GridBagConstraints gbc, Font fonte) {
        JTextField field = new JTextField(10);
        field.setFont(fonte);
        field.setBackground(new Color(230, 230, 230));
        field.setPreferredSize(new Dimension(150, 25));

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(field, gbc);

        return field;
    }


    private void consultarCarga() {
        String placa = placaField.getText();
        for (Carga c : bd.getListaCarga()) {
            if (c.getPlaca().equalsIgnoreCase(placa)) {
                marcaField.setText(c.getMarca());
                modeloField.setText(c.getModelo());
                corField.setText(c.getCor());
                rodasField.setText(String.valueOf(c.getQtdRodas()));
                velMaxField.setText(c.calcVel(c.getVelocMax()) + " Cm/h");
                pistoesField.setText(String.valueOf(c.getMotor().getQtdPist()));
                potenciaField.setText(String.valueOf(c.getMotor().getPotencia()));
                cargaMaxField.setText(String.valueOf(c.getCargaMax()));
                taraField.setText(String.valueOf(c.getTara()));
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Veículo de carga não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
    }

    private void excluirCarga() {
        String placa = placaField.getText();
        boolean removido = bd.removerCargaPorPlaca(placa);
        if (removido) {
            JOptionPane.showMessageDialog(this, "Veículo de carga removido com sucesso!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Veículo de carga não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
