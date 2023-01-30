package org.Bibliotech.View;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.event.*;
import java.util.ArrayList;
import org.Bibliotech.Controller.FiltriController;

public class SearchView extends GeneralView{
    private JPanel rootPanel;
    private JPanel imagePanel;
    private JPanel searchPanel;
    private JLabel imageLabel;
    private JTextField searchField;
    private JButton filtriButton;
    private JButton searchButton;
    private JPanel filtriPanel;
    private JCheckBox autoreCheckBox;
    private JCheckBox genereCheckBox;
    private JCheckBox dataDiPubblicazioneCheckBox;
    private JCheckBox formatoCheckBox;
    private JCheckBox prezzoCheckBox;
    private JComboBox genereComboBox; //potrebbe diventare JComboBox, serve una query che va a prendere distinct generi da db
    private JComboBox formatoBox;
    private JTextField minprezzoBox;
    private JTextField maxprezzoBox;
    private JTextField maxDataP;
    private JTextField minDataP;
    private JComboBox ReasourceSelectorBox;
    private JComboBox autoreNomeComboBox;
    private JComboBox autoreCognomeComboBox;
    private JCheckBox linguaCheckBox;
    private JComboBox linguaComboBox;
    private JPanel resultPanel;
    private JTable resultTable;
    private JCheckBox editoreCheckBox;
    private JComboBox editoreComboBox;

    private FiltriController fc;
    
    public SearchView(){
        fc = new FiltriController();
        setFields(false);
        ImageIcon glassIconImage = new ImageIcon("src/main/Immagini/glassIcon.png");
        imageLabel.setIcon(logoIcon);
        searchButton.setIcon(glassIconImage);
        imagePanel.setSize(720,240);
        filtriPanel.setVisible(false);
        resultPanel.setVisible(false);
        JFrame frame = newView("Search", rootPanel);
        this.setSize(720, 560);


        minprezzoBox.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(!minprezzoBox.getText().chars().allMatch(Character::isDigit)){
                    minprezzoBox.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(minprezzoBox.getText().isBlank()){
                    minprezzoBox.setText("Minimo");
                }
            }
        });
        maxprezzoBox.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(!maxprezzoBox.getText().chars().allMatch(Character::isDigit)){
                    maxprezzoBox.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(maxprezzoBox.getText().isBlank()){
                    maxprezzoBox.setText("Massimo");
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            //if(searchField.getText().isBlank()){
            //    JOptionPane.showMessageDialog(null, "Inserire un testo di ricerca");
            //}
            //else{
                TableColumn column = new TableColumn();
                column.setHeaderValue("Titolo");
                resultPanel.setVisible(true);
                resultTable.addColumn(column);
                resultTable.setValueAt("Titolo", 0, 0);
            //}
            }
        });

        filtriButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtriPanel.setVisible(!filtriPanel.isShowing());
            }
        });

        autoreCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                autoreNomeComboBox.setEnabled(autoreCheckBox.isSelected());
                autoreCognomeComboBox.setEnabled(autoreCheckBox.isSelected());
            }
        });
        genereCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                genereComboBox.setEnabled(genereCheckBox.isSelected());
            }
        });
        dataDiPubblicazioneCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                minDataP.setEnabled(dataDiPubblicazioneCheckBox.isSelected());
                maxDataP.setEnabled(dataDiPubblicazioneCheckBox.isSelected());
            }
        });
        formatoCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                formatoBox.setEnabled(formatoCheckBox.isSelected());
            }
        });
        prezzoCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                minprezzoBox.setEnabled(prezzoCheckBox.isSelected());
                maxprezzoBox.setEnabled(prezzoCheckBox.isSelected());
            }
        });

        linguaCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                linguaComboBox.setEnabled(linguaCheckBox.isSelected());
            }
        });
    }

    private void setFields(boolean mode) {
        ArrayList<String> nomiAutori = fc.leggiNomeAutori();
        ArrayList<String> cognomiAutori = fc.leggiCognomeAutori();
        ArrayList<String> lingue = fc.leggiLingue();
        ArrayList<String> generi = fc.leggiGeneri();
        ArrayList<String> formati = fc.leggiFormati();
        ArrayList<String> editori = fc.leggiEditori();

        for (String nome : nomiAutori) {
            autoreNomeComboBox.addItem(nome);
        }
        for (String cognome : cognomiAutori) {
            autoreCognomeComboBox.addItem(cognome);
        }
        for (String lingua : lingue) {
            linguaComboBox.addItem(lingua);
        }
        for (String genere : generi) {
            genereComboBox.addItem(genere);
        }
        for (String formato : formati) {
            formatoBox.addItem(formato);
        }
        for (String editore : editori) {
            editoreComboBox.addItem(editore);
        }

        autoreNomeComboBox.setEnabled(mode);
        autoreCognomeComboBox.setEnabled(mode);
        linguaComboBox.setEnabled(mode);
        genereComboBox.setEnabled(mode);
        formatoBox.setEnabled(mode);
        minprezzoBox.setEnabled(mode);
        maxprezzoBox.setEnabled(mode);
        minDataP.setEnabled(mode);
        maxDataP.setEnabled(mode);
    }
}
