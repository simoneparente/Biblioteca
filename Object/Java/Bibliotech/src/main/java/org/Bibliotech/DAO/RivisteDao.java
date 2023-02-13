package org.Bibliotech.DAO;

import org.Bibliotech.Model.Riviste;

public interface RivisteDao {
    Riviste getRiviste(String query, String parametro);
    Riviste getRiviste(String query, String parametro1, String parametro2);

    //Attributi Tabella Riviste
    Riviste getRiviste();
    Riviste getRivisteByNome(String nome);
    Riviste getRivisteByIssn(String issn);
    Riviste getRivisteByArgomento(String argomento);
    Riviste getRivisteByRangeDataPubblicazione(String dataPubblicazione);
    Riviste getRivisteByDataPubblicazioneMin(String dataPubblicazioneMin);
    Riviste getRivisteByDataPubblicazioneMax(String dataPubblicazioneMax);
    Riviste getRivisteByLingua(String lingua);
    Riviste getRivisteByFormato(String formato);
    Riviste getRivisteByPrezzo(String prezzo);
}