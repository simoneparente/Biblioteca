package org.Bibliotech.View;

import javax.swing.*;

public class RegisterView extends View{
    private static RegisterView instance;
    static final String nome="Register";
    private JPanel logoPanel;
    private JPanel contentPanel; //panel che contiene tutti i componenti della view
    private JLabel logoLabel;
    private JPanel rootPanel;
    private JTextField usernameField;
    private JPasswordField confermaPasswordField;
    private JPasswordField passwordField1;
    private JCheckBox mostraPasswordChechBox;
    private JPanel fieldsPanel;
    private JButton registratiButton;
    private JButton annullaButton;
    private JLabel usernameLabel;

    RegisterView() {
        super(nome); //nome della view passato al costruttore
        logoPanel.setSize(360, 250);
        this.setContentPane(rootPanel); //setta il contentPanel come contentPane del JFrame
        logoLabel.setIcon(logoLabelIcon); //setta l'icona del logo (logoLabelIcon viene presa da superclasse View)
    }

    public static RegisterView getInstance(){
        if(instance == null){
            instance = new RegisterView();
        }
        return instance;
    }
    public static void main(String[] args){
        RegisterView rv = getInstance();
    }
}
