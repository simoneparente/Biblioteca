package org.Bibliotech.DAO;

public interface UtenteDao {
    boolean checkUserExistenceInDB(String username);

    boolean checkLogin(String username, String password);

    boolean addUser(String username, String password);

    boolean changePassword(String username, String nuovaPassword);

    int getPermessi(String username);

    boolean checkNotifiche(String username);

}