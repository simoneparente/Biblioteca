CREATE OR REPLACE VIEW b.ins_articolo_autore AS
SELECT doi,
       titolo,
       concat(au.nome, '_', au.cognome) as nome_cognome,
       datapubblicazione,
       editore,
       lingua,
       formato
FROM (b.articolo ar JOIN b.autorearticolo auar ON ar.id_articolo = auar.id_articolo) ar_aur
         JOIN b.autore au ON au.id_autore = ar_aur.id_autore;


CREATE OR REPLACE FUNCTION b.tfun_articoloAutore() RETURNS TRIGGER AS
    $$
    DECLARE
        autori text[]= string_to_array(NEW.nome_cognome, ',');
        n_autori INTEGER= array_length(autori, 1);
        autore_nome  b.autore.nome%TYPE;
        autore_cognome b.autore.cognome%TYPE;
    BEGIN
         IF EXISTS(SELECT * FROM b.articolo WHERE titolo = NEW.titolo AND datapubblicazione = NEW.datapubblicazione) THEN
        RAISE NOTICE 'Articolo già presente';
    ELSE
        INSERT INTO b.articolo(titolo, doi, datapubblicazione, editore, lingua, formato) VALUES (NEW.titolo, NEW.doi, NEW.datapubblicazione, NEW.editore, NEW.lingua, New.Formato);
        FOR i IN 1..n_autori
            LOOP
                autore_nome := split_part(autori[i], '_', 1);
                autore_cognome := split_part(autori[i], '_', 2);
                RAISE NOTICE 'nome{%} | cognome{%}', autore_nome, autore_cognome;

                IF EXISTS(SELECT * FROM b.autore WHERE nome = autore_nome AND cognome = autore_cognome) THEN
                    RAISE NOTICE 'Autore già presente {%}', autori[i];
                ELSE
                    INSERT INTO b.autore (nome, cognome) VALUES (autore_nome, autore_cognome);
                END IF;
                INSERT INTO b.autorearticolo(id_autore, id_articolo) SELECT a.id_autore, ar.id_articolo
                                                                FROM b.autore as a, b.articolo as ar
                                                                WHERE a.nome=autore_nome       AND
                                                                      a.cognome=autore_cognome AND
                                                                      ar.titolo = NEW.titolo    AND
                                                                      ar.datapubblicazione = NEW.datapubblicazione;
            END LOOP;
    END IF;
    RETURN NEW;

    END
    $$
LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trig_ArticoloAutore INSTEAD OF INSERT ON b.ins_articolo_autore
    FOR EACH ROW
    EXECUTE FUNCTION b.tfun_articoloAutore();


INSERT INTO b.ins_articolo_autore(doi, titolo, nome_cognome, datapubblicazione, editore, lingua, formato)
values ('10.1101/2020.12.31', 'La scoperta delle proteine', 'Mario_Rossi, Giuseppe_Bianchi', '2020-12-31', 'Nature Publishing Group', 'Italiano', 'Cartaceo'),
       ('10.1101/2021.12.31', 'Il futuro dell''intelligenza artificiale', 'John_Smith, Jane_Doe', '2021-12-31', 'Springer', 'Inglese', 'Digitale'),
       ('10.1101/2022.12.31', 'Il futuro dell''energia', 'Luca_Bianchi, Roberto_Neri', '2022-12-31', 'Wiley', 'Italiano', 'Cartaceo'),
       ('10.1101/2023.12.31', 'Il futuro dell''automobile', 'Mark_Johnson, David_Williams', '2023-12-31', 'Elsevier', 'Inglese', 'Digitale'),
       ('10.1101/2024.12.31', 'Il futuro dell''informatica', 'Andrea_Rossi, Giuseppe_Verdi', '2024-12-31', 'Springer', 'Italiano', 'Cartaceo'),
       ('10.1101/2025.12.31', 'Il futuro dell''intelligenza artificiale', 'Luca_Bianchi, Roberto_Neri', '2025-12-31', 'Nature Publishing Group', 'Inglese', 'Digitale'),
       ('10.1101/2026.12.31', 'Il futuro della medicina', 'John_Smith, Jane_Doe', '2026-12-31', 'Wiley', 'Italiano', 'Cartaceo'),
       ('10.1101/2027.12.31', 'Il futuro delle tecnologie', 'Mark_Johnson, David_Williams', '2027-12-31', 'Elsevier', 'Inglese', 'Digitale'),
       ('10.1101/2028.12.31', 'Il futuro dell''energia', 'Andrea_Rossi, Giuseppe_Verdi', '2028-12-31', 'Springer', 'Italiano', 'Cartaceo'),
       ('10.1101/2029.12.31', 'Il futuro dell''automobile', 'Luca_Bianchi, Roberto_Neri', '2029-12-31', 'Nature Publishing Group', 'Inglese', 'Digitale'),
       ('10.1101/2030.12.31', 'Il futuro della fessa', 'John_Smith, Jane_Doe', '2030-12-31', 'Wiley', 'Italiano', 'Cartaceo'),
       ('10.1101/2031.12.31', 'Il futuro della fessa artificiale', 'Mark_Johnson, David_Williams', '2031-12-31', 'Elsevier', 'Inglese', 'Digitale');





--CREATE VIEW ins_rivista_articolo AS
--SELECT *
--FROM b.articolo a
--         JOIN b.articoloinrivista air ON a.id_articolo = air.id_articolo