DROP SCHEMA IF EXISTS b CASCADE;
CREATE SCHEMA b;

CREATE TYPE b.TipoUtente AS ENUM ('0', '1', '2');

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

    CONSTRAINT PK_Riviste PRIMARY KEY (ID_Rivista),
    CONSTRAINT UQ_ISSN UNIQUE (ISSN)
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
    CONSTRAINT UK_Nome_Indirizzo_DataInizio UNIQUE (Nome, Indirizzo, DataInizio)
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
    CONSTRAINT CK_Libri CHECK (Prezzo >= 0),
    CONSTRAINT CK_NOTNULL_Titolo CHECK (Titolo IS NOT NULL),
    CONSTRAINT CK_Titolo CHECK (Titolo <> '')
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
    Permessi  b.TipoUtente DEFAULT '0',

    CONSTRAINT PK_Utente PRIMARY KEY (ID_Utente),
    CONSTRAINT UK_Utente UNIQUE (Username)
);

CREATE TABLE b.Richiesta
(
    ID_Utente SERIAL,
    ID_Serie  SERIAL,

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
--Funzioni e Procedure utili
------------------------------------------------------------------------------------------------------------------------
--Procedura Inserimento Autori
CREATE OR REPLACE PROCEDURE b.insAutori(stringAutori text, idRisorsa INTEGER, tipoRisorsa INTEGER) AS
$$
DECLARE
    autori        text[]  = string_to_array(stringAutori, ',');
    numAutori     INTEGER = array_length(autori, 1);
    autoreNome    b.autore.nome%TYPE;
    autoreCognome b.autore.cognome%TYPE;
    idAutore      b.autore.id_autore%TYPE;
BEGIN
    FOR i IN 1..numAutori
        LOOP
            autoreNome = split_part(autori[i], '_', 1);
            autoreNome = ltrim(autoreNome, ' ');
            autoreCognome = split_part(autori[i], '_', 2);
            autoreCognome = ltrim(autoreCognome, ' ');
            RAISE NOTICE 'autoreNome: % autoreCognome: %', autoreNome, autoreCognome;
            IF NOT EXISTS(SELECT * FROM b.autore WHERE nome = autoreNome AND cognome = autoreCognome) THEN
                RAISE NOTICE 'Autore non presente, verrà inserito';
                INSERT INTO b.autore (nome, cognome) VALUES (autoreNome, autoreCognome);
            END IF;
            idAutore = (SELECT id_autore FROM b.autore WHERE nome = autoreNome AND cognome = autoreCognome);
            IF (tipoRisorsa = 1) THEN
                INSERT INTO b.autorelibro (id_autore, id_libro) VALUES (idAutore, idRisorsa);
            ELSEIF (tipoRisorsa = 0) THEN
                INSERT INTO b.autorearticolo (id_autore, id_articolo) VALUES (idAutore, idRisorsa);
            END IF;
        END LOOP;
END
$$ LANGUAGE plpgsql;

--Funzione che restituisce la disponibilità di un libro in un negozio
CREATE OR REPLACE FUNCTION b.getDisponibilitaLibro(inputLibro b.libri.id_libro%TYPE) RETURNS boolean AS
$$
DECLARE
BEGIN
    IF EXISTS(SELECT * FROM b.stock s WHERE s.id_libro = inputLibro) THEN
        return true;
    ELSE
        return false;
    END IF;
END;
$$ language plpgsql;


--Funzione che restituisc la disponibilità di una serie in un negozio
CREATE OR REPLACE FUNCTION b.getDisponibilitaSerie(inputSerie b.Serie.id_serie%TYPE) RETURNS boolean AS
$$
DECLARE
    scorrilibri b.libri.id_libro%TYPE;
    cursoreLibri CURSOR FOR (SELECT id_libro
                             FROM b.libriinserie
                             WHERE id_serie = inputSerie);
BEGIN
    OPEN cursorelibri;
    LOOP
        FETCH cursoreLibri INTO scorrilibri;
        EXIT WHEN NOT FOUND;
        IF NOT b.getDisponibilitaLibro(scorrilibri) THEN
            CLOSE cursoreLibri;
            return false;
        END IF;
    END LOOP;
    CLOSE cursoreLibri;
    return true;
END;
$$ language plpgsql;
------------------------------------------------------------------------------------------------------------------------


------------------------------------------------------------------------------------------------------------------------
--Trigger per l'inserimento Articoli Scientifici
------------------------------------------------------------------------------------------------------------------------

--Inserimentnto Articolo Scientifico e Rivista dove è stato presentato

--View da dove viene inserito un articolo scientifico e la rivista dove è stato presentato
CREATE OR REPLACE VIEW b.ins_ArticoliRivista AS
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

--Funzione del trigger
CREATE OR REPLACE FUNCTION b.ftrig_ArticoliRivista() RETURNS trigger AS
$$
DECLARE
    idRivista  b.riviste.id_rivista%TYPE;
    idArticolo INTEGER;
BEGIN
    --Controllo che l'articolo non sia già presente nel DataBase
    IF EXISTS(SELECT * FROM b.articoli WHERE doi = NEW.doi) THEN
        RAISE NOTICE 'Articolo già presente, non verrà inserito';
    ELSE
        --Controllo che la rivista non sia già presente nel DataBase in tal caso la inserisco
        IF NOT EXISTS(SELECT * FROM b.riviste WHERE issn = NEW.issnRivista) THEN
            RAISE NOTICE 'Rivista non presente, verrà inserita';
            INSERT INTO b.riviste (nome, issn, argomento, datapubblicazione, responsabile, prezzo)
            VALUES (NEW.nomeRivista, NEW.issnRivista, NEW.argomentoRivista, NEW.datapubblicazione,
                    NEW.responsabileRivista, NEW.prezzoRivista);
            --Controllo che la rivista presente nel database abbia la stessa data di pubblicazione
        ELSEIF NOT EXISTS(SELECT datapubblicazione
                          FROM b.riviste
                          WHERE issn = NEW.issnRivista
                            AND datapubblicazione = NEW.datapubblicazione) THEN
            RAISE NOTICE 'Rivista già presente ma con data di pubblicazione diversa, l''articolo non verrà inserito';
            RETURN NEW;
        END IF;
        --Inserisco l'articolo
        INSERT INTO b.articoli (doi, titolo, datapubblicazione, disciplina, editore, lingua, formato)
        VALUES (NEW.doi, NEW.titolo, NEW.datapubblicazione, NEW.disciplina, NEW.editore, NEW.lingua, NEW.formato);

        --Recupero l'id dell'articolo appena inserito
        idArticolo = (SELECT id_articolo FROM b.articoli WHERE doi = NEW.doi);

        --Inserisco gli autori richiamando la procedura insAutori
        CALL b.insAutori(NEW.AutoriNome_Cognome, idArticolo, 0);

        --Inserisco l'articolo nella rivista
        idRivista = (SELECT id_rivista FROM b.riviste WHERE issn = NEW.issnRivista);
        INSERT INTO b.articoliInRiviste (id_articolo, id_rivista) VALUES (idArticolo, idRivista);
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

--Trigger per l'inserimento di un articolo scientifico e la rivista dove è stato presentato
CREATE OR REPLACE TRIGGER trig_ArticoliRivista
    INSTEAD OF INSERT
    ON b.ins_ArticoliRivista
    FOR EACH ROW
EXECUTE FUNCTION b.ftrig_ArticoliRivista();


--Inserimento Articolo Scientifico e Conferenza dove è stato presentato

--View da dove viene inserito un articolo scientifico e la conferenza dove è stato presentato
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

--Funzione del trigger
CREATE OR REPLACE FUNCTION b.ftrig_ArticoliConferenze() RETURNS TRIGGER AS
$$
DECLARE
    idArticolo   INTEGER;
    idConferenza b.evento.id_evento%TYPE;
BEGIN
    --Controllo che l'articolo non sia già presente nel DataBase
    IF EXISTS(SELECT * FROM b.articoli WHERE doi = NEW.doi) THEN
        RAISE NOTICE 'Articolo già presente, non verrà inserito';
        --Controllo se la data di pubblicazione dell'articolo è compresa tra la data di inizio e la data di fine della conferenza
    ELSEIF (NEW.datapubblicazione < NEW.datainizioConferenza OR NEW.datapubblicazione > NEW.datafineConferenza) THEN
        RAISE NOTICE 'La data di pubblicazione dell''articolo non è compresa tra la data di inizio e la data di fine della conferenza, l''articolo non verrà inserito';
    ELSE
        --Controllo che la conferenza non sia già presente nel DataBase in tal caso la inserisco
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
        --Inserisco l'articolo
        INSERT INTO b.articoli (doi, titolo, datapubblicazione, disciplina, editore, lingua, formato)
        VALUES (NEW.doi, NEW.titolo, NEW.datapubblicazione, NEW.disciplina, NEW.editore, NEW.lingua, NEW.formato);

        --Recupero l'id dell'articolo appena inserito
        idArticolo = (SELECT id_articolo FROM b.articoli WHERE doi = NEW.doi);

        --Inserisco gli autori richiamando la procedura insAutori
        CALL b.insAutori(NEW.AutoriNome_Cognome, idArticolo, 0);

        --Inserisco l'articolo nella conferenza
        idConferenza = (SELECT id_evento
                        FROM b.evento
                        WHERE nome = NEW.nomeConferenza
                          AND indirizzo = NEW.indirizzoConferenza);
        INSERT INTO b.Conferenza (id_articolo, id_evento) VALUES (idArticolo, idConferenza);
    END IF;
    RETURN NEW;
END ;
$$ LANGUAGE plpgsql;

--Trigger per l'inserimento di un articolo scientifico e la conferenza dove è stato presentato
CREATE OR REPLACE TRIGGER trig_ArticoliConferenze
    INSTEAD OF INSERT
    ON b.ins_ArticoliConferenze
    FOR EACH ROW
EXECUTE FUNCTION b.ftrig_ArticoliConferenze();
------------------------------------------------------------------------------------------------------------------------


------------------------------------------------------------------------------------------------------------------------
--Trigger per l'inserimento di un libro
------------------------------------------------------------------------------------------------------------------------

--View da dove viene inserito un libro
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

--Funzione del trigger per l'inserimento di un libro
CREATE OR REPLACE FUNCTION b.ftrig_Libri() RETURNS TRIGGER AS
$$
DECLARE
    idLibro b.libri.ID_Libro%TYPE;
    idSerie b.serie.ID_Serie%TYPE;
BEGIN
    --Controllo che il libro non sia già presente nel DataBase
    IF EXISTS(SELECT * FROM b.libri WHERE isbn = NEW.isbn) THEN
        RAISE NOTICE 'Libro già presente';
    ELSE
        --Controllo che la serie di appartenenza del libro non sia già presente nel DataBase in tal caso la inserisco
        IF NOT EXISTS(SELECT * FROM b.serie WHERE issn = NEW.issn_serie_di_appartenenza) THEN
            RAISE NOTICE 'Serie non presente';
            IF NEW.nome_serie_di_appartenenza IS NOT NULL THEN
                INSERT INTO b.serie(nome, issn) values (NEW.nome_serie_di_appartenenza, NEW.issn_serie_di_appartenenza);
            END IF;
            --Controllo che il formato del libro sia compatibile con la serie già presente nel DataBase
        ELSEIF NOT EXISTS(SELECT *
                          FROM (b.serie s NATURAL JOIN b.libriinserie ls)
                                   JOIN b.libri l ON ls.id_libro = l.id_libro
                          WHERE l.formato = NEW.formato) THEN
            RAISE NOTICE 'Il formato del libro non è compatibile con la serie, libro non inserito';
            RETURN NEW;
        END IF;
        --Inserisco il libro
        INSERT INTO b.libri (titolo, isbn, datapubblicazione, editore, genere, lingua, formato, prezzo)
        VALUES (NEW.titolo, NEW.isbn, NEW.datapubblicazione, NEW.editore, NEW.genere, NEW.lingua, NEW.formato,
                NEW.prezzo);
        --Recupero l'id del libro appena inserito
        idLibro = (SELECT id_libro FROM b.libri WHERE isbn = NEW.isbn);

        --Inserisco gli autori richiamando la procedura insAutori
        CALL b.insAutori(NEW.autoriNome_cognome, idLibro, 1);

        --Inserisco il libro nella serie
        idSerie = (SELECT id_serie FROM b.serie WHERE issn = NEW.issn_serie_di_appartenenza);
        RAISE NOTICE 'idSerie: %', idSerie;
        IF idSerie IS NOT NULL THEN
            INSERT INTO b.libriinserie (id_libro, id_serie) VALUES (idLibro, idSerie);
        END IF;
    END IF;
    RETURN NEW;
END
$$ LANGUAGE plpgsql;

--Trigger per l'inserimento di un libro
CREATE OR REPLACE TRIGGER trig_Libri
    INSTEAD OF INSERT
    ON b.ins_libri
    FOR EACH ROW
EXECUTE FUNCTION b.ftrig_Libri();



--Trigger per l'inserimento della presentazione di un libro nel DataBase
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
--Trigger per la rimozione di una risorsa dal DataBase
------------------------------------------------------------------------------------------------------------------------

--Funzione del trigger per la rimozione di un articolo scientifico
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
    IdConferenza     b.evento.id_evento%TYPE   = (SELECT id_evento
                                                  FROM b.conferenza
                                                  WHERE id_articolo = OLD.id_articolo);
BEGIN
    --Rimuovo gli autori se non hanno scritto altri articoli o libri
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

    --Rimuovo la Rivista se non ha altri articoli
    IF NOT EXISTS(SELECT *
                  FROM b.articoliinriviste
                  WHERE id_articolo <> old.id_articolo
                    AND id_rivista = idRivista) THEN
        DELETE FROM b.riviste WHERE id_rivista = idRivista;
    END IF;

    --Rimuovo Conferenza se non ha altri articoli
    IF NOT EXISTS(SELECT *
                  FROM b.conferenza
                  WHERE id_articolo <> old.id_articolo
                    AND id_evento = IdConferenza) THEN
        DELETE FROM b.evento WHERE id_evento = IdConferenza;
    END IF;

    CLOSE idAutoreArticoli;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

--Trigger per la rimozione di un articolo scientifico
CREATE TRIGGER trig_rimozioneArticoli
    BEFORE DELETE
    ON b.articoli
    FOR EACH ROW
EXECUTE PROCEDURE b.ftrig_rimozineArticoli();


--Funzione del trigger per la rimozione di un libro
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
    --Rimuovo gli autori se non hanno scritto altri articoli o libri
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

    --Rimuovo la presentazione del libro
    DELETE FROM b.evento WHERE id_evento = idEvento;

    --Rimuovo la serie se non ha altri libri
    IF NOT EXISTS(SELECT * FROM b.libriinserie WHERE id_serie = idSerie AND id_libro <> OLD.id_libro) THEN
        DELETE FROM b.serie WHERE id_serie = idSerie;
    END IF;

    CLOSE idAutoriLibri;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

--Trigger per la rimozione di un libro
CREATE TRIGGER trig_rimozioneLibri
    BEFORE DELETE
    ON b.libri
    FOR EACH ROW
EXECUTE PROCEDURE b.ftrig_rimozineLibri();
------------------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------------------
--Trigger per la gestione dello stock di un negozio
------------------------------------------------------------------------------------------------------------------------

--View da dove inserisco i dati per aggiungere un libro allo stock
CREATE VIEW b.ins_stock AS
SELECT id_negozio,
       isbn,
       quantita
FROM b.libri,
     b.stock;

--Funzione del trigger per lo stock di un negozio
CREATE OR REPLACE FUNCTION b.ftrig_stock() RETURNS TRIGGER AS
$$
DECLARE
    idLibro b.libri.id_libro%TYPE = (SELECT id_libro
                                     FROM b.libri
                                     WHERE isbn = NEW.isbn);
BEGIN
    --Controllo se il libro è presente nel database
    IF NOT EXISTS(SELECT * FROM b.libri WHERE isbn = NEW.isbn) THEN
        RAISE NOTICE 'Libro non presente, inserimento non possibile';
        --Controllo se il negozio è presente nel database
    ELSEIF NOT EXISTS(SELECT * FROM b.negozio WHERE id_negozio = NEW.id_negozio) THEN
        RAISE NOTICE 'Negozio non presente, inserimento non possibile';
    ELSE
        --Controllo se il libro non è presente nello stock del negozio ed in tal caso lo inserisco
        IF NOT EXISTS(SELECT * FROM b.stock WHERE id_negozio = NEW.id_negozio AND id_libro = idLibro) THEN
            INSERT INTO b.stock (id_negozio, id_libro, quantita) VALUES (NEW.id_negozio, idLibro, NEW.quantita);
            --Altrimenti aggiorno la quantità del libro nello stock del negozio
        ELSE
            UPDATE b.stock
            SET quantita = quantita + NEW.quantita
            WHERE id_negozio = NEW.id_negozio
              AND id_libro = idLibro;
        END IF;
    END IF;
    RETURN NEW;
END;
$$ language plpgsql;

CREATE OR REPLACE TRIGGER trig_Stock
    INSTEAD OF INSERT
    ON b.ins_stock
    FOR EACH ROW
EXECUTE FUNCTION b.ftrig_stock();

--Funzione del trigger per l'aggiornamento dello stock di un negozio
CREATE OR REPLACE FUNCTION b.ftrig_RimozioneDaStock() RETURNS trigger AS
$$
BEGIN
    --Controllo se la quantità è 0 e in tal caso rimuovo il libro dallo stock
    if (NEW.quantita = 0) then
        DELETE FROM b.stock WHERE id_libro = OLD.id_libro;
    end if;
END;
$$ language plpgsql;

--Trigger per l'aggiornamento dello stock di un negozio
CREATE TRIGGER trig_RimozioneDaStock
    AFTER UPDATE OF quantita
    ON b.stock
    FOR EACH ROW
EXECUTE FUNCTION b.ftrig_RimozioneDaStock();
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
CREATE VIEW b.view_Articoli_riviste AS
SELECT a.titolo,
       a.doi,
       a.datapubblicazione,
       a.disciplina,
       a.editore,
       a.lingua,
       a.formato,
       r.nome as titolo_riviste
FROM (b.Articoli as a NATURAL JOIN b.Articoliinriviste as ar)
         JOIN b.riviste as r on ar.id_rivista = r.id_rivista;

--View Articoli con Confereza
CREATE VIEW b.view_Articoli_conferenza AS
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
--ResultView Applicativo
------------------------------------------------------------------------------------------------------------------------

--Result View Libri
CREATE VIEW b.resultView_libri AS
SELECT DISTINCT titolo,
                isbn,
                b.getAutoriByLibro(l.id_libro) AS Autori,
                dataPubblicazione,
                editore,
                genere,
                lingua,
                s.nome                         AS serie,
                formato,
                prezzo,
                b.getDisponibilita(l.id_libro) AS Disponibilità
FROM (b.libri l FULL OUTER JOIN b.libriinserie lis ON l.id_libro = lis.id_libro)
         FULL JOIN b.serie S ON lis.id_serie = s.id_serie;

--Result View Articoli
CREATE VIEW b.resultView_articoli AS
SELECT distinct titolo,
                doi,
                b.getAutoriByArticolo(a.id_articolo) AS Autori,
                lingua,
                formato,
                editore,
                disciplina,
                a.datapubblicazione
FROM (b.articoli a NATURAL JOIN b.autorearticolo);

--Result View Serie
CREATE VIEW b.resultView_serie AS
SELECT DISTINCT ON (s.issn) s.nome                        as nome, --da valutare se vogliamo fare così, dubito però di base funziona
                            issn,
                            editore,
                            lingua,
                            formato,
                            datapubblicazione,
                            b.getDisponibilitaSerie(issn) AS Disponibilità
FROM (b.libri as l NATURAL JOIN b.libriinserie as ls)
         JOIN b.serie as s on ls.id_serie = s.id_serie;

--Result View Riviste
CREATE VIEW b.resultView_riviste AS
SELECT distinct r.nome as nome,
                r.issn as issn,
                disciplina,
                editore,
                lingua,
                formato,
                r.datapubblicazione
FROM (b.Articoli as a NATURAL JOIN b.Articoliinriviste as ar)
         JOIN b.riviste as r on ar.id_rivista = r.id_rivista;
------------------------------------------------------------------------------------------------------------------------



CREATE OR REPLACE FUNCTION b.getNegoziConSerie(id_serieIn b.serie.id_serie%TYPE) RETURNS VARCHAR AS
$$
DECLARE
    nomi_negozi  VARCHAR := '';
    cursore CURSOR FOR (SELECT ne.nome
                        FROM (b.serie s JOIN b.libriinserie ls ON ls.id_serie = s.id_serie)
                                 JOIN (b.stock st JOIN b.negozio ne ON st.id_negozio = ne.id_negozio)
                                      ON st.id_libro = ls.id_libro
                        WHERE s.id_serie = id_SerieIn);
    n_negozi     INTEGER=(SELECT COUNT(*)
                          FROM (b.serie s JOIN b.libriinserie ls ON ls.id_serie = s.id_serie)
                                   JOIN b.stock st ON st.id_libro = ls.id_libro
                          WHERE s.id_serie = id_SerieIn);
    nome_negozio b.negozio.nome%TYPE;

BEGIN
    OPEN cursore;
    FOR i IN 1..n_negozi
        LOOP
            FETCH cursore INTO nome_negozio;
            if (i <> n_negozi) then
                nomi_negozi = nomi_negozi || nome_negozio || ', ';
            else
                nomi_negozi = nomi_negozi || nome_negozio;
            end if;
        end loop;
    return nomi_negozi;
    CLOSE cursore;
end;
$$
    LANGUAGE plpgsql;

--view di serie e libri stock
SELECT id_serie, se.nome, se.issn, l.titolo, st.id_negozio, st.quantita
FROM b.libriinserie ls
         NATURAL JOIN b.serie se
         NATURAL JOIN b.libri l
         NATURAL JOIN b.stock st;


CREATE OR REPLACE FUNCTION b.getIDSerieByISSN(issnIn b.serie.issn%TYPE) RETURNS b.serie.id_serie%TYPE AS
$$
DECLARE
    id b.serie.id_serie%TYPE;
BEGIN
    RAISE NOTICE 'ISSN(%)', issnIn;
    id = (SELECT id_serie
          FROM b.serie se
          WHERE se.issn = issnIn);
    RAISE NOTICE 'ID{%}', id;
    RETURN id;
end;
$$
    LANGUAGE plpgsql;


--serie disponibili in generale
SELECT rs.nome, rs.formato, b.getdisponibilitaSerie(b.getIDSerieByISSN(issn)) as disponibilita_serie
FROM b.resultview_serie as rs
WHERE b.getdisponibilitaSerie(b.getIDSerieByISSN(issn)) IS TRUE;


SELECT b.getnegoziconserie(b.getIDSeriebyissn('983-533158791'));



CREATE VIEW b.notifiche AS
SELECT nome, b.getNegoziConSerie(b.getIDSerieByISSN(issn)) as Disponibile_in, issn, username
FROM b.serie s JOIN b.richiesta r ON s.id_serie=r.id_serie JOIN b.utente u ON u.id_utente=r.id_utente
WHERE b.getDisponibilitaSerie(r.id_serie) IS true;

CREATE VIEW b.resultview_notifiche AS
SELECT nome, Disponibile_in FROM b.notifiche;

--CREATE VIEW b.notifiche AS
--SELECT *, b.getDisponibilitaSerie(id_serie) AS disponibilita
--FROM b.serie
--         NATURAL JOIN b.richiesta
--WHERE b.getDisponibilitaSerie(id_serie) IS true;


