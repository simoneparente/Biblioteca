package org.Bibliotech.DAO;

public interface UtenteDao {
    public boolean checkLogin(String username, String password);
    public boolean addUser(String username, String password);
}
