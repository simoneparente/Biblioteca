package org.Bibliotech.View;

import org.Bibliotech.Controller.RisorsaController;
import org.Bibliotech.Controller.UtenteController;
import org.Bibliotech.Model.Utente;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class RichiestaView extends JDialog {
    private JPanel contentPane;
    private JButton inviaButton;
    private JButton cancellaButton;
    private JComboBox<String> richiediSerieComboBox;
    private JComboBox<String> richiediISSNComboBox;

    public RichiestaView() {
        setTitle("Richiedi serie");
        setContentPane(contentPane);
        setLocationRelativeTo(null);
        setModal(true);
        getRootPane().setDefaultButton(inviaButton);
        fillComboBoxes();

        inviaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        cancellaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        richiediSerieComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    richiediISSNComboBox.removeAllItems();
                    ArrayList<String> items = RisorsaController.getInstance().getIssnSerie(String.valueOf(richiediSerieComboBox.getSelectedItem()));
                    for (String item : items) {
                        richiediISSNComboBox.addItem(item);
                    }
                }
            }
        });
    }

    private void fillISSNComboBox() {
        richiediISSNComboBox.removeAllItems();
        ArrayList<String> items = RisorsaController.getInstance().getIssnSerie(String.valueOf(richiediSerieComboBox.getSelectedItem()));
        for (String item : items) {
            richiediISSNComboBox.addItem(item);
        }
    }

    private void fillTitoliComboBox() {
        ArrayList<String> items = RisorsaController.getInstance().leggiSerieLibri();
        for (String item : items) {
            richiediSerieComboBox.addItem(item);
        }
    }

    private void fillComboBoxes() {
        fillTitoliComboBox();
        fillISSNComboBox();
    }


    private void onOK() {
        if (UtenteController.getInstance().
                inviaRichiestaSerie(Utente.getInstance().getUsername(), String.valueOf(richiediISSNComboBox.getSelectedItem()))) {
            JOptionPane.showMessageDialog(null, "Richiesta inviata con successo");
        } else {
            JOptionPane.showMessageDialog(null, "Richiesta non inviata");
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
