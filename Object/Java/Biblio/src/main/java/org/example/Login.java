package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    private JPanel rootPanel;
    private JPanel userPanel;
    private JPanel imagePanel;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton annullaButton;
    private JButton okButton;
    private JButton registerNowButton;
    private JLabel immagineLabel;
    private JButton ok;

    Login(){
        JFrame loginFrame = new JFrame();

        immagineLabel.setIcon(new ImageIcon("src/Immagini/logo.png"));

        loginFrame.setBounds(100,100, 360, 480);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setContentPane(rootPanel);
        loginFrame.setVisible(true);


        registerNowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginFrame.dispose();
                new RegisterGUI();

            }
        });
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        annullaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
