package org.Bibliotech.View;

import org.Bibliotech.Controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchViewSerie extends View {
    private static SearchViewSerie instance;
    private static final String nome="Search";
    private JPanel rootPanel;
    private JPanel logoPanel;
    private JLabel logoLabel;
    private JPanel contentPanel;
    private JPanel filtriPanel;
    private JTextField searchField;
    private JButton searchButton;
    private JButton filtriButton;
    private JCheckBox editoreCheckBox;
    private JCheckBox linguaCheckBox;
    private JComboBox editoreComboBox;
    private JComboBox linguaComboBox;
    private JTextField dataDaField;
    private JTextField dataAField;
    private JComboBox formatoComboBox;
    private JCheckBox dataPubblicazioneCheckBox;
    private JCheckBox formatoCheckBox;
    private JPanel editorePanel;
    private JPanel linguaPanel;
    private JPanel dataPubblicazionePanel;
    private JPanel formatoPanel;
    private JComboBox filtroRisorseComboBox;

    SearchViewSerie(){
        super(nome);
        super.setSize(720, 640);
        logoPanel.setSize(360, 250);
        this.setContentPane(rootPanel); //setta il contentPanel come contentPane del JFrame
        filtriPanel.setVisible(true);
        logoLabel.setIcon(logoLabelIcon); //setta l'icona del logo (logoLabelIcon viene presa da superclasse View)
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static SearchViewSerie getInstance(){
        if(instance == null){
            instance = new SearchViewSerie();
        }
        return instance;
    }
}
