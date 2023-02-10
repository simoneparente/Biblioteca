package org.example;

import java.util.Random;

public class Articoli {
    public String generaTitolo() {
        Random random = new Random();
        String[] titolo1 = {"La scienza  ", "La politica ", "La scoperta ", "Il futuro", "L'innovazione ", "Studio ", "Ricerca "};
        String[] titolo2 = {"della vita", "dell'informatica", "della terra", "dell'universo", "della fisica", "della chimica", "della biologia", "della medicina"};
        String titolo = titolo1[random.nextInt(titolo1.length)] + titolo2[random.nextInt(titolo2.length)];
        return titolo;
    }

    public String generaDoi() {
        Random random = new Random();
        String doi1 = random.nextInt(8999) +1000 + "";
        String doi2 = random.nextInt(8999) +1000 + "";
        String doi3 = random.nextInt(8999) +1000 + "";
        String doi = "10." + doi1 + "/" + doi2 + "." + doi3;
        return doi;
    }

    public String generaDisciplina(){
        Random random = new Random();
        String[] disciplina = {"Fisica", "Chimica", "Biologia", "Medicina", "Informatica", "Matematica", "Economia", "Politica", "Filosofia", "Psicologia", "Sociologia", "Storia", "Letteratura"};
        String disciplinaScelta = disciplina[random.nextInt(disciplina.length)];
        return disciplinaScelta;
    }

    public String generaEditore(){
        Random random = new Random();
        String[] editore = {"Nature Publishing Group", "ScienceDirect", "Wiley", "Elsevier", "Oxford University Press", "Springer Nature", "BMJ", "Taylor & Francis", "Sage Publishing", "Cambridge University Press"};
        String editoreScelto = editore[random.nextInt(editore.length)];
        return editoreScelto;
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

    public String generaRivista(){
        Random random = new Random();
        String[] riviste = {"Nature", "Science", "The Lancet", "The New England Journal of Medicine", "Cell", "Proceedings of the National Academy of Sciences", "PLOS ONE", "JAMA", "Journal of Clinical Oncology", "BMC Medicine"};
        String rivista = riviste[random.nextInt(riviste.length)];
        return rivista;
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

    public String generaPrezzo(){
        double prezzo = Math.floor(Math.random() * (25 - 3 + 1) + 3);
        String prezzoA = String.valueOf(prezzo);
        return prezzoA;
    }

    public String generaNomeConferenza(String disciplina){
        return "Conferenza di " + disciplina;
    }

    public String generaIndirizzoConferenza(){
        Random random = new Random();
        String[] indirizzo = {"Via Roma", "Via Milano", "Via Venezia", "Via Firenze", "Via Torino", "Via Bologna", "Via Napoli", "Via Palermo", "Via Genova", "Via Bari"};
        String[] indirizzo2 = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

        String indirizzoA = indirizzo[random.nextInt(indirizzo.length)] + " " + indirizzo2[random.nextInt(indirizzo2.length)];

        return indirizzoA;
    }


    public String generaArticolo(){
        String insRivista = "INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('";
        String insConferenza = "INSERT INTO b.ins_ArticoliConferenza(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, luogoconferenza, dataconferenza, responsabileconferenza, prezzoconferenza) VALUES ('";
        Random random = new Random();
        Autore autore = new Autore();
        String doi = generaDoi();
        String titolo = generaTitolo();
        String autori = autore.generaAutore();
        String datapubblicazione = generaDataCasualeUltimi70Anni();
        String disciplina = generaDisciplina();
        String editore = generaEditore();
        String lingua = generaLingua();
        String formato = generaFormato();
        String articolo = titolo + "', " + "', " + doi + "', " + autori + "', " + datapubblicazione + "', " + disciplina + "', " + editore + "', " + lingua + "', " + formato + "', '";

        if(random.nextInt(2) == 0){
            
        }

    }

    public static void main(String[] args) {
       Articoli articolo = new Articoli();
       System.out.println(articolo.generaTitolo());
       System.out.println(articolo.generaDoi());
    }
}
