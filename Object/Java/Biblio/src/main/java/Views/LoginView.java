package Views;

import Controller.*;

import javax.naming.ldap.Control;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends Finestre{
    public JFrame loginFrame;
    private JPanel rootPanel;
    private JPanel userPanel;
    private JPanel imagePanel;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton annullaButton;
    private JButton okButton;
    private JButton registerNowButton;
    private JLabel immagineLabel;
    private JButton ok;
    public LoginView(){
        loginFrame= new_Finestra("Login", rootPanel);
        //Login->Register Button
        registerNowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterView registerView = new RegisterView();
                registerView.new_Finestra(registerView.nome, registerView.outerPanel);
                registerView.show_Finestra();
                LoginView.super.close_Finestra();
            }
        });
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        annullaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
    public JFrame getFrame(){
        return loginFrame;
    }
}
