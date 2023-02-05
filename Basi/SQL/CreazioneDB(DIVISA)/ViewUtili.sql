------------------------------------------------------------------------------------------------------------------------
--View Utili
------------------------------------------------------------------------------------------------------------------------

--View Libri con autore
CREATE VIEW b.view_libri_autore AS
SELECT l.titolo,
       l.isbn,
       l.datapubblicazione,
       l.editore,
       l.genere,
       l.lingua,
       l.formato,
       l.prezzo,
       a.nome,
       a.cognome
FROM (b.libri as l NATURAL JOIN b.autorelibro as al)
         JOIN b.autore as a on al.id_autore = a.id_autore;

--View Libri con Serie
CREATE VIEW b.view_libri_serie AS
SELECT DISTINCT l.titolo,
                l.isbn,
                l.datapubblicazione,
                l.editore,
                l.genere,
                l.lingua,
                l.formato,
                l.prezzo,
                s.nome as nome_serie,
                ISSN
FROM (b.libri as l NATURAL JOIN b.libriinserie as ls)
         JOIN b.serie as s on ls.id_serie = s.id_serie;

--View Articoli con Autore
CREATE VIEW b.view_Articoli_autore AS
SELECT a.titolo,
       a.doi,
       a.datapubblicazione,
       a.disciplina,
       a.editore,
       a.lingua,
       a.formato,
       au.nome,
       au.cognome
FROM (b.Articoli as a NATURAL JOIN b.autoreArticolo as aa)
         JOIN b.autore as au on aa.id_autore = au.id_autore;

--View Articoli con Riviste
CREATE VIEW b.view_articoli_riviste AS
SELECT a.titolo,
       a.doi,
       a.datapubblicazione,
       a.disciplina,
       a.editore,
       a.lingua,
       a.formato,
       r.nome as titolo_rivista
FROM (b.Articoli as a NATURAL JOIN b.Articoliinriviste as ar)
         JOIN b.riviste as r on ar.id_rivista = r.id_rivista;

--View Articoli con Confereza
CREATE VIEW b.view_Articoli_conferenze AS
SELECT a.titolo,
       a.doi,
       a.datapubblicazione,
       a.disciplina,
       a.editore,
       a.lingua,
       a.formato,
       e.nome as titolo_conferenza
FROM (b.Articoli as a NATURAL JOIN b.conferenza as c)
         JOIN b.evento as e on c.id_evento = e.id_evento;
------------------------------------------------------------------------------------------------------------------------
