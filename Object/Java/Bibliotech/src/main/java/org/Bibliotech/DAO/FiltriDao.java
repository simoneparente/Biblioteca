package org.Bibliotech.DAO;

import java.util.ArrayList;

public interface FiltriDao {

    public ArrayList<String> getAutoriLibri();
    public ArrayList<String> getGenereLibri();
    public ArrayList<String> getLinguaLibri();
    public ArrayList<String> getEditoreLibri();
    public ArrayList<String> getFormatoLibri();
    public ArrayList<String> getPresentatoInLibro(); //da controllare
    public ArrayList<String> getSerieLibri();
    public ArrayList<String> getAutoriArticoli();
    public ArrayList<String> getDisciplinaArticoli();
    public ArrayList<String> getLinguaArticoli();
    public ArrayList<String> getEditoreArticoli();
    public ArrayList<String> getFormatoArticoli();
    public ArrayList<String> getRivistaArticoli();
    public ArrayList<String> getConferenzeArticoli();
    public ArrayList<String> getArgomentiRiviste();
    public ArrayList<String> getLingueRiviste();
    public ArrayList<String> getFormatiRiviste();
    public ArrayList<String> getEditoriSerie();
    public ArrayList<String> getLingueSerie();
    public ArrayList<String> getFormatiSerie();


}
