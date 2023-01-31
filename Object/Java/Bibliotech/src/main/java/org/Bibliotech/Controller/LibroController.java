package org.Bibliotech.Controller;

import org.Bibliotech.DAOimplementazione.ImplementazioneLibro;
import org.Bibliotech.Model.Libri;
import org.Bibliotech.Model.Libro;

public class LibroController {
    ImplementazioneLibro libro;

    public LibroController() {
        libro = new ImplementazioneLibro();
    }

    public boolean addLibro(String titolo, String isbn, String autorinome_cognome, String dataPubblicazione, String editore, String genere, String lingua, String formato, double prezzo, String nome_serie_di_appartnenza, String issn_serie_di_appartenenza){
        return libro.addLibro(titolo, isbn, autorinome_cognome, dataPubblicazione, editore, genere, lingua, formato, prezzo, nome_serie_di_appartnenza, issn_serie_di_appartenenza);
    }
    public Libri leggiLibri(){
        return libro.getLibri();
    }
    public Libri leggiLibriByTitolo(String titolo){
        return libro.getLibriByTitolo(titolo);
    }
    public Libri leggiLibriByAutore(String autore){
        return libro.getLibriByAutore(autore);
    }
    public Libri leggiLibriByGenere(String genere){
        return libro.getLibriByGenere(genere);
    }
    public Libri leggiLibriByLingua(String lingua){
        return libro.getLibriByLingua(lingua);
    }
    public Libri leggiLibriByEditore(String editore){
        return libro.getLibriByEditore(editore);
    }
    public Libri leggiLibriByRangeDataPubblicazione(String dataPubblicazioneMin, String dataPubblicazioneMax){
        return libro.getLibriByRangeDataPubblicazione(dataPubblicazioneMin, dataPubblicazioneMax);
    }
    public Libri leggiLibriByDataPubblicazioneMin(String dataPubblicazioneMin){
        return libro.getLibriByDataPubblicazioneMin(dataPubblicazioneMin);
    }
    public Libri leggiLibriByDataPubblicazioneMax(String dataPubblicazioneMax){
        return libro.getLibriByDataPubblicazioneMax(dataPubblicazioneMax);
    }
    public Libri leggiLibriByFormato(String formato){
        return libro.getLibriByFormato(formato);
    }
    public Libri leggiLibriByRangePrezzo(double min, double max){
        return libro.getLibriByRangePrezzo(min, max);
    }
    public Libri leggiLibriByPrezzoMin(double prezzoMin){
        return libro.getLibriByPrezzoMin(prezzoMin);
    }
    public Libri leggiLibriByPrezzoMax(double prezzoMax){
        return libro.getLibriByPrezzoMax(prezzoMax);
    }
    public Libro leggiLibriByIsbn(String isbn){
        return libro.getLibroByIsbn(isbn);
    }
}
