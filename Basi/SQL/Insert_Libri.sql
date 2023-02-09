CREATE OR REPLACE VIEW b.ins_Libri AS
SELECT l.titolo,
       l.ISBN,
       j.TEXT as AutoriNome_Cognome, --'Nome1_Cognome1 Nome2_Cognome2'
       l.datapubblicazione,
       l.Editore,
       l.Genere,
       l.Lingua,
       l.Formato,
       l.Prezzo,
       s.nome as NOME_Serie_di_Appartenenza,
       s.ISSN as ISSN_Serie_di_Appartenenza
FROM b.libri as l,
     b.serie as s,
     b.jolly as j;

CREATE OR REPLACE FUNCTION b.ftrig_LibriAutoreSerie() RETURNS TRIGGER AS
$$
DECLARE
    idLibro b.libri.ID_Libro%TYPE;
    idSerie b.serie.ID_Serie%TYPE;
BEGIN
    IF EXISTS(SELECT * FROM b.libri WHERE isbn = NEW.isbn) THEN
        RAISE NOTICE 'Libro già presente';
    ELSE
        IF NOT EXISTS(SELECT * FROM b.riviste WHERE issn = NEW.issn_serie_di_appartenenza) THEN
            RAISE NOTICE 'Serie non presente';
            INSERT INTO b.serie(nome, issn) values (NEW.nome_serie_di_appartenenza, NEW.issn_serie_di_appartenenza);
        ELSEIF NOT EXISTS(SELECT *
                          FROM (b.serie s NATURAL JOIN libriinserie ls)
                                   JOIN libri l ON ls.id_libro = l.id_libro
                          WHERE l.formato = NEW.formato) THEN
            RAISE NOTICE 'Il formato del libro non è compatibile con la serie, libro non inserito';
            RETURN NEW;
        END IF;
        INSERT INTO b.libri (titolo, isbn, datapubblicazione, editore, genere, lingua, formato, prezzo)
        VALUES (NEW.titolo, NEW.isbn, NEW.datapubblicazione, NEW.editore, NEW.genere, NEW.lingua, NEW.formato,
                NEW.prezzo);
        idLibro = (SELECT id_libro FROM b.libri WHERE isbn = NEW.isbn);
        PERFORM b.insAutori(NEW.autoriNome_cognome, idLibro);
        idSerie = (SELECT id_serie FROM b.serie WHERE issn = NEW.issn_serie_di_appartenenza);
        INSERT INTO b.libriinserie (id_libro, id_serie) VALUES (idLibro, idSerie);
    END IF;
END
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trig_LibriAutoreSerie
    INSTEAD OF INSERT
    ON b.ins_libri_autore_serie
    FOR EACH ROW
EXECUTE FUNCTION b.ftrig_LibriAutoreSerie();
