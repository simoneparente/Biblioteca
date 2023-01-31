package org.Bibliotech.DAOimplementazione;

import org.Bibliotech.ConnessioneDB;
import org.Bibliotech.DAO.ArticoloDao;
import org.Bibliotech.Model.Articoli;
import org.Bibliotech.Model.Articolo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImplementazioneArticolo implements ArticoloDao {
    private Connection connection;
    public ImplementazioneArticolo(){
        try {
            connection = ConnessioneDB.getInstance().connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Aggiunta Articolo
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

    //Get Articoli tramite parametri
    @Override
    public Articoli getArticoli(String query, String parametro){
        Articoli articoli = new Articoli();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, parametro);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Articolo articolo = new Articolo();
                articolo.setTitolo(resultSet.getString("titolo"));
                articolo.setDataPubblicazione(resultSet.getString("doi"));
                articolo.setEditore(resultSet.getString("dataPubblicazione"));
                articolo.setDoi(resultSet.getString("disciplina"));
                articolo.setLingua(resultSet.getString("editore"));
                articolo.setFormato(resultSet.getString("lingua"));
                articolo.setDisciplina(resultSet.getString("formato"));
                articoli.addArticolo(articolo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articoli;
    }
    @Override
    public Articoli getArticoli(String query, String parametro1, String parametro2){
        Articoli articoli = new Articoli();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, parametro1);
            preparedStatement.setString(2, parametro2);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Articolo articolo = new Articolo();
                articolo.setTitolo(resultSet.getString("titolo"));
                articolo.setDataPubblicazione(resultSet.getString("doi"));
                articolo.setEditore(resultSet.getString("dataPubblicazione"));
                articolo.setDoi(resultSet.getString("disciplina"));
                articolo.setLingua(resultSet.getString("editore"));
                articolo.setFormato(resultSet.getString("lingua"));
                articolo.setDisciplina(resultSet.getString("formato"));
                articoli.addArticolo(articolo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articoli;
    }

    //Get tramite tabella Articolo
    @Override
    public Articoli getArticoli(){
        Articoli articoli = new Articoli();
        String getArticoliQuery = "SELECT * FROM b.articolo";
        try {
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
    public Articoli getArticoliByTitolo(String titolo){
        String getArticoloByTitoloQuery = "SELECT * FROM b.articolo WHERE titolo = ?";
        return getArticoli(getArticoloByTitoloQuery, titolo);
    }
    @Override
    public Articoli getArticoloByDoi(String doi){
        String getArticoloByDoiQuery = "SELECT * FROM b.articolo WHERE doi = ?";
        return getArticoli(getArticoloByDoiQuery, doi);
    }
    @Override
    public Articoli getArticoliByDataPubblicazioneMin(String dataPubblicazione){
        String getArticoloByDataPubblicazioneQuery = "SELECT * FROM b.articolo WHERE dataPubblicazione >= ?";
        return getArticoli(getArticoloByDataPubblicazioneQuery, dataPubblicazione);
    }
    @Override
    public Articoli getArticoliByDataPubblicazioneMax(String dataPubblicazione){
        String getArticoloByDataPubblicazioneQuery = "SELECT * FROM b.articolo WHERE dataPubblicazione <= ?";
        return getArticoli(getArticoloByDataPubblicazioneQuery, dataPubblicazione);
    }
    @Override
    public Articoli getArticoliByRangeDataPubblicazione(String dataPubblicazioneMIN, String dataPubblicazioneMAX){
        String getArticoloByDataPubblicazioneQuery = "SELECT * FROM b.articolo WHERE dataPubblicazione = ?";
        return getArticoli(getArticoloByDataPubblicazioneQuery, dataPubblicazioneMIN, dataPubblicazioneMAX);
    }
    @Override
    public Articoli getArticoliByDisciplina(String disciplina){
        String getArticoloByDisciplinaQuery = "SELECT * FROM b.articolo WHERE disciplina = ?";
        return getArticoli(getArticoloByDisciplinaQuery, disciplina);
    }
    @Override
    public Articoli getArticoliByEditore(String editore){
        String getArticoloByEditoreQuery = "SELECT * FROM b.articolo WHERE editore = ?";
        return getArticoli(getArticoloByEditoreQuery, editore);
    }
    @Override
    public Articoli getArticoliByLingua(String lingua){
        String getArticoloByLinguaQuery = "SELECT * FROM b.articolo WHERE lingua = ?";
        return getArticoli(getArticoloByLinguaQuery, lingua);
    }
    @Override
    public Articoli getArticoliByFormato(String formato){
        String getArticoloByFormatoQuery = "SELECT * FROM b.articolo WHERE formato = ?";
        return getArticoli(getArticoloByFormatoQuery, formato);
    }

    //Get tramite altre tabelle
    @Override
    public Articoli getArticoliByAutore(String nome, String cognome){
        String getArticoloByAutoreQuery = "SELECT DISTINCT a.titolo, a.doi, a.datapubblicazione, a.disciplina, a.editore, a.lingua, a.formato FROM view_articolo_autore a WHERE au.nome = ? AND au.cognome = ?";
        return getArticoli(getArticoloByAutoreQuery, nome, cognome);
    }
    @Override
    public Articoli getArticoliByRivista(String rivista){
        String getArticoloByRivistaQuery = "SELECT DISTINCT a.titolo, a.doi, a.datapubblicazione, a.disciplina, a.editore, a.lingua, a.formato FROM view_articolo_rivista a WHERE r.nome = ?";
        return getArticoli(getArticoloByRivistaQuery, rivista);
    }
    @Override
    public Articoli getArticoliByConferenza(String conferenza){
        String getArticoloByConferenzaQuery = "SELECT DISTINCT a.titolo, a.doi, a.datapubblicazione, a.disciplina, a.editore, a.lingua, a.formato FROM view_articolo_conferenza a WHERE titolo_conferenza = ?";
        return getArticoli(getArticoloByConferenzaQuery, conferenza);
    }
}