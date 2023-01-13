--Insert View
CREATE OR REPLACE VIEW b.viewEventoPresentazione AS
SELECT l.ISBN as ISBN_Libiro, e.Indirizzo, e.StrutturaOspitante, e.DataInizio, e.DataFine, e.Responsabile
FROM (b.evento as e NATURAL JOIN b.presentazione as p) JOIN b.libro as l ON p.libro = l.ID_Libro;

--Creazione FTrigger
CREATE OR REPLACE FUNCTION b.ins_presentazione()
    RETURNS trigger AS
$$
    DECLARE
    BEGIN
        IF NOT EXISTS(SELECT * FROM b.libro WHERE isbn=NEW.ISBN) THEN
            RAISE NOTICE 'Il libro non esiste!! Presentazione non inserita';
        ELSE IF EXISTS(SELECT * FROM (b.evento as e NATURAL JOIN b.presentazione as p) JOIN b.libro as l ON p.libro = l.ID_Libro
                           WHERE ISBN = NEW.ISBN) THEN
            RAISE NOTICE 'Esista già una presentazione per questo libro!! Presentazione non inserita';
        ELSE
            INSERT INTO b.evento (indirizzo, strutturaospitante, datainizio, datafine, responsabile)
                VALUES (NEW.Indirizzo, NEW.StrutturaOspitante, NEW.DataInizio, NEW.DataFine, NEW.Responsabile);
            INSERT INTO b.presentazione (evento, libro) SELECT e.ID_evento, l.ID_libro FROM b.evento e, b.libro l WHERE l.ISBN = NEW.ISBN AND
        END IF;
    RETURN NEW;
    END
$$
language plpgsql;



--Creazione Trigger
CREATE OR REPLACE TRIGGER trig_presentazione
    INSTEAD OF INSERT
    ON  b.viewEventoPresentazione
    FOR EACH ROW
EXECUTE FUNCTION b.ins_presentazione();