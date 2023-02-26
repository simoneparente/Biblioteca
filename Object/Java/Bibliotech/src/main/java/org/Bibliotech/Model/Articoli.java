package org.Bibliotech.Model;

import java.util.ArrayList;

public class Articoli {
    private ArrayList<Articolo> articoli;

// --Commented out by Inspection START (25/02/2023 22:50):
//    public Articoli() {
//        articoli = new ArrayList<Articolo>();
//    }
// --Commented out by Inspection STOP (25/02/2023 22:50)

    public void addArticolo(Articolo articolo) {
        articoli.add(articolo);
    }

// --Commented out by Inspection START (25/02/2023 22:51):
//    public void removeArticolo(Articolo articolo) {
//        articoli.remove(articolo);
//    }
// --Commented out by Inspection STOP (25/02/2023 22:51)

// --Commented out by Inspection START (25/02/2023 22:51):
//    public ArrayList<Articolo> getArticoli() {
//        return articoli;
//    }
// --Commented out by Inspection STOP (25/02/2023 22:51)

// --Commented out by Inspection START (25/02/2023 22:51):
//    public void setArticoli(ArrayList<Articolo> articoli) {
//        this.articoli = articoli;
//    }
// --Commented out by Inspection STOP (25/02/2023 22:51)

// --Commented out by Inspection START (25/02/2023 22:51):
//    public Articolo getArticoloByDoi(String isbn) {
//        for (Articolo articolo : articoli) {
//            if (articolo.getDoi().equals(isbn)) {
//                return articolo;
//            }
//        }
//        return null;
//    }
// --Commented out by Inspection STOP (25/02/2023 22:51)

// --Commented out by Inspection START (25/02/2023 22:51):
//    public Articoli getArticoliByTitolo(String titolo) {
//        Articoli articoliByTitolo = new Articoli();
//        for (Articolo articolo : articoli) {
//            if (articolo.getTitolo().toLowerCase().contains(titolo.toLowerCase())) {
//                articoliByTitolo.addArticolo(articolo);
//            }
//        }
//        return articoliByTitolo;
//    }
// --Commented out by Inspection STOP (25/02/2023 22:51)

// --Commented out by Inspection START (25/02/2023 22:51):
//    public Articoli getArticoliByAutore(String autore) {
//        Articoli articoliByAutore = new Articoli();
//        for (Articolo articolo : articoli) {
//            if (articolo.getAutoriString().toLowerCase().contains(autore.toLowerCase())) {
//                articoliByAutore.addArticolo(articolo);
//            }
//        }
//        return articoliByAutore;
//    }
// --Commented out by Inspection STOP (25/02/2023 22:51)

// --Commented out by Inspection START (25/02/2023 22:51):
//    public Articoli getArticoliByEditore(String editore) {
//        Articoli articoliByEditore = new Articoli();
//        for (Articolo articolo : articoli) {
//            if (articolo.getEditore().toLowerCase().contains(editore.toLowerCase())) {
//                articoliByEditore.addArticolo(articolo);
//            }
//        }
//        return articoliByEditore;
//    }
// --Commented out by Inspection STOP (25/02/2023 22:51)

// --Commented out by Inspection START (25/02/2023 22:51):
//    public Articoli getArticoliByDataRangePubblicazione(String dataPubblicazioneMin, String dataPubblicazioneMax) {
//        Articoli articoliByDataRangePubblicazione = new Articoli();
//        for (Articolo articolo : articoli) {
//            if (articolo.getDataPubblicazione().compareTo(dataPubblicazioneMin) >= 0 && articolo.getDataPubblicazione().compareTo(dataPubblicazioneMax) <= 0) {
//                articoliByDataRangePubblicazione.addArticolo(articolo);
//            }
//        }
//        return articoliByDataRangePubblicazione;
//    }
// --Commented out by Inspection STOP (25/02/2023 22:51)

// --Commented out by Inspection START (25/02/2023 22:51):
//    public Articoli getArticoliByDataPubblicazioneMin(String dataPubblicazioneMin) {
//        Articoli articoliByDataPubblicazioneMin = new Articoli();
//        for (Articolo articolo : articoli) {
//            if (articolo.getDataPubblicazione().compareTo(dataPubblicazioneMin) >= 0) {
//                articoliByDataPubblicazioneMin.addArticolo(articolo);
//            }
//        }
//        return articoliByDataPubblicazioneMin;
//    }
// --Commented out by Inspection STOP (25/02/2023 22:51)

// --Commented out by Inspection START (25/02/2023 22:51):
//    public Articoli getArticoliByDataPubblicazioneMax(String dataPubblicazioneMax) {
//        Articoli articoliByDataPubblicazioneMax = new Articoli();
//        for (Articolo articolo : articoli) {
//            if (articolo.getDataPubblicazione().compareTo(dataPubblicazioneMax) <= 0) {
//                articoliByDataPubblicazioneMax.addArticolo(articolo);
//            }
//        }
//        return articoliByDataPubblicazioneMax;
//    }
// --Commented out by Inspection STOP (25/02/2023 22:51)

// --Commented out by Inspection START (25/02/2023 22:51):
//    public Articoli getArticoliByLingua(String lingua) {
//        Articoli articoliByLingua = new Articoli();
//        for (Articolo articolo : articoli) {
//            if (articolo.getLingua().toLowerCase().contains(lingua.toLowerCase())) {
//                articoliByLingua.addArticolo(articolo);
//            }
//        }
//        return articoliByLingua;
//    }
// --Commented out by Inspection STOP (25/02/2023 22:51)

// --Commented out by Inspection START (25/02/2023 22:51):
//    public Articoli getArticoliByDisciplina(String disciplina) {
//        Articoli articoliByDisciplina = new Articoli();
//        for (Articolo articolo : articoli) {
//            if (articolo.getDisciplina().toLowerCase().contains(disciplina.toLowerCase())) {
//                articoliByDisciplina.addArticolo(articolo);
//            }
//        }
//        return articoliByDisciplina;
//    }
// --Commented out by Inspection STOP (25/02/2023 22:51)

// --Commented out by Inspection START (25/02/2023 22:51):
//    public Articoli getArticoliByFormato(String formato) {
//        Articoli articoliByFormato = new Articoli();
//        for (Articolo articolo : articoli) {
//            if (articolo.getFormato().toLowerCase().contains(formato.toLowerCase())) {
//                articoliByFormato.addArticolo(articolo);
//            }
//        }
//        return articoliByFormato;
//    }
// --Commented out by Inspection STOP (25/02/2023 22:51)
}
