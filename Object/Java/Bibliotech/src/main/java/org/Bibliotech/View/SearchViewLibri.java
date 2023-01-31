package org.Bibliotech.View;

import javax.swing.*;

public class SearchViewLibri extends SearchView{
    private JPanel rootPanel;
    private JPanel filtriPanel;
    private JCheckBox autoreCheckBox;
    private JCheckBox prezzoCheckBox;
    private JCheckBox linguaCheckBox;
    private JCheckBox genereCheckBox;
    private JCheckBox dataPubblicazioneCheckBox;
    private JCheckBox formatoCheckBox;
    private JCheckBox editoreCheckBox;
    private JPanel autorePanel;
    private JComboBox autoreComboBox;
    private JComboBox linguaComboBox;
    private JPanel linguaPanel;
    private JComboBox genereComboBox;
    private JPanel generePanel;
    private JPanel dataPanel;
    private JPanel formatoPanel;
    private JPanel EditorePanel;
    private JPanel prezzoPanel;
    private JComboBox dataComboBox;
    private JComboBox formatoComboBox;
    private JComboBox editoreComboBox;
    private JComboBox prezzoComboBox;

    SearchViewLibri(){
        super();
        this.setContentPane(rootPanel);
        this.setVisible(true);
    }

    public static void main(String[] args){
        SearchViewLibri svl = new SearchViewLibri();
    }
}
