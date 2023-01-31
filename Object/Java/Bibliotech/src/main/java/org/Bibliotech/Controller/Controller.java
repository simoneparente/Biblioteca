package org.Bibliotech.Controller;

import org.Bibliotech.DAOimplementazione.ImplementazioneUtente;
import org.Bibliotech.View.LoginView;
import org.Bibliotech.View.RegisterView;
import org.Bibliotech.View.View;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Controller {
    private static Controller instance;
    public Controller() {
        home.showView();
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    private View home = RegisterView.getInstance();


    public void register(JTextField usernameField, JPasswordField passwordField, JPasswordField confermaPasswordField) {
        if (checkFields(usernameField, passwordField, confermaPasswordField)) {
            if (checkPasswordMatch(passwordField, confermaPasswordField)) {
                ImplementazioneUtente iu=new ImplementazioneUtente();
                if(!iu.checkUserExistInDatabase(usernameField.getText())){
                    iu.addUser(usernameField.getText(), String.valueOf(passwordField.getPassword()))    ;
                    System.out.println("Utente " + usernameField.getText() + " registrato");
                }
                else {
                    usernameField.setBackground(Color.red);
                    System.out.println("Utente già esistente");
                }

            }
        }
        else {
            System.out.println("Errore");
        }

    }

    private boolean checkPasswordMatch(JPasswordField passwordField, JPasswordField confermaPasswordField) {
        String password = String.valueOf(passwordField.getPassword());
        String confermaPassword = String.valueOf(confermaPasswordField.getPassword());
        if(password.equals(confermaPassword) && (!password.isBlank() && !confermaPassword.isBlank())) return true;
        else {
            passwordField.setBackground(Color.red);
            confermaPasswordField.setBackground(Color.red);
            return false;
        }
    }

    private boolean checkFields(JTextField usernameField, JPasswordField passwordField, JPasswordField confermaPasswordField) {
        if (usernameField.getText().isBlank()) {
            usernameField.setBackground(Color.red);
        } else {
            usernameField.setBackground(UIManager.getColor("JTextField.background"));
        }
        if (passwordField.getPassword().toString().isBlank()) {
            passwordField.setBackground(Color.red);
        } else {
            passwordField.setBackground(UIManager.getColor("JTextField.background"));
        }
        if (confermaPasswordField.getPassword().toString().isBlank()) {
            confermaPasswordField.setBackground(Color.red);
        } else {
            confermaPasswordField.setBackground(UIManager.getColor("JTextField.background"));
        }
        if (usernameField.getText().isBlank() || passwordField.getPassword().toString().isBlank() || confermaPasswordField.getPassword().toString().isBlank()) {
            return false;
        } else {
            return true;
        }
    }
}
