CREATE TABLE IF NOT EXISTS doctors
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    title        VARCHAR(255),
    name         VARCHAR(50)  NOT NULL,
    surname      VARCHAR(50)  NOT NULL,
    description  VARCHAR(255),
    phone_number VARCHAR(50)  NOT NULL,
    username     VARCHAR(255) NOT NULL,
    FOREIGN KEY (username) REFERENCES users (username)
);
