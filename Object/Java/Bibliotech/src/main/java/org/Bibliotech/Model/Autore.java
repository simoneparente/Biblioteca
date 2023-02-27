package org.Bibliotech.Model;

public class Autore {
    private String nome;
    private String cognome;

    public Autore(String nome, String cognome) {
        this.nome = nome;
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }
    public String getNomeCognome() {
        return nome + "_" + cognome;
    }
}
