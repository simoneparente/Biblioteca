package org.Bibliotech.View;

import org.Bibliotech.Controller.RisorsaController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Vector;

public class ResultView extends View {
    private static final String nome = "Result";
    private static ResultView instance = null;
    private JPanel rootPanel;
    private JTable resultTable;
    private JScrollPane resultScrollPane;
    private ArrayList<String> filtri;

    ResultView(RisorsaController fc) {
        super(nome);
        super.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        super.setResizable(true);
        super.setContentPane(rootPanel);
        resultTable.setDefaultEditor(Object.class, null);
        WindowListener closeWindow = new WindowAdapter() {//listener per la chiusura della finestra
            @Override
            public void windowClosing(WindowEvent we) {
                int result = JOptionPane.showConfirmDialog(ResultView.getInstance(), "Vuoi chiudere questa pagina?", "Esci", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    ResultView.super.dispose(); //chiude la finestra
                    SearchView.getInstance().setLocationRelativeTo(null); //centra la finestra di ricerca
                }
            }
        };
        super.addWindowListener(closeWindow);
    }


    public static ResultView getInstance() {
        if (instance == null) {
            instance = new ResultView(RisorsaController.getInstance());
        }
        return instance;
    }

    public void updateTable(String nomeTabellaIn, String query) {
        if (nomeTabellaIn.isBlank()) {
        } else {
            String nomeTabella = "resultview_" + nomeTabellaIn;
            emptyTable();
            RisorsaController fc = RisorsaController.getInstance();
            DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
            ArrayList<String> columns = fc.getColumns(nomeTabella);
            Vector<Vector<Object>> rows = fc.getRows(query);


            for (String column : columns) {
                model.addColumn(column);
            }
            for (Vector<Object> row : rows) {
                model.addRow(row);
            }
        }


    }

    private void emptyTable() {
        DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
        model.setRowCount(0);
        model.setColumnCount(0);
    }


}


