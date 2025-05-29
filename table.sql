CREATE DATABASE IF NOT EXISTS flight;
USE flight;

-- Flight table
CREATE TABLE Flight (
    Id VARCHAR(50) PRIMARY KEY,
    `From` VARCHAR(255),
    `To` VARCHAR(255),
    Departure DATETIME,
    Arrival DATETIME,
    Status ENUM('Scheduled', 'Delayed', 'Cancelled', 'Departed', 'Arrived') DEFAULT 'Scheduled',
    SeatAvailable INT
);

-- User table
CREATE TABLE User (
    Id VARCHAR(50) PRIMARY KEY,
    `Date` DATE,
    FullName NVARCHAR(50),
    gender ENUM('M', 'F'),
    age INT,
    Phone VARCHAR(15)
);

-- Seat table (seat definitions, classes, prices)
CREATE TABLE Seat (
    SeatId VARCHAR(20) PRIMARY KEY,
    Class ENUM('economy', 'business', 'first class'),
    Price INT
);

-- New: FlightSeat table to assign seats to flights and track availability
CREATE TABLE FlightSeat (
    FlightId VARCHAR(50),
    SeatId VARCHAR(20),
    SeatStatus ENUM('Available', 'Booked', 'Unavailable') DEFAULT 'Available',
    PRIMARY KEY (FlightId, SeatId),
    FOREIGN KEY (FlightId) REFERENCES Flight(Id) ON DELETE CASCADE,
    FOREIGN KEY (SeatId) REFERENCES Seat(SeatId) ON DELETE CASCADE
);

-- Ticket table
CREATE TABLE Ticket (
    Id VARCHAR(50) PRIMARY KEY,
    UserId VARCHAR(50),
    FlightId VARCHAR(50),
    SeatId VARCHAR(20),
    `Time` DATETIME,
    FOREIGN KEY (UserId) REFERENCES User(Id) ON DELETE CASCADE,
    FOREIGN KEY (FlightId) REFERENCES Flight(Id) ON DELETE CASCADE,
    FOREIGN KEY (SeatId) REFERENCES Seat(SeatId) ON DELETE CASCADE
);
