package org.Bibliotech.View;

import javax.swing.*;

public class SearchView extends View{
    private static SearchView instance = null;
    private JPanel rootPanel;

    public SearchView(){
        super("Search");
        this.setVisible(true);
        super.setRootPane(SearchViewArticoli.getInstance().getRootPane());


    }

    public static View getInstance() {
        if(instance==null){
            instance = new SearchView();
        }
        return instance;
    }
}
