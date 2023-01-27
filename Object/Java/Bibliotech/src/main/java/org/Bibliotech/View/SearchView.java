package org.Bibliotech.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchView extends GeneralView{
    private JPanel rootPanel;
    private JPanel imagePanel;
    private JPanel searchPanel;
    private JLabel imageLabel;
    private JTextField searchField;
    private JButton filtriButton;
    private JButton searchButton;
    private JPanel filtriPanel;
    private JCheckBox checkBox1;
    private JCheckBox checkBox2;
    private JCheckBox checkBox3;
    private JCheckBox checkBox4;

    public SearchView(){
        ImageIcon glassIconImage = new ImageIcon("src/main/Immagini/glassIcon.png");
        imageLabel.setIcon(logoIcon);
        searchButton.setIcon(glassIconImage);
        imagePanel.setSize(360,250);
        JFrame frame = newView("Search", rootPanel);
        filtriButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    setSize(750,480);
                }
        });
    }
}
