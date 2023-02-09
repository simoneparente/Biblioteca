package org.Bibliotech.DAOimplementazione;

import org.Bibliotech.ConnessioneDB;
import org.Bibliotech.Model.Libri;
import org.Bibliotech.Model.Libro;
import org.Bibliotech.DAO.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    public Libri getLibri(String query, String parametro) {
        Libri libri = new Libri();
        String getLibriQuery = query;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getLibriQuery);
            preparedStatement.setString(1, parametro);
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
    public Libri getLibri(String query, String parametro1, String parametro2) {
        Libri libri = new Libri();
        String getLibriQuery = query;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getLibriQuery);
            preparedStatement.setString(1, parametro1);
            preparedStatement.setString(2, parametro2);
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

    public boolean addLibro(Libro libro) {
        String addLibroQuery = "INSERT INTO b.libri (titolo, genere, editore, datapubblicazione, isbn, formato, lingua, prezzo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(addLibroQuery);
            preparedStatement.setString(1, libro.getTitolo());
            preparedStatement.setString(2, libro.getGenere());
            preparedStatement.setString(3, libro.getEditore());
            preparedStatement.setString(4, libro.getDataPubblicazione());
            preparedStatement.setString(5, libro.getIsbn());
            preparedStatement.setString(6, libro.getFormato());
            preparedStatement.setString(7, libro.getLingua());
            preparedStatement.setDouble(8, libro.getPrezzo());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Libri getLibri(){
        Libri libri = new Libri();
        String getLibriQuery = "SELECT * FROM b.libri";
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
    public Libri getLibriByTitolo(String titolo) {
        String getLibroByTitoloQuery = "SELECT * FROM b.libri WHERE titolo = ?";
        return getLibri(getLibroByTitoloQuery, titolo);
    }
    @Override
    public Libri getLibroByIsbn(String isbn) {
        String getLibroByIsbnQuery = "SELECT * FROM b.libri WHERE isbn = ?";
        return getLibri(getLibroByIsbnQuery, isbn);
    }
    @Override
    public Libri getLibriByRangeDataPubblicazione(String dataPubblicazioneMIN, String dataPubblicazioneMAX) {
        String getLibroByRangeDataPubblicazioneQuery = "SELECT * FROM b.libri WHERE datapubblicazione BETWEEN ? AND ?";
        return getLibri(getLibroByRangeDataPubblicazioneQuery, dataPubblicazioneMIN, dataPubblicazioneMAX);
    }
    @Override
    public Libri getLibriByDataPubblicazioneMin(String dataPubblicazioneMIN) {
        String getLibroByDataPubblicazioneMINQuery = "SELECT * FROM b.libri WHERE datapubblicazione >= ?";
        return getLibri(getLibroByDataPubblicazioneMINQuery, dataPubblicazioneMIN);
    }
    @Override
    public Libri getLibriByDataPubblicazioneMax(String dataPubblicazioneMAX) {
        String getLibroByDataPubblicazioneMAXQuery = "SELECT * FROM b.libri WHERE datapubblicazione <= ?";
        return getLibri(getLibroByDataPubblicazioneMAXQuery, dataPubblicazioneMAX);
    }
    @Override
    public Libri getLibriByEditore(String editore) {
        String getLibroByEditoreQuery = "SELECT * FROM b.libri WHERE editore = ?";
        return getLibri(getLibroByEditoreQuery, editore);
    }
    @Override
    public Libri getLibriByGenere(String genere) {
        String getLibroByGenereQuery = "SELECT * FROM b.libri WHERE genere = ?";
        return getLibri(getLibroByGenereQuery, genere);
    }
    @Override
    public Libri getLibriByLingua(String lingua) {
        String getLibroByLinguaQuery = "SELECT * FROM b.libri WHERE lingua = ?";
        return getLibri(getLibroByLinguaQuery, lingua);
    }
    @Override
    public Libri getLibriByFormato(String formato) {
        String getLibroByFormatoQuery = "SELECT * FROM b.libri WHERE formato = ?";
        return getLibri(getLibroByFormatoQuery, formato);
    }
    @Override
    public Libri getLibriByRangePrezzo(double prezzoMIN, double prezzoMAX) {
        String getLibroByRangePrezzoQuery = "SELECT * FROM b.libri WHERE prezzo BETWEEN ? AND ?";
        String prezzoMn = String.valueOf(prezzoMIN);
        String prezzoMx = String.valueOf(prezzoMAX);
        return getLibri(getLibroByRangePrezzoQuery, prezzoMn, prezzoMx);
    }
    @Override
    public Libri getLibriByPrezzoMin(double prezzoMIN) {
        String getLibroByPrezzoMINQuery = "SELECT * FROM b.libri WHERE prezzo >= ?";
        String prezzo = String.valueOf(prezzoMIN);
        return getLibri(getLibroByPrezzoMINQuery, prezzo);
    }
    @Override
    public Libri getLibriByPrezzoMax(double prezzoMAX) {
        String getLibroByPrezzoMAXQuery = "SELECT * FROM b.libri WHERE prezzo <= ?";
        String prezzo = String.valueOf(prezzoMAX);
        return getLibri(getLibroByPrezzoMAXQuery, prezzo);
    }

    @Override
    public boolean addLibro(Libro libro) {
        return false;
    }

    //Get tramite altre tabelle
    @Override
    public Libri getLibriByAutore(String nome, String cognome){
        String getLibroByAutoreQuery = "SELECT l.titolo, l.isbn, l.datapubblicazione, l.editore, l.genere, l.lingua, l.formato, l.prezzo FROM b.view_libri_autore WHERE nome = ? AND cognome = ?";
        return getLibri(getLibroByAutoreQuery, nome, cognome);
    }
    @Override
    public Libri getLibriBySerie(String nome, String cognome){
        String getLibroBySerieQuery = "SELECT l.titolo, l.isbn, l.datapubblicazione, l.editore, l.genere, l.lingua, l.formato, l.prezzo FROM b.view_libri_serie WHERE nome_serie = ?";
        return getLibri(getLibroBySerieQuery, nome, cognome);
    }

    @Override
    public Libri searchLibro(String query) {
        Libri libri = new Libri();
        String searchLibroQuery = query;
        try {
            PreparedStatement ps = connection.prepareStatement(searchLibroQuery);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
}