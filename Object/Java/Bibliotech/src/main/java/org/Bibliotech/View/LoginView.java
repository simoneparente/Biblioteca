package org.Bibliotech.View;

import org.Bibliotech.Controller.UtenteController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.Bibliotech.Main.mc;

public class LoginView extends GeneralView {

    private JPanel rootPanel;
    private JButton registratiButton;
    private JButton loginButton;
    private JButton annullaButton;
    private JTextField usernameTextField;
    private JPanel imagePanel;
    private JPanel contentPanel;
    private JPasswordField passwordField;
    private JCheckBox mostraPasswordCheckBox;
    private JLabel imageLabel;

    public LoginView(){
        imageLabel.setIcon(logoIcon);
        imagePanel.setSize(360,250);
        JFrame frame = newView("Login", rootPanel);
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mc.switchGUI(mc.getRegisterView().getName(), getName());
            }
        });
        annullaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usernameTextField.setText("");
                passwordField.setText("");
            }
        });
        mostraPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(mostraPasswordCheckBox.isSelected()){
                    passwordField.setEchoChar((char)0);
                }else{
                    passwordField.setEchoChar('•');
                }
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                String password = String.valueOf(passwordField.getPassword());
                if (username.isBlank()) {
                    JOptionPane.showMessageDialog(null, "Il campo username non può essere vuoto, inserisci un username valido!");
                } else {
                    if (password.isBlank()) {
                        JOptionPane.showMessageDialog(null, "Il campo password non può essere vuoto, inserisci una password valida");
                    } else {
                        UtenteController c = new UtenteController();
                        if (c.loginUtente(username, password)) {
                            mc.switchGUI(mc.getSearchView().getName(), mc.getLoginView().getName());
                        }else {
                            usernameTextField.setText("");
                            passwordField.setText("");
                            JOptionPane.showMessageDialog(null, "Username e/o Password errati");
                        }
                    }
                }
            }
        });
    }
}