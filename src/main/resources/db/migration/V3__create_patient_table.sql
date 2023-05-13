CREATE TABLE IF NOT EXISTS patients
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(50)  NOT NULL,
    surname      VARCHAR(50)  NOT NULL,
    pesel        VARCHAR(12)  NOT NULL,
    phone_number VARCHAR(50)  NOT NULL,
    username     VARCHAR(255) NOT NULL,
    FOREIGN KEY (username) REFERENCES users (username)
)