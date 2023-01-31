package org.Bibliotech.View;

import javax.swing.*;

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
