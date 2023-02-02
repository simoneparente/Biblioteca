package org.Bibliotech.Model;

import java.util.ArrayList;

public class Articolo {
    private ArrayList<Autore> autori;
    private String titolo;
    private String autoriString = "";
    private String dataPubblicazione;
    private String editore;
    private String doi;
    private String lingua;
    private String formato;
    private String disciplina;
    private String rivista;


    public Articolo(String titolo,
                    ArrayList<Autore> autori,
                    String dataPubblicazione,
                    String editore,
                    String doi,
                    String lingua,
                    String formato,
                    String disciplina,
                    String rivista) {
        this.titolo = titolo;
        this.autori = autori;
        this.dataPubblicazione = dataPubblicazione;
        this.editore = editore;
        this.doi = doi;
        this.lingua = lingua;
        this.formato = formato;
        this.disciplina = disciplina;
        this.rivista = rivista;
    }

    public Articolo() {}

    public String getTitolo() {
        return titolo;
    }
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getAutoriString() {
        for (Autore autore : autori) {
            autoriString = autoriString + autore.getNome() + " " + autore.getCognome() + ", ";
        }
        return autoriString.substring(0, autoriString.length() - 2);
    }

    public ArrayList<Autore> getAutori() {
        return autori;
    }
    public void setAutori(ArrayList<Autore> autori) {
        this.autori = autori;
    }
    public String getDataPubblicazione() {
        return dataPubblicazione;
    }
    public void setDataPubblicazione(String dataPubblicazione) {
        this.dataPubblicazione = dataPubblicazione;
    }
    public String getEditore() {
        return editore;
    }
    public void setEditore(String editore) {
        this.editore = editore;
    }
    public String getDoi() {
        return doi;
    }
    public void setDoi(String doi) {
        this.doi = doi;
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
    public String getDisciplina() {
        return disciplina;
    }
    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }
    public String getRivista() {
        return rivista;
    }
    public void setRivista(String rivista) {
        this.rivista = rivista;
    }

}
