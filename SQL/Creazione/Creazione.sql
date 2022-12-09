CREATE SCHEMA b;

CREATE TABLE b.Libro
(
    ID_Libro          INTEGER,
    ISBN              VARCHAR(13),
    Titolo            VARCHAR(32),
    DataPubblicazione DATE,
    Prezzo            DOUBLE PRECISION,
    Editore           VARCHAR(64),
    Lingua            VARCHAR(32),
    Formato           VARCHAR(32),
    Tipo              VARCHAR(32),
    Genere            VARCHAR(32),
    Descrizione       VARCHAR(128),

    CONSTRAINT Libro_PK PRIMARY KEY (ID_Libro)
);

CREATE TABLE b.Articolo
(
    ID_Articolo       INTEGER, --PK
    DOI               VARCHAR(13),
    Titolo            VARCHAR(64),
    DataPubblicazione DATE,
    Editore           VARCHAR(64),
    Disciplina        VARCHAR(32),
    Formato           VARCHAR(32),
    Lingua            VARCHAR(32),

    CONSTRAINT Articolo_PK PRIMARY KEY (ID_Articolo)
);


CREATE TABLE b.Rivista
(
    ID_Rivista        INTEGER,     --PK
    ISBN              VARCHAR(13), --o int
    Editore           VARCHAR(64),
    Numero            INTEGER,
    DataPubblicazione DATE,
    Formato           VARCHAR(32),
    Lingua            VARCHAR(32),
    Curatore          VARCHAR(64),

    CONSTRAINT Rivista_PK PRIMARY KEY (ID_Rivista)
);

CREATE TABLE b.Conferenza
(
    ID_Conferenza      INTEGER, --PK
    Indirizzo          VARCHAR(64),
    StrutturaOspitante VARCHAR(32),
    Responsabile       VARCHAR(32),
    DataOraInizio      TIMESTAMP,
    DataOraFine        TIMESTAMP,

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
    ID_Autore   INTEGER,
    Nome        VARCHAR(32),
    Cognome     VARCHAR(32),
    DataNascita DATE,
    Nazionalità VARCHAR(32),

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
    Titolo      VARCHAR(32),
    Descrizione VARCHAR(128),

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
    Indirizzo        VARCHAR(64),
    Descrizione      VARCHAR(128),
    DataOraInizio    TIMESTAMP,
    DataOraFine      TIMESTAMP,

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
    ISSN        VARCHAR(8),
    Titolo      VARCHAR(32),
    Descrizione VARCHAR(128),

    CONSTRAINT Serie_PK PRIMARY KEY (ID_Serie)
);

CREATE TABLE b.Sequel
(
    Serie    INTEGER, --FK
    LibroPre INTEGER, --FK
    LibroSeq INTEGER, --FK

    CONSTRAINT Serie_FK
        FOREIGN KEY (Serie) REFERENCES b.Serie (ID_Serie),
    CONSTRAINT LibroPRE_FK
        FOREIGN KEY (LibroPre) REFERENCES b.Libro (ID_Libro),
    CONSTRAINT LibroSEQ_FK
        FOREIGN KEY (LibroSeq) REFERENCES b.Libro (ID_Libro)
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
)
