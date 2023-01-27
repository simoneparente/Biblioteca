package org.Bibliotech.Model;

import java.util.ArrayList;

public class Libro {
    private String titolo;
    private ArrayList<Autore> autori;

    private String autoriString = "";
    private String genere;
    private String editore;
    private String dataPubblicazione;
    private String isbn;
    private String formato;

    private String lingua;
    private double prezzo;
    public Libro() {
    }
    public Libro(String titolo,
                 ArrayList<Autore> autori,
                 String genere, String editore,
                 String dataPubblicazione,
                 String isbn,
                 String formato,
                 String lingua,
                 double prezzo) {
        this.titolo = titolo;
        this.autori = autori;
        this.genere = genere;
        this.editore = editore;
        this.dataPubblicazione = dataPubblicazione;
        this.isbn = isbn;
        this.formato = formato;
        this.lingua = lingua;
        this.prezzo = prezzo;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public ArrayList<Autore> getAutori() {
        return autori;
    }

    public String getAutoriString() {
        for (Autore autore : autori) {
            autoriString = autoriString + autore.getNome() + " " + autore.getCognome() + ", ";
        }
        return autoriString.substring(0, autoriString.length() - 2);
    }

    public String getLingua() {
        return lingua;
    }

    public void setLingua(String lingua) {
        this.lingua = lingua;
    }
    public void setAutori(ArrayList<Autore> autori) {
        this.autori = autori;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public String getEditore() {
        return editore;
    }

    public void setEditore(String editore) {
        this.editore = editore;
    }

    public String getDataPubblicazione() {
        return dataPubblicazione;
    }

    public void setDataPubblicazione(String dataPubblicazione) {
        this.dataPubblicazione = dataPubblicazione;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

}


