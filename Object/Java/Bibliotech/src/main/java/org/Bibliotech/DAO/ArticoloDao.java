package org.Bibliotech.DAO;

import org.Bibliotech.Model.Articoli;

public interface ArticoloDao {
    public boolean addArticolo(String doi, String titolo, String autorinome_cognome, String dataPubblicazione, String editore, String lingua, String formato);
    public Articoli getArticoli();
    public Articoli getArticoloByDoi(String doi);
    public Articoli getArticoliByTitolo(String titolo);
    public Articoli getArticoliByAutore(String autore);
    public Articoli getArticoliByLingua(String lingua);
    public Articoli getArticoliByEditore(String editore);
    public Articoli getArticoliByRangeDataPubblicazione(String dataPubblicazioneMin, String dataPubblicazioneMax);
    public Articoli getArticoliByDataPubblicazioneMin(String dataPubblicazioneMin);
    public Articoli getArticoliByDataPubblicazioneMax(String dataPubblicazioneMax);
    public Articoli getArticoliByFormato(String formato);
}
