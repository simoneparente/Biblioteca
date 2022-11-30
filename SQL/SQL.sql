CREATE SCHEMA p;

CREATE TABLE p.Negozio
(
    ID_Negozio INT,
    Nome VARCHAR(32),
    CONSTRAINT Negozio_PK PRIMARY KEY(ID_Negozio)
);

CREATE TABLE p.Libro
(
    ISSN INT,
    Titolo VARCHAR(32),
    Autore VARCHAR(32),
    Prezzo_copertina DOUBLE PRECISION,

    CONSTRAINT Libro_PK PRIMARY KEY (ISSN)
);

CREATE TABLE p.StockLibri
(
    ISSN INT,
    Negozio INT,
    Quantita INT,
    Prezzo_vendita DOUBLE PRECISION,

    CONSTRAINT Stock_PK PRIMARY KEY (ISSN, Negozio),
    CONSTRAINT Stock_ISSN FOREIGN KEY (ISSN) REFERENCES p.Libro(ISSN),
    CONSTRAINT Stock_Negozio FOREIGN KEY(Negozio) REFERENCES p.Negozio(ID_Negozio)
);

CREATE TABLE p.StockArticoli
(
    DOI VARCHAR(64),
    
)