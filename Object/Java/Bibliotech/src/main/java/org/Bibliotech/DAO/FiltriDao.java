package org.Bibliotech.DAO;

import java.util.ArrayList;

public interface FiltriDao {

    ArrayList<String> getAutoriLibri();

    ArrayList<String> getGenereLibri();

    ArrayList<String> getLinguaLibri();

    ArrayList<String> getEditoreLibri();

    ArrayList<String> getFormatoLibri();

    ArrayList<String> getPresentatoInLibro(); //da controllare

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
