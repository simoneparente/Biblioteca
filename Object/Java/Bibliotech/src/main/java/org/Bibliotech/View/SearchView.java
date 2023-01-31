package org.Bibliotech.View;

import javax.swing.*;

public class SearchView extends View{
    private final static String nome="Search";

    private static SearchView instance;
    private JPanel rootPanel;
    private JPanel logoPanel;
    private JLabel logoLabel;
    private JPanel contentPanel;
    private JTextField searchField;
    private JButton cercaButton;
    private JButton filtriButton;
    private JPanel filtriPanel;
    private JComboBox autoreComboBox;

    SearchView(){
        super(nome);
        logoPanel.setSize(360, 250);
        this.setContentPane(rootPanel);
        logoLabel.setIcon(logoLabelIcon);
    }
    public static void main(String[] args){
        SearchView sv = new SearchView();
    }

}
