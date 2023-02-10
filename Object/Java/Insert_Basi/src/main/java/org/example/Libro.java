package org.example;

import jdk.internal.foreign.SystemLookup;

import java.util.Random;

public class Libro {

    public String generaTitolo() {
        Random random = new Random();
        String[] titoli1 = {"La storia di ", "Il libro di ", "Il viaggio di", "L''avventura di", "Il ritorno di", "In viaggio con", "In viaggio col", "La morte di", "La ressurezione di"};
        String[] titoli2 = {"Mario", "Luigi", "Pippo", "Pluto", "Paperino", "Topolino", "Minnie", "Paperina", "Paperoga", "Davide", "Lorenzo", "Folle", "Pinguino", "Delfino", "Gatto", "Cane", "Papero", "Pappagllo"};
        String e = " e ";

        String titoloA = titoli1[random.nextInt(titoli1.length)] + " " + titoli2[random.nextInt(titoli2.length)];
        String titoloB = titoli1[random.nextInt(titoli1.length)] + " " + titoli2[random.nextInt(titoli2.length)] + e + titoli2[random.nextInt(titoli2.length)];

        if (random.nextInt(2) == 0) {
            return titoloA;
        } else {
            return titoloB;
        }
    }

    public String generaIsbn() {
        Random random = new Random();
        String[] isbn = {"978-88-17-", "978-88-18-", "978-88-19-", "978-88-20-", "978-88-21-", "978-88-22-", "978-88-23-", "978-88-24-", "978-88-25-", "978-88-26-", "978-88-27-", "978-88-28-", "978-88-29-", "978-88-30-", "978-88-31-", "978-88-32-", "978-88-33-", "978-88-34-", "978-88-35-", "978-88-36-", "978-88-37-", "978-88-38-", "978-88-39-", "978-88-40-", "978-88-41-", "978-88-42-", "978-88-43-", "978-88-44-", "978-88-45-", "978-88-46-", "978-88-47-", "978-88-48-", "978-88-49-", "978-88-50-", "978-88-51-", "978-88-52-", "978-88-53-", "978-88-54-", "978-88-55-", "978-88-56-", "978-88-57-", "978-88-58-", "978-88-59-", "978-88-60-", "978-88-61-", "978-88-62-", "978-88-63-", "978-88-64-", "978-88-65-", "978-88-66-", "978-88-67-", "978-88-68-", "978-88-69-", "978-88-70-", "978-88-71-", "978-88-72-", "978-88-73-", "978-88-74-", "978-88-75-", "978-88-76-", "978-88-77-", "978-88-78-", "978-88-79-", "978-88-80-", "978-88-81-", "978-88-82-", "978-88-83-", "978-88-84-"};
        String[] isbn2 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] isbn3 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] isbn4 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] isbn5 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] isbn6 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] isbn7 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] isbn8 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] isbn9 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] isbn10 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

        String isbnA = isbn[random.nextInt(isbn.length)] + isbn2[random.nextInt(isbn2.length)] + isbn3[random.nextInt(isbn3.length)] + isbn4[random.nextInt(isbn4.length)] + isbn5[random.nextInt(isbn5.length)] + isbn6[random.nextInt(isbn6.length)] + isbn7[random.nextInt(isbn7.length)] + isbn8[random.nextInt(isbn8.length)] + isbn9[random.nextInt(isbn9.length)] + isbn10[random.nextInt(isbn10.length)];
        return isbnA;
    }

    public String generaIssn(){
        Random random = new Random();
        String[] issn = {"977-", "978-", "979-", "980-", "981-", "982-", "983-", "984-", "985-", "986-", "987-", "988-", "989-", "990-", "991-", "992-", "993-", "994-", "995-", "996-", "997-", "998-", "999-"};
        String[] issn2 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] issn3 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] issn4 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] issn5 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] issn6 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] issn7 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] issn8 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] issn9 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] issn10 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String issnA = issn[random.nextInt(issn.length)] + issn2[random.nextInt(issn2.length)] + issn3[random.nextInt(issn3.length)] + issn4[random.nextInt(issn4.length)] + issn5[random.nextInt(issn5.length)] + issn6[random.nextInt(issn6.length)] + issn7[random.nextInt(issn7.length)] + issn8[random.nextInt(issn8.length)] + issn9[random.nextInt(issn9.length)] + issn10[random.nextInt(issn10.length)];
        return issnA;
    }

    public String generaDataCasualeUltimi70Anni() {
        Random random = new Random();
        int annoAttuale = 2023;
        int annoMinimo = annoAttuale - 70;
        int anno = random.nextInt(annoAttuale - annoMinimo + 1) + annoMinimo;
        int mese = random.nextInt(12) + 1;
        int giorno = random.nextInt(28) + 1;
        return anno + "-" + mese + "-" + giorno;
    }

    public String generaEditore(){
        Random random = new Random();
        String[] ediotre = {"Mondadori", "Feltrinelli", "Pearson", "Zanichelli", "Bompiani"};
        String editore = ediotre[random.nextInt(ediotre.length)];
        return editore;
    }

    public String generaGenere() {
        Random random = new Random();
        String[] generi = {"Romanzo", "Didattico"};
        String genere = generi[random.nextInt(generi.length)];
        return genere;
    }

    public String generaLingua() {
        Random random = new Random();
        String[] lingue = {"Italiano", "Inglese", "Francese", "Spagnolo", "Tedesco"};
        String lingua = lingue[random.nextInt(lingue.length)];
        return lingua;
    }

    public String generaFormato() {
        Random random = new Random();
        String[] formati = {"Ebook", "Cartaceo", "Audio"};
        String formato = formati[random.nextInt(formati.length)];
        return formato;
    }

    public String generaPrezzo(){
        double prezzo = Math.floor(Math.random() * (25 - 3 + 1) + 3);
        String prezzoA = String.valueOf(prezzo);
        return prezzoA;
    }

    public String generaSerieDiAppartenenza(String genere){
        Random random = new Random();
        String serieDiAppartenenza;
        if(genere.equals("Romanzo")){
            String[] serie = {"Biblioteca delfi ", "Storie più belle", "Gialli", "ibri belli"};
            serieDiAppartenenza = serie[random.nextInt(serie.length)];
        } else {
            String[] serie = {"edizione giallo", "edizione blu", "edizione verde", "edizione rossa", "editione nera"};
            serieDiAppartenenza = serie[random.nextInt(serie.length)];
        }
        return serieDiAppartenenza;
    }

    public String generaAutori(){
        Random random = new Random();
        Autore autore = new Autore();
        String autori = "";
        int nautori = random.nextInt(5) + 1;
        for (int i = 0; i < nautori; i++) {
            if(i == nautori - 1){
                autori = autore.generaAutore();
            } else {
                autori = " " + autore.generaAutore();
            }
        }
        return autori;
    }

    public String generaLibro(){
        Random random = new Random();
        String isbn = generaIsbn();
        String titolo = generaTitolo();
        String editore = generaEditore();
        String genere = generaGenere();
        String lingua = generaLingua();
        String formato = generaFormato();
        String autori = generaAutori();
        String dataPubblicazione = generaDataCasualeUltimi70Anni();
        String prezzo = generaPrezzo();
        String serieDiAppartenenza = null;
        String issnSerieDiAppartenenza;
        String insert1 = "INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo, nome_serie_di_appartenenza, issn_serie_di_appartenenza) VALUES ('";
        String insert2 = "INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('";
        String libro = null;

        if(random.nextInt(10) == 1){
            serieDiAppartenenza = generaSerieDiAppartenenza(genere);
            issnSerieDiAppartenenza = generaIssn();
            libro = insert1 + titolo + "', '" + isbn + "', '" + autori + "', '" + dataPubblicazione + "', '" + editore + "', '" + genere + "', '" + lingua + "', '" + formato + "', '" + prezzo + "', '" + serieDiAppartenenza + "', '" + issnSerieDiAppartenenza + "');";
        } else {
            libro = insert2 + titolo + "', '" + isbn + "', '" + autori + "', '" + dataPubblicazione + "', '" + editore + "', '" + genere + "', '" + lingua + "', '" + formato + "', '" + prezzo + "');";
        }
        System.out.println(libro);
        return libro;
    }

    public static void main(String[] args) {
        Libro libro = new Libro();
        libro.generaLibro();
    }
}