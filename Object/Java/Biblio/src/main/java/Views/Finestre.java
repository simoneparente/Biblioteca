package Views;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Finestre extends JFrame {
    String nome;
    //ImageIcon icona = new ImageIcon("src/Immagini/icon_bianco_arancione.png");
    ImageIcon iconaFinestre = new ImageIcon("src/main/Immagini/iconaFinestre.png");
    JPanel rootPanel;

    ImageIcon logoIcon= new ImageIcon("src/main/Immagini/logoIcon.png");
    //Crea nuova finestra (chiamare con NomeClasseFiglia.new_Finestra("NomeObject.nome", nomeObject.panelEsterno))
    public JFrame new_Finestra(String nome_in, JPanel rootPanel_in)
    {
        nome = nome_in;
        rootPanel= rootPanel_in;
        this.setTitle(nome);
        this.setIconImage(iconaFinestre.getImage());
        this.setSize(375, 480);
        //this.setResizable(false);
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
    public JFrame getFrame(){
        return this;
    }
    public JPanel getRootPanel(){
        return rootPanel;
    }
}
