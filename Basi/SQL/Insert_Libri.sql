CREATE OR REPLACE VIEW b.viewlibroautore AS
SELECT titolo, datapubblicazione, concat(nome, '_', cognome) as Nome_Cognome
FROM (b.Libro JOIN b.autorelibro ON libro.id_libro = autorelibro.id_libro) JOIN b.autore ON autore.id_autore = autorelibro.id_autore;

CREATE OR REPLACE FUNCTION b.ins_libroautore()
    RETURNS trigger AS
    $$
    DECLARE
        v_nome b.autore.nome%TYPE=split_part(NEW.nome_cognome, '_', 1);
        v_cognome b.autore.cognome%TYPE=split_part(NEW.nome_cognome, '_', 2);
        v_titolo b.libro.titolo%TYPE=NEW.titolo;
        v_datapubblicazione b.libro.datapubblicazione%TYPE=NEW.datapubblicazione;

        v_id_libro b.libro.id_libro%TYPE;
        v_id_autore b.autore.id_autore%TYPE;

    BEGIN
        RAISE NOTICE 'nome{%}', v_nome;
        RAISE NOTICE 'cognome{%}', v_cognome;

        IF NOT EXISTS(SELECT *
                      FROM b.libro l
                      WHERE l.titolo=v_titolo AND l.datapubblicazione=v_datapubblicazione)
                      THEN
            INSERT INTO b.libro(titolo, datapubblicazione) VALUES (v_titolo, v_datapubblicazione);
        END IF;
        IF NOT EXISTS(SELECT * FROM b.autore a WHERE a.nome = v_nome AND a.cognome = v_cognome) THEN
            INSERT INTO b.autore (nome, cognome) VALUES (v_nome, v_cognome);
        END IF;
        v_id_autore=(SELECT id_autore FROM b.autore WHERE nome=v_nome AND cognome=v_cognome);
        v_id_libro=(SELECT id_libro FROM b.libro WHERE titolo=v_titolo AND datapubblicazione=v_datapubblicazione);
        INSERT INTO b.autorelibro (id_libro, id_autore) VALUES (v_id_libro, v_id_autore);
        RETURN NEW;
    END

    $$
LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trig_libroautore INSTEAD OF INSERT ON b.libroautore
FOR EACH ROW
    EXECUTE FUNCTION b.ins_libroautore();


INSERT INTO b.libroautore(titolo, datapubblicazione, nome_cognome)
values ('Il nome della rosa', '1980-01-01', 'Umberto_Eco');