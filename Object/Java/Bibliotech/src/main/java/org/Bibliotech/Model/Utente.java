package org.Bibliotech.Model;
public class Utente {
    private String username;
    private String password;
    public Utente(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //Sta roba non serve
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}
