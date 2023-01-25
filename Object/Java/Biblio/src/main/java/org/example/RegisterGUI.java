package org.example;

import com.formdev.flatlaf.*;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;

public class RegisterGUI {
    private JTextField usernameField;//
    private JLabel usernameLabel;//
    private JPasswordField passwordField;//
    private JLabel passwordLabel;//
    private JLabel logoLabel;//
    private JPanel outerPanel;//
    private JPanel dataPanel;//
    private JPanel logoPanel;//
    private JPasswordField confermaPasswordField; //
    private JLabel confermaPasswordLabel;//
    private JButton annullaButton;
    private JButton registratiButton;

    RegisterGUI(){
        FlatIntelliJLaf.setup();
        logoLabel.setIcon(new ImageIcon("src/Immagini/logo.png"));
        JFrame frame = new JFrame("Register");
        frame.setBounds(100, 100, 360, 480);
        frame.setResizable(false);
        frame.setContentPane(outerPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        confermaPasswordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    //==click registrati;
                }
                else {
                super.keyPressed(e);
            }
        }
        });
        registratiButton.addMouseListener(new MouseAdapter() {
        });
    }
}
