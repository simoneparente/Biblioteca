package GUI;

import javax.swing.*;

public class LoginPage extends JFrame{
    String frameIconPath="src/main/java/GUI/Images/icona.jpg";
    String frameLogoPath="C:\\Università\\Corsi\\Basi_di_Dati\\GitHub\\Biblioteca\\Object\\Java\\untitled\\src\\main\\java\\Images\\logo.png";
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPanel loginPanel;
    private JLabel immagine;
    private JPanel borderPanel;
    private JPanel imagePanel;


    public LoginPage(){
            super("Login");
        try {
            immagine.setIcon(new ImageIcon("C:\\Università\\Corsi\\Basi_di_Dati\\GitHub\\Biblioteca\\Object\\Java\\untitled1\\src\\main\\java\\Images\\logo.png"));
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setContentPane(borderPanel);

            this.setSize(900, 900);
            this.setLocationRelativeTo(null);
            this.setVisible(true);
            //this.setIconImage(new ImageIcon(frameLogoPath).getImage());
            this.pack();

        }
       catch (NullPointerException e){
            System.out.println("Errore: "+e);
        }
    }

    public static void main(String[] args) {
        LoginPage  p=new LoginPage();
    }


}
