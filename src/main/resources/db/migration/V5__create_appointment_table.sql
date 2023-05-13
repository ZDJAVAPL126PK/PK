CREATE TABLE IF NOT EXISTS appointments
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    status        VARCHAR(20) NOT NULL,
    CHECK (status IN ('CANCELED', 'CONFIRMED', 'AWAITING')),
    date_and_time TIMESTAMP   NOT NULL,
    patient_id    BIGINT      NOT NULL,
    doctor_id     BIGINT      NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES patients (id),
    FOREIGN KEY (doctor_id) REFERENCES doctors (id)
)