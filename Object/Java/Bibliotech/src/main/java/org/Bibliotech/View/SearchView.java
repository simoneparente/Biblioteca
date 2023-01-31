package org.Bibliotech.View;

import javax.swing.*;

public class SearchView extends View{
    private final static String nome="Search";

    private static SearchView instance;
    private JPanel rootPanel;
    private JPanel logoPanel;
    private JLabel logoLabel;
    private JPanel contentPanel;
    SearchView(){
        super(nome);
        logoPanel.setSize(360, 250);
        this.setContentPane(rootPanel);
        logoLabel.setIcon(logoLabelIcon);
    }

    public static SearchView getInstance(){
        if(instance == null){
            instance = new SearchView();
        }
        return instance;
    }

    public static void main(String[] args){
        getInstance();
    }

}
