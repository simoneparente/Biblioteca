CREATE VIEW b.ins_stock_libri AS
SELECT id_negozio,
       isbn,
       quantita
FROM b.libri,
     b.stock;


CREATE OR REPLACE FUNCTION b.ftrig_stock() RETURNS TRIGGER AS
$$
DECLARE
    idLibro b.libri.id_libro%TYPE = (SELECT id_libro FROM b.libri WHERE isbn = NEW.isbn);
BEGIN
    --Controllo se il libro è presente nel database
    IF NOT EXISTS(SELECT * FROM b.libri WHERE isbn = NEW.isbn) THEN
        RAISE NOTICE 'Libro non presente, inserimento non possibile';
    --Controllo se il negozio è presente nel database
    ELSEIF NOT EXISTS(SELECT * FROM b.negozio WHERE id_negozio = NEW.id_negozio) THEN
        RAISE NOTICE 'Negozio non presente, inserimento non possibile';
    ELSE
        --Controllo se il libro non è presente nello stock del negozio ed in tal caso lo inserisco
        IF NOT EXISTS (SELECT * FROM b.stock WHERE id_negozio = NEW.id_negozio AND id_libro = idLibro) THEN
            INSERT INTO b.stock (id_negozio, id_libro, quantita) VALUES (NEW.id_negozio, idLibro, NEW.quantita);
        --Altrimenti aggiorno la quantità del libro nello stock del negozio
        ELSE
            UPDATE b.stock SET quantita = quantita + NEW.quantita WHERE id_negozio = NEW.id_negozio AND id_libro = idLibro;
        END IF;
    END IF;
    RETURN NEW;
END;
$$