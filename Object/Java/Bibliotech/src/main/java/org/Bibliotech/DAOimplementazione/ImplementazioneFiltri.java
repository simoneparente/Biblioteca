package org.Bibliotech.DAOimplementazione;

import org.Bibliotech.ConnessioneDB;
import org.Bibliotech.DAO.FiltriDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;


public class ImplementazioneFiltri implements FiltriDao {
    private Connection connection;

    public ImplementazioneFiltri() {
        try {
            connection = ConnessioneDB.getInstance().connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<String> getAutoriLibri() {
        ArrayList<String> autori = new ArrayList<>();
        String getAutoriQuery = "SELECT DISTINCT nome, cognome FROM b.view_libri_autore ORDER BY cognome, nome";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getAutoriQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                autori.add(rs.getString("nome") + " " + rs.getString("cognome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return autori;
    }

    @Override
    public ArrayList<String> getGenereLibri() {
        ArrayList<String> generi = new ArrayList<>();
        String getGenereQuery = "SELECT DISTINCT genere FROM b.libri";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getGenereQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                generi.add(rs.getString("genere"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generi;
    }

    @Override
    public ArrayList<String> getIssnSerie(String nomeSerie) {
        ArrayList<String> issnSerie = new ArrayList<>();
        String getIssnSerieQuery = "SELECT DISTINCT issn FROM b.serie WHERE nome = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getIssnSerieQuery);
            preparedStatement.setString(1, nomeSerie);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                issnSerie.add(rs.getString("issn"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return issnSerie;
    }

    @Override
    public ArrayList<String> getRiviste() {
        ArrayList<String> riviste = new ArrayList<>();
        String getRivisteQuery = "SELECT DISTINCT nome FROM b.riviste";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getRivisteQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                riviste.add(rs.getString("nome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return riviste;
    }

    @Override
    public ArrayList<String> getConferenze() {
        ArrayList<String> conferenze = new ArrayList<>();
        String getConferenzeQuery = "SELECT DISTINCT nome FROM b.conferenza NATURAL JOIN b.evento";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getConferenzeQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                conferenze.add(rs.getString("nome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conferenze;
    }

    @Override
    public ArrayList<String> getLinguaLibri() {
        ArrayList<String> lingue = new ArrayList<>();
        String getLinguaQuery = "SELECT DISTINCT lingua FROM b.libri";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getLinguaQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                lingue.add(rs.getString("lingua"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lingue;
    }

    @Override
    public ArrayList<String> getEditoreLibri() {
        ArrayList<String> editori = new ArrayList<>();
        String getEditoreQuery = "SELECT DISTINCT editore FROM b.libri";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getEditoreQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                editori.add(rs.getString("editore"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return editori;
    }

    @Override
    public ArrayList<String> getFormatoLibri() {
        ArrayList<String> formati = new ArrayList<>();
        String getFormatoQuery = "SELECT DISTINCT formato FROM b.libri";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getFormatoQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                formati.add(rs.getString("formato"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return formati;
    }

    @Override
    public ArrayList<String> getPresentatoInLibro() {
        ArrayList<String> presentatoIn = new ArrayList<>();
        String getPresentatoInQuery = "SELECT DISTINCT e.strutturaospitante, e.datainizio FROM (b.libri as l NATURAL JOIN b.presentazione as p) JOIN b.evento as e ON e.id_evento = p.id_evento ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getPresentatoInQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                presentatoIn.add(rs.getString("strutturaospitante") + " " + rs.getString("datainizio"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return presentatoIn;
    }

    @Override
    public ArrayList<String> getSerieLibri() {
        ArrayList<String> serie = new ArrayList<>();
        String getSerieQuery = "SELECT DISTINCT nome FROM b.serie";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getSerieQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                serie.add(rs.getString("nome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serie;
    }

    @Override
    public ArrayList<String> getAutoriArticoli() {
        ArrayList<String> autori = new ArrayList<>();
        String getAutoriQuery = "SELECT DISTINCT nome, cognome FROM b.view_articoli_autore";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getAutoriQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                autori.add(rs.getString("nome") + " " + rs.getString("cognome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return autori;
    }

    @Override
    public ArrayList<String> getDisciplinaArticoli() {
        ArrayList<String> discipline = new ArrayList<>();
        String getDisciplinaQuery = "SELECT DISTINCT disciplina FROM b.articoli";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getDisciplinaQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                discipline.add(rs.getString("disciplina"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return discipline;
    }

    @Override
    public ArrayList<String> getLinguaArticoli() {
        ArrayList<String> lingue = new ArrayList<>();
        String getLinguaQuery = "SELECT DISTINCT lingua FROM b.articoli";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getLinguaQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                lingue.add(rs.getString("lingua"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lingue;
    }

    @Override
    public ArrayList<String> getEditoreArticoli() {
        ArrayList<String> editori = new ArrayList<>();
        String getEditoreQuery = "SELECT DISTINCT editore FROM b.articoli";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getEditoreQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                editori.add(rs.getString("editore"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return editori;
    }

    @Override
    public ArrayList<String> getFormatoArticoli() {
        ArrayList<String> formati = new ArrayList<>();
        String getFormatoQuery = "SELECT DISTINCT formato FROM b.articoli";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getFormatoQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                formati.add(rs.getString("formato"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return formati;
    }

    @Override
    public ArrayList<String> getRivistaArticoli() {
        ArrayList<String> riviste = new ArrayList<>();
        String getRivistaQuery = "SELECT DISTINCT titolo_riviste FROM b.view_articoli_riviste";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getRivistaQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                riviste.add(rs.getString("titolo_riviste"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return riviste;
    }

    @Override
    public ArrayList<String> getConferenzeArticoli() {
        ArrayList<String> conferenze = new ArrayList<>();
        String getConferenzeQuery = "SELECT DISTINCT titolo_conferenza FROM b.view_articoli_conferenza";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getConferenzeQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                conferenze.add(rs.getString("titolo_conferenza"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conferenze;
    }

    @Override
    public ArrayList<String> getArgomentiRiviste() {
        ArrayList<String> argomenti = new ArrayList<>();
        String getArgomentiQuery = "SELECT DISTINCT argomento FROM b.riviste";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getArgomentiQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                argomenti.add(rs.getString("argomento"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return argomenti;
    }

    @Override
    public ArrayList<String> getLingueRiviste() {
        ArrayList<String> lingue = new ArrayList<>();
        String getLingueQuery = "SELECT DISTINCT lingua FROM b.view_articoli_riviste";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getLingueQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                lingue.add(rs.getString("lingua"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lingue;
    }

    @Override
    public ArrayList<String> getFormatiRiviste() {
        ArrayList<String> formati = new ArrayList<>();
        String getFormatiQuery = "SELECT DISTINCT formato FROM b.view_articoli_riviste";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getFormatiQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                formati.add(rs.getString("formato"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return formati;
    }

    @Override
    public ArrayList<String> getEditoriSerie() {
        ArrayList<String> editori = new ArrayList<>();
        String getEditoreQuery = "SELECT DISTINCT editore FROM b.view_libri_serie";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getEditoreQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                editori.add(rs.getString("editore"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return editori;
    }

    @Override
    public ArrayList<String> getLingueSerie() {
        ArrayList<String> lingue = new ArrayList<>();
        String getLingueQuery = "SELECT DISTINCT lingua FROM b.view_libri_serie";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getLingueQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                lingue.add(rs.getString("lingua"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lingue;
    }

    @Override
    public ArrayList<String> getFormatiSerie() {
        ArrayList<String> formati = new ArrayList<>();
        String getFormatiQuery = "SELECT DISTINCT formato FROM b.view_libri_serie";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getFormatiQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                formati.add(rs.getString("formato"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return formati;
    }


    public ArrayList<String> getColumns(String nomeTable) {
        ArrayList<String> columns = new ArrayList<>();
        String getColumnsQuery = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getColumnsQuery);
            preparedStatement.setString(1, nomeTable);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                columns.add(rs.getString("COLUMN_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columns;
    }

    public Vector<Vector<Object>> getRows(String query) {
        int i = 0, j;
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                Vector<Object> row = new Vector<Object>(rsmd.getColumnCount());
                for (i = 1; i <= rsmd.getColumnCount(); i++) {
                    row.add(rs.getObject(i));
                }
                data.add(row);
            }

            return data;
        } catch (SQLException e) {
            System.out.println("Errore " + e);
        }
        return null;
    }

    public ArrayList<String> getRivisteISSN(String nomerivista) {
        ArrayList<String> riviste = new ArrayList<>();
        String getRivistaQuery = "SELECT DISTINCT issn FROM b.riviste WHERE nome='" + nomerivista + "'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getRivistaQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                riviste.add(rs.getString("issn"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return riviste;
    }
}
