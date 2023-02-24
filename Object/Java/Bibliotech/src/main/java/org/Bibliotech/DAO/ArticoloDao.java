package org.Bibliotech.DAO;

import org.Bibliotech.Model.Articoli;

public interface ArticoloDao {
    boolean addArticolo(String titoloArticolo, String autori, String editore, String disciplina, String formato, String doi, String nomeRivista);


   boolean addArticoloAddRivistaInDB(String titoloArticolo, String autori, String editore, String disciplina, String formato,
                                 String doi, String lingua, String nomeRivista, String issn, String argomento, String dataPubblicazione,
                                 String responsabile, Double prezzo);

}
