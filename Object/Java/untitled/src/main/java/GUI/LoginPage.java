package GUI;

import javax.swing.*;
import java.awt.*;

public class LoginPage {
    String frameIconPath="src/main/java/GUI/Images/icona.jpg";
    String frameLogoPath="src/main/java/GUI/Images/logo.jpg";
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPanel loginPanel;
    private JLabel logo;

    public LoginPage(){
        JFrame frame = new JFrame("Login");
        frame.setContentPane(loginPanel);
        frame.setBounds(100,100,360,360);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(new ImageIcon(frameIconPath).getImage());
        frame.setVisible(true);
        logo.setVisible(true);
        frame.add(logo);

    }

    public static void main(String[] args) {
        new LoginPage();
    }

}
