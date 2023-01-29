DROP SCHEMA IF EXISTS b CASCADE;
CREATE SCHEMA b;

------------------------------------------------------------------------------------------------------------------------
                                                --Creazione tabelle
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE b.Articolo
(
    ID_Articolo       SERIAL,
    Titolo            VARCHAR(128),
    DOI               VARCHAR(128),
    DataPubblicazione DATE,
    Editore           VARCHAR(128),
    Lingua            VARCHAR(128),
    Formato           VARCHAR(128),

    CONSTRAINT PK_Articolo PRIMARY KEY (ID_Articolo),
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
    CONSTRAINT FK_AutoreArticolo_Autore FOREIGN KEY (ID_Autore) REFERENCES b.Autore (ID_Autore),
    CONSTRAINT FK_AutoreArticolo_Articolo FOREIGN KEY (ID_Articolo) REFERENCES b.Articolo (ID_Articolo)
);

CREATE TABLE b.Rivista
(
    ID_Rivista        SERIAL,
    ISSN              VARCHAR(128),
    Nome              VARCHAR(128),
    Argomento         VARCHAR(128),
    DataPubblicazione DATE,
    Responsabile      VARCHAR(128),

    CONSTRAINT PK_Rivista PRIMARY KEY (ID_Rivista)
);

CREATE TABLE b.ArticoloInRivista
(
    ID_Articolo SERIAL,
    ID_Rivista  SERIAL,

    CONSTRAINT PK_ArticoloInRivista PRIMARY KEY (ID_Articolo, ID_Rivista),
    CONSTRAINT FK_ArticoloInRivista_Articolo FOREIGN KEY (ID_Articolo) REFERENCES b.Articolo (ID_Articolo),
    CONSTRAINT FK_ArticoloInRivista_Rivista FOREIGN KEY (ID_Rivista) REFERENCES b.Rivista (ID_Rivista)
);

CREATE TABLE b.Evento
(
    ID_Evento          SERIAL,
    Indirizzo          VARCHAR(128),
    StrutturaOspitante VARCHAR(128),
    DataInizio         DATE,
    DataFine           DATE,
    Responsabile       VARCHAR(128),

    CONSTRAINT PK_Evento PRIMARY KEY (ID_Evento),
    CONSTRAINT CK_Date CHECK (DataInizio < DataFine),
    CONSTRAINT UK_Evento UNIQUE (Indirizzo, StrutturaOspitante, DataInizio, DataFine, Responsabile)
);

CREATE TABLE b.Conferenza
(
    Articolo SERIAL,
    Evento   SERIAL,

    CONSTRAINT PK_Conferenza PRIMARY KEY (Articolo, Evento),
    CONSTRAINT FK_Conferenza_Articolo FOREIGN KEY (Articolo) REFERENCES b.Articolo (ID_Articolo),
    CONSTRAINT FK_Conferenza_Evento FOREIGN KEY (Evento) REFERENCES b.Evento (ID_Evento)
);

CREATE TABLE b.Libro
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

    CONSTRAINT PK_Libro PRIMARY KEY (ID_Libro),
    CONSTRAINT UK_Libro UNIQUE (ISBN),
    CONSTRAINT CK_Libro CHECK (Prezzo > 0)
);

CREATE TABLE b.AutoreLibro
(
    ID_Autore SERIAL,
    ID_Libro  SERIAL,

    CONSTRAINT PK_AutoreLibro PRIMARY KEY (ID_Autore, ID_Libro),
    CONSTRAINT FK_AutoreLibro_Autore FOREIGN KEY (ID_Autore) REFERENCES b.Autore (ID_Autore),
    CONSTRAINT FK_AutoreLibro_Libro FOREIGN KEY (ID_Libro) REFERENCES b.Libro (ID_Libro)
);

CREATE TABLE b.Presentazione
(
    Evento SERIAL,
    Libro  SERIAL,

    CONSTRAINT PK_Presentazione PRIMARY KEY (Evento, Libro),
    CONSTRAINT FK_Presentazione_Evento FOREIGN KEY (Evento) REFERENCES b.Evento (ID_Evento),
    CONSTRAINT FK_Presentazione_Libro FOREIGN KEY (Libro) REFERENCES b.Libro (ID_Libro)
);

CREATE TABLE b.Serie
(
    ID_Serie SERIAL,
    ISSN     VARCHAR(128),
    Nome     VARCHAR(128),

    CONSTRAINT PK_Serie PRIMARY KEY (ID_Serie),
    CONSTRAINT UK_Serie UNIQUE (ISSN)
);

CREATE TABLE b.LibroINSerie
(
    ID_Serie        INTEGER,
    Libro           INTEGER,
    LibroSuccessivo INTEGER,

    CONSTRAINT FK_Serie_Libro FOREIGN KEY (Libro) REFERENCES b.Libro (ID_Libro),
    CONSTRAINT FK_Serie_LibroSuccessivo FOREIGN KEY (LibroSuccessivo) REFERENCES b.Libro (ID_Libro)
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
    Negozio  SERIAL,
    Libro    SERIAL,
    Quantita INTEGER, -- se quantita=0 la tupla viene eliminata (scoprire come si fa)

    CONSTRAINT FK_Stock_Negozio FOREIGN KEY (Negozio) REFERENCES b.Negozio (ID_Negozio),
    CONSTRAINT FK_Stock_Libro FOREIGN KEY (Libro) REFERENCES b.Libro (ID_Libro)
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
    CONSTRAINT FK_Richiesta_Utente FOREIGN KEY (ID_Utente) REFERENCES b.Utente (ID_Utente),
    CONSTRAINT FK_Richiesta_Serie FOREIGN KEY (ID_Serie) REFERENCES b.Serie (ID_Serie)
);

------------------------------------------------------------------------------------------------------------------------
                            --Tabella Jolly con campo text per le insert multiple
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE b.Jolly
(
    Text  TEXT
);
------------------------------------------------------------------------------------------------------------------------


------------------------------------------------------------------------------------------------------------------------
--Trigger Insert Articoli ed Autori
------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE VIEW b.ins_libro_autore AS
    SELECT doi,
           titolo,
           TEXT                 as AutoriNome_Cognome, --nome1_cognome1@nome2_cognome2@nome3_cognome3
           datapubblicazione,
           editore,
           lingua,
           formato
    FROM b.articolo, b.jolly;

CREATE OR REPLACE FUNCTION b.tfun_ArticoloAutore() RETURNS TRIGGER AS
$$
DECLARE
    autori         text[]= string_to_array(NEW.nome_cognome, '@');
    n_autori       INTEGER= array_length(autori, 1);
    autore_nome    b.autore.nome%TYPE;
    autore_cognome b.autore.cognome%TYPE;
BEGIN
    --Controllo se l'articolo è già presente
    IF EXISTS(SELECT * FROM b.articolo WHERE titolo = NEW.titolo AND datapubblicazione = NEW.datapubblicazione) THEN
        RAISE NOTICE 'Articolo già presente';
    ELSE
        INSERT INTO b.articolo(titolo, doi, datapubblicazione, editore, lingua, formato)
        VALUES (NEW.titolo, NEW.doi, NEW.datapubblicazione, NEW.editore, NEW.lingua, New.Formato);

        --Insert autori
        FOR i IN 1..n_autori
            LOOP
                autore_nome := split_part(autori[i], '_', 1);
                autore_cognome := split_part(autori[i], '_', 2);
                RAISE NOTICE 'nome{%} | cognome{%}', autore_nome, autore_cognome;

                IF EXISTS(SELECT * FROM b.autore WHERE nome = autore_nome AND cognome = autore_cognome) THEN --controllo se l'autore è già presente
                    RAISE NOTICE 'Autore già presente {%}', autori[i];
                ELSE
                    INSERT INTO b.autore (nome, cognome) VALUES (autore_nome, autore_cognome); --inserisco autore
                END IF;
                INSERT INTO b.autorearticolo(id_autore, id_articolo)
                SELECT a.id_autore, ar.id_articolo --recupero id_autore e id_articolo
                FROM b.autore as a,
                     b.articolo as ar
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

CREATE OR REPLACE TRIGGER trig_ArticoloAutore
    INSTEAD OF INSERT
    ON b.ins_articolo_autore
    FOR EACH ROW
EXECUTE FUNCTION b.tfun_ArticoloAutore();
------------------------------------------------------------------------------------------------------------------------


------------------------------------------------------------------------------------------------------------------------
--Trigger Insert Conferenze
------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE VIEW b.ins_evento_conferenza AS
SELECT indirizzo,
       strutturaospitante,
       datainizio,
       datafine,
       responsabile,
       TEXT                 as Doi_Articoli_Presentati --Più articoli separati da @ (es:DOI1@DOI2)
FROM b.jolly,
     b.evento as e;

CREATE OR REPLACE FUNCTION b.tfun_EventoConferenza() RETURNS TRIGGER AS
$$
DECLARE
    articoli    text[]  := string_to_array(NEW.Doi_Articoli_Presentati, '@');
    narticoli   INTEGER := array_length(articoli, 1);
    newarticolo b.articolo.id_articolo%TYPE;
    newevento   b.evento.ID_Evento%TYPE;
    vcheck      INTEGER := 0;
BEGIN
    --Verifico che tutti gli articoli esistano
    FOR i IN 1..narticoli
        LOOP
            IF NOT EXISTS(SELECT * FROM b.articolo WHERE doi = articoli[i]) THEN
                vcheck = 1;
            end if;
        end loop;

    --Se non esistono tutti gli articoli non inserisco l'evento
    IF (vcheck != 0) THEN
        RAISE NOTICE 'EVENTO NON INSERITO, UNO O PIU'' ARTICOLI SONO INESISTENTI';
    ELSE  --Se tutti gli articoli esistono inserisco l'evento
        INSERT INTO evento (indirizzo, strutturaospitante, datainizio, datafine, responsabile) --Inserisco l'evento
        VALUES (NEW.indirizzo, NEW.strutturaospitante, NEW.datainizio, NEW.datafine, NEW.responsabile);

        --Recupero l'id dell'evento appena inserito
        newevento = (SELECT id_evento
                     FROM b.evento
                     WHERE indirizzo = NEW.indirizzo
                       AND strutturaospitante = NEW.strutturaospitante
                       AND datainizio = NEW.datainizio
                       AND datafine = NEW.datafine
                       AND responsabile = NEW.responsabile);

        --Inserisco le conferenze per ogni articolo
        FOR i IN 1..narticoli
            LOOP
                newarticolo = (SELECT id_articolo FROM b.articolo WHERE doi = articoli[i]); --Recupero l'id dell'articolo
                INSERT INTO conferenza (articolo, evento) VALUES (newarticolo, newevento);
            end loop;
    END IF;
end
$$ LANGUAGE plpgsql;

CREATE TRIGGER trig_Evento_Conferenza
    INSTEAD OF INSERT
    ON b.ins_evento_conferenza
    FOR EACH ROW
EXECUTE FUNCTION b.tfun_EventoConferenza();
------------------------------------------------------------------------------------------------------------------------


------------------------------------------------------------------------------------------------------------------------
--Trigger Insert Libri, Autori e Serie
------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE VIEW b.ins_libro_autore_serie AS
SELECT l.titolo,
       l.ISBN,
       j.TEXT                         as AutoriNome_Cognome, --'nome1_cognome1@nome2_cognome2@nome3_cognome3'
       l.datapubblicazione,
       l.Editore,
       l.Genere,
       l.Lingua,
       l.Formato,
       l.Prezzo,
       s.nome                         as NOME_Serie_di_Appartenenza,
       s.ISSN                         as ISSN_Serie_di_Appartenenza
FROM b.libro as l,
     b.serie as s,
     b.jolly as j;

CREATE OR REPLACE FUNCTION b.tfun_LibroaAutoreSerie() RETURNS TRIGGER AS
$$
DECLARE
    autori         text[]  := string_to_array(NEW.Autorinome_cognome, '@');
    nautori        INTEGER := array_length(autori, 1);
    autore_nome    b.autore.nome%TYPE;
    autore_cognome b.autore.cognome%TYPE;
    newLibro       b.libro.ID_Libro%TYPE;
    newSerie       b.serie.ID_Serie%TYPE;
BEGIN
    --Verifico che il libro non sia già presente
    IF EXISTS(SELECT * FROM b.libro WHERE titolo = NEW.titolo AND datapubblicazione = NEW.datapubblicazione) THEN
        RAISE NOTICE 'Libro già presente';
    ELSE
        --Insert Libro
        INSERT INTO b.libro(titolo, ISBN, datapubblicazione, Editore, Genere, Lingua, Formato, Prezzo)
        VALUES (NEW.titolo, NEW.ISBN, NEW.datapubblicazione, NEW.editore, NEW.datapubblicazione, NEW.lingua,
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
                SELECT a.id_autore, l.id_libro  -- Trasformo l'ISNN in un ID e recupero l'ID dell'autore
                FROM b.autore as a,
                     b.libro as l
                WHERE a.nome = autore_nome
                  AND a.cognome = autore_cognome
                  AND l.titolo = NEW.titolo
                  AND l.datapubblicazione = NEW.datapubblicazione;
            END LOOP;

        --Insert Serie
        newLibro = (SELECT ID_Libro FROM b.libro WHERE ISBN = NEW.ISBN); -- Trasformo l'ISNN in un ID
        IF NEW.nome_serie_di_appartenenza IS NOT NULL AND
           NEW.issn_serie_di_appartenenza IS NOT NULL THEN -- Controllo se il libro fa parte di una serie
            RAISE NOTICE 'Fa parte di una Serie';

            --Verifico che la serie non sia già presente
            IF EXISTS(SELECT * FROM b.serie WHERE ISSN = NEW.ISSN_Serie_Di_Appartenenza) THEN
                newSerie = (SELECT id_serie FROM b.serie WHERE issn = New.ISSN_Serie_Di_Appartenenza);
                RAISE NOTICE 'Serie già presente';
                --Aggiorno il libro successivo
                UPDATE b.libroinserie
                SET librosuccessivo = newLibro
                WHERE id_serie = newSerie
                  AND librosuccessivo IS NULL;
                RAISE NOTICE 'LIBRO SUCCESSIVO INSERITO';

                --Aggiorno la tabella libroinserie
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

CREATE OR REPLACE TRIGGER trig_LibroAutoreSerie
    INSTEAD OF INSERT
    ON b.ins_libro_autore_serie
    FOR EACH ROW
EXECUTE FUNCTION b.tfun_LibroaAutoreSerie();
------------------------------------------------------------------------------------------------------------------------


------------------------------------------------------------------------------------------------------------------------
                                            --Trigger Insert Presentazioni
------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE VIEW b.ins_presentazione AS
SELECT l.ISBN,
       e.Indirizzo,
       e.StrutturaOspitante,
       e.DataInizio,
       e.DataFine,
       e.Responsabile
FROM b.evento as e, b.libro as l;

CREATE OR REPLACE FUNCTION b.ftrig_presentazione()
    RETURNS trigger AS
$$
DECLARE
BEGIN
    IF NOT EXISTS(SELECT * FROM b.libro WHERE isbn = NEW.ISBN) THEN --Controllo se il libro esiste
        RAISE NOTICE 'Il libro non esiste!! Presentazione non inserita';
    ELSEIF EXISTS(SELECT *
                  FROM (b.evento as e NATURAL JOIN b.presentazione as p) --Controllo se esiste già una presentazione per quel libro
                           JOIN b.libro as l ON p.libro = l.ID_Libro
                  WHERE ISBN = NEW.ISBN) THEN
        RAISE NOTICE 'Esista già una presentazione per questo libro!! Presentazione non inserita';
    ELSE  --Inserisco la presentazione
        INSERT INTO b.evento (indirizzo, strutturaospitante, datainizio, datafine, responsabile) --Inserisco l'evento
        VALUES (NEW.Indirizzo, NEW.StrutturaOspitante, NEW.DataInizio, NEW.DataFine, NEW.Responsabile);
        INSERT INTO b.presentazione (evento, libro)  --Inserisco la presentazione
        SELECT e.ID_evento, l.ID_libro   --Trasformo l'ISBN in un ID e recupero l'ID dell'evento
        FROM b.evento e,
             b.libro l
        WHERE l.ISBN = NEW.ISBN
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
CREATE VIEW b.ins_stock_Libro AS
SELECT negozio              as id_negozio,
       isbn,
       quantita
FROM b.libro, b.stock;

CREATE OR REPLACE FUNCTION b.ftrig_stocklibro() RETURNS TRIGGER AS
$$
DECLARE
    v_idlibro b.libro.id_libro%TYPE=(SELECT id_libro
                                     FROM b.libro
                                     WHERE isbn = NEW.isbn);
BEGIN
    IF NOT EXISTS(SELECT * FROM b.negozio WHERE id_negozio = NEW.id_negozio) OR --controllo che non esistano sia il negozio
       NOT EXISTS(SELECT * FROM b.libro WHERE id_libro = v_idlibro) THEN --sia il libro
        RAISE NOTICE 'ID Negozio o ISBN errati, dati non inseriti';
    ELSE
        --controllo se esista già una tupla composta da id_negozio e id_libro
        IF EXISTS(SELECT * FROM b.stock WHERE stock.negozio = NEW.id_negozio AND stock.libro = v_idlibro) THEN
            UPDATE b.stock
            SET quantita=quantita + NEW.quantita
            WHERE negozio = NEW.id_negozio AND stock.libro = v_idlibro;
        ELSE --se non esiste la creo
            INSERT INTO b.stock values (NEW.id_negozio, v_idlibro, NEW.quantita);
        end if;
    END IF;
    RETURN NEW;
END
$$
    LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trig_StockLibro
    INSTEAD OF INSERT
    ON b.ins_stock_Libro
    FOR EACH ROW
EXECUTE FUNCTION b.ftrig_stocklibro();
------------------------------------------------------------------------------------------------------------------------