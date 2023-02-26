package org.Bibliotech.View;

import org.Bibliotech.Controller.Controller;
import org.Bibliotech.Controller.FiltriController;
import org.Bibliotech.Controller.UtenteController;
import org.Bibliotech.Model.Utente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

public class ProfiloView extends View {
    private static final String nome = "ProfiloView";
    private static final Image reloadImage = defaultToolkit.getImage("src/main/Immagini/reload.png");
    private static final ImageIcon reloadIconImageIcon = new ImageIcon(reloadImage);
    private static ProfiloView instance = null;
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
    private JLabel reloadIcon;
    private JTable notificheTable;
    private JScrollPane notificheScrollPanel;
    private JPanel notifichePanel;

    private ProfiloView() {
        super(nome);
        super.setContentPane(rootPanel);
        super.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        WindowListener closeWindow = new WindowAdapter() {//listener per la chiusura della finestra
            @Override
            public void windowClosing(WindowEvent we) {
                Controller.getInstance().switchView(SearchView.getInstance(), ProfiloView.getInstance());
            }
        };
        super.addWindowListener(closeWindow);
        setVisible(true);
        usernameLabel.setText("Username: " + Utente.getInstance().getUsername());
        permessiLabel.setText("Permessi: " + Utente.getInstance().getPermessi());
        passwordPanel.setVisible(false);
        reloadIcon.setIcon(reloadIconImageIcon);
        notificheTable.setDefaultEditor(Object.class, null);
        refreshTable();

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
                String vecchiaPassword = String.valueOf((vecchiaPasswordField.getPassword()));
                String nuovaPassword = String.valueOf((nuovaPasswordField.getPassword()));
                String confermaPassword = String.valueOf((confermaPasswordField.getPassword()));
                if (checkPasswordFields(vecchiaPassword, nuovaPassword, confermaPassword)) {
                    if (UtenteController.getInstance().cambiaPassword(Utente.getInstance().getUsername(), Utente.getInstance().getPassword(), nuovaPassword)) {
                        JOptionPane.showMessageDialog(null, "Password cambiata con successo", "Successo", JOptionPane.INFORMATION_MESSAGE);
                        Utente.getInstance().setPassword(nuovaPassword);
                        passwordPanel.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Errore nel cambiamento della password", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        reloadIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                refreshTable();
                UtenteController.getInstance().getNotifiche(Utente.getInstance().getUsername());
            }
        });
    }

    public static ProfiloView getInstance() {
        if (instance == null) {
            instance = new ProfiloView();
        }
        return instance;
    }

    void refreshTable() {
        emptyTable();
        String query = "SELECT nome, Disponibile_in FROM b.notifiche n WHERE n.username='" + Utente.getInstance().getUsername() + "'";
        FiltriController fc = FiltriController.getInstance();
        DefaultTableModel model = (DefaultTableModel) notificheTable.getModel();
        ArrayList<String> columns = fc.getColumns("resultview_notifiche");
        Vector<Vector<Object>> rows = fc.getRows(query);

        for (String column : columns) {
            model.addColumn(column);
        }
        notificheTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        notificheTable.getColumnModel().getColumn(1).setPreferredWidth(400);
        for (Vector<Object> row : rows) {
            model.addRow(row);
        }
    }

    private void emptyTable() {
        DefaultTableModel model = (DefaultTableModel) notificheTable.getModel();
        model.setRowCount(0);
        model.setColumnCount(0);
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
        } else {
            return true;
        }
    }

    public void refreshPage() {
        usernameLabel.setText("Username: " + Utente.getInstance().getUsername());
        permessiLabel.setText("Permessi: " + Utente.getInstance().getPermessi());
        passwordPanel.setVisible(false);
        refreshTable();
    }
}

