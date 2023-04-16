CREATE TABLE IF NOT EXISTS address (
                                        id INT PRIMARY KEY AUTO_INCREMENT,
                                        city VARCHAR(100) NOT NULL,
                                        street VARCHAR(50) NOT NULL,
                                        postCode VARCHAR(50) NOT NULL
)