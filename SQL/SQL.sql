CREATE SCHEMA p;

CREATE TABLE p.Utente
(
    username VARCHAR(64),      --PK
    email VARCHAR(64),
    password VARCHAR(64),

    CONSTRAINT PK_Utente PRIMARY KEY (username)
);

INSERT INTO p.Utente(username, email, password)
(
    VALUES ('prova', 'abcd', '1234'),
           ('prova1','abcd','4321')
);