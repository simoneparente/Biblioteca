INSERT INTO b.negozio(nome, tipo)
VALUES ('Libreria Nazionale', 'Fisico'),
        ('Libreria Online', 'Online'),
        ('Libreria Universitaria', 'Fisico'),
        ('Libreria delle Idee', 'Fisico'),
        ('Libreria delle Donne', 'Online'),
        ('Libreria delle Lingue', 'Fisico'),
        ('Libreria delle Scienze', 'Online'),
        ('Libreria dei Classici', 'Online'),
        ('Libreria dei Ragazzi', 'Online'),
        ('Libreria dei Viaggi', 'Fisico'),
        ('Libreria della Storia', 'Online'),
        ('Libreria dell''arte', 'Fisico'),
        ('Libreria delle novità', 'Online'),
        ('Libreria dei fumetti', 'Fisico'),
        ('Libreria della Musica', 'Fisico');


INSERT INTO b.view_insertStockLibro(id_negozio, isbn, quantita)
    VALUES (1,'978-88-17-88000-0', 35),
           (99,'978-88-17-88000-0', 22);

--Creazione view Stock e libro
DROP VIEW b.viewstocklibro;
CREATE OR REPLACE VIEW b.viewStockLibro AS
SELECT nome as Negozio, tipo, titolo, quantita
FROM (b.stock JOIN b.negozio on stock.negozio = negozio.id_negozio) JOIN b.libro ON id_libro=stock.libro;

CREATE VIEW b.view_insertStockLibro AS
SELECT id_negozio, isbn, quantita
FROM (b.stock JOIN b.negozio on stock.negozio = negozio.id_negozio) JOIN b.libro ON id_libro=stock.libro;



CREATE OR REPLACE FUNCTION b.ins_stocklibro() RETURNS TRIGGER AS
$$
    DECLARE
    v_idlibro b.libro.id_libro%TYPE=(SELECT id_libro FROM b.libro WHERE isbn=NEW.isbn);
    BEGIN
        IF NOT EXISTS(SELECT * FROM b.negozio WHERE id_negozio=NEW.id_negozio) OR  --controllo che non esistano sia il negozio
           NOT EXISTS(SELECT * FROM b.libro WHERE id_libro=v_idlibro) THEN              --sia il libro
            RAISE NOTICE 'ID Negozio o ISBN errati, dati non inseriti';
        ELSE
            --controllo se esista già una tupla composta da id_negozio e id_libro
            IF EXISTS(SELECT * FROM b.stock WHERE stock.negozio=NEW.id_negozio AND stock.libro=v_idlibro) THEN
                UPDATE b.stock SET quantita=quantita+NEW.quantita WHERE negozio=NEW.id_negozio AND stock.libro=v_idlibro;
            ELSE --se non esiste la creo
                INSERT INTO b.stock values(NEW.id_negozio, v_idlibro, NEW.quantita);
                end if;
            --RAISE NOTICE 'id_libro(%)', v_idlibro;
        END IF;
        RETURN NEW;
    END
$$
LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER triggerStockLibro INSTEAD OF INSERT ON b.view_insertStockLibro FOR EACH ROW
    EXECUTE FUNCTION b.ins_stockLibro();