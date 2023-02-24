package org.Bibliotech.DAOimplementazione;

import org.Bibliotech.ConnessioneDB;
import org.Bibliotech.DAO.ArticoloDao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
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
    public boolean addArticolo(String titoloArticolo, String autori, String editore, String disciplina, String formato, String doi, String lingua, String nomeRivista, String issn) {
        String query="INSERT INTO b.ins_ArticoliRivista(titolo, autorinome_cognome, editore, disciplina, formato, doi, lingua, nomerivista, issnrivista) VALUES(?,?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, titoloArticolo);
            preparedStatement.setString(2, autori);
            preparedStatement.setString(3, editore);
            preparedStatement.setString(4, disciplina);
            preparedStatement.setString(5, formato);
            preparedStatement.setString(6, doi);
            preparedStatement.setString(7, lingua);
            preparedStatement.setString(8, nomeRivista);
            preparedStatement.setString(9, issn);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean addArticoloAddRivistaInDB(String titoloArticolo, String autori, String editore, String disciplina, String formato,
                                         String doi, String lingua,  String nomeRivista, String issn, String argomento, String dataPubblicazione,
                                         String responsabile, Double prezzo){
        String query="INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, " +
                "editore, lingua, formato, nomerivista, " + "issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES" +
                "(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, titoloArticolo);
            preparedStatement.setString(2, doi);
            preparedStatement.setString(3, autori);
            preparedStatement.setDate(4, Date.valueOf(dataPubblicazione));
            preparedStatement.setString(5, disciplina);
            preparedStatement.setString(6, editore);
            preparedStatement.setString(7, lingua);
            preparedStatement.setString(8, formato);
            preparedStatement.setString(9, nomeRivista);
            preparedStatement.setString(10, issn);
            preparedStatement.setString(11, argomento);
            preparedStatement.setString(12, responsabile);
            preparedStatement.setDouble(13, prezzo);
            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}