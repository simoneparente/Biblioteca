package org.Bibliotech.DAOimplementazione;
import org.Bibliotech.ConnessioneDB;
import org.Bibliotech.DAO.RivisteDao;
import org.Bibliotech.Model.Riviste;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImplementazioneRivista implements RivisteDao {
    private Connection connection;
    public ImplementazioneRivista() {
        try {
            connection= ConnessioneDB.getInstance().connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean addRivista(String issn, String nome, String argomento, String dataPubblicazione, String lingua, String formato, String responsabile, String prezzo, String doi_articoli_pubblicati){
        String addRivista= "INSERT INTO b.ins_rivista (issn, nome, argomento, datapubblicazione, lingua, formato, responsabile, prezzo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(addRivista);
            preparedStatement.setString(1, issn);
            preparedStatement.setString(2, nome);
            preparedStatement.setString(3, argomento);
            preparedStatement.setString(4, dataPubblicazione);
            preparedStatement.setString(5, lingua);
            preparedStatement.setString(6, formato);
            preparedStatement.setString(7, responsabile);
            preparedStatement.setString(8, prezzo);
            preparedStatement.setString(9, doi_articoli_pubblicati);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Riviste getRiviste(String query, String parametro) {
        Riviste riviste= new Riviste();
        String getRiviste= "SELECT * FROM b.riviste WHERE "+query+"=?";
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(getRiviste);
            preparedStatement.setString(1, parametro);
            ResultSet resultSet= preparedStatement.executeQuery();
            while (resultSet.next()){
                riviste.setIssn(resultSet.getString("issn"));
                riviste.setNome(resultSet.getString("nome"));
                riviste.setArgomento(resultSet.getString("argomento"));
                riviste.setDataPubblicazione(resultSet.getString("datapubblicazione"));
                riviste.setLingua(resultSet.getString("lingua"));
                riviste.setFormato(resultSet.getString("formato"));
                riviste.setResponsabile(resultSet.getString("responsabile"));
                riviste.setPrezzo(resultSet.getString("prezzo"));
                riviste.setDoi_articoli_pubblicati(resultSet.getString("doi_articoli_pubblicati"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return riviste;
    }

    @Override
    public Riviste getRiviste(String query, String parametro1, String parametro2) {
        return null;
    }

    @Override
    public Riviste getRiviste() {
        return null;
    }

    @Override
    public Riviste getRivisteByNome(String nome) {
        return null;
    }

    @Override
    public Riviste getRivisteByIssn(String issn) {
        return null;
    }

    @Override
    public Riviste getRivisteByArgomento(String argomento) {
        return null;
    }

    @Override
    public Riviste getRivisteByRangeDataPubblicazione(String dataPubblicazione) {
        return null;
    }

    @Override
    public Riviste getRivisteByDataPubblicazioneMin(String dataPubblicazioneMin) {
        return null;
    }

    @Override
    public Riviste getRivisteByDataPubblicazioneMax(String dataPubblicazioneMax) {
        return null;
    }

    @Override
    public Riviste getRivisteByLingua(String lingua) {
        return null;
    }

    @Override
    public Riviste getRivisteByFormato(String formato) {
        return null;
    }

    @Override
    public Riviste getRivisteByPrezzo(String prezzo) {
        return null;
    }
}
