package org.Bibliotech.View;

import javax.swing.*;

public class ResultView extends View{
    private static ResultView instance = null;
    private JPanel rootPanel;

    ResultView(String nome) {
        super(nome);
    }

    public ResultView getInstance() {
        if (instance == null) {
            instance = new ResultView();
        }
        return instance;
    }
}

