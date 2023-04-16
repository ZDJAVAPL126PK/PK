CREATE TABLE IF NOT EXISTS addresses
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    city VARCHAR(100) NOT NULL,
    street VARCHAR(50) NOT NULL,
    postCode VARCHAR(25) NOT NULL,
    doctor_id INT,
    FOREIGN KEY (doctor_id) REFERENCES doctors (id)
);