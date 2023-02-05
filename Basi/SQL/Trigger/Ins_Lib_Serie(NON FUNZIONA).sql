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
                RAISE NOTICE 'Serie già presente';
                oldFormato = (SELECT DISTINCT l.formato
                              FROM (b.libri l NATURAL JOIN b.libriinserie ls)
                                       JOIN b.serie s ON ls.id_serie = s.id_serie
                              WHERE s.issn = NEW.ISSN_Serie_Di_Appartenenza);
                RAISE NOTICE 'Formato vecchio: %', oldFormato;
                IF (NEW.formato = oldFormato) THEN
                    newSerie = (SELECT id_serie FROM b.serie WHERE issn = New.ISSN_Serie_Di_Appartenenza);
                    --Aggiorno il libri successivo
                    UPDATE b.libriinserie
                    SET librisuccessivo = newLibri
                    WHERE id_serie = newSerie
                      AND librisuccessivo IS NULL;
                    RAISE NOTICE 'LIBRO SUCCESSIVO INSERITO';

                    --Aggiorno la tabella libriinserie
                    INSERT INTO b.libriinserie (id_serie, libri) VALUES (newSerie, newLibri);
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