package org.example.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.example.Main.mc;

public class RegisterView extends GeneralView {

    private JPanel rootPanel;
    private JPanel imagePanel;
    private JPanel contentPanel;
    private JButton annullaButton;
    private JButton loginButton;
    private JButton registratiButton;
    private JTextField usernameTextField;
    private JButton homeButton;
    private JPasswordField passwordField;
    private JCheckBox showPWBox;

    public RegisterView(){
        this.setContentPane(rootPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(false);
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mc.switchGUI("login", "register");
            }
        });
        annullaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usernameTextField.setText("");
                passwordField.setText("");
            }
        });
        showPWBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(showPWBox.isSelected()){
                    passwordField.setEchoChar((char)0);
                }else{
                    passwordField.setEchoChar('•');
                }
            }
        });
    }
}
