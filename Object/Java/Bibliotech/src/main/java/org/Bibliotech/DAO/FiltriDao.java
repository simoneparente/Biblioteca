package org.Bibliotech.DAO;

import java.util.ArrayList;

public interface FiltriDao {

    public ArrayList<String> getAutoriLibri();
    public ArrayList<String> getGeneriLibri();
    public ArrayList<String> getLingueLibri();
    public ArrayList<String> getEditoriLibri();
    public ArrayList<String> getFormatiLibri();
    public ArrayList<String> getAutoriArticoli();
    public ArrayList<String> getLinguaArticoli();
    public ArrayList<String> getFormatoArticoli();
    public ArrayList<String> getRivisteArticoli();

}
