--Insert Articolo Autore
INSERT INTO b.ins_articolo_autore(doi, titolo, autorinome_cognome, datapubblicazione, disciplina, editore, lingua, formato)
values ('10.1101/2020.12.31', 'La scoperta delle proteine', 'Mario_Rossi Giuseppe_Bianchi', '2020-12-31', 'Natura','Nature Publishing Group', 'Italiano', 'Cartaceo'),
       ('10.1101/2021.12.31', 'Il futuro dell''intelligenza artificiale', 'John_Smith Jane_Doe', '2021-12-31', 'Informatica', 'Springer', 'Inglese', 'Digitale'),
       ('10.1101/2022.12.31', 'Il futuro dell''energia', 'Luca_Bianchi Roberto_Neri', '2022-12-31', 'Energia', 'Wiley', 'Italiano', 'Cartaceo'),
       ('10.1101/2023.12.31', 'Il futuro dell''automobile', 'Mark_Johnson David_Williams', '2023-12-31', 'Meccanina', 'Elsevier', 'Inglese', 'Digitale'),
       ('10.1101/2024.12.31', 'Il futuro dell''informatica', 'Andrea_Rossi Giuseppe_Verdi', '2024-12-31', 'Informatica','Springer', 'Italiano', 'Cartaceo'),
       ('10.1101/2025.12.31', 'Il futuro dell''intelligenza artificiale', 'Luca_Bianchi Roberto_Neri', '2025-12-31', 'Informaitca', 'Nature Publishing Group', 'Inglese', 'Digitale'),
       ('10.1101/2026.12.31', 'Il futuro della medicina', 'John_Smith Jane_Doe', '2026-12-31', 'Meidicina', 'Wiley', 'Italiano', 'Cartaceo'),
       ('10.1101/2027.12.31', 'Il futuro delle tecnologie', 'Mark_Johnson David_Williams', '2027-12-31', 'Tecnologia', 'Elsevier', 'Inglese', 'Digitale'),
       ('10.1101/2028.12.31', 'Il futuro dell''energia', 'Andrea_Rossi Giuseppe_Verdi', '2028-12-31', 'Energia', 'Springer', 'Italiano', 'Cartaceo'),
       ('10.1101/2029.12.31', 'Il futuro dell''automobile', 'Luca_Bianchi Roberto_Neri', '2029-12-31', 'Meccanina', 'Nature Publishing Group', 'Inglese', 'Digitale'),
       ('10.1101/2030.12.31', 'Il futuro della fessa', 'John_Smith Jane_Doe', '2030-12-31', 'Anatomia', 'Wiley', 'Italiano', 'Cartaceo'),
       ('10.1101/2031.12.31', 'Il futuro della fessa artificiale', 'Mark_Johnson David_Williams', '2031-12-31', 'Anatomia', 'Elsevier', 'Inglese', 'Digitale');

--Insert Libro Autore Serie
INSERT INTO b.ins_libro_autore_serie (titolo, ISBN, autorinome_cognome, datapubblicazione, editore, genere, lingua, formato,
                                     nome_serie_di_appartenenza, issn_serie_di_appartenenza)
VALUES ('Il Signore degli Anelli 1', '978-88-17-88000-0', 'J.R.R._Tolkien', '1954-07-29', 'Mondadori', 'Fantasy',
         'Italiano', 'Ebook', 'Il Signore degli Anelli', '978-88-04-58339-8');

--Insert Presentazione
INSERT INTO b.ins_presentazione (ISBN, Indirizzo, StrutturaOspitante, DataInizio, DataFine, Responsabile)
VALUES ('978-88-17-88000-0', 'Via Roma 1', 'Casa Editrice', '2018-01-01', '2018-01-01', 'Mario Rossi');

--Insert Stock
INSERT INTO b.ins_stock_libro(id_negozio, isbn, quantita)
    VALUES (1,'978-88-17-88000-0', 35),
           (99,'978-88-17-88000-0', 22);

--Insert Articolo Rivista
INSERT INTO b.ins_rivista (issn, nome, argomento, datapubblicazione, responsabile, doi_articoli_pubblicati)
VALUES ('978-88-04-58339-8', 'Il Signore degli Anelli', 'Fantasy', '1954-07-29', 'J.R.R._Tolkien', '10.1101/2020.12.31 10.1101/2021.12.31');

--Insert Articolo Conferenza
INSERT INTO b.ins_conferenza (indirizzo, strutturaospitante, datainizio, datafine, responsabile, doi_articoli_presentati)
VALUES ('Via Roma 1', 'Casa Editrice', '2018-01-01', '2018-01-01', 'Mario Rossi', '10.1101/2027.12.31 10.1101/2031.12.31');