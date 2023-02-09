package org.Bibliotech.Controller;

import org.Bibliotech.DAOimplementazione.ImplementazioneUtente;
import org.Bibliotech.View.LoginView;
import org.Bibliotech.View.SearchView;

import javax.swing.*;
import java.awt.*;

public class LoginController extends Controller{
    private static LoginController instance;
    public static LoginController getInstance() {
        if (instance == null) {
            instance = new LoginController();
        }
        return instance;
    }

    public void login(JTextField usernameField, JPasswordField passwordField){
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());
        changeLoginFieldsColor(usernameField, passwordField);
        if(username.isBlank() || password.isBlank()){
            JOptionPane.showMessageDialog(null, "Inserire username e password");
        }
        else{
            ImplementazioneUtente iu = new ImplementazioneUtente();
            if(iu.checkLogin(username, password)){
                JOptionPane.showMessageDialog(null, "Login effettuato");
                switchView(SearchView.getInstance(), LoginView.getInstance());
            }
            else{
                JOptionPane.showMessageDialog(null, "Username o password errati");
                usernameField.setBackground(Color.red);
                passwordField.setBackground(Color.red);
            }
        }
    }

    private void changeLoginFieldsColor(JTextField usernameField, JPasswordField passwordField) {
        if (usernameField.getText().isBlank()) {
            usernameField.setBackground(Color.red);
        } else {
            usernameField.setBackground(UIManager.getColor("JTextField.background"));
        }
        if (String.valueOf(passwordField.getPassword()).isBlank()) {
            passwordField.setBackground(Color.red);
        } else {
            passwordField.setBackground(UIManager.getColor("JTextField.background"));
        }
    }

}
