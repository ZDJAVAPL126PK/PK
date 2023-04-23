ALTER TABLE appointments ADD COLUMN doctor_id BIGINT;
ALTER TABLE appointments ADD FOREIGN KEY (doctor_id) REFERENCES doctors(id);