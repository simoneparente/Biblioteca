package org.Bibliotech.View;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ResultView extends View{
    private static final String nome="Result";
    private static ResultView instance = null;
    private JPanel rootPanel;
    private JTable resultTable;
    private JScrollPane resultScrollPane;

    ResultView() {
        super(nome);
        super.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        WindowListener closeWindow = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit Application", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    ResultView.super.dispose();
                    //.getInstance().getFrame().setLocationRelativeTo(null);
                }
            }
        };
        super.addWindowListener(closeWindow);
    }



    public static View getInstance() {
        if (instance == null) {
            instance = new ResultView();
        }
        return instance;
    }
}

