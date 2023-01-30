package org.Bibliotech.DAO;

import java.util.ArrayList;

public interface FiltriDao {

    public ArrayList<String> getNomeAutori();
    public ArrayList<String> getCognomeAutori();
    public ArrayList<String> getGenere();
    public ArrayList<String> getLingua();
    public ArrayList<String> getEditore();
    public ArrayList<String> getFormato();
}
