**Articoli**: *ID_Articolo, Titolo, DOI, DataPubblicazione, Disciplina, Editore, Lingua, Formato*

**Autore**: *ID_Autore, Nome, Cognome*

**AutoreArticolo**: *ID_Autore, ID_Articolo* 
***ID_Autore*** -> **Autore**(*ID_Autore*)
***ID_Articolo*** -> **Articolo**(*ID_Articolo*)

**Riviste**: *ID_Rivista, ISSN, Nome, Argomento, DataPubblicazione, Responsabile, Prezzo*

**ArticoliInRiviste**: *ID_Rivista, ID_Articolo*
*ID_Rivista* -> **Rivista**(*ID_Rivista*)
*ID_Articolo* -> **Articolo**(*ID_Articolo*)

**Evento**: *ID_Evento, Nome, Indirizzo, StrutturaOspitante, DataInizio, DataFine, Responsabile*

**Conferenza**: *ID_Evento, ID_Articolo*
*ID_Evento* -> **Evento**(*ID_Evento*)
*ID_Articolo* -> **Articolo**(*ID_Articolo*)


**Libri**: *ID_Libro, Titolo, ISBN, DataPubblicazione, Editore, Genere, Lingua, Formato, Prezzo*

**AutoreLibro**: *ID_Autore, ID_Libro*
*ID_Autore* -> **Autore**(*ID_Autore*)
*ID_Libro* -> **Libro**(*ID_Libro*)

**Presentazione**: *ID_Evento, ID_Libro*
*ID_Evento* -> **Evento**(*ID_Evento*)
*ID_Libro* -> **Libro**(*ID_Libro*)

**Serie**: *ID_Serie, Nome, ISSN*

**LibriInSerie**: *ID_Serie, Libro, LibroSuccessivo*
*ID_Serie* -> **Serie**(*ID_Serie*)
*ID_Libro* -> **Libro**(*ID_Libro*)

**Negozio**: *ID_Negozio, Nome, Tipo*

**Stock**: *ID_Negozio, ID_Libro, Quantit*à
*ID_Negozio* -> **Negozio**(*ID_Negozio*)
*ID_Libro* -> **Libro**(*ID_Libro*)


**Utente**: *ID_Utente, Username, Password, Permessi*

**Richiesta**: *ID_Utente, ID_Serie*
*ID_Utente* -> **Utente**(*ID_Utente*)
*ID_Serie* -> **Serie**(*ID_Serie*)