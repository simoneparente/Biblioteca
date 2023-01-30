package org.Bibliotech.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.event.*;
import java.util.ArrayList;
import org.Bibliotech.Controller.FiltriController;
import static org.Bibliotech.Main.mc;

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
    private JComboBox reasourceSelectorBox;
    private JComboBox autoreComboBox;
    private JCheckBox linguaCheckBox;
    private JComboBox linguaComboBox;
    private JPanel resultPanel;
    private JTable resultTable;
    private JCheckBox editoreCheckBox;
    private JComboBox editoreComboBox;
    private JScrollPane resultPane;

    private FiltriController fc;
    
    public SearchView(){
        fc = new FiltriController();
        setupFields("libro");
        ImageIcon glassIconImage = new ImageIcon("src/main/Immagini/glassIcon.png");
        imageLabel.setIcon(logoIcon);
        searchButton.setIcon(glassIconImage);
        imagePanel.setSize(720,240);
        filtriPanel.setVisible(false);
        resultPane.setVisible(true);
        resultTable.setVisible(true);
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
            public void actionPerformed(ActionEvent e) { //da modificare
                mc.switchGUI(mc.getResultView().getName(), "");
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
                autoreComboBox.setEnabled(autoreCheckBox.isSelected());
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
        reasourceSelectorBox.addActionListener(new ActionListener() { //selettore risorsa da cercare
            @Override
            public void actionPerformed(ActionEvent e) {
                String risorsaSelezionata = reasourceSelectorBox.getSelectedItem().toString();
                switch (risorsaSelezionata) {
                    case "Libro":
                        break;
                    case "Aritcolo":
                        break;
                }
            }
        });
        editoreCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                editoreComboBox.setEnabled(editoreCheckBox.isSelected());
            }
        });
        reasourceSelectorBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String risorsaSelezionata = reasourceSelectorBox.getSelectedItem().toString();
            }
        });
    }
    private void fillFilters(String risorsaSelezionata){
        ArrayList<String> autori = fc.leggiAutoriLibri();
        ArrayList<String> lingue = fc.leggiLingueLibri();
        ArrayList<String> generi = fc.leggiGeneriLibri();
        ArrayList<String> formati = fc.leggiFormatiLibri();
        ArrayList<String> editori = fc.leggiEditoriLibri();

        for (String nome : autori) {
            autoreComboBox.addItem(nome);
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
    }

    private void disableFields(String risorsaSelezionata){
        autoreComboBox.setEnabled(false);
        linguaComboBox.setEnabled(false);
        genereComboBox.setEnabled(false);
        formatoBox.setEnabled(false);
        minprezzoBox.setEnabled(false);
        maxprezzoBox.setEnabled(false);
        minDataP.setEnabled(false);
        maxDataP.setEnabled(false);
        editoreComboBox.setEnabled(false);
    }

    private void setupFields(String RisorsaSelezionata) {
        disableFields(RisorsaSelezionata);
        fillFilters(RisorsaSelezionata);
    }
}
