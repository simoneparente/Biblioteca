package org.Bibliotech.Model;

public class Utente {
    private static Utente instance = null;
    private String username;
    private String password;
    private int permessi;

    Utente() {
        username = null;
        password = null;
        permessi = 0;
    }

    public static Utente getInstance() {
        if (instance == null) {
            instance = new Utente();
        }
        return instance;
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
}
