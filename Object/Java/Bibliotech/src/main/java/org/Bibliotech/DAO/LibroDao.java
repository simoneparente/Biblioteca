package org.Bibliotech.DAO;
import org.Bibliotech.Model.Libro;
import org.Bibliotech.Model.Libri;

public interface LibroDao {
    Libri getLibri(String query, String parametro);
    Libri getLibri(String query, String parametro1, String parametro2);

    //Attributi Tabella Libri
    Libri getLibri();
    Libri getLibriByTitolo(String titolo);
    Libri getLibroByIsbn(String isbn);
    Libri getLibriByRangeDataPubblicazione(String dataPubblicazioneMin, String dataPubblicazioneMax);
    Libri getLibriByDataPubblicazioneMin(String dataPubblicazioneMin);
    Libri getLibriByDataPubblicazioneMax(String dataPubblicazioneMax);
    Libri getLibriByEditore(String editore);
    Libri getLibriByGenere(String genere);
    Libri getLibriByLingua(String lingua);
    Libri getLibriByFormato(String formato);
    Libri getLibriByRangePrezzo(double min, double max);
    Libri getLibriByPrezzoMin(double prezzoMin);
    Libri getLibriByPrezzoMax(double prezzoMax);


    public String getISSNByNomeSerie(String nomeSerie);


    //metodo per aggiungere un libro

    boolean addLibro(Libro libro);

    //Attributi Tabelle Multiple
    Libri getLibriByAutore(String nome, String cognome);
    Libri getLibriBySerie(String nome_serie_di_appartenenza, String issn_serie_di_appartenenza);
    Libri searchLibro(String query);
}
