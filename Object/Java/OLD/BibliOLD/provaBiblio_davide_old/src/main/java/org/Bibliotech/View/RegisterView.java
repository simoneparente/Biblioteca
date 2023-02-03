package org.Bibliotech.View;

import org.Bibliotech.Controller.UtenteController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.Bibliotech.Main.mc;

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
        newView("register", rootPanel);
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
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                String password = String.valueOf(passwordField.getPassword());
                if (username.isBlank()) {
                    JOptionPane.showMessageDialog(null, "Inserisci un username valido");
                } else {
                    //probabile aggiunta di trigger o controllo per user gia in db
                    JOptionPane.showMessageDialog(null, "Username valido");
                    if (password.isBlank()) {
                        JOptionPane.showMessageDialog(null, "Inserisci una password valida");
                    } else {
                        JOptionPane.showMessageDialog(null, "User e Password validi");
                        UtenteController c = new UtenteController();
                        if (c.registraUtente(username, password)) {
                            JOptionPane.showMessageDialog(null, "Utente registrato con successo");
                            mc.switchGUI("login", "register");
                        }
                    }
                }
            }
        });
    }
}
