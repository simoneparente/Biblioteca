package org.Bibliotech.View;

import org.Bibliotech.Controller.LoginController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginView extends View{
    static final String nome="Login";
    private static LoginView instance;
    private JPanel logoPanel;
    private JPanel contentPanel; //panel che contiene tutti i componenti della view
    private JLabel logoLabel;
    private JPanel rootPanel;
    private JTextField usernameField;
    private JPasswordField confermaPasswordField;
    private JPasswordField passwordField;
    private JCheckBox mostraPasswordCheckBox;
    private JButton loginButton;
    private JCheckBox mostraPasswordChechBox;
    private JPanel fieldsPanel;
    private JButton annullaButton;
    private JLabel registratiLabel;
    private JLabel usernameLabel;

    LoginView() {
        super(nome);
        logoPanel.setSize(360, 250);
        this.setContentPane(rootPanel);
        logoLabel.setIcon(logoLabelIcon);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginController.getInstance().login(usernameField, passwordField);
            }
        });
        registratiLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                LoginController.getInstance().switchView(RegisterView.getInstance(), LoginView.getInstance());
            }
        });
        registratiLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                registratiLabel.setBorder(BorderFactory.createLineBorder(Color.decode("#F39524"), 1));
            }
        });
        registratiLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                registratiLabel.setBorder(null);
            }
        });
        mostraPasswordCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (mostraPasswordCheckBox.isSelected()) {
                    passwordField.setEchoChar((char) 0);
                } else {
                    passwordField.setEchoChar('•');
                }
            }
        });
        annullaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usernameField.setText("");
                passwordField.setText("");
            }
        });
    }


    public static LoginView getInstance(){
        if(instance == null){
            instance = new LoginView();
        }
        return instance;
    }
    public static void main(String[] args){
        getInstance();
    }

    public String getViewName(){
        return nome;
    }
}
