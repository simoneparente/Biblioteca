package org.Bibliotech.DAO;

import org.Bibliotech.Model.Libro;

import java.util.ArrayList;

public interface risorsaDao {

    boolean addLibro(Libro libro);
    boolean addArticoloRivista(String titoloArticolo, String autori, String editore, String disciplina, String formato, String doi, String lingua, String nomeRivista, String issn);


    boolean addArticoloAddRivistaInDB(String titoloArticolo, String autori, String editore, String disciplina, String formato,
                                      String doi, String lingua, String nomeRivista, String issn, String argomento, String dataPubblicazione,
                                      String responsabile, Double prezzo);

    boolean addArticoloConferenza(String titoloArticolo, String autori, String editore, String disciplina,
                                         String formato, String doi, String lingua, String nomeConferenza,
                                         String dataInizioConferenza);

    boolean addArticoloAddConferenza(String titoloArticolo, String autori, String editore, String disciplina,
                                            String formato, String doi, String lingua, String nomeConferenza,
                                            String responsabileConferenza, String strutturaOspitanteConferenza,
                                            String indirizzoConferenza, String dataInizioConferenza, String dataFineConferenza);


    ArrayList<String> getAutoriLibri();

    ArrayList<String> getGenereLibri();

    ArrayList<String> getLinguaLibri();

    ArrayList<String> getEditoreLibri();

    ArrayList<String> getFormatoLibri();

    ArrayList<String> getSerieLibri();

    ArrayList<String> getAutoriArticoli();

    ArrayList<String> getDisciplinaArticoli();

    ArrayList<String> getLinguaArticoli();

    ArrayList<String> getEditoreArticoli();

    ArrayList<String> getFormatoArticoli();

    ArrayList<String> getRivistaArticoli();

    ArrayList<String> getConferenzeArticoli();

    ArrayList<String> getArgomentiRiviste();

    ArrayList<String> getLingueRiviste();

    ArrayList<String> getFormatiRiviste();

    ArrayList<String> getEditoriSerie();

    ArrayList<String> getLingueSerie();

    ArrayList<String> getFormatiSerie();

    ArrayList<String> getIssnSerie(String nomeSerie);

    ArrayList<String> getRiviste();

    ArrayList<String> getConferenze();
}
