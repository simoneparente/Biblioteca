package Views;

import com.sun.tools.javac.Main;

import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;
import java.io.File;
import java.net.URL;

public class Finestre extends JFrame {
    final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
    final Image image = defaultToolkit.getImage("src/main/Immagini/iconaFinestre.png"   );
    final Taskbar taskbar = Taskbar.getTaskbar();
    String nome;
    //ImageIcon icona = new ImageIcon("src/Immagini/icon_bianco_arancione.png");
    ImageIcon iconaFinestre = new ImageIcon(image);
    JPanel rootPanel;
    ImageIcon logoIcon= new ImageIcon("src/main/Immagini/logoIcon.png");

    //Crea nuova finestra (chiamare con NomeClasseFiglia.new_Finestra("NomeObject.nome", nomeObject.panelEsterno))
    public JFrame new_Finestra(String nome_in, JPanel rootPanel_in)
    {
        nome = nome_in;
        rootPanel= rootPanel_in;
        this.setTitle(nome);
        this.setIconImage(iconaFinestre.getImage());
        try{
            taskbar.setIconImage(image);
        } catch (final UnsupportedOperationException u){
        } catch (final SecurityException e){
            System.out.printf("Eccezzione TaskBar");
        }
        this.setSize(375, 480);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
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
