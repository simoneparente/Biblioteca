CREATE OR REPLACE FUNCTION b.ftrig_RimozioneDaStock() RETURNS trigger AS
$$
    DECLARE
    BEGIN
        if (NEW.quantita = 0) then
            DELETE FROM b.stock WHERE id_libro = OLD.id_libro;
        end if;
    END;
$$
    LANGUAGE plpgsql;

CREATE TRIGGER trig_RimozioneDaStock
    AFTER UPDATE OF quantita
    ON b.stock
    FOR EACH ROW
EXECUTE FUNCTION b.ftrig_RimozioneDaStock();