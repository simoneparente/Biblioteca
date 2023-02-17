package org.Bibliotech.Model;

import org.w3c.dom.ls.LSOutput;

import java.sql.Date;
import java.util.ArrayList;

public class Libro {
    private String titolo;
    private ArrayList<Autore> autori;
    private String autoriString = "";
    private String genere;
    private String editore;
    private Date dataPubblicazione;
    private String isbn;
    private String formato;
    private String lingua;
    private String prezzo;
    private String serieDiAppartenenza;


    private String ISSNSerieDiAppartenenza;
    public Libro() {}
    public Libro(String titolo, String genere, ArrayList<Autore> autori, String editore, String prezzo,
                 String isbn, String dataPubblicazioneIn, String formato, String lingua, String serieDiAppartenenza, String ISSNSerieDiAppartenenza) {
        Date dataPubblicazione = Date.valueOf(dataPubblicazioneIn);
        this.titolo = titolo;
        this.autori = autori;
        this.genere = genere;
        this.editore = editore;
        this.dataPubblicazione = dataPubblicazione;
        this.isbn = isbn;
        this.formato = formato;
        this.lingua = lingua;
        this.prezzo = prezzo;
        this.serieDiAppartenenza = serieDiAppartenenza;
        this.ISSNSerieDiAppartenenza = ISSNSerieDiAppartenenza;
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
        autoriString = "";
        for (Autore autore : autori) {
            autoriString = autoriString + autore.getNomeCognome() + ", ";
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

    public Date getDataPubblicazione() {
        return dataPubblicazione;
    }

    public void setDataPubblicazione(Date dataPubblicazione) {
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

    public String getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(String prezzo) {
        this.prezzo = prezzo;
    }

    public String getSerieDiAppartenenza() {
        return serieDiAppartenenza;
    }

    public String getISSNSerieDiAppartenenza() {
        return ISSNSerieDiAppartenenza;
    }
}


