package org.Bibliotech.DAO;

import org.Bibliotech.Model.Riviste;

public interface RivisteDao {
    Riviste getRiviste(String query, String parametro);
    Riviste getRiviste(String query, String parametro1, String parametro2);


}