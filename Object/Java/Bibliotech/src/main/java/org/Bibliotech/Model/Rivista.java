package org.Bibliotech.Model;

import java.sql.Date;

public class Rivista {
    String nome;
    String issn;
    String argomento;
    Date dataPubblicazione;
    String responsabile;
    Double prezzo;

    public Rivista(String nome, String issn, String argomento, String dataPubblicazione, String responsabile, Double prezzo){
        this.nome = nome;
        this.issn = issn;
        this.argomento = argomento;
        this.dataPubblicazione = Date.valueOf(dataPubblicazione);
        this.responsabile = responsabile;
        this.prezzo = prezzo;
    }

    public String getNome() {
        return nome;
    }

    public String getIssn() {
        return issn;
    }

    public String getArgomento() {
        return argomento;
    }

    public Date getDataPubblicazione() {
        return dataPubblicazione;
    }

    public String getResponsabile() {
        return responsabile;
    }

    public Double getPrezzo() {
        return prezzo;
    }

}
