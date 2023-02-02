package org.Bibliotech.Controller;

import org.Bibliotech.DAOimplementazione.ImplementazioneFiltri;

import java.util.ArrayList;

public class FiltriController {
    private static FiltriController instance=null;
    ImplementazioneFiltri filtri;

    public static FiltriController getInstance() {
        if (instance == null) {
            instance = new FiltriController();
        }
        return instance;
    }
    public FiltriController(){
        filtri = new ImplementazioneFiltri();
    }
    public ArrayList<String> leggiAutoriLibri(){
        return filtri.getAutoriLibri();
    }
    public ArrayList<String> leggiGeneriLibri(){
        return filtri.getGenereLibri();
    }
    public ArrayList<String> leggiEditoriLibri(){
        return filtri.getEditoreLibri();
    }
    public ArrayList<String> leggiFormatiLibri(){
        return filtri.getFormatoLibri();
    }
    public ArrayList<String> leggiLingueLibri(){
        return filtri.getLinguaLibri();
    }
    public ArrayList<String> leggiPresentatoInLibri(){
        return filtri.getPresentatoInLibro();
    }
    public ArrayList<String> leggiAutoriArticoli(){
        return filtri.getAutoriArticoli();
    }
    public ArrayList<String> leggiDisciplineArticoli(){
        return filtri.getDisciplinaArticoli();
    }
    public ArrayList<String> leggiEditoriArticoli(){
        return filtri.getEditoreArticoli();
    }
    public ArrayList<String> leggiFormatiArticoli(){
        return filtri.getFormatoArticoli();
    }
    public ArrayList<String> leggiLingueArticoli(){
        return filtri.getLinguaArticoli();
    }
    public ArrayList<String> leggiPresentatoInArticoli(){
        return filtri.getPresentatoInArticoli();
    }
    public ArrayList<String> getColumns(String nomeTable) { return filtri.getColumns(nomeTable);
    }
}
