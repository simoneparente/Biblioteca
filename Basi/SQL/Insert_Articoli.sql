CREATE OR REPLACE VIEW b.ins_ArticoliPresentazione AS
SELECT a.doi,
       a.titolo,
       TEXT           as AutoriNome_Cognome, --'nome1 cognome1, nome2 cognome2'
       a.datapubblicazione,
       a.disciplina,
       a.editore,
       a.lingua,
       a.formato,
       r.nome         as nomeRivista,
       r.issn         as issnRivista,
       r.argomento    as argomentoRivista,
       r.responsabile as responsabileRivista,
       r.prezzo       as prezzoRivista
FROM b.Articoli a,
     b.jolly,
     b.riviste r;

CREATE OR REPLACE PROCEDURE b.insAutori(stringAutori text, idRisorsa INTEGER) AS
$$
DECLARE
    autori        text[]  = string_to_array(stringAutori, ' ');
    numAutori     INTEGER = array_length(autori, 1);
    autoreNome    b.autore.nome%TYPE;
    autoreCognome b.autore.cognome%TYPE;
    idAutore      b.autore.id_autore%TYPE;
BEGIN
    FOR i IN 1..numAutori
        LOOP
            autoreNome = split_part(autori[i], '_', 1);
            autoreCognome = split_part(autori[i], '_', 2);
            RAISE NOTICE 'autoreNome: % autoreCognome: %', autoreNome, autoreCognome;
            IF NOT EXISTS(SELECT * FROM b.autore WHERE nome = autoreNome AND cognome = autoreCognome) THEN
                RAISE NOTICE 'Autore non presente, verrà inserito';
                INSERT INTO b.autore (nome, cognome) VALUES (autoreNome, autoreCognome);
            END IF;
            idAutore = (SELECT id_autore FROM b.autore WHERE nome = autoreNome AND cognome = autoreCognome);
            INSERT INTO b.autorelibro (id_autore, id_libro) VALUES (idAutore, idRisorsa);
        END LOOP;
END
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION b.ftrig_ArticoliPresentazione() RETURNS trigger AS
$$
DECLARE
    idRivista  b.riviste.id_rivista%TYPE;
    idArticolo b.articoli.doi%TYPE;
BEGIN
    IF EXISTS(SELECT * FROM b.articoli WHERE doi = NEW.doi) THEN
        RAISE NOTICE 'Articolo già presente, non verrà inserito';
    ELSE
        IF NOT EXISTS(SELECT * FROM b.riviste WHERE issn = NEW.issnRivista) THEN
            RAISE NOTICE 'Rivista non presente, verrà inserita';
            INSERT INTO b.riviste (nome, issn, argomento, datapubblicazione, responsabile, prezzo)
            VALUES (NEW.nomeRivista, NEW.issnRivista, NEW.argomentoRivista, NEW.responsabileRivista, NEW.prezzoRivista);
        ELSEIF NOT EXISTS(SELECT datapubblicazione
                          FROM b.riviste
                          WHERE issn = NEW.issnRivista
                            AND datapubblicazione = NEW.datapubblicazione) THEN
            RAISE NOTICE 'Rivista già presente ma con data di pubblicazione diversa, l''articolo non verrà inserito';
            RETURN NEW;
        END IF;
        INSERT INTO b.articoli (doi, titolo, datapubblicazione, disciplina, editore, lingua, formato)
        VALUES (NEW.doi, NEW.titolo, NEW.datapubblicazione, NEW.disciplina, NEW.editore, NEW.lingua, NEW.formato);
        idArticolo = (SELECT id_articolo FROM b.articoli WHERE doi = NEW.doi);
        PERFORM b.insAutori(NEW.AutoriNome_Cognome, idArticolo);
        idRivista = (SELECT id_rivista FROM b.riviste WHERE issn = NEW.issnRivista);
        INSERT INTO b.articoliInRiviste (id_articolo, id_rivista) VALUES (idArticolo, idRivista);
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trig_ArticoliPresentazione
    INSTEAD OF INSERT
    ON b.ins_ArticoliPresentazione
    FOR EACH ROW
EXECUTE FUNCTION b.ftrig_ArticoliPresentazione();


CREATE OR REPLACE VIEW b.ins_articoliConferenze AS
SELECT a.doi,
       a.titolo,
       TEXT                 as AutoriNome_Cognome, --'nome1 cognome1 nome2 cognome2'
       a.datapubblicazione,
       a.disciplina,
       a.editore,
       a.lingua,
       a.formato,
       e.nome               as nomeConferenza,
       e.indirizzo          as indirizzoConferenza,
       e.strutturaospitante as strutturaospitanteConferenza,
       e.datainizio         as datainizioConferenza,
       e.datafine           as datafineConferenza,
       e.responsabile       as responsabileConferenza
FROM b.Articoli a,
     b.jolly,
     b.evento e;

CREATE OR REPLACE FUNCTION b.ftrig_ArticoliConferenze() RETURNS TRIGGER AS
$$
DECLARE
    idArticolo   b.articoli.doi%TYPE;
    idConferenza b.evento.id_evento%TYPE;
BEGIN
    IF EXISTS(SELECT * FROM b.articoli WHERE doi = NEW.doi) THEN
        RAISE NOTICE 'Articolo già presente, non verrà inserito';
    ELSEIF (NEW.datapubblicazione < NEW.datainizioConferenza OR NEW.datapubblicazione > NEW.datafineConferenza) THEN
        RAISE NOTICE 'La data di pubblicazione dell''articolo non è compresa tra la data di inizio e la data di fine della conferenza, l''articolo non verrà inserito';
    ELSE
        IF NOT EXISTS(SELECT *
                      FROM b.evento
                      WHERE nome = NEW.nomeConferenza
                        AND indirizzo = NEW.indirizzoConferenza
                        AND datainizio = NEW.dataInizioConferenza) THEN
            RAISE NOTICE 'Conferenza non presente, verrà inserita';
            INSERT INTO b.evento (nome, indirizzo, strutturaospitante, datainizio, datafine, responsabile)
            VALUES (NEW.nomeConferenza, NEW.indirizzoConferenza, NEW.strutturaospitanteConferenza,
                    NEW.datainizioConferenza, NEW.datafineConferenza, NEW.responsabileConferenza);
        END IF;
        INSERT INTO b.articoli (doi, titolo, datapubblicazione, disciplina, editore, lingua, formato);
        idArticolo = (SELECT id_articolo FROM b.articoli WHERE doi = NEW.doi);
        PERFORM b.insAutori(NEW.AutoriNome_Cognome, idArticolo);
        idConferenza = (SELECT id_evento FROM b.evento WHERE nome = NEW.nomeConferenza AND indirizzo = NEW.indirizzoConferenza);
        INSERT INTO b.Conferenza (id_articolo, id_evento) VALUES (idArticolo, idConferenza);
    END IF;
    RETURN NEW;
END ;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trig_ArticoliConferenze
    INSTEAD OF INSERT
    ON b.ins_ArticoliConferenze
    FOR EACH ROW
EXECUTE FUNCTION b.ftrig_ArticoliConferenze();