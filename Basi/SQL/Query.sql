SELECT l.titolo, l.isbn, l.datapubblicazione, l.editore, l.genere, l.lingua, l.formato, l.prezzo
FROM (b.libro as l NATURAL JOIN b.autorelibro as al) JOIN b.autore as a on al.id_autore = a.id_autore;

SELECT ar.titolo, ar.doi, ar.datapubblicazione, ar.editore, ar.lingua, ar.formato
FROM (b.articolo as ar NATURAL JOIN b.autorearticolo as aa ) JOIN b.autore as au on aa.id_autore = au.id_autore;

SELECT l.titolo, l.isbn, l.datapubblicazione, l.editore, l.genere, l.lingua, l.formato, l.prezzo
FROM (b.libro as l NATURAL JOIN b.presentazione as p) JOIN b.evento as e on p.evento = e.id_evento;

SELECT e.strutturaospitante, e.datainizio
FROM (b.libro as l NATURAL JOIN b.presentazione as p) JOIN b.evento as e on p.evento = e.id_evento;

SELECT a.nome, a.cognome
FROM (b.autore as a NATURAL JOIN b.autorearticolo as aa) JOIN b.articolo as ar on aa.id_articolo = ar.id_articolo;


CREATE TABLE b.players (id INT, about TEXT, age INT);

INSERT INTO b.players (id, about, age) VALUES (generate_series(1, 1000),  repeat('A cool player. ', 1) || 'My number is ' || trunc(random()*1000), trunc(random()*10 * 2 + 10));

INSERT INTO b.ins_libro_autore_serie(titolo, isbn, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato, prezzo, nome_serie_di_appartenenza, issn_serie_di_appartenenza)
VALUES ()
