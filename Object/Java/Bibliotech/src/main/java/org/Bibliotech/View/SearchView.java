package org.Bibliotech.View;

import org.Bibliotech.Controller.Controller;
import org.Bibliotech.Controller.FiltriController;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

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
    private JTextField dataPubblicazioneDaArticoloField;
    private JTextField dataPubblicazioneAArticoloField;
    private JComboBox formatoArticoloComboBox;
    private JComboBox rivistaArticoloComboBox;
    private JCheckBox linguaRivisteCheckBox;
    private JCheckBox dataPubblicazioneRivisteCheckBox;
    private JCheckBox formatoRivisteCheckBox;
    private JComboBox linguaRivisteComboBox;
    private JTextField dataPubblicazioneDaRivisteField;
    private JTextField dataPubblicazioneARivisteField;
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
    private JTextField dataPubblicazioneDaSerieField;
    private JTextField dataPubblicazioneASerieField;
    private JPanel formatoSeriePanel;
    private JComboBox formatoSerieComboBox;

    private ArrayList<String> filtriSelezionati;

    public SearchView() {
        super(nome);
        logoLabel.setIcon(logoLabelIcon); //setta l'icona del logo (logoLabelIcon viene presa da superclasse View)
        this.setVisible(true);
        this.setContentPane(rootPanel); //setta il contentPanel come contentPane del JFrame
        setFiltriInvisibili();
        setFieldsDisabled();
        fillAllComboBoxes();
        ricaricaSearchField(String.valueOf(risorsaComboBox.getSelectedItem()));
        filtriSelezionati = new ArrayList<>();


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
            if (e.getStateChange() == ItemEvent.SELECTED) {
                ricaricaSearchField(String.valueOf(risorsaComboBox.getSelectedItem()));
                if (filtriCheckBox.isSelected()) {
                    ricaricaFiltri(String.valueOf(risorsaComboBox.getSelectedItem()));
                }
            } else if (e.getStateChange() == ItemEvent.SELECTED && !filtriCheckBox.isSelected()) {
                setFiltriInvisibili();
            }
        });
        //aggiunge un listener al searchButton che, quando viene premuto, apre la ResultView, implementare ricerca
        searchButton.addActionListener(e -> {
            if (searchField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Inserire qualcosa da cercare");
            } else {
                Controller.getInstance().switchView(ResultView.getInstance(), null); //apre la ResultView
                SearchView.super.setLocationRelativeTo(null); //centra la SearchView rispetto al monitor
                int searchViewX = SearchView.getInstance().getX(); //ottiene la posizione della SearchView
                int searchViewY = SearchView.getInstance().getY(); //ottiene la posizione della SearchView
                int searchViewW = SearchView.getInstance().getWidth(); //ottiene la larghezza della SearchView
                SearchView.getInstance().setLocation(searchViewX - searchViewW / 2, searchViewY); //centra la SearchView rispetto alla ResultView
                ResultView.getInstance().setLocation(SearchView.getInstance().getX() + searchViewW, searchViewY); //centra la ResultView rispetto alla SearchView
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

        //aggiunge un listener al linguaArticoloCheckBox che, quando viene selezionato, abilita/disabilita il relativo JComboBox
        linguaArticoloCheckBox.addActionListener(e -> linguaArticoloComboBox.setEnabled(linguaArticoloCheckBox.isSelected()));

        //aggiunge un listener al dataPubblicazioneArticoloCheckBox che, quando viene selezionato, abilita/disabilita i relativi JTextField
        dataPubblicazioneArticoloCheckBox.addActionListener(e -> {
            dataPubblicazioneDaArticoloField.setEnabled(dataPubblicazioneArticoloCheckBox.isSelected());
            dataPubblicazioneAArticoloField.setEnabled(dataPubblicazioneArticoloCheckBox.isSelected());
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
            dataPubblicazioneDaRivisteField.setEnabled(dataPubblicazioneRivisteCheckBox.isSelected());
            dataPubblicazioneARivisteField.setEnabled(dataPubblicazioneRivisteCheckBox.isSelected());
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
            dataPubblicazioneDaSerieField.setEnabled(dataPubblicazioneSerieCheckBox.isSelected());
            dataPubblicazioneASerieField.setEnabled(dataPubblicazioneSerieCheckBox.isSelected());
        });

        //aggiunge un listener al formatoSerieCheckBox che, quando viene selezionato, abilita/disabilita il relativo JComboBox
        formatoSerieCheckBox.addActionListener(e -> formatoSerieComboBox.setEnabled(formatoSerieCheckBox.isSelected()));
        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                searchField.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (searchField.getText().equals("")) {
                    ricaricaSearchField(String.valueOf(risorsaComboBox.getSelectedItem()));
                }
            }
        });
    }

    private void ricaricaSearchField(String risorsa) {
        switch (risorsa) {
            case "Libri" -> searchField.setText("Cerca un libro...");
            case "Serie" -> searchField.setText("Cerca una serie...");
            case "Articoli" -> searchField.setText("Cerca un articolo...");
            case "Riviste" -> searchField.setText("Cerca una rivista...");
            default -> {
                searchField.setText("Selezionare una risorsa...");
            }
        }
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
        dataPubblicazioneDaArticoloField.setEnabled(false);
        dataPubblicazioneAArticoloField.setEnabled(false);
        formatoArticoloComboBox.setEnabled(false);
        rivistaArticoloComboBox.setEnabled(false);
        conferenzaArticoloComboBox.setEnabled(false);
        //Riviste
        argomentoRivisteComboBox.setEnabled(false);
        linguaRivisteComboBox.setEnabled(false);
        dataPubblicazioneDaRivisteField.setEnabled(false);
        dataPubblicazioneARivisteField.setEnabled(false);
        formatoRivisteComboBox.setEnabled(false);
        prezzoDaRivisteField.setEnabled(false);
        prezzoARivisteField.setEnabled(false);
        //Serie
        editoreSerieComboBox.setEnabled(false);
        linguaSerieComboBox.setEnabled(false);
        dataPubblicazioneDaSerieField.setEnabled(false);
        dataPubblicazioneASerieField.setEnabled(false);
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

    public void fillAllComboBoxes() {
        fillComboBox(autoreLibroComboBox, FiltriController.getInstance().leggiAutoriLibri());
        fillComboBox(editoreLibroComboBox, FiltriController.getInstance().leggiEditoriLibri());
        fillComboBox(genereLibroComboBox, FiltriController.getInstance().leggiGeneriLibri());
        fillComboBox(linguaLibroComboBox, FiltriController.getInstance().leggiLingueLibri());
        fillComboBox(serieLibroComboBox, FiltriController.getInstance().leggiSerieLibri());
        fillComboBox(formatoLibroComboBox, FiltriController.getInstance().leggiFormatiLibri());
        fillComboBox(autoreArticoloComboBox, FiltriController.getInstance().leggiAutoriArticoli());
        fillComboBox(editoreArticoloComboBox, FiltriController.getInstance().leggiEditoriArticoli());
        fillComboBox(disciplinaArticoloComboBox, FiltriController.getInstance().leggiDisciplineArticoli());
        fillComboBox(linguaArticoloComboBox, FiltriController.getInstance().leggiLingueArticoli());
        fillComboBox(rivistaArticoloComboBox, FiltriController.getInstance().leggiRivisteArticoli());
        fillComboBox(conferenzaArticoloComboBox, FiltriController.getInstance().leggiConferenzeArticoli());
        fillComboBox(formatoArticoloComboBox, FiltriController.getInstance().leggiFormatiArticoli());
        fillComboBox(argomentoRivisteComboBox, FiltriController.getInstance().leggiArgomentiRiviste());
        fillComboBox(linguaRivisteComboBox, FiltriController.getInstance().leggiLingueRiviste());
        fillComboBox(formatoRivisteComboBox, FiltriController.getInstance().leggiFormatiRiviste());
        fillComboBox(editoreSerieComboBox, FiltriController.getInstance().leggiEditoriSerie());
        fillComboBox(linguaSerieComboBox, FiltriController.getInstance().leggiLingueSerie());
        fillComboBox(formatoSerieComboBox, FiltriController.getInstance().leggiFormatiSerie());
    }

    public void fillComboBox(JComboBox comboBox, ArrayList<String> items) {
        comboBox.removeAllItems();
        for (String item : items) {
            comboBox.addItem(item);
        }
    }

    public String buildQueryByFiltri() {  // in base alla risorsa selezionata, costruisce la query
        String q = "";
        String risorsa = String.valueOf(risorsaComboBox.getSelectedItem());

        switch (risorsa) {
            case "Libri":
                q = buildQueryByFiltriLibri(); // costruisce la query per i libri
                break;
            case "Articoli":
                q = buildQueryByFiltriArticoli(); // costruisce la query per gli articoli
                break;
            case "Riviste":
                q = buildQueryByFiltriRiviste(); // costruisce la query per le riviste
                break;
            case "Serie":
                q = buildQueryByFiltriSerie(); // costruisce la query per le serie
                break;
            default:
                break;
        }
        return q;
    }

    private String buildQueryByFiltriSerie() { // costruisce la query per le serie in base ai filtri selezionati
        String titoloOrIssn,editore, lingua, dataPubblicazioneDa, dataPubblicazioneA, formato;
        titoloOrIssn = searchField.getText();
        String query = "SELECT * FROM b.filter_serie WHERE nome LIKE '%" + titoloOrIssn + "%' OR issn LIKE '%" + titoloOrIssn + "%' AND ";


        if (editoreSerieCheckBox.isSelected()) {
            editore = String.valueOf(editoreSerieComboBox.getSelectedItem());
        } else {
            editore = "";
        }

        if (linguaSerieCheckBox.isSelected()) {
            lingua = String.valueOf(linguaSerieComboBox.getSelectedItem());
        } else {
            lingua = "";
        }

        if (formatoSerieCheckBox.isSelected()) {
            formato = String.valueOf(formatoSerieComboBox.getSelectedItem());
        } else {
            formato = "";
        }

        if (dataPubblicazioneSerieCheckBox.isSelected()) {
            dataPubblicazioneDa = dataPubblicazioneDaSerieField.getText();
            dataPubblicazioneA = dataPubblicazioneASerieField.getText();
        } else {
            dataPubblicazioneDa = "";
            dataPubblicazioneA = "";
        }

        String finalQuery = query +
                "editore = '" + editore +
                "' AND lingua = '" + lingua +
                "' AND formato = '" + formato +
                "' AND data_pubblicazione BETWEEN '" + dataPubblicazioneDa +
                "' AND '" + dataPubblicazioneA + "'";
        return finalQuery;
    }

    private String buildQueryByFiltriRiviste() { // costruisce la query per le riviste in base ai filtri selezionati
        String nomeOrIssn, argomento, lingua, formato, dataPubblicazioneDa, dataPubblicazioneA;
        nomeOrIssn = searchField.getText();
        String query = "SELECT * FROM b.filter_riviste WHERE nome LIKE '%" + nomeOrIssn + "%' OR issn LIKE '%" + nomeOrIssn + "%') AND ";

        if (argomentoRivisteCheckBox.isSelected()) {
            argomento = String.valueOf(argomentoRivisteComboBox.getSelectedItem());
        } else {
            argomento = "";
        }

        if (linguaRivisteCheckBox.isSelected()) {
            lingua = String.valueOf(linguaRivisteComboBox.getSelectedItem());
        } else {
            lingua = "";
        }

        if (formatoRivisteCheckBox.isSelected()) {
            formato = String.valueOf(formatoRivisteComboBox.getSelectedItem());
        } else {
            formato = "";
        }

        if (dataPubblicazioneRivisteCheckBox.isSelected()) {
            dataPubblicazioneDa = dataPubblicazioneDaRivisteField.getText();
            dataPubblicazioneA = dataPubblicazioneARivisteField.getText();
        } else {
            dataPubblicazioneDa = "";
            dataPubblicazioneA = "";
        }

        String finalQuery = query +
                "argomento = '" + argomento +
                "' AND lingua = '" + lingua +
                "' AND formato = '" + formato +
                "' AND data_pubblicazione BETWEEN '" + dataPubblicazioneDa +
                "' AND '" + dataPubblicazioneA + "'";
        return finalQuery;
    }

    private String buildQueryByFiltriLibri() { // costruisce la query per i libri in base ai filtri selezionati
        String titoloOrisbn, autore, editore, genere, lingua, serie, formato, dataPubblicazioneDa, dataPubblicazioneA, prezzoDa, prezzoA;
        titoloOrisbn = searchField.getText();
        String query = "SELECT * FROM b.filter_libri WHERE (titolo LIKE '%" + titoloOrisbn + "%' OR isbn LIKE '%" + titoloOrisbn + "%') AND";

        if (autoreLibroCheckBox.isSelected()) {
            autore = String.valueOf(autoreLibroComboBox.getSelectedItem());
        } else {
            autore = "";
        }

        if (editoreLibroCheckBox.isSelected()) {
            editore = String.valueOf(editoreLibroComboBox.getSelectedItem());
        } else {
            editore = "";
        }

        if (genereLibroCheckBox.isSelected()) {
            genere = String.valueOf(genereLibroComboBox.getSelectedItem());
        } else {
            genere = "";
        }

        if (linguaLibroCheckBox.isSelected()) {
            lingua = String.valueOf(linguaLibroComboBox.getSelectedItem());
        } else {
            lingua = "";
        }

        if (serieLibroCheckBox.isSelected()) {
            serie = String.valueOf(serieLibroComboBox.getSelectedItem());
        } else {
            serie = "";
        }

        if (formatoLibroCheckBox.isSelected()) {
            formato = String.valueOf(formatoLibroComboBox.getSelectedItem());
        } else {
            formato = "";
        }

        if (dataPubblicazioneLibroCheckBox.isSelected()) {
            dataPubblicazioneDa = dataDaLibroField.getText();
            dataPubblicazioneA = dataALibroField.getText();
        } else {
            dataPubblicazioneDa = "";
            dataPubblicazioneA = "";
        }

        if (prezzoLibroCheckBox.isSelected()) {
            prezzoDa = prezzoDaLibroField.getText();
            prezzoA = prezzoALibroField.getText();

        } else {
            prezzoDa = "";
            prezzoA = "";

        }
        String finalQuery = query +
                "autore = '" + autore +
                "' AND editore = '" + editore +
                "' AND genere = '" + genere +
                "' AND lingua = '" + lingua +
                "' AND serie = '" + serie +
                "' AND formato = '" + formato +
                "' AND data_pubblicazione BETWEEN '" + dataPubblicazioneDa + "' AND '" + dataPubblicazioneA +
                "' AND prezzo BETWEEN '" + prezzoDa + "' AND '" + prezzoA + "';";
        return finalQuery;
    }


    private String buildQueryByFiltriArticoli() { // costruisce la query per gli articoli in base ai filtri selezionati
        String titoloOrDOI, autore, editore, disciplina, lingua, rivista, conferenza, formato, dataPubblicazioneDa, dataPubblicazioneA;
        titoloOrDOI = searchField.getText();

        String query = "SELECT * FROM b.filter_articoli WHERE (titolo LIKE '%"+ titoloOrDOI +"%' OR doi LIKE '%"+ titoloOrDOI +"%') AND ";

           if(autoreArticoloCheckBox.isSelected()) {
               autore = String.valueOf(autoreArticoloComboBox.getSelectedItem());
           }else {
               autore = "";
           }

           if(editoreArticoloCheckBox.isSelected()) {
               editore = String.valueOf(editoreArticoloComboBox.getSelectedItem());
           }else {
               editore = "";
           }

           if(disciplinaArticoloCheckBox.isSelected()) {
               disciplina = String.valueOf(disciplinaArticoloComboBox.getSelectedItem());
           }else {
               disciplina = "";
           }

           if(linguaArticoloCheckBox.isSelected()) {
               lingua = String.valueOf(linguaArticoloComboBox.getSelectedItem());
           }else {
                lingua = "";
           }

           if(rivistaArticoloCheckBox.isSelected()) {
                rivista = String.valueOf(rivistaArticoloComboBox.getSelectedItem());
           }else {
               rivista = "";
           }

           if(conferenzaArticoloCheckBox.isSelected()) {
                conferenza = String.valueOf(conferenzaArticoloComboBox.getSelectedItem());
           }else {
                conferenza = "";
           }

           if(formatoArticoloCheckBox.isSelected()) {
               formato = String.valueOf(formatoArticoloComboBox.getSelectedItem());
           }else {
               formato = "";
           }

           if(dataPubblicazioneArticoloCheckBox.isSelected()) {
               dataPubblicazioneDa = dataPubblicazioneDaArticoloField.getText();
               dataPubblicazioneA = dataPubblicazioneAArticoloField.getText();
           }else {
                dataPubblicazioneDa = "";
                dataPubblicazioneA = "";
           }
           String finalQuery = query + "autore = '" + autore +
                   "' AND editore = '" + editore +
                   "' AND disciplina = '" + disciplina +
                   "' AND lingua = '" + lingua +
                   "' AND rivista = '" + rivista +
                   "' AND conferenza = '" + conferenza +
                   "' AND formato = '" + formato +
                   "' AND data_pubblicazione BEETWEEN '" + dataPubblicazioneDa + "'AND'" + dataPubblicazioneA + "';";
        return finalQuery;
    }
}