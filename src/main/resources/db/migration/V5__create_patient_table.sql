CREATE TABLE IF NOT EXISTS patients
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(50)  NOT NULL,
    surname     VARCHAR(50)  NOT NULL,
    pesel       VARCHAR(12)  NOT NULL,
    email       VARCHAR(255) NOT NULL,
    phoneNumber VARCHAR(50)  NOT NULL
)