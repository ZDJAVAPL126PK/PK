CREATE TABLE IF NOT EXISTS addresses
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    city       VARCHAR(100) NOT NULL,
    street     VARCHAR(50)  NOT NULL,
    postCode   VARCHAR(25)  NOT NULL,
    doctor_id  BIGINT,
    patient_id BIGINT,
    FOREIGN KEY (doctor_id) REFERENCES doctors (id),
    FOREIGN KEY (patient_id) REFERENCES patients (id)
);