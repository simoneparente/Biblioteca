package org.Bibliotech.DAOimplementazione;

import org.Bibliotech.ConnessioneDB;
import org.Bibliotech.DAO.FiltriDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;


public class ImplementaioneFiltri implements FiltriDao {
    private Connection connection;
    public ImplementaioneFiltri() {
        try {
            connection= ConnessioneDB.getInstance().connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public ArrayList<String> getNomeAutori() {
        ArrayList<String> nomiAutori = new ArrayList<>();

        return nomiAutori;
    }

    @Override
    public ArrayList<String> getCognomeAutori() {
        ArrayList<String> cognomiAutori = new ArrayList<>();
        return cognomiAutori
    }

    @Override
    public ArrayList<String> getGenere() {
        return null;
    }

    @Override
    public ArrayList<String> getLingua() {
        return null;
    }

    @Override
    public ArrayList<String> getEditore() {
        return null;
    }

    @Override
    public ArrayList<String> getFormato() {
        return null;
    }
}
