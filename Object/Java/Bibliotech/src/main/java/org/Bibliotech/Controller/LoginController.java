package org.Bibliotech.Controller;

import org.Bibliotech.Model.Utente;
import org.Bibliotech.View.LoginView;
import org.Bibliotech.View.SearchView;

import javax.swing.*;
import java.awt.*;

public class LoginController extends Controller {
    private static LoginController instance;

    public static LoginController getInstance() {
        if (instance == null) {
            instance = new LoginController();
        }
        return instance;
    }

    public void login(JTextField usernameField, JPasswordField passwordField) {

        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());
        changeLoginFieldsColor(usernameField, passwordField);
        if (username.isBlank() || password.isBlank()) {
            JOptionPane.showMessageDialog(null, "Inserire username e password");
        } else {
            if (UtenteController.getInstance().loginUtente(username, password)) {
                JOptionPane.showMessageDialog(null, "Login effettuato");
                SearchView.getInstance().checkPermessiNotifiche();
                switchView(SearchView.getInstance(), LoginView.getInstance());
            } else {
                JOptionPane.showMessageDialog(null, "Username o password errati");
                usernameField.setBorder(BorderFactory.createLineBorder(Color.red, 2));
                passwordField.setBorder(BorderFactory.createLineBorder(Color.red, 2));
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

    public void logout() {
        Utente.getInstance().setUsername(null);
        Utente.getInstance().setPassword(null);
        Utente.getInstance().setPermessi(-1);
        LoginView.resetFields();
        switchView(LoginView.getInstance(), SearchView.getInstance());
    }
}
