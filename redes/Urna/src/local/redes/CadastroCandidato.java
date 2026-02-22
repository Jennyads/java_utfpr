package local.redes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

// @author Jeniffer Cristina Freitas Ramos

public class CadastroCandidato extends JDialog {

    private final Urna service;

    private JTextField txtNumero;
    private JTextField txtNome;
    private JLabel lblStatus;

    public CadastroCandidato(Frame owner, Urna service) {
        super(owner, "Cadastro de Candidatos", true);
        this.service = service;

        setSize(400, 220);
        setLayout(new BorderLayout());
        setLocationRelativeTo(owner);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel painelCentro = new JPanel(new GridBagLayout());
        painelCentro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;


        gbc.gridx = 0;
        gbc.gridy = 0;
        painelCentro.add(new JLabel("Número do candidato:"), gbc);

        txtNumero = new JTextField(15);
        txtNumero.setPreferredSize(new Dimension(180, 25));
        gbc.gridx = 1;
        painelCentro.add(txtNumero, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        painelCentro.add(new JLabel("Nome do candidato:"), gbc);

        txtNome = new JTextField(15);
        txtNome.setPreferredSize(new Dimension(180, 25));
        gbc.gridx = 1;
        painelCentro.add(txtNome, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton btnSalvar = new JButton("Cadastrar");
        btnSalvar.addActionListener(this::salvarCandidato);
        painelCentro.add(btnSalvar, gbc);

        add(painelCentro, BorderLayout.CENTER);

        lblStatus = new JLabel(" ");
        lblStatus.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(lblStatus, BorderLayout.SOUTH);
    }

    private void salvarCandidato(ActionEvent event) {
        String textoNumero = txtNumero.getText().trim();
        String nome = txtNome.getText().trim();

        if (textoNumero.isEmpty() || nome.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Informe número e nome do candidato.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int numero = Integer.parseInt(textoNumero);

            Candidato candidato = new Candidato(numero, nome);
            service.registrarCandidato(candidato);

            lblStatus.setText("Candidato cadastrado com sucesso!");
            txtNumero.setText("");
            txtNome.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Número do candidato deve ser inteiro.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao cadastrar candidato: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
