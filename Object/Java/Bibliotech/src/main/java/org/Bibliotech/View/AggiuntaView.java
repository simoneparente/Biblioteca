package org.Bibliotech.View;

import org.Bibliotech.Controller.Controller;
import org.Bibliotech.Controller.RisorsaController;
import org.Bibliotech.DAOimplementazione.ImplementazioneRisorsa;
import org.Bibliotech.Model.Articolo;
import org.Bibliotech.Model.Conferenza;
import org.Bibliotech.Model.Rivista;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AggiuntaView extends View {
    private static final String nome = "Add resource";
    private static AggiuntaView instance;
    private JPanel rootPanel;
    private JPanel imagePanel;
    private JPanel risorsaPanel;
    private JPanel libriPanel;
    private JPanel articoliPanel;
    private JComboBox<String> risorsaComboBox;
    private JButton addButton;
    private JTextField titoloLibroField;
    private JTextField autoriLibroField;
    private JTextField editoreLibroField;
    private JTextField genereLibroField;
    private JTextField prezzoLibroField;
    private JCheckBox libroFaParteCheckBox;
    private JComboBox<String> serieLibroBox;
    private JTextField titoloArticoliField;
    private JTextField disciplinaArticoloField;
    private JTextField autoriArticoloField;
    private JComboBox<String> formatoArticoliComboBox;
    private JTextField editoreArticoliField;
    private JTextField doiField;
    private JComboBox<String> presentatoInBox;
    private JTextField isbnLibroField;
    private JTextField dataPublicazioneLibroField;
    private JLabel logoLabel;
    private JButton annullaButton;
    private JPanel seriePanel;
    private JTextField nomeSerieField;
    private JTextField issnSerieField;
    private JComboBox<String> formatoLibroComboBox;
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
    private JComboBox<String> rivistaComboBox;
    private JComboBox<String> conferenzaComboBox;
    private JPanel rivistaConferenzaPanel;
    private JComboBox<String> rivistaISSNComboBox;
    private JTextField linguaArticoloField;
    private JComboBox<String> conferenzaDataInizioComboBox;
    private JComboBox issnSerieLibroBox;

    public AggiuntaView() {
        super(nome);
        logoLabel.setIcon(logoLabelIcon); //setta l'icona del logo (logoLabelIcon viene presa da superclasse View)
        this.setVisible(true);
        this.setContentPane(rootPanel); //setta il contentPanel come contentPane del JFrame
        setAllPlaceHolders();
        setPanelInvisibili();
        serieLibroBox.setEnabled(false); //disabilita il combobox delle serie
        issnSerieLibroBox.setVisible(false);
        rivistaComboBox.setVisible(false); //setta invisibili i combobox delle riviste e delle conferenze
        rivistaISSNComboBox.setVisible(false);
        conferenzaComboBox.setVisible(false);
        conferenzaDataInizioComboBox.setVisible(false);
        fillAllComboBoxes();
        risorsaComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    ricaricaPanel(String.valueOf(risorsaComboBox.getSelectedItem()));
                }
            }
        });
        libroFaParteCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serieLibroBox.setEnabled(libroFaParteCheckBox.isSelected());
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
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (!serieLibroBox.getSelectedItem().equals("・・・Aggiungi nuova serie・・・")) {
                        seriePanel.setVisible(false);
                        if(!(serieLibroBox.getSelectedItem().equals(""))) {
                            issnSerieLibroBox.setVisible(true);
                            issnSerieLibroBox.removeAllItems();
                            fillComboBox(issnSerieLibroBox, RisorsaController.getInstance().getIssnSerie(serieLibroBox.getSelectedItem().toString()));
                        }else{
                            issnSerieLibroBox.setVisible(false);
                        }
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
                    titoloLibroField.setBorder(getRedBorder());
                    check++;
                }
                if (String.valueOf(autoriLibroField.getText()).equals("Autori") || !String.valueOf(autoriLibroField.getText()).contains("_")) {
                    autoriLibroField.setBorder(getRedBorder());
                    check++;
                }
                if (String.valueOf(genereLibroField.getText()).equals("Genere")) {
                    genereLibroField.setBorder(getRedBorder());
                    check++;
                }
                if (String.valueOf(editoreLibroField.getText()).equals("Editore")) {
                    editoreLibroField.setBorder(getRedBorder());
                    check++;
                }
                if (String.valueOf(prezzoLibroField.getText()).equals("Prezzo")) {
                    prezzoLibroField.setText("");
                }
                if (String.valueOf(isbnLibroField.getText()).equals("ISBN")) {
                    isbnLibroField.setBorder(getRedBorder());
                    check++;
                }
                if (String.valueOf(dataPublicazioneLibroField.getText()).equals("Data di pubblicazione")) {
                    dataPublicazioneLibroField.setBorder(getRedBorder());
                    check++;
                }
                if (String.valueOf(formatoLibroComboBox.getSelectedItem()).equals("Formato")) {
                    formatoLibroComboBox.setBorder(getRedBorder());
                    check++;
                }
                if (String.valueOf(linguaLibroField.getText()).equals("Lingua")) {
                    linguaLibroField.setBorder(getRedBorder());
                    check++;
                }
                if (libroFaParteCheckBox.isSelected()) {
                    if(String.valueOf(serieLibroBox.getSelectedItem()).isBlank()){
                        serieLibroBox.setBorder(getRedBorder());
                    } else {
                        serieLibroBox.setBorder(getDefaultJComboBoxBorder());
                        if (String.valueOf(serieLibroBox.getSelectedItem()).equals("・・・Aggiungi nuova serie・・・")) {
                            addLibroAddSerie(check);
                        }
                        else{
                            addLibroSerie(check);
                        }
                    }
                } else {
                    addLibro(check);
                }
            }

            private void checkAddArticolo() {
                resetAllArticoloBorders();
                int check = 0;
                if (String.valueOf(titoloArticoliField.getText()).equals("Titolo")) {
                    titoloArticoliField.setBorder(getRedBorder());
                    check++;
                }
                if (String.valueOf(autoriArticoloField.getText()).equals("Autori") || !String.valueOf(autoriArticoloField.getText()).contains("_")) {
                    autoriArticoloField.setBorder(getRedBorder());
                    check++;
                }
                if (String.valueOf(editoreArticoliField.getText()).equals("Editore")) {
                    editoreArticoliField.setBorder(getRedBorder());
                    check++;
                }
                if (String.valueOf(disciplinaArticoloField.getText()).equals("Disciplina")) {
                    disciplinaArticoloField.setBorder(getRedBorder());
                    check++;
                }
                if (String.valueOf(formatoArticoliComboBox.getSelectedItem()).equals("Formato")) {
                    formatoArticoliComboBox.setBorder(getRedBorder());
                    check++;
                }
                if (String.valueOf(doiField.getText()).equals("DOI")) {
                    doiField.setBorder(getRedBorder());
                    check++;
                }
                if(linguaArticoloField.getText().equals("Lingua")){
                    linguaArticoloField.setBorder(getRedBorder());
                    check++;
                }
                if (String.valueOf(presentatoInBox.getSelectedItem()).isBlank()) {
                    presentatoInBox.setBorder(getRedBorder());
                    check++;
                }
                if (check != 0) {
                    JOptionPane.showMessageDialog(null, "Controllare i campi in rosso");
                } else {
                    if (checkConferenzaOrRivista().equals("Rivista")) {
                        if (String.valueOf(rivistaComboBox.getSelectedItem()).equals("・・・Aggiungi nuova rivista・・・")) {
                            addArticoloAddRivista();
                        } else if (String.valueOf(rivistaComboBox.getSelectedItem()).isBlank()) {
                            JOptionPane.showMessageDialog(null, "Selezionare una rivista");
                        } else {
                            addArticoloRivista();
                        }
                    } else if(checkConferenzaOrRivista().equals("Conferenza")){
                        if (String.valueOf(conferenzaComboBox.getSelectedItem()).equals("・・・Aggiungi nuova conferenza・・・")) {
                            System.out.println("Aggiungi conferenza");
                            addArticoloAddConferenza();
                        } else if (String.valueOf(conferenzaComboBox.getSelectedItem()).isBlank()) {
                            JOptionPane.showMessageDialog(null, "Selezionare una conferenza");
                        } else {
                            System.out.println("Aggiungi articolo");
                            addArticoloConferenza();
                        }
                    }

                }
            }
        });
        presentatoInBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (String.valueOf(presentatoInBox.getSelectedItem()).equals("Rivista")) { //se è stato selezionato rivista
                        presentatoInBox.setBorder(getDefaultJComboBoxBorder());
                        //rivistaPanel.setVisible(true);//rendo visibile il pannello rivista
                        conferenzaPanel.setVisible(false); //rendo invisibile il pannello conferenza
                        conferenzaDataInizioComboBox.setVisible(false);
                        rivistaComboBox.setVisible(true);
                        conferenzaComboBox.setVisible(false);
                    } else if (String.valueOf(presentatoInBox.getSelectedItem()).equals("Conferenza")) { //se è stato selezionato conferenza
                        //conferenzaPanel.setVisible(true);//rendo visibile il pannello conferenza
                        rivistaPanel.setVisible(false); //rendo invisibile il pannello rivista
                        conferenzaComboBox.setVisible(true);
                        conferenzaDataInizioComboBox.setVisible(false);
                        rivistaComboBox.setVisible(false);
                        rivistaISSNComboBox.setVisible(false);
                    } else {
                        conferenzaPanel.setVisible(false);
                        rivistaPanel.setVisible(false);
                        conferenzaComboBox.setVisible(false);
                        conferenzaDataInizioComboBox.setVisible(false);
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
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    refreshRivistaFields();
                }
            }
        });

        conferenzaComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                refreshConferenzaFields();
                if (e.getStateChange() == ItemEvent.SELECTED) {

                    conferenzaPanel.setVisible(String.valueOf(conferenzaComboBox.getSelectedItem()).equals("・・・Aggiungi nuova conferenza・・・"));
                    //conferenzaDataInizioComboBox.setVisible(!String.valueOf(conferenzaComboBox.getSelectedItem()).equals("・・・Aggiungi nuova conferenza・・・"));
                    conferenzaDataInizioComboBox.setVisible((!String.valueOf(conferenzaComboBox.getSelectedItem()).equals("・・・Aggiungi nuova conferenza・・・")) && !(String.valueOf(conferenzaComboBox.getSelectedItem()).equals("")));
                    if(!conferenzaPanel.isVisible()){
                        fillComboBox(conferenzaDataInizioComboBox, RisorsaController.getInstance().getConferenzaDataInizio(String.valueOf(conferenzaComboBox.getSelectedItem())));
                }
            }
        }
        });
    }

    private void addArticoloConferenza() {
        Articolo articolo=new Articolo(titoloArticoliField.getText(),
                autoriArticoloField.getText(), editoreArticoliField.getText(), disciplinaArticoloField.getText(),
                String.valueOf(formatoArticoliComboBox.getSelectedItem()), doiField.getText(),
                linguaArticoloField.getText());

        if(RisorsaController.getInstance().addArticoloConferenza(articolo, String.valueOf(conferenzaComboBox.getSelectedItem()),
                String.valueOf(conferenzaDataInizioComboBox.getSelectedItem()))){
                JOptionPane.showMessageDialog(null, "Articolo aggiunto con successo");
            } else {
                JOptionPane.showMessageDialog(null, "Errore nell'aggiunta dell'articolo");
            }
    }

    private Border getDefaultJComboBoxBorder() {
        return UIManager.getLookAndFeel().getDefaults().getBorder("ComboBox.border");

    }

    public static AggiuntaView getInstance() {
        if (instance == null) {
            instance = new AggiuntaView();
        }
        return instance;
    }

    private Border getRedBorder() {
        return BorderFactory.createLineBorder(Color.RED, 2);
    }

    private void addArticoloAddConferenza() {
        if (checkConferenzaFields()) {
            Articolo articolo= new Articolo(titoloArticoliField.getText(), autoriArticoloField.getText(),
                    editoreArticoliField.getText(), disciplinaArticoloField.getText(),
                    String.valueOf(formatoArticoliComboBox.getSelectedItem()), doiField.getText(),
                    linguaArticoloField.getText());
            Conferenza conferenza = new Conferenza(nomeConferenzaField.getText(), responsabileConferenzaField.getText(),
                    strutturaConferenzaField.getText(), indirizzoConferenzaField.getText(), dataDaConferenzaField.getText(),
                    dataAConferenzaField.getText());
            if(RisorsaController.getInstance().addArticoloAddConferenza(articolo, conferenza)) {
                JOptionPane.showMessageDialog(null, "Articolo aggiunto con successo");
            } else {
                JOptionPane.showMessageDialog(null, "Errore nell'aggiunta dell'articolo");
            }
        }
    }

    private boolean checkConferenzaFields() {
        int check = 0;
        refreshConferenzaFields();
        if(String.valueOf(conferenzaComboBox.getSelectedItem()).equals("・・・Aggiungi nuova conferenza・・・")) {
            if (String.valueOf(conferenzaComboBox.getSelectedItem()).isEmpty()) {
                conferenzaComboBox.setBorder(getRedBorder());
                check++;
            }
            if (String.valueOf(nomeConferenzaField.getText()).equals("Nome")) {
                nomeConferenzaField.setBorder(getRedBorder());
                check++;
            }
            if (String.valueOf(responsabileConferenzaField.getText()).equals("Responsabile")) {
                responsabileConferenzaField.setBorder(getRedBorder());
                check++;
            }
            if (String.valueOf(strutturaConferenzaField.getText()).equals("Struttura Ospitante")) {
                strutturaConferenzaField.setBorder(getRedBorder());
                check++;
            }
            if (String.valueOf(indirizzoConferenzaField.getText()).equals("Indirizzo")) {
                indirizzoConferenzaField.setBorder(getRedBorder());
                check++;
            }
            if (String.valueOf(dataDaConferenzaField.getText()).equals("Data inizio")) {
                dataDaConferenzaField.setBorder(getRedBorder());
                check++;
            }
            if (dataAConferenzaField.getText().equals("Data fine")) {
                dataAConferenzaField.setBorder(getRedBorder());
                check++;
            }
        }
        return check == 0;
    }

    private void refreshConferenzaFields() {
        conferenzaComboBox.setBorder(getDefaultJTextFieldBorder());
        nomeConferenzaField.setBorder(getDefaultJTextFieldBorder());
        responsabileConferenzaField.setBorder(getDefaultJTextFieldBorder());
        strutturaConferenzaField.setBorder(getDefaultJTextFieldBorder());
        indirizzoConferenzaField.setBorder(getDefaultJTextFieldBorder());
        dataDaConferenzaField.setBorder(getDefaultJTextFieldBorder());
        dataAConferenzaField.setBorder(getDefaultJTextFieldBorder());
    }

    private void addArticoloRivista() { //aggiunge articolo in rivista già presente
        Articolo articolo = new Articolo(titoloArticoliField.getText(), autoriArticoloField.getText(),
                editoreArticoliField.getText(), disciplinaArticoloField.getText(), String.valueOf(formatoArticoliComboBox.getSelectedItem()),
                doiField.getText(), linguaArticoloField.getText());
        if (RisorsaController.getInstance().addArticoloRivista(articolo, String.valueOf(rivistaComboBox.getSelectedItem()), String.valueOf(rivistaISSNComboBox.getSelectedItem()))) {
            JOptionPane.showMessageDialog(null, "Articolo aggiunto con successo");
        }
    }

    private void addArticoloAddRivista() { //aggiunge sia articolo che rivista
        if (checkRivistaFields()) {
            Articolo articolo = new Articolo(titoloArticoliField.getText(), autoriArticoloField.getText(),
                    editoreArticoliField.getText(), disciplinaArticoloField.getText(), String.valueOf(formatoArticoliComboBox.getSelectedItem()),
                    doiField.getText(), linguaArticoloField.getText());
            Rivista rivista= new Rivista(nomeRivistaField.getText(), issnRivistaField.getText(), argomentoRivistaField.getText(),
                    datapubblicazioneRivistaField.getText(), responsabileRivistaField.getText(), Double.parseDouble(prezzoRivistaField.getText()));
            RisorsaController.getInstance().addArticoloAddRivistaInDB(articolo, rivista);
            JOptionPane.showMessageDialog(null, "Articolo e rivista aggiunti con successo");
        } else {
            JOptionPane.showMessageDialog(null, "Errore nell'inserimento");
        }
    }


    private boolean checkRivistaFields() {
        int check = 0;
        refreshRivistaFields();
        if (String.valueOf(rivistaComboBox.getSelectedItem()).isEmpty()) {
            rivistaComboBox.setBorder(getRedBorder());
            check++;
        }
        if (String.valueOf(nomeRivistaField.getText()).equals("Nome")) {
            nomeRivistaField.setBorder(getRedBorder());
            check++;
        }
        if (String.valueOf(issnRivistaField.getText()).equals("ISSN")) {
            issnRivistaField.setBorder(getRedBorder());
            check++;
        }
        if (String.valueOf(argomentoRivistaField.getText()).equals("Argomento")) {
            argomentoRivistaField.setBorder(getRedBorder());
            check++;
        }
        if (String.valueOf(responsabileRivistaField.getText()).equals("Responsabile")) {
            responsabileRivistaField.setBorder(getRedBorder());
            check++;
        }
        if (String.valueOf(datapubblicazioneRivistaField.getText()).equals("Data pubblicazione")) {
            datapubblicazioneRivistaField.setBorder(getRedBorder());
            check++;
        }
        if (String.valueOf(prezzoRivistaField.getText()).equals("Prezzo")) {
            prezzoRivistaField.setBorder(getRedBorder());
            check++;
        }
        if (String.valueOf(datapubblicazioneRivistaField.getText()).equals("Data di pubblicazione")) {
            datapubblicazioneRivistaField.setBorder(getRedBorder());
            check++;
        }
        return check == 0;
    }

    private void refreshRivistaFields() { //refresha i campi della rivista
        rivistaComboBox.setBorder(getDefaultJComboBoxBorder());
        nomeRivistaField.setBorder(getDefaultJTextFieldBorder());
        issnRivistaField.setBorder(getDefaultJTextFieldBorder());
        argomentoRivistaField.setBorder(getDefaultJTextFieldBorder());
        responsabileRivistaField.setBorder(getDefaultJTextFieldBorder());
        datapubblicazioneRivistaField.setBorder(getDefaultJTextFieldBorder());
        prezzoRivistaField.setBorder(getDefaultJTextFieldBorder());
        datapubblicazioneRivistaField.setBorder(getDefaultJTextFieldBorder());
        if (String.valueOf(rivistaComboBox.getSelectedItem()).equals("・・・Aggiungi nuova rivista・・・")) {
            rivistaPanel.setVisible(true);
            rivistaISSNComboBox.setVisible(false);
        } else if (String.valueOf(rivistaComboBox.getSelectedItem()).isBlank()) {
            rivistaPanel.setVisible(false);
            rivistaISSNComboBox.setVisible(false);
            rivistaComboBox.setBorder(getRedBorder());
        } else {
            rivistaPanel.setVisible(false);
            rivistaISSNComboBox.setVisible(true);
        }
        fillComboBox(rivistaISSNComboBox, RisorsaController.getInstance().leggiRivisteISSN(String.valueOf(rivistaComboBox.getSelectedItem())));
    }

    private String checkConferenzaOrRivista() {
        return String.valueOf(presentatoInBox.getSelectedItem());
    }

    private void resetAllArticoloBorders() { //resetta i bordi di tutti i JTextField di Libro, Articolo, Conferenza e Rivista
        resetArticoloBorders();
        resetConferenzaBorders();
        resetRivistaBorders();
    }

    private void resetRivistaBorders() { //resetta i bordi di tutti i JTextField di Rivista
        nomeRivistaField.setBorder(getDefaultJTextFieldBorder());
        issnRivistaField.setBorder(getDefaultJTextFieldBorder());
        argomentoRivistaField.setBorder(getDefaultJTextFieldBorder());
        responsabileRivistaField.setBorder(getDefaultJTextFieldBorder());
        datapubblicazioneRivistaField.setBorder(getDefaultJTextFieldBorder());
        prezzoRivistaField.setBorder(getDefaultJTextFieldBorder());
    }

    private void resetConferenzaBorders() { //resetta i bordi di tutti i JTextField di Conferenza
        conferenzaComboBox.setBorder(getDefaultJComboBoxBorder());
        conferenzaDataInizioComboBox.setBorder(getDefaultJComboBoxBorder());
        nomeConferenzaField.setBorder(getDefaultJTextFieldBorder());
        responsabileConferenzaField.setBorder(getDefaultJTextFieldBorder());
        strutturaConferenzaField.setBorder(getDefaultJTextFieldBorder());
        indirizzoConferenzaField.setBorder(getDefaultJTextFieldBorder());
        dataDaConferenzaField.setBorder(getDefaultJTextFieldBorder());
        dataAConferenzaField.setBorder(getDefaultJTextFieldBorder());
    }

    private void resetArticoloBorders() { //resetta i bordi di tutti i JTextField di Articolo
        titoloArticoliField.setBorder(getDefaultJTextFieldBorder());
        autoriArticoloField.setBorder(getDefaultJTextFieldBorder());
        editoreArticoliField.setBorder(getDefaultJTextFieldBorder());
        titoloArticoliField.setBorder(getDefaultJTextFieldBorder());
        disciplinaArticoloField.setBorder(getDefaultJTextFieldBorder());
        formatoArticoliComboBox.setBorder(getDefaultJComboBoxBorder());
        doiField.setBorder(getDefaultJTextFieldBorder());
        linguaArticoloField.setBorder(getDefaultJTextFieldBorder());
        presentatoInBox.setBorder(getDefaultJTextFieldBorder());
        rivistaComboBox.setBorder(getDefaultJComboBoxBorder());
    }

    private Border getDefaultJTextFieldBorder() {
        return UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border");
    }

    private void resetLibroBorders() { //resetta i bordi di tutti i JTextField
        titoloLibroField.setBorder(getDefaultJTextFieldBorder());
        genereLibroField.setBorder(getDefaultJTextFieldBorder());
        autoriLibroField.setBorder(getDefaultJTextFieldBorder());
        editoreLibroField.setBorder(getDefaultJTextFieldBorder());
        prezzoLibroField.setBorder(getDefaultJTextFieldBorder());
        isbnLibroField.setBorder(getDefaultJTextFieldBorder());
        dataPublicazioneLibroField.setBorder(getDefaultJTextFieldBorder());
        formatoLibroComboBox.setBorder(getDefaultJComboBoxBorder());
        linguaLibroField.setBorder(getDefaultJTextFieldBorder());
    }

    private void addLibro(int check) {
        if (check == 0) {
            RisorsaController.getInstance().addLibroInDB(titoloLibroField.getText(), genereLibroField.getText(),
                    autoriLibroField.getText(), editoreLibroField.getText(),
                    Double.parseDouble(prezzoLibroField.getText()), isbnLibroField.getText(),
                    dataPublicazioneLibroField.getText(),
                    String.valueOf(formatoLibroComboBox.getSelectedItem()), linguaLibroField.getText(),
                    null, null);
        } else {
            JOptionPane.showMessageDialog(null, "Compilare i campi in rosso");
        }
    }

    private void addLibroSerie(int check) {
        if (check == 0) {
            RisorsaController.getInstance().addLibroInDB(titoloLibroField.getText(), genereLibroField.getText(),
                    autoriLibroField.getText(), editoreLibroField.getText(),
                    Double.parseDouble(prezzoLibroField.getText()), isbnLibroField.getText(),
                    dataPublicazioneLibroField.getText(),
                    String.valueOf(formatoLibroComboBox.getSelectedItem()), linguaLibroField.getText(),
                    String.valueOf(String.valueOf(serieLibroBox.getSelectedItem())), String.valueOf(issnSerieLibroBox.getSelectedItem()));
        } else {
            JOptionPane.showMessageDialog(null, "Compilare i campi in rosso");
        }
    }
    private void addLibroAddSerie(int check) {
        if (check == 0) {
            RisorsaController.getInstance().addLibroInDB(titoloLibroField.getText(), genereLibroField.getText(),
                    autoriLibroField.getText(), editoreLibroField.getText(),
                    Double.parseDouble(prezzoLibroField.getText()), isbnLibroField.getText(),
                    dataPublicazioneLibroField.getText(),
                    String.valueOf(formatoLibroComboBox.getSelectedItem()), linguaLibroField.getText(),
                    nomeSerieField.getText(), issnSerieField.getText());
        } else {
            JOptionPane.showMessageDialog(null, "Compilare i campi in rosso");
        }
    }

    public void setAllPlaceHolders() {
        setLibroPlaceHolders();
        setArticoloPlaceHolders();
        setConferenzaPlaceHolders();
        setRivistaPlaceHolders();
    }

    private void setRivistaPlaceHolders() {
        setPlaceholderText(argomentoRivistaField, "Argomento");
        setPlaceholderText(datapubblicazioneRivistaField, "Data di pubblicazione");
        setPlaceholderText(issnRivistaField, "ISSN");
        setPlaceholderText(responsabileRivistaField, "Responsabile");
        setPlaceholderText(prezzoRivistaField, "Prezzo");
        setPlaceholderText(nomeRivistaField, "Nome");
    }

    private void setConferenzaPlaceHolders() {
        setPlaceholderText(issnSerieField, "ISSN");
        setPlaceholderText(nomeSerieField, "Nome");
        setPlaceholderText(nomeConferenzaField, "Nome");
        setPlaceholderText(dataDaConferenzaField, "Data inizio");
        setPlaceholderText(dataAConferenzaField, "Data fine");
        setPlaceholderText(strutturaConferenzaField, "Struttura Ospitante");
        setPlaceholderText(indirizzoConferenzaField, "Indirizzo");
        setPlaceholderText(responsabileConferenzaField, "Responsabile");
    }

    private void setArticoloPlaceHolders() {
        setPlaceholderText(doiField, "DOI");
        setPlaceholderText(titoloArticoliField, "Titolo");
        setPlaceholderText(disciplinaArticoloField, "Disciplina");
        setPlaceholderText(autoriArticoloField, "Autori");
        setPlaceholderText(editoreArticoliField, "Editore");
        setPlaceholderText(linguaArticoloField, "Lingua");
    }

    private void setLibroPlaceHolders() {
        setPlaceholderText(titoloLibroField, "Titolo");
        setPlaceholderText(autoriLibroField, "Autori");
        setPlaceholderText(editoreLibroField, "Editore");
        setPlaceholderText(genereLibroField, "Genere");
        setPlaceholderText(prezzoLibroField, "Prezzo");
        setPlaceholderText(isbnLibroField, "ISBN");
        setPlaceholderText(dataPublicazioneLibroField, "Data di pubblicazione");
        setPlaceholderText(linguaLibroField, "Lingua");
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
        fillComboBox(serieLibroBox, RisorsaController.getInstance().leggiSerieLibri());
        fillComboBox(issnSerieLibroBox, RisorsaController.getInstance().getIssnSerie(String.valueOf(serieLibroBox.getSelectedItem())));
        fillComboBox(rivistaComboBox, RisorsaController.getInstance().leggiRiviste());
        fillComboBox(conferenzaComboBox, RisorsaController.getInstance().leggiConferenze());
        fillComboBox(rivistaISSNComboBox, RisorsaController.getInstance().leggiRivisteISSN(String.valueOf(rivistaComboBox.getSelectedItem())));
    }

    private void fillComboBox(JComboBox<String> comboBox, ArrayList<String> items) {
        comboBox.removeAllItems();
        if (comboBox.equals(serieLibroBox)) {
            comboBox.addItem("");
            comboBox.addItem("・・・Aggiungi nuova serie・・・");
            comboBox.addItem("");
        }
        if (comboBox.equals(rivistaComboBox)) {
            comboBox.addItem("");
            comboBox.addItem("・・・Aggiungi nuova rivista・・・");
            comboBox.addItem("");
        }
        if (comboBox.equals(conferenzaComboBox)) {
            comboBox.addItem("");
            comboBox.addItem("・・・Aggiungi nuova conferenza・・・");
            comboBox.addItem("");

        }
        for (String item : items) {
            comboBox.addItem(item);
        }
    }

    private void setPanelInvisibili() {
        libriPanel.setVisible(false);
        articoliPanel.setVisible(false);
        seriePanel.setVisible(false);
        rivistaPanel.setVisible(false);
        conferenzaPanel.setVisible(false);
    }

    private void ricaricaPanel(String risorsa) {
        setPanelInvisibili();
        switch (risorsa) {
            case "Libro" -> libriPanel.setVisible(true);
            case "Articolo" -> articoliPanel.setVisible(true);
            default -> {
            }
        }
    }
}
