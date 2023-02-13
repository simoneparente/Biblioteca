package org.Bibliotech.DAO;
import org.Bibliotech.Model.Utente;

public interface UtenteDao {
    boolean checkUserExistenceInDB(String username);

    boolean checkLogin(String username, String password);
    boolean addUser(String username, String password);
    boolean changePassword(String username, String nuovaPassword);
    int getPermessi(String username);
}