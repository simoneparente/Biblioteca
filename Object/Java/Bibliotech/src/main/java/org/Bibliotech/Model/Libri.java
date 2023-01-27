package org.Bibliotech.Model;

import java.util.ArrayList;

public class Libri {
    private ArrayList<Libro> libri;

    public Libri() {
        libri = new ArrayList<Libro>();
    }

    public void addLibro(Libro libro) {
        libri.add(libro);
    }

    public void removeLibro(Libro libro) {
        libri.remove(libro);
    }

    public ArrayList<Libro> getLibri() {
        return libri;
    }

    public void setLibri(ArrayList<Libro> libri) {
        this.libri = libri;
    }

    public Libro getLibroByIsbn(String isbn) {
        for (Libro libro : libri) {
            if (libro.getIsbn().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }

    public Libri getLibriByTitolo(String titolo) {
        Libri libriByTitolo = new Libri();
        for (Libro libro : libri) {
            if (libro.getTitolo().toLowerCase().contains(titolo.toLowerCase())) {
                libriByTitolo.addLibro(libro);
            }
        }
        return libriByTitolo;
    }

    public Libri getLibriByAutore(String autore) {
        Libri libriByAutore = new Libri();
        for (Libro libro : libri) {
            if (libro.getAutoriString().toLowerCase().contains(autore.toLowerCase())) {
                libriByAutore.addLibro(libro);
            }
        }
        return libriByAutore;
    }

    public Libri getLibriByGenere(String genere) {
        Libri libriByGenere = new Libri();
        for (Libro libro : libri) {
            if (libro.getGenere().toLowerCase().contains(genere.toLowerCase())) {
                libriByGenere.addLibro(libro);
            }
        }
        return libriByGenere;
    }

    public Libri getLibriByEditore(String editore) {
        Libri libriByEditore = new Libri();
        for (Libro libro : libri) {
            if (libro.getEditore().toLowerCase().contains(editore.toLowerCase())) {
                libriByEditore.addLibro(libro);
            }
        }
        return libriByEditore;
    }

    public Libri getLibriByDataRangePubblicazione(String dataPubblicazioneMin, String dataPubblicazioneMax) {
        Libri libriByDataPubblicazione = new Libri();
        for (Libro libro : libri) {
            if (libro.getDataPubblicazione().compareTo(dataPubblicazioneMin) >= 0 && libro.getDataPubblicazione().compareTo(dataPubblicazioneMax) <= 0) {
                libriByDataPubblicazione.addLibro(libro);
            }
        }
        return libriByDataPubblicazione;
    }

    public Libri getLibriByDataPubblicazioneMin(String dataPubblicazioneMin) {
        Libri libriByDataPubblicazioneMin = new Libri();
        for (Libro libro : libri) {
            if (libro.getDataPubblicazione().compareTo(dataPubblicazioneMin) >= 0) {
                libriByDataPubblicazioneMin.addLibro(libro);
            }
        }
        return libriByDataPubblicazioneMin;
    }

    public Libri getLibriByDataPubblicazioneMax(String dataPubblicazioneMax) {
        Libri libriByDataPubblicazioneMax = new Libri();
        for (Libro libro : libri) {
            if (libro.getDataPubblicazione().compareTo(dataPubblicazioneMax) <= 0) {
                libriByDataPubblicazioneMax.addLibro(libro);
            }
        }
        return libriByDataPubblicazioneMax;
    }

    public Libri getLibriByFormato(String formato) {
        Libri libriByFormato = new Libri();
        for (Libro libro : libri) {
            if (libro.getFormato().toLowerCase().contains(formato.toLowerCase())) {
                libriByFormato.addLibro(libro);
            }
        }
        return libriByFormato;
    }

    public Libri getLibriByRangePrezzo(double min, double max) {
        Libri libriByPrezzo = new Libri();
        for (Libro libro : libri) {
            if (libro.getPrezzo() >= min && libro.getPrezzo() <= max) {
                libriByPrezzo.addLibro(libro);
            }
        }
        return libriByPrezzo;
    }

    public Libri getLibriByPrezzoMin(double min) {
        Libri libriByPrezzoMin = new Libri();
        for (Libro libro : libri) {
            if (libro.getPrezzo() >= min) {
                libriByPrezzoMin.addLibro(libro);
            }
        }
        return libriByPrezzoMin;
    }

    public Libri getLibriByPrezzoMax(double max) {
        Libri libriByPrezzoMax = new Libri();
        for (Libro libro : libri) {
            if (libro.getPrezzo() <= max) {
                libriByPrezzoMax.addLibro(libro);
            }
        }
        return libriByPrezzoMax;
    }






}
