package org.Bibliotech.DAO;

public interface ArticoloDao {
    boolean addArticoloRivista(String titoloArticolo, String autori, String editore, String disciplina, String formato, String doi, String lingua, String nomeRivista, String issn);


    boolean addArticoloAddRivistaInDB(String titoloArticolo, String autori, String editore, String disciplina, String formato,
                                      String doi, String lingua, String nomeRivista, String issn, String argomento, String dataPubblicazione,
                                      String responsabile, Double prezzo);

    public boolean addArticoloConferenza(String titoloArticolo, String autori, String editore, String disciplina,
                                         String formato, String doi, String lingua, String nomeConferenza,
                                         String dataInizioConferenza);

    public boolean addArticoloAddConferenza(String titoloArticolo, String autori, String editore, String disciplina,
                                  String formato, String doi, String lingua, String nomeConferenza,
                                  String responsabileConferenza, String strutturaOspitanteConferenza,
                                  String indirizzoConferenza, String dataInizioConferenza, String dataFineConferenza);
}
