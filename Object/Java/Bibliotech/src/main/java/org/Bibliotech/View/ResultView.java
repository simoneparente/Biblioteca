package org.Bibliotech.View;

import org.Bibliotech.Controller.FiltriController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.util.ArrayList;

public class ResultView extends GeneralView{
    private JPanel rootPanel;
    private JScrollPane resultPanel;
    private JTable resultTable;
    public ResultView(){
        JFrame frame = newView("Result", rootPanel);
        FiltriController fc = new FiltriController();
        DefaultTableModel model = new DefaultTableModel();
        ArrayList<String> columns = fc.getColumns("view_libro_autore_prezzo"); //dovrà diventare una variabile ed essere definita in base a reasourceSelectorBox
        for(String s : columns){
            model.addColumn(s);
        }
        resultTable.setModel(model);

        TableColumnModel cols = resultTable.getColumnModel();
        for (int i = 0; i < cols.getColumnCount(); i++) {
            cols.getColumn(i).setPreferredWidth(100);
        }
    }
}
