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

    @Override
    public boolean addArticolo(String titoloArticolo, String autori, String editore, String disciplina, String formato, String doi, String nomeRivista) {
        String getISSNquery = "SELECT ISSN FROM b.rivista WHERE nomeRivista = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(getISSNquery);
        }catch (SQLException e){
            e.printStackTrace();
        }

        String query = "INSERT INTO articolo (titoloArticolo, autori, editore, disciplina, formato, doi, nomeRivista) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return false;
    }
    @Override
    public boolean addArticoloAddRivistaInDB(String titoloArticolo, String autori, String editore, String disciplina, String formato, String doi, String nomeRivista, String issn, String argomento, String responsabile, String dataPubblicazioneRivista, Double prezzo) {
    return true;
    }
}