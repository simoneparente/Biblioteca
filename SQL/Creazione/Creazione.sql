DROP SCHEMA b cascade ;
CREATE SCHEMA b;

CREATE TABLE b.Libro
(
    ID_Libro          INTEGER,     --PK
    ISBN              VARCHAR(13)       NOT NULL,
    Titolo            VARCHAR(32)       NOT NULL,
    DataPubblicazione DATE              NOT NULL,
    Prezzo            DOUBLE PRECISION  NOT NULL,
    Editore           VARCHAR(64)       NOT NULL,
    Lingua            VARCHAR(32)       NOT NULL,
    Formato           VARCHAR(32)       NOT NULL,
    Tipo              VARCHAR(32)       NOT NULL,
    Genere            VARCHAR(32)       NOT NULL,
    Descrizione       VARCHAR(128),

    CONSTRAINT Libro_PK PRIMARY KEY (ID_Libro)
);

CREATE TABLE b.Articolo
(
    ID_Articolo       INTEGER, --PK
    DOI               VARCHAR(13),
    Titolo            VARCHAR(64) NOT NULL,
    DataPubblicazione DATE        NOT NULL,
    Editore           VARCHAR(64) NOT NULL,
    Disciplina        VARCHAR(32) NOT NULL,
    Formato           VARCHAR(32) NOT NULL,
    Lingua            VARCHAR(32) NOT NULL,

    CONSTRAINT Articolo_PK PRIMARY KEY (ID_Articolo),
    CONSTRAINT UNIQUE_DOI UNIQUE (DOI)
);


CREATE TABLE b.Rivista
(
    ID_Rivista        INTEGER,     --PK
    Nome              VARCHAR(64) NOT NULL,
    Numero            INTEGER     NOT NULL,
    ISBN              VARCHAR(13), --o int
    Editore           VARCHAR(64) NOT NULL,
    DataPubblicazione DATE        NOT NULL,
    Formato           VARCHAR(32) NOT NULL,
    Lingua            VARCHAR(32) NOT NULL,
    Curatore          VARCHAR(64) NOT NULL,

    CONSTRAINT Rivista_PK PRIMARY KEY (ID_Rivista),
    CONSTRAINT UNIQUE_nome_numero UNIQUE (Nome, Numero),
    CONSTRAINT UNIQUE_ISBN UNIQUE (ISBN)
);

CREATE TABLE b.Conferenza
(
    ID_Conferenza      INTEGER, --PK
    Indirizzo          VARCHAR(64) NOT NULL,
    StrutturaOspitante VARCHAR(32) NOT NULL,
    Responsabile       VARCHAR(32) NOT NULL,
    DataOraInizio      TIMESTAMP   NOT NULL,
    DataOraFine        TIMESTAMP, --NOT NULL?

    CONSTRAINT Conferenza_PK PRIMARY KEY (ID_Conferenza)
);

CREATE TABLE b.ArticoloInRivista
(
    Articolo INTEGER, --FK
    Rivista  INTEGER, --FK

    CONSTRAINT Articolo_FK
        FOREIGN KEY (Articolo) REFERENCES b.Articolo (ID_Articolo),
    CONSTRAINT Rivista_FK
        FOREIGN KEY (Rivista) REFERENCES b.Rivista (ID_Rivista)
);

CREATE TABLE b.ArticoloInConferenza
(
    Conferenza INTEGER, --FK
    Articolo   INTEGER, --FK


    CONSTRAINT Conferenza_FK
        FOREIGN KEY (Conferenza) REFERENCES b.Conferenza (ID_Conferenza),
    CONSTRAINT Articolo_FK
        FOREIGN KEY (Articolo) REFERENCES b.Articolo (ID_Articolo)
);

CREATE TABLE b.Autore
(
    ID_Autore   INTEGER,  --PK
    Nome        VARCHAR(32) NOT NULL,
    Cognome     VARCHAR(32),
    DataNascita DATE NOT NULL,
    Nazionalità VARCHAR(32) NOT NULL,

    CONSTRAINT Autore_PK PRIMARY KEY (ID_Autore)
);

CREATE TABLE b.AutoreArticolo
(
    Autore INTEGER, --FK
    Libro  INTEGER, --FK

    CONSTRAINT Autore_FK
        FOREIGN KEY (Autore) REFERENCES b.Autore (ID_Autore),
    CONSTRAINT Libro_FK
        FOREIGN KEY (Libro) REFERENCES b.Libro (ID_Libro)
);

CREATE TABLE b.Collana
(
    ID_Collana  INTEGER,
    Titolo      VARCHAR(32) NOT NULL,
    Descrizione VARCHAR(128) NOT NULL,

    CONSTRAINT Collana_PK PRIMARY KEY (ID_Collana)
);

CREATE TABLE b.LibroInCollana
(
    Collana INTEGER, --FK
    Libro   INTEGER, --FK

    CONSTRAINT Collana_FK
        FOREIGN KEY (Collana) REFERENCES b.Collana (ID_Collana),
    CONSTRAINT Libro_FK
        FOREIGN KEY (Libro) REFERENCES b.Libro (ID_Libro)
);

CREATE TABLE b.Presentazione
(
    ID_Presentazione INT,
    Indirizzo        VARCHAR(64) NOT NULL,
    Descrizione      VARCHAR(128) NOT NULL,
    DataOraInizio    TIMESTAMP NOT NULL,
    DataOraFine      TIMESTAMP, --NOT NULL?

    CONSTRAINT Presentazione_PK PRIMARY KEY (ID_Presentazione)
);

CREATE TABLE b.PresentazioneLibro
(
    Presentazione INTEGER, --FK
    Libro         INTEGER, --FK

    CONSTRAINT Presentazione_FK
        FOREIGN KEY (Presentazione) REFERENCES b.Presentazione (ID_Presentazione),
    CONSTRAINT Libro_FK
        FOREIGN KEY (Libro) REFERENCES b.Libro (ID_Libro)
);

CREATE TABLE b.Serie
(
    ID_Serie    INTEGER,
    ISSN        VARCHAR(8) UNIQUE,
    Titolo      VARCHAR(32) NOT NULL,
    Descrizione VARCHAR(128) NOT NULL,

    CONSTRAINT Serie_PK PRIMARY KEY (ID_Serie)
);


CREATE TABLE b.Sequel
(
    Serie    INTEGER, --FK
    ID_Libro INTEGER, --FK
    Numero INTEGER,

    CONSTRAINT Serie_FK
        FOREIGN KEY (Serie) REFERENCES b.Serie (ID_Serie),
    CONSTRAINT Libro_FK
        FOREIGN KEY (ID_Libro) REFERENCES b.Libro (ID_Libro),
    CONSTRAINT UNIQUE_numero_serie UNIQUE (Numero, Serie)
);

CREATE TABLE b.Negozio
(
    ID_Negozio     INTEGER,
    Nome           VARCHAR(32),
    P_IVA          VARCHAR(11), --o int
    Indirizzo      VARCHAR(64),
    NumeroTelefono VARCHAR(32), --o int
    URL            VARCHAR(64),

    CONSTRAINT Serie_FK PRIMARY KEY (ID_Negozio)
);


CREATE TABLE b.Stock
(
    Negozio INTEGER, --FK
    Serie   INTEGER, --FK

    CONSTRAINT Negozio_FK
        FOREIGN KEY (Negozio) REFERENCES b.Negozio (ID_Negozio),
    CONSTRAINT Serie_FK
        FOREIGN KEY (Serie) REFERENCES b.Serie (ID_Serie)
);

CREATE TABLE b.Utente
(
    ID_Utente INTEGER,
    Username  VARCHAR(32),
    eMail     VARCHAR(128),
    Password  VARCHAR(32),

    CONSTRAINT Utente_PK PRIMARY KEY (ID_Utente)
);

CREATE TABLE b.Richiesta
(
    ID_Richiesta INTEGER,
    Serie        INTEGER,     --FK
    Utente       INTEGER,     --FK

    CONSTRAINT Richiesta_PK PRIMARY KEY (ID_Richiesta),

    CONSTRAINT Serie_FK
        FOREIGN KEY (Serie) REFERENCES b.Serie (ID_Serie),
    CONSTRAINT Utente_FK
        FOREIGN KEY (Utente) REFERENCES b.Utente (ID_Utente)
);

CREATE TABLE b.Notifica
(
    ID_Notifica   INTEGER,
    DataEmissione TIMESTAMP,
    Negozio       INTEGER, --FK
    Richiesta     INTEGER, --FK

    CONSTRAINT Notifica_PK PRIMARY KEY (ID_Notifica),

    CONSTRAINT Negozio_FK
        FOREIGN KEY (Negozio) REFERENCES b.Negozio (ID_Negozio),
    CONSTRAINT Richiesta_FK
        FOREIGN KEY (Richiesta) REFERENCES b.Richiesta (ID_Richiesta)
);


CREATE VIEW b.vistalibri AS
    SELECT

CREATE OR REPLACE VIEW b.VistaLibriPresentazioni AS
SELECT id_libro, l.titolo, p.indirizzo
FROM (b.libro l NATURAL JOIN b.sequel s) ;



CREATE OR REPLACE TRIGGER insertlibroserie INSTEAD OF INSERT ON b.VistaLibri
    EXECUTE FUNCTION b.insertlibroserie();

INSERT INTO b.VistaLibri(id_libro, isbn, titolo, datapubblicazione, prezzo, editore, lingua, formato, tipo, genere, descrizione, serie, numero)
(
    values (1, '1', 'titolo1', '2019-01-01', 1, 'editore1', 'lingua1', 'formato1', 'tipo1', 'genere1', 'descrizione1', 1, 1)
)