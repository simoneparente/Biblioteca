package org.Bibliotech.View;

import org.Bibliotech.Controller.Controller;

import javax.swing.*;
import javax.xml.transform.Result;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class SearchView extends View {
    private static SearchView instance = null;
    private static final String nome = "Search";
    private JPanel rootPanel;
    private JPanel logoPanel;
    private JLabel logoLabel;
    private JPanel filtriLibriPanel;
    private JCheckBox autoreLibroCheckBox;
    private JCheckBox editoreLibroCheckBox;
    private JCheckBox genereLibroCheckBox;
    private JCheckBox linguaLibroCheckBox;
    private JCheckBox dataPubblicazioneLibroCheckBox;
    private JCheckBox formatoLibroCheckBox;
    private JCheckBox prezzoLibroCheckBox;
    private JPanel autoreLibroPanel;
    private JComboBox autoreLibroComboBox;
    private JPanel editoreLibroPanel;
    private JComboBox editoreLibroComboBox;
    private JPanel genereLibroPanel;
    private JComboBox genereLibroComboBox;
    private JPanel linguaLibroPanel;
    private JComboBox linguaLibroComboBox;
    private JPanel dataPubblicazioneLibroPanel;
    private JTextField dataDaLibroField;
    private JTextField dataALibroField;
    private JPanel formatoLibroPanel;
    private JComboBox formatoLibroComboBox;
    private JPanel prezzoLibroPanel;
    private JTextField prezzoDaLibroField;
    private JTextField prezzoALibroField;
    private JCheckBox serieLibroCheckBox;
    private JComboBox serieLibroComboBox;
    private JPanel filtriArticoliPanel;
    private JCheckBox disciplinaArticoloCheckBox;
    private JComboBox disciplinaArticoloComboBox;
    private JCheckBox conferenzaArticoloCheckBox;
    private JComboBox conferenzaArticoloComboBox;
    private JPanel filtriRivistePanel;
    private JCheckBox argomentoRivisteCheckBox;
    private JComboBox argomentoRivisteComboBox;
    private JCheckBox autoreArticoloCheckBox;
    private JCheckBox editoreArticoloCheckBox;
    private JCheckBox linguaArticoloCheckBox;
    private JCheckBox dataPubblicazioneArticoloCheckBox;
    private JCheckBox formatoArticoloCheckBox;
    private JCheckBox rivistaArticoloCheckBox;
    private JPanel autoreArticoloPanel;
    private JPanel editoreArticoloPanel;
    private JPanel disciplinaArticoloPanel;
    private JPanel linguaArticoloPanel;
    private JPanel dataPubblicazioneArticoloPanel;
    private JPanel formatoArticoloPanel;
    private JPanel rivistaArticoloPanel;
    private JComboBox autoreArticoloComboBox;
    private JComboBox editoreArticoloComboBox;
    private JComboBox linguaArticoloComboBox;
    private JTextField dataPubblicazioneDaArticoloComboBox;
    private JTextField dataPubblicazioneAArticoloComboBox;
    private JComboBox formatoArticoloComboBox;
    private JComboBox rivistaArticoloComboBox;
    private JCheckBox linguaRivisteCheckBox;
    private JCheckBox dataPubblicazioneRivisteCheckBox;
    private JCheckBox formatoRivisteCheckBox;
    private JComboBox linguaRivisteComboBox;
    private JTextField dataPubblicazioneDaRivisteComboBox;
    private JTextField dataPubblicazioneARivisteComboBox;
    private JPanel argomentoRivistePanel;
    private JPanel linguaRivistePanel;
    private JCheckBox prezzoRivisteCheckBox;
    private JPanel formatoRivistePanel;
    private JPanel dataPubblicazioneRivistePanel;
    private JComboBox formatoRivisteComboBox;
    private JPanel prezzoRivistePanel;
    private JTextField prezzoDaRivisteField;
    private JTextField prezzoARivisteField;
    private JPanel searchPanel;
    private JTextField searchField;
    private JComboBox risorsaComboBox;
    private JCheckBox filtriCheckBox;
    private JButton searchButton;
    private JPanel filtriSeriePanel;
    private JCheckBox editoreSerieCheckBox;
    private JCheckBox linguaSerieCheckBox;
    private JCheckBox dataPubblicazioneSerieCheckBox;
    private JCheckBox formatoSerieCheckBox;
    private JPanel editoreSeriePanel;
    private JComboBox editoreSerieComboBox;
    private JPanel linguaSeriePanel;
    private JComboBox linguaSerieComboBox;
    private JPanel dataPubblicazioneSeriePanel;
    private JTextField dataDaSerieField;
    private JTextField dataASerieField;
    private JPanel formatoSeriePanel;
    private JComboBox formatoSerieComboBox;

    public SearchView() {
        super(nome);
        this.setVisible(true);
        this.setContentPane(rootPanel); //setta il contentPanel come contentPane del JFrame
        setFiltriInvisibili();
        setFieldsDisabled();

        logoLabel.setIcon(logoLabelIcon); //setta l'icona del logo (logoLabelIcon viene presa da superclasse View)

        filtriCheckBox.addActionListener(e -> {
            if (filtriCheckBox.isSelected()) {
                if (risorsaComboBox.getSelectedItem().equals("")) {
                    JOptionPane.showMessageDialog(null, "Selezionare una risorsa");
                    filtriCheckBox.setSelected(false);
                } else {
                    ricaricaFiltri(String.valueOf(risorsaComboBox.getSelectedItem()));
                }
            } else {
                setFiltriInvisibili();
            }
        });
        //LISTENER CHECKBOX FILTRI LIBRI
        //------------------------------------------------------------------------------------------
        //aggiunge un listener alla risorsaComboBox che, quando viene selezionata una risorsa, ricarica i filtri
        risorsaComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED && filtriCheckBox.isSelected()) {
                ricaricaFiltri(String.valueOf(risorsaComboBox.getSelectedItem()));
            } else if (e.getStateChange() == ItemEvent.SELECTED && !filtriCheckBox.isSelected()) {
                setFiltriInvisibili();
            }
        });
        //aggiunge un listener al searchButton che, quando viene premuto, apre la ResultView, implementare ricerca
        searchButton.addActionListener(e -> {
            if(searchField.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Inserire qualcosa da cercare");
            }
            else {
                Controller.getInstance().switchView(ResultView.getInstance(), null); //apre la ResultView
                SearchView.super.setLocationRelativeTo(null); //centra la SearchView rispetto al monitor
                int searchViewX = SearchView.getInstance().getX(); //ottiene la posizione della SearchView
                int searchViewY = SearchView.getInstance().getY(); //ottiene la posizione della SearchView
                int searchViewW = SearchView.getInstance().getWidth(); //ottiene la larghezza della SearchView
                int x; //variabile che conterrà la posizione x della ResultView
                SearchView.getInstance().setLocation(searchViewX-searchViewW/2, searchViewY); //centra la SearchView rispetto alla ResultView
                ResultView.getInstance().setLocation(SearchView.getInstance().getX()+searchViewW,searchViewY); //centra la ResultView rispetto alla SearchView
                ResultView.getInstance().updateTable(String.valueOf(risorsaComboBox.getSelectedItem()).toLowerCase());


            }
        });
        //aggiunge un listener al autoreLibroCheckBox che, quando viene selezionato, abilita/disabilita il relativo JComboBox
        autoreLibroCheckBox.addActionListener(e -> autoreLibroComboBox.setEnabled(autoreLibroCheckBox.isSelected()));
        //aggiunge un listener al editoreLibroCheckBox che, quando viene selezionato, abilita/disabilita il relativo JComboBox
        editoreLibroCheckBox.addActionListener(e -> editoreLibroComboBox.setEnabled(editoreLibroCheckBox.isSelected()));
        //aggiunge un listener al genereLibroCheckBox che, quando viene selezionato, abilita/disabilita il relativo JComboBox
        genereLibroCheckBox.addActionListener(e -> genereLibroComboBox.setEnabled(genereLibroCheckBox.isSelected()));
        //aggiunge un listener al linguaLibroCheckBox che, quando viene selezionato, abilita/disabilita il relativo JComboBox
        linguaLibroCheckBox.addActionListener(e -> linguaLibroComboBox.setEnabled(linguaLibroCheckBox.isSelected()));
        //aggiunge un listener al dataPubblicazioneLibroCheckBox che, quando viene selezionato, abilita/disabilita i relativi JTextField
        dataPubblicazioneLibroCheckBox.addActionListener(e -> {
            dataDaLibroField.setEnabled(dataPubblicazioneLibroCheckBox.isSelected());
            dataALibroField.setEnabled(dataPubblicazioneLibroCheckBox.isSelected());
        });
        //aggiunge un listener al formatoLibroCheckBox che, quando viene selezionato, abilita/disabilita il relativo JComboBox
        formatoLibroCheckBox.addActionListener(e -> formatoLibroComboBox.setEnabled(formatoLibroCheckBox.isSelected()));
        //aggiunge un listener al prezzoLibroCheckBox che, quando viene selezionato, abilita/disabilita i relativi JTextField
        prezzoLibroCheckBox.addActionListener(e -> {
            prezzoDaLibroField.setEnabled(prezzoLibroCheckBox.isSelected());
            prezzoALibroField.setEnabled(prezzoLibroCheckBox.isSelected());
        });
        //aggiunge un listener al serieLibroCheckBox che, quando viene selezionato, abilita/disabilita il relativo JComboBox
        serieLibroCheckBox.addActionListener(e -> serieLibroComboBox.setEnabled(serieLibroCheckBox.isSelected()));
        //LISTENER CHECKBOX FILTRI ARTICOLI
        //------------------------------------------------------------------------------------------
        //aggiunge un listener al autoreArticoloCheckBox che, quando viene selezionato, abilita/disabilita il relativo JComboBox
        autoreArticoloCheckBox.addActionListener(e -> autoreArticoloComboBox.setEnabled(autoreArticoloCheckBox.isSelected()));
        //aggiunge un listener al editoreArticoloCheckBox che, quando viene selezionato, abilita/disabilita il relativo JComboBox
        editoreArticoloCheckBox.addActionListener(e -> editoreArticoloComboBox.setEnabled(editoreArticoloCheckBox.isSelected()));
        //aggiunge un listener al disciplinaArticoloCheckBox che, quando viene selezionato, abilita/disabilita il relativo JComboBox
        disciplinaArticoloCheckBox.addActionListener(e -> disciplinaArticoloComboBox.setEnabled(disciplinaArticoloCheckBox.isSelected()));
        linguaArticoloCheckBox.addActionListener(e -> linguaArticoloComboBox.setEnabled(linguaArticoloCheckBox.isSelected()));
        //aggiunge un listener al dataPubblicazioneArticoloCheckBox che, quando viene selezionato, abilita/disabilita i relativi JTextField
        dataPubblicazioneArticoloCheckBox.addActionListener(e -> {
            dataPubblicazioneDaArticoloComboBox.setEnabled(dataPubblicazioneArticoloCheckBox.isSelected());
            dataPubblicazioneAArticoloComboBox.setEnabled(dataPubblicazioneArticoloCheckBox.isSelected());
        });
        //aggiunge un listener al formatoArticoloCheckBox che, quando viene selezionato, abilita/disabilita il relativo JComboBox
        formatoArticoloCheckBox.addActionListener(e -> formatoArticoloComboBox.setEnabled(formatoArticoloCheckBox.isSelected()));
        //aggiunge un listener al rivistaArticoloCheckBox che, quando viene selezionato, abilita/disabilita i relativi JTextField
        rivistaArticoloCheckBox.addActionListener(e -> rivistaArticoloComboBox.setEnabled(rivistaArticoloCheckBox.isSelected()));
        //aggiunge un listener al conferenzaArticoloCheckBox che, quando viene selezionato, abilita/disabilita il relativo JComboBox
        conferenzaArticoloCheckBox.addActionListener(e -> conferenzaArticoloComboBox.setEnabled(conferenzaArticoloCheckBox.isSelected()));
        //LISTENER CHECKBOX FILTRI RIVISTE
        //------------------------------------------------------------------------------------------
        //aggiunge un listener al argomentoRivisteCheckBox che, quando viene selezionato, abilita/disabilita il relativo JComboBox
        argomentoRivisteCheckBox.addActionListener(e -> argomentoRivisteComboBox.setEnabled(argomentoRivisteCheckBox.isSelected()));
        //aggiunge un listener al linguaRivisteCheckBox che, quando viene selezionato, abilita/disabilita il relativo JComboBox
        linguaRivisteCheckBox.addActionListener(e -> linguaRivisteComboBox.setEnabled(linguaRivisteCheckBox.isSelected()));
        dataPubblicazioneRivisteCheckBox.addActionListener(e -> {
            dataPubblicazioneDaRivisteComboBox.setEnabled(dataPubblicazioneRivisteCheckBox.isSelected());
            dataPubblicazioneARivisteComboBox.setEnabled(dataPubblicazioneRivisteCheckBox.isSelected());
        });
        //aggiunge un listener al formatoRivisteCheckBox che, quando viene selezionato, abilita/disabilita il relativo JComboBox
        formatoRivisteCheckBox.addActionListener(e -> formatoRivisteComboBox.setEnabled(formatoRivisteCheckBox.isSelected()));
        //aggiunge un listener al prezzoRivisteCheckBox che, quando viene selezionato, abilita/disabilita i relativi JTextField
        prezzoRivisteCheckBox.addActionListener(e -> {
            prezzoDaRivisteField.setEnabled(prezzoRivisteCheckBox.isSelected());
            prezzoARivisteField.setEnabled(prezzoRivisteCheckBox.isSelected());
        });
        //LISTENER CHECKBOX FILTRI SERIE
        //------------------------------------------------------------------------------------------
        //aggiunge un listener al editoreSerieCheckBox che, quando viene selezionato, abilita/disabilita il relativo JComboBox
        editoreSerieCheckBox.addActionListener(e -> editoreSerieComboBox.setEnabled(editoreSerieCheckBox.isSelected()));
        //aggiunge un listener al linguaSerieCheckBox che, quando viene selezionato, abilita/disabilita il relativo JComboBox
        linguaSerieCheckBox.addActionListener(e -> linguaSerieComboBox.setEnabled(linguaSerieCheckBox.isSelected()));
        //aggiunge un listener al dataPubblicazioneSerieCheckBox che, quando viene selezionato, abilita/disabilita i relativi JTextField
        dataPubblicazioneSerieCheckBox.addActionListener(e -> {
            dataDaSerieField.setEnabled(dataPubblicazioneSerieCheckBox.isSelected());
            dataASerieField.setEnabled(dataPubblicazioneSerieCheckBox.isSelected());
        });
        //aggiunge un listener al formatoSerieCheckBox che, quando viene selezionato, abilita/disabilita il relativo JComboBox
        formatoSerieCheckBox.addActionListener(e -> formatoSerieComboBox.setEnabled(formatoSerieCheckBox.isSelected()));
    }

    private void setFieldsDisabled() { //NON è COMPLETO
        //libri
        autoreLibroComboBox.setEnabled(false);
        editoreLibroComboBox.setEnabled(false);
        genereLibroComboBox.setEnabled(false);
        linguaLibroComboBox.setEnabled(false);
        dataDaLibroField.setEnabled(false);
        dataDaLibroField.setEnabled(false);
        dataALibroField.setEnabled(false);
        formatoLibroComboBox.setEnabled(false);
        prezzoDaLibroField.setEnabled(false);
        prezzoALibroField.setEnabled(false);
        serieLibroComboBox.setEnabled(false);
        //Articoli
        autoreArticoloComboBox.setEnabled(false);
        editoreArticoloComboBox.setEnabled(false);
        disciplinaArticoloComboBox.setEnabled(false);
        linguaArticoloComboBox.setEnabled(false);
        dataPubblicazioneDaArticoloComboBox.setEnabled(false);
        dataPubblicazioneAArticoloComboBox.setEnabled(false);
        formatoArticoloComboBox.setEnabled(false);
        rivistaArticoloComboBox.setEnabled(false);
        conferenzaArticoloComboBox.setEnabled(false);
        //Riviste
        argomentoRivisteComboBox.setEnabled(false);
        linguaRivisteComboBox.setEnabled(false);
        dataPubblicazioneDaRivisteComboBox.setEnabled(false);
        dataPubblicazioneARivisteComboBox.setEnabled(false);
        formatoRivisteComboBox.setEnabled(false);
        prezzoDaRivisteField.setEnabled(false);
        prezzoARivisteField.setEnabled(false);
        //Serie
        editoreSerieComboBox.setEnabled(false);
        linguaSerieComboBox.setEnabled(false);
        dataDaSerieField.setEnabled(false);
        dataASerieField.setEnabled(false);
        formatoSerieComboBox.setEnabled(false);
    }

    private void setFiltriInvisibili() {
        filtriLibriPanel.setVisible(false);
        filtriArticoliPanel.setVisible(false);
        filtriRivistePanel.setVisible(false);
        filtriSeriePanel.setVisible(false);
    }

    private void ricaricaFiltri(String risorsa) {
        setFiltriInvisibili();
        switch (risorsa) {
            case "Libri" -> filtriLibriPanel.setVisible(true);
            case "Serie" -> filtriSeriePanel.setVisible(true);
            case "Articoli" -> filtriArticoliPanel.setVisible(true);
            case "Riviste" -> filtriRivistePanel.setVisible(true);
            default -> {
            }
        }
    }

    public static View getInstance() {
        if (instance == null) {
            instance = new SearchView();
        }
        return instance;
    }
}
