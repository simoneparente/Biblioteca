--Insert View
CREATE OR REPLACE VIEW b.viewLibroaAutoreSerie AS
SELECT l.titolo,
       l.ISBN,
       concat(a.nome, ' ', a.cognome) as Nome_Cognome,
       l.datapubblicazione,
       l.Editore,
       l.Genere,
       l.Lingua,
       l.Formato,
       s.nome                         as NOME_Serie_di_Appartenenza,
       s.ISSN                         as ISSN_Serie_di_Appartenenza
FROM (((b.libro as l JOIN b.autorelibro as al ON l.id_libro = al.id_libro) JOIN b.autore as a
       ON al.id_autore = a.id_autore) JOIN b.libroinserie as ls ON l.id_libro = ls.libro)
         JOIN b.serie as s ON ls.id_serie = s.id_serie;

CREATE OR REPLACE FUNCTION b.tfun_LibroaAutoreSerie() RETURNS TRIGGER AS
$$
DECLARE
    autori         text[]  := string_to_array(NEW.nome_cognome, ',');
    nautori        INTEGER := array_length(autori, 1);
    autore_nome    b.autore.nome%TYPE;
    autore_cognome b.autore.cognome%TYPE;
    newLibro       b.libro.ID_Libro%TYPE;
    newSerie       b.serie.ID_Serie%TYPE;
BEGIN
    --RAISE NOTICE 'nautori{%}', nautori;
    IF EXISTS(SELECT * FROM b.libro WHERE titolo = NEW.titolo AND datapubblicazione = NEW.datapubblicazione) THEN
        RAISE NOTICE 'Libro già presente';
    ELSE
        --Inserimento libro
        INSERT INTO b.libro(titolo, ISBN, datapubblicazione, Editore, Genere, Lingua, Formato)
        VALUES (NEW.titolo, NEW.ISBN, NEW.datapubblicazione, NEW.editore, NEW.datapubblicazione, NEW.lingua,
                New.Formato);
        --Inserimento Autori
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
                INSERT INTO b.autorelibro(id_autore, id_libro)
                SELECT a.id_autore, l.id_libro
                FROM b.autore as a,
                     b.libro as l
                WHERE a.nome = autore_nome
                  AND a.cognome = autore_cognome
                  AND l.titolo = NEW.titolo
                  AND l.datapubblicazione = NEW.datapubblicazione;
            END LOOP;

        --Inserimento Serie
        newLibro = (SELECT ID_Libro FROM b.libro WHERE ISBN = NEW.ISBN); -- Trasformo l'ISNN in un ID
        IF NEW.nome_serie_di_appartenenza IS NOT NULL AND
           NEW.issn_serie_di_appartenenza IS NOT NULL THEN -- Controllo se fa parte di una serie
            RAISE NOTICE 'Fa parte di una Serie';

            --controllo se ci sono altri libri di quella serie inserito (è un seguito)
            IF EXISTS(SELECT * FROM b.serie WHERE ISSN = NEW.ISSN_Serie_Di_Appartenenza) THEN
                newSerie = (SELECT id_serie FROM b.serie WHERE issn = New.ISSN_Serie_Di_Appartenenza);
                RAISE NOTICE 'Serie già presente';

                UPDATE b.libroinserie
                SET librosuccessivo = newLibro
                --FROM b.libroinserie
                WHERE id_serie = newSerie
                  AND librosuccessivo IS NULL;
                RAISE NOTICE 'LIBRO SUCCESSIVO INSERITO';

                INSERT INTO b.libroinserie (id_serie, libro) VALUES (newSerie, newLibro);
                RAISE NOTICE 'NUOVO LIBRO INSERITO';

            ELSE --NON ci sono altri libri, il libro è il primo della serie
                RAISE NOTICE 'Serie non presente';

                --Inserisco una nuova serie
                INSERT INTO b.serie (issn, nome)
                VALUES (NEW.ISSN_Serie_Di_Appartenenza, NEW.Nome_Serie_Di_Appartenenza);
                newSerie = (SELECT id_serie FROM b.serie WHERE issn = New.ISSN_Serie_Di_Appartenenza);
                RAISE NOTICE 'newserie{%}', newSerie;
                --Inserisco in libroinserie
                INSERT INTO b.libroinserie (id_serie, libro) VALUES (newSerie, newLibro);
                RAISE NOTICE 'NUOVO LIBRO INSERITO';
            end if;
        end if;
    END IF;
    RETURN NEW;
end;

$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trig_LibroaAutoreSerie
    INSTEAD OF INSERT
    ON b.viewLibroaAutoreSerie
    FOR EACH ROW
EXECUTE FUNCTION b.tfun_LibroaAutoreSerie();

--Insert View
INSERT INTO b.viewLibroaAutoreSerie (titolo, ISBN, nome_cognome, datapubblicazione, editore, genere, lingua, formato,
                                     nome_serie_di_appartenenza, issn_serie_di_appartenenza)
VALUES ('Il Signore degli Anelli 1', '978-88-04-58343-8', 'J.R.R._Tolkien', '1954-07-29', 'Mondadori', 'Fantasy',
         'Italiano', 'Ebook', 'Il Signore degli Anelli', '978-88-04-58339-8');