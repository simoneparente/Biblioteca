CREATE OR REPLACE FUNCTION ftrig_rimozineArticoli() RETURNS trigger AS
$$
DECLARE
    idAutoreArticolo b.autore.id_autore%TYPE;
    idAutoreArticoli CURSOR FOR SELECT id_autore
                                FROM b.autorearticolo
                                WHERE id_articolo = OLD.id_articolo;
    idRivista b.riviste.id_rivista%TYPE = (SELECT id_rivista FROM b.articoliinriviste WHERE id_articolo = OLD.id_articolo);
    IdPresentazione b.evento.id_evento%TYPE = (SELECT id_evento FROM b.presentazione WHERE id_articolo = OLD.id_articolo);
BEGIN
    --Eliminazione Autori se non hanno scritto altro
    OPEN idAutoreArticoli;
    LOOP
        FETCH idAutoreArticoli INTO idAutoreArticolo;
        EXIT WHEN NOT FOUND;
        IF NOT EXISTS(SELECT id_autore FROM b.autorearticolo WHERE id_autore = idAutoreArticolo AND id_articolo <> OLD.id_articolo) THEN
            IF NOT EXISTS(SELECT * FROM b.autorelibro WHERE id_autore = idAutoreArticolo) THEN
                DELETE FROM b.autore WHERE id_autore = idAutoreArticolo;
            END IF;
        END IF;
    END LOOP;

    --Eliminazione Rivista se non ha altri articoli
    IF NOT EXISTS(SELECT * FROM b.articoliinriviste WHERE id_articolo <> old.id_articolo AND id_rivista = idRivista) THEN
        DELETE FROM b.riviste WHERE id_rivista = idRivista;
    END IF;

    --Eliminazione Presentazione se non ha altri articoli
    IF NOT EXISTS(SELECT * FROM b.presentazione WHERE id_articolo <> old.id_articolo AND id_evento = IdPresentazione) THEN
        DELETE FROM b.evento WHERE id_evento = IdPresentazione;
    END IF;

    CLOSE idAutoreArticoli;
    RETURN NEW;
end;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trig_rimozioneArticoli
    BEFORE DELETE
    ON b.articoli
    FOR EACH ROW
EXECUTE PROCEDURE ftrig_rimozineArticoli();