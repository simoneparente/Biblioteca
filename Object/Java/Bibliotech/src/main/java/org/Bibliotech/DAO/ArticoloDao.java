package org.Bibliotech.DAO;

public interface ArticoloDao {
    boolean addArticolo(String titoloArticolo, String autori, String editore, String disciplina, String formato, String doi, String lingua, String nomeRivista, String issn);


    boolean addArticoloAddRivistaInDB(String titoloArticolo, String autori, String editore, String disciplina, String formato,
                                      String doi, String lingua, String nomeRivista, String issn, String argomento, String dataPubblicazione,
                                      String responsabile, Double prezzo);

}
