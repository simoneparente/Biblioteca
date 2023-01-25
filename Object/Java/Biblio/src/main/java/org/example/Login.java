package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    private JPanel rootPanel;
    private JButton annullaButton;
    private JPanel outerPanel;
    private JPanel dataPanel;
    private JTextField usernameField;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton registratiButton;
    private JPanel logoPanel;
    private JButton LoginButton;
    private JButton okButton;
    private JButton registerNowButton;
    private JLabel immagineLabel;
    private JButton ok;
    private Controller c;
    Login(Controller controller){
        c = controller;
        Login loginFrame = c.getLoginFrame();

        immagineLabel.setIcon(new ImageIcon("src/Immagini/logo.png"));

        loginFrame.setBounds(100,100, 360, 480);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setResizable(false);
        loginFrame.setContentPane(rootPanel);
        loginFrame.setVisible(false);



        annullaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.goToPage(loginFrame, new RegisterGUI(c).getRegisterGUI());
            }
        });
        LoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
