--Insert Articolo Autore
INSERT INTO b.ins_articoli_autori(doi, titolo, autorinome_cognome, datapubblicazione, disciplina, editore, lingua,
                                  formato)
values ('10.1101/2020.12.31', 'La scoperta delle proteine', 'Mario_Rossi Giuseppe_Bianchi', '2020-12-31', 'Natura',
        'Nature Publishing Group', 'Italiano', 'Cartaceo'),
       ('10.1101/2021.12.31', 'Il futuro dell''intelligenza artificiale', 'John_Smith Jane_Doe', '2021-12-31',
        'Informatica', 'Springer', 'Inglese', 'Digitale'),
       ('10.1101/2022.12.31', 'Il futuro dell''energia', 'Luca_Bianchi Roberto_Neri', '2022-12-31', 'Energia', 'Wiley',
        'Italiano', 'Cartaceo'),
       ('10.1101/2023.12.31', 'Il futuro dell''automobile', 'Mark_Johnson David_Williams', '2023-12-31', 'Meccanina',
        'Elsevier', 'Inglese', 'Digitale'),
       ('10.1101/2024.12.31', 'Il futuro dell''informatica', 'Andrea_Rossi Giuseppe_Verdi', '2024-12-31', 'Informatica',
        'Springer', 'Italiano', 'Cartaceo'),
       ('10.1101/2025.12.31', 'Il futuro dell''intelligenza artificiale', 'Luca_Bianchi Roberto_Neri', '2025-12-31',
        'Informaitca', 'Nature Publishing Group', 'Inglese', 'Digitale'),
       ('10.1101/2026.12.31', 'Il futuro della medicina', 'John_Smith Jane_Doe', '2026-12-31', 'Meidicina', 'Wiley',
        'Italiano', 'Cartaceo'),
       ('10.1101/2027.12.31', 'Il futuro delle tecnologie', 'Mark_Johnson David_Williams', '2027-12-31', 'Tecnologia',
        'Elsevier', 'Inglese', 'Digitale'),
       ('10.1101/2028.12.31', 'Il futuro dell''energia', 'Andrea_Rossi Giuseppe_Verdi', '2028-12-31', 'Energia',
        'Springer', 'Italiano', 'Cartaceo'),
       ('10.1101/2029.12.31', 'Il futuro dell''automobile', 'Luca_Bianchi Roberto_Neri', '2029-12-31', 'Meccanina',
        'Nature Publishing Group', 'Inglese', 'Digitale'),
       ('10.1101/2030.12.31', 'Il futuro della fessa', 'John_Smith Jane_Doe', '2030-12-31', 'Anatomia', 'Wiley',
        'Italiano', 'Cartaceo'),
       ('10.1101/2031.12.31', 'Il futuro della fessa artificiale', 'Mark_Johnson David_Williams', '2031-12-31',
        'Anatomia', 'Elsevier', 'Inglese', 'Digitale');

--Insert Libro Autore Serie
INSERT INTO b.ins_libri_autore_serie (titolo, ISBN, autorinome_cognome, datapubblicazione, editore, genere, lingua,
                                      formato,
                                      nome_serie_di_appartenenza, issn_serie_di_appartenenza)
VALUES ('Il Signore degli Anelli 1', '978-88-17-88000-0', 'J.R.R._Tolkien ciao_fratm', '1954-07-29', 'Mondadori',
        'Fantasy',
        'Italiano', 'Ebook', 'Il Signore degli Anelli', '978-88-04-58339-8');
INSERT INTO b.ins_libri_autore_serie (titolo, ISBN, autorinome_cognome, datapubblicazione, editore, genere, lingua,
                                      formato,
                                      nome_serie_di_appartenenza, issn_serie_di_appartenenza)
VALUES ('Il Signore degli Anelli 2 cartaceo', '978-88-17-88033-0', 'J.R.R._Tolkien ciao_fratm', '1954-07-29',
        'Mondadori', 'Fantasy', 'Italiano', 'Cartaceo', 'Il Signore degli Anelli', '978-88-04-58339-8');

INSERT INTO b.ins_presentazione(isbn, nome, indirizzo, strutturaospitante, datainizio, datafine, responsabile)
VALUES ('978-88-17-88000-0', 'luca', 'Via Roma 1', 'Mondadori', '2020-12-31', '2021-12-31', 'Mario Rossi');

--INSERT NEGOZI
INSERT INTO b.negozio(nome, tipo)
VALUES ('NEGOEIFJDOFSKID', 'Fisico');
--Insert Stock
INSERT INTO b.ins_stock_libri(id_negozio, isbn, quantita)
VALUES (1, '978-88-04-58339-8', 35),
       (1, '978-88-17-88000-0', 22);

--Insert Articolo Rivista
INSERT INTO b.ins_riviste (issn, nome, argomento, datapubblicazione, responsabile, doi_articoli_pubblicati)
VALUES ('978-88-04-58339-8', 'Il Signore degli Anelli', 'Fantasy', '1954-07-29', 'J.R.R._Tolkien',
        '10.1101/2020.12.31 10.1101/2021.12.31');

--Insert Articolo Conferenza
INSERT INTO b.ins_conferenza (nome, indirizzo, strutturaospitante, datainizio, datafine, responsabile,
                              doi_articoli_presentati)
VALUES ('culo', 'Via Roma 1', 'Casa Editrice', '2018-01-01', '2018-01-01', 'Mario Rossi',
        '10.1101/2027.12.31 10.1101/2031.12.31');



--INSERT PROVA FUNZIONE CELAFAREMO
INSERT INTO b.ins_libri_autore_serie (titolo, ISBN, autorinome_cognome, datapubblicazione, editore, genere, lingua,
                                      formato,
                                      nome_serie_di_appartenenza, issn_serie_di_appartenenza)
VALUES ('I S D A 1', '333', 'jkr', '2023-02-03', 'Io', 'fts', 'ita', 'Ebook', 'I S D A', '999'),
       ('I S D A 2', '334', 'jkr', '2024-02-03', 'Io', 'fts', 'ita', 'Ebook', 'I S D A', '999');

INSERT INTO b.ins_libri_autore_serie (titolo, ISBN, autorinome_cognome, datapubblicazione, editore, genere, lingua,
                                      formato,
                                      nome_serie_di_appartenenza, issn_serie_di_appartenenza)
VALUES ('I S D A 1', '333', 'jkr', '2023-02-03', 'Io', 'fts', 'ita', 'Cartaceo', 'I S D A', '222'),
       ('I S D A 2', '333', 'jkr', '2024-02-03', 'Io', 'fts', 'ita', 'Cartaceo', 'I S D A', '222');

