package org.Bibliotech.View;

import javax.swing.*;

public class ProfiloView extends View {
    private static ProfiloView instance = null;
    private static final String nome = "ProfiloView";
    private JPanel rootPanel;
    private JLabel usernameLabel;
    private JLabel permessiLabel;
    private JLabel cambiaPasswordTextArea;
    private JPanel infoPanel;
    private JPanel notifichePanel;

    private ProfiloView() {
        super(nome);
        super.setContentPane(rootPanel);
        setVisible(true);
    }

    public static ProfiloView getInstance() {
        if (instance == null) {
            instance = new ProfiloView();
        }
        return instance;
    }
}
