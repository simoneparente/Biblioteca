package org.Bibliotech.Controller;

import org.Bibliotech.DAOimplementazione.ImplementazioneFiltri;

import java.util.ArrayList;

public class FiltriController {
    ImplementazioneFiltri filtri;
    public FiltriController(){
        filtri = new ImplementazioneFiltri();
    }
    public ArrayList<String> leggiNomeAutori(){
        return filtri.getNomeAutori();
    }
    public ArrayList<String> leggiCognomeAutori(){
        return filtri.getCognomeAutori();
    }
    public ArrayList<String> leggiGeneri(){
        return filtri.getGenere();
    }
    public ArrayList<String> leggiEditori(){
        return filtri.getEditore();
    }
    public ArrayList<String> leggiFormati(){
        return filtri.getFormato();
    }
    public ArrayList<String> leggiLingue(){
        return filtri.getLingua();
    }

}
