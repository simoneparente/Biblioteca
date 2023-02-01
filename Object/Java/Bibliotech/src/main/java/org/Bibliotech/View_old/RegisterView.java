package org.Bibliotech.View_old;

import org.Bibliotech.Controller.UtenteController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.Bibliotech.Main.mc;

public class RegisterView extends GeneralView {

    private JPanel rootPanel;
    private JPanel imagePanel;
    private JPanel contentPanel;
    private JButton annullaButton;
    private JButton registratiButton;
    private JTextField usernameTextField;
    private JButton homeButton;
    private JPasswordField passwordField;
    private JCheckBox showPWBox;
    private JLabel imageLabel;
    private JPasswordField confermaPasswordField;
    private JLabel confermaPasswordLabel;

    public RegisterView() {
        imageLabel.setIcon(logoIcon);
        imagePanel.setSize(360, 250);
        newView("Register", rootPanel);
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mc.switchGUI(mc.getLoginView().getName(), mc.getRegisterView().getName());
            }
        });
        annullaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usernameTextField.setText("");
                passwordField.setText("");
                confermaPasswordField.setText("");
            }
        });
        showPWBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPWBox.isSelected()) {
                    passwordField.setEchoChar((char) 0);
                    confermaPasswordField.setEchoChar((char) 0);
                } else {
                    passwordField.setEchoChar('•');
                    confermaPasswordField.setEchoChar('•');
                }
            }
        });
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                String password = String.valueOf(passwordField.getPassword());
                String confermaPassword = String.valueOf(confermaPasswordField.getPassword());
                UtenteController c = new UtenteController();
                boolean blanksFlag = checkFields(username, password, confermaPassword); //true=tutto ok, false=problema
                boolean checkPasswordMatch=checkPasswordMatch(password, confermaPassword);
                boolean checkUserInDatabase=c.checkUserExistInDatabase(username);
                //System.out.println("blankflags: "+blanksFlag);
                //System.out.println("match: "+checkPasswordMatch);
                if(!blanksFlag){
                    JOptionPane.showMessageDialog(null, "Controllare i campi in rosso");
                }
                else if(checkUserInDatabase){
                    JOptionPane.showMessageDialog(usernameTextField, "Username già in uso, sceglierne un altro.");

                }
                else {
                    if (checkPasswordMatch) {
                        c.registraUtente(username, password);
                        JOptionPane.showMessageDialog(null, "Utente " + username + " registrato con successo");
                        mc.switchGUI(mc.getSearchView().getName(), mc.getLoginView().getName());
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Le password non corrispondono");
                    }
                }
            }
        });
        /*registratiButton.addActionListener(new ActionListener() {
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
                            mc.switchGUI(mc.getSearchView().getName(), mc.getRegisterView().getName());
                        }
                    }
                }
            }
        });*/
    }
    private boolean checkPasswordMatch(String password, String confermaPassword) {
        if(password.equals(confermaPassword) && (!password.isBlank() && !confermaPassword.isBlank())) return true;
        else {
            passwordField.setBackground(Color.red);
            confermaPasswordField.setBackground(Color.red);
            return false;
        }
    }

    //controlla che tutte le stringhe non siano vuote o composte da soli whitespaces, ritorna false se almeno di loro è
    // vuoto o contiene solo whitespaces, inoltre cambia colore dei JTextField in rosso se non hanno caratteri al
    //loro interno, in caso contrario li setta al loro colore di default
    private boolean checkFields(String username, String password, String confermaPassword) {
        boolean userFlag=username.isBlank();
        boolean passwordFlag=password.isBlank();
        boolean confermaPasswordFlag=confermaPassword.isBlank();

        if (userFlag) {
            usernameTextField.setBackground(Color.red);
        } else {
            usernameTextField.setBackground(UIManager.getColor("JTextField.background"));
        }

        if (passwordFlag) {
            passwordField.setBackground(Color.red);
        } else {
            passwordField.setBackground(UIManager.getColor("JTextField.background"));
        }

        if (confermaPasswordFlag) {
            confermaPasswordField.setBackground(Color.red);
        } else {
            confermaPasswordField.setBackground(UIManager.getColor("JTextField.background"));
        }

        if(!userFlag && !passwordFlag && !confermaPasswordFlag){
            return true;
        }
        else return false;
    }
}
