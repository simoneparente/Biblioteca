package org.Bibliotech.Controller;

import org.Bibliotech.DAOimplementazione.ImplementazioneFiltri;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FiltriController {
    ImplementazioneFiltri filtri;
    public FiltriController(){
        filtri = new ImplementazioneFiltri();
    }
    public ArrayList<String> leggiAutori(){
        return filtri.getAutoriLibri();
    }
    public ArrayList<String> leggiGeneri(){
        return filtri.getGeneriLibri();
    }
    public ArrayList<String> leggiEditori(){
        return filtri.getEditoriLibri();
    }
    public ArrayList<String> leggiFormati(){
        return filtri.getFormatiLibri();
    }
    public ArrayList<String> leggiLingue(){
        return filtri.getLingueLibri();
    }

    public ArrayList<String> getColumns() { return filtri.getColumns();
    }
}
