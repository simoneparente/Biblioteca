package org.Bibliotech.DAOimplementazione;

import org.Bibliotech.ConnessioneDB;
import org.Bibliotech.DAO.FiltriDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ImplementazioneFiltri implements FiltriDao {
    private Connection connection;

    public ImplementazioneFiltri() {
        try {
            connection= ConnessioneDB.getInstance().connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public ArrayList<String> getNomeAutori() {
        ArrayList<String> nomiAutori = new ArrayList<>();
        String getNomeAutoriQuery = "SELECT DISTINCT nome FROM b.autore";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(getNomeAutoriQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                nomiAutori.add(rs.getString("nome"));
            }
        } catch(SQLException e){

        }
        return nomiAutori;
    }

    @Override
    public ArrayList<String> getCognomeAutori() {
        ArrayList<String> cognomiAutori = new ArrayList<>();
        String getCognomeAutoriQuery = "SELECT DISTINCT cognome FROM b.autore";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(getCognomeAutoriQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                cognomiAutori.add(rs.getString("cognome"));
            }
        } catch(SQLException e){

        }
        return cognomiAutori;
    }

    @Override
    public ArrayList<String> getGenere() {
        ArrayList<String> generi = new ArrayList<>();
        String getGenereQuery = "SELECT DISTINCT FROM b.libro";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(getGenereQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                generi.add(rs.getString("genere"));
            }
        } catch(SQLException e){

        }
        return generi;
    }

    @Override
    public ArrayList<String> getLingua() {
        ArrayList<String> lingue = new ArrayList<>();
        String getLinguaQuery = "SELECT DISTINCT lingua FROM b.libro";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(getLinguaQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                lingue.add(rs.getString("lingua"));
            }
        } catch(SQLException e){

        }
        return lingue;
    }

    @Override
    public ArrayList<String> getEditore() {
        ArrayList<String> editori = new ArrayList<>();
        String getEditoreQuery = "SELECT DISTINCT editore FROM b.libro";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(getEditoreQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                editori.add(rs.getString("editore"));
            }
        } catch(SQLException e){

        }
        return editori;
    }

    @Override
    public ArrayList<String> getFormato() {
        ArrayList<String> formati = new ArrayList<>();
        String getFormatoQuery = "SELECT DISTINCT formato FROM b.libro";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(getFormatoQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                formati.add(rs.getString("formato"));
            }
        } catch(SQLException e){

        }
        return formati;
    }
}
