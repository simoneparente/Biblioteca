--Insert View
CREATE OR REPLACE VIEW b.viewlibroautore AS
SELECT titolo, ISBN, concat(nome, ' ', cognome) as AutoreNome_Cognome,datapubblicazione, Editore, Genere, Lingua, Formato
FROM (b.Libro JOIN b.autorelibro ON libro.id_libro = autorelibro.id_libro)
         JOIN b.autore ON autore.id_autore = autorelibro.id_autore;


--Creazione FTriggher
CREATE OR REPLACE FUNCTION b.ins_libroautore() --inserimento di più autori tramite view
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
    END IF;
    RETURN NEW;
END
$$
LANGUAGE plpgsql;

--Creazione Trigger
CREATE OR REPLACE TRIGGER trig_libroautore
    INSTEAD OF INSERT
    ON b.viewlibroautore
    FOR EACH ROW
EXECUTE FUNCTION b.ins_libroautore();

INSERT INTO b.viewlibroautore(titolo, datapubblicazione, nome_cognome, isbn)
values ('Il nome della rosa', '1980-01-01', 'Umberto_Eco,Provo_Provoni,Marco_Marconi,Marco_Marconinnifurhy','cazzoculodiocane');
