package local.redes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.*;
import java.nio.charset.StandardCharsets;

// @author Jeniffer Cristina Freitas Ramos
public class BatePapo extends JFrame {
    private static final String ADDRESS = "224.1.1.20";
    private static final int DOOR = 50000;

    private final JTextField tfUser = new JTextField(12);
    private final JTextArea taText = new JTextArea(12, 40);
    private final JTextField tfText = new JTextField();
    private final JButton btConect = new JButton("Conectar");
    private final JButton btSend = new JButton("Enviar");

    private volatile boolean connected = false;


    private MulticastSocket socket;
    private InetAddress group;
    private Listener listener;
    private String user;

    public BatePapo() {
        setTitle("CHAT");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));


        JPanel top = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4,4,4,4);
        c.gridy = 0; c.anchor = GridBagConstraints.WEST;

        c.gridx = 0; top.add(new JLabel("Usuário"), c);
        c.gridx = 1; c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 0.3; top.add(tfUser, c);
        c.gridx = 2; c.fill = GridBagConstraints.NONE; c.weightx = 0; top.add(new JLabel("Endereço"), c);
        JTextField tfEndereco = new JTextField(ADDRESS, 12); tfEndereco.setEnabled(false);
        c.gridx = 3; c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 0.25; top.add(tfEndereco, c);
        c.gridx = 4; c.fill = GridBagConstraints.NONE; c.weightx = 0; top.add(new JLabel("Porta"), c);
        JTextField tfPorta = new JTextField(String.valueOf(DOOR), 6); tfPorta.setEnabled(false);
        c.gridx = 5; c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 0.15; top.add(tfPorta, c);
        c.gridx = 6; c.fill = GridBagConstraints.NONE; c.weightx = 0; top.add(btConect, c);
        add(top, BorderLayout.NORTH);


        taText.setEditable(false);
        taText.setLineWrap(true);
        taText.setWrapStyleWord(true);
        add(new JScrollPane(taText), BorderLayout.CENTER);


        JPanel bottom = new JPanel(new BorderLayout(6,6));
        bottom.add(tfText, BorderLayout.CENTER);
        bottom.add(btSend, BorderLayout.EAST);
        add(bottom, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);


        btConect.addActionListener(e -> Conect());
        btSend.addActionListener(e -> Send());
        tfText.addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) Send();
            }
        });

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override public void windowClosing(java.awt.event.WindowEvent e) {
                disconnect();
            }
        });
    }

    private void Conect() {
        if (connected) {
            disconnect();
            appendInfo("Desconectado.");
            return;
        }
        user = tfUser.getText().trim();
        if (user.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o usuário.");
            return;
        }
        try {
            conectServer();
            appendInfo("Conectado em " + ADDRESS + ":" + DOOR + " como " + user + ".");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar: " + ex.getMessage());
        }
    }

    private void Send() {
        if (!connected) {
            JOptionPane.showMessageDialog(this, "Conecte-se primeiro.");
            return;
        }
        String textUser = tfText.getText().trim();
        if (textUser.isEmpty()) return;

        if ("Sair".equalsIgnoreCase(textUser)) {
            disconnect();
            appendInfo("Você saiu do chat.");
            return;
        }

        try {
            String message = user + ": " + textUser;
            byte[] data = message.getBytes(StandardCharsets.UTF_8);
            DatagramPacket pkt = new DatagramPacket(data, data.length, group, DOOR);
            socket.send(pkt);
            tfText.setText("");
        } catch (Exception ex) {
            appendInfo("Falha ao enviar: " + ex.getMessage());
        }
    }

    private void conectServer() throws Exception {
        group = InetAddress.getByName(ADDRESS);
        if (!group.isMulticastAddress()) throw new IllegalArgumentException("Endereço multicast inválido");

        socket = new MulticastSocket(DOOR);
        socket.setReuseAddress(true);


        socket.joinGroup(group);


        listener = new Listener(socket, user, new MsgHandler() {
            @Override public void onMsg(String s) { appendMsg(s); }
            @Override public void onInfo(String s) { appendInfo(s); }
        });
        listener.start();

        setConnectionFields(true);
        connected = true;
    }

    private void disconnect() {
        connected = false;
        try {
            if (listener != null) listener.shutdown();
            if (socket != null) {
                try {
                    if (group != null) socket.leaveGroup(group);
                } catch (Exception ignore) {}
                socket.close();
            }
        } finally {
            setConnectionFields(false);
        }
    }

    private void setConnectionFields(boolean on) {
        tfUser.setEnabled(!on);
        btConect.setText(on ? "Desconectar" : "Conectar");
        tfText.setEnabled(on);
        btSend.setEnabled(on);
    }

    private void appendMsg(String msg) {
        SwingUtilities.invokeLater(() -> {
            taText.append(msg + System.lineSeparator());
            taText.setCaretPosition(taText.getDocument().getLength());
        });
    }
    private void appendInfo(String info) { appendMsg("[INFO] " + info); }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BatePapo().setVisible(true));
    }


    interface MsgHandler { void onMsg(String s); void onInfo(String s); }


    static class Listener extends Thread {
        private final MulticastSocket socket;
        private final String currentUser;
        private final MsgHandler handler;
        private volatile boolean running = true;

        Listener(MulticastSocket socket, String currentUser, MsgHandler handler) {
            super("Listener");
            setDaemon(true);
            this.socket = socket;
            this.currentUser = currentUser;
            this.handler = handler;
        }

        @Override public void run() {
            try {
                byte[] msg = new byte[128];
                while (running && !socket.isClosed()) {
                    DatagramPacket dg = new DatagramPacket(msg, msg.length);
                    socket.receive(dg);
                    String message = new String(dg.getData(), dg.getOffset(), dg.getLength(), StandardCharsets.UTF_8);

                    if (!message.startsWith(currentUser + ": ")) {
                        handler.onMsg(message);
                    }
                    msg = new byte[128];
                }
            } catch (Exception e) {
                if (running) handler.onInfo("[ERRO] " + e.getMessage());
            }
        }
        void shutdown() { running = false; }
    }
}
