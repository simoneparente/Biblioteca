package org.example;

public class Autore {
    public String generaAutore() {
        String[] nome = {"Mario", "Jhon", "Davide", "Luca", "Marco", "Giovanni", "Giuseppe", "Francesco", "Alessandro", "Alessio", "Andrea", "Angelo", "Antonio", "Carlo", "Daniele", "Dario", "Davide", "Diego", "Edoardo", "Emanuele", "Enrico", "Federico", "Filippo", "Francesco", "Gabriele", "Giacomo", "Giuseppe", "Giuseppe", "Giovanni", "Giuliano", "Giulio", "Lorenzo", "Luca", "Luigi", "Marco", "Massimo", "Matteo", "Mattia", "Mauro", "Mirko", "Nicola", "Paolo", "Pierluigi", "Pierpaolo", "Raffaele", "Riccardo", "Roberto", "Salvatore", "Simone", "Stefano", "Silvio", "Umberto", "Valerio", "Vincenzo"};
        String[] cognome = {"Penna", "Santi", "Parente", "Tecchia", "Esposito", "Barra", "Tramontana", "Pezzella", "Ciambriello", "Abate", "DeGregorio","DeLuca", "DeSantis", "DeSimone", "DeVito", "DiBenedetto", "DiCarlo", "DiGiacomo", "DiLorenzo", "DiMarco", "DiMauro", "DiNapoli", "DiPaolo", "DiPietro", "DiRienzo", "DiSalvo"};
        String nomeAutore = nome[(int) (Math.random() * nome.length)] + "_" + cognome[(int) (Math.random() * cognome.length)];
        return nomeAutore;
    }
}
