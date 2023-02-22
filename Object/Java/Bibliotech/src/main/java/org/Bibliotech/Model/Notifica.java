package org.Bibliotech.Model;

public class Notifica {

    private String username;
    private String nomeSerie;
    private String issn;
    private String negozi;

    public Notifica(String username){
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNomeSerie() {
        return nomeSerie;
    }

    public void setNomeSerie(String nomeSerie) {
        this.nomeSerie = nomeSerie;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }


    public void setNegozi(String negozi) {
        this.negozi = negozi;
    }

    public String getNegozi() {
        return negozi;
    }
}
