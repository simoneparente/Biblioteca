package org.Bibliotech.View;

import org.Bibliotech.Controller.MainController;

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

    public LoginView(){
        newView("login", rootPanel);

        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mc.switchGUI(mc.getRegisterView().getName(), getName());
            }


        });
        annullaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
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
    }
}
