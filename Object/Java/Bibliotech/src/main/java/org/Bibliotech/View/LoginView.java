package org.Bibliotech.View;

import org.Bibliotech.Controller.LoginController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginView extends View {
    static final String nome = "Login";
    private static LoginView instance;
    private JPanel logoPanel;
    private JPanel contentPanel; //panel che contiene tutti i componenti della view
    private JLabel logoLabel;
    private JPanel rootPanel;
    private JTextField usernameField;
    private JPasswordField confermaPasswordField;
    private JPasswordField passwordField;
    private JCheckBox mostraPasswordCheckBox;
    private JButton loginButton;
    private JPanel fieldsPanel;
    private JButton annullaButton;
    private JLabel registratiLabel;
    private JLabel usernameLabel;

    LoginView() {
        super(nome);
        logoPanel.setSize(360, 250);
        this.setContentPane(rootPanel);
        logoLabel.setIcon(logoLabelIcon);


        loginButton.addActionListener(e -> {
            LoginController.getInstance().login(usernameField, passwordField);
            //SearchView.getInstance().checkPermessi();
        });
        registratiLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                LoginController.getInstance().switchView(RegisterView.getInstance(), LoginView.getInstance());
            }
        });
        registratiLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                registratiLabel.setBorder(BorderFactory.createLineBorder(Color.decode("#F39524"), 1));
            }
        });
        registratiLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                registratiLabel.setBorder(null);
            }
        });
        mostraPasswordCheckBox.addItemListener(e -> {
            if (mostraPasswordCheckBox.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('•');
            }
        });
        annullaButton.addActionListener(e -> {
            usernameField.setText("");
            passwordField.setText("");
        });
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginButton.doClick();
                }
            }
        });
    }

    public static LoginView getInstance() {
        if (instance == null) {
            instance = new LoginView();
        }
        return instance;
    }

    public static void resetFields() {
        instance.usernameField.setText("");
        instance.passwordField.setText("");
        instance.mostraPasswordCheckBox.setSelected(false);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return String.valueOf(passwordField.getPassword());
    }

    public void refreshFields() {
        mostraPasswordCheckBox.setSelected(false);
        usernameField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        passwordField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
    }
}
