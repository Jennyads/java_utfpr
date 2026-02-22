package local.redes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.rmi.Naming;
import java.util.List;

// @author Jeniffer Cristina Freitas Ramos

public class Cliente extends JFrame {

    private Urna service;

    private JComboBox<Candidato> comboCandidatos;
    private JTextField txtQuantidade;
    private JLabel lblStatus;

    public Cliente() {
        setTitle("Urna Java");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 280);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        inicializarComponentes();
        conectarServidor();
        atualizarListaCandidatos();
    }

    private void inicializarComponentes() {
        JLabel lblTitulo = new JLabel("Urna Java", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel painelCentro = new JPanel();
        painelCentro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelCentro.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton btnCadastro = new JButton("Cadastro de Candidatos");
        btnCadastro.addActionListener(this::abrirCadastroCandidatos);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        painelCentro.add(btnCadastro, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        painelCentro.add(new JLabel("Selecione o Candidato:"), gbc);

        comboCandidatos = new JComboBox<>();
        gbc.gridx = 1;
        painelCentro.add(comboCandidatos, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        painelCentro.add(new JLabel("Quantidade de votos:"), gbc);

        txtQuantidade = new JTextField();
        gbc.gridx = 1;
        painelCentro.add(txtQuantidade, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton btnEnviar = new JButton("Enviar ao Servidor Central");
        btnEnviar.addActionListener(this::enviarVotos);
        painelCentro.add(btnEnviar, gbc);

        add(painelCentro, BorderLayout.CENTER);


        JPanel painelSul = new JPanel(new BorderLayout());
        lblStatus = new JLabel(" ");
        lblStatus.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        painelSul.add(lblStatus, BorderLayout.CENTER);

        JButton btnEncerrar = new JButton("Encerrar esta Urna");
        btnEncerrar.addActionListener(e -> fecharUrna());
        painelSul.add(btnEncerrar, BorderLayout.EAST);

        add(painelSul, BorderLayout.SOUTH);
    }

    private void conectarServidor() {
        try {
            service = (Urna) Naming.lookup("rmi://127.0.0.1/UrnaService");
            lblStatus.setText("Conectado ao servidor central.");
        } catch (Exception e) {
            lblStatus.setText("Falha ao conectar ao servidor.");
            JOptionPane.showMessageDialog(this,
                    "Não foi possível conectar ao servidor RMI.\n" + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarListaCandidatos() {
        if (service == null) {
            return;
        }
        try {
            List<Candidato> lista = service.listarCandidatos();
            DefaultComboBoxModel<Candidato> model = new DefaultComboBoxModel<>();
            for (Candidato c : lista) {
                model.addElement(c);
            }
            comboCandidatos.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao listar candidatos: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirCadastroCandidatos(ActionEvent event) {
        if (service == null) {
            JOptionPane.showMessageDialog(this,
                    "Servidor não conectado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        CadastroCandidato frame = new CadastroCandidato(this, service);
        frame.setVisible(true);

        atualizarListaCandidatos();
    }

    private void enviarVotos(ActionEvent event) {
        if (service == null) {
            JOptionPane.showMessageDialog(this,
                    "Servidor não conectado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Candidato selecionado = (Candidato) comboCandidatos.getSelectedItem();
        if (selecionado == null) {
            JOptionPane.showMessageDialog(this,
                    "Selecione um candidato.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String textoQtd = txtQuantidade.getText().trim();
        if (textoQtd.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Informe a quantidade de votos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int quantidade = Integer.parseInt(textoQtd);
            service.computarVotos(selecionado.getNumero(), quantidade);
            lblStatus.setText("Dados enviados com sucesso!");
            txtQuantidade.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Quantidade de votos deve ser um número inteiro.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao enviar votos: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fecharUrna() {
        int opcao = JOptionPane.showConfirmDialog(this,
                "Deseja encerrar esta urna?", "Confirmação",
                JOptionPane.YES_NO_OPTION);
        if (opcao == JOptionPane.YES_OPTION) {
            dispose();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Cliente tela = new Cliente();
            tela.setVisible(true);
        });
    }
}
