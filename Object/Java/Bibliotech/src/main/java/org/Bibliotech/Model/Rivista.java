package org.Bibliotech.Model;

import java.util.ArrayList;

public class Rivista {
    private String nome;
    private String issn;
    private String argomento;
    private String dataPubblicazione;
    private String lingua;
    private String formato;
    private String prezzo;
    private String doi_articoli_pubblicati;

    public Rivista(String nome,
                   String issn,
                   String argomento,
                   String dataPubblicazione,
                   String lingua,
                   String formato,
                   String prezzo,
                   String doi_articoli_pubblicati) {
        this.nome = nome;
        this.issn = issn;
        this.argomento = argomento;
        this.dataPubblicazione = dataPubblicazione;
        this.lingua = lingua;
        this.formato = formato;
        this.prezzo = prezzo;
        this.doi_articoli_pubblicati = doi_articoli_pubblicati;
    }

    public Rivista(){}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getArgomento() {
        return argomento;
    }

    public void setArgomento(String argomento) {
        this.argomento = argomento;
    }

    public String getDataPubblicazione() {
        return dataPubblicazione;
    }

    public void setDataPubblicazione(String dataPubblicazione) {
        this.dataPubblicazione = dataPubblicazione;
    }

    public String getLingua() {
        return lingua;
    }

    public void setLingua(String lingua) {
        this.lingua = lingua;
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

    public String getDoi_articoli_pubblicati() {
        return doi_articoli_pubblicati;
    }

    public void setDoi_articoli_pubblicati(String doi_articoli_pubblicati) {
        this.doi_articoli_pubblicati = doi_articoli_pubblicati;
    }

}
