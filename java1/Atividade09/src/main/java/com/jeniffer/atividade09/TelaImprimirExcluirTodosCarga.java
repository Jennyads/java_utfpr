package com.jeniffer.atividade09;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaImprimirExcluirTodosCarga extends JFrame {
    private BDVeiculos bd;
    private JTable tabela;
    private DefaultTableModel modelo;

    public TelaImprimirExcluirTodosCarga(BDVeiculos bd) {
        this.bd = bd;

        setTitle("Imprimir / Excluir Todos - Carga");
        setSize(800, 300);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        String[] colunas = {"Placa", "Marca", "Modelo", "Cor", "Qtd. Rodas", "Veloc Máx (Cm/h)", "Qtd. Pist", "Potência", "Carga Máx", "Tara"};
        modelo = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabela);
        add(scrollPane, BorderLayout.CENTER);

        JPanel botoes = new JPanel();

        JButton btnImprimir = new JButton("Imprimir Todos");
        btnImprimir.setBackground(Color.YELLOW);
        botoes.add(btnImprimir);

        JButton btnExcluir = new JButton("Excluir Todos");
        btnExcluir.setBackground(Color.YELLOW);
        botoes.add(btnExcluir);

        JButton btnSair = new JButton("Sair");
        botoes.add(btnSair);

        add(botoes, BorderLayout.SOUTH);

        btnImprimir.addActionListener(e -> imprimirTodos());
        btnExcluir.addActionListener(e -> excluirTodos());
        btnSair.addActionListener(e -> {
            new TelaCarga(bd);
            dispose();
        });

        imprimirTodos();

        setVisible(true);
    }

    private void imprimirTodos() {
        modelo.setRowCount(0);
        for (Carga c : bd.getListaCarga()) {
            modelo.addRow(new Object[]{
                    c.getPlaca(), c.getMarca(), c.getModelo(), c.getCor(),
                    c.getQtdRodas(), c.calcVel(c.getVelocMax()) + " cm/h"
                    , c.getMotor().getQtdPist(),
                    c.getMotor().getPotencia(), c.getCargaMax(), c.getTara()
            });
        }
    }

    private void excluirTodos() {
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir todos?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            bd.getListaCarga().clear();
            modelo.setRowCount(0);
            JOptionPane.showMessageDialog(this, "Todos os veículos de carga foram excluídos.");
        }
    }
}
