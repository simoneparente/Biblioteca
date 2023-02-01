package org.Bibliotech.View;

import javax.swing.*;

public class GeneralView extends JFrame {

    String nome;

    JPanel rootPanel;

    public JFrame newView(String nome, JPanel rootPanel){
        this.nome = nome;
        this.setContentPane(rootPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        return this;
    }

    public String getName() {
        return this.nome;
    }
    public void showView(){
        this.setVisible(true);
    }

    public void hideView(){
        this.setVisible(false);
    }
}
