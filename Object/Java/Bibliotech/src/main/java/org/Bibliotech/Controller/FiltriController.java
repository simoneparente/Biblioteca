package org.Bibliotech.Controller;

import org.Bibliotech.DAOimplementazione.ImplementazioneFiltri;

import java.util.ArrayList;

public class FiltriController {
    ImplementazioneFiltri filtri;
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
    

    public ArrayList<String> getColumns() { return filtri.getColumns();//nomi colonne table
    }
}
