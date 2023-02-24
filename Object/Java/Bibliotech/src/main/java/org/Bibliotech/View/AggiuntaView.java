package org.Bibliotech.View;

import org.Bibliotech.Controller.ArticoloController;
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
    private JTextField disciplinaArticoloField;
    private JTextField autoriArticoloField;
    private JComboBox formatoArticoliComboBox;
    private JTextField editoreArticoliField;
    private JTextField doiField;
    private JComboBox presentatoInBox;
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
    private JLabel helpLabel;
    private JComboBox rivistaComboBox;
    private JComboBox conferenzaComboBox;
    private JPanel rcPanel;
    private JComboBox rivistaISSNComboBox;
    private JTextField linguaArticoloField;

    public AggiuntaView() {
        super(nome);
        logoLabel.setIcon(logoLabelIcon); //setta l'icona del logo (logoLabelIcon viene presa da superclasse View)
        this.setVisible(true);
        this.setContentPane(rootPanel); //setta il contentPanel come contentPane del JFrame
        setPlaceHolders();
        setPanelInvisibili();
        serieLibroBox.setEnabled(false); //disabilita il combobox delle serie
        rivistaComboBox.setVisible(false); //setta invisibili i combobox delle riviste e delle conferenze
        rivistaISSNComboBox.setVisible(false);
        conferenzaComboBox.setVisible(false);
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
                } else {
                    serieLibroBox.setEnabled(false);
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
                    checkAddArticolo();
                } else {
                    JOptionPane.showMessageDialog(null, "Selezionare una risorsa");
                }
            }

            private void checkAddLibro() {
                resetLibroBorders();
                int check = 0;
                if (String.valueOf(titoloLibroField.getText()).equals("Titolo")) {
                    titoloLibroField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                    check++;
                }
                if (String.valueOf(autoriLibroField.getText()).equals("Autori") || !String.valueOf(autoriLibroField.getText()).contains("_")) {
                    autoriLibroField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                    check++;
                }
                if (String.valueOf(genereLibroField.getText()).equals("Genere")) {
                    genereLibroField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                    check++;
                }
                if (String.valueOf(editoreLibroField.getText()).equals("Editore")) {
                    editoreLibroField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                    check++;
                }
                if (String.valueOf(prezzoLibroField.getText()).equals("Prezzo")) {
                    prezzoLibroField.setText("");
                }
                if (String.valueOf(isbnLibroField.getText()).equals("ISBN")) {
                    isbnLibroField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                    check++;
                }
                if (String.valueOf(dataPublicazioneLibroField.getText()).equals("Data di pubblicazione")) {
                    dataPublicazioneLibroField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                    check++;
                }
                if (String.valueOf(formatoLibroComboBox.getSelectedItem()).equals("Formato")) {
                    formatoLibroComboBox.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                    check++;
                }
                if (String.valueOf(linguaLibroField.getText()).equals("Lingua")) {
                    linguaLibroField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                    check++;
                }
                if (ilLibroFaParteCheckBox.isSelected()) {
                    if (String.valueOf(serieLibroBox.getSelectedItem()).equals("・・・Aggiungi nuova serie・・・")) {
                        addLibroSerie(check);
                    }
                } else {
                    addLibro(check);
                }
            }

            private void checkAddArticolo() {
                resetArticoloBorders();
                int check = 0;
                if (String.valueOf(titoloArticoliField.getText()).equals("Titolo")) {
                    titoloArticoliField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                    check++;
                }
                if (String.valueOf(autoriArticoloField.getText()).equals("Autori") || !String.valueOf(autoriArticoloField.getText()).contains("_")) {
                    autoriArticoloField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                    check++;
                }
                if (String.valueOf(editoreArticoliField.getText()).equals("Editore")) {
                    editoreArticoliField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                    check++;
                }
                if (String.valueOf(disciplinaArticoloField.getText()).equals("Disciplina")) {
                    disciplinaArticoloField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                    check++;
                }
                if (String.valueOf(formatoArticoliComboBox.getSelectedItem()).equals("Formato")) {
                    formatoArticoliComboBox.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                    check++;
                }
                if (String.valueOf(doiField.getText()).equals("DOI")) {
                    doiField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                    check++;
                }
                if (String.valueOf(presentatoInBox.getSelectedItem()).isBlank()) {
                    presentatoInBox.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                    check++;
                }
                if (check != 0) {
                    JOptionPane.showMessageDialog(null, "Controllare i campi in rosso");
                } else {
                    if (checkConferenzaOrRivista().equals("Rivista")) {
                        if (String.valueOf(rivistaComboBox.getSelectedItem()).equals("・・・Aggiungi nuova rivista・・・")) {
                            addArticoloAddRivista();
                        } else if(String.valueOf(rivistaComboBox.getSelectedItem()).isBlank()){
                            JOptionPane.showMessageDialog(null, "Selezionare una rivista");
                        } else{
                            addArticoloRivista();
                        }
                    }

            }
        }
        });
        presentatoInBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    if(String.valueOf(presentatoInBox.getSelectedItem()).equals("Rivista")){ //se è stato selezionato rivista
                        //rivistaPanel.setVisible(true);//rendo visibile il pannello rivista
                        conferenzaPanel.setVisible(false); //rendo invisibile il pannello conferenza
                        rivistaComboBox.setVisible(true);
                        conferenzaComboBox.setVisible(false);
                    } else if (String.valueOf(presentatoInBox.getSelectedItem()).equals("Conferenza")){ //se è stato selezionato conferenza
                        //conferenzaPanel.setVisible(true);//rendo visibile il pannello conferenza
                        rivistaPanel.setVisible(false); //rendo invisibile il pannello rivista
                        conferenzaComboBox.setVisible(true);
                        rivistaComboBox.setVisible(false);
                        rivistaISSNComboBox.setVisible(false);
                    } else {
                        conferenzaPanel.setVisible(false);
                        rivistaPanel.setVisible(false);
                        conferenzaComboBox.setVisible(false);
                        rivistaComboBox.setVisible(false);
                        rivistaISSNComboBox.setVisible(false);
                    }
                }
            }
        });
        helpLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                JOptionPane.showMessageDialog(autoriLibroField, """
                        Gli autori devono essere inseriti secondo il seguente \s
                        formato: Nome1_Cognome1, Nome2_Cognome2, ...""");
            }
        });
        helpLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                helpLabel.setBorder(BorderFactory.createLineBorder(Color.decode("#F39524"), 1));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                helpLabel.setBorder(null);
            }
        });
        rivistaComboBox.addItemListener(new ItemListener() { //qui
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    refreshRivistaFields();
                }
            }
        });

        conferenzaComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                refreshConferenzaFields();
                if(e.getStateChange() == ItemEvent.SELECTED){
                    if(!String.valueOf(conferenzaComboBox.getSelectedItem()).equals("・・・Aggiungi nuova conferenza・・・")){
                        conferenzaPanel.setVisible(false);
                    }else {
                        conferenzaPanel.setVisible(true);
                    }
                }
            }
        });
    }

    private void addArticoloConferenza() {
        if(checkConferenzaFields()) {
            //ArticoloController.getInstance().addArticolo();
            System.out.println("aggiungere query");
        }
        System.out.println("Implementare addArticoloConferenza");
    }

    private boolean checkConferenzaFields() {
        int check = 0;
        refreshConferenzaFields();
        if(String.valueOf(conferenzaComboBox.getSelectedItem()).isEmpty()){
            conferenzaComboBox.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            check++;
        }
        if(String.valueOf(nomeConferenzaField.getText()).equals("Nome")){
            nomeConferenzaField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            check++;
        }
        if(String.valueOf(responsabileConferenzaField.getText()).equals("Responsabile")){
            responsabileConferenzaField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            check++;
        }
        if(String.valueOf(strutturaConferenzaField.getText()).equals("Struttura Ospitante")){
            strutturaConferenzaField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            check++;
        }
        if(String.valueOf(indirizzoConferenzaField.getText()).equals("Indirizzo")){
            indirizzoConferenzaField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            check++;
        }
        if(String.valueOf(dataDaConferenzaField.getText()).equals("Data inizio")){
            dataDaConferenzaField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            check++;
        }
        if(dataAConferenzaField.getText().equals("Data fine")){
            dataAConferenzaField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            check++;
        }
        if(check == 0){
            return true;
        } else {
            return false;
        }
    }

    private void refreshConferenzaFields() {
        conferenzaComboBox.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        nomeConferenzaField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        responsabileConferenzaField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        strutturaConferenzaField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        indirizzoConferenzaField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        dataDaConferenzaField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        dataAConferenzaField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
    }

    private void addArticoloRivista() { //aggiunge articolo in rivista già presente
        if(ArticoloController.getInstance().addArticolo(titoloArticoliField.getText(), autoriArticoloField.getText(),
                editoreArticoliField.getText(), disciplinaArticoloField.getText(), String.valueOf(formatoArticoliComboBox.getSelectedItem()),
                doiField.getText(), linguaArticoloField.getText(), String.valueOf(rivistaComboBox.getSelectedItem()), String.valueOf(rivistaISSNComboBox.getSelectedItem()))){
            JOptionPane.showMessageDialog(null, "Articolo aggiunto con successo");
        };
    }

    private void addArticoloAddRivista() { //aggiunge sia articolo che rivista
        if(checkRivistaFields()) {
            ArticoloController.getInstance().addArticoloAddRivistaInDB(titoloArticoliField.getText(), autoriArticoloField.getText(),
                    editoreArticoliField.getText(), disciplinaArticoloField.getText(), String.valueOf(formatoArticoliComboBox.getSelectedItem()),
                    doiField.getText(), linguaArticoloField.getText(), nomeRivistaField.getText(), issnRivistaField.getText(), argomentoRivistaField.getText(),
                    datapubblicazioneRivistaField.getText(), responsabileRivistaField.getText(), Double.parseDouble(prezzoRivistaField.getText()));
            JOptionPane.showMessageDialog(null, "Articolo e rivista aggiunti con successo");
        }
        else {
            JOptionPane.showMessageDialog(null, "Errore nell'inserimento");
        }

    }

    private boolean checkRivistaFields() {
        int check = 0;
        refreshRivistaFields();
        if(String.valueOf(rivistaComboBox.getSelectedItem()).isEmpty()){
            rivistaComboBox.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            check++;
        }
        if(String.valueOf(nomeRivistaField.getText()).equals("Nome")){
            nomeRivistaField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            check++;
        }
        if(String.valueOf(issnRivistaField.getText()).equals("ISSN")){
            issnRivistaField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            check++;
        }
        if(String.valueOf(argomentoRivistaField.getText()).equals("Argomento")){
            argomentoRivistaField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            check++;
        }
        if(String.valueOf(responsabileRivistaField.getText()).equals("Responsabile")){
            responsabileRivistaField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            check++;
        }
        if(String.valueOf(datapubblicazioneRivistaField.getText()).equals("Data pubblicazione")){
            datapubblicazioneRivistaField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            check++;
        }
        if(String.valueOf(prezzoRivistaField.getText()).equals("Prezzo")){
            prezzoRivistaField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            check++;
        }
        if(String.valueOf(datapubblicazioneRivistaField.getText()).equals("Data di pubblicazione")){
            datapubblicazioneRivistaField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            check++;
        }
        if(check==0){
            return true;
        } else {
            return false;
        }
    }

    private void refreshRivistaFields() { //qui2
        rivistaComboBox.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        nomeRivistaField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        issnRivistaField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        argomentoRivistaField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        responsabileRivistaField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        datapubblicazioneRivistaField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        prezzoRivistaField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        datapubblicazioneRivistaField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        if(String.valueOf(rivistaComboBox.getSelectedItem()).equals("・・・Aggiungi nuova rivista・・・")){
            rivistaPanel.setVisible(true);
            rivistaISSNComboBox.setVisible(false);
        } else if(String.valueOf(rivistaComboBox.getSelectedItem()).isBlank()){
            rivistaPanel.setVisible(false);
            rivistaISSNComboBox.setVisible(false);
            rivistaComboBox.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        } else {
            rivistaPanel.setVisible(false);
            rivistaISSNComboBox.setVisible(true);
        }
        fillComboBox(rivistaISSNComboBox, FiltriController.getInstance().leggiRivisteISSN(String.valueOf(rivistaComboBox.getSelectedItem())));
    }

    private String checkConferenzaOrRivista() {
        return String.valueOf(presentatoInBox.getSelectedItem());
        }

    private void resetArticoloBorders(){ //resetta i bordi di tutti i JTextField di Articoli, conferenze e riviste associate
        //reset articolo
        titoloArticoliField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        autoriArticoloField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        editoreArticoliField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        titoloArticoliField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        disciplinaArticoloField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        formatoArticoliComboBox.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        doiField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        presentatoInBox.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        rivistaComboBox.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        //reset conferenza
        conferenzaComboBox.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        nomeConferenzaField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        responsabileConferenzaField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        strutturaConferenzaField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        indirizzoConferenzaField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        dataDaConferenzaField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        dataAConferenzaField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        //reset rivista
        nomeRivistaField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        issnRivistaField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        argomentoRivistaField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        responsabileRivistaField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        datapubblicazioneRivistaField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        prezzoRivistaField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
    }

    private void resetLibroBorders() { //resetta i bordi di tutti i JTextField
        titoloLibroField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        genereLibroField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        autoriLibroField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        editoreLibroField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        prezzoLibroField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        isbnLibroField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        dataPublicazioneLibroField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        formatoLibroComboBox.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        linguaLibroField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
    }

    private void addLibro(int check) {
        if(check==0) {
            LibroController.addLibroInDB(titoloLibroField.getText(), genereLibroField.getText(),
                    autoriLibroField.getText(), editoreLibroField.getText(),
                    prezzoLibroField.getText(), isbnLibroField.getText(),
                    dataPublicazioneLibroField.getText(),
                    String.valueOf(formatoLibroComboBox.getSelectedItem()), linguaLibroField.getText(),
                    null, null);
        }
        else {
            JOptionPane.showMessageDialog(null, "Compilare i campi in rosso");
        }
    }

    private void addLibroSerie(int check) {
        if(check==0) {
            LibroController.addLibroInDB(titoloLibroField.getText(), genereLibroField.getText(),
                    autoriLibroField.getText(), editoreLibroField.getText(),
                    prezzoLibroField.getText(), isbnLibroField.getText(),
                    dataPublicazioneLibroField.getText(),
                    String.valueOf(formatoLibroComboBox.getSelectedItem()), linguaLibroField.getText(),
                    nomeSerieField.getText(), issnSerieField.getText());
        }
        else {
            JOptionPane.showMessageDialog(null, "Compilare i campi in rosso");
        }
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
        setPlaceholderText(disciplinaArticoloField, "Disciplina");
        setPlaceholderText(autoriArticoloField, "Autori");
        setPlaceholderText(editoreArticoliField, "Editore");
        setPlaceholderText(issnSerieField, "ISSN");
        setPlaceholderText(nomeSerieField, "Nome");
        setPlaceholderText(nomeConferenzaField, "Nome");
        setPlaceholderText(dataDaConferenzaField, "Data inizio");
        setPlaceholderText(dataAConferenzaField, "Data fine");
        setPlaceholderText(strutturaConferenzaField, "Struttura Ospitante");
        setPlaceholderText(indirizzoConferenzaField, "Indirizzo");
        setPlaceholderText(responsabileConferenzaField, "Responsabile");
        setPlaceholderText(argomentoRivistaField, "Argomento");
        setPlaceholderText(datapubblicazioneRivistaField, "Data di pubblicazione");
        setPlaceholderText(issnRivistaField, "ISSN");
        setPlaceholderText(responsabileRivistaField, "Responsabile");
        setPlaceholderText(prezzoRivistaField, "Prezzo");
        setPlaceholderText(nomeRivistaField, "Nome");
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
        fillComboBox(rivistaComboBox, FiltriController.getInstance().leggiRiviste());
        fillComboBox(conferenzaComboBox, FiltriController.getInstance().leggiConferenze());
        fillComboBox(rivistaISSNComboBox, FiltriController.getInstance().leggiRivisteISSN(String.valueOf(rivistaComboBox.getSelectedItem())));
    }

    private void fillComboBox(JComboBox<String> comboBox, ArrayList<String> items) {
        comboBox.removeAllItems();
        if(comboBox.equals(serieLibroBox)){
            comboBox.addItem("");
            comboBox.addItem("・・・Aggiungi nuova serie・・・");
            comboBox.addItem("");
        }
        if(comboBox.equals(rivistaComboBox)){
            comboBox.addItem("");
            comboBox.addItem("・・・Aggiungi nuova rivista・・・");
            comboBox.addItem("");
        }
        if(comboBox.equals(conferenzaComboBox)){
            comboBox.addItem("");
            comboBox.addItem("・・・Aggiungi nuova conferenza・・・");
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
