package com.jeniffer.atividade09;

import javax.swing.*;
import java.awt.*;

public class TelaInicial extends JFrame {
    private BDVeiculos bd;

    public TelaInicial(BDVeiculos bd) {
        this.bd = bd;

        setTitle("Sistema de Gestão de Veículos");
        setSize(700, 500);
        setMinimumSize(new Dimension(700, 500));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        JFrame.setDefaultLookAndFeelDecorated(true);


        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.white);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 0, 15, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;


        JButton btnPasseio = criarBotaoPersonalizado("Gerenciar Veículos de Passeio", new Color(0, 122, 255));
        gbc.gridy = 0;
        panel.add(btnPasseio, gbc);



        JButton btnCarga = criarBotaoPersonalizado("Gerenciar Veículos de Carga", new Color(0, 153, 0));
        gbc.gridy = 1;
        panel.add(btnCarga, gbc);

        add(panel);


        btnPasseio.addActionListener(e -> {
            new TelaPasseio(bd);
            dispose();
        });

        btnCarga.addActionListener(e -> {
            new TelaCarga(bd);
            dispose();
        });

        setVisible(true);
    }


    private JButton criarBotaoPersonalizado(String texto, Color corFundo) {
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

        botao.setForeground(Color.BLACK);
        botao.setFocusPainted(false);
        botao.setContentAreaFilled(false);
        botao.setOpaque(false);
        botao.setPreferredSize(new Dimension(300, 50));
        botao.setFont(new Font("Arial", Font.BOLD, 14));

        return botao;
    }
}
