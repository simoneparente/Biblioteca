package org.Bibliotech.Controller;

import org.Bibliotech.DAOimplementazione.ImplementazioneRisorsa;
import org.Bibliotech.Model.*;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Vector;

public class RisorsaController {
    private static RisorsaController instance = null;
    ImplementazioneRisorsa filtri;

    public RisorsaController() {
        filtri = new ImplementazioneRisorsa();
    }

    public static RisorsaController getInstance() {
        if (instance == null) {
            instance = new RisorsaController();
        }
        return instance;
    }

    public ArrayList<String> leggiAutoriLibri() {
        return filtri.getAutoriLibri();
    }

    public ArrayList<String> leggiGeneriLibri() {
        return filtri.getGenereLibri();
    }

    public ArrayList<String> leggiEditoriLibri() {
        return filtri.getEditoreLibri();
    }

    public ArrayList<String> leggiFormatiLibri() {
        return filtri.getFormatoLibri();
    }

    public ArrayList<String> leggiLingueLibri() {
        return filtri.getLinguaLibri();
    }

    public ArrayList<String> leggiSerieLibri() {
        return filtri.getSerieLibri();
    }

    public ArrayList<String> leggiAutoriArticoli() {
        return filtri.getAutoriArticoli();
    }

    public ArrayList<String> leggiDisciplineArticoli() {
        return filtri.getDisciplinaArticoli();
    }

    public ArrayList<String> leggiEditoriArticoli() {
        return filtri.getEditoreArticoli();
    }

    public ArrayList<String> leggiFormatiArticoli() {
        return filtri.getFormatoArticoli();
    }

    public ArrayList<String> leggiLingueArticoli() {
        return filtri.getLinguaArticoli();
    }

    public ArrayList<String> leggiRivisteArticoli() {
        return filtri.getRivistaArticoli();
    }

    public ArrayList<String> leggiConferenzeArticoli() {
        return filtri.getConferenzeArticoli();
    }

    public ArrayList<String> leggiArgomentiRiviste() {
        return filtri.getArgomentiRiviste();
    }

    public ArrayList<String> leggiLingueRiviste() {
        return filtri.getLingueRiviste();
    }

    public ArrayList<String> leggiFormatiRiviste() {
        return filtri.getFormatiRiviste();
    }

    public ArrayList<String> leggiEditoriSerie() {
        return filtri.getEditoriSerie();
    }

    public ArrayList<String> leggiLingueSerie() {
        return filtri.getLingueSerie();
    }

    public ArrayList<String> leggiFormatiSerie() {
        return filtri.getFormatiSerie();
    }

    public ArrayList<String> getIssnSerie(String nomeSerie) {
        return filtri.getIssnSerie(nomeSerie);
    }


    public ArrayList<String> getColumns(String nomeTable) {
        return filtri.getColumns(nomeTable);
    } //restituisce i nomi delle colonne della tabella

    public Vector<Vector<Object>> getRows(String query) {
        return filtri.getRows(query);
    } //in funzione della query, restituisce le righe della tabella

    public ArrayList<String> leggiRiviste() {
        return filtri.getRiviste();
    }

    public ArrayList<String> leggiRivisteISSN(String nomerivista) {
        return filtri.getRivisteISSN(nomerivista);
    }

    public ArrayList<String> leggiConferenze() {
        return filtri.getConferenze();
    }

    public boolean addArticoloAddRivistaInDB(Articolo articolo, Rivista rivista) {
        return filtri.addArticoloAddRivistaInDB(articolo, rivista);
    }

    //public boolean addArticoloAddConferenzaInDB(

    public boolean addArticoloRivista(Articolo articolo, String nomeRivista, String issn) {
        return filtri.addArticoloRivista(articolo, nomeRivista, issn);
    }

    public boolean addArticoloAddConferenza(Articolo articolo, Conferenza conferenza) {
        return filtri.addArticoloAddConferenza(articolo, conferenza);
    }

    public boolean addArticoloConferenza(Articolo articolo, String nomeConferenza, String dataInizioConferenza) {
        return filtri.addArticoloConferenza(articolo, nomeConferenza, dataInizioConferenza);

    }

    public ArrayList<String> getConferenzaDataInizio(String nomeConferenza) {
        return filtri.getConferenzaDataInizio(nomeConferenza);
    }

    public void addLibroInDB(String titolo, String genere, String autori, String editore, Double prezzo,
                                    String isbn, String dataPubblicazione, String formato, String lingua, String nomeSerie, String ISSNSerie) {
        ArrayList<Autore> al_autori;
        if (autori.contains(",")) {
            String autoriStringSenzaSpazi = autori.replace(", ", ","); // rimuove gli spazi dopo la virgola
            al_autori = autoriStringToArrayList(autoriStringSenzaSpazi, StringUtils.countMatches(autori, ",") + 1);
        } else {
            al_autori = autoriStringToArrayList(autori, 1);
        }
        if (filtri.addLibro(new Libro(titolo, genere, al_autori, editore, prezzo, isbn, dataPubblicazione, formato, lingua, nomeSerie, ISSNSerie))) {
            JOptionPane.showMessageDialog(null, "Libro aggiunto con successo");
        } else {
            JOptionPane.showMessageDialog(null, "Errore nell'aggiunta del libro");
        }
    }

    private ArrayList<Autore> autoriStringToArrayList(String autori, int count) {
        try {
            ArrayList<Autore> al_autori = new ArrayList<Autore>();
            for (int i = 0; i < count; i++) {
                Autore autore = new Autore(autori.split(",")[i].split("_")[0], autori.split(",")[i].split("_")[1]);
                System.out.println("istanza i = " + i + "dati:  " + autore.getNome() + " " + autore.getCognome() + " " + autore.getNomeCognome());
                al_autori.add(new Autore(autore.getNome(), autore.getCognome()));
            }
            return al_autori;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, """
                    Gli autori devono essere inseriti secondo il seguente \s
                    formato: Nome1_Cognome1, Nome2_Cognome2, ...""");
            return null;
        }
    }

}
