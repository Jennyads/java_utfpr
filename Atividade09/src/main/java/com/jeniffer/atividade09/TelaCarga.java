package com.jeniffer.atividade09;

import javax.swing.*;
import java.awt.*;

public class TelaCarga extends JFrame {
    private BDVeiculos bd;

    public TelaCarga(BDVeiculos bd) {
        this.bd = bd;

        setTitle("Veículos de Carga");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        JFrame.setDefaultLookAndFeelDecorated(true);


        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;


        JButton btnCadastrar = criarBotao("Cadastrar", new Color(0, 153, 0), Color.BLACK);
        JButton btnConsultar = criarBotao("Consultar / Excluir pela placa", new Color(0, 153, 0), Color.BLACK);
        JButton btnImprimir = criarBotao("Imprimir / Excluir todos", new Color(0, 153, 0), Color.BLACK);
        JButton btnSair = criarBotao("Sair", Color.RED, Color.WHITE);

        gbc.gridy = 0;
        panel.add(btnCadastrar, gbc);
        gbc.gridy = 1;
        panel.add(btnConsultar, gbc);
        gbc.gridy = 2;
        panel.add(btnImprimir, gbc);
        gbc.gridy = 3;
        panel.add(btnSair, gbc);

        add(panel);


        btnCadastrar.addActionListener(e -> new TelaCadastroCarga(bd));
        btnConsultar.addActionListener(e -> new TelaConsultaCarga(bd));
        btnImprimir.addActionListener(e -> new TelaImprimirExcluirTodosCarga(bd));
        btnSair.addActionListener(e -> {
            new TelaInicial(bd);
            dispose();
        });

        setVisible(true);
    }

    private JButton criarBotao(String texto, Color corFundo, Color corTexto) {
        JButton botao = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(corFundo);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g2);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {

            }
        };

        botao.setForeground(corTexto);
        botao.setFocusPainted(false);
        botao.setContentAreaFilled(false);
        botao.setOpaque(false);
        botao.setPreferredSize(new Dimension(300, 40));
        botao.setFont(new Font("Arial", Font.BOLD, 13));
        return botao;
    }
}
