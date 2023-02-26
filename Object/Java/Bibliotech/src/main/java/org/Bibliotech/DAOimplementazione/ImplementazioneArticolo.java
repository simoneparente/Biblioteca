package org.Bibliotech.DAOimplementazione;

import org.Bibliotech.ConnessioneDB;
import org.Bibliotech.DAO.ArticoloDao;

import java.sql.*;

public class ImplementazioneArticolo implements ArticoloDao {
    private Connection connection;

    public ImplementazioneArticolo() {
        try {
            connection = ConnessioneDB.getInstance().connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean addArticolo(String titoloArticolo, String autori, String editore, String disciplina, String formato, String doi, String lingua, String nomeRivista, String issn) {
        String query = "INSERT INTO b.ins_ArticoliRivista(titolo, autorinome_cognome, datapubblicazione, editore, disciplina, formato, doi, lingua, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, titoloArticolo);
            preparedStatement.setString(2, autori);
            preparedStatement.setDate(3, getDateByISSN(issn));
            preparedStatement.setString(4, editore);
            preparedStatement.setString(5, disciplina);
            preparedStatement.setString(6, formato);
            preparedStatement.setString(7, doi);
            preparedStatement.setString(8, lingua);
            preparedStatement.setString(9, nomeRivista);
            preparedStatement.setString(10, issn);
            preparedStatement.setString(11, getArgomentoByISSN(issn));
            preparedStatement.setString(12, getResponsabileByISSN(issn));
            preparedStatement.setDouble(13, getPrezoByISSN(issn));

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Date getDateByISSN(String issn) {
        String query = "SELECT dataPubblicazione FROM b.riviste WHERE issn=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, issn);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) return rs.getDate(1);
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }

    private double getPrezoByISSN(String issn) {
        String query = "SELECT prezzo FROM b.riviste WHERE issn=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, issn);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return 0;
    }

    private String getResponsabileByISSN(String issn) {
        String query = "SELECT responsabile FROM b.riviste WHERE issn=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, issn);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }

    private String getArgomentoByISSN(String issn) {
        String query = "SELECT argomento FROM b.riviste WHERE issn=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, issn);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }


    @Override
    public boolean addArticoloAddRivistaInDB(String titoloArticolo, String autori, String editore, String disciplina, String formato,
                                             String doi, String lingua, String nomeRivista, String issn, String argomento, String dataPubblicazione,
                                             String responsabile, Double prezzo) {
        String query = "INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, " +
                "editore, lingua, formato, nomerivista, " + "issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES" +
                "(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}