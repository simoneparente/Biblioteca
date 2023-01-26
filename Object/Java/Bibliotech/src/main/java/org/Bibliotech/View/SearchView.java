package org.Bibliotech.View;

import javax.swing.*;

public class SearchView extends GeneralView{
    private JPanel rootPanel;
    private JPanel imagePanel;
    private JPanel searchPanel;
    private JLabel imageLabel;
    private JTextField searchField;
    private JLabel glassIconLabel;

    public SearchView(){
        ImageIcon glassIconImage = new ImageIcon("src/main/Immagini/glassIcon.png");
        imageLabel.setIcon(logoIcon);
        glassIconLabel.setIcon(glassIconImage);
        imagePanel.setSize(360,250);
        newView("Search", rootPanel);
    }
}
