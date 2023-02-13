SELECT distinct titolo,
                b.getAutoriByLibro(l.id_libri) AS Autore,
                editore,
                prezzo,
                lingua,
                formato,
                b.getDisponibilita(l.id_libri)
FROM (b.libri l JOIN b.autorelibri al ON l.id_libri = al.id_libri)
         JOIN b.autore a ON a.id_autore = al.id_autore;

CREATE OR REPLACE FUNCTION b.getDisponibilita(inputLibro b.libri.id_libri%TYPE) RETURNS boolean AS
$$
DECLARE
BEGIN
    IF EXISTS(SELECT * FROM b.stock s WHERE s.libri = inputLibro) THEN
        return true;
    else
        return false;
    end if;

END;
$$
    LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION b.getAutoriByLibro(inputIdLibro b.libri.id_libri%TYPE) RETURNS TEXT AS
$$
DECLARE
    returnAutori     TEXT;
    cursore CURSOR FOR (SELECT nome, cognome
                        FROM (b.autore a NATURAL JOIN b.autorelibri al)
                                 JOIN b.libri l ON l.id_libri = al.id_libri
                        WHERE l.id_libri = inputIdLibro);
    dimensioneAutori INTEGER= (SELECT COUNT(*)
                               FROM (b.autore a NATURAL JOIN b.autorelibri al)
                                        JOIN b.libri l ON l.id_libri = al.id_libri
                               WHERE l.id_libri = inputIdLibro);
    autoreNome       b.autore.nome%TYPE;
    autoreCognome    b.autore.cognome%TYPE;
    controllo        bool= false; --se è a false non sono stati inseriti ancora autori in returnAutori
BEGIN
    OPEN cursore;
    FOR b IN 1..dimensioneAutori
        LOOP
            FETCH cursore INTO autoreNome, autoreCognome;
            if controllo IS false THEN
                returnAutori = autoreNome || ' ' || autoreCognome;
                controllo = true;
            else
                returnAutori = returnAutori || ', ' || autoreNome || ' ' || autoreCognome;
            end if;
        END LOOP;
    CLOSE cursore;
    return returnAutori;
END;
$$
    LANGUAGE plpgsql;

SELECT b.getAutoriByLibro(1);


---INSERT PROVA FUNZIONE INSAUTORI ECC
INSERT INTO b.ins_libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo)
VALUES  ('a', '123', 'a_a, b_b', '2023-02-13', 'aa', 'aaa', 'i', 'Cartaceo', 12.3);

INSERT INTO b.ins_Libri(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo)
VALUES ('Il viaggio di Minnie e Minnie', '978-88-60-269972745', 'Simone_DiNapoli', '2005-3-7', 'Zanichelli', 'Romanzo', 'Inglese', 'Ebook', '7.0');