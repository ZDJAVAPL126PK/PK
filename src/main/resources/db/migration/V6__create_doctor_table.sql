CREATE TABLE IF NOT EXISTS doctor (
                                       id INT PRIMARY KEY AUTO_INCREMENT,
                                       title VARCHAR(50),
                                       name VARCHAR(50) NOT NULL,
                                       surname VARCHAR(50) NOT NULL,
                                       description VARCHAR(50),
                                       email VARCHAR(50) NOT NULL,
                                       phoneNumber VARCHAR(50) NOT NULL
)