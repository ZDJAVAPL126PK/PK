CREATE TABLE IF NOT EXISTS users
(
    username VARCHAR(255) PRIMARY KEY NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    enabled BOOL DEFAULT TRUE,
    role VARCHAR(30) NOT NULL,
    CHECK  (role IN ('ROLE_ADMIN', 'ROLE_DOCTOR', 'ROLE_PATIENT'))
);

INSERT INTO users (username, password, role, email)
VALUES ('admin', '$2a$10$f6K9roeLJp3z97lnJ0mu2.QtMMk.3HmTPB4kT9xsau0Ll.nbvJ55q', 'ROLE_ADMIN', 'example@email.com'),
       ('doctor', '$2a$10$f6K9roeLJp3z97lnJ0mu2.QtMMk.3HmTPB4kT9xsau0Ll.nbvJ55q', 'ROLE_DOCTOR', 'example@email.com'),
       ('patient', '$2a$10$f6K9roeLJp3z97lnJ0mu2.QtMMk.3HmTPB4kT9xsau0Ll.nbvJ55q', 'ROLE_PATIENT', 'example@email.com');