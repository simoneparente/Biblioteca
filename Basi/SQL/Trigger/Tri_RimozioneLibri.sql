CREATE OR REPLACE FUNCTION b.ftri_RimozioneLibro() RETURNS trigger AS
$$
DECLARE
    idAutore b.autore.id_autore%TYPE;
    idAutori CURSOR FOR SELECT id_autore
                        FROM b.autorelibrI
                        WHERE id_libri = OLD.id_libri;
    idEvento b.evento.id_evento%TYPE;
    idEventi CURSOR FOR SELECT evento
                        FROM b.presentazione
                        WHERE id_libri = OLD.id_libri;

BEGIN
    --ELIMINARE DA TABELLA AUTORE_LIBRO ED AUTORE
    OPEN idAutori;
    LOOP
        FETCH idAutori INTO idAutore;
        EXIT WHEN NOT FOUND;
        DELETE FROM b.autorelibri WHERE id_autore = idAutore AND id_libri = OLD.id_libri;
        IF NOT EXISTS(SELECT * FROM b.autorelibri WHERE id_autore = idAutore) THEN
            DELETE FROM b.autore WHERE id_autore = idAutore;
        END IF;
    END LOOP;
    CLOSE idAutori;
    --ELIMINARE DA TABELLA PRESENTAZIONE ED EVENTO
    OPEN idEventi;
    LOOP
        FETCH idEventi INTO idEvento;
        EXIT WHEN NOT FOUND;
        DELETE FROM b.presentazione WHERE evento = idEvento AND id_libri = OLD.id_libri;
        DELETE FROM b.evento WHERE id_evento = idEvento;
    END LOOP;
    --ELIMINARE DA TABELLA RIVSISTE
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trig_RimozioneLibro
    BEFORE DELETE
    ON b.Libri
    FOR EACH ROW
EXECUTE PROCEDURE b.ftri_RimozioneLibro();

DELETE
FROM b.Libri
WHERE id_libri = 1;