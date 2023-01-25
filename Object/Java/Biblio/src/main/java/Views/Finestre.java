package Views;

import javax.swing.*;

public class Finestre extends JFrame {
    String nome;
    ImageIcon icona = new ImageIcon("src/Immagini/icona.png");
    JPanel rootPanel;
    //Crea nuova finestra (chiamare con NomeClasseFiglia.new_Finestra("NomeObject.nome", nomeObject.panelEsterno))
    public JFrame new_Finestra(String nome_in, JPanel rootPanel_in)
    {
        nome = nome_in;
        rootPanel= rootPanel_in;
        this.setTitle(nome);
        this.setIconImage(icona.getImage());
        this.setSize(360, 480);
        this.setContentPane(rootPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return this;
    }

    //Mostra la finestra (chiamare con NomeClasseFiglia.show_Finestra())
    public void show_Finestra()
    {
        this.setVisible(true);
    }
    //Chiude la finestra (chiamare con NomeClasseFiglia.close_Finestra())
    public void close_Finestra()
    {
        this.setVisible(false);
    }
}
