package org.Bibliotech.View;


import org.Bibliotech.Controller.RegisterController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


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
    private JCheckBox mostraPasswordCheckBox;
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
                RegisterController.getInstance().register(usernameField, passwordField, confermaPasswordField);
            }
        });
        mostraPasswordCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (mostraPasswordCheckBox.isSelected()) {
                    passwordField.setEchoChar((char) 0);
                    confermaPasswordField.setEchoChar((char) 0);
                } else {
                    passwordField.setEchoChar('•');
                    confermaPasswordField.setEchoChar('•');
                }
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
