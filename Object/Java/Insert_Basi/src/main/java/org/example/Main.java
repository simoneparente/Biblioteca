package org.example;

import java.io.File;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        File fileLibri = new File("/Users/bickpenna/Documents/GitHub/Biblioteca/Basi/SQL/Libri.sql");
        File fileArticoli = new File("/Users/bickpenna/Documents/GitHub/Biblioteca/Basi/SQL/Articoli.sql");
        PrintWriter writerLibri = null;
        PrintWriter writerArticoli = null;
        try {
            writerLibri = new PrintWriter(fileLibri);
            writerArticoli = new PrintWriter(fileArticoli);
        } catch (Exception e) {
            System.out.println("Errore");
        }
        int i;
        for (i = 0; i < 150; i++) {
            Libro libro = new Libro();
            Articoli articolo = new Articoli();
            writerLibri.println(libro.generaLibro());
            writerArticoli.println(articolo.generaArticolo());
        }

        System.out.println("i =" + i);
    }
}