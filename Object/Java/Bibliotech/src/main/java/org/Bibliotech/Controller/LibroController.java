package org.Bibliotech.Controller;

import org.Bibliotech.DAOimplementazione.ImplementazioneLibro;
import org.Bibliotech.Model.Autore;
import org.Bibliotech.Model.Libri;
import org.Bibliotech.Model.Libro;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class LibroController {
    ImplementazioneLibro libro;

    public LibroController() {
        libro = new ImplementazioneLibro();
    }

    public static void addLibroInDB(String titolo, String genere, String autori, String editore, String prezzo,
                                    String isbn, String dataPubblicazione, String formato, String lingua, String nomeSerie, String ISSNSerie) {;
        ImplementazioneLibro il = new ImplementazioneLibro();
        ArrayList<Autore> al_autori;
        if (autori.contains(",")) {
            String autoriStringSenzaSpazi= autori.replace(", ", ","); // rimuove gli spazi dopo la virgola
            al_autori = autoriStringToArrayList(autoriStringSenzaSpazi, StringUtils.countMatches(autori, ",") + 1);
        } else {
            al_autori = autoriStringToArrayList(autori, 1);
        }
        if (il.addLibro(new Libro(titolo, genere, al_autori, editore, prezzo, isbn, dataPubblicazione, formato, lingua, nomeSerie, ISSNSerie))) {
            JOptionPane.showMessageDialog(null, "Libro aggiunto con successo");
        } else {
            JOptionPane.showMessageDialog(null, "Errore nell'aggiunta del libro");
        }
    }

    private static ArrayList<Autore> autoriStringToArrayList(String autori, int count) {
        ArrayList<Autore> al_autori = new ArrayList<Autore>();
        for (int i = 0; i < count; i++) {
            Autore autore =  new Autore(autori.split(",")[i].split("_")[0], autori.split(",")[i].split("_")[1]);
            System.out.println("istanza i = " +i+ "dati:  "+ autore.getNome() + " " + autore.getCognome() + " " + autore.getNomeCognome());
            al_autori.add(new Autore(autore.getNome(), autore.getCognome()));
        }
        return al_autori;
    }

    public Libri leggiLibri() {
        return libro.getLibri();
    }

    public Libri leggiLibriByTitolo(String titolo) {
        return libro.getLibriByTitolo(titolo);
    }

    public Libri leggiLibriByAutore(String autorenome, String autorecognome) {
        return libro.getLibriByAutore(autorenome, autorecognome);
    }

    public Libri leggiLibriByGenere(String genere) {
        return libro.getLibriByGenere(genere);
    }

    public Libri leggiLibriByLingua(String lingua) {
        return libro.getLibriByLingua(lingua);
    }

    public Libri leggiLibriByEditore(String editore) {
        return libro.getLibriByEditore(editore);
    }

    public Libri leggiLibriByRangeDataPubblicazione(String dataPubblicazioneMin, String dataPubblicazioneMax) {
        return libro.getLibriByRangeDataPubblicazione(dataPubblicazioneMin, dataPubblicazioneMax);
    }

    public Libri leggiLibriByDataPubblicazioneMin(String dataPubblicazioneMin) {
        return libro.getLibriByDataPubblicazioneMin(dataPubblicazioneMin);
    }

    public Libri leggiLibriByDataPubblicazioneMax(String dataPubblicazioneMax) {
        return libro.getLibriByDataPubblicazioneMax(dataPubblicazioneMax);
    }

    public Libri leggiLibriByFormato(String formato) {
        return libro.getLibriByFormato(formato);
    }

    public Libri leggiLibriByRangePrezzo(double min, double max) {
        return libro.getLibriByRangePrezzo(min, max);
    }

    public Libri leggiLibriByPrezzoMin(double prezzoMin) {
        return libro.getLibriByPrezzoMin(prezzoMin);
    }

    public Libri leggiLibriByPrezzoMax(double prezzoMax) {
        return libro.getLibriByPrezzoMax(prezzoMax);
    }

    public Libri leggiLibriByIsbn(String isbn) {
        return libro.getLibroByIsbn(isbn);
    }

    public Libri cercaLibri(String query) {
        return libro.searchLibro(query);
    }
}
