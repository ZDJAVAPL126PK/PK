ALTER TABLE appointments ADD COLUMN doctor_id INT;
ALTER TABLE appointments ADD FOREIGN KEY (doctor_id) REFERENCES doctors(id);