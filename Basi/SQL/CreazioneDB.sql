DROP SCHEMA IF EXISTS b CASCADE;
CREATE SCHEMA b;

------------------------------------------------------------------------------------------------------------------------
--Creazione tabelle
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE b.Articoli
(
    ID_Articolo       SERIAL,
    Titolo            VARCHAR(128),
    DOI               VARCHAR(128),
    DataPubblicazione DATE,
    Disciplina        VARCHAR(128),
    Editore           VARCHAR(128),
    Lingua            VARCHAR(128),
    Formato           VARCHAR(128),

    CONSTRAINT PK_Articoli PRIMARY KEY (ID_Articolo),
    CONSTRAINT UK_Articolo UNIQUE (DOI)
);

CREATE TABLE b.Autore
(
    ID_Autore SERIAL,
    Nome      VARCHAR(128),
    Cognome   VARCHAR(128),

    CONSTRAINT PK_Autore PRIMARY KEY (ID_Autore)
);

CREATE TABLE b.AutoreArticolo
(
    ID_Autore   SERIAL,
    ID_Articolo SERIAL,

    CONSTRAINT PK_AutoreArticolo PRIMARY KEY (ID_Autore, ID_Articolo),
    CONSTRAINT FK_AutoreArticolo_Autore FOREIGN KEY (ID_Autore) REFERENCES b.Autore (ID_Autore) ON DELETE CASCADE,
    CONSTRAINT FK_AutoreArticolo_Articoli FOREIGN KEY (ID_Articolo) REFERENCES b.Articoli (ID_Articolo) ON DELETE CASCADE
);

CREATE TABLE b.Riviste
(
    ID_Rivista        SERIAL,
    ISSN              VARCHAR(128),
    Nome              VARCHAR(128),
    Argomento         VARCHAR(128),
    DataPubblicazione DATE,
    Responsabile      VARCHAR(128),
    Prezzo            FLOAT,

    CONSTRAINT PK_Riviste PRIMARY KEY (ID_Rivista)
);

CREATE TABLE b.ArticoliInRiviste
(
    ID_Articolo SERIAL,
    ID_Rivista  SERIAL,

    CONSTRAINT PK_ArticoliInRiviste PRIMARY KEY (ID_Articolo, ID_Rivista),
    CONSTRAINT FK_ArticoliInRiviste_Articolo FOREIGN KEY (ID_Articolo) REFERENCES b.Articoli (ID_Articolo) ON DELETE CASCADE,
    CONSTRAINT FK_ArticoliInRiviste_Rivista FOREIGN KEY (ID_Rivista) REFERENCES b.Riviste (ID_Rivista) ON DELETE CASCADE
);


CREATE TABLE b.Evento
(
    ID_Evento          SERIAL,
    Nome               VARCHAR(128),
    Indirizzo          VARCHAR(128),
    StrutturaOspitante VARCHAR(128),
    DataInizio         DATE,
    DataFine           DATE,
    Responsabile       VARCHAR(128),

    CONSTRAINT PK_Evento PRIMARY KEY (ID_Evento),
    CONSTRAINT CK_Date CHECK (DataInizio <= DataFine),
    CONSTRAINT UK_Evento UNIQUE (Indirizzo, StrutturaOspitante, DataInizio, DataFine, Responsabile)
);

CREATE TABLE b.Conferenza
(
    ID_Articolo SERIAL,
    ID_Evento   SERIAL,

    CONSTRAINT PK_Conferenza PRIMARY KEY (ID_Articolo, ID_Evento),
    CONSTRAINT FK_Conferenza_Articolo FOREIGN KEY (ID_Articolo) REFERENCES b.Articoli (ID_Articolo) ON DELETE CASCADE,
    CONSTRAINT FK_Conferenza_Evento FOREIGN KEY (ID_Evento) REFERENCES b.Evento (ID_Evento) ON DELETE CASCADE
);


CREATE TABLE b.Libri
(
    ID_Libro          SERIAL,
    Titolo            VARCHAR(128),
    ISBN              VARCHAR(128),
    DataPubblicazione DATE,
    Editore           VARCHAR(128),
    Genere            VARCHAR(128),
    Lingua            VARCHAR(128),
    Formato           VARCHAR(128),
    Prezzo            FLOAT,

    CONSTRAINT PK_Libri PRIMARY KEY (ID_Libro),
    CONSTRAINT UK_Libro UNIQUE (ISBN),
    CONSTRAINT CK_Libri CHECK (Prezzo > 0)
);

CREATE TABLE b.AutoreLibro
(
    ID_Autore SERIAL,
    ID_Libro  SERIAL,

    CONSTRAINT PK_AutoreLibro PRIMARY KEY (ID_Autore, ID_Libro),
    CONSTRAINT FK_AutoreLibro_Autore FOREIGN KEY (ID_Autore) REFERENCES b.Autore (ID_Autore) ON DELETE CASCADE,
    CONSTRAINT FK_AutoreLibro_Libro FOREIGN KEY (ID_Libro) REFERENCES b.Libri (ID_Libro) ON DELETE CASCADE
);

CREATE TABLE b.Presentazione
(
    ID_Evento SERIAL,
    ID_Libro  SERIAL,

    CONSTRAINT PK_Presentazione PRIMARY KEY (ID_Evento, ID_Libro),
    CONSTRAINT FK_Presentazione_Evento FOREIGN KEY (ID_Evento) REFERENCES b.Evento (ID_Evento) ON DELETE CASCADE,
    CONSTRAINT FK_Presentazione_Libro FOREIGN KEY (ID_Libro) REFERENCES b.Libri (ID_Libro) ON DELETE CASCADE
);

CREATE TABLE b.Serie
(
    ID_Serie SERIAL,
    ISSN     VARCHAR(128),
    Nome     VARCHAR(128),

    CONSTRAINT PK_Serie PRIMARY KEY (ID_Serie),
    CONSTRAINT UK_Serie UNIQUE (ISSN)
);

CREATE TABLE b.LibriInSerie
(
    ID_Serie INTEGER,
    ID_Libro INTEGER,

    CONSTRAINT PK_LibriInSerie PRIMARY KEY (ID_Serie, ID_Libro),
    CONSTRAINT FK_Libri_Serie FOREIGN KEY (ID_Serie) REFERENCES b.Serie (ID_Serie) ON DELETE CASCADE,
    CONSTRAINT FK_Serie_Libri FOREIGN KEY (ID_Libro) REFERENCES b.Libri (ID_Libro) ON DELETE CASCADE
);

CREATE TABLE b.Negozio
(
    ID_Negozio SERIAL,
    Nome       VARCHAR(128),
    Tipo       VARCHAR(128),

    CONSTRAINT PK_Negozio PRIMARY KEY (ID_Negozio)
);

CREATE TABLE b.Stock
(
    ID_Negozio SERIAL,
    ID_Libro   SERIAL,
    Quantita   INTEGER, -- se quantita=0 la tupla viene eliminata (scoprire come si fa)

    CONSTRAINT FK_Stock_Negozio FOREIGN KEY (ID_Negozio) REFERENCES b.Negozio (ID_Negozio) ON DELETE CASCADE,
    CONSTRAINT FK_Stock_Libri FOREIGN KEY (ID_Libro) REFERENCES b.Libri (ID_Libro) ON DELETE CASCADE
);

------------------------------------------------------------------------------------------------------------------------
--Tabelle per la gestione delle notifiche di disponibilità di un libro
------------------------------------------------------------------------------------------------------------------------

CREATE TABLE b.Utente
(
    ID_Utente SERIAL,
    Username  VARCHAR(128),
    Password  VARCHAR(128),

    CONSTRAINT PK_Utente PRIMARY KEY (ID_Utente),
    CONSTRAINT UK_Utente UNIQUE (Username)
);

CREATE TABLE b.Richiesta
(
    ID_Utente     SERIAL,
    ID_Serie      SERIAL,
    Disponibilità BOOLEAN,

    CONSTRAINT PK_Richiesta PRIMARY KEY (ID_Utente, ID_Serie),
    CONSTRAINT FK_Richiesta_Utente FOREIGN KEY (ID_Utente) REFERENCES b.Utente (ID_Utente) ON DELETE CASCADE,
    CONSTRAINT FK_Richiesta_Serie FOREIGN KEY (ID_Serie) REFERENCES b.Serie (ID_Serie) ON DELETE CASCADE
);

------------------------------------------------------------------------------------------------------------------------
--Tabella Jolly con campo text per le insert multiple
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE b.Jolly
(
    Text TEXT
);
------------------------------------------------------------------------------------------------------------------------


------------------------------------------------------------------------------------------------------------------------
--Trigger Insert Articoli ed Autori
------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE VIEW b.ins_articoli_autori AS
SELECT doi,
       titolo,
       TEXT as AutoriNome_Cognome, --'nome1 cognome1, nome2 cognome2'
       datapubblicazione,
       disciplina,
       editore,
       lingua,
       formato
FROM b.Articoli,
     b.jolly;

CREATE OR REPLACE FUNCTION b.ftrig_ArticoliAutori() RETURNS TRIGGER AS
$$
DECLARE
    autori         text[]= string_to_array(NEW.autorinome_cognome, ' ');
    n_autori       INTEGER= array_length(autori, 1);
    autore_nome    b.autore.nome%TYPE;
    autore_cognome b.autore.cognome%TYPE;
BEGIN
    --Controllo se l'articolo è già presente
    IF EXISTS(SELECT * FROM b.articoli WHERE titolo = NEW.titolo AND datapubblicazione = NEW.datapubblicazione) THEN
        RAISE NOTICE 'Articolo già presente';
    ELSE
        INSERT INTO b.articoli(titolo, doi, datapubblicazione, disciplina, editore, lingua, formato)
        VALUES (NEW.titolo, NEW.doi, NEW.datapubblicazione, NEW.disciplina, NEW.editore, NEW.lingua, New.Formato);

        --Insert autori
        FOR i IN 1..n_autori
            LOOP
                autore_nome := split_part(autori[i], '_', 1);
                autore_cognome := split_part(autori[i], '_', 2);
                RAISE NOTICE 'nome{%} | cognome{%}', autore_nome, autore_cognome;

                --Controllo se l'autore è già presente
                IF EXISTS(SELECT * FROM b.autore WHERE nome = autore_nome AND cognome = autore_cognome) THEN
                    RAISE NOTICE 'Autore già presente {%}', autori[i];
                ELSE
                    INSERT INTO b.autore (nome, cognome) VALUES (autore_nome, autore_cognome); --inserisco autore
                END IF;
                INSERT INTO b.autoreArticolo(id_autore, id_Articolo)
                SELECT a.id_autore, ar.id_articolo --recupero id_autore e id_articolo
                FROM b.autore as a,
                     b.articoli as ar
                WHERE a.nome = autore_nome
                  AND a.cognome = autore_cognome
                  AND ar.titolo = NEW.titolo
                  AND ar.datapubblicazione = NEW.datapubblicazione;
            END LOOP;
    END IF;
    RETURN NEW;
END
$$
    LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trig_ArticoliAutori
    INSTEAD OF INSERT
    ON b.ins_articoli_autori
    FOR EACH ROW
EXECUTE FUNCTION b.ftrig_ArticoliAutori();
------------------------------------------------------------------------------------------------------------------------


------------------------------------------------------------------------------------------------------------------------
--Trigger Insert Riviste
------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE VIEW b.ins_riviste AS
SELECT issn,
       nome,
       argomento,
       datapubblicazione,
       responsabile,
       prezzo,
       text as Doi_Articoli_Pubblicati --Più articoli (DOI1 DOI2)
FROM b.riviste,
     b.jolly;

CREATE OR REPLACE FUNCTION b.ftrig_riviste() RETURNS TRIGGER AS
$$
DECLARE
    articolip   text[]  := string_to_array(NEW.Doi_Articoli_Pubblicati, ' ');
    narticoli   INTEGER := array_length(articolip, 1);
    newArticolo b.Articoli.id_Articolo%TYPE;
    newrivista  b.riviste.ID_Rivista%TYPE;
    vcheck      INTEGER := 0;
BEGIN
    FOR i IN 1..narticoli
        LOOP
            newArticolo = (SELECT id_Articolo FROM b.Articoli WHERE doi = articolip[i]);
            --Controllo se l'Articolo esiste
            IF NOT EXISTS(SELECT * FROM b.Articoli WHERE doi = articolip[i]) THEN
                RAISE NOTICE 'Articoli {%} non presente', articolip[i];
                vcheck := 1;
                --Controllo se l'Articolo è già presente in una conferenza
            ELSEIF EXISTS(SELECT * FROM b.conferenza c WHERE c.id_articolo = newArticolo) THEN
                RAISE NOTICE 'Articolo {%} già presente in una conferenza', articolip[i];
                vcheck := 2;
            END IF;
        END LOOP;

    IF (vcheck = 1) THEN
        RAISE NOTICE 'EVENTO NON INSERITO, UNO O PIU'' ARTICOLI SONO INESISTENTI ';
    ELSIF (vcheck = 2) THEN
        RAISE NOTICE 'EVENTO NON INSERITO, UNO O PIU'' ARTICOLI SONO GIA'' PRESENTI IN UNA CONFERENZA ';
    ELSE
        --Inserisco la rivista
        INSERT INTO b.riviste (nome, issn, argomento, datapubblicazione, responsabile, prezzo)
        VALUES (NEW.nome, NEW.issn, NEW.argomento, NEW.datapubblicazione, NEW.responsabile,
                NEW.prezzo);

        --Recupero l'id della rivista appena inserita
        SELECT id_rivista INTO newrivista FROM b.riviste WHERE nome = NEW.nome AND issn = NEW.issn;

        --Inserisco gli articoli nella rivista
        FOR i IN 1..narticoli
            LOOP
                SELECT id_Articolo INTO newArticolo FROM b.Articoli WHERE doi = articolip[i]; --recupero id Articoli
                INSERT INTO b.articoliinriviste (id_articolo, id_rivista)
                VALUES (newArticolo, newrivista);
            END LOOP;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trig_Riviste
    INSTEAD OF INSERT
    ON b.ins_riviste
    FOR EACH ROW
EXECUTE FUNCTION b.ftrig_riviste();
------------------------------------------------------------------------------------------------------------------------


------------------------------------------------------------------------------------------------------------------------
--Trigger Insert Articoli ed Autori
------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE VIEW b.ins_articoli_autori AS
SELECT doi,
       titolo,
       TEXT as AutoriNome_Cognome, --'nome1 cognome1, nome2 cognome2'
       datapubblicazione,
       disciplina,
       editore,
       lingua,
       formato
FROM b.Articoli,
     b.jolly;

CREATE OR REPLACE FUNCTION b.ftrig_ArticoliAutori() RETURNS TRIGGER AS
$$
DECLARE
    autori         text[]= string_to_array(NEW.autorinome_cognome, ' ');
    n_autori       INTEGER= array_length(autori, 1);
    autore_nome    b.autore.nome%TYPE;
    autore_cognome b.autore.cognome%TYPE;
BEGIN
    --Controllo se l'articolo è già presente
    IF EXISTS(SELECT * FROM b.articoli WHERE titolo = NEW.titolo AND datapubblicazione = NEW.datapubblicazione) THEN
        RAISE NOTICE 'Articolo già presente';
    ELSE
        INSERT INTO b.articoli(titolo, doi, datapubblicazione, disciplina, editore, lingua, formato)
        VALUES (NEW.titolo, NEW.doi, NEW.datapubblicazione, NEW.disciplina, NEW.editore, NEW.lingua, New.Formato);

        --Insert autori
        FOR i IN 1..n_autori
            LOOP
                autore_nome := split_part(autori[i], '_', 1);
                autore_cognome := split_part(autori[i], '_', 2);
                RAISE NOTICE 'nome{%} | cognome{%}', autore_nome, autore_cognome;

                --Controllo se l'autore è già presente
                IF EXISTS(SELECT * FROM b.autore WHERE nome = autore_nome AND cognome = autore_cognome) THEN
                    RAISE NOTICE 'Autore già presente {%}', autori[i];
                ELSE
                    INSERT INTO b.autore (nome, cognome) VALUES (autore_nome, autore_cognome); --inserisco autore
                END IF;
                INSERT INTO b.autoreArticolo(id_autore, id_Articolo)
                SELECT a.id_autore, ar.id_articolo --recupero id_autore e id_articolo
                FROM b.autore as a,
                     b.articoli as ar
                WHERE a.nome = autore_nome
                  AND a.cognome = autore_cognome
                  AND ar.titolo = NEW.titolo
                  AND ar.datapubblicazione = NEW.datapubblicazione;
            END LOOP;
    END IF;
    RETURN NEW;
END
$$
    LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trig_ArticoliAutori
    INSTEAD OF INSERT
    ON b.ins_articoli_autori
    FOR EACH ROW
EXECUTE FUNCTION b.ftrig_ArticoliAutori();
------------------------------------------------------------------------------------------------------------------------


------------------------------------------------------------------------------------------------------------------------
--Trigger Rimozione Articoli
------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION b.ftrig_rimozineArticoli() RETURNS trigger AS
$$
DECLARE
    idAutoreArticolo b.autore.id_autore%TYPE;
    idAutoreArticoli CURSOR FOR SELECT id_autore
                                FROM b.autorearticolo
                                WHERE id_articolo = OLD.id_articolo;
    idRivista        b.riviste.id_rivista%TYPE = (SELECT id_rivista
                                                  FROM b.articoliinriviste
                                                  WHERE id_articolo = OLD.id_articolo);
    IdPresentazione  b.evento.id_evento%TYPE   = (SELECT id_evento
                                                  FROM b.presentazione
                                                  WHERE id_articolo = OLD.id_articolo);
BEGIN
    --Eliminazione Autori se non hanno scritto altro
    OPEN idAutoreArticoli;
    LOOP
        FETCH idAutoreArticoli INTO idAutoreArticolo;
        EXIT WHEN NOT FOUND;
        IF NOT EXISTS(SELECT id_autore
                      FROM b.autorearticolo
                      WHERE id_autore = idAutoreArticolo
                        AND id_articolo <> OLD.id_articolo) THEN
            IF NOT EXISTS(SELECT * FROM b.autorelibro WHERE id_autore = idAutoreArticolo) THEN
                DELETE FROM b.autore WHERE id_autore = idAutoreArticolo;
            END IF;
        END IF;
    END LOOP;

    --Eliminazione Rivista se non ha altri articoli
    IF NOT EXISTS(SELECT *
                  FROM b.articoliinriviste
                  WHERE id_articolo <> old.id_articolo
                    AND id_rivista = idRivista) THEN
        DELETE FROM b.riviste WHERE id_rivista = idRivista;
    END IF;

    --Eliminazione Presentazione se non ha altri articoli
    IF NOT EXISTS(SELECT *
                  FROM b.presentazione
                  WHERE id_articolo <> old.id_articolo
                    AND id_evento = IdPresentazione) THEN
        DELETE FROM b.evento WHERE id_evento = IdPresentazione;
    END IF;

    CLOSE idAutoreArticoli;
    RETURN NEW;
end;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trig_rimozioneArticoli
    BEFORE DELETE
    ON b.articoli
    FOR EACH ROW
EXECUTE PROCEDURE b.ftrig_rimozineArticoli();
------------------------------------------------------------------------------------------------------------------------


------------------------------------------------------------------------------------------------------------------------
--Trigger Insert Riviste
------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE VIEW b.ins_riviste AS
SELECT issn,
       nome,
       argomento,
       datapubblicazione,
       responsabile,
       prezzo,
       text as Doi_Articoli_Pubblicati --Più articoli (DOI1 DOI2)
FROM b.riviste,
     b.jolly;

CREATE OR REPLACE FUNCTION b.ftrig_riviste() RETURNS TRIGGER AS
$$
DECLARE
    articolip   text[]  := string_to_array(NEW.Doi_Articoli_Pubblicati, ' ');
    oldFormato  b.Articoli.formato%TYPE;
    narticoli   INTEGER := array_length(articolip, 1);
    newArticolo b.Articoli.id_Articolo%TYPE;
    newrivista  b.riviste.ID_Rivista%TYPE;
    vcheck      INTEGER := 0;
BEGIN
    FOR i IN 1..narticoli
        LOOP
            newArticolo = (SELECT id_Articolo FROM b.Articoli WHERE doi = articolip[i]);
            --Controllo se l'Articolo esiste
            IF NOT EXISTS(SELECT * FROM b.Articoli WHERE doi = articolip[i]) THEN
                RAISE NOTICE 'Articoli {%} non presente', articolip[i];
                vcheck := 1;
                --Controllo se l'Articolo è già presente in una conferenza
            ELSEIF EXISTS(SELECT * FROM b.conferenza c WHERE c.id_articolo = newArticolo) THEN
                RAISE NOTICE 'Articolo {%} già presente in una conferenza', articolip[i];
                vcheck := 2;
                --Controllo se l'Articolo è già presente in una rivista
            ELSEIF EXISTS(SELECT * FROM b.articoliinriviste a WHERE a.id_articolo = newArticolo) THEN
                RAISE NOTICE 'Articolo {%} già presente in una rivista', articolip[i];
                vcheck := 3;
                --Controllo se l'Articolo ha lo stesso formato di tutti gli altri
            ELSEIF (i <> 0) THEN
                oldFormato = (SELECT formato FROM b.Articoli WHERE doi = articolip[i - 1]);
                IF (oldFormato <> (SELECT formato FROM b.Articoli WHERE doi = articolip[i])) THEN
                    RAISE NOTICE 'Articolo {%} ha formato diverso', articolip[i];
                    vcheck := 4;
                END IF;
            END IF;
        END LOOP;


    IF (vcheck = 1) THEN
        RAISE NOTICE 'EVENTO NON INSERITO, UNO O PIU'' ARTICOLI SONO INESISTENTI ';
    ELSIF (vcheck = 2) THEN
        RAISE NOTICE 'EVENTO NON INSERITO, UNO O PIU'' ARTICOLI SONO GIA'' PRESENTI IN UNA CONFERENZA ';
    ELSEIF (vcheck = 3) THEN
        RAISE NOTICE 'EVENTO NON INSERITO, UNO O PIU'' ARTICOLI SONO GIA'' PRESENTI IN UNA RIVISTA ';
    ELSEIF (vcheck = 4) THEN
        RAISE NOTICE 'EVENTO NON INSERITO, UNO O PIU'' ARTICOLI SONO CON FORMATO DIVERSO';
    ELSE
        --Inserisco la rivista
        INSERT INTO b.riviste (nome, issn, argomento, datapubblicazione, responsabile, prezzo)
        VALUES (NEW.nome, NEW.issn, NEW.argomento, NEW.datapubblicazione, NEW.responsabile,
                NEW.prezzo);

        --Recupero l'id della rivista appena inserita
        SELECT id_rivista INTO newrivista FROM b.riviste WHERE nome = NEW.nome AND issn = NEW.issn;

        --Inserisco gli articoli nella rivista
        FOR i IN 1..narticoli
            LOOP
                SELECT id_Articolo INTO newArticolo FROM b.Articoli WHERE doi = articolip[i]; --recupero id Articoli
                INSERT INTO b.articoliinriviste (id_articolo, id_rivista)
                VALUES (newArticolo, newrivista);
            END LOOP;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trig_Riviste
    INSTEAD OF INSERT
    ON b.ins_riviste
    FOR EACH ROW
EXECUTE FUNCTION b.ftrig_riviste();
------------------------------------------------------------------------------------------------------------------------


------------------------------------------------------------------------------------------------------------------------
--Trigger Insert Conferenze
------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE VIEW b.ins_conferenza AS
SELECT nome,
       indirizzo,
       strutturaospitante,
       datainizio,
       datafine,
       responsabile,
       TEXT as Doi_Articoli_Presentati --Più articoli separati da @ (es:DOI1@DOI2)
FROM b.jolly,
     b.evento as e;

CREATE OR REPLACE FUNCTION b.ftrig_conferenza() RETURNS TRIGGER AS
$$
DECLARE
    articolip   text[]  := string_to_array(NEW.Doi_Articoli_Presentati, ' ');
    narticoli   INTEGER := array_length(articolip, 1);
    newArticolo b.Articoli.id_Articolo%TYPE;
    newevento   b.evento.ID_Evento%TYPE;
    vcheck      boolean := true;
    nome        text;
BEGIN
    FOR i IN 1..narticoli
        LOOP
            newArticolo = (SELECT id_Articolo FROM b.Articoli WHERE doi = articolip[i]);
            --Controllo se l'Articolo esiste
            IF NOT EXISTS(SELECT * FROM b.Articoli WHERE doi = articolip[i]) THEN
                vcheck = false;
                RAISE NOTICE 'Articolo {%} non presente', articolip[i];
                --Controllo se l'Articolo è già presente in una rivista
            ELSEIF EXISTS(SELECT * FROM b.articoliinriviste WHERE id_Articolo = newArticolo) THEN
                vcheck = false;
                RAISE NOTICE 'Articolo {%} già presente in una rivista', articolip[i];
            ELSEIF EXISTS(SELECT * FROM b.conferenza WHERE id_Articolo = newArticolo) THEN
                vcheck = false;
                RAISE NOTICE 'Articolo {%} già presente in una conferenza', articolip[i];
            END IF;
        end loop;

    --Gli errori sono scritti in ordine di importanza
    IF NOT vcheck THEN
        RAISE NOTICE 'EVENTO NON INSERITO, ERRORE';
    ELSE --Se tutti gli articoli esistono inserisco l'evento
        nome = NEW.nome;
        RAISE NOTICE 'Inserisco l''evento';
        INSERT INTO b.evento (nome, indirizzo, strutturaospitante, datainizio, datafine,
                              responsabile) --Inserisco l'evento
        VALUES (NEW.nome, NEW.indirizzo, NEW.strutturaospitante, NEW.datainizio, NEW.datafine, NEW.responsabile);
        RAISE NOTICE 'Evento inserito con successo';

        --Recupero l'id dell'evento appena inserito
        newevento = (SELECT id_evento
                     FROM b.evento e
                     WHERE e.nome = NEW.nome
                       AND indirizzo = NEW.indirizzo
                       AND strutturaospitante = NEW.strutturaospitante
                       AND datainizio = NEW.datainizio
                       AND datafine = NEW.datafine
                       AND responsabile = NEW.responsabile);
        RAISE NOTICE 'Evento inserito con successo con id {%}', newevento;

        --Inserisco le conferenze per ogni Articolo
        FOR i IN 1..narticoli
            LOOP
                newArticolo =
                        (SELECT id_Articolo FROM b.Articoli WHERE doi = articolip[i]); --Recupero l'id dell'Articoli
                INSERT INTO b.conferenza (id_articolo, id_evento) VALUES (newArticolo, newevento);
            end loop;
    END IF;
    RETURN NEW;
end
$$ LANGUAGE plpgsql;

CREATE TRIGGER trig_conferenza
    INSTEAD OF INSERT
    ON b.ins_conferenza
    FOR EACH ROW
EXECUTE FUNCTION b.ftrig_conferenza();
------------------------------------------------------------------------------------------------------------------------


------------------------------------------------------------------------------------------------------------------------
--Trigger Insert Libri, Autori e Serie
------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE VIEW b.ins_libri_autore_serie AS
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
    autori         text[]  := string_to_array(NEW.Autorinome_cognome, ' ');
    nautori        INTEGER := array_length(autori, 1);
    autore_nome    b.autore.nome%TYPE;
    autore_cognome b.autore.cognome%TYPE;
    newLibro       b.libri.ID_Libro%TYPE;
    newSerie       b.serie.ID_Serie%TYPE;
    oldFormato     b.libri.formato%TYPE;
BEGIN
    --Verifico che il libri non sia già presente
    IF EXISTS(SELECT * FROM b.libri WHERE titolo = NEW.titolo AND datapubblicazione = NEW.datapubblicazione) THEN
        RAISE NOTICE 'Libri già presente';
    ELSE
        IF EXISTS(SELECT * FROM b.serie WHERE issn = NEW.issn_serie_di_appartenenza) THEN
            RAISE NOTICE 'Serie già presente';

            oldFormato = (SELECT DISTINCT l.formato
                          FROM (b.libri l NATURAL JOIN b.libriinserie ls)
                                   JOIN b.serie s on s.id_serie = ls.id_serie);
            IF (oldFormato <> NEW.formato) THEN
                RAISE NOTICE 'Il formato del libro non è compatibile con la serie, libro non inserito';
                RETURN NEW;
            END IF;
        ELSE
            RAISE NOTICE 'Serie non presente';
            INSERT INTO b.serie(nome, issn) values (NEW.nome_serie_di_appartenenza, NEW.issn_serie_di_appartenenza);
        END IF;
        --Insert Libri
        INSERT INTO b.libri(titolo, ISBN, datapubblicazione, Editore, Genere, Lingua, Formato, Prezzo)
        VALUES (NEW.titolo, NEW.ISBN, NEW.datapubblicazione, NEW.editore, NEW.genere, NEW.lingua,
                New.Formato, NEW.prezzo);
        --Insert Autori
        FOR i IN 1..nautori
            LOOP
                autore_nome := split_part(autori[i], '_', 1);
                autore_cognome := split_part(autori[i], '_', 2);

                --Verifico che l'autore non sia già presente
                IF EXISTS(SELECT * FROM b.autore WHERE nome = autore_nome AND cognome = autore_cognome) THEN
                    RAISE NOTICE 'Autore già presente {%}', autori[i];
                ELSE --Inserisco l'autore
                    INSERT INTO b.autore (nome, cognome) VALUES (autore_nome, autore_cognome);
                END IF;

                --Aggiorno la tabella autorelibro
                INSERT INTO b.autorelibro(id_autore, id_libro)
                SELECT a.id_autore, l.id_libro -- Trasformo l'ISNN in un ID e recupero l'ID dell'autore
                FROM b.autore as a,
                     b.libri as l
                WHERE a.nome = autore_nome
                  AND a.cognome = autore_cognome
                  AND l.titolo = NEW.titolo
                  AND l.datapubblicazione = NEW.datapubblicazione;
            END LOOP;
        --Insert Serie
        newLibro = (SELECT id_libro FROM b.libri WHERE isbn = NEW.isbn);
        newSerie = (SELECT id_serie FROM b.serie WHERE issn = NEW.issn_serie_di_appartenenza);
        INSERT INTO b.libriinserie(id_libro, id_serie) values (newLibro, newSerie);
        RETURN NEW;
    end if;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trig_LibriAutoreSerie
    INSTEAD OF INSERT
    ON b.ins_libri_autore_serie
    FOR EACH ROW
EXECUTE FUNCTION b.ftrig_LibriAutoreSerie();
------------------------------------------------------------------------------------------------------------------------


------------------------------------------------------------------------------------------------------------------------
--Trigger Rimozione Libri
------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION b.ftrig_rimozineLibri() RETURNS trigger AS
$$
DECLARE
    idAutoreLibro b.autore.id_autore%TYPE;
    idAutoriLibri CURSOR FOR (SELECT id_autore
                              FROM b.autorelibro
                              WHERE id_libro = OLD.id_libro);
    idEvento      b.evento.id_evento%TYPE = (SELECT id_evento
                                             FROM b.presentazione
                                             WHERE id_libro = OLD.id_libro);
    idSerie       b.serie.id_serie%TYPE   = (SELECT id_serie
                                             FROM b.libriinserie
                                             WHERE id_libro = OLD.id_libro);
BEGIN
    --Eliminazione Autori se non hanno scritto altro
    OPEN idAutoriLibri;
    LOOP
        FETCH idAutoriLibri INTO idAutoreLibro;
        EXIT WHEN NOT FOUND;
        IF NOT EXISTS(SELECT * FROM b.autorelibro WHERE id_autore = idAutoreLibro AND id_libro <> OLD.id_libro) THEN
            IF NOT EXISTS(SELECT * FROM b.autorearticolo WHERE id_autore = idAutoreLibro) THEN
                DELETE FROM b.autore WHERE id_autore = idAutoreLibro;
            END IF;
        END IF;
    END LOOP;

    --Eliminazione Presentazione ed Evento
    DELETE FROM b.evento WHERE id_evento = idEvento;

    --Eliminazione Serie se non ha altri libri
    IF NOT EXISTS(SELECT * FROM b.libriinserie WHERE id_serie = idSerie AND id_libro <> OLD.id_libro) THEN
        DELETE FROM b.serie WHERE id_serie = idSerie;
    END IF;

    CLOSE idAutoriLibri;
    RETURN NEW;
end;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trig_rimozioneLibri
    BEFORE DELETE
    ON b.libri
    FOR EACH ROW
EXECUTE PROCEDURE b.ftrig_rimozineLibri();
------------------------------------------------------------------------------------------------------------------------


------------------------------------------------------------------------------------------------------------------------
--Trigger Insert Presentazioni
------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE VIEW b.ins_presentazione AS
SELECT l.ISBN,
       e.nome,
       e.Indirizzo,
       e.StrutturaOspitante,
       e.DataInizio,
       e.DataFine,
       e.Responsabile
FROM b.evento as e,
     b.libri as l;

CREATE OR REPLACE FUNCTION b.ftrig_presentazione()
    RETURNS trigger AS
$$
DECLARE
BEGIN
    IF NOT EXISTS(SELECT * FROM b.libri WHERE isbn = NEW.ISBN) THEN --Controllo se il libri esiste
        RAISE NOTICE 'Il libri non esiste!! Presentazione non inserita';
    ELSEIF EXISTS(SELECT *
                  FROM (b.evento as e NATURAL JOIN b.presentazione as p) --Controllo se esiste già una presentazione per quel libri
                           JOIN b.libri as l ON p.id_libro = l.id_libro
                  WHERE ISBN = NEW.ISBN) THEN
        RAISE NOTICE 'Esista già una presentazione per questo libro!! Presentazione non inserita';
    ELSE --Inserisco la presentazione
        INSERT INTO b.evento (nome, indirizzo, strutturaospitante, datainizio, datafine,
                              responsabile) --Inserisco l'¬evento
        VALUES (NEW.nome, NEW.Indirizzo, NEW.StrutturaOspitante, NEW.DataInizio, NEW.DataFine, NEW.Responsabile);
        INSERT INTO b.presentazione (id_evento, id_libro) --Inserisco la presentazione
        SELECT e.ID_evento, l.ID_libro --Trasformo l'ISBN in un ID e recupero l'ID dell'evento
        FROM b.evento e,
             b.libri l
        WHERE l.ISBN = NEW.ISBN
          AND e.nome = NEW.nome
          AND e.indirizzo = NEW.Indirizzo
          AND e.strutturaospitante = NEW.StrutturaOspitante
          AND e.datainizio = NEW.DataInizio
          AND e.datafine = NEW.DataFine
          AND e.responsabile = NEW.Responsabile;
    END IF;
    RETURN NEW;
END
$$
    language plpgsql;

CREATE OR REPLACE TRIGGER trig_presentazione
    INSTEAD OF INSERT
    ON b.ins_presentazione
    FOR EACH ROW
EXECUTE FUNCTION b.ftrig_presentazione();
------------------------------------------------------------------------------------------------------------------------


------------------------------------------------------------------------------------------------------------------------
--Trigger Insert Stock Negozi
------------------------------------------------------------------------------------------------------------------------
CREATE VIEW b.ins_stock_libri AS
SELECT id_negozio,
       isbn,
       quantita
FROM b.libri,
     b.stock;

CREATE OR REPLACE FUNCTION b.ftrig_stocklibri() RETURNS TRIGGER AS
$$
DECLARE
    v_idlibro b.libri.id_libro%TYPE=(SELECT id_libro
                                     FROM b.libri
                                     WHERE isbn = NEW.isbn);
BEGIN
    IF NOT EXISTS(SELECT * FROM b.negozio WHERE id_negozio = NEW.id_negozio) OR --controllo che non esistano sia il negozio
       NOT EXISTS(SELECT * FROM b.libri WHERE id_libro = v_idlibro) THEN --sia il libro
        RAISE NOTICE 'ID Negozio o ISBN errati, dati non inseriti';
    ELSE
        --controllo se esista già una tupla composta da id_negozio e id_libro
        IF EXISTS(SELECT * FROM b.stock WHERE stock.id_negozio = NEW.id_negozio AND stock.id_libro = v_idlibro) THEN
            UPDATE b.stock
            SET quantita=quantita + NEW.quantita
            WHERE id_negozio = NEW.id_negozio
              AND stock.id_libro = v_idlibro;
        ELSE --se non esiste la creo
            INSERT INTO b.stock values (NEW.id_negozio, v_idlibro, NEW.quantita);
        end if;
    END IF;
    RETURN NEW;
END
$$
    LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trig_StockLibri
    INSTEAD OF INSERT
    ON b.ins_stock_Libri
    FOR EACH ROW
EXECUTE FUNCTION b.ftrig_stocklibri();
------------------------------------------------------------------------------------------------------------------------


------------------------------------------------------------------------------------------------------------------------
--Trigger Rimozione da Stock
------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION b.ftrig_RimozioneDaStock() RETURNS trigger AS
$$
DECLARE
BEGIN
    if (NEW.quantita = 0) then
        DELETE FROM b.stock WHERE id_libro = OLD.id_libro;
    end if;
END;
$$
    LANGUAGE plpgsql;

CREATE TRIGGER trig_RimozioneDaStock
    AFTER UPDATE OF quantita
    ON b.stock
    FOR EACH ROW
EXECUTE FUNCTION b.ftrig_RimozioneDaStock();
------------------------------------------------------------------------------------------------------------------------


------------------------------------------------------------------------------------------------------------------------
--View Utili
------------------------------------------------------------------------------------------------------------------------

--View Libri con autore
CREATE VIEW b.view_libri_autore AS
SELECT l.titolo,
       l.isbn,
       l.datapubblicazione,
       l.editore,
       l.genere,
       l.lingua,
       l.formato,
       l.prezzo,
       a.nome,
       a.cognome
FROM (b.libri as l NATURAL JOIN b.autorelibro as al)
         JOIN b.autore as a on al.id_autore = a.id_autore;

--View Libri con Serie
CREATE VIEW b.view_libri_serie AS
SELECT DISTINCT l.titolo,
                l.isbn,
                l.datapubblicazione,
                l.editore,
                l.genere,
                l.lingua,
                l.formato,
                l.prezzo,
                s.nome as nome_serie,
                ISSN
FROM (b.libri as l NATURAL JOIN b.libriinserie as ls)
         JOIN b.serie as s on ls.id_serie = s.id_serie;

--View Articoli con Autore
CREATE VIEW b.view_Articoli_autore AS
SELECT a.titolo,
       a.doi,
       a.datapubblicazione,
       a.disciplina,
       a.editore,
       a.lingua,
       a.formato,
       au.nome,
       au.cognome
FROM (b.Articoli as a NATURAL JOIN b.autoreArticolo as aa)
         JOIN b.autore as au on aa.id_autore = au.id_autore;

--View Articoli con Riviste
CREATE VIEW b.view_articoli_riviste AS
SELECT a.titolo,
       a.doi,
       a.datapubblicazione,
       a.disciplina,
       a.editore,
       a.lingua,
       a.formato,
       r.nome as titolo_riviste,
       r.issn as issn_riviste
FROM (b.Articoli as a NATURAL JOIN b.Articoliinriviste as ar)
         JOIN b.riviste as r on ar.id_rivista = r.id_rivista;

--View Articoli con Confereza
CREATE VIEW b.view_Articoli_conferenze AS
SELECT a.titolo,
       a.doi,
       a.datapubblicazione,
       a.disciplina,
       a.editore,
       a.lingua,
       a.formato,
       e.nome as titolo_conferenza
FROM (b.Articoli as a NATURAL JOIN b.conferenza as c)
         JOIN b.evento as e on c.id_evento = e.id_evento;
------------------------------------------------------------------------------------------------------------------------


------------------------------------------------------------------------------------------------------------------------
--Funzioni Applicativo
------------------------------------------------------------------------------------------------------------------------

--Funzione che restituisce la disponibilità di un libro in un negozio
CREATE OR REPLACE FUNCTION b.getDisponibilita(inputLibro b.libri.id_libro%TYPE) RETURNS boolean AS
$$
DECLARE
BEGIN
    IF EXISTS(SELECT * FROM b.stock s WHERE s.id_libro = inputLibro) THEN
        return true;
    else
        return false;
    end if;
END;
$$
    LANGUAGE plpgsql;


--Funzione che restituisc la disponibilità di una serie in un negozio
CREATE OR REPLACE FUNCTION b.getDisponibilitaSerie(inputSerieISSN b.Serie.ISSN%TYPE) RETURNS boolean AS
$$
DECLARE
    scorrilibri b.libri.id_libro%TYPE;
    cursoreLibri CURSOR FOR (SELECT id_libro
                             FROM (b.libri l NATURAL JOIN b.libriinserie ls)
                                      JOIN b.serie s ON s.id_serie = ls.id_serie
                             WHERE ISSN = inputSerieISSN);
BEGIN
    OPEN cursorelibri;
    LOOP
        FETCH cursoreLibri INTO scorrilibri;
        EXIT WHEN NOT FOUND;
        if NOT b.getDisponibilita(scorrilibri) THEN
            CLOSE cursoreLibri;
            return false;
        end if;
    end loop;
    CLOSE cursoreLibri;
    return true;
end;
$$
    LANGUAGE plpgsql;

--Funzione che restituisce una stringa con i nomi degli autori di un libro
CREATE OR REPLACE FUNCTION b.getAutoriByLibro(inputIdLibro b.libri.id_libro%TYPE) RETURNS TEXT AS
$$
DECLARE
    returnAutori  TEXT;
    cursore CURSOR FOR (SELECT nome, cognome
                        FROM (b.autore a NATURAL JOIN b.autorelibro al)
                                 JOIN b.libri l ON l.id_libro = al.id_libro
                        WHERE l.id_libro = inputIdLibro);
    autoreNome    b.autore.nome%TYPE;
    autoreCognome b.autore.cognome%TYPE;
    controllo     bool= false; --se è a false non sono stati inseriti ancora autori in returnAutori
BEGIN
    OPEN cursore;
    LOOP
        FETCH cursore INTO autoreNome, autoreCognome;
        EXIT WHEN NOT FOUND;
        if controllo IS false THEN
            returnAutori = autoreNome || ' ' || autoreCognome;
            controllo = true;
        else
            returnAutori = returnAutori || ', ' || autoreNome || ' ' || autoreCognome;
        end if;
    END LOOP;
    CLOSE cursore;
    return returnAutori;
END;
$$
    LANGUAGE plpgsql;

--Funzione che restituisce una stringa con i nomi degli autori di un articolo
CREATE OR REPLACE FUNCTION b.getAutoriByArticolo(inputIdArticolo b.articoli.id_articolo%TYPE) RETURNS TEXT AS
$$
DECLARE
    autoreNome    b.autore.nome%TYPE;
    autoreCognome b.autore.cognome%TYPE;
    returnAutori  TEXT;
    controllo     bool= false; --se è a false non sono stati inseriti ancora autori in returnAutori
    cursore cursor for (SELECT nome, cognome
                        FROM (b.autore a NATURAL JOIN b.autorearticolo aa)
                                 JOIN b.articoli ar ON ar.id_articolo = aa.id_articolo
                        WHERE ar.id_articolo = inputIdArticolo);
BEGIN
    open cursore;
    LOOP
        FETCH cursore INTO autoreNome, autoreCognome;
        EXIT WHEN NOT FOUND;
        if controllo IS false THEN
            returnAutori = autoreNome || ' ' || autoreCognome;
            controllo = true;
        else
            returnAutori = returnAutori || ', ' || autoreNome || ' ' || autoreCognome;
        end if;
    END LOOP;
    CLOSE cursore;
    return returnAutori;
end;
$$ LANGUAGE plpgsql;
------------------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------------------
--ResultView Applicativo
------------------------------------------------------------------------------------------------------------------------

--Result View Libri
CREATE VIEW b.resultView_libri AS
SELECT DISTINCT titolo,
                isbn,
                b.getAutoriByLibro(l.id_libro) AS Autori,
                editore,
                genere,
                lingua,
                s.nome AS serie,
                formato,
                prezzo,
                b.getDisponibilita(l.id_libro) AS Disponibilità
FROM (b.libri l FULL OUTER JOIN b.libriinserie lis ON l.id_libro = lis.id_libro) JOIN b.serie S ON lis.id_serie=s.id_serie;

--Result View Articoli
CREATE VIEW b.resultView_articoli AS
SELECT distinct titolo,
                doi,
                b.getAutoriByArticolo(a.id_articolo) AS Autori,
                lingua,
                formato,
                editore,
                disciplina,
                a.datapubblicazione                  as data_pubblicazione
FROM (b.articoli a NATURAL JOIN b.autorearticolo);

--Result View Serie
CREATE VIEW b.resultView_serie AS
SELECT DISTINCT ON (nome_serie) nome_serie as nome, --da valutare se vogliamo fare così, dubito però di base funziona
                issn,
                editore,
                lingua,
                formato,
                datapubblicazione,
                b.getDisponibilitaSerie(issn) AS Disponibilità
FROM b.view_libri_serie;

--Result View Riviste
CREATE VIEW b.resultView_riviste AS
SELECT distinct titolo_riviste as nome,
                issn_riviste as issn,
                disciplina,
                editore,
                lingua,
                formato
FROM b.view_articoli_riviste;
------------------------------------------------------------------------------------------------------------------------
