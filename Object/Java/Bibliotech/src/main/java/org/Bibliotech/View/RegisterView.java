package org.Bibliotech.View;


import org.Bibliotech.Controller.Controller;
import org.Bibliotech.Controller.UtenteController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RegisterView extends View {
private static final String nome = "RegisterView";
    private static RegisterView instance;
    private JPanel rootPanel;
    private JPanel logoPanel;
    private JLabel logoLabel;
    private JPanel contentPanel;
    private JTextField usernameField;
    private JLabel usernameLabel;
    private JPasswordField passwordField;
    private JLabel passwordLabel;
    private JPasswordField confermaPasswordField;
    private JLabel confermaPasswordLabel;
    private JCheckBox mostraPasswordChechBox;
    private JPanel fieldsPanel;
    private JButton registratiButton;
    private JButton annullaButton;
    RegisterView() {
        super(nome); //nome della view passato al costruttore
        logoPanel.setSize(360, 250);
        this.setContentPane(rootPanel); //setta il contentPanel come contentPane del JFrame
        logoLabel.setIcon(logoLabelIcon); //setta l'icona del logo (logoLabelIcon viene presa da superclasse View)
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getInstance().register(usernameField, passwordField, confermaPasswordField);
            }
        });
    }

    public static View getInstance() {
        if (instance == null) {
            instance = new RegisterView();
        }
        return instance;
    }
}
