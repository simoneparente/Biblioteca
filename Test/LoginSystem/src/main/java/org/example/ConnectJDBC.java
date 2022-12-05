package org.example;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectJDBC    {
    public static void main() {
        Connection connection=null;

        try{
            Class.forName("org.postgresql.Driver");
            connection= DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","1234");

            if(connection!=null){
                System.out.println("Connection OK");
            }
            else{
                System.out.println("Connection Failed");
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }


    }
}
