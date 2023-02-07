-- CREATE VIEW b.filter_articoli1 AS
-- SELECT a.titolo, a.doi, au.id_autore, a.editore, a.disciplina, a.lingua, r.nome as Nome_Rivista, e.nome as Nome_Conferenza, a.formato, a.datapubblicazione
-- FROM b.articoli a, b.riviste r, b.conferenza c, b.evento e, b.autore au

CREATE VIEW b.filter_articoli AS
SELECT a.titolo, a.doi, concat(au.nome, ' ', au.cognome) as Nome_Autore, a.editore, a.disciplina, a.lingua, r.nome as Nome_Rivista, e.nome as Nome_Conferenza, a.formato, a.datapubblicazione
FROM (((((b.articoli a NATURAL JOIN b.articoliinriviste ar) JOIN b.riviste r ON ar.id_rivista = r.id_rivista)
    JOIN b.autorearticolo aa ON a.id_articolo = aa.id_articolo) JOIN b.autore au ON aa.id_autore = au.id_autore)
         JOIN b.conferenza c ON a.id_articolo = c.id_articolo) JOIN b.evento e ON c.id_evento = e.id_evento;

-- Autore ed Articolo
CREATE VIEW b.filter_autorearticolo AS
SELECT *
FROM b.articoli a NATURAL JOIN b.autorearticolo aa NATURAL JOIN b.autore au;

-- SELECT a.titolo, a.doi, au.id_autore, a.editore, a.disciplina, a.lingua, r.nome as Nome_Rivista, e.nome as Nome_Conferenza, a.formato, a.datapubblicazione

CREATE VIEW b.filter_articoloautorerivista AS
SELECT a.titolo, a.doi, a.editore, a.disciplina, a.lingua, r.nome as Nome_Rivista, a.formato, a.datapubblicazione
FROM b.filter_autorearticolo a NATURAL JOIN b.articoliinriviste ar JOIN b.riviste r ON ar.id_rivista = r.id_rivista;

CREATE VIEW b.filter_articoloautoreevento AS
SELECT a.titolo, a.doi, a.editore, a.disciplina, a.lingua, e.nome as Nome_Conferenza, a.formato, a.datapubblicazione
FROM b.filter_autorearticolo as a NATURAL JOIN b.conferenza as c JOIN b.evento as e ON c.id_evento = e.id_evento;

--SELECT a.titolo, a.doi, concat(au.nome, ' ', au.cognome) as Nome_Autore, a.editore, a.disciplina, a.lingua, r.nome as Nome_Rivista, e.nome as Nome_Conferenza, a.formato, a.datapubblicazione


CREATE VIEW b.filter_articoli AS
SELECT a.titolo, a.doi, concat(a.nome, ' ', a.cognome) as Nome_Autore, a.editore, a.disciplina, a.lingua, r.nome as Nome_Rivista, e.nome as Nome_Conferenza, a.formato, a.datapubblicazione
FROM b.filter_articoloautorerivista as a CROSS JOIN b.filter_articoloautoreevento as e;