DROP SCHEMA IF EXISTS b CASCADE;
CREATE SCHEMA b;

CREATE TABLE b.Articolo(
    ID_Articolo        SERIAL,
    Titolo             VARCHAR(128),
    DOI                VARCHAR(128),
    DataPubblicazione  DATE,
    Editore            VARCHAR(128),
    Lingua             VARCHAR(128),
    Formato            VARCHAR(128),

    CONSTRAINT PK_Articolo PRIMARY KEY (ID_Articolo),
    CONSTRAINT UK_Articolo UNIQUE (DOI)
);

CREATE TABLE b.Autore(
    ID_Autore          SERIAL,
    Nome               VARCHAR(128),
    Cognome            VARCHAR(128),

    CONSTRAINT PK_Autore PRIMARY KEY (ID_Autore)
);

CREATE TABLE b.AutoreArticolo(
    ID_Autore          SERIAL,
    ID_Articolo        SERIAL,

    CONSTRAINT PK_AutoreArticolo PRIMARY KEY (ID_Autore, ID_Articolo),
    CONSTRAINT FK_AutoreArticolo_Autore FOREIGN KEY (ID_Autore) REFERENCES b.Autore(ID_Autore),
    CONSTRAINT FK_AutoreArticolo_Articolo FOREIGN KEY (ID_Articolo) REFERENCES b.Articolo(ID_Articolo)
);

CREATE TABLE b.Rivista(
    ID_Rivista         SERIAL,
    ISSN               VARCHAR(128),
    Nome               VARCHAR(128),
    Argomento          VARCHAR(128),
    DataPubblicazione  DATE,
    Responsabile       VARCHAR(128),

    CONSTRAINT PK_Rivista PRIMARY KEY (ID_Rivista)
);

CREATE TABLE b.ArticoloInRivista(
    ID_Articolo        SERIAL,
    ID_Rivista         SERIAL,

    CONSTRAINT PK_ArticoloInRivista PRIMARY KEY (ID_Articolo, ID_Rivista),
    CONSTRAINT FK_ArticoloInRivista_Articolo FOREIGN KEY (ID_Articolo) REFERENCES b.Articolo(ID_Articolo),
    CONSTRAINT FK_ArticoloInRivista_Rivista FOREIGN KEY (ID_Rivista) REFERENCES b.Rivista(ID_Rivista)
);

CREATE TABLE b.Evento(
    ID_Evento          SERIAL,
    Indirizzo          VARCHAR(128),
    StrutturaOspitante VARCHAR(128),
    DataInizio         DATE,
    DataFine           DATE,
    Responsabile       VARCHAR(128),

    CONSTRAINT PK_Evento PRIMARY KEY (ID_Evento),
    CONSTRAINT UK_Evento UNIQUE (Indirizzo, StrutturaOspitante, DataInizio, DataFine, Responsabile)
);

CREATE TABLE b.Conferenza(
    Articolo        SERIAL,
    Evento          SERIAL,

    CONSTRAINT PK_Conferenza PRIMARY KEY (Articolo, Evento),
    CONSTRAINT FK_Conferenza_Articolo FOREIGN KEY (Articolo) REFERENCES b.Articolo(ID_Articolo),
    CONSTRAINT FK_Conferenza_Evento FOREIGN KEY (Evento) REFERENCES b.Evento(ID_Evento)
);

CREATE TABLE b.Libro(
    ID_Libro           SERIAL,
    Titolo             VARCHAR(128),
    ISBN               VARCHAR(128),
    DataPubblicazione  DATE,
    Editore            VARCHAR(128),
    Genere             VARCHAR(128),
    Lingua             VARCHAR(128),
    Formato            VARCHAR(128),
    Prezzo             FLOAT,

    CONSTRAINT PK_Libro PRIMARY KEY (ID_Libro),
    CONSTRAINT UK_Libro UNIQUE (ISBN),
    CONSTRAINT CK_Libro CHECK (Prezzo > 0)
);

CREATE TABLE b.AutoreLibro(
    ID_Autore          SERIAL,
    ID_Libro           SERIAL,

    CONSTRAINT PK_AutoreLibro PRIMARY KEY (ID_Autore, ID_Libro),
    CONSTRAINT FK_AutoreLibro_Autore FOREIGN KEY (ID_Autore) REFERENCES b.Autore(ID_Autore),
    CONSTRAINT FK_AutoreLibro_Libro FOREIGN KEY (ID_Libro) REFERENCES b.Libro(ID_Libro)
);

CREATE TABLE b.Presentazione(
    Evento             SERIAL,
    Libro              SERIAL,

    CONSTRAINT PK_Presentazione PRIMARY KEY (Evento, Libro),
    CONSTRAINT FK_Presentazione_Evento FOREIGN KEY (Evento) REFERENCES b.Evento(ID_Evento),
    CONSTRAINT FK_Presentazione_Libro FOREIGN KEY (Libro) REFERENCES b.Libro(ID_Libro)
);

CREATE TABLE b.Serie(
    ID_Serie           SERIAL,
    ISSN               VARCHAR(128),
    Nome               VARCHAR(128),

    CONSTRAINT PK_Serie PRIMARY KEY (ID_Serie),
    CONSTRAINT UK_Serie UNIQUE (ISSN)
);

CREATE TABLE b.LibroINSerie(
    ID_Serie           INTEGER,
    Libro              INTEGER,
    LibroSuccessivo    INTEGER,

    CONSTRAINT FK_Serie_Libro FOREIGN KEY (Libro) REFERENCES b.Libro(ID_Libro),
    CONSTRAINT FK_Serie_LibroSuccessivo FOREIGN KEY (LibroSuccessivo) REFERENCES b.Libro(ID_Libro)
);

CREATE TABLE b.Negozio(
    ID_Negozio         SERIAL,
    Nome               VARCHAR(128),
    Tipo               VARCHAR(128),

    CONSTRAINT PK_Negozio PRIMARY KEY (ID_Negozio)
);

CREATE TABLE b.Stock(
    Negozio            SERIAL,
    Libro              SERIAL,
    Quantita           INTEGER, -- se quantita=0 la tupla viene eliminata (scoprire come si fa)

    CONSTRAINT FK_Stock_Negozio FOREIGN KEY (Negozio) REFERENCES b.Negozio(ID_Negozio),
    CONSTRAINT FK_Stock_Libro FOREIGN KEY (Libro) REFERENCES b.Libro(ID_Libro)
);

CREATE TABLE b.Utente(
    ID_Utente          SERIAL,
    Username           VARCHAR(128),
    Password           VARCHAR(128),

    CONSTRAINT PK_Utente PRIMARY KEY (ID_Utente),
    CONSTRAINT UK_Utente UNIQUE (Username)
);

CREATE TABLE b.Richiesta(
    ID_Utente         SERIAL,
    ID_Serie          SERIAL,
    Disponibilità     BOOLEAN,

    CONSTRAINT PK_Richiesta PRIMARY KEY (ID_Utente, ID_Serie),
    CONSTRAINT FK_Richiesta_Utente FOREIGN KEY (ID_Utente) REFERENCES b.Utente(ID_Utente),
    CONSTRAINT FK_Richiesta_Serie FOREIGN KEY (ID_Serie) REFERENCES b.Serie(ID_Serie)
);