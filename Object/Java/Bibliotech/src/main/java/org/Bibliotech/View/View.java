package org.Bibliotech.View;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame{
    final ImageIcon logoLabelIcon = new ImageIcon("src/main/Immagini/logoIcon.png");
    final Taskbar taskbar = Taskbar.getTaskbar();  //TaskBar per mac
    final static Toolkit defaultToolkit = Toolkit.getDefaultToolkit(); //TaskBar per mac
    private final static Image imageIconaFinestre = defaultToolkit.getImage("src/main/Immagini/iconaFinestre.png");
    private static final ImageIcon iconaFinestre = new ImageIcon(imageIconaFinestre);
    private JPanel rootPanel;
    private JPanel logoPanel;
    private JPanel contentPanel;
    private JLabel logoLabel;
    View(String nome){
        FlatDarkLaf.setup();
        //this.setName("Bibliotech"); cosa fai?
        this.setTitle(nome); //nome visualizzato sul JFrame
        this.setIconImage(iconaFinestre.getImage()); //icona del JFrame
        this.setSize(720,480); //dimensione del JFrame
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try{
            taskbar.setIconImage(imageIconaFinestre);
        } catch (final UnsupportedOperationException u){
        } catch (final SecurityException e){
            System.out.printf("Eccezione TaskBar");
        }
    }
    public void showView(){
        this.setVisible(true);
    }
}
