package org.Bibliotech.DAOimplementazione;

import org.Bibliotech.ConnessioneDB;
import org.Bibliotech.DAO.UtenteDao;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImplementazioneUtente implements UtenteDao {
    private Connection connection;

    public ImplementazioneUtente() {
        try {
            connection = ConnessioneDB.getInstance().connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkUserExistInDatabase(JTextField usernameField) {
        String checkUserExistInDatabase = "SELECT * FROM b.utente WHERE username= ?";
        try {
            PreparedStatement checkUserExistInDatabaseQuery = connection.prepareStatement(checkUserExistInDatabase);
            checkUserExistInDatabaseQuery.setString(1, usernameField.getText());
            ResultSet rs = checkUserExistInDatabaseQuery.executeQuery();
            return rs.next(); //se esiste un utente con quel nome ritorna true
        } catch (SQLException e) {
            e.printStackTrace();
            usernameField.setBorder(BorderFactory.createLineBorder(Color.red, 2));
            return false;
        }
    }

    @Override
    public boolean checkLogin(String username, String password) {
        String checkLoginQuery = "SELECT * FROM b.Utente WHERE Username = ? AND Password = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkLoginQuery);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addUser(String username, String password) {
        String addUserQuery = "INSERT INTO b.Utente (Username, Password) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(addUserQuery);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean changePassword(String username, String nuovaPassword) {
        String changePasswordQuery = "UPDATE b.Utente SET Password = ? WHERE Username = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(changePasswordQuery);
            preparedStatement.setString(1, nuovaPassword);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getPermessi(String username) {
        String getPermessiQuery = "SELECT permessi FROM b.Utente WHERE username = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getPermessiQuery);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt("permessi");
            } else {
                System.out.println("Errore: non è stato possibile ottenere i permessi dell'utente");
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean checkNotifiche(String username) {
        String checkRichiesteQuery = "SELECT * FROM b.notifiche WHERE username= ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkRichiesteQuery);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean inviaRichiestaSerie(String username, String issn) {
        String query = "INSERT INTO b.richiesta(id_utente, id_serie) " +
                "VALUES ((SELECT id_utente FROM b.utente WHERE username= ?), " +
                "(SELECT id_serie FROM b.serie WHERE issn= ?))";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, issn);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
}
