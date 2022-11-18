CREATE TABLE persons (
    Name VARCHAR PRIMARY KEY,
    Age INTEGER,
    DriversLicense BOOLEAN
);

CREATE TABLE cars (
    Id SERIAL PRIMARY KEY,
    Brand VARCHAR,
    Model VARCHAR,
    Price NUMERIC
);

CREATE TABLE carDrivers (
    Id SERIAL PRIMARY KEY,
    Driver VARCHAR,
    CarId INTEGER,
    FOREIGN KEY (Driver) REFERENCES persons (Name),
    FOREIGN KEY (CarId) REFERENCES cars (Id)
)