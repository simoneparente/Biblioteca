package org.Bibliotech.DAOimplementazione;

import org.Bibliotech.ConnessioneDB;
import org.Bibliotech.DAO.risorsaDao;
import org.Bibliotech.Model.Articolo;
import org.Bibliotech.Model.Conferenza;
import org.Bibliotech.Model.Libro;
import org.Bibliotech.Model.Rivista;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;


public class ImplementazioneRisorsa implements risorsaDao {
    private Connection connection;

    public ImplementazioneRisorsa() {
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
        int i;
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

    public boolean addLibro(Libro libro) {
        String addLibroQuery = "INSERT INTO b.ins_Libri (titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo, nome_serie_di_appartenenza, issn_serie_di_appartenenza) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(addLibroQuery);
            preparedStatement.setString(1, libro.getTitolo());
            preparedStatement.setString(2, libro.getIsbn());
            preparedStatement.setString(3, libro.getAutoriString());
            preparedStatement.setDate(4, libro.getDataPubblicazione());
            preparedStatement.setString(5, libro.getEditore());
            preparedStatement.setString(6, libro.getGenere());
            preparedStatement.setString(7, libro.getLingua());
            preparedStatement.setString(8, libro.getFormato());
            if (libro.getPrezzo().equals("")) {
                preparedStatement.setNull(9, Types.DOUBLE);
            } else {
                preparedStatement.setDouble(9, libro.getPrezzo());
            }

            //se il libro non appartiene a una serie (il checkobox è disabilitato) allora il nome e l'issn della serie sono null
            if (libro.getSerieDiAppartenenza() == null) {
                preparedStatement.setNull(10, Types.VARCHAR);
                preparedStatement.setNull(11, Types.VARCHAR);
            } else {
                preparedStatement.setString(10, libro.getSerieDiAppartenenza());
                preparedStatement.setString(11, libro.getISSNSerieDiAppartenenza());
            }
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addArticoloRivista(Articolo articolo, String nomeRivista, String issn) {
        String query = "INSERT INTO b.ins_ArticoliRivista(titolo, autorinome_cognome, datapubblicazione, editore, disciplina, formato, doi, lingua, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, articolo.getTitolo());
            preparedStatement.setString(2, articolo.getAutori());
            preparedStatement.setDate(3, getDateByISSN(issn));
            preparedStatement.setString(4, articolo.getEditore());
            preparedStatement.setString(5, articolo.getDisciplina());
            preparedStatement.setString(6, articolo.getFormato());
            preparedStatement.setString(7, articolo.getDoi());
            preparedStatement.setString(8, articolo.getLingua());
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


    @Override
    public boolean addArticoloAddRivistaInDB(Articolo articolo, Rivista rivista) {
        String query = "INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, " + "editore, lingua, formato, nomerivista, " + "issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES" + "(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, articolo.getTitolo());
            preparedStatement.setString(2, articolo.getDoi());
            preparedStatement.setString(3, articolo.getAutori());
            preparedStatement.setDate(4, rivista.getDataPubblicazione());
            preparedStatement.setString(5, articolo.getDisciplina());
            preparedStatement.setString(6, articolo.getEditore());
            preparedStatement.setString(7, articolo.getLingua());
            preparedStatement.setString(8, articolo.getFormato());
            preparedStatement.setString(9, rivista.getNome());
            preparedStatement.setString(10, rivista.getIssn());
            preparedStatement.setString(11, rivista.getArgomento());
            preparedStatement.setString(12, rivista.getResponsabile());
            preparedStatement.setDouble(13, rivista.getPrezzo());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<String> getConferenzaDataInizio(String nomeConferenza) {
        ArrayList<String> conferenze = new ArrayList<>();
        String query = "SELECT DISTINCT datainizio FROM b.conferenza c JOIN b.evento e ON e.id_evento=c.id_evento  WHERE e.nome=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nomeConferenza);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                conferenze.add(rs.getString(1));
            }
            return conferenze;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addArticoloConferenza(Articolo articolo, String nomeConferenza, String dataInizioConferenza) {
        String responsabileConferenza = getResponsabileConferenza(nomeConferenza, dataInizioConferenza);
        String strutturaOspitanteConferenza = getStrutturaOspitanteConferenza(nomeConferenza, dataInizioConferenza);
        String indirizzoConferenza = getIndirizzoConferenza(nomeConferenza, dataInizioConferenza);
        String dataFineConferenza = getDataFineConferenza(nomeConferenza, dataInizioConferenza);
        String query = "INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, " + "disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, " + "datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, articolo.getTitolo());
            preparedStatement.setString(2, articolo.getDoi());
            preparedStatement.setString(3, articolo.getAutori());
            preparedStatement.setDate(4, Date.valueOf(dataInizioConferenza));
            preparedStatement.setString(5, articolo.getDisciplina());
            preparedStatement.setString(6, articolo.getEditore());
            preparedStatement.setString(7, articolo.getLingua());
            preparedStatement.setString(8, articolo.getFormato());
            preparedStatement.setString(9, nomeConferenza);
            preparedStatement.setString(10, indirizzoConferenza);
            preparedStatement.setDate(11, Date.valueOf(dataInizioConferenza));
            preparedStatement.setDate(12, Date.valueOf(dataFineConferenza));
            preparedStatement.setString(13, strutturaOspitanteConferenza);
            preparedStatement.setString(14, responsabileConferenza);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getDataFineConferenza(String nomeConferenza, String dataInizioConferenza) {
        String query = "SELECT datafine FROM  b.evento e WHERE e.nome=? AND e.datainizio=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nomeConferenza);
            preparedStatement.setDate(2, Date.valueOf(dataInizioConferenza));
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getIndirizzoConferenza(String nomeConferenza, String dataInizioConferenza) {
        String query = "SELECT indirizzo FROM  b.evento e WHERE e.nome=? AND e.datainizio=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nomeConferenza);
            preparedStatement.setDate(2, Date.valueOf(dataInizioConferenza));
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getStrutturaOspitanteConferenza(String nomeConferenza, String dataInizioConferenza) {
        String query = "SELECT strutturaospitante FROM  b.evento e WHERE e.nome=? AND e.datainizio=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nomeConferenza);
            preparedStatement.setDate(2, Date.valueOf(dataInizioConferenza));
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getResponsabileConferenza(String nomeConferenza, String dataInizioConferenza) {
        String query = "SELECT responsabile FROM  b.evento e WHERE e.nome=? AND e.datainizio=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nomeConferenza);
            preparedStatement.setDate(2, Date.valueOf(dataInizioConferenza));
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean addArticoloAddConferenza(Articolo articolo, Conferenza conferenza) {
        String query = "INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, " + "disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, " + "datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, articolo.getTitolo());
            preparedStatement.setString(2, articolo.getDoi());
            preparedStatement.setString(3, articolo.getAutori());
            preparedStatement.setDate(4, conferenza.getDataInizio());
            preparedStatement.setString(5, articolo.getDisciplina());
            preparedStatement.setString(6, articolo.getEditore());
            preparedStatement.setString(7, articolo.getLingua());
            preparedStatement.setString(8, articolo.getFormato());
            preparedStatement.setString(9, conferenza.getNome());
            preparedStatement.setString(10, conferenza.getIndirizzo());
            preparedStatement.setDate(11, conferenza.getDataInizio());
            preparedStatement.setDate(12, conferenza.getDataFine());
            preparedStatement.setString(13, conferenza.getStrutturaOspitante());
            preparedStatement.setString(14, conferenza.getResponsabile());
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

}

