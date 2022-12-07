package org.example;

import java.sql.*;
import java.util.*;


//import static jdk.internal.net.http.common.Utils.close;

public class UtenteDAO {
    private Connection conn;
    //costruttore
    public UtenteDAO() throws Exception {
        //proprietà db
        String dbuser = "postgres";
        String dbpassword = "1234";
        String dburl = "jdbc:postgresql://localhost:5432/postgres";
        //connessione db
        conn = DriverManager.getConnection(dburl, dbuser, dbpassword);
        System.out.println("Connesso al DB con successo!");
    }

    //metodo che restituiscce tutti gli utenti e li mette in una lista
    public ArrayList<Utente> getAllUtenti() throws Exception {
        ArrayList<Utente> utenti = new ArrayList<>();

        Statement stmt = null;
        ResultSet rs = null;

        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM p.utente");

            while(rs.next()){
                Utente tempUtente;
                tempUtente = convertRowToUtente(rs);
                utenti.add(tempUtente);
                System.out.println("username: "+tempUtente.getUsername()+" email: "+tempUtente.getEmail()+" password: "+tempUtente.getPassword());
            }
            return utenti;
        }finally{
            if (stmt != null) {
                stmt.close();
               // conn.close();
            }
            if (rs != null) {
                rs.close();
               // conn.close();
            }
        }
    }

    //metodo che l'uetnte che ha un determinato username
    public Utente getUtenteByUsername (String username) throws Exception{
        Utente utente = new Utente();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            ps = conn.prepareStatement("SELECT * FROM  p.utente WHERE username = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();
            if(rs == null) {
                System.out.println("Non esiste un utente con username: " + username);
            }
            else {
                while (rs.next()) {
                    utente = convertRowToUtente(rs);
                }
            }
        }finally{
            if (ps != null) {
                ps.close();
               // conn.close();
            }
            if (rs != null) {
                rs.close();
                //conn.close();
            }
        }
        return utente;
    }
        public void registerUtente(String username,String email, String password) throws Exception{
        Statement stmt = null;
        try{
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO p.Utente (username, email, password)" +
                    "( VALUES ('"+username+"', '"+email+"', '"+password+"') )");
        }catch(Exception e){
            System.out.println("Errore:"+e);
        }finally {
            if (stmt != null) {
                stmt.close();
                // conn.close();
            }
        }
    }

    public String getPasswordByUsername(String username) throws Exception{
        Utente utente = getUtenteByUsername(username);
        return utente.getPassword();
    }
    private Utente convertRowToUtente(ResultSet rs) throws Exception{
        Utente utente = new Utente();
        utente.setUsername(rs.getString("username"));
        utente.setPassword(rs.getString("password"));
        utente.setEmail(rs.getString("email"));
        return utente;
    }
}
