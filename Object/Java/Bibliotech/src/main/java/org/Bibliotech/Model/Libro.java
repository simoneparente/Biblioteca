package org.Bibliotech.Model;

import java.sql.Date;
import java.util.ArrayList;

public class Libro {
    private String titolo;
    private ArrayList<Autore> autori;
    private String genere;
    private String editore;
    private Date dataPubblicazione;
    private String isbn;
    private String formato;
    private String lingua;
    private Double prezzo;
    private String serieDiAppartenenza;


    private String ISSNSerieDiAppartenenza;


    public Libro(String titolo, String genere, ArrayList<Autore> autori, String editore, Double prezzo,
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


    public String getAutoriString() {
        String autoriString = "";
        for (Autore autore : autori) {
            autoriString = autoriString + autore.getNomeCognome() + ", ";
        }
        return autoriString.substring(0, autoriString.length() - 2);
    }

    public String getLingua() {
        return lingua;
    }

    public String getGenere() {
        return genere;
    }

    public String getEditore() {
        return editore;
    }

    public Date getDataPubblicazione() {
        return dataPubblicazione;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getFormato() {
        return formato;
    }

    public Double getPrezzo() {
        return prezzo;
    }

    public String getSerieDiAppartenenza() {
        return serieDiAppartenenza;
    }

    public String getISSNSerieDiAppartenenza() {
        return ISSNSerieDiAppartenenza;
    }
}


