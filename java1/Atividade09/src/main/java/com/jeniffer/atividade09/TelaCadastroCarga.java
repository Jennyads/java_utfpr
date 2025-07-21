package com.jeniffer.atividade09;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroCarga extends JFrame {
    private BDVeiculos bd;

    private JTextField placaField, marcaField, modeloField, corField, velMaxField, qtdRodasField, pistoesField, potenciaField, cargaMaxField, taraField;

    public TelaCadastroCarga(BDVeiculos bd) {
        this.bd = bd;

        setTitle("Cadastro de Carga");
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        Font fonte = new Font("Arial", Font.BOLD, 12);


        placaField = criarLinha(panel, gbc, "Placa:", fonte);
        marcaField = criarLinha(panel, gbc, "Marca:", fonte);
        modeloField = criarLinha(panel, gbc, "Modelo:", fonte);
        corField = criarLinha(panel, gbc, "Cor:", fonte);
        velMaxField = criarLinha(panel, gbc, "Velocidade Máx.:", fonte);
        qtdRodasField = criarLinha(panel, gbc, "Qtd. Rodas:", fonte);
        pistoesField = criarLinha(panel, gbc, "Qtd. Pistões:", fonte);
        potenciaField = criarLinha(panel, gbc, "Potência:", fonte);
        cargaMaxField = criarLinha(panel, gbc, "Carga Máx.:", fonte);
        taraField = criarLinha(panel, gbc, "Tara:", fonte);


        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton cadastrarButton = new JButton("Cadastrar");
        JButton limparButton = new JButton("Limpar");
        JButton novoButton = new JButton("Novo");
        JButton sairButton = new JButton("Sair");

        Font btnFont = new Font("Arial", Font.BOLD, 12);
        JButton[] botoes = {cadastrarButton, limparButton, novoButton, sairButton};
        for (JButton b : botoes) {
            b.setFont(btnFont);
            botoesPanel.add(b);
        }


        cadastrarButton.addActionListener(e -> cadastrarCarga());
        limparButton.addActionListener(e -> limparCampos());
        novoButton.addActionListener(e -> limparCampos());
        sairButton.addActionListener(e -> {
            new TelaCarga(bd);
            dispose();
        });

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(botoesPanel, gbc);

        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JTextField criarLinha(JPanel panel, GridBagConstraints gbc, String labelText, Font fonte) {
        JLabel label = new JLabel(labelText);
        label.setFont(fonte);
        JTextField field = new JTextField(10);
        field.setFont(fonte);
        field.setBackground(new Color(230, 230, 230)); // cinza claro

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(label, gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);

        return field;
    }

    private void cadastrarCarga() {
        try {
            Carga c = new Carga();
            c.setPlaca(placaField.getText());
            c.setMarca(marcaField.getText());
            c.setModelo(modeloField.getText());
            c.setCor(corField.getText());

            try {
                c.setVelocMax(Float.parseFloat(velMaxField.getText()));
            } catch (VelocException ex) {
                ex.impErroVeloc();
                try {
                    c.setVelocMax(90);
                    JOptionPane.showMessageDialog(this,
                            "Velocidade inválida!\nA velocidade foi ajustada para 90 km/h (Carga).",
                            "Ajuste de Velocidade",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (VelocException ex2) {
                    ex2.impErroVelocPadrao();
                }
            }

            c.setQtdRodas(Integer.parseInt(qtdRodasField.getText()));
            c.getMotor().setQtdPist(Integer.parseInt(pistoesField.getText()));
            c.getMotor().setPotencia(Integer.parseInt(potenciaField.getText()));
            c.setCargaMax(Integer.parseInt(cargaMaxField.getText()));
            c.setTara(Integer.parseInt(taraField.getText()));

            bd.adicionarCarga(c);

            JOptionPane.showMessageDialog(this, "Veículo de Carga cadastrado com sucesso!");

            limparCampos();

        } catch (VeicExistException ex) {
            ex.impErroExist();
            JOptionPane.showMessageDialog(this,
                    "Erro: Já existe um veículo cadastrado com esta placa.",
                    "Veículo já cadastrado",
                    JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro: Digite valores numéricos válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        placaField.setText("");
        marcaField.setText("");
        modeloField.setText("");
        corField.setText("");
        velMaxField.setText("");
        qtdRodasField.setText("");
        pistoesField.setText("");
        potenciaField.setText("");
        cargaMaxField.setText("");
        taraField.setText("");
    }
}
