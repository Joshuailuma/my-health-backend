CREATE TABLE Patient (
  id INT PRIMARY KEY AUTO_INCREMENT,
  firstName VARCHAR(50) NOT NULL,
  lastName VARCHAR(50) NOT NULL,
  email VARCHAR(50) UNIQUE,
  phoneNumber VARCHAR(20),
  registrationStatus INT,
  dateOfBirth DATE,
  countryCode VARCHAR(5),
  rating DECIMAL(1,1),
  gender VARCHAR(10) CHECK (gender IN ('MALE', 'FEMALE', 'OTHER')),
);