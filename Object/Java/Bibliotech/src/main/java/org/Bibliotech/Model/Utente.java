package org.Bibliotech.Model;

import org.Bibliotech.Controller.UtenteController;
import org.Bibliotech.View.LoginView;

public class Utente {
    private static Utente instance = null;
    private String username;
    private String password;
    private int  permessi;

    Utente(){
        username = null;
        password = null;
        permessi = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPermessi() {
        return permessi;
    }

    public void setPermessi(int permessi) {
        this.permessi = permessi;
    }

    public static Utente getInstance(){
        if(instance == null){
            instance = new Utente();
        }
        return instance;
    }
}
