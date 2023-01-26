package org.Bibliotech.DAOimplementazione;

import org.Bibliotech.ConnessioneDB;
import org.Bibliotech.DAO.UtenteDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImplementazioneUtente implements UtenteDao{
    private Connection connection;

    public ImplementazioneUtente() {
        try {
            connection=ConnessioneDB.getInstance().connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkLogin(String username, String password) {
        boolean esito = false;
        String checkLoginQuery = "SELECT * FROM b.Utente WHERE Username = ? AND Password = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(checkLoginQuery);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                esito = true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return esito;
    }
    @Override
    public boolean addUser(String username, String password) {
        boolean esito = false;
        String addUserQuery = "INSERT INTO b.Utente (Username, Password) VALUES (?, ?)";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(addUserQuery);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
            esito = true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return esito;
    }


}
