package org.Bibliotech.View;

import org.Bibliotech.Controller.Controller;
import org.Bibliotech.Controller.FiltriController;

import javax.swing.*;
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
    private JButton button1;
    private JTextField titoloField;
    private JTextField autoriField;
    private JTextField editoreField;
    private JTextField genereField;
    private JTextField prezzoField;
    private JCheckBox ilLibroFaParteCheckBox;
    private JComboBox serieBox;
    private JTextField titoloArticoliField;
    private JTextField DisciplinaField;
    private JTextField autoriArticoloField;
    private JTextField formatoArticoliField;
    private JTextField editoreArticoliField;
    private JTextField doiField;
    private JComboBox PresentatoInBox;
    private JTextField isbnField;
    private JTextField dataPublicazioneField;
    private JLabel logoLabel;
    private JButton annullaButton;

    public AggiuntaView() {
        super(nome);
        logoLabel.setIcon(logoLabelIcon); //setta l'icona del logo (logoLabelIcon viene presa da superclasse View)
        this.setVisible(true);
        this.setContentPane(rootPanel); //setta il contentPanel come contentPane del JFrame
        setPlaceHolders();
        setFiltriInvisibili();
        serieBox.setEnabled(false);
        fillAllComboBoxes();
        risorsaComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    ricaricaFiltri(String.valueOf(risorsaComboBox.getSelectedItem()));
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
        setPlaceholderText(formatoArticoliField, "Formato");
        setPlaceholderText(editoreArticoliField, "Titolo");
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

    private void fillComboBox(JComboBox comboBox, ArrayList<String> items) {
        comboBox.removeAllItems();
        if(comboBox.equals(serieBox))comboBox.addItem("・・・Aggiungi nuova serie・・・");
        for (String item : items) {
            comboBox.addItem(item);
        }
    }
    private void setFiltriInvisibili() {
        LibriPanel.setVisible(false);
        ArticoliPanel.setVisible(false);
    }

    private void ricaricaFiltri(String risorsa) {
        setFiltriInvisibili();
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
