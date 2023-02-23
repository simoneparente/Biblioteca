--INSERT INTO b.utente(username, password, permessi) VALUES ('a', 'a', '2');
--INSERT EDITIONE GIALLA
INSERT INTO b.ins_stock(id_negozio, isbn, quantita)
VALUES (1, '978-88-54-564028040', 10);

INSERT INTO b.ins_stock(id_negozio, isbn, quantita)
VALUES (2, '978-88-54-564028040', 10);

INSERT INTO b.ins_stock(id_negozio, isbn, quantita)
VALUES (3, '978-88-54-564028040', 10);




--IN VIAGGIO CON DELFINO EDIZIONE BLU ISSN 987-005...688
INSERT INTO b.ins_stock(id_negozio, isbn, quantita)VALUES
    (1, '978-88-50-085942264', '3');

INSERT INTO b.ins_stock(id_negozio, isbn, quantita)VALUES
    (2, '978-88-24-042907733', '12');


INSERT INTO b.ins_stock(id_negozio, isbn, quantita)VALUES
    (2, '978-88-81-166011132', '8');

INSERT INTO b.ins_stock(id_negozio, isbn, quantita) VALUES
    (3, '978-88-35-599812178', '6');

INSERT INTO b.richiesta(id_utente, id_serie) VALUES
                                                 (1, 2);

INSERT INTO b.richiesta(id_utente, id_serie) VALUES
                                                 (1, 3);


