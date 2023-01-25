package org.example;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class RegisterGUI {
    private JTextField usernameField;
    private JLabel usernameLabel;
    private JPasswordField passwordField;
    private JLabel passwordLabel;
    private JLabel logoLabel;
    private JPanel outerPanel;
    private JPanel dataPanel;
    private JPanel logoPanel;
    private JPasswordField passwordField1;
    private JLabel confermaPassword;
    private JButton annullaButton;
    private JButton registratiButton;

    private Controller c;

    RegisterGUI(Controller controller){
        c = controller;
        RegisterGUI registergui= controller.getRegisterGUI();

        logoLabel.setIcon(new ImageIcon("src/Immagini/logo.png"));
        JFrame frame = new JFrame("Register");
        frame.setBounds(100, 100, 360, 480);
        frame.setResizable(false);
        frame.setContentPane(outerPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(false);

        passwordField1.addKeyListener(new KeyAdapter() {
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
    }
}
