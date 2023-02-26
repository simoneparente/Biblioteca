package org.Bibliotech.DAOimplementazione;

import org.Bibliotech.ConnessioneDB;
import org.Bibliotech.DAO.LibroDao;
import org.Bibliotech.Model.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class ImplementazioneLibro implements LibroDao {
    private Connection connection;

    public ImplementazioneLibro() {
        try {
            connection = ConnessioneDB.getInstance().connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addLibro(Libro libro) {
        String addLibroQuery = "INSERT INTO b.ins_Libri (titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, " +
                "formato, prezzo, nome_serie_di_appartenenza, issn_serie_di_appartenenza) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(addLibroQuery);
            preparedStatement.setString(1, libro.getTitolo());
            preparedStatement.setString(2, libro.getIsbn());
            preparedStatement.setString(3, libro.getAutoriString());
            preparedStatement.setDate(4, libro.getDataPubblicazione());
            preparedStatement.setString(5, libro.getEditore());
            preparedStatement.setString(6, libro.getGenere());
            preparedStatement.setString(7, libro.getLingua());
            preparedStatement.setString(8, libro.getFormato());
            if (libro.getPrezzo().equals("")) {
                preparedStatement.setNull(9, Types.DOUBLE);
            } else {
                preparedStatement.setDouble(9, Double.parseDouble(libro.getPrezzo()));
            }

            //se il libro non appartiene a una serie (il checkobox è disabilitato) allora il nome e l'issn della serie sono null
            if (libro.getSerieDiAppartenenza() == null) {
                preparedStatement.setNull(10, Types.VARCHAR);
                preparedStatement.setNull(11, Types.VARCHAR);
            } else {
                preparedStatement.setString(10, libro.getSerieDiAppartenenza());
                preparedStatement.setString(11, libro.getISSNSerieDiAppartenenza());
            }
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
