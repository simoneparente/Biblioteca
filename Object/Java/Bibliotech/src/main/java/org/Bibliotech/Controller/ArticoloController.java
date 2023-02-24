package org.Bibliotech.Controller;

import org.Bibliotech.DAOimplementazione.ImplementazioneArticolo;

public class ArticoloController {
    private static ArticoloController instance = null;

    public static ArticoloController getInstance() {
        if (instance == null) {
            instance = new ArticoloController();
        }
        return instance;
    }

    public boolean addArticoloAddRivistaInDB(String titoloArticolo, String autori, String editore, String disciplina, String formato,
                                             String doi, String lingua, String nomeRivista, String issn, String argomento, String dataPubblicazione,
                                             String responsabile, Double prezzo) {
        ImplementazioneArticolo ia = new ImplementazioneArticolo();
        return ia.addArticoloAddRivistaInDB(titoloArticolo, autori, editore, disciplina, formato,
                doi, lingua, nomeRivista, issn, argomento, dataPubblicazione,
                responsabile, prezzo);
    }

    //public boolean addArticoloAddConferenzaInDB(

    public boolean addArticolo(String titoloArticolo, String autori, String editore, String disciplina, String formato, String doi, String lingua, String nomeRivista, String issn) {
        ImplementazioneArticolo ia = new ImplementazioneArticolo();
        return ia.addArticolo(titoloArticolo, autori, editore, disciplina, formato, doi, lingua, nomeRivista, issn);
    }
}
