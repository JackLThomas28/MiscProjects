CREATE TABLE notes(
  id INT PRIMARY KEY AUTO_INCREMENT,
  notekey VARCHAR(255),
  title VARCHAR(255),
  body TEXT
);

INSERT INTO notes(notekey, title, body)
VALUES('FroggyFresh', 'John Cena', 'Christmas List');

INSERT INTO notes(notekey, title, body)
VALUES('Rihanna', 'Umbrella', 'Stand under my umbrella');
