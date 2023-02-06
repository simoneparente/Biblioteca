package org.Bibliotech.Controller;

import org.Bibliotech.DAOimplementazione.ImplementazioneLibro;
import org.Bibliotech.Model.Libri;
import org.Bibliotech.Model.Libro;

public class LibroController {
    ImplementazioneLibro libro;

    public LibroController() {
        libro = new ImplementazioneLibro();
    }
    public Libri leggiLibri(){
        return libro.getLibri();
    }
    public Libri leggiLibriByTitolo(String titolo){
        return libro.getLibriByTitolo(titolo);
    }
    public Libri leggiLibriByAutore(String autorenome, String autorecognome){
        return libro.getLibriByAutore(autorenome, autorecognome);
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
    public Libri leggiLibriByIsbn(String isbn){
        return libro.getLibroByIsbn(isbn);
    }

    public Libri cercaLibri(String query){
        return libro.searchLibro(query);
    }
}
