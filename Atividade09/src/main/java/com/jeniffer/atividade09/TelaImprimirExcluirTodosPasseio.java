package com.jeniffer.atividade09;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaImprimirExcluirTodosPasseio extends JFrame {
    private BDVeiculos bd;
    private JTable tabela;
    private DefaultTableModel modelo;

    public TelaImprimirExcluirTodosPasseio(BDVeiculos bd) {
        this.bd = bd;

        setTitle("Imprimir / Excluir Todos - Passeio");
        setSize(800, 300);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        String[] colunas = {"Placa", "Marca", "Modelo", "Cor", "Qtd. Rodas", "Veloc Máx (M/h)", "Qtd. Pist", "Potência", "Qtd. Passag"};
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
            new TelaPasseio(bd);
            dispose();
        });

        imprimirTodos();

        setVisible(true);
    }

    private void imprimirTodos() {
        modelo.setRowCount(0);
        for (Passeio p : bd.getListaPasseio()) {
            modelo.addRow(new Object[]{
                    p.getPlaca(), p.getMarca(), p.getModelo(), p.getCor(),
                    p.getQtdRodas(), p.calcVel(p.getVelocMax()), p.getMotor().getQtdPist(),
                    p.getMotor().getPotencia(), p.getQtdPassageiros()
            });
        }
    }

    private void excluirTodos() {
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir todos?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            bd.getListaPasseio().clear();
            modelo.setRowCount(0);
            JOptionPane.showMessageDialog(this, "Todos os veículos de passeio foram excluídos.");
        }
    }
}
