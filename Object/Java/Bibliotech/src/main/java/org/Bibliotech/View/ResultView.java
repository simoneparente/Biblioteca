package org.Bibliotech.View;

import javax.swing.*;

public class ResultView {
    private static ResultView instance = null;
    private JPanel rootPanel;
    public ResultView getInstance() {
        if (instance == null) {
            instance = new ResultView();
        }
        return instance;
    }
}

