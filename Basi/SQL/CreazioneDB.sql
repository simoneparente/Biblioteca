DROP SCHEMA IF EXISTS b CASCADE;
CREATE SCHEMA b;

------------------------------------------------------------------------------------------------------------------------
--Creazione tabelle
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE b.Articoli
(
    ID_Articoli       SERIAL,
    Titolo            VARCHAR(128),
    DOI               VARCHAR(128),
    DataPubblicazione DATE,
    Disciplina        VARCHAR(128),
    Editore           VARCHAR(128),
    Lingua            VARCHAR(128),
    Formato           VARCHAR(128),

    CONSTRAINT PK_Articoli PRIMARY KEY (ID_Articoli),
    CONSTRAINT UK_Articoli UNIQUE (DOI)
);

CREATE TABLE b.Autore
(
    ID_Autore SERIAL,
    Nome      VARCHAR(128),
    Cognome   VARCHAR(128),

    CONSTRAINT PK_Autore PRIMARY KEY (ID_Autore)
);

CREATE TABLE b.AutoreArticoli
(
    ID_Autore   SERIAL,
    ID_Articoli SERIAL,

    CONSTRAINT PK_AutoreArticoli PRIMARY KEY (ID_Autore, ID_Articoli),
    CONSTRAINT FK_AutoreArticoli_Autore FOREIGN KEY (ID_Autore) REFERENCES b.Autore (ID_Autore),
    CONSTRAINT FK_AutoreArticoli_Articoli FOREIGN KEY (ID_Articoli) REFERENCES b.Articoli (ID_Articoli)
);

CREATE TABLE b.Riviste
(
    ID_Riviste        SERIAL,
    ISSN              VARCHAR(128),
    Nome              VARCHAR(128),
    Argomento         VARCHAR(128),
    DataPubblicazione DATE,
    Responsabile      VARCHAR(128),
    Prezzo            FLOAT,

    CONSTRAINT PK_Riviste PRIMARY KEY (ID_Riviste)
);

CREATE TABLE b.ArticoliInRiviste
(
    ID_Articoli SERIAL,
    ID_Riviste  SERIAL,

    CONSTRAINT PK_ArticoliInRiviste PRIMARY KEY (ID_Articoli, ID_Riviste),
    CONSTRAINT FK_ArticoliInRiviste_Articoli FOREIGN KEY (ID_Articoli) REFERENCES b.Articoli (ID_Articoli),
    CONSTRAINT FK_ArticoliInRiviste_Riviste FOREIGN KEY (ID_Riviste) REFERENCES b.Riviste (ID_Riviste)
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
    Articoli SERIAL,
    Evento   SERIAL,

    CONSTRAINT PK_Conferenza PRIMARY KEY (Articoli, Evento),
    CONSTRAINT FK_Conferenza_Articoli FOREIGN KEY (Articoli) REFERENCES b.Articoli (ID_Articoli),
    CONSTRAINT FK_Conferenza_Evento FOREIGN KEY (Evento) REFERENCES b.Evento (ID_Evento)
);

CREATE TABLE b.Libri
(
    ID_Libri          SERIAL,
    Titolo            VARCHAR(128),
    ISBN              VARCHAR(128),
    DataPubblicazione DATE,
    Editore           VARCHAR(128),
    Genere            VARCHAR(128),
    Lingua            VARCHAR(128),
    Formato           VARCHAR(128),
    Prezzo            FLOAT,

    CONSTRAINT PK_Libri PRIMARY KEY (ID_Libri),
    CONSTRAINT UK_Libri UNIQUE (ISBN),
    CONSTRAINT CK_Libri CHECK (Prezzo > 0)
);

CREATE TABLE b.AutoreLibri
(
    ID_Autore SERIAL,
    ID_Libri  SERIAL,

    CONSTRAINT PK_AutoreLibri PRIMARY KEY (ID_Autore, ID_Libri),
    CONSTRAINT FK_AutoreLibri_Autore FOREIGN KEY (ID_Autore) REFERENCES b.Autore (ID_Autore),
    CONSTRAINT FK_AutoreLibri_Libri FOREIGN KEY (ID_Libri) REFERENCES b.Libri (ID_Libri)
);

CREATE TABLE b.Presentazione
(
    Evento SERIAL,
    Libri  SERIAL,

    CONSTRAINT PK_Presentazione PRIMARY KEY (Evento, Libri),
    CONSTRAINT FK_Presentazione_Evento FOREIGN KEY (Evento) REFERENCES b.Evento (ID_Evento),
    CONSTRAINT FK_Presentazione_Libri FOREIGN KEY (Libri) REFERENCES b.Libri (ID_Libri)
);

CREATE TABLE b.Serie
(
    ID_Serie SERIAL,
    ISSN     VARCHAR(128),
    Nome     VARCHAR(128),

    CONSTRAINT PK_Serie PRIMARY KEY (ID_Serie),
    CONSTRAINT UK_Serie UNIQUE (ISSN)
);

CREATE TABLE b.LibriINSerie
(
    ID_Serie        INTEGER,
    Libri           INTEGER,
    LibriSuccessivo INTEGER,

    CONSTRAINT FK_Serie_Libri FOREIGN KEY (Libri) REFERENCES b.Libri (ID_Libri),
    CONSTRAINT FK_Serie_LibriSuccessivo FOREIGN KEY (LibriSuccessivo) REFERENCES b.Libri (ID_Libri)
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
    Libri    SERIAL,
    Quantita INTEGER, -- se quantita=0 la tupla viene eliminata (scoprire come si fa)

    CONSTRAINT FK_Stock_Negozio FOREIGN KEY (Negozio) REFERENCES b.Negozio (ID_Negozio),
    CONSTRAINT FK_Stock_Libri FOREIGN KEY (Libri) REFERENCES b.Libri (ID_Libri)
);

------------------------------------------------------------------------------------------------------------------------
--Tabelle per la gestione delle notifiche di disponibilità di un libri
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
    Text TEXT
);
------------------------------------------------------------------------------------------------------------------------


------------------------------------------------------------------------------------------------------------------------
--Trigger Insert Articoli ed Autori
------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE VIEW b.ins_Articoli_autore AS
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

CREATE OR REPLACE FUNCTION b.ftrig_ArticoliAutore() RETURNS TRIGGER AS
$$
DECLARE
    autori         text[]= string_to_array(NEW.autorinome_cognome, ' ');
    n_autori       INTEGER= array_length(autori, 1);
    autore_nome    b.autore.nome%TYPE;
    autore_cognome b.autore.cognome%TYPE;
BEGIN
    --Controllo se l'Articoli è già presente
    IF EXISTS(SELECT * FROM b.Articoli WHERE titolo = NEW.titolo AND datapubblicazione = NEW.datapubblicazione) THEN
        RAISE NOTICE 'Articoli già presente';
    ELSE
        INSERT INTO b.Articoli(titolo, doi, datapubblicazione, disciplina, editore, lingua, formato)
        VALUES (NEW.titolo, NEW.doi, NEW.datapubblicazione, NEW.disciplina, NEW.editore, NEW.lingua, New.Formato);

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
                INSERT INTO b.autoreArticoli(id_autore, id_Articoli)
                SELECT a.id_autore, ar.id_Articoli --recupero id_autore e id_Articoli
                FROM b.autore as a,
                     b.Articoli as ar
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

CREATE OR REPLACE TRIGGER trig_ArticoliAutore
    INSTEAD OF INSERT
    ON b.ins_Articoli_autore
    FOR EACH ROW
EXECUTE FUNCTION b.ftrig_ArticoliAutore();
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
    newArticoli b.Articoli.id_Articoli%TYPE;
    newriviste  b.riviste.ID_Riviste%TYPE;
    vcheck      INTEGER := 0;
BEGIN
    FOR i IN 1..narticoli
        LOOP
            newArticoli = (SELECT id_Articoli FROM b.Articoli WHERE doi = articolip[i]);
            --Controllo se l'Articoli esiste
            IF NOT EXISTS(SELECT * FROM b.Articoli WHERE doi = articolip[i]) THEN
                RAISE NOTICE 'Articoli {%} non presente', articolip[i];
                vcheck := 1;
                --Controllo se l'Articoli è già presente in una conferenza
            ELSEIF EXISTS(SELECT * FROM b.conferenza c WHERE c.articoli = newArticoli) THEN
                RAISE NOTICE 'Articoli {%} già presente in una conferenza', articolip[i];
                vcheck := 2;
            END IF;
        END LOOP;

    IF (vcheck = 1) THEN
        RAISE NOTICE 'EVENTO NON INSERITO, UNO O PIU'' ARTICOLI SONO INESISTENTI ';
    ELSIF (vcheck = 2) THEN
        RAISE NOTICE 'EVENTO NON INSERITO, UNO O PIU'' ARTICOLI SONO GIA'' PRESENTI IN UNA CONFERENZA ';
    ELSE
        --Inserisco la riviste
        INSERT INTO b.riviste (nome, issn, argomento, datapubblicazione, responsabile, prezzo)
        VALUES (NEW.nome, NEW.issn, NEW.argomento, NEW.datapubblicazione, NEW.responsabile,
                NEW.prezzo);

        --Recupero l'id della riviste appena inserita
        SELECT id_riviste INTO newriviste FROM b.riviste WHERE nome = NEW.nome AND issn = NEW.issn;

        --Inserisco gli articoli nella riviste
        FOR i IN 1..narticoli
            LOOP
                SELECT id_Articoli INTO newArticoli FROM b.Articoli WHERE doi = articolip[i]; --recupero id Articoli
                INSERT INTO b.Articoliinriviste (id_Articoli, id_riviste)
                VALUES (newArticoli, newriviste);
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
    newArticoli b.Articoli.id_Articoli%TYPE;
    newevento   b.evento.ID_Evento%TYPE;
    vcheck      INTEGER := 0;
    nome        text;
BEGIN
    FOR i IN 1..narticoli
        LOOP
            newArticoli = (SELECT id_Articoli FROM b.Articoli WHERE doi = articolip[i]);
            --Controllo se l'Articoli esiste
            IF NOT EXISTS(SELECT * FROM b.Articoli WHERE doi = articolip[i]) THEN
                vcheck = 1;
                RAISE NOTICE 'Articoli {%} non presente', articolip[i];
                --Controllo se l'Articoli è già presente in una riviste
            ELSEIF EXISTS(SELECT * FROM b.Articoliinriviste WHERE id_Articoli = newArticoli) THEN
                vcheck = 2;
                RAISE NOTICE 'Articoli {%} già presente in una riviste', articolip[i];
            END IF;
        end loop;

    IF (vcheck = 1) THEN
        RAISE NOTICE 'EVENTO NON INSERITO, UNO O PIU'' ARTICOLI SONO INESISTENTI';
    ELSEIF (vcheck = 2) THEN
        RAISE NOTICE 'EVENTO NON INSERITO, UNO O PIU'' ARTICOLI SONO GIA'' PRESENTI IN UNA RIVISTE';
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

        --Inserisco le conferenze per ogni Articoli
        FOR i IN 1..narticoli
            LOOP
                newArticoli =
                        (SELECT id_Articoli FROM b.Articoli WHERE doi = articolip[i]); --Recupero l'id dell'Articoli
                INSERT INTO b.conferenza (Articoli, evento) VALUES (newArticoli, newevento);
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

CREATE OR REPLACE FUNCTION b.ftrig_LibriaAutoreSerie() RETURNS TRIGGER AS
$$
DECLARE
    autori         text[]  := string_to_array(NEW.Autorinome_cognome, ' ');
    nautori        INTEGER := array_length(autori, 1);
    autore_nome    b.autore.nome%TYPE;
    autore_cognome b.autore.cognome%TYPE;
    newLibri       b.libri.ID_Libri%TYPE;
    newSerie       b.serie.ID_Serie%TYPE;
BEGIN
    --Verifico che il libri non sia già presente
    IF EXISTS(SELECT * FROM b.libri WHERE titolo = NEW.titolo AND datapubblicazione = NEW.datapubblicazione) THEN
        RAISE NOTICE 'Libri già presente';
    ELSE
        --Insert Libri
        INSERT INTO b.libri(titolo, ISBN, datapubblicazione, Editore, Genere, Lingua, Formato, Prezzo)
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

                --Aggiorno la tabella autorelibri
                INSERT INTO b.autorelibri(id_autore, id_libri)
                SELECT a.id_autore, l.id_libri -- Trasformo l'ISNN in un ID e recupero l'ID dell'autore
                FROM b.autore as a,
                     b.libri as l
                WHERE a.nome = autore_nome
                  AND a.cognome = autore_cognome
                  AND l.titolo = NEW.titolo
                  AND l.datapubblicazione = NEW.datapubblicazione;
            END LOOP;

        --Insert Serie
        newLibri = (SELECT ID_Libri FROM b.libri WHERE ISBN = NEW.ISBN); -- Trasformo l'ISNN in un ID
        IF NEW.nome_serie_di_appartenenza IS NOT NULL AND
           NEW.issn_serie_di_appartenenza IS NOT NULL THEN -- Controllo se il libri fa parte di una serie
            RAISE NOTICE 'Fa parte di una Serie';

            --Verifico che la serie non sia già presente
            IF EXISTS(SELECT * FROM b.serie WHERE ISSN = NEW.ISSN_Serie_Di_Appartenenza) THEN
                newSerie = (SELECT id_serie FROM b.serie WHERE issn = New.ISSN_Serie_Di_Appartenenza);
                RAISE NOTICE 'Serie già presente';
                --Aggiorno il libri successivo
                UPDATE b.libriinserie
                SET librisuccessivo = newLibri
                WHERE id_serie = newSerie
                  AND librisuccessivo IS NULL;
                RAISE NOTICE 'LIBRi SUCCESSIVO INSERITO';

                --Aggiorno la tabella libriinserie
                INSERT INTO b.libriinserie (id_serie, libri) VALUES (newSerie, newLibri);
                RAISE NOTICE 'NUOVO LIBRi INSERITO';

            ELSE --NON ci sono altri libri, il libri è il primo della serie
                RAISE NOTICE 'Serie non presente';

                --Inserisco una nuova serie
                INSERT INTO b.serie (issn, nome)
                VALUES (NEW.ISSN_Serie_Di_Appartenenza, NEW.Nome_Serie_Di_Appartenenza);
                newSerie = (SELECT id_serie FROM b.serie WHERE issn = New.ISSN_Serie_Di_Appartenenza);
                RAISE NOTICE 'newserie{%}', newSerie;
                --Inserisco in libriinserie
                INSERT INTO b.libriinserie (id_serie, libri) VALUES (newSerie, newLibri);
                RAISE NOTICE 'NUOVO LIBRi INSERITO';
            end if;
        end if;
    END IF;
    RETURN NEW;
end;

$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trig_LibriAutoreSerie
    INSTEAD OF INSERT
    ON b.ins_libri_autore_serie
    FOR EACH ROW
EXECUTE FUNCTION b.ftrig_LibriaAutoreSerie();
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
                           JOIN b.libri as l ON p.libri = l.ID_Libri
                  WHERE ISBN = NEW.ISBN) THEN
        RAISE NOTICE 'Esista già una presentazione per questo libri!! Presentazione non inserita';
    ELSE --Inserisco la presentazione
        INSERT INTO b.evento (nome, indirizzo, strutturaospitante, datainizio, datafine,
                              responsabile) --Inserisco l'evento
        VALUES (NEW.nome, NEW.Indirizzo, NEW.StrutturaOspitante, NEW.DataInizio, NEW.DataFine, NEW.Responsabile);
        INSERT INTO b.presentazione (evento, libri) --Inserisco la presentazione
        SELECT e.ID_evento, l.ID_libri --Trasformo l'ISBN in un ID e recupero l'ID dell'evento
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
CREATE VIEW b.ins_stock_Libri AS
SELECT negozio as id_negozio,
       isbn,
       quantita
FROM b.libri,
     b.stock;

CREATE OR REPLACE FUNCTION b.ftrig_stocklibri() RETURNS TRIGGER AS
$$
DECLARE
    v_idlibri b.libri.id_libri%TYPE=(SELECT id_libri
                                     FROM b.libri
                                     WHERE isbn = NEW.isbn);
BEGIN
    IF NOT EXISTS(SELECT * FROM b.negozio WHERE id_negozio = NEW.id_negozio) OR --controllo che non esistano sia il negozio
       NOT EXISTS(SELECT * FROM b.libri WHERE id_libri = v_idlibri) THEN --sia il libri
        RAISE NOTICE 'ID Negozio o ISBN errati, dati non inseriti';
    ELSE
        --controllo se esista già una tupla composta da id_negozio e id_libri
        IF EXISTS(SELECT * FROM b.stock WHERE stock.negozio = NEW.id_negozio AND stock.libri = v_idlibri) THEN
            UPDATE b.stock
            SET quantita=quantita + NEW.quantita
            WHERE negozio = NEW.id_negozio
              AND stock.libri = v_idlibri;
        ELSE --se non esiste la creo
            INSERT INTO b.stock values (NEW.id_negozio, v_idlibri, NEW.quantita);
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
--Funzioni Applicativo
------------------------------------------------------------------------------------------------------------------------

--Funzione che restituisc la disponibilità di una serie in un negozio

CREATE OR REPLACE FUNCTION b.getDisponibilitaSerie(inputSerieISSN b.Serie.ISSN%TYPE) RETURNS boolean AS
$$
DECLARE
    scorrilibri  b.libri.id_libri%TYPE;
    cursoreLibri CURSOR FOR (SELECT id_libri
                             FROM (b.libri l NATURAL JOIN b.libriinserie ls)
                                      JOIN b.serie s ON s.id_serie = ls.id_serie
                             WHERE ISSN = inputSerieISSN);
    nScorriLibri INTEGER := (SELECT count(*)
                             FROM (b.libri l NATURAL JOIN b.libriinserie ls)
                                      JOIN b.serie s ON s.id_serie = ls.id_serie
                             WHERE ISSN = inputSerieISSN);
BEGIN
    OPEN cursorelibri;
    FOR i IN 1..nScorriLibri
        LOOP
            FETCH cursoreLibri INTO scorrilibri;
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


--Funzione che restituisce la disponibilità di un libro in un negozio
CREATE OR REPLACE FUNCTION b.getDisponibilita(inputLibro b.libri.id_libri%TYPE) RETURNS boolean AS
$$
DECLARE
BEGIN
    IF EXISTS(SELECT * FROM b.stock s WHERE s.libri = inputLibro) THEN
        return true;
    else
        return false;
    end if;
END;
$$
    LANGUAGE plpgsql;

--Funzione che restituisce una stringa con i nomi degli autori di un libro
CREATE OR REPLACE FUNCTION b.getAutoriByLibro(inputIdLibro b.libri.id_libri%TYPE) RETURNS TEXT AS
$$
DECLARE
    returnAutori     TEXT;
    cursore CURSOR FOR (SELECT nome, cognome
                        FROM (b.autore a NATURAL JOIN b.autorelibri al)
                                 JOIN b.libri l ON l.id_libri = al.id_libri
                        WHERE l.id_libri = inputIdLibro);
    dimensioneAutori INTEGER= (SELECT COUNT(*)
                               FROM (b.autore a NATURAL JOIN b.autorelibri al)
                                        JOIN b.libri l ON l.id_libri = al.id_libri
                               WHERE l.id_libri = inputIdLibro);
    autoreNome       b.autore.nome%TYPE;
    autoreCognome    b.autore.cognome%TYPE;
    controllo        bool= false; --se è a false non sono stati inseriti ancora autori in returnAutori
BEGIN
    OPEN cursore;
    FOR b IN 1..dimensioneAutori
        LOOP
            FETCH cursore INTO autoreNome, autoreCognome;
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
CREATE OR REPLACE FUNCTION b.getAutoriByArticolo(inputIdArticolo b.articoli.id_articoli%TYPE) RETURNS TEXT AS
$$
DECLARE
    autoreNome       b.autore.nome%TYPE;
    autoreCognome    b.autore.cognome%TYPE;
    returnAutori     TEXT;
    controllo        bool= false; --se è a false non sono stati inseriti ancora autori in returnAutori
    cursore cursor for (SELECT nome, cognome
                        FROM (b.autore a NATURAL JOIN b.autorearticoli aa)
                                 JOIN b.articoli ar ON ar.id_articoli = aa.id_articoli
                        WHERE ar.id_articoli = inputIdArticolo);
    dimensioneAutori INTEGER= (SELECT COUNT(*)
                               FROM (b.autore a NATURAL JOIN b.autorearticoli aa)
                                        JOIN b.articoli ar ON ar.id_articoli = aa.id_articoli
                               WHERE ar.id_articoli = inputIdArticolo);
BEGIN
    open cursore;
    FOR b IN 1..dimensioneAutori
        LOOP
            FETCH cursore INTO autoreNome, autoreCognome;
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
FROM (b.libri as l NATURAL JOIN b.autorelibri as al)
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
FROM (b.Articoli as a NATURAL JOIN b.autoreArticoli as aa)
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
         JOIN b.riviste as r on ar.id_riviste = r.id_riviste;

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
         JOIN b.evento as e on c.evento = e.id_evento;
------------------------------------------------------------------------------------------------------------------------


------------------------------------------------------------------------------------------------------------------------
--ResultView Applicativo
------------------------------------------------------------------------------------------------------------------------

--Result View Libri
CREATE VIEW b.resultView_libri AS
SELECT distinct titolo,
                b.getAutoriByLibro(l.id_libri) AS Autore,
                editore,
                prezzo,
                lingua,
                formato,
                b.getDisponibilita(l.id_libri) AS Disponibilità
FROM b.libri l;

--Result View Articoli
CREATE VIEW b.resultView_articoli AS
SELECT distinct titolo,
                b.getAutoriByArticolo(a.id_articoli) AS Autori,
                lingua,
                formato,
                editore
FROM b.articoli a;

--Result View Serie
CREATE VIEW b.resultView_serie AS
SELECT distinct nome_serie                    as nome,
                editore,
                lingua,
                formato,
                b.getDisponibilitaSerie(issn) AS Disponibilità
FROM b.view_libri_serie;

--Result View Riviste
CREATE VIEW b.resultView_riviste AS
SELECT distinct titolo_riviste as nome,
                disciplina,
                editore,
                lingua,
                formato
FROM b.view_articoli_riviste;
------------------------------------------------------------------------------------------------------------------------

