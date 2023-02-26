package org.Bibliotech.Controller;

import org.Bibliotech.View.LoginView;
import org.Bibliotech.View.View;

public class Controller {
    private static Controller instance = null;

    public Controller() {
        //switchView(SearchView.getInstance(), null);
        switchView(LoginView.getInstance(), null); //default sarà questo
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public void switchView(View openView, View closeView) {
        if (openView == null) {
            System.out.println("openView non può essere null");
        } else {
            if (closeView != null) {
                closeView.hideView();
            }
            openView.showView();
        }
    }


}
