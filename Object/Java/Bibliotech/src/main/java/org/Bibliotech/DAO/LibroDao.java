package org.Bibliotech.DAO;
import org.Bibliotech.Model.Libro;
import org.Bibliotech.Model.Libri;

public interface LibroDao {
    public boolean addLibro(String titolo, String autori, String genere, String editore, String dataPubblicazione, String isbn, String formato, String lingua, double prezzo);
    public Libri getLibri();
    public Libro getLibroByIsbn(String isbn);
    public Libri getLibriByTitolo(String titolo);
    public Libri getLibriByAutore(String autore);
    public Libri getLibriByLingua(String lingua);
    public Libri getLibriByEditore(String editore);
    public Libri getLibriByRangeDataPubblicazione(String dataPubblicazioneMin, String dataPubblicazioneMax);
    public Libri getLibriByDataPubblicazioneMin(String dataPubblicazioneMin);
    public Libri getLibriByDataPubblicazioneMax(String dataPubblicazioneMax);
    public Libri getLibriByFormato(String formato);
    public Libri getLibriByRangePrezzo(double min, double max);
    public Libri getLibriByPrezzoMin(double prezzoMin);
    public Libri getLibriByPrezzoMax(double prezzoMax);
    public Libri getLibriByGenere(String genere);

}