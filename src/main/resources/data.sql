DROP TABLE IF EXISTS users;

CREATE TABLE users (
                              id INT AUTO_INCREMENT  PRIMARY KEY,
                              login VARCHAR(250) NOT NULL,
                              password VARCHAR(250) NOT NULL
);

INSERT INTO users (login, password) VALUES
('artyomnurgaliev', 'password');