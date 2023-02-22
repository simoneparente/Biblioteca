CREATE VIEW b.filter_articoli AS
SELECT a.titolo, a.doi, au.id_autore, a.editore, a.disciplina, a.lingua, r.nome as Nome_Rivista, e.nome as Nome_Conferenza, a.formato, a.datapubblicazione
FROM b.articoli a, b.riviste r, b.conferenza c, b.evento e, b.autore au


;
SELECT * FROM b.view_articoli_autore WHERE (titolo LIKE '%%' OR doi LIKE '%%')

SELECT * FROM b.resultview_libri WHERE (titolo LIKE '%%' OR isbn LIKE '%%')


SELECT * FROM b.notifiche WHERE username = 'a'