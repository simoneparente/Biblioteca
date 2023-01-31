package org.Bibliotech.DAO;

import org.Bibliotech.Model.Articoli;

public interface ArticoloDao {
    public boolean addArticolo(String doi, String titolo, String autorinome_cognome, String dataPubblicazione, String editore, String lingua, String disciplina, String formato);
    public Articoli getArticoli(String query, String parametro);
    Articoli getArticoli(String query, String parametro1, String parametro2);

    //Attributi Tabella Articoli
    public Articoli getArticoli();
    public Articoli getArticoliByTitolo(String titolo);
    public Articoli getArticoloByDoi(String doi);
    public Articoli getArticoliByRangeDataPubblicazione(String dataPubblicazioneMin, String dataPubblicazioneMax);
    public Articoli getArticoliByDataPubblicazioneMin(String dataPubblicazioneMin);
    public Articoli getArticoliByDataPubblicazioneMax(String dataPubblicazioneMax);
    public Articoli getArticoliByDisciplina(String disciplina);
    public Articoli getArticoliByEditore(String editore);
    public Articoli getArticoliByLingua(String lingua);
    public Articoli getArticoliByFormato(String formato);

    //Attributi Tabelle Multiple
    public Articoli getArticoliByAutore(String nomeCognome);
    public Articoli getArticoliByRivista(String rivista);
    public Articoli getArticoliByConferenza(String conferenza);
}
