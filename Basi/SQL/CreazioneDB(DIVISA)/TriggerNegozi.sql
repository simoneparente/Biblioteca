------------------------------------------------------------------------------------------------------------------------
--Trigger Insert Stock Negozi
------------------------------------------------------------------------------------------------------------------------
CREATE VIEW b.ins_stock_libri AS
SELECT id_negozio,
       isbn,
       quantita
FROM b.libri,
     b.stock;

CREATE OR REPLACE FUNCTION b.ftrig_stocklibri() RETURNS TRIGGER AS
$$
DECLARE
    v_idlibro b.libri.id_libro%TYPE=(SELECT id_libro
                                     FROM b.libri
                                     WHERE isbn = NEW.isbn);
BEGIN
    IF NOT EXISTS(SELECT * FROM b.negozio WHERE id_negozio = NEW.id_negozio) OR --controllo che non esistano sia il negozio
       NOT EXISTS(SELECT * FROM b.libri WHERE id_libro = v_idlibro) THEN --sia il libro
        RAISE NOTICE 'ID Negozio o ISBN errati, dati non inseriti';
    ELSE
        --controllo se esista già una tupla composta da id_negozio e id_libro
        IF EXISTS(SELECT * FROM b.stock WHERE stock.id_negozio = NEW.id_negozio AND stock.id_libro = v_idlibro) THEN
            UPDATE b.stock
            SET quantita=quantita + NEW.quantita
            WHERE id_negozio = NEW.id_negozio
              AND stock.id_libro = v_idlibro;
        ELSE --se non esiste la creo
            INSERT INTO b.stock values (NEW.id_negozio, v_idlibro, NEW.quantita);
        end if;
    END IF;
    RETURN NEW;
END
$$
    LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trig_StockLibri
    INSTEAD OF INSERT
    ON b.ins_stock_Libri
    FOR EACH ROW
EXECUTE FUNCTION b.ftrig_stocklibri();
------------------------------------------------------------------------------------------------------------------------
