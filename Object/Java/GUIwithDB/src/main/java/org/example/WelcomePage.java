package org.example;

import javax.swing.*;
import java.awt.*;

public class WelcomePage {
    JFrame frame = new JFrame();
    JLabel welcomeLabel = new JLabel("Hello!");

    String path="src\\main\\resources\\icona.jpg";
    //ImageIcon icona = new ImageIcon("src\\main\\resources\\icona.jpg");

    WelcomePage(String userID){
        welcomeLabel.setBounds(0,0,200,35);
        welcomeLabel.setFont(new Font(null,Font.PLAIN,25));
        welcomeLabel.setText("Hello "+ userID);
        welcomeLabel.setVisible(true);

        frame.add(welcomeLabel);
        frame.setIconImage(new ImageIcon(path).getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
