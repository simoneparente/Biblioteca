package org.Bibliotech.View;

import org.Bibliotech.Controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class SearchView extends View {
    private static SearchView instance = null;
    private static final String nome = "Search";
    private boolean filtriState;
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
    private JTextField minPrezzoLibroField;
    private JTextField maxPrezzoLibroField;
    private JCheckBox serieLibroCheckBox;
    private JComboBox serieLibroComboBox;
    private JPanel filtriArticoliPanel;
    private JCheckBox disciplinaArticoloCheckBox;
    private JComboBox disciplinaComboBox;
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
    private JCheckBox editoreCheckBox;
    private JCheckBox linguaCheckBox;
    private JCheckBox dataPubblicazioneCheckBox;
    private JCheckBox formatoCheckBox;
    private JPanel editorePanel;
    private JComboBox editoreComboBox;
    private JPanel linguaPanel;
    private JComboBox linguaComboBox;
    private JPanel dataPubblicazionePanel;
    private JTextField dataDaField;
    private JTextField dataAField;
    private JPanel formatoPanel;
    private JComboBox formatoComboBox;

    public SearchView() {
        super(nome);
        this.setVisible(true);
        this.setContentPane(rootPanel); //setta il contentPanel come contentPane del JFrame
        setFiltriFalse();
        setFieldsFalse();

        logoLabel.setIcon(logoLabelIcon); //setta l'icona del logo (logoLabelIcon viene presa da superclasse View)

        filtriCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (filtriCheckBox.isSelected()) {
                    if (risorsaComboBox.getSelectedItem().equals("")) {
                        JOptionPane.showMessageDialog(null, "Selezionare una risorsa");
                        filtriCheckBox.setSelected(false);
                    } else {
                        ricaricaFiltri(String.valueOf(risorsaComboBox.getSelectedItem()));
                    }
                } else {
                    setFiltriFalse();
                }
            }
        });
        risorsaComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED && filtriCheckBox.isSelected()) {
                    ricaricaFiltri(String.valueOf(risorsaComboBox.getSelectedItem()));
                } else if (e.getStateChange() == ItemEvent.SELECTED && !filtriCheckBox.isSelected()) {
                    setFiltriFalse();
                }
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getInstance().switchView(ResultView.getInstance(), null);
                SearchView.super.setLocationRelativeTo(ResultView.getInstance());
            }
        });
    }

    private void setFieldsFalse() { //NON è COMPLETO
        autoreLibroComboBox.setVisible(false);
        editoreLibroComboBox.setVisible(false);
        genereLibroComboBox.setVisible(false);


    }

    private void setFiltriFalse() {
        filtriLibriPanel.setVisible(false);
        filtriArticoliPanel.setVisible(false);
        filtriRivistePanel.setVisible(false);
        filtriSeriePanel.setVisible(false);
    }

    private void ricaricaFiltri(String risorsa) {
        setFiltriFalse();
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
