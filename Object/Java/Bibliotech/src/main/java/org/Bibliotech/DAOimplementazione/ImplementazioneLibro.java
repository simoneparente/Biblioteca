package org.Bibliotech.DAOimplementazione;

import org.Bibliotech.ConnessioneDB;
import org.Bibliotech.DAO.LibroDao;
import org.Bibliotech.Model.Autore;
import org.Bibliotech.Model.Libri;
import org.Bibliotech.Model.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public boolean addLibro(String titolo, ArrayList<Autore> autori, String genere, String editore, String dataPubblicazione, String isbn, String formato, String lingua, double prezzo) {
        String addLibroQuery= "INSERT INTO b.libro (titolo, genere, editore, data_pubblicazione, isbn, formato, lingua, prezzo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(addLibroQuery);
            preparedStatement.setString(1, titolo);
            preparedStatement.setString(2, genere);
            preparedStatement.setString(3, editore);
            preparedStatement.setString(4, dataPubblicazione);
            preparedStatement.setString(5, isbn);
            preparedStatement.setString(6, formato);
            preparedStatement.setString(7, lingua);
            preparedStatement.setDouble(8, prezzo);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public Libri getLibri() {
        Libri libri = new Libri();
        String getLibriQuery = "SELECT * FROM b.libro";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getLibriQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Libro libro = new Libro();
                libro.setTitolo(rs.getString("titolo"));
                libro.setGenere(rs.getString("genere"));
                libro.setEditore(rs.getString("editore"));
                libro.setDataPubblicazione(rs.getString("datapubblicazione"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setFormato(rs.getString("formato"));
                libro.setLingua(rs.getString("lingua"));
                libro.setPrezzo(rs.getDouble("prezzo"));
                libri.addLibro(libro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libri;
    }

    @Override
    public Libro getLibroByIsbn(String isbn) {
        Libro libro = new Libro();
        String getLibroByIsbnQuery = "SELECT * FROM b.libro WHERE isbn = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getLibroByIsbnQuery);
            preparedStatement.setString(1, isbn);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                libro.setTitolo(rs.getString("titolo"));
                libro.setGenere(rs.getString("genere"));
                libro.setEditore(rs.getString("editore"));
                libro.setDataPubblicazione(rs.getString("datapubblicazione"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setFormato(rs.getString("formato"));
                libro.setLingua(rs.getString("lingua"));
                libro.setPrezzo(rs.getDouble("prezzo"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return libro;
    }

    @Override
    public Libri getLibriByTitolo(String titolo) {
        Libri libri = new Libri();
        String getLibriByTitoloQuery = "SELECT * FROM b.libro WHERE titolo = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getLibriByTitoloQuery);
            preparedStatement.setString(1, titolo);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Libro libro = new Libro();
                libro.setTitolo(rs.getString("titolo"));
                libro.setGenere(rs.getString("genere"));
                libro.setEditore(rs.getString("editore"));
                libro.setDataPubblicazione(rs.getString("datapubblicazione"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setFormato(rs.getString("formato"));
                libro.setLingua(rs.getString("lingua"));
                libro.setPrezzo(rs.getDouble("prezzo"));
                libri.addLibro(libro);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return libri;
    }

    @Override
    public Libri getLibriByAutore(String autore) {
        Libri libri = new Libri();
        String getLibriByAutoreQuery = "SELECT * FROM b.viewlibroautoreserie WHERE nome_cognome LIKE '%'?'%'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getLibriByAutoreQuery);
            preparedStatement.setString(1, autore);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Libro libro = new Libro();
                libro.setTitolo(rs.getString("titolo"));
                libro.setGenere(rs.getString("genere"));
                libro.setEditore(rs.getString("editore"));
                libro.setDataPubblicazione(rs.getString("datapubblicazione"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setFormato(rs.getString("formato"));
                libro.setLingua(rs.getString("lingua"));
                libro.setPrezzo(rs.getDouble("prezzo"));
                libri.addLibro(libro);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return libri;
    }

    @Override
    public Libri getLibriByLingua(String lingua) {
        Libri libri = new Libri();
        String getLibriByLinguaQuery = "SELECT * FROM b.libro WHERE lingua = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getLibriByLinguaQuery);
            preparedStatement.setString(1, lingua);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Libro libro = new Libro();
                libro.setTitolo(rs.getString("titolo"));
                libro.setGenere(rs.getString("genere"));
                libro.setEditore(rs.getString("editore"));
                libro.setDataPubblicazione(rs.getString("datapubblicazione"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setFormato(rs.getString("formato"));
                libro.setLingua(rs.getString("lingua"));
                libro.setPrezzo(rs.getDouble("prezzo"));
                libri.addLibro(libro);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return libri;
    }


    @Override
    public Libri getLibriByEditore(String editore) {
        Libri libri = new Libri();
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
