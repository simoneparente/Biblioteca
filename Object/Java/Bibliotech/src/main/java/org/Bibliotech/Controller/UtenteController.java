package org.Bibliotech.Controller;
import org.Bibliotech.DAOimplementazione.ImplementazioneUtente;

public class UtenteController {
    private ImplementazioneUtente utente;
    public UtenteController() {
        utente = new ImplementazioneUtente();
    }

    public boolean registraUtente(String username, String password) {
        boolean esitoRegistrazione;
        esitoRegistrazione=utente.addUser(username, password);
        return esitoRegistrazione;
    }
    public boolean loginUtente(String username, String password) {
        boolean esitoLogin;
        esitoLogin = utente.checkLogin(username, password);
        return esitoLogin;
    }
}
