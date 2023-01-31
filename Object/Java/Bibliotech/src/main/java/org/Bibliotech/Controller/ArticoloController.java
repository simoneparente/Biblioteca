package org.Bibliotech.Controller;

import org.Bibliotech.DAOimplementazione.ImplementazioneArticolo;
import org.Bibliotech.Model.Articoli;

public class ArticoloController {
    ImplementazioneArticolo articolo;
    public ArticoloController() {
        articolo = new ImplementazioneArticolo();
    }

    public boolean addArticolo(String doi, String titolo, String autorinome_cognome, String dataPubblicazione, String editore, String lingua, String formato, String disciplina){
        return articolo.addArticolo(doi, titolo, autorinome_cognome, dataPubblicazione, editore, lingua, formato, disciplina);
    }

    public Articoli leggiArticoli(){
        return articolo.getArticoli();
    }
    public Articoli leggiArticoliByTitolo(String titolo){
        return articolo.getArticoliByTitolo(titolo);
    }
    public Articoli leggiArticoliByDoi(String doi){
        return articolo.getArticoloByDoi(doi);
    }
    public Articoli leggiArticoliByRangeDataPubblicazione(String dataPubblicazioneMin, String dataPubblicazioneMax){
        return articolo.getArticoliByRangeDataPubblicazione(dataPubblicazioneMin, dataPubblicazioneMax);
    }
    public Articoli leggiArticoliByDataPubblicazioneMin(String dataPubblicazioneMin){
        return articolo.getArticoliByDataPubblicazioneMin(dataPubblicazioneMin);
    }
    public Articoli leggiArticoliByDataPubblicazioneMax(String dataPubblicazioneMax){
        return articolo.getArticoliByDataPubblicazioneMax(dataPubblicazioneMax);
    }
    public Articoli leggiArticoliByDisciplina(String disciplina){
        return articolo.getArticoliByDisciplina(disciplina);
    }
    public Articoli leggiArticoliByEditore(String editore){
        return articolo.getArticoliByEditore(editore);
    }
    public Articoli leggiArticoliByLingua(String lingua){
        return articolo.getArticoliByLingua(lingua);
    }
    public Articoli leggiArticoliByFormato(String formato){
        return articolo.getArticoliByFormato(formato);
    }

    public Articoli leggiArticoliByAutore(String nomeCognome){
        return articolo.getArticoliByAutore(nomeCognome);
    }
    public Articoli leggiArticoliByRivista(String rivista){
        return articolo.getArticoliByRivista(rivista);
    }
    public Articoli leggiArticoliByConferenza(String conferenza){
        return articolo.getArticoliByConferenza(conferenza);
    }
}
