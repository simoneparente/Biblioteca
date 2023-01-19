package org.example;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception {
        UtenteDAO utenteDao = new UtenteDAO();
        System.out.println("***PROVA***");
        new LoginPage(utenteDao.getAllUtenti());
        //System.out.println("utente cercato:"+utenteDao.getUtenteByUsername ("Mario").getUsername()+" pw dell'utente: "+ utenteDao.getUtenteByUsername ("Mario").getPassword());
      // utenteDao.registerUtente("Giggigno","giggilamail@gmail.com","123321");

    }
}