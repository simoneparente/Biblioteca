package org.Bibliotech.View;


import org.Bibliotech.Controller.Controller;
import org.Bibliotech.Controller.RegisterController;

import javax.swing.*;
import java.awt.event.*;


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
    private JPanel buttonsPanel;

    RegisterView() {
        super(nome); //nome della view passato al costruttore
        logoPanel.setSize(360, 250);
        this.setContentPane(rootPanel); //setta il contentPanel come contentPane del JFrame
        logoLabel.setIcon(logoLabelIcon); //setta l'icona del logo (logoLabelIcon viene presa da superclasse View)
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (RegisterController.getInstance().register(usernameField, passwordField, confermaPasswordField)) {
                    JOptionPane.showMessageDialog(null, "Registrazione avvenuta con successo");
                    Controller.getInstance().switchView(LoginView.getInstance(), RegisterView.getInstance());
                    LoginView.getInstance().refreshFields();
                    refreshFields();
                } else {
                    LoginView.getInstance().hideView();
                    JOptionPane.showMessageDialog(null, "Errore durante la registrazione");
                }
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
        annullaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getInstance().switchView(LoginView.getInstance(), RegisterView.getInstance());
            }
        });
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    registratiButton.doClick();
                }
            }
        });
    }

    public static RegisterView getInstance() {
        if (instance == null) {
            instance = new RegisterView();
        }
        return instance;
    }

    private void refreshFields() {
        usernameField.setText("");
        passwordField.setText("");
        confermaPasswordField.setText("");
    }
}
