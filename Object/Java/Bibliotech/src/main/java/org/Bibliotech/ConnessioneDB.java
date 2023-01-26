package org.Bibliotech;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnessioneDB{
    private static ConnessioneDB instance;
    public Connection connection = null;
    private String nome = "postgres";
    private String password = "1234";
    private String url = "jdbc:postgresql://localhost:5432/postgres"; // postgres è il nome del database
    private String driver = "org.postgresql.Driver";

    private ConnessioneDB() throws SQLException {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, nome, password);

        } catch (ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
            ex.printStackTrace();
        }

    }
    public static ConnessioneDB getInstance() throws SQLException {
        if (instance == null) {
            instance = new ConnessioneDB();
        } else if (instance.connection.isClosed()) {
            instance = new ConnessioneDB();
        }
        return instance;
    }
}