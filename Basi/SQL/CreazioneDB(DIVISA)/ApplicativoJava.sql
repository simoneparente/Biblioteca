------------------------------------------------------------------------------------------------------------------------
--Funzioni Applicativo
------------------------------------------------------------------------------------------------------------------------

--Funzione che restituisc la disponibilità di una serie in un negozio
CREATE OR REPLACE FUNCTION b.getDisponibilitaSerie(inputSerieISSN b.Serie.ISSN%TYPE) RETURNS boolean AS
$$
DECLARE
    scorrilibri  b.libri.id_libro%TYPE;
    cursoreLibri CURSOR FOR (SELECT id_libro
                             FROM (b.libri l NATURAL JOIN b.libriinserie ls)
                                      JOIN b.serie s ON s.id_serie = ls.id_serie
                             WHERE ISSN = inputSerieISSN);
    nScorriLibri INTEGER := (SELECT count(*)
                             FROM (b.libri l NATURAL JOIN b.libriinserie ls)
                                      JOIN b.serie s ON s.id_serie = ls.id_serie
                             WHERE ISSN = inputSerieISSN);
BEGIN
    OPEN cursorelibri;
    FOR i IN 1..nScorriLibri
        LOOP
            FETCH cursoreLibri INTO scorrilibri;
            if NOT b.getDisponibilita(scorrilibri) THEN
                CLOSE cursoreLibri;
                return false;
            end if;
        end loop;
    CLOSE cursoreLibri;
    return true;
end;
$$
    LANGUAGE plpgsql;


--Funzione che restituisce la disponibilità di un libro in un negozio
CREATE OR REPLACE FUNCTION b.getDisponibilita(inputLibro b.libri.id_libro%TYPE) RETURNS boolean AS
$$
DECLARE
BEGIN
    IF EXISTS(SELECT * FROM b.stock s WHERE s.id_libro = inputLibro) THEN
        return true;
    else
        return false;
    end if;
END;
$$
    LANGUAGE plpgsql;

--Funzione che restituisce una stringa con i nomi degli autori di un libro
CREATE OR REPLACE FUNCTION b.getAutoriByLibro(inputIdLibro b.libri.id_libro%TYPE) RETURNS TEXT AS
$$
DECLARE
    returnAutori     TEXT;
    cursore CURSOR FOR (SELECT nome, cognome
                        FROM (b.autore a NATURAL JOIN b.autorelibro al)
                                 JOIN b.libri l ON l.id_libro = al.id_libro
                        WHERE l.id_libro = inputIdLibro);
    dimensioneAutori INTEGER= (SELECT COUNT(*)
                               FROM (b.autore a NATURAL JOIN b.autorelibro al)
                                        JOIN b.libri l ON l.id_libro = al.id_libro
                               WHERE l.id_libro = inputIdLibro);
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

--Funzione che restituisce una stringa con i nomi degli autori di un articolo
CREATE OR REPLACE FUNCTION b.getAutoriByArticolo(inputIdArticolo b.articoli.id_articolo%TYPE) RETURNS TEXT AS
$$
DECLARE
    autoreNome       b.autore.nome%TYPE;
    autoreCognome    b.autore.cognome%TYPE;
    returnAutori     TEXT;
    controllo        bool= false; --se è a false non sono stati inseriti ancora autori in returnAutori
    cursore cursor for (SELECT nome, cognome
                        FROM (b.autore a NATURAL JOIN b.autorearticolo aa)
                                 JOIN b.articoli ar ON ar.id_articolo = aa.id_articolo
                        WHERE ar.id_articolo = inputIdArticolo);
    dimensioneAutori INTEGER= (SELECT COUNT(*)
                               FROM (b.autore a NATURAL JOIN b.autorearticolo aa)
                                        JOIN b.articoli ar ON ar.id_articolo = aa.id_articolo
                               WHERE ar.id_articolo = inputIdArticolo);
BEGIN
    open cursore;
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
end;
$$ LANGUAGE plpgsql;
------------------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------------------
--ResultView Applicativo
------------------------------------------------------------------------------------------------------------------------

--Result View Libri
CREATE VIEW b.resultView_libri AS
SELECT distinct titolo,
                b.getAutoriByLibro(l.id_libro) AS Autore,
                editore,
                prezzo,
                lingua,
                formato,
                b.getDisponibilita(l.id_libro) AS Disponibilità
FROM b.libri l;

--Result View Articoli
CREATE VIEW b.resultView_articoli AS
SELECT distinct titolo,
                b.getAutoriByArticolo(a.id_articolo) AS Autori,
                lingua,
                formato,
                editore
FROM b.articoli a;

--Result View Serie
CREATE VIEW b.resultView_serie AS
SELECT distinct nome_serie                    as nome,
                editore,
                lingua,
                formato,
                b.getDisponibilitaSerie(issn) AS Disponibilità
FROM b.view_libri_serie;

--Result View Riviste
CREATE VIEW b.resultView_riviste AS
SELECT distinct titolo_rivista as nome,
                disciplina,
                editore,
                lingua,
                formato
FROM b.view_articoli_riviste;
------------------------------------------------------------------------------------------------------------------------

