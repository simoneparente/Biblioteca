--Insert View
CREATE OR REPLACE VIEW b.viewLibroaAutoreSerie AS
SELECT titolo,
       ISBN,
       concat(autore.nome, ' ', cognome) as Nome_Cognome,
       datapubblicazione,
       Editore,
       Genere,
       Lingua,
       Formato,
       Serie.nome                 as NOME_Serie_di_Appartenenza,
       Serie.issn                 as ISSN_Serie_di_Appartenenza
FROM ((b.Libro JOIN b.autorelibro ON libro.id_libro = autorelibro.id_libro)
    JOIN b.autore ON autore.id_autore = autorelibro.id_autore)
         JOIN b.Serie ON Serie.libro = Libro.id_libro;


CREATE OR REPLACE FUNCTION b.trgLibroaAutoreSerie() RETURNS TRIGGER AS
$$
DECLARE
    autori         text[]  := string_to_array(NEW.nome_cognome, ',');
    nautori        INTEGER := array_length(autori, 1);
    autore_nome    b.autore.nome%TYPE;
    autore_cognome b.autore.cognome%TYPE;
    newLibro       b.libro.ID_Libro%TYPE;
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
        newLibro = (SELECT ID_Libro FROM libro WHERE ISBN = NEW.ISBN); -- Trasformo l'ISNN in un ID
        IF NEW.Serie_Di_Appartenenza IS NOT NULL THEN -- Controllo se fa parte di una serie
            RAISE NOTICE 'Fa parte di una Serie';

            --controllo se ci sono altri libri di quella serie inserito (è un seguito)
            IF EXISTS(SELECT * FROM serie WHERE nome = NEW.Serie_Di_Appartenenza) THEN
                RAISE NOTICE 'Serie già presente';
                UPDATE b.serie
                SET librosuccessivo = newLibro
                FROM serie
                WHERE nome = NEW.Serie_Di_Appartenenza AND librosuccessivo IS NULL;
                RAISE NOTICE 'LIBRO SUCCESSIVO INSERITO';
                INSERT INTO b.serie (nome, libro) VALUES (NEW.Serie_Di_Appartenenza, newLibro);
                RAISE NOTICE 'NUOVO LIBRO INSERITO';

            ELSE --NON ci sono altri libri, il libro è il primo della serie
                RAISE NOTICE 'Serie non presente';
                INSERT INTO b.serie (nome, libro) VALUES (NEW.Serie_Di_Appartenenza, newLibro);
            end if;
        end if;
    END IF;

end;

$$ LANGUAGE plpgsql;