package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Properties;

public class Credenziali {
    HashMap<String,String> loginInfo = new HashMap<String,String>();
    Properties pro = new Properties();
    FileReader cred;

    {
        try {
            cred = new FileReader("src/coseUtili/Credenziali.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    Credenziali(){
        //while((line = reader.Re))
    }
}
