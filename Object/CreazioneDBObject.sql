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
    CLOSE cursore;
    return nomi_negozi;
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


--Inserimenti
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('La scoperta della vita', '10.9895/8702.2737', 'Edoardo_DeVito', '1991-12-23', 'Fisica', 'BMJ', 'Spagnolo', 'Ebook', 'Cell', '978-615579719', 'Fisica', 'Vincenzo_Tecchia', '20.0');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('La scoperta della vita', '10.9128/2628.6079', 'Matteo_Penna', '2023-12-15', 'Medicina', 'ScienceDirect', 'Inglese', 'Audio', 'Conferenza di Medicina', 'Via Napoli 5', '2023-12-15', '2023-12-15', 'Piazza Palermo', 'Mario_Barra');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('Ricerca della fisica', '10.8331/2591.2513', 'Vincenzo_Abate', '2018-6-12', 'Biologia', 'Wiley', 'Spagnolo', 'Cartaceo', 'BMC Medicine', '989-549202011', 'Biologia', 'Nicola_DiPietro', '18.0');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('La politica dell''informatica', '10.3904/6686.5986', 'Giuseppe_DiNapoli', '2014-1-2', 'Matematica', 'Nature Publishing Group', 'Tedesco', 'Ebook', 'JAMA', '979-961481387', 'Matematica', 'Filippo_Parente', '20.0');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('Studio dell''universo', '10.6421/9460.5921', 'Valerio_DeSantis', '2019-12-15', 'Economia', 'Oxford University Press', 'Inglese', 'Ebook', 'Conferenza di Economia', 'Via Torino 2', '2019-12-15', '2019-12-17', 'Piazza Torino', 'Umberto_DiRienzo');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('L''innovazione della chimica', '10.6700/1129.7037', 'Giuseppe_Penna', '1985-5-15', 'Economia', 'Cambridge University Press', 'Italiano', 'Ebook', 'Proceedings of the National Academy of Sciences', '994-374210491', 'Economia', 'Daniele_DiNapoli', '14.0');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('Studio della fisica', '10.8574/3039.9421', 'Alessandro_Ciambriello', '2010-5-22', 'Politica', 'Elsevier', 'Italiano', 'Audio', 'Conferenza di Politica', 'Via Roma 4', '2010-5-22', '2010-5-23', 'Sala Torino', 'Giovanni_DiBenedetto');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('Ricerca della medicina', '10.4138/7117.5372', 'Mauro_Pezzella', '2022-4-4', 'Politica', 'Springer Nature', 'Tedesco', 'Audio', 'Journal of Clinical Oncology', '998-682977791', 'Politica', 'Angelo_Santi', '8.0');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('Ricerca della terra', '10.4132/5070.3259', 'Nicola_Santi', '2016-2-12', 'Informatica', 'BMJ', 'Spagnolo', 'Audio', 'Conferenza di Informatica', 'Via Torino 1', '2016-2-12', '2016-2-14', 'Sala Genova', 'Giuliano_DiCarlo');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('L''innovazione della terra', '10.9492/6165.6674', 'Daniele_Tecchia', '2001-9-6', 'Letteratura', 'Wiley', 'Inglese', 'Ebook', 'Conferenza di Letteratura', 'Via Palermo 1', '2001-9-6', '2001-9-8', 'Sala Firenze', 'Roberto_Tramontana');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('L''innovazione dell''universo', '10.3539/8979.5481', 'Matteo_DiGiacomo', '1962-3-8', 'Fisica', 'Wiley', 'Francese', 'Ebook', 'The Lancet', '979-644681629', 'Fisica', 'Davide_DiNapoli', '3.0');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('Ricerca della medicina', '10.6473/9932.6049', 'Giuliano_DiBenedetto', '1961-8-4', 'Economia', 'Springer Nature', 'Inglese', 'Audio', 'The New England Journal of Medicine', '977-877022183', 'Economia', 'Mirko_DiPietro', '13.0');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('La scienza  della terra', '10.1975/2610.4686', 'Luca_DiLorenzo', '1973-6-14', 'Fisica', 'Oxford University Press', 'Inglese', 'Ebook', 'Nature', '997-596597657', 'Fisica', 'Vincenzo_DiLorenzo', '5.0');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('Studio dell''universo', '10.6718/3604.4398', 'Raffaele_Ciambriello', '1972-9-16', 'Sociologia', 'Oxford University Press', 'Spagnolo', 'Cartaceo', 'Conferenza di Sociologia', 'Via Torino 8', '1972-9-16', '1972-9-18', 'Sala Roma', 'Pierluigi_Pezzella');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('Studio della terra', '10.1518/4947.2138', 'Jhon_Santi', '1980-7-18', 'Filosofia', 'Taylor & Francis', 'Tedesco', 'Audio', 'Science', '984-746244353', 'Filosofia', 'Giuliano_DiPietro', '14.0');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('La politica della biologia', '10.1139/5996.4095', 'Filippo_Abate', '2006-2-21', 'Storia', 'Nature Publishing Group', 'Italiano', 'Cartaceo', 'Conferenza di Storia', 'Via Firenze 8', '2006-2-21', '2006-2-21', 'Palazzetto Venezia', 'Alessandro_DeVito');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('La scoperta della biologia', '10.3386/9830.9585', 'Francesco_Tecchia', '2022-3-9', 'Economia', 'Elsevier', 'Francese', 'Cartaceo', 'Conferenza di Economia', 'Via Milano 3', '2022-3-9', '2022-3-9', 'Sala Bari', 'Jhon_DiCarlo');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('La scienza  della terra', '10.1527/8685.5473', 'Angelo_Esposito', '1959-10-23', 'Informatica', 'Elsevier', 'Francese', 'Cartaceo', 'Conferenza di Informatica', 'Via Genova 1', '1959-10-23', '1959-10-23', 'Palazzetto Napoli', 'Davide_Esposito');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('La scienza  della chimica', '10.5762/2414.5714', 'Davide_DeSimone', '2013-6-13', 'Politica', 'Springer Nature', 'Italiano', 'Audio', 'The New England Journal of Medicine', '977-832800325', 'Politica', 'Vincenzo_Ciambriello', '22.0');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('Il futurodella chimica', '10.7577/4610.8213', 'Alessandro_Ciambriello', '1953-9-13', 'Biologia', 'Cambridge University Press', 'Francese', 'Ebook', 'Nature', '997-564149808', 'Biologia', 'Mario_DiNapoli', '24.0');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('L''innovazione della medicina', '10.3644/4569.5949', 'Davide_DiRienzo', '2016-4-10', 'Informatica', 'ScienceDirect', 'Tedesco', 'Ebook', 'Conferenza di Informatica', 'Via Bari 3', '2016-4-10', '2016-4-10', 'Palazzetto Firenze', 'Giacomo_DeGregorio');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('Il futurodella fisica', '10.9635/3551.2628', 'Mattia_DiSalvo', '2009-1-9', 'Fisica', 'Nature Publishing Group', 'Tedesco', 'Cartaceo', 'Proceedings of the National Academy of Sciences', '989-540019803', 'Fisica', 'Matteo_Penna', '15.0');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('Studio della terra', '10.8227/8445.5817', 'Umberto_Tecchia', '1959-2-21', 'Filosofia', 'ScienceDirect', 'Tedesco', 'Cartaceo', 'Conferenza di Filosofia', 'Via Torino 10', '1959-2-21', '1959-2-23', 'Hotel Bari', 'Filippo_Esposito');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('Ricerca della terra', '10.9308/2180.6835', 'Andrea_Pezzella', '1959-8-24', 'Informatica', 'Taylor & Francis', 'Inglese', 'Ebook', 'The Lancet', '978-494984735', 'Informatica', 'Antonio_DiMauro', '10.0');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('Studio della terra', '10.7445/3202.4709', 'Daniele_DiNapoli', '1986-6-20', 'Chimica', 'ScienceDirect', 'Francese', 'Ebook', 'Conferenza di Chimica', 'Via Bari 6', '1986-6-20', '1986-6-22', 'Palazzetto Firenze', 'Pierluigi_DiGiacomo');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('L''innovazione della chimica', '10.6451/9091.9677', 'Raffaele_Penna', '1972-10-14', 'Matematica', 'Sage Publishing', 'Spagnolo', 'Audio', 'Conferenza di Matematica', 'Via Palermo 10', '1972-10-14', '1972-10-14', 'Hotel Napoli', 'Nicola_Pezzella');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('L''innovazione della vita', '10.7372/9412.7039', 'Luigi_Tecchia', '1994-3-11', 'Medicina', 'Wiley', 'Spagnolo', 'Ebook', 'Conferenza di Medicina', 'Via Napoli 9', '1994-3-11', '1994-3-12', 'Hotel Napoli', 'Mirko_Ciambriello');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('Studio della medicina', '10.8457/7526.9797', 'Marco_DiRienzo', '1997-6-2', 'Chimica', 'Elsevier', 'Spagnolo', 'Ebook', 'BMC Medicine', '995-805222770', 'Chimica', 'Valerio_Parente', '5.0');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('L''innovazione dell''universo', '10.7733/4526.5356', 'Lorenzo_DiCarlo', '1977-5-6', 'Politica', 'Taylor & Francis', 'Italiano', 'Cartaceo', 'Conferenza di Politica', 'Via Bari 2', '1977-5-6', '1977-5-7', 'Hotel Palermo', 'Vincenzo_DeGregorio');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('Ricerca dell''informatica', '10.2288/3503.6160', 'Francesco_DiPaolo', '1989-5-4', 'Letteratura', 'Taylor & Francis', 'Italiano', 'Cartaceo', 'Conferenza di Letteratura', 'Via Bari 5', '1989-5-4', '1989-5-4', 'Hotel Venezia', 'Lorenzo_DeVito');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('Studio della terra', '10.4196/4196.4591', 'Francesco_DeGregorio', '1978-3-22', 'Storia', 'Elsevier', 'Francese', 'Audio', 'PLOS ONE', '993-322203813', 'Storia', 'Emanuele_DeGregorio', '15.0');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('Il futurodella medicina', '10.8471/3901.5154', 'Diego_Barra', '2005-12-18', 'Politica', 'Sage Publishing', 'Inglese', 'Cartaceo', 'BMC Medicine', '984-147746394', 'Politica', 'Filippo_Pezzella', '5.0');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('Ricerca della medicina', '10.3377/9291.1951', 'Matteo_Abate', '2009-3-14', 'Biologia', 'Elsevier', 'Francese', 'Cartaceo', 'Conferenza di Biologia', 'Via Venezia 6', '2009-3-14', '2009-3-15', 'Piazza Roma', 'Raffaele_Santi');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('Ricerca della chimica', '10.3682/2106.2410', 'Luigi_DiNapoli', '2008-6-3', 'Medicina', 'Cambridge University Press', 'Tedesco', 'Cartaceo', 'PLOS ONE', '988-006020075', 'Medicina', 'Giovanni_DeGregorio', '7.0');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('Studio della terra', '10.1945/2516.6279', 'Andrea_DiBenedetto', '1973-10-2', 'Informatica', 'Nature Publishing Group', 'Inglese', 'Audio', 'Nature', '998-302120794', 'Informatica', 'Diego_Esposito', '25.0');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('La politica dell''universo', '10.7548/9835.7683', 'Giacomo_DiBenedetto', '1986-6-13', 'Psicologia', 'Oxford University Press', 'Tedesco', 'Cartaceo', 'Conferenza di Psicologia', 'Via Torino 10', '1986-6-13', '1986-6-13', 'Sala Venezia', 'Pierluigi_DiPaolo');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('La scoperta della biologia', '10.9158/1635.3679', 'Silvio_Parente', '1953-11-4', 'Medicina', 'Sage Publishing', 'Spagnolo', 'Ebook', 'BMC Medicine', '984-130341216', 'Medicina', 'Pierpaolo_DiMauro', '3.0');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('La politica della fisica', '10.6842/3142.5229', 'Mattia_Tramontana', '2023-11-1', 'Letteratura', 'Elsevier', 'Tedesco', 'Audio', 'Conferenza di Letteratura', 'Via Milano 9', '2023-11-1', '2023-11-3', 'Piazza Genova', 'Massimo_Penna');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('Il futurodell''informatica', '10.7592/7269.2147', 'Andrea_DiPaolo', '1959-7-5', 'Filosofia', 'Wiley', 'Spagnolo', 'Audio', 'Conferenza di Filosofia', 'Via Milano 8', '1959-7-5', '1959-7-6', 'Hotel Bologna', 'Marco_Penna');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('La scienza  della medicina', '10.7915/1583.3212', 'Giulio_Pezzella', '1984-8-9', 'Medicina', 'Nature Publishing Group', 'Francese', 'Cartaceo', 'Nature', '990-119558613', 'Medicina', 'Stefano_Pezzella', '15.0');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('La scoperta della medicina', '10.2357/8644.8080', 'Lorenzo_Parente', '1993-12-16', 'Storia', 'Elsevier', 'Tedesco', 'Audio', 'Cell', '983-897151723', 'Storia', 'Nicola_DeVito', '8.0');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('Ricerca della fisica', '10.3919/1280.3014', 'Giovanni_DiNapoli', '1974-7-11', 'Informatica', 'ScienceDirect', 'Inglese', 'Audio', 'BMC Medicine', '998-035107984', 'Informatica', 'Vincenzo_DiNapoli', '4.0');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('La politica della biologia', '10.9384/6386.1742', 'Riccardo_DiNapoli', '1977-9-5', 'Letteratura', 'Oxford University Press', 'Inglese', 'Cartaceo', 'JAMA', '992-374042403', 'Letteratura', 'Giuseppe_DiRienzo', '12.0');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('La scienza  della biologia', '10.1256/3292.3368', 'Mattia_Pezzella', '2017-1-4', 'Matematica', 'BMJ', 'Spagnolo', 'Ebook', 'Conferenza di Matematica', 'Via Roma 1', '2017-1-4', '2017-1-5', 'Piazza Bologna', 'Mattia_DeGregorio');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('La scienza  della medicina', '10.6785/9824.8127', 'Gabriele_Tramontana', '2019-5-16', 'Matematica', 'Springer Nature', 'Italiano', 'Audio', 'Conferenza di Matematica', 'Via Bologna 8', '2019-5-16', '2019-5-17', 'Sala Bologna', 'Jhon_Pezzella');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('La politica dell''informatica', '10.4428/3061.6580', 'Riccardo_Abate', '1991-7-9', 'Filosofia', 'ScienceDirect', 'Spagnolo', 'Ebook', 'Conferenza di Filosofia', 'Via Milano 9', '1991-7-9', '1991-7-11', 'Hotel Firenze', 'Diego_DeSimone');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('La scienza  della chimica', '10.9777/4698.6050', 'Mario_DiGiacomo', '1996-10-11', 'Fisica', 'Nature Publishing Group', 'Spagnolo', 'Cartaceo', 'Conferenza di Fisica', 'Via Genova 8', '1996-10-11', '1996-10-13', 'Hotel Firenze', 'Giuseppe_Tramontana');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('La scoperta dell''universo', '10.8416/8416.4669', 'Giovanni_DiRienzo', '1998-9-5', 'Biologia', 'Sage Publishing', 'Spagnolo', 'Cartaceo', 'Conferenza di Biologia', 'Via Bari 2', '1998-9-5', '1998-9-5', 'Piazza Napoli', 'Daniele_DiPaolo');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('Ricerca della fisica', '10.1764/2782.9733', 'Nicola_Ciambriello', '1980-6-18', 'Fisica', 'Cambridge University Press', 'Tedesco', 'Cartaceo', 'Conferenza di Fisica', 'Via Bari 6', '1980-6-18', '1980-6-19', 'Hotel Torino', 'Francesco_Tecchia');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('Studio della medicina', '10.5114/8277.8860', 'Nicola_Santi', '1959-11-11', 'Economia', 'BMJ', 'Inglese', 'Ebook', 'Journal of Clinical Oncology', '994-304550246', 'Economia', 'Giuseppe_DiGiacomo', '19.0');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('Il futurodella terra', '10.5706/9152.5760', 'Alessio_DiLorenzo', '2022-2-21', 'Biologia', 'Springer Nature', 'Inglese', 'Audio', 'Conferenza di Biologia', 'Via Venezia 3', '2022-2-21', '2022-2-21', 'Palazzetto Torino', 'Carlo_Ciambriello');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('La politica dell''universo', '10.2673/1449.5884', 'Umberto_Abate', '1959-2-11', 'Economia', 'Elsevier', 'Italiano', 'Ebook', 'PLOS ONE', '992-453766266', 'Economia', 'Davide_Penna', '6.0');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('La scienza  della fisica', '10.9481/9322.1353', 'Massimo_DeVito', '1953-10-13', 'Psicologia', 'Oxford University Press', 'Francese', 'Cartaceo', 'Cell', '990-019295280', 'Psicologia', 'Valerio_Pezzella', '6.0');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('Studio dell''informatica', '10.2277/9342.3226', 'Giulio_DeLuca', '1964-1-17', 'Matematica', 'Springer Nature', 'Italiano', 'Cartaceo', 'The New England Journal of Medicine', '995-383571779', 'Matematica', 'Angelo_DiNapoli', '21.0');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('La politica della vita', '10.9348/5658.2022', 'Marco_Parente', '2012-2-2', 'Storia', 'Elsevier', 'Francese', 'Audio', 'Cell', '998-517562099', 'Storia', 'Emanuele_DeGregorio', '3.0');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('Ricerca dell''informatica', '10.7348/8557.4543', 'Luigi_Tramontana', '1980-11-20', 'Biologia', 'BMJ', 'Tedesco', 'Ebook', 'Conferenza di Biologia', 'Via Bari 1', '1980-11-20', '1980-11-21', 'Piazza Genova', 'Marco_DeSantis');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('Il futurodella terra', '10.9856/2159.9354', 'Luigi_Barra', '1979-6-21', 'Informatica', 'Wiley', 'Italiano', 'Ebook', 'Conferenza di Informatica', 'Via Palermo 7', '1979-6-21', '1979-6-22', 'Hotel Palermo', 'Filippo_Pezzella');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('Il futurodella medicina', '10.7922/5233.5668', 'Mattia_DeSimone', '1973-5-10', 'Informatica', 'Elsevier', 'Italiano', 'Cartaceo', 'The New England Journal of Medicine', '977-911304988', 'Informatica', 'Giuseppe_Tecchia', '25.0');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('L''innovazione dell''informatica', '10.7515/3250.1888', 'Alessio_DiGiacomo', '1991-3-20', 'Informatica', 'Taylor & Francis', 'Francese', 'Audio', 'The Lancet', '987-430328891', 'Informatica', 'Mirko_DiMauro', '7.0');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('Il futurodella fisica', '10.9783/1099.4039', 'Daniele_DeSantis', '1956-6-13', 'Politica', 'ScienceDirect', 'Francese', 'Audio', 'Conferenza di Politica', 'Via Firenze 4', '1956-6-13', '1956-6-13', 'Hotel Genova', 'Mario_DeVito');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('La politica della terra', '10.1486/1572.1980', 'Stefano_Parente', '1982-11-20', 'Psicologia', 'Taylor & Francis', 'Italiano', 'Cartaceo', 'Proceedings of the National Academy of Sciences', '987-205130905', 'Psicologia', 'Stefano_DiMauro', '21.0');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('La politica della fisica', '10.3963/2830.3983', 'Giuseppe_Ciambriello', '1964-11-1', 'Sociologia', 'Nature Publishing Group', 'Tedesco', 'Cartaceo', 'The New England Journal of Medicine', '995-115798517', 'Sociologia', 'Giacomo_Abate', '3.0');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('La scoperta della medicina', '10.6381/3260.8579', 'Umberto_Penna', '1999-4-1', 'Matematica', 'Sage Publishing', 'Spagnolo', 'Ebook', 'PLOS ONE', '982-031317658', 'Matematica', 'Francesco_DiRienzo', '19.0');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('Studio dell''universo', '10.6518/9206.3321', 'Carlo_DiCarlo', '2004-5-19', 'Storia', 'Sage Publishing', 'Italiano', 'Audio', 'Journal of Clinical Oncology', '982-255903305', 'Storia', 'Pierluigi_DeLuca', '7.0');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('La politica della terra', '10.9852/3273.8385', 'Diego_DiCarlo', '2014-1-23', 'Biologia', 'Elsevier', 'Francese', 'Audio', 'Conferenza di Biologia', 'Via Napoli 1', '2014-1-23', '2014-1-25', 'Sala Torino', 'Vincenzo_Esposito');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('Studio della vita', '10.8651/2521.1385', 'Marco_Penna', '1989-7-23', 'Filosofia', 'Springer Nature', 'Italiano', 'Audio', 'Proceedings of the National Academy of Sciences', '990-238065073', 'Filosofia', 'Paolo_DeSantis', '8.0');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('La scienza  dell''informatica', '10.4143/7732.7124', 'Mauro_DiMarco', '1981-1-10', 'Biologia', 'Oxford University Press', 'Inglese', 'Ebook', 'Science', '995-597333205', 'Biologia', 'Paolo_DeSantis', '19.0');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('Studio della fisica', '10.5591/2454.8939', 'Antonio_DeSimone', '2001-8-4', 'Storia', 'Taylor & Francis', 'Inglese', 'Ebook', 'Conferenza di Storia', 'Via Milano 8', '2001-8-4', '2001-8-4', 'Palazzetto Bologna', 'Pierluigi_DiNapoli');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('La scienza  della biologia', '10.9384/7433.8502', 'Giacomo_DiRienzo', '1980-4-5', 'Letteratura', 'Cambridge University Press', 'Spagnolo', 'Audio', 'Nature', '992-691831893', 'Letteratura', 'Davide_DiNapoli', '19.0');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('Studio della terra', '10.2327/3062.2178', 'Francesco_Tecchia', '1984-12-11', 'Biologia', 'Nature Publishing Group', 'Inglese', 'Ebook', 'Conferenza di Biologia', 'Via Roma 9', '1984-12-11', '1984-12-12', 'Sala Napoli', 'Giovanni_DiPietro');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('Studio della terra', '10.1653/2150.6314', 'Giulio_Penna', '1998-3-20', 'Storia', 'ScienceDirect', 'Italiano', 'Ebook', 'Conferenza di Storia', 'Via Genova 9', '1998-3-20', '1998-3-20', 'Piazza Venezia', 'Andrea_Barra');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('La scienza  della biologia', '10.4287/1155.3970', 'Umberto_DeGregorio', '1975-2-12', 'Medicina', 'Nature Publishing Group', 'Tedesco', 'Ebook', 'Conferenza di Medicina', 'Via Genova 6', '1975-2-12', '1975-2-14', 'Palazzetto Venezia', 'Simone_DiCarlo');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('Il futurodella medicina', '10.6109/4409.8834', 'Angelo_DiMarco', '1998-9-17', 'Economia', 'Taylor & Francis', 'Inglese', 'Audio', 'Conferenza di Economia', 'Via Bologna 6', '1998-9-17', '1998-9-18', 'Sala Milano', 'Vincenzo_DiSalvo');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('Studio dell''informatica', '10.1957/8632.6846', 'Giulio_DeVito', '1998-8-22', 'Economia', 'Springer Nature', 'Inglese', 'Audio', 'JAMA', '994-550834489', 'Economia', 'Giuliano_DiBenedetto', '20.0');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('La scienza  della chimica', '10.8779/3378.1103', 'Luca_DiPaolo', '2013-10-1', 'Storia', 'Sage Publishing', 'Tedesco', 'Audio', 'Conferenza di Storia', 'Via Bologna 8', '2013-10-1', '2013-10-3', 'Piazza Bari', 'Davide_DeVito');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('La scoperta dell''informatica', '10.7726/6902.7744', 'Giuseppe_DeVito', '1984-3-2', 'Chimica', 'Cambridge University Press', 'Francese', 'Audio', 'Conferenza di Chimica', 'Via Milano 7', '1984-3-2', '1984-3-4', 'Sala Torino', 'Paolo_Penna');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('La scoperta dell''informatica', '10.8071/5480.5665', 'Roberto_DeVito', '2011-10-14', 'Fisica', 'BMJ', 'Inglese', 'Cartaceo', 'Conferenza di Fisica', 'Via Milano 2', '2011-10-14', '2011-10-15', 'Palazzetto Palermo', 'Giulio_DeGregorio');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('La scienza  della fisica', '10.5110/1249.1547', 'Giuseppe_DiLorenzo', '1980-1-1', 'Filosofia', 'Oxford University Press', 'Italiano', 'Audio', 'Conferenza di Filosofia', 'Via Bologna 10', '1980-1-1', '1980-1-2', 'Palazzetto Napoli', 'Simone_DiPaolo');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('Studio della vita', '10.6725/9308.5418', 'Giuseppe_Pezzella', '2016-8-24', 'Informatica', 'Wiley', 'Spagnolo', 'Ebook', 'Conferenza di Informatica', 'Via Roma 3', '2016-8-24', '2016-8-24', 'Hotel Firenze', 'Federico_DeLuca');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('Studio dell''universo', '10.3504/4456.8549', 'Giulio_Penna', '1998-9-4', 'Storia', 'Elsevier', 'Tedesco', 'Ebook', 'Conferenza di Storia', 'Via Venezia 2', '1998-9-4', '1998-9-5', 'Hotel Torino', 'Davide_Pezzella');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('La scienza  della terra', '10.7053/3928.2178', 'Alessandro_Santi', '2018-6-12', 'Chimica', 'Cambridge University Press', 'Tedesco', 'Audio', 'The Lancet', '981-114171535', 'Chimica', 'Federico_Penna', '11.0');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('Ricerca della biologia', '10.7842/2544.8374', 'Federico_DiMarco', '2022-7-20', 'Biologia', 'Taylor & Francis', 'Spagnolo', 'Audio', 'Conferenza di Biologia', 'Via Bologna 3', '2022-7-20', '2022-7-21', 'Palazzetto Firenze', 'Giuliano_DiMauro');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('La politica dell''informatica', '10.7886/8784.5013', 'Mattia_DeLuca', '1988-4-13', 'Psicologia', 'ScienceDirect', 'Spagnolo', 'Audio', 'Journal of Clinical Oncology', '996-027300737', 'Psicologia', 'Giovanni_DiLorenzo', '24.0');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('Il futurodella biologia', '10.9700/6372.2010', 'Luca_DiNapoli', '2001-6-9', 'Storia', 'Taylor & Francis', 'Tedesco', 'Ebook', 'Conferenza di Storia', 'Via Bari 1', '2001-6-9', '2001-6-10', 'Piazza Bari', 'Jhon_DiPietro');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('Studio della biologia', '10.1459/6571.1411', 'Francesco_Penna', '2016-2-16', 'Medicina', 'Cambridge University Press', 'Francese', 'Audio', 'JAMA', '981-410489418', 'Medicina', 'Dario_DiRienzo', '24.0');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('La politica dell''universo', '10.1833/4404.4955', 'Silvio_DiCarlo', '1984-7-15', 'Filosofia', 'Cambridge University Press', 'Italiano', 'Ebook', 'Journal of Clinical Oncology', '983-924851143', 'Filosofia', 'Matteo_Penna', '7.0');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('Studio dell''universo', '10.2463/4613.7048', 'Stefano_Parente', '2022-3-19', 'Matematica', 'Sage Publishing', 'Inglese', 'Cartaceo', 'Nature', '982-335467972', 'Matematica', 'Antonio_Santi', '7.0');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('L''innovazione della vita', '10.6830/3013.2151', 'Carlo_DiRienzo', '1958-7-3', 'Politica', 'Cambridge University Press', 'Italiano', 'Ebook', 'JAMA', '999-917866789', 'Politica', 'Davide_DiLorenzo', '10.0');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('Studio della vita', '10.4845/1222.1355', 'Antonio_Tramontana', '2011-3-4', 'Economia', 'ScienceDirect', 'Tedesco', 'Audio', 'Conferenza di Economia', 'Via Palermo 5', '2011-3-4', '2011-3-5', 'Piazza Firenze', 'Andrea_DeVito');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('Ricerca dell''universo', '10.5249/1405.4396', 'Luigi_Penna', '1983-9-25', 'Biologia', 'Elsevier', 'Francese', 'Ebook', 'Science', '986-973373460', 'Biologia', 'Carlo_DiSalvo', '15.0');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('L''innovazione dell''universo', '10.9951/9459.5537', 'Paolo_Ciambriello', '2013-10-11', 'Matematica', 'Sage Publishing', 'Tedesco', 'Audio', 'Conferenza di Matematica', 'Via Bari 7', '2013-10-11', '2013-10-11', 'Hotel Bologna', 'Raffaele_DiBenedetto');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('L''innovazione dell''informatica', '10.8535/7606.3673', 'Francesco_Pezzella', '1995-10-24', 'Chimica', 'Cambridge University Press', 'Spagnolo', 'Ebook', 'Conferenza di Chimica', 'Via Napoli 6', '1995-10-24', '1995-10-25', 'Sala Palermo', 'Massimo_Santi');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('La scoperta dell''informatica', '10.9180/6258.6662', 'Jhon_Tecchia', '2022-11-20', 'Informatica', 'Elsevier', 'Spagnolo', 'Cartaceo', 'Conferenza di Informatica', 'Via Bari 8', '2022-11-20', '2022-11-21', 'Hotel Genova', 'Luca_DiRienzo');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('L''innovazione della terra', '10.3403/1860.7936', 'Giulio_DiLorenzo', '1977-7-18', 'Informatica', 'Nature Publishing Group', 'Spagnolo', 'Ebook', 'JAMA', '998-859280929', 'Informatica', 'Giovanni_DiBenedetto', '9.0');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('La scoperta della fisica', '10.8650/2427.6226', 'Umberto_DiBenedetto', '2011-3-21', 'Medicina', 'BMJ', 'Francese', 'Audio', 'Nature', '991-012526508', 'Medicina', 'Mirko_DeLuca', '6.0');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('Ricerca della biologia', '10.6832/8937.2585', 'Luca_DiGiacomo', '1982-7-4', 'Chimica', 'Elsevier', 'Spagnolo', 'Audio', 'The New England Journal of Medicine', '988-596434260', 'Chimica', 'Davide_DiLorenzo', '5.0');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('La scoperta dell''informatica', '10.6890/4632.6698', 'Pierpaolo_DeLuca', '1996-4-16', 'Chimica', 'Wiley', 'Italiano', 'Audio', 'Conferenza di Chimica', 'Via Bologna 4', '1996-4-16', '1996-4-16', 'Palazzetto Bari', 'Giuseppe_DeGregorio');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('L''innovazione della chimica', '10.5028/6155.9356', 'Simone_DiMarco', '1960-4-12', 'Psicologia', 'Elsevier', 'Italiano', 'Audio', 'The New England Journal of Medicine', '989-307972545', 'Psicologia', 'Diego_DeVito', '17.0');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('Il futurodella medicina', '10.3671/5059.9230', 'Luigi_DiCarlo', '2005-5-16', 'Fisica', 'BMJ', 'Spagnolo', 'Cartaceo', 'Conferenza di Fisica', 'Via Milano 4', '2005-5-16', '2005-5-17', 'Hotel Bologna', 'Luca_DiCarlo');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('La politica della vita', '10.6961/7612.6322', 'Carlo_DeSimone', '1997-4-4', 'Filosofia', 'Taylor & Francis', 'Francese', 'Cartaceo', 'Science', '986-199224954', 'Filosofia', 'Giuseppe_DiCarlo', '21.0');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('Il futurodell''universo', '10.6632/5001.1330', 'Mattia_DeVito', '1959-10-4', 'Economia', 'Oxford University Press', 'Tedesco', 'Ebook', 'Conferenza di Economia', 'Via Roma 10', '1959-10-4', '1959-10-5', 'Hotel Torino', 'Enrico_DiPietro');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('Il futurodella terra', '10.1130/3615.1332', 'Marco_Pezzella', '1976-3-7', 'Medicina', 'Sage Publishing', 'Italiano', 'Ebook', 'The Lancet', '982-256767683', 'Medicina', 'Mattia_Abate', '23.0');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('L''innovazione dell''universo', '10.3742/7490.9651', 'Francesco_Parente', '2007-7-12', 'Medicina', 'Cambridge University Press', 'Spagnolo', 'Cartaceo', 'Conferenza di Medicina', 'Via Palermo 7', '2007-7-12', '2007-7-13', 'Sala Milano', 'Giuseppe_Tecchia');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('La scoperta della vita', '10.8965/3901.8124', 'Filippo_DiRienzo', '1970-6-9', 'Storia', 'Nature Publishing Group', 'Tedesco', 'Cartaceo', 'Conferenza di Storia', 'Via Bari 9', '1970-6-9', '1970-6-11', 'Hotel Firenze', 'Pierluigi_Tramontana');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('La scienza  della terra', '10.8413/9935.2525', 'Umberto_DeVito', '1980-7-4', 'Psicologia', 'Springer Nature', 'Spagnolo', 'Ebook', 'Conferenza di Psicologia', 'Via Torino 7', '1980-7-4', '1980-7-6', 'Sala Venezia', 'Raffaele_Tecchia');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('La politica della chimica', '10.1155/2232.6850', 'Lorenzo_DiPietro', '1985-10-6', 'Fisica', 'Taylor & Francis', 'Francese', 'Cartaceo', 'Nature', '989-402248593', 'Fisica', 'Marco_Esposito', '4.0');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('Ricerca della fisica', '10.9350/6185.3084', 'Marco_Santi', '1990-1-3', 'Economia', 'Oxford University Press', 'Italiano', 'Audio', 'Conferenza di Economia', 'Via Milano 8', '1990-1-3', '1990-1-4', 'Palazzetto Genova', 'Giovanni_DiLorenzo');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('L''innovazione della chimica', '10.2420/3591.3663', 'Pierpaolo_Penna', '2017-10-11', 'Economia', 'Oxford University Press', 'Italiano', 'Cartaceo', 'Science', '982-254831175', 'Economia', 'Emanuele_Parente', '3.0');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('Studio dell''universo', '10.4403/6618.4957', 'Pierluigi_DeGregorio', '1974-1-1', 'Biologia', 'Taylor & Francis', 'Francese', 'Ebook', 'Conferenza di Biologia', 'Via Bologna 4', '1974-1-1', '1974-1-3', 'Sala Torino', 'Simone_DiPaolo');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('La scienza  della fisica', '10.5345/4490.8746', 'Antonio_DiMarco', '1999-3-9', 'Fisica', 'Oxford University Press', 'Italiano', 'Audio', 'Conferenza di Fisica', 'Via Firenze 1', '1999-3-9', '1999-3-9', 'Palazzetto Venezia', 'Jhon_DiGiacomo');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('Studio della terra', '10.5475/7044.1709', 'Edoardo_DiLorenzo', '2020-6-3', 'Chimica', 'Elsevier', 'Francese', 'Cartaceo', 'Conferenza di Chimica', 'Via Bari 7', '2020-6-3', '2020-6-3', 'Hotel Roma', 'Andrea_Abate');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('Studio della vita', '10.8769/4028.9180', 'Silvio_DiSalvo', '2008-7-9', 'Letteratura', 'Nature Publishing Group', 'Tedesco', 'Cartaceo', 'The Lancet', '990-409023926', 'Letteratura', 'Dario_DeSimone', '17.0');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('L''innovazione dell''informatica', '10.3593/7513.8196', 'Andrea_Parente', '1958-1-20', 'Chimica', 'Sage Publishing', 'Spagnolo', 'Cartaceo', 'BMC Medicine', '998-505851203', 'Chimica', 'Salvatore_DiMauro', '16.0');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('Studio della medicina', '10.2037/7552.6552', 'Silvio_DeLuca', '1979-1-13', 'Biologia', 'Nature Publishing Group', 'Francese', 'Ebook', 'The New England Journal of Medicine', '990-307493042', 'Biologia', 'Lorenzo_DeLuca', '22.0');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('La scienza  della chimica', '10.1804/3698.3322', 'Giovanni_DeLuca', '2013-5-12', 'Politica', 'Elsevier', 'Tedesco', 'Cartaceo', 'Conferenza di Politica', 'Via Firenze 5', '2013-5-12', '2013-5-12', 'Palazzetto Bologna', 'Antonio_DiNapoli');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('La scienza  della terra', '10.6871/8050.6539', 'Edoardo_DiMauro', '2023-5-3', 'Matematica', 'Elsevier', 'Tedesco', 'Audio', 'PLOS ONE', '983-701848238', 'Matematica', 'Umberto_DeSantis', '23.0');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('Studio della terra', '10.2330/1873.2847', 'Jhon_DeSantis', '2019-1-12', 'Medicina', 'Taylor & Francis', 'Italiano', 'Cartaceo', 'Nature', '991-636191983', 'Medicina', 'Salvatore_DeSantis', '12.0');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('La scienza  della biologia', '10.3388/3535.3623', 'Riccardo_Abate', '1998-8-7', 'Filosofia', 'Nature Publishing Group', 'Italiano', 'Ebook', 'Cell', '987-449767069', 'Filosofia', 'Stefano_DiMauro', '4.0');
INSERT INTO b.ins_ArticoliRivista(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomerivista, issnrivista, argomentorivista, responsabilerivista, prezzorivista) VALUES ('La politica della biologia', '10.5368/6187.3029', 'Valerio_Barra', '1967-4-8', 'Politica', 'Taylor & Francis', 'Francese', 'Ebook', 'Journal of Clinical Oncology', '980-715823516', 'Politica', 'Umberto_DeLuca', '21.0');
INSERT INTO b.ins_ArticoliConferenze(titolo, doi, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato, nomeconferenza, indirizzoconferenza, datainizioconferenza, datafineconferenza, strutturaospitanteconferenza, responsabileconferenza) VALUES ('L''innovazione dell''informatica', '10.8319/2709.8867', 'Mario_DiBenedetto', '1961-1-7', 'Sociologia', 'Nature Publishing Group', 'Italiano', 'Audio', 'Conferenza di Sociologia', 'Via Bari 9', '1961-1-7', '1961-1-9', 'Piazza Napoli', 'Luigi_DeLuca');

INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il viaggio di Minnie e Minnie', '978-88-60-269972745', 'Simone_DiNapoli', '2005-3-7', 'Zanichelli', 'Romanzo', 'Inglese', 'Digitale', '7.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La ressurezione di Pippo', '978-88-53-932751319', 'Valerio_DiMarco', '2011-11-24', 'Mondadori', 'Didattico', 'Tedesco', 'Audiolibro', '6.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('L''avventura di Minnie e Cane', '978-88-46-103211873', 'Pierluigi_DeSantis', '1975-11-18', 'Bompiani', 'Didattico', 'Inglese', 'Digitale', '18.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo, nome_serie_di_appartenenza, issn_serie_di_appartenenza) VALUES ('L''avventura di Lorenzo', '978-88-52-050500042', 'Francesco_DeSimone', '1984-1-7', 'Bompiani', 'Romanzo', 'Tedesco', 'Audiolibro', '24.0', 'Storie più belle', '977-871546406');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La storia di  Topolino e Paperina', '978-88-79-616446530', 'Simone_Pezzella', '1958-10-13', 'Zanichelli', 'Romanzo', 'Tedesco', 'Digitale', '8.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('In viaggio con Topolino e Topolino', '978-88-60-074494594', 'Luigi_DiRienzo', '1997-6-3', 'Pearson', 'Didattico', 'Spagnolo', 'Audiolibro', '21.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La ressurezione di Gatto', '978-88-36-286488757', 'Luigi_DiBenedetto', '1986-6-13', 'Zanichelli', 'Didattico', 'Spagnolo', 'Cartaceo', '12.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('In viaggio col Minnie e Mario', '978-88-53-308304349', 'Federico_Abate', '2018-2-1', 'Pearson', 'Romanzo', 'Tedesco', 'Audiolibro', '14.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il libro di  Pinguino e Topolino', '978-88-63-852503170', 'Umberto_Penna', '2022-4-14', 'Pearson', 'Romanzo', 'Francese', 'Digitale', '11.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La ressurezione di Papero', '978-88-81-000211454', 'Nicola_Santi', '1996-6-5', 'Zanichelli', 'Didattico', 'Spagnolo', 'Digitale', '17.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il ritorno di Pippo', '978-88-60-682139049', 'Roberto_DeGregorio', '2002-9-6', 'Feltrinelli', 'Didattico', 'Francese', 'Audiolibro', '4.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('L''avventura di Pappagllo e Folle', '978-88-48-958223159', 'Edoardo_Ciambriello', '1958-10-8', 'Bompiani', 'Romanzo', 'Tedesco', 'Digitale', '7.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La morte di Gatto', '978-88-26-702302170', 'Stefano_DiPietro', '2022-6-19', 'Mondadori', 'Romanzo', 'Italiano', 'Cartaceo', '10.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La ressurezione di Pluto e Paperino', '978-88-72-972187300', 'Alessandro_Parente', '2015-1-18', 'Pearson', 'Romanzo', 'Tedesco', 'Audiolibro', '21.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il viaggio di Luigi e Lorenzo', '978-88-79-600126238', 'Stefano_DiMauro', '1978-1-15', 'Bompiani', 'Didattico', 'Tedesco', 'Digitale', '24.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('In viaggio col Gatto e Minnie', '978-88-23-934623424', 'Stefano_DeVito', '1971-6-8', 'Zanichelli', 'Romanzo', 'Italiano', 'Digitale', '14.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il viaggio di Gatto e Cane', '978-88-41-304286789', 'Filippo_DiRienzo', '2006-3-23', 'Zanichelli', 'Didattico', 'Tedesco', 'Audiolibro', '17.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('In viaggio col Luigi e Minnie', '978-88-32-788027764', 'Paolo_DeLuca', '2019-7-14', 'Pearson', 'Didattico', 'Italiano', 'Cartaceo', '19.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La ressurezione di Papero e Mario', '978-88-21-941445953', 'Simone_DiMauro', '1959-11-11', 'Mondadori', 'Romanzo', 'Tedesco', 'Cartaceo', '12.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('In viaggio col Pappagllo e Topolino', '978-88-29-816740654', 'Vincenzo_Penna', '2002-6-5', 'Pearson', 'Romanzo', 'Inglese', 'Audiolibro', '10.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La morte di Paperina', '978-88-28-405666049', 'Riccardo_DiRienzo', '1978-1-4', 'Mondadori', 'Romanzo', 'Tedesco', 'Cartaceo', '10.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo, nome_serie_di_appartenenza, issn_serie_di_appartenenza) VALUES ('In viaggio con Luigi', '978-88-54-564028040', 'Salvatore_Tecchia', '1984-6-12', 'Bompiani', 'Didattico', 'Spagnolo', 'Digitale', '4.0', 'edizione giallo', '983-533158791');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La ressurezione di Topolino', '978-88-30-219462132', 'Angelo_DiMauro', '1959-12-23', 'Zanichelli', 'Didattico', 'Inglese', 'Audiolibro', '15.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La morte di Paperina e Lorenzo', '978-88-80-012400495', 'Pierpaolo_DiLorenzo', '2020-1-21', 'Pearson', 'Romanzo', 'Spagnolo', 'Cartaceo', '24.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La morte di Folle e Pippo', '978-88-62-472633939', 'Luca_Parente', '1959-4-4', 'Mondadori', 'Romanzo', 'Tedesco', 'Cartaceo', '22.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('In viaggio con Cane', '978-88-77-879928707', 'Paolo_DiLorenzo', '2022-6-19', 'Feltrinelli', 'Didattico', 'Italiano', 'Cartaceo', '8.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('In viaggio col Lorenzo e Minnie', '978-88-41-439485491', 'Gabriele_DiRienzo', '1963-5-27', 'Zanichelli', 'Didattico', 'Spagnolo', 'Cartaceo', '4.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('L''avventura di Mario e Paperino', '978-88-40-856803646', 'Antonio_DiMarco', '1961-5-16', 'Mondadori', 'Didattico', 'Tedesco', 'Digitale', '14.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La ressurezione di Mario e Paperoga', '978-88-82-445209939', 'Francesco_Tecchia', '1985-11-6', 'Bompiani', 'Didattico', 'Spagnolo', 'Digitale', '17.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('In viaggio col Minnie e Mario', '978-88-84-999001880', 'Giuseppe_DiMarco', '1962-9-2', 'Zanichelli', 'Didattico', 'Italiano', 'Audiolibro', '6.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il ritorno di Delfino e Mario', '978-88-51-589984050', 'Dario_DiRienzo', '1959-3-25', 'Mondadori', 'Romanzo', 'Spagnolo', 'Cartaceo', '17.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La storia di  Paperino e Luigi', '978-88-56-143369553', 'Francesco_Abate', '1980-6-4', 'Bompiani', 'Romanzo', 'Italiano', 'Audiolibro', '7.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo, nome_serie_di_appartenenza, issn_serie_di_appartenenza) VALUES ('In viaggio con Gatto', '978-88-38-332278147', 'Roberto_DiMauro', '1978-12-6', 'Zanichelli', 'Romanzo', 'Spagnolo', 'Cartaceo', '21.0', 'Gialli', '992-214237366');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il viaggio di Paperoga e Pippo', '978-88-55-038415107', 'Giuseppe_DiCarlo', '1953-11-27', 'Zanichelli', 'Didattico', 'Tedesco', 'Audiolibro', '10.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo, nome_serie_di_appartenenza, issn_serie_di_appartenenza) VALUES ('La ressurezione di Folle', '978-88-81-069651893', 'Mattia_DeLuca', '1959-11-24', 'Zanichelli', 'Romanzo', 'Tedesco', 'Digitale', '13.0', 'ibri belli', '979-066957177');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il ritorno di Delfino e Lorenzo', '978-88-71-733900099', 'Emanuele_Parente', '1986-10-20', 'Zanichelli', 'Didattico', 'Inglese', 'Cartaceo', '19.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La ressurezione di Mario e Mario', '978-88-48-932861095', 'Marco_Barra', '1977-9-8', 'Mondadori', 'Didattico', 'Francese', 'Digitale', '4.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('L''avventura di Papero e Paperino', '978-88-40-443025376', 'Alessandro_Tramontana', '1963-9-24', 'Bompiani', 'Didattico', 'Italiano', 'Cartaceo', '17.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La storia di  Minnie e Paperina', '978-88-49-861563694', 'Enrico_Tramontana', '2015-11-26', 'Feltrinelli', 'Didattico', 'Francese', 'Audiolibro', '22.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo, nome_serie_di_appartenenza, issn_serie_di_appartenenza) VALUES ('In viaggio col Paperino e Gatto', '978-88-21-718714226', 'Giuseppe_DeGregorio', '1980-10-11', 'Mondadori', 'Romanzo', 'Francese', 'Audiolibro', '20.0', 'Storie più belle', '986-086966491');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La storia di  Folle e Pippo', '978-88-68-472189753', 'Alessio_DiPaolo', '2007-5-16', 'Feltrinelli', 'Romanzo', 'Francese', 'Digitale', '21.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il viaggio di Paperino', '978-88-76-005700490', 'Marco_DiSalvo', '1977-1-26', 'Zanichelli', 'Romanzo', 'Francese', 'Digitale', '7.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('L''avventura di Delfino', '978-88-44-030515044', 'Francesco_DiNapoli', '1960-3-24', 'Pearson', 'Didattico', 'Italiano', 'Cartaceo', '18.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('In viaggio con Gatto', '978-88-73-500967246', 'Emanuele_DeVito', '1997-4-24', 'Feltrinelli', 'Romanzo', 'Spagnolo', 'Digitale', '22.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('In viaggio con Pippo', '978-88-72-549140185', 'Nicola_DeSantis', '1966-10-12', 'Bompiani', 'Didattico', 'Spagnolo', 'Digitale', '23.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La ressurezione di Folle', '978-88-75-145420286', 'Daniele_DiPaolo', '2003-5-13', 'Pearson', 'Didattico', 'Italiano', 'Digitale', '25.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La storia di  Luigi e Davide', '978-88-36-338988633', 'Francesco_Parente', '1975-2-21', 'Pearson', 'Romanzo', 'Inglese', 'Digitale', '14.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('In viaggio col Delfino e Pluto', '978-88-42-204664107', 'Jhon_DiPaolo', '1992-11-3', 'Mondadori', 'Didattico', 'Inglese', 'Audiolibro', '7.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il viaggio di Delfino e Delfino', '978-88-52-657976716', 'Vincenzo_Pezzella', '1973-3-12', 'Pearson', 'Romanzo', 'Inglese', 'Audiolibro', '11.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il ritorno di Folle', '978-88-83-988593306', 'Paolo_DeGregorio', '1954-6-14', 'Mondadori', 'Romanzo', 'Francese', 'Cartaceo', '23.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('In viaggio con Papero e Pippo', '978-88-29-964499714', 'Matteo_Tramontana', '1995-3-12', 'Mondadori', 'Romanzo', 'Inglese', 'Cartaceo', '23.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il viaggio di Folle', '978-88-32-570965831', 'Paolo_DiNapoli', '1960-10-17', 'Pearson', 'Didattico', 'Spagnolo', 'Cartaceo', '6.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La ressurezione di Lorenzo', '978-88-27-034878932', 'Davide_Tecchia', '1968-10-14', 'Feltrinelli', 'Didattico', 'Inglese', 'Audiolibro', '20.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La morte di Paperina', '978-88-83-247392833', 'Giuliano_DeGregorio', '1987-6-18', 'Mondadori', 'Romanzo', 'Tedesco', 'Audiolibro', '11.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La storia di  Mario e Papero', '978-88-33-477853223', 'Giacomo_DiLorenzo', '1954-11-25', 'Pearson', 'Didattico', 'Italiano', 'Digitale', '11.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La ressurezione di Pluto', '978-88-34-434036920', 'Giuseppe_DeVito', '1972-4-27', 'Mondadori', 'Didattico', 'Italiano', 'Audiolibro', '23.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('L''avventura di Delfino e Pluto', '978-88-29-312645544', 'Silvio_Pezzella', '2017-8-23', 'Bompiani', 'Romanzo', 'Italiano', 'Cartaceo', '13.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('L''avventura di Luigi e Luigi', '978-88-29-033659794', 'Mattia_DiRienzo', '1955-9-28', 'Mondadori', 'Romanzo', 'Italiano', 'Cartaceo', '22.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('In viaggio col Delfino', '978-88-43-613872401', 'Lorenzo_DiSalvo', '1985-7-2', 'Bompiani', 'Romanzo', 'Inglese', 'Digitale', '19.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il libro di  Davide e Luigi', '978-88-49-399259298', 'Stefano_DiSalvo', '2004-5-23', 'Feltrinelli', 'Didattico', 'Inglese', 'Cartaceo', '13.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La morte di Gatto e Delfino', '978-88-72-938437862', 'Francesco_Barra', '1979-3-12', 'Zanichelli', 'Didattico', 'Tedesco', 'Audiolibro', '18.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo, nome_serie_di_appartenenza, issn_serie_di_appartenenza) VALUES ('Il viaggio di Pinguino', '978-88-69-880839536', 'Luca_DiLorenzo', '1958-2-8', 'Mondadori', 'Didattico', 'Francese', 'Digitale', '4.0', 'edizione blu', '987-603814591');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('In viaggio con Pappagllo e Pluto', '978-88-35-196892818', 'Edoardo_DiRienzo', '1962-4-26', 'Bompiani', 'Didattico', 'Italiano', 'Cartaceo', '16.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il viaggio di Luigi', '978-88-63-685863827', 'Emanuele_Penna', '2002-11-3', 'Feltrinelli', 'Romanzo', 'Inglese', 'Audiolibro', '10.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La morte di Luigi', '978-88-47-151775556', 'Carlo_DiLorenzo', '1955-6-6', 'Feltrinelli', 'Romanzo', 'Italiano', 'Cartaceo', '6.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo, nome_serie_di_appartenenza, issn_serie_di_appartenenza) VALUES ('Il ritorno di Pinguino', '978-88-22-669966357', 'Luca_DiLorenzo', '1967-7-5', 'Zanichelli', 'Didattico', 'Francese', 'Digitale', '17.0', 'edizione verde', '983-940067564');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il libro di  Cane', '978-88-44-126024113', 'Giuliano_DiRienzo', '1990-5-2', 'Bompiani', 'Didattico', 'Inglese', 'Audiolibro', '12.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La storia di  Folle', '978-88-31-589695041', 'Lorenzo_Ciambriello', '1990-9-15', 'Zanichelli', 'Didattico', 'Tedesco', 'Audiolibro', '5.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La morte di Pappagllo e Delfino', '978-88-62-543474187', 'Paolo_DeSimone', '1973-4-12', 'Mondadori', 'Didattico', 'Italiano', 'Audiolibro', '16.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo, nome_serie_di_appartenenza, issn_serie_di_appartenenza) VALUES ('Il viaggio di Luigi', '978-88-84-249021490', 'Francesco_DiPaolo', '2017-6-12', 'Pearson', 'Romanzo', 'Italiano', 'Audiolibro', '9.0', 'Storie più belle', '991-372407905');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La ressurezione di Pippo e Pappagllo', '978-88-19-264486565', 'Massimo_DiNapoli', '2006-7-20', 'Zanichelli', 'Didattico', 'Francese', 'Cartaceo', '17.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('In viaggio con Davide e Paperina', '978-88-22-234748198', 'Mirko_DiGiacomo', '1994-5-16', 'Bompiani', 'Romanzo', 'Inglese', 'Cartaceo', '21.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo, nome_serie_di_appartenenza, issn_serie_di_appartenenza) VALUES ('Il libro di  Delfino', '978-88-81-166011132', 'Andrea_Barra', '1969-2-19', 'Mondadori', 'Romanzo', 'Spagnolo', 'Audiolibro', '13.0', 'ibri belli', '990-766571261');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('In viaggio col Davide', '978-88-50-082818331', 'Giulio_DeSimone', '1991-5-15', 'Zanichelli', 'Romanzo', 'Italiano', 'Cartaceo', '8.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('In viaggio con Davide e Cane', '978-88-52-764098305', 'Giacomo_Tramontana', '1961-1-18', 'Feltrinelli', 'Romanzo', 'Inglese', 'Audiolibro', '9.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo, nome_serie_di_appartenenza, issn_serie_di_appartenenza) VALUES ('Il libro di  Pippo e Paperoga', '978-88-48-018717324', 'Giacomo_Tramontana', '1994-9-23', 'Zanichelli', 'Romanzo', 'Inglese', 'Cartaceo', '24.0', 'Gialli', '993-594184982');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La storia di  Paperino e Gatto', '978-88-82-071087633', 'Giuseppe_DeLuca', '2019-3-11', 'Pearson', 'Romanzo', 'Inglese', 'Audiolibro', '17.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il ritorno di Pinguino', '978-88-47-470809990', 'Lorenzo_DeSimone', '1962-11-28', 'Pearson', 'Didattico', 'Inglese', 'Cartaceo', '18.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il ritorno di Cane e Gatto', '978-88-84-809734136', 'Giacomo_Pezzella', '1962-4-25', 'Zanichelli', 'Didattico', 'Tedesco', 'Audiolibro', '12.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La morte di Davide e Mario', '978-88-75-711760181', 'Luca_DiMarco', '1985-2-12', 'Mondadori', 'Romanzo', 'Spagnolo', 'Audiolibro', '9.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La morte di Pappagllo', '978-88-68-619161425', 'Daniele_Tramontana', '1989-11-8', 'Feltrinelli', 'Didattico', 'Italiano', 'Digitale', '14.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('In viaggio col Cane', '978-88-41-191966870', 'Giovanni_Santi', '1973-11-26', 'Bompiani', 'Didattico', 'Francese', 'Digitale', '25.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('In viaggio con Delfino', '978-88-80-996361091', 'Emanuele_Parente', '2020-2-23', 'Mondadori', 'Didattico', 'Francese', 'Audiolibro', '16.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La storia di  Luigi', '978-88-18-965773485', 'Gabriele_DiGiacomo', '1983-11-1', 'Zanichelli', 'Didattico', 'Italiano', 'Audiolibro', '21.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('In viaggio col Pippo e Paperino', '978-88-57-166994368', 'Giuliano_DiMarco', '1986-5-12', 'Pearson', 'Romanzo', 'Tedesco', 'Audiolibro', '16.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('In viaggio con Pinguino e Paperina', '978-88-45-724495285', 'Luigi_DeGregorio', '2017-6-6', 'Zanichelli', 'Romanzo', 'Tedesco', 'Cartaceo', '22.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo, nome_serie_di_appartenenza, issn_serie_di_appartenenza) VALUES ('Il viaggio di Paperina e Topolino', '978-88-82-379208104', 'Giuseppe_Parente', '1971-11-7', 'Mondadori', 'Romanzo', 'Spagnolo', 'Digitale', '21.0', 'Storie più belle', '986-056965120');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo, nome_serie_di_appartenenza, issn_serie_di_appartenenza) VALUES ('La ressurezione di Delfino e Topolino', '978-88-82-809095066', 'Diego_Tramontana', '1993-12-18', 'Bompiani', 'Didattico', 'Inglese', 'Audiolibro', '24.0', 'edizione verde', '984-681023834');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il libro di  Gatto', '978-88-34-786551042', 'Edoardo_Tramontana', '1958-1-23', 'Pearson', 'Didattico', 'Inglese', 'Audiolibro', '25.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La ressurezione di Papero', '978-88-52-146949998', 'Paolo_Abate', '2001-1-26', 'Pearson', 'Romanzo', 'Tedesco', 'Audiolibro', '12.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('L''avventura di Papero', '978-88-61-041558116', 'Riccardo_Pezzella', '2019-2-15', 'Bompiani', 'Didattico', 'Tedesco', 'Digitale', '14.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La ressurezione di Topolino', '978-88-34-026626103', 'Riccardo_DiPietro', '1958-10-26', 'Mondadori', 'Didattico', 'Spagnolo', 'Audiolibro', '25.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La morte di Mario e Pappagllo', '978-88-30-026328139', 'Pierpaolo_DiRienzo', '2021-2-26', 'Feltrinelli', 'Romanzo', 'Inglese', 'Digitale', '13.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo, nome_serie_di_appartenenza, issn_serie_di_appartenenza) VALUES ('In viaggio col Lorenzo e Paperino', '978-88-30-260559597', 'Marco_DiLorenzo', '1993-7-18', 'Pearson', 'Romanzo', 'Italiano', 'Cartaceo', '17.0', 'Storie più belle', '983-965210823');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('In viaggio con Folle', '978-88-36-840707728', 'Marco_Ciambriello', '1966-10-23', 'Feltrinelli', 'Didattico', 'Spagnolo', 'Digitale', '3.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La storia di  Davide', '978-88-46-287775678', 'Davide_Abate', '1971-7-6', 'Pearson', 'Romanzo', 'Italiano', 'Cartaceo', '24.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La ressurezione di Folle e Papero', '978-88-57-997433885', 'Riccardo_Santi', '1980-7-10', 'Zanichelli', 'Romanzo', 'Italiano', 'Cartaceo', '18.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La ressurezione di Pluto e Lorenzo', '978-88-21-762505071', 'Pierluigi_Tramontana', '2020-1-28', 'Mondadori', 'Romanzo', 'Spagnolo', 'Cartaceo', '3.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo, nome_serie_di_appartenenza, issn_serie_di_appartenenza) VALUES ('L''avventura di Paperina e Delfino', '978-88-50-357804008', 'Edoardo_DeSantis', '1965-3-22', 'Zanichelli', 'Didattico', 'Italiano', 'Audiolibro', '15.0', 'editione nera', '995-081631587');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('In viaggio col Luigi e Pluto', '978-88-82-578021410', 'Daniele_Santi', '1994-7-15', 'Bompiani', 'Romanzo', 'Spagnolo', 'Cartaceo', '16.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('In viaggio con Paperoga', '978-88-47-813207988', 'Dario_DiPietro', '2007-5-17', 'Zanichelli', 'Romanzo', 'Tedesco', 'Cartaceo', '23.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('In viaggio col Delfino', '978-88-32-035829775', 'Massimo_Ciambriello', '2023-4-27', 'Zanichelli', 'Didattico', 'Spagnolo', 'Audiolibro', '3.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il viaggio di Gatto e Delfino', '978-88-44-888017698', 'Antonio_DiMauro', '1965-1-6', 'Feltrinelli', 'Didattico', 'Tedesco', 'Digitale', '17.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il ritorno di Mario e Pippo', '978-88-80-986780385', 'Diego_DiSalvo', '2004-11-15', 'Feltrinelli', 'Didattico', 'Italiano', 'Audiolibro', '16.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il viaggio di Pinguino e Luigi', '978-88-32-603065455', 'Mirko_DiMarco', '1958-2-14', 'Feltrinelli', 'Didattico', 'Italiano', 'Cartaceo', '20.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo, nome_serie_di_appartenenza, issn_serie_di_appartenenza) VALUES ('La ressurezione di Mario', '978-88-35-599812178', 'Francesco_DiPaolo', '2012-1-16', 'Zanichelli', 'Didattico', 'Francese', 'Cartaceo', '10.0', 'editione nera', '994-725123284');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il viaggio di Paperoga e Luigi', '978-88-25-832763324', 'Diego_Esposito', '1996-9-13', 'Pearson', 'Didattico', 'Inglese', 'Cartaceo', '4.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('In viaggio col Paperino', '978-88-64-194001426', 'Simone_DiNapoli', '2022-3-13', 'Zanichelli', 'Romanzo', 'Spagnolo', 'Cartaceo', '25.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La storia di  Davide e Pappagllo', '978-88-75-962522083', 'Mattia_DeVito', '2023-8-25', 'Pearson', 'Didattico', 'Tedesco', 'Audiolibro', '17.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il libro di  Paperino', '978-88-70-940961281', 'Mirko_Penna', '2001-12-14', 'Bompiani', 'Romanzo', 'Francese', 'Audiolibro', '22.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il libro di  Gatto', '978-88-30-706245035', 'Davide_DeVito', '1999-11-6', 'Bompiani', 'Didattico', 'Inglese', 'Cartaceo', '11.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('L''avventura di Cane', '978-88-70-808547325', 'Andrea_Barra', '1998-7-20', 'Mondadori', 'Romanzo', 'Inglese', 'Digitale', '20.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La ressurezione di Davide', '978-88-49-600530169', 'Umberto_DiRienzo', '2017-6-6', 'Feltrinelli', 'Romanzo', 'Inglese', 'Digitale', '16.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il viaggio di Cane e Mario', '978-88-80-104312224', 'Pierluigi_DiRienzo', '2001-1-27', 'Zanichelli', 'Romanzo', 'Spagnolo', 'Digitale', '21.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('L''avventura di Davide e Pinguino', '978-88-72-028243905', 'Enrico_Barra', '1992-12-12', 'Pearson', 'Didattico', 'Italiano', 'Audiolibro', '14.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('L''avventura di Gatto e Paperina', '978-88-40-087214299', 'Massimo_Santi', '1992-3-23', 'Pearson', 'Didattico', 'Tedesco', 'Cartaceo', '5.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il libro di  Mario', '978-88-80-659060033', 'Davide_Tecchia', '1984-12-10', 'Bompiani', 'Didattico', 'Tedesco', 'Cartaceo', '8.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('L''avventura di Pluto e Luigi', '978-88-68-074892646', 'Pierluigi_DiLorenzo', '1970-3-23', 'Feltrinelli', 'Didattico', 'Italiano', 'Digitale', '7.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il ritorno di Minnie e Paperina', '978-88-25-023489925', 'Giuliano_DiSalvo', '1981-9-24', 'Zanichelli', 'Romanzo', 'Italiano', 'Digitale', '12.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il libro di  Paperina', '978-88-84-622893867', 'Mario_Penna', '1972-2-5', 'Bompiani', 'Romanzo', 'Francese', 'Digitale', '3.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il libro di  Paperoga e Cane', '978-88-39-488662520', 'Giuliano_DiGiacomo', '1975-12-25', 'Pearson', 'Romanzo', 'Tedesco', 'Cartaceo', '19.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La ressurezione di Davide', '978-88-22-524441104', 'Roberto_DiRienzo', '1969-7-17', 'Pearson', 'Didattico', 'Francese', 'Digitale', '22.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il ritorno di Cane e Pinguino', '978-88-43-747540224', 'Alessio_DeVito', '2004-5-20', 'Mondadori', 'Didattico', 'Spagnolo', 'Digitale', '18.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il viaggio di Cane', '978-88-37-427471911', 'Giuseppe_Ciambriello', '1995-5-10', 'Zanichelli', 'Didattico', 'Spagnolo', 'Digitale', '16.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La morte di Cane', '978-88-65-447633802', 'Paolo_DiNapoli', '1956-6-4', 'Zanichelli', 'Didattico', 'Francese', 'Cartaceo', '16.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('In viaggio con Folle e Davide', '978-88-47-073759383', 'Filippo_DiCarlo', '2017-4-10', 'Pearson', 'Didattico', 'Tedesco', 'Digitale', '23.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il libro di  Mario e Pluto', '978-88-34-119650989', 'Mirko_DiNapoli', '1982-12-11', 'Mondadori', 'Romanzo', 'Tedesco', 'Audiolibro', '11.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo, nome_serie_di_appartenenza, issn_serie_di_appartenenza) VALUES ('In viaggio col Delfino', '978-88-50-085942264', 'Valerio_DiLorenzo', '1972-6-21', 'Bompiani', 'Didattico', 'Inglese', 'Digitale', '17.0', 'edizione blu', '987-005805688');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo, nome_serie_di_appartenenza, issn_serie_di_appartenenza) VALUES ('Il libro di  Cane', '978-88-24-042907733', 'Giuseppe_DeSimone', '1979-10-21', 'Pearson', 'Romanzo', 'Italiano', 'Audiolibro', '21.0', 'ibri belli', '990-007150279');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo, nome_serie_di_appartenenza, issn_serie_di_appartenenza) VALUES ('Il libro di  Gatto e Topolino', '978-88-57-637631612', 'Edoardo_DiLorenzo', '1966-6-2', 'Mondadori', 'Romanzo', 'Inglese', 'Digitale', '6.0', 'Gialli', '987-679054567');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('In viaggio col Papero e Delfino', '978-88-71-140341268', 'Filippo_Parente', '2001-4-26', 'Bompiani', 'Didattico', 'Inglese', 'Audiolibro', '10.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('L''avventura di Topolino', '978-88-57-875443533', 'Antonio_DiPaolo', '2008-8-6', 'Pearson', 'Romanzo', 'Italiano', 'Digitale', '19.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('L''avventura di Minnie', '978-88-40-851191424', 'Marco_Tramontana', '1962-10-14', 'Mondadori', 'Didattico', 'Francese', 'Cartaceo', '12.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il libro di  Luigi e Paperino', '978-88-57-816537385', 'Jhon_DiRienzo', '1986-11-20', 'Zanichelli', 'Didattico', 'Spagnolo', 'Digitale', '13.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il ritorno di Lorenzo', '978-88-84-325940639', 'Federico_DiGiacomo', '1980-9-24', 'Bompiani', 'Didattico', 'Italiano', 'Digitale', '15.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La morte di Cane e Pappagllo', '978-88-61-805603953', 'Giulio_DiMarco', '2005-10-12', 'Feltrinelli', 'Didattico', 'Inglese', 'Cartaceo', '11.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('L''avventura di Cane', '978-88-58-586469281', 'Lorenzo_DiLorenzo', '1956-11-17', 'Zanichelli', 'Didattico', 'Spagnolo', 'Audiolibro', '7.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La storia di  Cane e Paperoga', '978-88-57-009801958', 'Carlo_Parente', '1997-10-23', 'Feltrinelli', 'Romanzo', 'Tedesco', 'Audiolibro', '6.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La storia di  Topolino e Davide', '978-88-78-406646812', 'Giovanni_DiBenedetto', '1990-11-15', 'Bompiani', 'Didattico', 'Spagnolo', 'Audiolibro', '8.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('In viaggio col Paperina', '978-88-67-099589579', 'Giovanni_DiGiacomo', '1968-7-13', 'Pearson', 'Didattico', 'Francese', 'Digitale', '6.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il ritorno di Gatto e Cane', '978-88-22-561164597', 'Edoardo_Esposito', '2018-2-18', 'Zanichelli', 'Didattico', 'Italiano', 'Cartaceo', '16.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il viaggio di Delfino', '978-88-34-070353353', 'Pierpaolo_Pezzella', '2017-6-8', 'Bompiani', 'Romanzo', 'Spagnolo', 'Cartaceo', '4.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('In viaggio con Pinguino', '978-88-56-742485098', 'Alessio_DeSantis', '1994-1-17', 'Bompiani', 'Didattico', 'Spagnolo', 'Audiolibro', '16.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('La ressurezione di Pluto e Paperoga', '978-88-36-058200278', 'Giulio_DiGiacomo', '2014-11-27', 'Mondadori', 'Didattico', 'Tedesco', 'Cartaceo', '24.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il viaggio di Gatteeo', '978-88-453-848285213', 'Davide_DiPaolo, Ciao_Cane', '2015-12-8', 'Bompiani', 'Romanzo', 'Francese', 'Digitale', '3.0');
INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo) VALUES ('Il viaggio di Pluto e Delfino', '978-88-20-816521213', 'Enrico_DiLorenzo', '1978-10-8', 'Bompiani', 'Didattico', 'Tedesco', 'Cartaceo', '25.0');

INSERT INTO b.ins_presentazione(isbn, nome, indirizzo, strutturaospitante, datainizio, datafine, responsabile)
VALUES ('978-88-61-805603953', 'Presentazione del libro: La morte di Cane e Pappagllo', 'Via Roma, 1', 'Biblioteca Comunale di Cagliari', '2005-10-12', '2005-10-12', 'Mario_Rossi');
INSERT INTO b.ins_presentazione(isbn, nome, indirizzo, strutturaospitante, datainizio, datafine, responsabile)
VALUES ('978-88-22-561164597', 'Presentazione del libro: Il ritorno di Gatto e Cane', 'Via Palermo, 28', 'Feltrinelli Centrale Lucca', '2018-2-18', '2018-2-18', 'Luca_Giordano');
INSERT INTO b.ins_presentazione(isbn, nome, indirizzo, strutturaospitante, datainizio, datafine, responsabile)
VALUES ('978-88-56-742485098', 'Presentazione del libro: In viaggio con Pinguino', 'Via Sannio, 21', 'Fnac Bibbiano', '1994-1-17', '1994-1-17', 'Pasquale_DeSantis');

INSERT INTO b.Negozio (Nome, Tipo)
VALUES ('Libreria del Centro', 'Fisico');
INSERT INTO b.Negozio (Nome, Tipo)
VALUES ('Libreria Online', 'Online');
INSERT INTO b.Negozio (Nome, Tipo)
VALUES ('Libreria dell''Usato', 'Fisico');
INSERT INTO b.Negozio (Nome, Tipo)
VALUES ('Libreria dei Testi', 'Fisico');
INSERT INTO b.Negozio (Nome, Tipo)
VALUES ('Fumetteria Comics', 'Fisico');
INSERT INTO b.Negozio (Nome, Tipo)
VALUES ('Libreria d''Arte', 'Fisico');
INSERT INTO b.Negozio (Nome, Tipo)
VALUES ('Libreria dei Cercatori', 'Fisico');
INSERT INTO b.Negozio (Nome, Tipo)
VALUES ('Libreria di Cucina', 'Fisico');
INSERT INTO b.Negozio (Nome, Tipo)
VALUES ('Libreria di Cucina', 'Fisico');
INSERT INTO b.Negozio (Nome, Tipo)
VALUES ('Libreria di Filosofia', 'Fisico');
INSERT INTO b.Negozio (Nome, Tipo)
VALUES ('Libreria della Fantasia', 'Fisico');
INSERT INTO b.Negozio (Nome, Tipo)
VALUES ('Libreria dei Bambini', 'Fisico');
INSERT INTO b.Negozio (Nome, Tipo)
VALUES ('Libreria di Storia', 'Fisico');
INSERT INTO b.Negozio (Nome, Tipo)
VALUES ('Fumetteria Online', 'Online');
INSERT INTO b.Negozio (Nome, Tipo)
VALUES ('Libreria di Viaggio Online', 'Online');
INSERT INTO b.Negozio (Nome, Tipo)
VALUES ('Libreria della Poesia', 'Fisico');

--INSERT EDITIONE GIALLA
INSERT INTO b.ins_stock(id_negozio, isbn, quantita)
VALUES (1, '978-88-54-564028040', 10);

INSERT INTO b.ins_stock(id_negozio, isbn, quantita)
VALUES (2, '978-88-54-564028040', 10);

INSERT INTO b.ins_stock(id_negozio, isbn, quantita)
VALUES (3, '978-88-54-564028040', 10);




--IN VIAGGIO CON DELFINO EDIZIONE BLU ISSN 987-005...688
INSERT INTO b.ins_stock(id_negozio, isbn, quantita)VALUES
    (1, '978-88-50-085942264', '3');

INSERT INTO b.ins_stock(id_negozio, isbn, quantita)VALUES
    (2, '978-88-24-042907733', '12');


INSERT INTO b.ins_stock(id_negozio, isbn, quantita)VALUES
    (2, '978-88-81-166011132', '8');

INSERT INTO b.ins_stock(id_negozio, isbn, quantita) VALUES
    (3, '978-88-35-599812178', '6');

INSERT INTO b.utente(username, password) VALUES ('Luigi', 'ciao123');
INSERT INTO b.utente(username, password) VALUES ('Mario', 'hello456');
INSERT INTO b.utente(username, password) VALUES ('Giovanni', 'saludo789');
INSERT INTO b.utente(username, password) VALUES ('Giuseppe', 'hola123');
INSERT INTO b.utente(username, password) VALUES ('Antonio', 'ciao456');
INSERT INTO b.utente(username, password, permessi) VALUES ('Tramontana', 'trenta', '2');



INSERT INTO b.richiesta(id_utente, id_serie) VALUES (1, 2);
INSERT INTO b.richiesta(id_utente, id_serie) VALUES (1, 3);
INSERT INTO b.richiesta(id_utente, id_serie) VALUES (1, 4);
INSERT INTO b.richiesta(id_utente, id_serie) VALUES (2, 1);