package org.Bibliotech.Controller;
import org.Bibliotech.DAOimplementazione.ImplementazioneUtente;
import org.Bibliotech.Model.Utente;
import org.Bibliotech.View.ProfiloView;

import javax.swing.*;

public class UtenteController {
    private static UtenteController instance= null;
    private ImplementazioneUtente utente;
    public UtenteController() {
        utente = new ImplementazioneUtente();
    }

    public boolean registraUtente(String username, String password) {
        return utente.addUser(username, password);
    }
    public boolean loginUtente(String username, String password) {
        return utente.checkLogin(username, password);
    }

    public boolean checkUserExistInDatabase(String username){ return utente.checkUserExistInDatabase(username);}
  //se non esiste ritorna false, se esiste ritorna true

    public static UtenteController getInstance(){
        if(instance == null){
            instance = new UtenteController();
        }
        return instance;
    }

    public boolean cambiaPassword(String username, String vecchiaPassword, String nuovaPassword) {
        if (utente.checkLogin(username, vecchiaPassword)) {
            return utente.changePassword(username, nuovaPassword);
    }
        else{ return false;}
    }
}
