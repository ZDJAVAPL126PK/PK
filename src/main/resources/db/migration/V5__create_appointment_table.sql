CREATE TABLE IF NOT EXISTS appointments
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(20) NOT NULL,
    CHECK  (status IN ('CANCELED', 'CONFIRMED', 'AWAITING')),
    date_and_time TIMESTAMP NOT NULL,
    patient_id INT,
    FOREIGN KEY (patient_id) REFERENCES patients(id)
)