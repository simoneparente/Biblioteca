package org.Bibliotech.View;

import org.Bibliotech.Controller.FiltriController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ResultView extends View{
    private static final String nome="Result";
    private static ResultView instance = null;
    private JPanel rootPanel;
    private JTable resultTable;
    private JScrollPane resultScrollPane;
    private ArrayList<String> filtri;

    ResultView(FiltriController fc){
        super(nome);
        super.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        super.setContentPane(rootPanel);
        WindowListener closeWindow = new WindowAdapter() {//listener per la chiusura della finestra
            @Override
            public void windowClosing(WindowEvent we) {
                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit Application", JOptionPane.YES_NO_OPTION);
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
            instance = new ResultView(FiltriController.getInstance());
        }
        return instance;
    }

    public void updateTable(String nomeTabellaIn, String query){
        String nomeTabella="resultview_"+nomeTabellaIn;
        emptyTable();
        FiltriController fc = FiltriController.getInstance();
        DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
        model.setRowCount(0);
        ArrayList<String> columns = fc.getColumns(nomeTabella);
        ArrayList<String> rows = fc.getRows(query);
        for(String column : columns){
            model.addColumn(column);
            //System.out.println(column);
        }



    }

    private void emptyTable() {
        DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
        model.setRowCount(0);
        model.setColumnCount(0);
    }

    

}


