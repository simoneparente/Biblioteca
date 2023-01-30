package org.Bibliotech.DAOimplementazione;

import org.Bibliotech.ConnessioneDB;
import org.Bibliotech.DAO.ArticoloDao;
import org.Bibliotech.Model.Articolo;
import org.Bibliotech.Model.Articoli;
import org.Bibliotech.Model.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImplementazioneArticolo implements ArticoloDao {
    private Connection connection;

    public ImplementazioneArticolo() {
        try {
            connection = ConnessioneDB.getInstance().connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean addArticolo(String doi, String titolo, String autorinome_cognome, String dataPubblicazione, String editore, String lingua, String formato, String disciplina) {
        String addArticoloQuery = "INSERT INTO b.ins_articolo_autore (doi, titolo, autorinome_cognome, dataPubblicazione, editore, lingua, formato, disciplina) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(addArticoloQuery);
            preparedStatement.setString(1, doi);
            preparedStatement.setString(2, titolo);
            preparedStatement.setString(3, autorinome_cognome);
            preparedStatement.setString(4, dataPubblicazione);
            preparedStatement.setString(5, editore);
            preparedStatement.setString(6, lingua);
            preparedStatement.setString(7, formato);
            preparedStatement.setString(8, disciplina);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public Articoli getArticoli() {
        Articoli articoli = new Articoli();
        String getArticoliQuery = "SELECT * FROM b.articolo";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(getArticoliQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Articolo articolo = new Articolo();
                articolo.setDoi(resultSet.getString("doi"));
                articolo.setTitolo(resultSet.getString("titolo"));
                articolo.setDataPubblicazione(resultSet.getString("dataPubblicazione"));
                articolo.setEditore(resultSet.getString("editore"));
                articolo.setLingua(resultSet.getString("lingua"));
                articolo.setDisciplina(resultSet.getString("disciplina"));
                articolo.setFormato(resultSet.getString("formato"));
                articoli.addArticolo(articolo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articoli;
    }

    @Override
    public Articoli getArticoloByDoi(String doi) {
        Articoli articoli = new Articoli();
        String getArticoloByDoiQuery = "SELECT * FROM b.articolo WHERE doi = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(getArticoloByDoiQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Articolo articolo = new Articolo();
                articolo.setDoi(resultSet.getString("doi"));
                articolo.setTitolo(resultSet.getString("titolo"));
                articolo.setDataPubblicazione(resultSet.getString("dataPubblicazione"));
                articolo.setEditore(resultSet.getString("editore"));
                articolo.setLingua(resultSet.getString("lingua"));
                articolo.setFormato(resultSet.getString("formato"));
                articoli.addArticolo(articolo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articoli;
    }

    @Override
    public Articoli getArticoliByTitolo(String titolo) {
        Articoli articoli = new Articoli();
        String getArticoliByTitoloQuery = "SELECT * FROM b.articolo WHERE titolo = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(getArticoliByTitoloQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Articolo articolo = new Articolo();
                articolo.setDoi(resultSet.getString("doi"));
                articolo.setTitolo(resultSet.getString("titolo"));
                articolo.setDataPubblicazione(resultSet.getString("dataPubblicazione"));
                articolo.setEditore(resultSet.getString("editore"));
                articolo.setLingua(resultSet.getString("lingua"));
                articolo.setFormato(resultSet.getString("formato"));
                articoli.addArticolo(articolo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articoli;
    }

    @Override
    public Articoli getArticoliByAutore(String autore) {
        Articoli articoli = new Articoli();
        String getArticoliByAutoreQuery = "SELECT ar.titolo, ar.doi, ar.datapubblicazione, ar.editore, ar.lingua, ar.formato\n" +
                                          "FROM (b.articolo as ar NATURAL JOIN b.autorearticolo as aa ) JOIN b.autore as au on aa.id_autore = au.id_autore" +
                                          "WHERE au.nome = ? AND au.cognome = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(getArticoliByAutoreQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Articolo articolo = new Articolo();
                articolo.setDoi(resultSet.getString("doi"));
                articolo.setTitolo(resultSet.getString("titolo"));
                articolo.setDataPubblicazione(resultSet.getString("dataPubblicazione"));
                articolo.setEditore(resultSet.getString("editore"));
                articolo.setLingua(resultSet.getString("lingua"));
                articolo.setFormato(resultSet.getString("formato"));
                articoli.addArticolo(articolo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articoli;
    }

    @Override
    public Articoli getArticoliByLingua(String lingua) {
        Articoli articoli = new Articoli();
        String getArticoliByLinguaQuery = "SELECT * FROM b.articolo WHERE lingua = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(getArticoliByLinguaQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Articolo articolo = new Articolo();
                articolo.setDoi(resultSet.getString("doi"));
                articolo.setTitolo(resultSet.getString("titolo"));
                articolo.setDataPubblicazione(resultSet.getString("dataPubblicazione"));
                articolo.setEditore(resultSet.getString("editore"));
                articolo.setLingua(resultSet.getString("lingua"));
                articolo.setFormato(resultSet.getString("formato"));
                articoli.addArticolo(articolo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articoli;
    }

    @Override
    public Articoli getArticoliByEditore(String editore) {
        Articoli articoli = new Articoli();
        String getArticoliByEditoreQuery = "SELECT * FROM b.articolo WHERE editore = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(getArticoliByEditoreQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Articolo articolo = new Articolo();
                articolo.setDoi(resultSet.getString("doi"));
                articolo.setTitolo(resultSet.getString("titolo"));
                articolo.setDataPubblicazione(resultSet.getString("dataPubblicazione"));
                articolo.setEditore(resultSet.getString("editore"));
                articolo.setLingua(resultSet.getString("lingua"));
                articolo.setFormato(resultSet.getString("formato"));
                articoli.addArticolo(articolo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articoli;
    }

    @Override
    public Articoli getArticoliByRangeDataPubblicazione(String dataPubblicazioneMin, String dataPubblicazioneMax) {
        Articoli articoli = new Articoli();
        String getArticoliByRangeDataPubblicazioneQuery = "SELECT * FROM b.articolo WHERE dataPubblicazione BETWEEN ? AND ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(getArticoliByRangeDataPubblicazioneQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Articolo articolo = new Articolo();
                articolo.setDoi(resultSet.getString("doi"));
                articolo.setTitolo(resultSet.getString("titolo"));
                articolo.setDataPubblicazione(resultSet.getString("dataPubblicazione"));
                articolo.setEditore(resultSet.getString("editore"));
                articolo.setLingua(resultSet.getString("lingua"));
                articolo.setFormato(resultSet.getString("formato"));
                articoli.addArticolo(articolo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articoli;
    }

    @Override
    public Articoli getArticoliByDataPubblicazioneMin(String dataPubblicazioneMin) {
        Articoli articoli = new Articoli();
        String getArticoliByDataPubblicazioneMinQuery = "SELECT * FROM b.articolo WHERE dataPubblicazione >= ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(getArticoliByDataPubblicazioneMinQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Articolo articolo = new Articolo();
                articolo.setDoi(resultSet.getString("doi"));
                articolo.setTitolo(resultSet.getString("titolo"));
                articolo.setDataPubblicazione(resultSet.getString("dataPubblicazione"));
                articolo.setEditore(resultSet.getString("editore"));
                articolo.setLingua(resultSet.getString("lingua"));
                articolo.setFormato(resultSet.getString("formato"));
                articoli.addArticolo(articolo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articoli;
    }

    @Override
    public Articoli getArticoliByDataPubblicazioneMax(String dataPubblicazioneMax) {
        Articoli articoli = new Articoli();
        String getArticoliByDataPubblicazioneMaxQuery = "SELECT * FROM b.articolo WHERE dataPubblicazione <= ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(getArticoliByDataPubblicazioneMaxQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Articolo articolo = new Articolo();
                articolo.setDoi(resultSet.getString("doi"));
                articolo.setTitolo(resultSet.getString("titolo"));
                articolo.setDataPubblicazione(resultSet.getString("dataPubblicazione"));
                articolo.setEditore(resultSet.getString("editore"));
                articolo.setLingua(resultSet.getString("lingua"));
                articolo.setFormato(resultSet.getString("formato"));
                articoli.addArticolo(articolo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articoli;
    }

    @Override
    public Articoli getArticoliByFormato(String formato) {
        Articoli articoli = new Articoli();
        String getArticoliByFormatoQuery = "SELECT * FROM b.articolo WHERE formato = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(getArticoliByFormatoQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Articolo articolo = new Articolo();
                articolo.setDoi(resultSet.getString("doi"));
                articolo.setTitolo(resultSet.getString("titolo"));
                articolo.setDataPubblicazione(resultSet.getString("dataPubblicazione"));
                articolo.setEditore(resultSet.getString("editore"));
                articolo.setLingua(resultSet.getString("lingua"));
                articolo.setFormato(resultSet.getString("formato"));
                articoli.addArticolo(articolo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articoli;
    }

    @Override
    public Articoli getArticoliByDisciplina(String disciplina) {
        Articoli articoli = new Articoli();
        String getArticoliByDisciplinaQuery = "SELECT * FROM b.articolo WHERE disciplina = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(getArticoliByDisciplinaQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Articolo articolo = new Articolo();
                articolo.setDoi(resultSet.getString("doi"));
                articolo.setTitolo(resultSet.getString("titolo"));
                articolo.setDataPubblicazione(resultSet.getString("dataPubblicazione"));
                articolo.setEditore(resultSet.getString("editore"));
                articolo.setLingua(resultSet.getString("lingua"));
                articolo.setFormato(resultSet.getString("formato"));
                articoli.addArticolo(articolo);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return articoli;
    }

}