package org.Bibliotech.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NotificaView extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel libroDisponibileLabel;

    public NotificaView(String titoloLibro, String nomeNegozio) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        libroDisponibileLabel.setText("Il libro \"" + titoloLibro + "\" è disponibile per l'acquisto presso: " + nomeNegozio);
        this.setVisible(true);
        this.pack();

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
    }

    public static void main(String[] args) {
        //NotificaView dialog = new NotificaView("ciao", getNomiNegozi());
        System.exit(0);
    }

    private void onOK() {
        dispose();
    }
}
