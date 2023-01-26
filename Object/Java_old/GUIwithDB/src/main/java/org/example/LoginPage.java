package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoginPage implements ActionListener {
    JFrame 
            frame = new JFrame();

    String path="src\\main\\resources\\icona.jpg";

    JButton loginButton = new JButton("Login");
    JButton resetButton = new JButton("Reset");
    JTextField userIDField = new JTextField();
    JPasswordField userPassField = new JPasswordField();
    JLabel userIDLabel = new JLabel("user ID:");
    JLabel userPassLabel = new JLabel("Password:");
    JLabel messageLabel = new JLabel();

    ArrayList<Utente> logininfo = new ArrayList<Utente>();

    LoginPage(ArrayList<Utente> loginInfoOriginal) {
        logininfo = loginInfoOriginal;

        userIDLabel.setBounds(50, 100, 200, 25);
        userPassLabel.setBounds(50, 150, 200, 25);

        messageLabel.setBounds(125, 250, 250, 35);
        messageLabel.setFont(new Font(null, Font.ITALIC, 25));

        userIDField.setBounds(125, 100, 200, 25);
        userPassField.setBounds(125, 150, 200, 25);

        loginButton.setBounds(125, 200, 100, 25);
        loginButton.setFocusable(false);
        loginButton.addActionListener(this);

        resetButton.setBounds(225, 200, 100, 25);
        resetButton.setFocusable(false);
        resetButton.addActionListener(this);

        frame.add(userIDLabel);
        frame.add(userPassLabel);
        frame.add(messageLabel);
        frame.add(userIDField);
        frame.add(userPassField);
        frame.add(loginButton);
        frame.add(resetButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(new ImageIcon(path).getImage());
        frame.setSize(500, 500);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetButton) {
            userIDField.setText("");
            userPassField.setText("");
        }
        if (e.getSource() == loginButton) {

            String userID = userIDField.getText();
            String password = String.valueOf(userPassField.getPassword());
            /*
            for(Utente utente: logininfo)
            {
                if(utente.getUsername().equals(userID))
                {
                    if(utente.getPassword().equals(password))
                    {
                        messageLabel.setForeground(Color.green);
                        messageLabel.setText("Login Riuscito");
                        frame.dispose();
                        WelcomePage welcomepage = new WelcomePage(userID);
                    }
                    if(!utente.getPassword().equals(password) && utente.getUsername().equals(userID))
                    {

                    }

                }
                if(!utente.getUsername().equals(userID))
                {
                    messageLabel.setForeground(Color.red);
                    messageLabel.setText("username non trovato");
                }
            }
             */
            for (Utente x : logininfo) {
                if(userID.equals(x.getUsername())){
                    messageLabel.setForeground(Color.green);
                    messageLabel.setText("Username trovato");
                    if(password.equals(x.getPassword())){
                        messageLabel.setForeground(Color.green);
                        messageLabel.setText("Login Riuscito");
                        frame.dispose();
                        WelcomePage welcomepage = new WelcomePage(userID);
                        break;
                    }else{
                        messageLabel.setForeground(Color.red);
                        messageLabel.setText("Password errata per questo username");
                        break;
                    }
                }else{
                    messageLabel.setForeground(Color.red);
                    messageLabel.setText("Username non trovato");
                }
            }


        }
    }
}
