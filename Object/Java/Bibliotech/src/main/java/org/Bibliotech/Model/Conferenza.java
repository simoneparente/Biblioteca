package org.Bibliotech.Model;

import java.sql.Date;

public class Conferenza {
    private String nome;
    private String responsabile;
    private String strutturaOspitante;
    private String indirizzo;
    private Date dataInizio;
    private Date dataFine;

    public Conferenza(String nome, String responsabile, String strutturaOspitante, String indirizzo, String dataInizio, String dataFine){
        this.nome = nome;
        this.responsabile = responsabile;
        this.strutturaOspitante = strutturaOspitante;
        this.indirizzo = indirizzo;
        this.dataInizio = Date.valueOf(dataInizio);
        this.dataFine = Date.valueOf(dataFine);
    }


    public String getNome() {
        return nome;
    }

    public String getResponsabile() {
        return responsabile;
    }

    public String getStrutturaOspitante() {
        return strutturaOspitante;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public Date getDataFine() {
        return dataFine;
    }
}
