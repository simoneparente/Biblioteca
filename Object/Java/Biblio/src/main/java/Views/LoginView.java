package Views;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LoginView extends Finestre{
    public JFrame loginFrame;
    private JPanel rootPanel;
    private JPanel userPanel;
    private JTextField usernameField;
    private JPasswordField passwordField1;
    private JButton annullaButton;
    private JButton okButton;
    private JButton registerNowButton;
    private JLabel imageLabel;
    private JPanel imagePanel;
    private JButton ok;

    public LoginView(){
        //ImageIcon logoIcon;
        //BufferedImage bigLogo;
        //try{
        //    bigLogo= ImageIO.read(new File("src/main/Immagini/BIBLIOTECH_NERO_E_ARANCIONE.jpg"));
        //    Image logoResized = bigLogo.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(),
        //            Image.SCALE_SMOOTH);
        //    logoIcon = new ImageIcon(logoResized);
        //}
        //catch (IOException e){
        //    e.printStackTrace();
        //    System.out.println("Errore caricamento immagine");
        //    logoIcon=null;
        //}

        imageLabel.setIcon(logoIcon);
        //System.out.println(imageLabel.getSize());
        //Image imageLogo= logoIcon.getImage();
        //Image newImageLogo= imageLogo.getScaledInstance(120, 120, Image.SCALE_DEFAULT);
        //logoIcon= new ImageIcon(imageLogo);
        imagePanel.setSize(360, 250);
        super.nome="Login";
        loginFrame= new_Finestra(nome, rootPanel);
        //Login->Register Button
        registerNowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent goRegister) {
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

}
