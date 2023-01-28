--view per tutti i libri, articoli, riviste, serie, conferenza (da finire)
SELECT l.titolo, a.titolo, r.nome
FROM (b.libro l FULL JOIN b.articolo a ON l.titolo=a.titolo FULL JOIN b.rivista r ON l.titolo=r.nome);