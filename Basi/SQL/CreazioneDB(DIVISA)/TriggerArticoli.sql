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
    vcheck      INTEGER := 0;
    nome        text;
BEGIN
    FOR i IN 1..narticoli
        LOOP
            newArticolo = (SELECT id_Articolo FROM b.Articoli WHERE doi = articolip[i]);
            --Controllo se l'Articolo esiste
            IF NOT EXISTS(SELECT * FROM b.Articoli WHERE doi = articolip[i]) THEN
                vcheck = 1;
                RAISE NOTICE 'Articolo {%} non presente', articolip[i];
                --Controllo se l'Articolo è già presente in una rivista
            ELSEIF EXISTS(SELECT * FROM b.articoliinriviste WHERE id_Articolo = newArticolo) THEN
                vcheck = 2;
                RAISE NOTICE 'Articolo {%} già presente in una rivista', articolip[i];
            END IF;
        end loop;

    IF (vcheck = 1) THEN
        RAISE NOTICE 'EVENTO NON INSERITO, UNO O PIU'' ARTICOLI SONO INESISTENTI';
    ELSEIF (vcheck = 2) THEN
        RAISE NOTICE 'EVENTO NON INSERITO, UNO O PIU'' ARTICOLI SONO GIA'' PRESENTI IN UNA RIVISTA';
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
                newArticolo = (SELECT id_Articolo FROM b.Articoli WHERE doi = articolip[i]); --Recupero l'id dell'Articoli
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
