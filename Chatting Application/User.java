import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.plaf.FontUIResource;

public class User extends JFrame {
    Socket socket;
    BufferedReader br;
    PrintWriter out;
    private JLabel heading = new JLabel("Clint Area");
    private JTextArea messagArea = new JTextArea();
    private JTextField messageInput = new JTextField();
    private FontUIResource font = new FontUIResource("Roboto", Font.BOLD, 20);

    public User() {
        try {
            System.out.println("sending request to server");
            socket = new Socket("192.168.1.5", 124);
            System.out.println("connected");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            createGUI();
            handleEvent();
            reading();
            // writing();
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
                    messagArea.append("user:" + dataTosend + "\n");
                    out.println(dataTosend);
                    out.flush();
                    messageInput.setText("");
                    messageInput.requestFocus();
                }
            }

        });
    }

    private void createGUI() {
        this.setTitle("User Messager");
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
                        System.out.println("server stop:");
                        JOptionPane.showMessageDialog(null, "server stop:");
                        messageInput.setEnabled(false);
                        socket.close();
                        break;
                    }
                    messagArea.append("server: " + msg + "\n");
                }
            } catch (Exception e) {
                System.out.println("exit");
            }

        };
        new Thread(r1).start();
    }

    public void writing() {
        Runnable r2 = () -> {
            System.out.println("Writer Started");
            try {
                while (!socket.isClosed()) {

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String data = br1.readLine();
                    out.println(data);
                    out.flush();
                    if (data.equals("IQuit")) {
                        socket.close();
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("exit");
            }

        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        new User();

    }
}
