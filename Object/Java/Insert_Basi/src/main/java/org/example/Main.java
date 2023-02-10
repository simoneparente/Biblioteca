package org.example;

import java.io.File;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        File fileLibri = new File("src/main/java/Libri.txt");
        File fileArticoli = new File("src/main/java/Articoli.txt");
        PrintWriter writerLibri = null;
        PrintWriter writerArticoli = null;
        try {
            writerLibri = new PrintWriter(fileLibri);
            writerArticoli = new PrintWriter(fileArticoli);
        } catch (Exception e) {
            System.out.println("Errore");
        }
        for(int i = 0; i < 100; i++) {
            Libro libro = new Libro();
            writerLibri.println(libro.generaLibro());
        }
    }
}