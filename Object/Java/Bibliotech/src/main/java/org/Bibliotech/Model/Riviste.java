package org.Bibliotech.Model;

import java.util.ArrayList;

public class Riviste {
    private ArrayList<Rivista> riviste;

    public Riviste() {
        riviste = new ArrayList<Rivista>();
    }

    public void addRivista(Rivista rivista) {
        riviste.add(rivista);
    }

    public void removeRivista(Rivista rivista) {
        riviste.remove(rivista);
    }

    public ArrayList<Rivista> getRiviste() {
        return riviste;
    }

    public void setRiviste(ArrayList<Rivista> riviste) {
        this.riviste = riviste;
    }

    public Rivista getRivistaByIssn(String issn) {
        for (Rivista rivista : riviste) {
            if (rivista.getIssn().equals(issn)) {
                return rivista;
            }
        }
        return null;
    }

    public Riviste getRivisteByNome(String titolo) {
        Riviste rivisteByNome = new Riviste();
        for (Rivista rivista : riviste) {
            if (rivista.getNome().toLowerCase().contains(titolo.toLowerCase())) {
                rivisteByNome.addRivista(rivista);
            }
        }
        return rivisteByNome;
    }

    public Riviste getRivisteByArgomento(String argomento) {
        Riviste rivisteByArgomento = new Riviste();
        for (Rivista rivista : riviste) {
            if (rivista.getArgomento().toLowerCase().contains(argomento.toLowerCase())) {
                rivisteByArgomento.addRivista(rivista);
            }
        }
        return rivisteByArgomento;
    }

    public Riviste getRivisteByRangeDataPubblicazione(String dataPubblicazione) {
        Riviste rivisteByRangeDataPubblicazione = new Riviste();
        for (Rivista rivista : riviste) {
            if (rivista.getDataPubblicazione().toLowerCase().contains(dataPubblicazione.toLowerCase())) {
                rivisteByRangeDataPubblicazione.addRivista(rivista);
            }
        }
        return rivisteByRangeDataPubblicazione;
    }

    public Riviste getRivisteByDataPubblicazioneMin(String dataPubblicazione) {
        Riviste rivisteByDataPubblicazioneMin = new Riviste();
        for (Rivista rivista : riviste) {
            if (rivista.getDataPubblicazione().toLowerCase().contains(dataPubblicazione.toLowerCase())) {
                rivisteByDataPubblicazioneMin.addRivista(rivista);
            }
        }
        return rivisteByDataPubblicazioneMin;
    }

    public Riviste getRivisteByDataPubblicazioneMax(String dataPubblicazione) {
        Riviste rivisteByDataPubblicazioneMax = new Riviste();
        for (Rivista rivista : riviste) {
            if (rivista.getDataPubblicazione().toLowerCase().contains(dataPubblicazione.toLowerCase())) {
                rivisteByDataPubblicazioneMax.addRivista(rivista);
            }
        }
        return rivisteByDataPubblicazioneMax;
    }

    public Riviste getRivisteByLingua(String lingua) {
        Riviste rivisteByLingua = new Riviste();
        for (Rivista rivista : riviste) {
            if (rivista.getLingua().toLowerCase().contains(lingua.toLowerCase())) {
                rivisteByLingua.addRivista(rivista);
            }
        }
        return rivisteByLingua;
    }

    public Riviste getRivisteByFormato(String formato) {
        Riviste rivisteByFormato = new Riviste();
        for (Rivista rivista : riviste) {
            if (rivista.getFormato().toLowerCase().contains(formato.toLowerCase())) {
                rivisteByFormato.addRivista(rivista);
            }
        }
        return rivisteByFormato;
    }

    public Riviste getRivisteByPrezzo(String prezzo) {
        Riviste rivisteByPrezzo = new Riviste();
        for (Rivista rivista : riviste) {
            if (rivista.getPrezzo().toLowerCase().contains(prezzo.toLowerCase())) {
                rivisteByPrezzo.addRivista(rivista);
            }
        }
        return rivisteByPrezzo;
    }

    public Riviste getRivisteByPrezzoMin(String prezzo) {
        Riviste rivisteByPrezzoMin = new Riviste();
        for (Rivista rivista : riviste) {
            if (rivista.getPrezzo().toLowerCase().contains(prezzo.toLowerCase())) {
                rivisteByPrezzoMin.addRivista(rivista);
            }
        }
        return rivisteByPrezzoMin;
    }

    public Riviste getRivisteByPrezzoMax(String prezzo) {
        Riviste rivisteByPrezzoMax = new Riviste();
        for (Rivista rivista : riviste) {
            if (rivista.getPrezzo().toLowerCase().contains(prezzo.toLowerCase())) {
                rivisteByPrezzoMax.addRivista(rivista);
            }
        }
        return rivisteByPrezzoMax;
    }

    public Riviste getRivisteByDoi(String doi) {
        Riviste rivisteByDoi = new Riviste();
        for (Rivista rivista : riviste) {
            if (rivista.getDoi().toLowerCase().contains(doi.toLowerCase())) {
                rivisteByDoi.addRivista(rivista);
            }
        }
        return rivisteByDoi;
    }

}
