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
    public ArrayList<String> leggiSerieLibri() { return filtri.getSerieLibri();}
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
    public ArrayList<String> leggiRivisteArticoli(){return filtri.getRivistaArticoli();}
    public ArrayList<String> leggiConferenzeArticoli(){return filtri.getConferenzeArticoli();}
    public ArrayList<String> leggiArgomentiRiviste(){return filtri.getArgomentiRiviste();}
    public ArrayList<String> leggiLingueRiviste(){return filtri.getLingueRiviste();}
    public ArrayList<String> leggiFormatiRiviste(){return filtri.getFormatiRiviste();}
    public ArrayList<String> leggiEditoriSerie(){return filtri.getEditoriSerie();}
    public ArrayList<String> leggiLingueSerie(){return filtri.getLingueSerie();}
    public ArrayList<String> leggiFormatiSerie(){return filtri.getFormatiSerie();}
    public ArrayList<String> getColumns(String nomeTable) { return filtri.getColumns(nomeTable);}


}
