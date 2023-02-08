package org.Bibliotech.DAO;
import org.Bibliotech.Model.Libro;
import org.Bibliotech.Model.Libri;

public interface LibroDao {
    public Libri getLibri(String query, String parametro);
    public Libri getLibri(String query, String parametro1, String parametro2);

    //Attributi Tabella Libri
    public Libri getLibri();
    public Libri getLibriByTitolo(String titolo);
    public Libri getLibroByIsbn(String isbn);
    public Libri getLibriByRangeDataPubblicazione(String dataPubblicazioneMin, String dataPubblicazioneMax);
    public Libri getLibriByDataPubblicazioneMin(String dataPubblicazioneMin);
    public Libri getLibriByDataPubblicazioneMax(String dataPubblicazioneMax);
    public Libri getLibriByEditore(String editore);
    public Libri getLibriByGenere(String genere);
    public Libri getLibriByLingua(String lingua);
    public Libri getLibriByFormato(String formato);
    public Libri getLibriByRangePrezzo(double min, double max);
    public Libri getLibriByPrezzoMin(double prezzoMin);
    public Libri getLibriByPrezzoMax(double prezzoMax);


    //metodo per aggiungere un libro

    public boolean addLibro(Libro libro);

    //Attributi Tabelle Multiple
    public Libri getLibriByAutore(String nome, String cognome);
    public Libri getLibriBySerie(String nome_serie_di_appartenenza, String issn_serie_di_appartenenza);
    public Libri searchLibro(String query);
}
