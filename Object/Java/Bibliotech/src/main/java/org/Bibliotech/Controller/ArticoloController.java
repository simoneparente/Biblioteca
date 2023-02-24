package org.Bibliotech.Controller;

import org.Bibliotech.DAOimplementazione.ImplementazioneArticolo;

public class ArticoloController {
    private static ArticoloController instance= null;
    public static ArticoloController getInstance() {
        if (instance == null) {
            instance = new ArticoloController();
        }
        return instance;
    }

    public boolean addArticoloAddRivistaInDB(String titoloArticolo, String autori, String editore, String Disciplina,
                                             String Formato, String DOI, String nomeRivista, String ISSN, String Argomento,
                                             String Responsabile, String DataPubblicazioneRivista, Double Prezzo){
        ImplementazioneArticolo ia= new ImplementazioneArticolo();
        return ia.addArticoloAddRivistaInDB(titoloArticolo, autori, editore, Disciplina, Formato, DOI, nomeRivista, ISSN, Argomento, Responsabile, DataPubblicazioneRivista, Prezzo);
    }

    public boolean addArticolo(String titoloArticolo, String autori, String editore, String Disciplina,
                               String Formato, String DOI, String nomeRivista) {
        ImplementazioneArticolo ia = new ImplementazioneArticolo();
        return ia.addArticolo(titoloArticolo, autori, editore, Disciplina, Formato, DOI, nomeRivista);
    }
}
