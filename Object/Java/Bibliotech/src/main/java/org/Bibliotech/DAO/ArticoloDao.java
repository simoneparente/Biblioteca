package org.Bibliotech.DAO;

import org.Bibliotech.Model.Articoli;

public interface ArticoloDao {
    Articoli getArticoli(String query, String parametro);
    Articoli getArticoli(String query, String parametro1, String parametro2);

    //Attributi Tabella Articoli
    Articoli getArticoli();
    Articoli getArticoliByTitolo(String titolo);
    Articoli getArticoloByDoi(String doi);
    Articoli getArticoliByRangeDataPubblicazione(String dataPubblicazioneMin, String dataPubblicazioneMax);
    Articoli getArticoliByDataPubblicazioneMin(String dataPubblicazioneMin);
    Articoli getArticoliByDataPubblicazioneMax(String dataPubblicazioneMax);
    Articoli getArticoliByDisciplina(String disciplina);
    Articoli getArticoliByEditore(String editore);
    Articoli getArticoliByLingua(String lingua);
    Articoli getArticoliByFormato(String formato);

    //Attributi Tabelle Multiple
    Articoli getArticoliByAutore(String nome, String cognome);
    Articoli getArticoliByRivista(String rivista);
    Articoli getArticoliByConferenza(String conferenza);
}
