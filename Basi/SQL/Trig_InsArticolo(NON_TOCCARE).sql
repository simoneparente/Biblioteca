CREATE VIEW b.view_autore_articolo AS --ID_Articolo, Doi, TitoloArticolo, DataPub, Nome_Cognome_Autore
SELECT ar.id_articolo,
       ar.doi,
       ar.titolo,
       ar.datapubblicazione,
       concat(au.nome, '_', au.cognome) as nome_cognome
FROM (b.articolo ar JOIN b.autorearticolo aa ON ar.id_articolo = aa.id_articolo)
         JOIN b.autore au ON au.id_autore = aa.id_autore;

CREATE OR REPLACE VIEW b.view_articolo_rivista AS
SELECT ar.id_articolo       as id_articolo,
       doi,
       titolo               as titolo_articolo,
       ar.datapubblicazione as data_articolo,
       editore,
       lingua,
       formato,
       nome                 as nome_rivista,
       argomento,
       ri.datapubblicazione as data_rivista,
       responsabile
FROM ((b.articolo ar JOIN b.articoloinrivista a_in_r ON ar.id_articolo = a_in_r.id_articolo)
    JOIN b.rivista ri ON a_in_r.id_rivista = ri.id_rivista);

--DROP VIEW b.view_articolo_conferenza;
CREATE OR REPLACE VIEW b.view_articolo_conferenza AS
SELECT id_articolo,
       titolo,
       doi,
       datapubblicazione as data_articolo,
       editore,
       lingua,
       formato,
       id_evento,
       indirizzo,
       strutturaospitante,
       datainizio        as inizio_conferenza,
       datafine          as fine_conferenza,
       responsabile
FROM (b.articolo ar JOIN b.conferenza co ON ar.id_articolo = co.articolo)
         JOIN b.evento e ON e.id_evento = co.evento

