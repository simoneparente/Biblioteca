package org.Bibliotech.View;

import javax.swing.*;
import java.awt.*;

public class GeneralView extends JFrame {
    String nome;
    JPanel rootPanel;

    //TaskBar
    final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
    final Image image = defaultToolkit.getImage("src/main/Immagini/iconaFinestre.png"   );
    final Taskbar taskbar = Taskbar.getTaskbar();

    ImageIcon iconaFinestre = new ImageIcon(image);
    ImageIcon logoIcon= new ImageIcon("src/main/Immagini/logoIcon.png");

    public JFrame newView(String nome, JPanel rootPanel){
        this.nome = nome;
        this.setTitle(nome);
        this.setIconImage(iconaFinestre.getImage());
        try{
            taskbar.setIconImage(image);
        } catch (final UnsupportedOperationException u){
        } catch (final SecurityException e){
            System.out.printf("Eccezione TaskBar");
        }
        this.setSize(375, 480);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setContentPane(rootPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        return this;
    }

    public String getName() {
        return this.nome;
    }
    public void showView(){
        this.setVisible(true);
    }
    public void hideView(){
        this.setVisible(false);
    }
}
