package org.Bibliotech.Controller;

import org.Bibliotech.DAOimplementazione.ImplementazioneUtente;
import org.Bibliotech.Model.Utente;
import org.Bibliotech.View.LoginView;

import javax.swing.*;
import java.awt.*;

public class RegisterController extends Controller{
    public static RegisterController instance;

    public static RegisterController getInstance() {
        if (instance == null) {
            instance = new RegisterController();
        }
        return instance;
    }

    public boolean register(JTextField usernameField, JPasswordField passwordField, JPasswordField confermaPasswordField) {

        if(checkRegisterFields(usernameField, passwordField, confermaPasswordField)
           && checkRegisterPasswordMatch(passwordField, confermaPasswordField)
           && !UtenteController.getInstance().checkUserExistInDatabase(usernameField)){
            UtenteController.getInstance().registraUtente(usernameField.getText(), String.valueOf(passwordField.getPassword()));
            System.out.println("Utente " + usernameField.getText() + " registrato");
            return true;
        } else {
            return false;
        }
        }
        //if (checkRegisterFields(usernameField, passwordField, confermaPasswordField)) {
        //    if (checkRegisterPasswordMatch(passwordField, confermaPasswordField)) {
        //        ImplementazioneUtente iu = new ImplementazioneUtente();
        //        if (!iu.checkUserExistInDatabase(usernameField.getText())) {
        //            UtenteController.getInstance().registraUtente(usernameField.getText(), String.valueOf(passwordField.getPassword()));
        //            System.out.println("Utente " + usernameField.getText() + " registrato");
        //            return true;
        //        } else {
        //            usernameField.setBorder(BorderFactory.createLineBorder(Color.red, 2));
        //            System.out.println("Utente già esistente");
        //            return false;
         //       }
        //    }
        //} else {
        //    System.out.println("Errore");
        //    return false;
        //}
        //return false;
    //}

    private boolean checkRegisterPasswordMatch(JPasswordField passwordField, JPasswordField confermaPasswordField) {
        String password = String.valueOf(passwordField.getPassword());
        String confermaPassword = String.valueOf(confermaPasswordField.getPassword());
        if(password.equals(confermaPassword) && (!password.isBlank() && !confermaPassword.isBlank())) {
            return true;
        } else {
            passwordField.setBorder(BorderFactory.createLineBorder(Color.red, 2));
            confermaPasswordField.setBorder(BorderFactory.createLineBorder(Color.red, 2));
            return false;
        }
    }

    private boolean checkRegisterFields(JTextField usernameField, JPasswordField passwordField, JPasswordField confermaPasswordField) {
        if (usernameField.getText().isBlank()) {
            usernameField.setBorder(BorderFactory.createLineBorder(Color.red, 2));
        } else {
            usernameField.setBackground(UIManager.getColor("JTextField.background"));
        }
        if (String.valueOf(passwordField.getPassword()).isBlank()) {
            passwordField.setBorder(BorderFactory.createLineBorder(Color.red, 2));
        } else {
            passwordField.setBackground(UIManager.getColor("JTextField.background"));
        }
        if (String.valueOf(confermaPasswordField.getPassword()).isBlank()) {
            confermaPasswordField.setBorder(BorderFactory.createLineBorder(Color.red, 2));
        } else {
            confermaPasswordField.setBackground(UIManager.getColor("JTextField.background"));
        }
        //se entrambi i campi sono compilati, ritorna true, altrimenti ritorna false
        return !usernameField.getText().isBlank() && !String.valueOf(passwordField.getPassword()).isBlank() && !String.valueOf(confermaPasswordField.getPassword()).isBlank();
    }
}
