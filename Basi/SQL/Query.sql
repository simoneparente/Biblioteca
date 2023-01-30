CREATE VIEW b.view_libro_autore_prezzo AS
SELECT l.titolo, l.isbn, l.datapubblicazione, l.editore, l.genere, l.lingua, l.formato, l.prezzo
FROM (b.libro as l NATURAL JOIN b.autorelibro as al) JOIN b.autore as a on al.id_autore = a.id_autore;

SELECT ar.titolo, ar.doi, ar.datapubblicazione, ar.editore, ar.lingua, ar.formato
FROM (b.articolo as ar NATURAL JOIN b.autorearticolo as aa ) JOIN b.autore as au on aa.id_autore = au.id_autore;