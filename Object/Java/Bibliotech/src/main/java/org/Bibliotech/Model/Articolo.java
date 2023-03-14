package org.Bibliotech.Model;

public class Articolo {
    private String titolo;
    private String autori;
    private String editore;
    private String disciplina;
    private String formato;
    private String doi;
    private String lingua;

    public Articolo(String titoloArticolo, String autori, String editore, String disciplina, String formato, String doi, String lingua){
        this.titolo = titoloArticolo;
        this.autori = autori;
        this.editore = editore;
        this.disciplina = disciplina;
        this.formato = formato;
        this.doi = doi;
        this.lingua = lingua;
    }
    public String getTitolo() {
        return titolo;
    }

    public String getAutori() {
        return autori;
    }

    public String getEditore() {
        return editore;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public String getFormato() {
        return formato;
    }

    public String getDoi() {
        return doi;
    }

    public String getLingua() {
        return lingua;
    }

}
