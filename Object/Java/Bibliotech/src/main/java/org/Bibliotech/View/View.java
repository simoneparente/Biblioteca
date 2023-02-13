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
        this.setTitle(nome); //nome visualizzato sul JFrame
        this.setIconImage(iconaFinestre.getImage()); //icona del JFrame
        this.setSize(720,560); //dimensione del JFrame
        this.setVisible(false);
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
    public void hideView(){
        this.setVisible(false);
    }

    public void adjustSearchPosition() {
        //this.setLocation(this.getX() + (ResultView.getInstance().getWidth())/2, this.getY());
        SearchView.getInstance().setLocationRelativeTo(null);
        int searchViewX = SearchView.getInstance().getX(); //ottiene la posizione della SearchView
        int searchViewY = SearchView.getInstance().getY(); //ottiene la posizione della SearchView
        int searchViewW = SearchView.getInstance().getWidth(); //ottiene la larghezza della SearchView
        SearchView.getInstance().setLocation(searchViewX - searchViewW / 2, searchViewY); //centra la SearchView rispetto alla ResultView
        ResultView.getInstance().setLocation(SearchView.getInstance().getX() + searchViewW, searchViewY); //centra la ResultView rispetto alla SearchView
    }

}
