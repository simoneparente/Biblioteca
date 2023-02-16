package org.Bibliotech.View;

import org.Bibliotech.Controller.UtenteController;
import org.Bibliotech.Model.Utente;

import javax.swing.*;
import java.awt.event.*;

public class ProfiloView extends View {
    private static ProfiloView instance = null;
    private static final String nome = "ProfiloView";
    private JPanel rootPanel;
    private JLabel usernameLabel;
    private JLabel permessiLabel;
    private JLabel cambiaPasswordTextArea;
    private JPanel infoPanel;
    private JPasswordField vecchiaPasswordField;
    private JPasswordField nuovaPasswordField;
    private JPasswordField confermaPasswordField;
    private JCheckBox mostraPasswordCheckBox;
    private JPanel passwordPanel;
    private JLabel vecchiaPasswordLabel;
    private JLabel nuovaPasswordLabel;
    private JLabel confermaPassordLabel;
    private JButton confermaButton;
    private JList notificheList;
    private JScrollPane notificheScrollPane;

    private ProfiloView() {
        super(nome);
        super.setContentPane(rootPanel);
        super.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        WindowListener closeWindow = new WindowAdapter() {//listener per la chiusura della finestra
            @Override
            public void windowClosing(WindowEvent we) {
                int result = JOptionPane.showConfirmDialog(null, "Vuoi tornare alla pagina di ricerca?", "Conferma uscita", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    ProfiloView.super.dispose(); //chiude la finestra
                    SearchView.getInstance().showView(); //mostra la finestra di ricerca
                }
            }
        };
        super.addWindowListener(closeWindow);
        setVisible(true);
        usernameLabel.setText("Username: " + Utente.getInstance().getUsername());
        permessiLabel.setText("Permessi: " + Utente.getInstance().getPermessi());
        passwordPanel.setVisible(false);

        cambiaPasswordTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                passwordPanel.setVisible(!passwordPanel.isVisible());
            }
        });
        mostraPasswordCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (mostraPasswordCheckBox.isSelected()) {
                    vecchiaPasswordField.setEchoChar((char) 0);
                    nuovaPasswordField.setEchoChar((char) 0);
                    confermaPasswordField.setEchoChar((char) 0);
                } else {
                    vecchiaPasswordField.setEchoChar('•');
                    nuovaPasswordField.setEchoChar('•');
                    confermaPasswordField.setEchoChar('•');
                }
            }
        });
        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String vecchiaPassword = new String(vecchiaPasswordField.getPassword());
                String nuovaPassword = new String(nuovaPasswordField.getPassword());
                String confermaPassword = new String(confermaPasswordField.getPassword());
                if(checkPasswordFields(vecchiaPassword, nuovaPassword, confermaPassword)){
                    if(UtenteController.getInstance().cambiaPassword(Utente.getInstance().getUsername(), Utente.getInstance().getPassword(), nuovaPassword)){
                        JOptionPane.showMessageDialog(null, "Password cambiata con successo", "Successo", JOptionPane.INFORMATION_MESSAGE);
                        Utente.getInstance().setPassword(nuovaPassword);
                        passwordPanel.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Errore nel cambiamento della password", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    private boolean checkPasswordFields(String vecchiaPassword, String nuovaPassword, String confermaPassword) {
        if (vecchiaPassword.isBlank() || nuovaPassword.isBlank() || confermaPassword.isBlank()) {
            JOptionPane.showMessageDialog(null, "Inserisci tutte le password", "Errore", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (!vecchiaPassword.equals(Utente.getInstance().getPassword())) {
            JOptionPane.showMessageDialog(null, "La vecchia password non è corretta", "Errore", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (!nuovaPassword.equals(confermaPassword)) {
            JOptionPane.showMessageDialog(null, "Le password non coincidono", "Errore", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else{
            return true;
        }
    }

    public static ProfiloView getInstance() {
        if (instance == null) {
            instance = new ProfiloView();
        }
        return instance;
    }
}

