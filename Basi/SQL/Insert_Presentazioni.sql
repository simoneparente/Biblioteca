--Insert View
CREATE OR REPLACE VIEW b.viewEventoPresentazione AS
SELECT l.ISBN as ISBN_Libiro, e.Indirizzo, e.StrutturaOspitante, e.DataInizo, e.DataFine, e.Responsabile
FROM (b.evento as e NATURAL JOIN b.presentazione as p) JOIN b.libro as l ON p.libro = l.ID_Libro;

--Creazione FTrigger
CREATE OR REPLACE FUNCTION b.ins_presentazione()
    RETURNS trigger AS
$$
    DECLARE
    BEGIN
        IF NOT EXISTS(SELECT * FROM b.libro WHERE
    end;
$$




--Creazione Trigger
CREATE OR REPLACE TRIGGER trig_presentazione
    INSTEAD OF INSERT
    ON  b.viewEventoPresentazione
    FOR EACH ROW
EXECUTE FUNCTION b.ins_presentazione();