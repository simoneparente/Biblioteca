--Insert View
CREATE OR REPLACE VIEW b.viewEventoPresentazione AS
SELECT l.ISBN, e.Indirizzo, e.StrutturaOspitante, e.DataInizio, e.DataFine, e.Responsabile
FROM (b.evento as e NATURAL JOIN b.presentazione as p) JOIN b.libro as l ON p.libro = l.ID_Libro;

--Creazione FTrigger
CREATE OR REPLACE FUNCTION b.ins_presentazione()
    RETURNS trigger AS
$$
    DECLARE
    BEGIN
        IF NOT EXISTS(SELECT * FROM b.libro WHERE isbn=NEW.ISBN) THEN
            RAISE NOTICE 'Il libro non esiste!! Presentazione non inserita';
        ELSEIF EXISTS(SELECT * FROM (b.evento as e NATURAL JOIN b.presentazione as p) JOIN b.libro as l ON p.libro = l.ID_Libro
                           WHERE ISBN = NEW.ISBN) THEN
            RAISE NOTICE 'Esista già una presentazione per questo libro!! Presentazione non inserita';
        ELSE
            INSERT INTO b.evento (indirizzo, strutturaospitante, datainizio, datafine, responsabile)
                VALUES (NEW.Indirizzo, NEW.StrutturaOspitante, NEW.DataInizio, NEW.DataFine, NEW.Responsabile);
            INSERT INTO b.presentazione (evento, libro) SELECT e.ID_evento, l.ID_libro
                                                        FROM b.evento e, b.libro l
                                                        WHERE l.ISBN = NEW.ISBN AND e.indirizzo = NEW.Indirizzo AND
                                                              e.strutturaospitante = NEW.StrutturaOspitante AND
                                                              e.datainizio = NEW.DataInizio AND
                                                              e.datafine = NEW.DataFine AND e.responsabile = NEW.Responsabile;
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

--Inserimento dati tramite view
INSERT INTO b.viewEventoPresentazione (ISBN, Indirizzo, StrutturaOspitante, DataInizio, DataFine, Responsabile)
VALUES ('978-88-17-88000-0', 'Via Roma 1', 'Casa Editrice', '2018-01-01', '2018-01-01', 'Mario Rossi');