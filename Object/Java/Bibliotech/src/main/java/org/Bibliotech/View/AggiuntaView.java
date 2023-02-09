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
    private JTextField titoloField;
    private JTextField autoriField;
    private JTextField editoreField;
    private JTextField genereField;
    private JTextField prezzoField;
    private JCheckBox ilLibroFaParteCheckBox;
    private JComboBox<String> serieBox;
    private JTextField titoloArticoliField;
    private JTextField DisciplinaField;
    private JTextField autoriArticoloField;
    private JComboBox formatoArticoliComboBox;
    private JTextField editoreArticoliField;
    private JTextField doiField;
    private JComboBox PresentatoInBox;
    private JTextField isbnField;
    private JTextField dataPublicazioneField;
    private JLabel logoLabel;
    private JButton annullaButton;
    private JPanel seriePanel;
    private JTextField nomeField;
    private JTextField issnField;
    private JComboBox formatoComboBox;

    public AggiuntaView() {
        super(nome);
        logoLabel.setIcon(logoLabelIcon); //setta l'icona del logo (logoLabelIcon viene presa da superclasse View)
        this.setVisible(true);
        this.setContentPane(rootPanel); //setta il contentPanel come contentPane del JFrame
        setPlaceHolders();
        setPanelInvisibili();
        serieBox.setEnabled(false);
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
                    serieBox.setEnabled(true);
                    PresentatoInBox.setEnabled(true);
                } else {
                    serieBox.setEnabled(false);
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
        serieBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    if(!serieBox.getSelectedItem().equals("・・・Aggiungi nuova serie・・・")){
                        seriePanel.setVisible(false);
                    }
                }
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (serieBox.getSelectedItem().equals("・・・Aggiungi nuova serie・・・")) {
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
                if(String.valueOf(titoloField.getText()).equals("Titolo")){
                    titoloField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                }
                if(String.valueOf(autoriField.getText()).equals("Autori")){
                    autoriField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                }
                if(String.valueOf(genereField.getText()).equals("Genere")){
                    genereField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                }
                if(String.valueOf(editoreField.getText()).equals("Editore")){
                    editoreField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                }
                if(String.valueOf(prezzoField.getText()).equals("Prezzo")){
                    prezzoField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                }
                if(String.valueOf(isbnField.getText()).equals("ISBN")){
                    isbnField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                }
                if(String.valueOf(dataPublicazioneField.getText()).equals("Data di pubblicazione")){
                    dataPublicazioneField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                }
                if(String.valueOf(formatoComboBox.getSelectedItem()).equals("Formato")){
                    formatoComboBox.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                }
                else{
LibroController.addLibroInDB(titoloField.getText(), autoriField.getText(), genereField.getText(), editoreField.getText(), prezzoField.getText(), isbnField.getText(), dataPublicazioneField.getText(), String.valueOf(formatoComboBox.getSelectedItem()));
                    JOptionPane.showMessageDialog(null, "Libro aggiunto");
                }
            }
            private void checkFieldsArticolo() {
            }


        });
    }

    public void setPlaceHolders(){
        setPlaceholderText(titoloField, "Titolo");
        setPlaceholderText(autoriField, "Autori");
        setPlaceholderText(editoreField, "Editore");
        setPlaceholderText(genereField, "Genere");
        setPlaceholderText(prezzoField, "Prezzo");
        setPlaceholderText(isbnField, "ISBN");
        setPlaceholderText(dataPublicazioneField, "Data di pubblicazione");
        setPlaceholderText(doiField, "DOI");
        setPlaceholderText(titoloArticoliField, "Titolo");
        setPlaceholderText(DisciplinaField, "Disciplina");
        setPlaceholderText(autoriArticoloField, "Autori");
        setPlaceholderText(editoreArticoliField, "Titolo");
        setPlaceholderText(issnField, "ISSN");
        setPlaceholderText(nomeField, "Nome della serie");

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
        fillComboBox(serieBox, FiltriController.getInstance().leggiSerieLibri());
    }

    private void fillComboBox(JComboBox<String> comboBox, ArrayList<String> items) {
        comboBox.removeAllItems();
        if(comboBox.equals(serieBox)){
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
