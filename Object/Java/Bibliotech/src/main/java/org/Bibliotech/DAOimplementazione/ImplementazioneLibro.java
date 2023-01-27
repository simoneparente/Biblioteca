package org.Bibliotech.DAOimplementazione;

import org.Bibliotech.ConnessioneDB;
import org.Bibliotech.DAO.LibroDao;
import org.Bibliotech.Model.Autore;
import org.Bibliotech.Model.Libri;
import org.Bibliotech.Model.Libro;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ImplementazioneLibro implements LibroDao {

    private Connection connection;
    public ImplementazioneLibro() {
        try {
            connection= ConnessioneDB.getInstance().connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean addLibro(String titolo, ArrayList<Autore> autori, String genere,
                            String editore, String dataPubblicazione, String isbn,
                            String formato, double prezzo) {
        String addLibroQuery
    }

    @Override
    public Libri getLibri() {
        return null;
    }

    @Override
    public Libro getLibroByIsbn(String isbn) {
        return null;
    }

    @Override
    public Libri getLibriByTitolo(String titolo) {
        return null;
    }

    @Override
    public Libri getLibriByAutore(String autore) {
        return null;
    }

    @Override
    public Libri getLibriByAutori(ArrayList<Autore> autori) {
        return null;
    }

    @Override
    public Libri getLibriByEditore(String editore) {
        return null;
    }

    @Override
    public Libri getLibriByRangeDataPubblicazione(String dataPubblicazioneMin, String dataPubblicazioneMax) {
        return null;
    }

    @Override
    public Libri getLibriByDataPubblicazioneMin(String dataPubblicazioneMin) {
        return null;
    }

    @Override
    public Libri getLibriByDataPubblicazioneMax(String dataPubblicazioneMax) {
        return null;
    }

    @Override
    public Libri getLibriByFormato(String formato) {
        return null;
    }

    @Override
    public Libri getLibriByRangePrezzo(double min, double max) {
        return null;
    }

    @Override
    public Libri getLibriByPrezzoMin(double prezzoMin) {
        return null;
    }

    @Override
    public Libri getLibriByPrezzoMax(double prezzoMax) {
        return null;
    }

    @Override
    public Libri getLibriByGenere(String genere) {
        return null;
    }
}
