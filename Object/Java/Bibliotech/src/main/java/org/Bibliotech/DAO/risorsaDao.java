package org.Bibliotech.DAO;

import org.Bibliotech.Model.Articolo;
import org.Bibliotech.Model.Conferenza;
import org.Bibliotech.Model.Libro;
import org.Bibliotech.Model.Rivista;

import java.util.ArrayList;

public interface risorsaDao {

    boolean addLibro(Libro libro);
    boolean addArticoloRivista(Articolo articolo, String nomeRivista, String issn);


    boolean addArticoloAddRivistaInDB(Articolo articolo, Rivista rivista);

    boolean addArticoloConferenza(Articolo articolo, String nomeConferenza,
                                         String dataInizioConferenza);

    boolean addArticoloAddConferenza(Articolo articolo, Conferenza conferenza);


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
