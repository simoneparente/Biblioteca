package org.Bibliotech.View;

import javax.swing.*;

public class SearchViewRiviste extends View {
    private static SearchViewRiviste instance;
    private static final String nome="Search";
    private JPanel rootPanel;
    private JPanel logoPanel;
    private JLabel logoLabel;
    private JPanel contentPanel;
    private JPanel filtriPanel;
    private JTextField searchField;
    private JButton searchButton;
    private JButton filtriButton;
    private JCheckBox argomentoCheckBox;
    private JCheckBox linguaCheckBox;
    private JComboBox argomentoComboBox;
    private JComboBox linguaComboBox;
    private JTextField dataDaField;
    private JTextField dataAField;
    private JComboBox formatoComboBox;
    private JCheckBox dataPubblicazioneCheckBox;
    private JCheckBox formatoCheckBox;
    private JPanel generePanel;
    private JPanel linguaPanel;
    private JPanel dataPubblicazionePanel;
    private JPanel formatoPanel;
    private JComboBox filtroRisorseComboBox;
    private JCheckBox prezzoCheckBox;
    private JPanel prezzoPanel;
    private JTextField minPrezzoField;
    private JTextField maxPrezzoField;

    SearchViewRiviste(){
        super(nome);
        super.setSize(720, 640);
        logoPanel.setSize(360, 250);
        this.setContentPane(rootPanel); //setta il contentPanel come contentPane del JFrame
        filtriPanel.setVisible(false);
        logoLabel.setIcon(logoLabelIcon); //setta l'icona del logo (logoLabelIcon viene presa da superclasse View)
    }

    public static SearchViewRiviste getInstance(){
        if(instance == null){
            instance = new SearchViewRiviste();
        }
        return instance;
    }
    public static void main(String[] args){
        SearchViewRiviste svl = getInstance();
    }
}
