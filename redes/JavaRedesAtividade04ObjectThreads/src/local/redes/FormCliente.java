package local.redes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

// @author Jeniffer Cristina Freitas Ramos

public class FormCliente extends JFrame {

    private JTextField txtNome;
    private JTextField txtIdade;
    private JTextArea  txtLog;
    private JButton    btnEnviar;

    public FormCliente() {
        super("Cliente - Enviar Pessoa (Atividade 04)");

        txtNome  = new JTextField(20);
        txtIdade = new JTextField(6);
        btnEnviar = new JButton("Enviar");
        txtLog   = new JTextArea(5, 30);
        txtLog.setEditable(false);
        txtLog.setMargin(new Insets(8, 8, 8, 8));

        JScrollPane scroll = new JScrollPane(txtLog,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,6,6,6);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0; c.gridy = 0; form.add(new JLabel("Nome:"), c);
        c.gridx = 1; c.gridy = 0; form.add(txtNome, c);

        c.gridx = 0; c.gridy = 1; form.add(new JLabel("Idade:"), c);
        c.gridx = 1; c.gridy = 1; form.add(txtIdade, c);

        c.gridx = 1; c.gridy = 2; c.anchor = GridBagConstraints.EAST;
        form.add(btnEnviar, c);

        setLayout(new BorderLayout(10,10));
        add(form, BorderLayout.NORTH);

        JPanel painelLog = new JPanel(new BorderLayout());
        painelLog.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        painelLog.add(scroll, BorderLayout.CENTER);

        add(painelLog, BorderLayout.CENTER);



        btnEnviar.addActionListener(this::enviarPessoa);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(520, 420);
        setLocationRelativeTo(null);
    }

    private void enviarPessoa(ActionEvent e) {
        String nome = txtNome.getText().trim();
        String idadeStr = txtIdade.getText().trim();

        if (nome.isEmpty() || idadeStr.isEmpty()) {
            appendLog("Preencha nome e idade.");
            return;
        }

        int idade;
        try {
            idade = Integer.parseInt(idadeStr);
            if (idade < 0) throw new NumberFormatException("Idade negativa");
        } catch (NumberFormatException ex) {
            appendLog("Idade inválida. Informe um número inteiro não negativo.");
            return;
        }

        final String HOST = "127.0.0.1";
        final int PORTA = 50000;

        try (Socket socket = new Socket(HOST, PORTA)) {
            appendLog("Conectado ao servidor " + HOST + ":" + PORTA);

            try (ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream  entrada = new ObjectInputStream(socket.getInputStream())) {

                Pessoa pessoa = new Pessoa(nome, idade);
                saida.writeObject(pessoa);
                saida.flush();

                Object resposta = entrada.readObject();
                if (resposta instanceof String msg) {
                    appendLog("Servidor: " + msg);
                } else {
                    appendLog("Resposta inesperada do servidor: " + resposta);
                }
            }

            appendLog("Conexão encerrada.");
        } catch (Exception ex) {
            appendLog("Erro: " + ex.getMessage());
            ex.printStackTrace();
        }


        txtNome.setText("");
        txtIdade.setText("");
        txtNome.requestFocus();
    }


    private void appendLog(String msg) {
        txtLog.append(msg + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormCliente().setVisible(true));
    }
}
