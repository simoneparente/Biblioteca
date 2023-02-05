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
        newLibro = (SELECT ID_Libro FROM b.libri WHERE ISBN = NEW.ISBN); -- Trasformo l'ISNN in un ID
        IF NEW.nome_serie_di_appartenenza IS NOT NULL AND
           NEW.issn_serie_di_appartenenza IS NOT NULL THEN -- Controllo se il libri fa parte di una serie
            RAISE NOTICE 'Fa parte di una Serie';

            --Verifico che la serie non sia già presente
            IF EXISTS(SELECT * FROM b.serie WHERE ISSN = NEW.ISSN_Serie_Di_Appartenenza) THEN
                RAISE NOTICE 'Serie già presente';
                newSerie = (SELECT id_serie FROM b.serie WHERE issn = New.ISSN_Serie_Di_Appartenenza);
                --Aggiorno il libro successivo
                UPDATE b.libriinserie
                SET librisuccessivo = newLibro
                WHERE id_serie = newSerie
                  AND librisuccessivo IS NULL;
                RAISE NOTICE 'LIBRO SUCCESSIVO INSERITO';

                --Aggiorno la tabella libriinserie
                INSERT INTO b.libriinserie (id_serie, id_libro) VALUES (newSerie, newLibro);
                RAISE NOTICE 'NUOVO LIBRO INSERITO';
            ELSE
                RAISE NOTICE 'LIBRO NON INSERITO FORMATO SBAGLIATO';
            END IF;
        ELSE --NON ci sono altri libri, il libri è il primo della serie
            RAISE NOTICE 'Serie non presente';

            --Inserisco una nuova serie
            INSERT INTO b.serie (issn, nome)
            VALUES (NEW.ISSN_Serie_Di_Appartenenza, NEW.Nome_Serie_Di_Appartenenza);
            newSerie = (SELECT id_serie FROM b.serie WHERE issn = New.ISSN_Serie_Di_Appartenenza);
            RAISE NOTICE 'newserie{%}', newSerie;
            --Inserisco in libriinserie
            INSERT INTO b.libriinserie (id_serie, id_libro) VALUES (newSerie, newLibro);
            RAISE NOTICE 'NUOVO LIBRi INSERITO';
        end if;
    END IF;
    RETURN NEW;
end;

$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trig_LibriAutoreSerie
    INSTEAD OF INSERT
    ON b.ins_libri_autore_serie
    FOR EACH ROW
EXECUTE FUNCTION b.ftrig_LibriAutoreSerie();
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
