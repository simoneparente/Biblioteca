package Views;

//import com.formdev.flatlaf.*;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegisterView extends Finestre{
    JFrame registerFrame;
    protected JTextField usernameField;//
    protected JLabel usernameLabel;
    protected JPasswordField passwordField;
    protected JLabel passwordLabel;
    protected JLabel logoLabel;
    protected JPanel outerPanel;
    protected JPanel dataPanel;
    protected JPanel logoPanel;
    protected JPasswordField confermaPasswordField;
    protected JLabel confermaPasswordLabel;
    protected JButton annullaButton;
    protected JButton registratiButton;
    private JButton homeButton;

    public RegisterView(){
        //FlatIntelliJLaf.setup();
        logoLabel.setIcon(new ImageIcon("src/Immagini/logo.png"));
        registerFrame = new JFrame("Register");
        registerFrame.setBounds(100, 100, 360, 480);
        registerFrame.setResizable(false);
        registerFrame.setContentPane(outerPanel);
        registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registerFrame.setVisible(false);
        homeButton.setIcon(super.icona);
        homeButton.setMaximumSize(new java.awt.Dimension(10,10));
        confermaPasswordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    //==click registrati;
                    System.out.println("Enter pressed");
                }
                else {
                super.keyPressed(e);
            }
        }
        });
        registratiButton.addMouseListener(new MouseAdapter() {
        });
        //Bottone che porta alla login page
        homeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                LoginView loginView = new LoginView();
                loginView.getFrame().setVisible(true);
                RegisterView.super.close_Finestra();
            }
        });
    }

    public JFrame getFrame() {
        if(registerFrame != null)
            return registerFrame;
        else
            System.out.println("Register frame is null");
        return null;
    }
}
