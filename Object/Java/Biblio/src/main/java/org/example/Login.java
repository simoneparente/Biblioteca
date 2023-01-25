package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    private JFrame loginFrame;
    private JPanel rootPanel;
    private JPanel userPanel;
    private JPanel imagePanel;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton annullaButton;
    private JButton okButton;
    private JButton registerNowButton;
    private JButton ok;
    private JLabel immagine;

    Login(String nome){
        immagine.setIcon(new ImageIcon(""));

        loginFrame.setName(nome);
        loginFrame.setBounds(100,100, 720, 480);


        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
