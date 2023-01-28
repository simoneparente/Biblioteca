package org.Bibliotech.Controller;
import org.Bibliotech.DAOimplementazione.ImplementazioneUtente;

public class UtenteController {
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
}
