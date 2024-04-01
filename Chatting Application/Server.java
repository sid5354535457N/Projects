import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.plaf.FontUIResource;

public class Server extends JFrame {
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;
    private JLabel heading = new JLabel("Server Area");
    private JTextArea messagArea = new JTextArea();
    private JTextField messageInput = new JTextField();
    private FontUIResource font = new FontUIResource("Roboto", Font.BOLD, 20);

    public Server() {
        try {
            server = new ServerSocket(124);
            System.out.println("server is started");
            System.out.println("loading..");
            socket = server.accept();
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            createGUI();
            handleEvent();
            reading();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleEvent() {
        messageInput.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    String dataTosend = messageInput.getText();
                    messagArea.append("server:" + dataTosend + "\n");
                    out.println(dataTosend);
                    out.flush();
                    messageInput.setText("");
                    messageInput.requestFocus();
                }
            }

        });
    }

    private void createGUI() {
        this.setTitle("Server Messager");
        this.setSize(600, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        heading.setFont(font);
        messagArea.setFont(font);
        messageInput.setFont(font);

        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        this.setLayout(new BorderLayout());

        this.add(heading, BorderLayout.NORTH);
        this.add(messagArea, BorderLayout.CENTER);
        this.add(messageInput, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    public void reading() {
        Runnable r1 = () -> {
            System.out.println("reader started:");
            try {
                while (true) {

                    String msg = br.readLine();
                    if (msg.equals("IQuit")) {
                        System.out.println("user stop:");
                        JOptionPane.showMessageDialog(null, "user stop:");
                        messageInput.setEnabled(false);
                        socket.close();
                        break;
                    }
                    messagArea.append("server: " + msg + "\n");
                }
            } catch (Exception e) {
                System.out.println("exited");
            }

        };
        new Thread(r1).start();
    }

    public static void main(String[] args) {
        System.out.println("Welcome to server");
        new Server();
    }
}