SELECT * --attributi di libro
FROM (b.libro NATURAL JOIN b.autorelibro) JOIN b.autore on b.autorelibro.id_autore = b.autore.id_autore --attributi di autore
WHERE b.autore.cognome = 'Poe' --cognome autore
