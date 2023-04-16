CREATE TABLE IF NOT EXISTS doctors
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255),
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    email VARCHAR(50) NOT NULL,
    phoneNumber VARCHAR(50) NOT NULL
)
