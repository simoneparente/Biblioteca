--CREATE VIEW
CREATE OR REPLACE VIEW  b.viewEventoCONF AS
SELECT e.indirizzo,
       e.strutturaospitante,
       e.datainizio,
       e.datafine,
       e.responsabile,
       a.doi as Doi_Articoli_Presentati
FROM b.articolo as a,
     b.evento as e;

--CREATE TFUNZIONE
CREATE OR REPLACE FUNCTION b.tfunEventoCONF() RETURNS TRIGGER AS
$$
DECLARE
    articoli    text[]  := string_to_array(NEW.Doi_Articoli_Presentati, ',');
    narticoli   INTEGER := array_length(articoli, 1);
    newarticolo b.articolo.id_articolo%TYPE;
    newevento   b.evento.ID_Evento%TYPE;
    vcheck      INTEGER := 0;
BEGIN
    --Verifico che tutti gli articoli esistano
    FOR i IN 1..narticoli
        LOOP
            IF NOT EXISTS(SELECT * FROM b.articolo WHERE doi = articoli[i]) THEN
                vcheck = 1;
            end if;
        end loop;

    IF (vcheck != 0) THEN
        RAISE NOTICE 'EVENTO NON INSERITO, UNO O PIù ARTICOLI SONO INESISTENTI';
    ELSE
        INSERT INTO evento (indirizzo, strutturaospitante, datainizio, datafine, responsabile)
        VALUES (NEW.indirizzo, NEW.strutturaospitante, NEW.datainizio, NEW.datafine, NEW.responsabile);
        newevento = (SELECT id_evento
                     FROM b.evento
                     WHERE indirizzo = NEW.indirizzo
                       AND strutturaospitante = NEW.strutturaospitante
                       AND datainizio = NEW.datainizio
                       AND datafine = NEW.datafine
                       AND responsabile = NEW.responsabile);
        FOR i IN 1..narticoli
            LOOP
                newarticolo = (SELECT id_articolo FROM b.articolo WHERE doi = articoli[i]);
                INSERT INTO conferenza (articolo, evento) VALUES (newarticolo, newevento);
            end loop;
    END IF;
end;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigEventoCONF
    INSTEAD OF INSERT ON b.viewEventoCONF
    FOR EACH ROW
    EXECUTE FUNCTION b.tfunEventoCONF();