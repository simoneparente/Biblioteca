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
    public ArrayList<String> getAutoriLibri() {
        ArrayList<String> autori = new ArrayList<>();
        String getAutoriQuery = "SELECT DISTINCT nome, cognome FROM b.autore";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(getAutoriQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                autori.add(rs.getString("nome") + " " + rs.getString("cognome"));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return autori;
    }

    @Override
    public ArrayList<String> getGenereLibri() {
        ArrayList<String> generi = new ArrayList<>();
        String getGenereQuery = "SELECT DISTINCT genere FROM b.libro";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(getGenereQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                generi.add(rs.getString("genere"));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return generi;
    }

    @Override
    public ArrayList<String> getLinguaLibri() {
        ArrayList<String> lingue = new ArrayList<>();
        String getLinguaQuery = "SELECT DISTINCT lingua FROM b.libro";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(getLinguaQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                lingue.add(rs.getString("lingua"));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return lingue;
    }

    @Override
    public ArrayList<String> getEditoreLibri() {
        ArrayList<String> editori = new ArrayList<>();
        String getEditoreQuery = "SELECT DISTINCT editore FROM b.libro";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(getEditoreQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                editori.add(rs.getString("editore"));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return editori;
    }

    @Override
    public ArrayList<String> getFormatoLibri() {
        ArrayList<String> formati = new ArrayList<>();
        String getFormatoQuery = "SELECT DISTINCT formato FROM b.libro";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(getFormatoQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                formati.add(rs.getString("formato"));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return formati;
    }

    @Override
    public ArrayList<String> getPresentatoInLibro() {
        ArrayList<String> presentatoIn = new ArrayList<>();
        String getPresentatoInQuery = "SELECT DISTINCT e.strutturaospitante, e.datainizio FROM (b.libro as l NATURAL JOIN b.presentazione as p) JOIN b.evento as e ON e.id_evento = p.evento ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getPresentatoInQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                presentatoIn.add(rs.getString("strutturaospitante") + " " + rs.getString("datainizio"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return presentatoIn;
    }

    @Override
    public ArrayList<String> getAutoriArticoli() {
        ArrayList<String> autori = new ArrayList<>();
        String getAutoriQuery = "SELECT DISTINCT a.nome, a.cognome FROM (b.autore AS a NATURAL JOIN b.autorearticolo AS aa) JOIN b.articolo AS ar ON ar.id_articolo = aa.id_articolo";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(getAutoriQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                autori.add(rs.getString("nome") + " " + rs.getString("cognome"));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return autori;
    }

    @Override
    public ArrayList<String> getDisciplinaArticoli() {
        ArrayList<String> discipline = new ArrayList<>();
        String getDisciplinaQuery = "SELECT DISTINCT disciplina FROM b.articolo";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(getDisciplinaQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                discipline.add(rs.getString("disciplina"));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return discipline;
    }

    @Override
    public ArrayList<String> getLinguaArticoli() {
        ArrayList<String> lingue = new ArrayList<>();
        String getLinguaQuery = "SELECT DISTINCT lingua FROM b.articolo";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(getLinguaQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                lingue.add(rs.getString("lingua"));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return lingue;
    }

    @Override
    public ArrayList<String> getEditoreArticoli() {
        ArrayList<String> editori = new ArrayList<>();
        String getEditoreQuery = "SELECT DISTINCT editore FROM b.articolo";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(getEditoreQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                editori.add(rs.getString("editore"));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return editori;
    }

    @Override
    public ArrayList<String> getFormatoArticoli() {
        ArrayList<String> formati = new ArrayList<>();
        String getFormatoQuery = "SELECT DISTINCT formato FROM b.articolo";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(getFormatoQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                formati.add(rs.getString("formato"));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return formati;
    }

    @Override
    public ArrayList<String> getPresentatoInArticoli() {
        ArrayList<String> presentatoIn = new ArrayList<>();
        String getPresentatoInQuery = "SELECT DISTINCT e.strutturaospitante, e.datainizio FROM (b.articolo as a NATURAL JOIN b.conferenza as c) JOIN b.evento as e ON e.id_evento = c.evento ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getPresentatoInQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                presentatoIn.add(rs.getString("strutturaospitante") + " " + rs.getString("datainizio"));
            }
        }
        catch (SQLException e){
                e.printStackTrace();
            }
        return presentatoIn;
    }

    public ArrayList<String> getColumns(String nomeTable){
        ArrayList<String> columns = new ArrayList<>();
        String getColumnsQuery = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(getColumnsQuery);
            preparedStatement.setString(1, nomeTable);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                columns.add(rs.getString("COLUMN_NAME"));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return columns;
    }
}
