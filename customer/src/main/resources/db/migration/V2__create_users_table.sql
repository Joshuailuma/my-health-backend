-- Table: users
CREATE TABLE users (
    id UUID PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    phone_number VARCHAR(15),
    registration_status VARCHAR(50),
    date_of_birth DATE,
    country_code VARCHAR(5),
    rating VARCHAR(10),
    gender VARCHAR(50),
    role VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    latitude DECIMAL,
    longitude DECIMAL
);

-- Add a trigger to update the `updated_at` column automatically
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_updated_at
BEFORE UPDATE ON users
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

-- Table: patient
CREATE TABLE patient (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE
);

-- Table: doctor
CREATE TABLE doctor (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    certificate_id VARCHAR(255),
    specialization VARCHAR(255)
);

-- Table: certificate
CREATE TABLE certificate (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(100) NOT NULL,
    file_url VARCHAR(255) NOT NULL,
    doctor_id UUID NOT NULL REFERENCES doctor(id) ON DELETE CASCADE
);
