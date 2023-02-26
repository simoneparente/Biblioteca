package org.Bibliotech.Controller;

import org.Bibliotech.DAOimplementazione.ImplementazioneArticolo;

import java.util.ArrayList;

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

    public boolean addArticoloRivista(String titoloArticolo, String autori, String editore, String disciplina,
                                      String formato, String doi, String lingua, String nomeRivista, String issn) {
        ImplementazioneArticolo ia = new ImplementazioneArticolo();
        return ia.addArticoloRivista(titoloArticolo, autori, editore, disciplina, formato, doi, lingua, nomeRivista, issn);
    }

    public boolean addArticoloAddConferenza(String titoloArticolo, String autori, String editore, String disciplina,
                                      String formato, String doi, String lingua, String nomeConferenza,
                                      String responsabileConferenza, String strutturaOspitanteConferenza,
                                      String indirizzoConferenza, String dataInizioConferenza, String dataFineConferenza) {
        ImplementazioneArticolo ia = new ImplementazioneArticolo();
        return ia.addArticoloAddConferenza(titoloArticolo, autori, editore, disciplina, formato, doi, lingua, nomeConferenza,
                responsabileConferenza, strutturaOspitanteConferenza, indirizzoConferenza, dataInizioConferenza,
                dataFineConferenza);
    }

    public boolean addArticoloConferenza(String titoloArticolo, String autori, String editore, String disciplina,
                                      String formato, String doi, String lingua, String nomeConferenza, String dataInizioConferenza) {
        ImplementazioneArticolo ia = new ImplementazioneArticolo();
        return ia.addArticoloConferenza(titoloArticolo, autori, editore, disciplina, formato, doi, lingua, nomeConferenza, dataInizioConferenza);

    }

    public ArrayList<String> getConferenzaDataInizio(String nomeConferenza) {
        ImplementazioneArticolo ia = new ImplementazioneArticolo();
        return ia.getConferenzaDataInizio(nomeConferenza);
    }
}
