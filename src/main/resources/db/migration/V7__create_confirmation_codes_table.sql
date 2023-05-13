CREATE TABLE IF NOT EXISTS confirmation_codes
(
    code                 VARCHAR(36) PRIMARY KEY NOT NULL,
    expiration_timestamp TIMESTAMP
);
