package org.Bibliotech.DAO;

import org.Bibliotech.Model.Serie;

public interface SerieDAO {

    Serie getSerie(String query, String parametro);
    Serie getSerie(String query, String parametro1, String parametro2);

    //Attributi Tabella Serie
    Serie getSerie();
    Serie getSerieById(int idSerie);
    Serie getSerieByISSN(String ISSN);
    Serie getSerieByNome(String nomeSerie);
}
