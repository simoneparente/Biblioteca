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