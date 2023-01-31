package org.Bibliotech.View;

import javax.swing.*;

public class ResultView extends View{
    private static final String nome="Result";
    private static ResultView instance = null;
    private JPanel rootPanel;
    private JTable resultTable;
    private JScrollPane resultScrollPane;

    ResultView() {
        super(nome);
    }

    public ResultView getInstance() {
        if (instance == null) {
            instance = new ResultView();
        }
        return instance;
    }
}

