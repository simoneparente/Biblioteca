CREATE SCHEMA p;

CREATE TABLE p.Utente
(
    username VARCHAR(64),      --PK
    email VARCHAR(64),
    password VARCHAR(64),

    CONSTRAINT PK_Utente PRIMARY KEY (username)
);

INSERT INTO p.Utente (username, email, password)
(
    VALUES ('Simone', 'simone@gmail.it', 'Clemente'),
           ('Mario', 'mario@gmail.it', 'Pinguino' ),
           ('Davide', 'davide@gmail.it', 'Mammt' ),
           ('Stefano', 'stefano@gmail.it', 'machiavass' )
);
