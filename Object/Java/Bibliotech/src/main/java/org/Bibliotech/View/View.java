package org.Bibliotech.View;


import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    final static Toolkit defaultToolkit = Toolkit.getDefaultToolkit(); //TaskBar per mac
    private final static Image imageIconaFinestre = defaultToolkit.getImage("src/main/resources/iconaFinestre.png");
    private static final ImageIcon iconaFinestre = new ImageIcon(imageIconaFinestre);
    final ImageIcon logoLabelIcon = new ImageIcon("src/main/resources/logoIcon.png");
    final Taskbar taskbar = Taskbar.getTaskbar();  //TaskBar per mac
    private JPanel rootPanel;
    private JPanel logoPanel;
    private JPanel contentPanel;
    private JLabel logoLabel;


    View(String nome) {
        FlatDarkLaf.setup();
        this.setTitle(nome); //nome visualizzato sul JFrame
        this.setIconImage(iconaFinestre.getImage()); //icona del JFrame
        this.setSize(720, 720); //dimensione del JFrame
        this.setVisible(false);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            taskbar.setIconImage(imageIconaFinestre);
        } catch (final UnsupportedOperationException u) {
            System.out.print("Eccezione TaskBar - UnsupportedOperationException");
        } catch (final SecurityException e) {
            System.out.print("Eccezione TaskBar - SecurityException");
        }
    }

    public void showView() {
        this.setVisible(true);
    }

    public void hideView() {
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
