package org.Bibliotech.DAO;

import org.Bibliotech.Model.Riviste;

public interface RivisteDao {
    public Riviste getRiviste(String query, String parametro);
    public Riviste getRiviste(String query, String parametro1, String parametro2);

    //Attributi Tabella Riviste
    public Riviste getRiviste();
    public Riviste getRivisteByNome(String nome);
    public Riviste getRivisteByIssn(String issn);
    public Riviste getRivisteByArgomento(String argomento);
    public Riviste getRivisteByRangeDataPubblicazione(String dataPubblicazione);
    public Riviste getRivisteByDataPubblicazioneMin(String dataPubblicazioneMin);
    public Riviste getRivisteByDataPubblicazioneMax(String dataPubblicazioneMax);
    public Riviste getRivisteByLingua(String lingua);
    public Riviste getRivisteByFormato(String formato);
    public Riviste getRivisteByPrezzo(String prezzo);
}