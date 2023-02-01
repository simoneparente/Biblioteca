package org.Bibliotech.Controller;

import org.Bibliotech.DAOimplementazione.ImplementazioneUtente;
import org.Bibliotech.View.LoginView;
import org.Bibliotech.View.RegisterView;
import org.Bibliotech.View.View;

import javax.swing.*;
import java.awt.*;

public class Controller {
    private static Controller instance;

    public Controller() {
        switchView(LoginView.getInstance(), null);
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }
    public void switchView(View openView, View closeView){
        if(openView == null){
            System.out.println("openView non può essere null");
        }
        else if(closeView == null) {
            openView.showView();
        }
        else{
            closeView.hideView();
            openView.showView();
    }

    }


}
