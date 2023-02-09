------------------------------------------------------------------------------------------------------------------------
--Trigger Insert Articoli ed Autori
------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE VIEW b.ins_articoli_autori AS
SELECT a.doi,
       a.titolo,
       TEXT as AutoriNome_Cognome, --'nome1 cognome1, nome2 cognome2'
       a.datapubblicazione,
       a.disciplina,
       a.editore,
       a.lingua,
       a.formato,
       e.nome,
       e.indirizzo,
       e.strutturaospitante,
       e.datainizio,
       e.datafine,
       e.responsabile,
       r.nome,
       r.issn,
       r.nome,
       r.issn,
       r.argomento,
       r.datapubblicazione,
       r.responsabile,
       r.prezzo
FROM b.Articoli a,
     b.jolly,
     b.evento e,
     b.riviste r;

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
     --if articolo è pesentato sia in rivista che in evento
        --data pubblicazione = rivista e compresa in evento
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
