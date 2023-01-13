--Insert View
CREATE OR REPLACE VIEW b.viewLibroaAutoreSerie AS
SELECT titolo, ISBN, concat(nome, ' ', cognome) as Nome_Cognome,datapubblicazione, Editore, Genere, Lingua, Formato, TitoloSerie as Serie_di_Appartenenza
FROM ((b.Libro JOIN b.autorelibro ON libro.id_libro = autorelibro.id_libro)
         JOIN b.autore ON autore.id_autore = autorelibro.id_autore) JOIN b.Serie ON Serie.libro=Libro.id_libro;

--Creazione FTriggher
CREATE OR REPLACE FUNCTION b.ins_libroautoreSerie() --inserimento di più autori tramite view
    RETURNS trigger AS
$$
DECLARE
    autori         text[]  := string_to_array(NEW.nome_cognome, ',');
    nautori        INTEGER := array_length(autori, 1);
    autore_nome    b.autore.nome%TYPE;
    autore_cognome b.autore.cognome%TYPE;
BEGIN
    --RAISE NOTICE 'nautori{%}', nautori;
    IF EXISTS(SELECT * FROM b.libro WHERE titolo = NEW.titolo AND datapubblicazione = NEW.datapubblicazione) THEN
        RAISE NOTICE 'Libro già presente';
    ELSE
        INSERT INTO b.libro(titolo, ISBN, datapubblicazione, Editore, Genere, Lingua, Formato) VALUES (NEW.titolo, NEW.ISBN, NEW.datapubblicazione, NEW.editore, NEW.datapubblicazione, NEW.lingua, New.Formato);
        FOR i IN 1..nautori
            LOOP
                autore_nome := split_part(autori[i], '_', 1);
                autore_cognome := split_part(autori[i], '_', 2);
                RAISE NOTICE 'nome{%} | cognome{%}', autore_nome, autore_cognome;

                IF EXISTS(SELECT * FROM b.autore WHERE nome = autore_nome AND cognome = autore_cognome) THEN
                    RAISE NOTICE 'Autore già presente {%}', autori[i];
                ELSE
                    INSERT INTO b.autore (nome, cognome) VALUES (autore_nome, autore_cognome);
                END IF;
                INSERT INTO b.autorelibro(id_autore, id_libro) SELECT a.id_autore, l.id_libro
                                                                FROM b.autore as a, b.libro as l
                                                                WHERE a.nome=autore_nome       AND
                                                                      a.cognome=autore_cognome AND
                                                                      l.titolo = NEW.titolo    AND
                                                                      l.datapubblicazione = NEW.datapubblicazione;
            END LOOP;
            IF NEW.serie_di_appartenenza IS NOT NULL THEN --controllo che faccia parte di una serie
                IF NOT EXISTS (SELECT * FROM b.serie WHERE titoloSerie = NEW.serie_di_appartenenza) THEN --controllo che la serie non esista già
                    RAISE NOTICE 'Serie non presente';
                    INSERT INTO b.serie (titoloSerie) VALUES (NEW.serie_di_appartenenza);
                    INSERT INTO b.serie (libro) SELECT l.id_libro
                                                FROM b.libro as l, serie as s
                                                WHERE l.titolo = NEW.titolo AND
                                                      l.datapubblicazione = NEW.datapubblicazione AND
                                                      s.titoloserie = NEW.serie_di_appartenenza;
                ELSE --se la serie esiste già
                    RAISE NOTICE 'Serie già presente';
                    UPDATE b.serie SET librosuccessivo = l.id_libro
                                    FROM b.libro as l, b.serie as s
                                    WHERE l.titolo = NEW.titolo AND
                                          l.datapubblicazione = NEW.datapubblicazione AND
                                          s.titoloSerie = NEW.serie_di_appartenenza AND
                                          s.librosuccessivo IS NULL;
                END IF;
            END IF;
    END IF;
    RETURN NEW;
END
$$
LANGUAGE plpgsql;

--Creazione Trigger
CREATE OR REPLACE TRIGGER trig_libroautoreSerie
    INSTEAD OF INSERT
    ON b.viewLibroaAutoreSerie
    FOR EACH ROW
EXECUTE FUNCTION b.ins_libroautoreSerie();

INSERT INTO b.viewLibroaAutoreSerie (titolo, ISBN, nome_cognome, datapubblicazione, editore, genere, lingua, formato, serie_di_appartenenza)
VALUES ('Il Signore degli Anelli', '978-88-04-58302-8', 'J.R.R._Tolkien', '1954-07-29', 'Mondadori', 'Fantasy', 'Italiano', 'Cartaceo', 'Il Signore degli Anelli');