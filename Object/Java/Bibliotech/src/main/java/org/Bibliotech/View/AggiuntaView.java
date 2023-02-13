package org.Bibliotech.View;

import org.Bibliotech.Controller.Controller;
import org.Bibliotech.Controller.FiltriController;
import org.Bibliotech.Controller.LibroController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AggiuntaView extends View {
    private static final String nome = "Add resource";
    private static AggiuntaView instance;
    private JPanel rootPanel;
    private JPanel imagePanel;
    private JPanel RisorsaPanel;
    private JPanel LibriPanel;
    private JPanel ArticoliPanel;
    private JComboBox risorsaComboBox;
    private JButton addButton;
    private JTextField titoloLibroField;
    private JTextField autoriLibroField;
    private JTextField editoreLibroField;
    private JTextField genereLibroField;
    private JTextField prezzoLibroField;
    private JCheckBox ilLibroFaParteCheckBox;
    private JComboBox<String> serieLibroBox;
    private JTextField titoloArticoliField;
    private JTextField DisciplinaField;
    private JTextField autoriArticoloField;
    private JComboBox formatoArticoliComboBox;
    private JTextField editoreArticoliField;
    private JTextField doiField;
    private JComboBox PresentatoInBox;
    private JTextField isbnLibroField;
    private JTextField dataPublicazioneLibroField;
    private JLabel logoLabel;
    private JButton annullaButton;
    private JPanel seriePanel;
    private JTextField nomeSerieField;
    private JTextField issnSerieField;
    private JComboBox formatoLibroComboBox;
    private JTextField nomeRivistaField;
    private JTextField issnRivistaField;
    private JTextField argomentoRivistaField;
    private JTextField responsabileRivistaField;
    private JTextField datapubblicazioneRivistaField;
    private JTextField prezzoRivistaField;
    private JPanel rivistaPanel;
    private JPanel conferenzaPanel;
    private JTextField nomeConferenzaField;
    private JTextField responsabileConferenzaField;
    private JTextField strutturaConferenzaField;
    private JTextField indirizzoConferenzaField;
    private JTextField dataDaConferenzaField;
    private JTextField dataAConferenzaField;
    private JTextField linguaLibroField;

    public AggiuntaView() {
        super(nome);
        logoLabel.setIcon(logoLabelIcon); //setta l'icona del logo (logoLabelIcon viene presa da superclasse View)
        this.setVisible(true);
        this.setContentPane(rootPanel); //setta il contentPanel come contentPane del JFrame
        setPlaceHolders();
        setPanelInvisibili();
        serieLibroBox.setEnabled(false);
        fillAllComboBoxes();
        risorsaComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    ricaricaPanel(String.valueOf(risorsaComboBox.getSelectedItem()));
                }
            }
        });
        ilLibroFaParteCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ilLibroFaParteCheckBox.isSelected()) {
                    serieLibroBox.setEnabled(true);
                    PresentatoInBox.setEnabled(true);
                } else {
                    serieLibroBox.setEnabled(false);
                    PresentatoInBox.setEnabled(false);
                }
            }
        });
        annullaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getInstance().switchView(SearchView.getInstance(), AggiuntaView.getInstance());
            }
        });
        serieLibroBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    if(!serieLibroBox.getSelectedItem().equals("・・・Aggiungi nuova serie・・・")){
                        seriePanel.setVisible(false);
                    }
                }
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (serieLibroBox.getSelectedItem().equals("・・・Aggiungi nuova serie・・・")) {
                        seriePanel.setVisible(true);
                    }
                }
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkRisorsa();
            }

            private void checkRisorsa() {
                if (String.valueOf(risorsaComboBox.getSelectedItem()).equals("Libro")) {
                    checkAddLibro();
                } else if (String.valueOf(risorsaComboBox.getSelectedItem()).equals("Articolo")) {
                    checkFieldsArticolo();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Selezionare una risorsa");
                }
            }

            private void checkAddLibro() {
                if(String.valueOf(titoloLibroField.getText()).equals("Titolo")){
                    titoloLibroField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                }
                if(String.valueOf(autoriLibroField.getText()).equals("Autori")){
                    autoriLibroField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                }
                if(String.valueOf(genereLibroField.getText()).equals("Genere")){
                    genereLibroField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                }
                if(String.valueOf(editoreLibroField.getText()).equals("Editore")){
                    editoreLibroField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                }
                if(String.valueOf(prezzoLibroField.getText()).equals("Prezzo")){
                    prezzoLibroField.setText("");
                }
                if(String.valueOf(isbnLibroField.getText()).equals("ISBN")){
                    isbnLibroField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                }
                if(String.valueOf(dataPublicazioneLibroField.getText()).equals("Data di pubblicazione")){
                    dataPublicazioneLibroField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                }
                if(String.valueOf(formatoLibroComboBox.getSelectedItem()).equals("Formato")){
                    formatoLibroComboBox.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                }
                if(String.valueOf(linguaLibroField.getText()).equals("Lingua")){
                    linguaLibroField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                }
                    if (ilLibroFaParteCheckBox.isSelected()) {
                        if(String.valueOf(serieLibroBox.getSelectedItem()).equals("・・・Aggiungi nuova serie・・・")) {
                            LibroController.addLibroInDB(titoloLibroField.getText(), genereLibroField.getText(),
                                    autoriLibroField.getText(), editoreLibroField.getText(),
                                    prezzoLibroField.getText(), isbnLibroField.getText(),
                                    dataPublicazioneLibroField.getText(),
                                    String.valueOf(formatoLibroComboBox.getSelectedItem()), linguaLibroField.getText(),
                                    nomeSerieField.getText(), issnSerieField.getText());
                        }
                    } else {
                        LibroController.addLibroInDB(titoloLibroField.getText(), genereLibroField.getText(),
                                autoriLibroField.getText(), editoreLibroField.getText(),
                                prezzoLibroField.getText(), isbnLibroField.getText(),
                                dataPublicazioneLibroField.getText(),
                                String.valueOf(formatoLibroComboBox.getSelectedItem()), linguaLibroField.getText(),
                                null, null);
                    }
                }

            private void checkFieldsArticolo() {
            }


        });
        PresentatoInBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    if(String.valueOf(PresentatoInBox.getSelectedItem()).equals("Rivista")){ //se è stato selezionato rivista
                        rivistaPanel.setVisible(true);//rendo visibile il pannello rivista
                        conferenzaPanel.setVisible(false); //rendo invisibile il pannello conferenza
                    } else if (String.valueOf(PresentatoInBox.getSelectedItem()).equals("Conferenza")){ //se è stato selezionato conferenza
                        conferenzaPanel.setVisible(true);//rendo visibile il pannello conferenza
                        rivistaPanel.setVisible(false); //rendo invisibile il pannello rivista
                    }
                }
            }
        });
    }

    public void setPlaceHolders(){
        setPlaceholderText(titoloLibroField, "Titolo");
        setPlaceholderText(autoriLibroField, "Autori");
        setPlaceholderText(editoreLibroField, "Editore");
        setPlaceholderText(genereLibroField, "Genere");
        setPlaceholderText(prezzoLibroField, "Prezzo");
        setPlaceholderText(isbnLibroField, "ISBN");
        setPlaceholderText(dataPublicazioneLibroField, "Data di pubblicazione");
        setPlaceholderText(linguaLibroField, "Lingua");
        setPlaceholderText(doiField, "DOI");
        setPlaceholderText(titoloArticoliField, "Titolo");
        setPlaceholderText(DisciplinaField, "Disciplina");
        setPlaceholderText(autoriArticoloField, "Autori");
        setPlaceholderText(editoreArticoliField, "Titolo");
        setPlaceholderText(issnSerieField, "ISSN");
        setPlaceholderText(nomeSerieField, "Nome della serie");
        setPlaceholderText(nomeConferenzaField, "Nome della conferenza");
        setPlaceholderText(dataDaConferenzaField, "Data inizio");
        setPlaceholderText(dataAConferenzaField, "Data fine");
        setPlaceholderText(strutturaConferenzaField, "Struttura Ospitante");
        setPlaceholderText(indirizzoConferenzaField, "Indirizzo");
        setPlaceholderText(responsabileConferenzaField, "Responsabile");
        setPlaceholderText(argomentoRivistaField, "Argomento rivista");
        setPlaceholderText(datapubblicazioneRivistaField, "Data pubblicazione");
        setPlaceholderText(issnRivistaField, "ISSN");
        setPlaceholderText(responsabileRivistaField, "Responsabile");
        setPlaceholderText(prezzoRivistaField, "Autori");
        setPlaceholderText(nomeRivistaField, "Nome della rivista");
    }
    public void setPlaceholderText(JTextField field, String text) {
        field.setText(text);
        field.addFocusListener(new FocusListener() {
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(text);
                }
            }
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(text)) {
                    field.setText("");
                }
            }
        });
    }

    private void fillAllComboBoxes() {
        fillComboBox(serieLibroBox, FiltriController.getInstance().leggiSerieLibri());
    }

    private void fillComboBox(JComboBox<String> comboBox, ArrayList<String> items) {
        comboBox.removeAllItems();
        if(comboBox.equals(serieLibroBox)){
            comboBox.addItem("");
            comboBox.addItem("・・・Aggiungi nuova serie・・・");
            comboBox.addItem("");
        }
        for (String item : items) {
            comboBox.addItem(item);
        }
    }
    private void setPanelInvisibili() {
        LibriPanel.setVisible(false);
        ArticoliPanel.setVisible(false);
        seriePanel.setVisible(false);
        rivistaPanel.setVisible(false);
        conferenzaPanel.setVisible(false);
    }

    private void ricaricaPanel(String risorsa) {
        setPanelInvisibili();
        switch (risorsa) {
            case "Libro" -> LibriPanel.setVisible(true);
            case "Articolo" -> ArticoliPanel.setVisible(true);
            default -> {
            }
        }
    }

    public static AggiuntaView getInstance() {
        if (instance == null) {
            instance = new AggiuntaView();
        }
        return instance;
    }
}
