package org.Bibliotech.View;

import org.Bibliotech.Controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchViewLibri extends View{
    private static SearchViewLibri instance;
    private static final String nome="Search";
    private JPanel rootPanel;
    private JPanel logoPanel;
    private JLabel logoLabel;
    private JPanel contentPanel;
    private JPanel filtriPanel;
    private JTextField searchField;
    private JButton searchButton;
    private JButton filtriButton;
    private JCheckBox autoreCheckBox;
    private JCheckBox editoreCheckBox;
    private JCheckBox genereCheckBox;
    private JCheckBox linguaCheckBox;
    private JComboBox autoreComboBox;
    private JComboBox editoreComboBox;
    private JComboBox genereComboBox;
    private JComboBox linguaComboBox;
    private JTextField dataDaField;
    private JTextField dataAField;
    private JComboBox formatoComboBox;
    private JTextField minPrezzoField;
    private JTextField maxPrezzoField;
    private JCheckBox dataPubblicazioneCheckBox;
    private JCheckBox formatoCheckBox;
    private JCheckBox prezzoCheckBox;
    private JPanel autorePanel;
    private JPanel editorePanel;
    private JPanel generePanel;
    private JPanel linguaPanel;
    private JPanel dataPubblicazionePanel;
    private JPanel formatoPanel;
    private JPanel prezzoPanel;
    private JComboBox filtroRisorseComboBox;
    private JCheckBox serieCheckBox;
    private JComboBox serieComboBox;

    SearchViewLibri(){
        super(nome);
        super.setVisible(false);
        super.setSize(720, 640);
        logoPanel.setSize(360, 250);
        this.setContentPane(rootPanel); //setta il contentPanel come contentPane del JFrame
        filtriPanel.setVisible(true);
        logoLabel.setIcon(logoLabelIcon); //setta l'icona del logo (logoLabelIcon viene presa da superclasse View)
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getInstance().switchView(ResultView.getInstance(), null);

            }
        });
    }

    public static SearchViewLibri getInstance(){
        if(instance == null){
            instance = new SearchViewLibri();
        }
        return instance;
    }
    public static void main(String[] args){
        SearchViewLibri svl = getInstance();
    }
}
