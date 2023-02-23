package org.Bibliotech.Controller;
import org.Bibliotech.DAOimplementazione.ImplementazioneUtente;
import org.Bibliotech.Model.Notifica;
import org.Bibliotech.Model.Utente;

import javax.swing.*;
import java.util.ArrayList;

public class UtenteController {
    private static UtenteController instance = null;
    private final ImplementazioneUtente utente;

    public UtenteController() {
        utente = new ImplementazioneUtente();
    }

    public void registraUtente(String username, String password) {
        utente.addUser(username, password);
    }

    public boolean loginUtente(String username, String password) {
        Utente.getInstance().setPermessi(utente.getPermessi(username));
        Utente.getInstance().setUsername(username);
        Utente.getInstance().setPassword(password);
        return utente.checkLogin(username, password);
    }

    public boolean checkUserExistInDatabase(JTextField usernameField) {
        return utente.checkUserExistInDatabase(usernameField);
    }
    //se non esiste ritorna false, se esiste ritorna true

    public static UtenteController getInstance() {
        if (instance == null) {
            instance = new UtenteController();
        }
        return instance;
    }

    public boolean cambiaPassword(String username, String vecchiaPassword, String nuovaPassword) {
        if (utente.checkLogin(username, vecchiaPassword)) {
            return utente.changePassword(username, nuovaPassword);
        } else {
            return false;
        }
    }

    public int getPermessi(String username) {
        return utente.getPermessi(username);
    }

    public boolean checkNotifiche(String username) {
        return utente.checkNotifiche(username);
    }

    public ArrayList<Notifica> getNotifiche(String username) {
        return utente.getNotifiche();
    }


    public boolean inviaRichiestaSerie(String username, String issn) {
         return utente.inviaRichiestaSerie(username, issn);
    }
}
