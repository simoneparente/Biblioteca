CREATE OR REPLACE FUNCTION ftrig_rimozineLibri() RETURNS trigger AS
$$
DECLARE
    idAutoreLibro b.autore.id_autore%TYPE;
    idAutoriLibri CURSOR FOR SELECT id_autore
                             FROM b.autorelibro
                             WHERE id_libro = OLD.id_libro;
    idEvento      b.evento.id_evento%TYPE = (SELECT id_evento
                                             FROM b.presentazione
                                             WHERE id_libro = OLD.id_libro) ) ;
    idSerie       b.serie.id_serie%TYPE   = (SELECT id_serie
                                             FROM b.libriinserie
                                             WHERE id_libro = OLD.id_libro);
BEGIN
    --Eliminazione Autori se non hanno scritto altro
    OPEN idAutoriLibri;
    LOOP
        FETCH idAutoriLibri INTO idAutoreLibro;
        EXIT WHEN NOT FOUND;
        IF NOT EXISTS(SELECT * FROM b.autorelibro WHERE id_autore = idAutoreLibro AND id_libro <> OLD.id_libro) THEN
            IF NOT EXISTS(SELECT * FROM b.autorearticolo WHERE id_autore = idAutoreLibro) THEN
                DELETE FROM b.autore WHERE id_autore = idAutoreLibro;
            END IF;
        END IF;
    END LOOP;

    --Eliminazione Presentazione ed Evento
    DELETE FROM b.evento WHERE id_evento = idEvento;

    --Eliminazione Serie se non ha altri libri
    IF NOT EXISTS(SELECT * FROM b.libriinserie WHERE id_serie = idSerie AND id_libro <> OLD.id_libro) THEN
        DELETE FROM b.serie WHERE id_serie = idSerie;
    END IF;

    CLOSE idAutoriLibri;
    RETURN NEW;
end;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trig_rimozioneLibri
    BEFORE DELETE
    ON b.libri
    FOR EACH ROW
EXECUTE PROCEDURE ftrig_rimozineLibri();