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
    protected JLabel imageLabel;
    protected JPanel outerPanel;
    protected JPanel dataPanel;
    protected JPanel imagePanel;
    protected JPasswordField confermaPasswordField;
    protected JLabel confermaPasswordLabel;
    protected JButton annullaButton;
    protected JButton registratiButton;


    public RegisterView(){
        //FlatIntelliJLaf.setup();
        imageLabel.setIcon(logoIcon);
        registerFrame= new_Finestra("Register", outerPanel);
        confermaPasswordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    //==click registrati;
                    System.out.println("Enter pressed");
                }
                else {
                super.keyPressed(e);
                close_Finestra();
            }
        }
        });
        registratiButton.addMouseListener(new MouseAdapter() {
        });
        //Bottone che porta alla login page
        annullaButton.addMouseListener(new MouseAdapter() { //go to login page
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                LoginView loginView = new LoginView();
                loginView.show_Finestra();
                close_Finestra();
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
