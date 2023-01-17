CREATE OR REPLACE VIEW b.view_articoli_conferenza AS
SELECT doi as seq_doi, indirizzo, strutturaospitante, datainizio, datafine, responsabile
FROM (b.evento e JOIN b.conferenza c on e.id_evento = c.evento)
         JOIN b.articolo a ON c.articolo = a.id_articolo;

CREATE OR REPLACE FUNCTION b.check_doi_exist(n_doi INTEGER, array_doi text[]) RETURNS bool AS
$$
DECLARE
    vcheck INTEGER := 0;
BEGIN
    FOR i IN 1..n_doi
        LOOP
            IF NOT EXISTS(SELECT *
                          FROM b.articolo
                          WHERE doi = array_doi[i]) THEN
                RAISE NOTICE 'DOI {%} NON PRESENTE', array_doi[i];
                vcheck = vcheck + 1;
            END IF;
        end loop;
    IF (vcheck = 0) THEN
        return true;
    ELSE
        RETURN FALSE;
    END IF;
end
$$
    LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION b.fun_ins_articoli_conferenza() RETURNS TRIGGER AS
$$
DECLARE
    v_id_evento   b.evento.id_evento%TYPE;
    array_doi     text[]  := string_to_array(NEW.seq_doi, '_');
    n_doi         INTEGER := array_length(array_doi, 1);
    check_doi     bool    := b.check_doi_exist(n_doi, array_doi);
    v_id_articolo b.articolo.id_articolo%TYPE;
BEGIN
    --CHECK SE ESISTE EVENTO
    IF (check_doi = true) THEN
        RAISE NOTICE 'n_doi{%}', n_doi;
        IF NOT EXISTS(SELECT *
                      FROM b.evento
                      WHERE indirizzo = NEW.indirizzo
                        AND strutturaospitante = NEW.strutturaospitante
                        AND datainizio = NEW.datainizio
                        AND datafine = NEW.datafine
                        AND responsabile = NEW.responsabile) THEN
            RAISE NOTICE 'Evento non presente'; --NON ESISTE -> LO CREO
            INSERT INTO b.evento(indirizzo, strutturaospitante, datainizio, datafine, responsabile)
            VALUES (NEW.indirizzo, NEW.strutturaospitante, NEW.datainizio, NEW.datafine, NEW.responsabile);
        ELSE --ESISTE -> T'APPO
            RAISE NOTICE 'Evento già presente';
        end if;
        v_id_evento = (SELECT id_evento
                       FROM b.evento
                       WHERE indirizzo = NEW.indirizzo
                         AND strutturaospitante = NEW.strutturaospitante
                         AND datainizio = NEW.datainizio
                         AND datafine = NEW.datafine
                         AND responsabile = NEW.responsabile);
        FOR i IN 1..n_doi
            LOOP
                v_id_articolo = (SELECT id_articolo FROM b.articolo WHERE doi = array_doi[i]);
                INSERT INTO b.conferenza(articolo, evento) VALUES (v_id_articolo, v_id_evento);
                RAISE NOTICE 'ID_ARTICOLO[%] DOI {%} Evento(%)', v_id_articolo, array_doi[i], v_id_evento;
            end loop;
    ELSE
        RAISE NOTICE 'I DOI sopracitati non sono corretti, nessun inserimento effettuato';
    END IF;
    RETURN NEW;
END
$$
    language plpgsql;

CREATE OR REPLACE TRIGGER trig_ins_articoli_conferenza
    INSTEAD OF INSERT
    ON b.view_articoli_conferenza
    FOR EACH ROW
EXECUTE FUNCTION b.fun_ins_articoli_conferenza();


INSERT INTO b.view_articoli_conferenza(seq_doi, indirizzo, strutturaospitante, datainizio, datafine, responsabile)
VALUES ('doichenonesiste_doichenonesiste2',
        'VIA DAL CAZZO', 'lorenzo tecchia', CURRENT_DATE, '2023-01-18', 'lorenzo recchia');