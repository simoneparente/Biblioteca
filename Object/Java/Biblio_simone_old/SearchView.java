package org.Bibliotech.View;

import javax.swing.*;

public class SearchView extends GeneralView{
    private JPanel rootPanel;
    private JPanel contentPanel;
    private JPanel imagePanel;
    private JLabel imageLabel;
    private JTextField searchField;

    SearchView(){
        newView("search", rootPanel);
        
    }
}
