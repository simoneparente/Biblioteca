package org.Bibliotech.Model;

import java.util.ArrayList;
public class Articoli {
    private ArrayList<Articolo> articoli;
    public Articoli() {
        articoli = new ArrayList<Articolo>();
    }

    public void addArticolo(Articolo articolo) {
        articoli.add(articolo);
    }

    public void removeArticolo(Articolo articolo) {
        articoli.remove(articolo);
    }

    public ArrayList<Articolo> getArticoli() {
        return articoli;
    }

    public void setArticoli(ArrayList<Articolo> articoli) {
        this.articoli = articoli;
    }

    public Articolo getArticoloByDoi(String isbn) {
        for (Articolo articolo : articoli) {
            if (articolo.getDoi().equals(isbn)) {
                return articolo;
            }
        }
        return null;
    }

    public Articoli getArticoliByTitolo(String titolo) {
        Articoli articoliByTitolo = new Articoli();
        for (Articolo articolo : articoli) {
            if (articolo.getTitolo().toLowerCase().contains(titolo.toLowerCase())) {
                articoliByTitolo.addArticolo(articolo);
            }
        }
        return articoliByTitolo;
    }

    public Articoli getArticoliByAutore(String autore) {
        Articoli articoliByAutore = new Articoli();
        for (Articolo articolo : articoli) {
            if (articolo.getAutoriString().toLowerCase().contains(autore.toLowerCase())) {
                articoliByAutore.addArticolo(articolo);
            }
        }
        return articoliByAutore;
    }

    public Articoli getArticoliByEditore(String editore) {
        Articoli articoliByEditore = new Articoli();
        for (Articolo articolo : articoli) {
            if (articolo.getEditore().toLowerCase().contains(editore.toLowerCase())) {
                articoliByEditore.addArticolo(articolo);
            }
        }
        return articoliByEditore;
    }

    public Articoli getArticoliByDataRangePubblicazione(String dataPubblicazioneMin, String dataPubblicazioneMax) {
        Articoli articoliByDataRangePubblicazione = new Articoli();
        for (Articolo articolo : articoli) {
            if (articolo.getDataPubblicazione().compareTo(dataPubblicazioneMin) >= 0 && articolo.getDataPubblicazione().compareTo(dataPubblicazioneMax) <= 0) {
                articoliByDataRangePubblicazione.addArticolo(articolo);
            }
        }
        return articoliByDataRangePubblicazione;
    }

    public Articoli getArticoliByDataPubblicazioneMin(String dataPubblicazioneMin) {
        Articoli articoliByDataPubblicazioneMin = new Articoli();
        for (Articolo articolo : articoli) {
            if (articolo.getDataPubblicazione().compareTo(dataPubblicazioneMin) >= 0) {
                articoliByDataPubblicazioneMin.addArticolo(articolo);
            }
        }
        return articoliByDataPubblicazioneMin;
    }

    public Articoli getArticoliByDataPubblicazioneMax(String dataPubblicazioneMax) {
        Articoli articoliByDataPubblicazioneMax = new Articoli();
        for (Articolo articolo : articoli) {
            if (articolo.getDataPubblicazione().compareTo(dataPubblicazioneMax) <= 0) {
                articoliByDataPubblicazioneMax.addArticolo(articolo);
            }
        }
        return articoliByDataPubblicazioneMax;
    }

    public Articoli getArticoliByLingua(String lingua) {
        Articoli articoliByLingua = new Articoli();
        for (Articolo articolo : articoli) {
            if (articolo.getLingua().toLowerCase().contains(lingua.toLowerCase())) {
                articoliByLingua.addArticolo(articolo);
            }
        }
        return articoliByLingua;
    }
}
