package org.Bibliotech.View;

import javax.swing.*;

public class SearchViewArticoli extends View {
    private static SearchViewArticoli instance;
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
    private JCheckBox disciplinaCheckBox;
    private JCheckBox linguaCheckBox;
    private JComboBox autoreComboBox;
    private JComboBox editoreComboBox;
    private JComboBox disciplinaComboBox;
    private JComboBox linguaComboBox;
    private JTextField dataDaField;
    private JTextField dataAField;
    private JComboBox formatoComboBox;
    private JComboBox maxPrezzoField;
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
    private JCheckBox conferenzaCheckBox;
    private JComboBox conferenzaComboBox;

    SearchViewArticoli(){
        super(nome);
        super.setSize(720, 640);
        logoPanel.setSize(360, 250);
        this.setContentPane(rootPanel); //setta il contentPanel come contentPane del JFrame
        filtriPanel.setVisible(false);
        logoLabel.setIcon(logoLabelIcon); //setta l'icona del logo (logoLabelIcon viene presa da superclasse View)
    }

    public static SearchViewArticoli getInstance(){
        if(instance == null){
            instance = new SearchViewArticoli();
        }
        return instance;
    }
    public static void main(String[] args){
        SearchViewArticoli svl = getInstance();
    }
    public String getViewName(){
        return nome;
    }

}
