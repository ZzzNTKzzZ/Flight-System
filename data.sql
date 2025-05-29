-- Use your database
USE flight;

-- Disable safe updates to allow deletions
SET SQL_SAFE_UPDATES = 0;

-- Delete data in order respecting foreign key constraints
DELETE FROM Ticket;  -- Tickets depend on User, Flight, Seat
DELETE FROM User;
DELETE FROM Flight;
DELETE FROM Seat;

-- Re-enable safe updates
SET SQL_SAFE_UPDATES = 1;

-- Insert Flights
INSERT INTO Flight (Id, `From`, `To`, Departure, Arrival, Status, SeatAvailable) VALUES
('FL001', 'New York', 'Los Angeles', '2025-06-01 08:00:00', '2025-06-01 11:00:00', 'Scheduled', 150),
('FL002', 'Chicago', 'Miami', '2025-06-01 09:00:00', '2025-06-01 13:00:00', 'Scheduled', 120),
('FL003', 'Dallas', 'Seattle', '2025-06-02 07:30:00', '2025-06-02 10:30:00', 'Delayed', 100),
('FL004', 'San Francisco', 'Denver', '2025-06-02 14:00:00', '2025-06-02 17:00:00', 'Scheduled', 80),
('FL005', 'Boston', 'Atlanta', '2025-06-03 12:00:00', '2025-06-03 15:30:00', 'Cancelled', 0),
('FL006', 'Houston', 'Orlando', '2025-06-03 16:00:00', '2025-06-03 19:00:00', 'Scheduled', 60),
('FL007', 'Las Vegas', 'San Diego', '2025-06-04 10:00:00', '2025-06-04 11:30:00', 'Scheduled', 70),
('FL008', 'Philadelphia', 'Detroit', '2025-06-04 13:00:00', '2025-06-04 15:00:00', 'Departed', 30),
('FL009', 'Phoenix', 'Minneapolis', '2025-06-05 09:00:00', '2025-06-05 12:00:00', 'Scheduled', 110),
('FL010', 'Charlotte', 'Portland', '2025-06-05 14:00:00', '2025-06-05 18:00:00', 'Scheduled', 90),
('FL011', 'Detroit', 'Boston', '2025-06-06 06:00:00', '2025-06-06 08:30:00', 'Arrived', 20),
('FL012', 'Seattle', 'Chicago', '2025-06-06 16:00:00', '2025-06-06 19:30:00', 'Scheduled', 50),
('FL013', 'Miami', 'New York', '2025-06-07 07:00:00', '2025-06-07 10:00:00', 'Scheduled', 130),
('FL014', 'Denver', 'Houston', '2025-06-07 12:00:00', '2025-06-07 15:00:00', 'Delayed', 40),
('FL015', 'Orlando', 'Las Vegas', '2025-06-08 08:00:00', '2025-06-08 11:00:00', 'Scheduled', 100),
('FL016', 'Chicago', 'Denver', '2025-06-09 09:30:00', '2025-06-09 11:00:00', 'Scheduled', 100),
('FL017', 'Denver', 'Chicago', '2025-06-12 14:00:00', '2025-06-12 15:30:00', 'Scheduled', 100),
('FL018', 'New York', 'Miami', '2025-06-10 07:00:00', '2025-06-10 10:00:00', 'Scheduled', 120),
('FL019', 'Miami', 'New York', '2025-06-14 17:00:00', '2025-06-14 20:00:00', 'Scheduled', 120),
('FL020', 'Seattle', 'San Francisco', '2025-06-11 08:30:00', '2025-06-11 10:00:00', 'Scheduled', 90),
('FL021', 'San Francisco', 'Seattle', '2025-06-13 12:00:00', '2025-06-13 13:30:00', 'Scheduled', 90),
('FL030', 'New York', 'Miami', '2025-06-10 07:00:00', '2025-06-10 10:00:00', 'Scheduled', 120),
('FL031', 'Miami', 'New York', '2025-06-11 09:00:00', '2025-06-12 12:00:00', 'Scheduled', 110),
('FL032', 'Miami', 'New York', '2025-06-11 17:00:00', '2025-06-12 20:00:00', 'Scheduled', 115),
('FL033', 'Miami', 'New York', '2025-06-11 20:00:00', '2025-06-12 23:00:00', 'Scheduled', 130),
('FL034', 'Los Angeles', 'Tokyo', '2025-06-15 16:00:00', '2025-06-16 08:00:00', 'Scheduled', 180),
('FL035', 'London', 'New York', '2025-06-16 09:00:00', '2025-06-16 12:00:00', 'Delayed', 170),
('FL036', 'San Diego', 'Phoenix', '2025-06-17 10:30:00', '2025-06-17 11:45:00', 'Scheduled', 60),
('FL037', 'Atlanta', 'Houston', '2025-06-18 14:00:00', '2025-06-18 16:00:00', 'Arrived', 0),
('FL038', 'Boston', 'Chicago', '2025-06-19 07:00:00', '2025-06-19 09:00:00', 'Scheduled', 130),
('FL039', 'Las Vegas', 'Denver', '2025-06-20 15:00:00', '2025-06-20 17:00:00', 'Cancelled', 0),
('FL040', 'Seattle', 'Boston', '2025-06-21 06:00:00', '2025-06-21 14:00:00', 'Scheduled', 100),
('FL041', 'New York', 'San Diego', '2025-06-22 11:00:00', '2025-06-22 14:30:00', 'Scheduled', 150),
('FL050', 'New York', 'Chicago', '2025-06-25 08:00:00', '2025-06-25 10:00:00', 'Scheduled', 10),
('FL051', 'Chicago', 'New York', '2025-06-30 18:00:00', '2025-06-30 20:00:00', 'Scheduled', 10);


-- Insert Users
INSERT INTO User (Id, `Date`, FullName, gender, age, Phone) VALUES
('U001', '1990-01-15', 'Alice Johnson', 'F', 35, '123-456-7890'),
('U002', '1985-03-22', 'Bob Smith', 'M', 38, '234-567-8901'),
('U003', '1992-07-10', 'Charlie Lee', 'M', 32, '345-678-9012'),
('U004', '1988-11-05', 'Diana Prince', 'F', 36, '456-789-0123'),
('U005', '1995-09-30', 'Ethan Hunt', 'M', 28, '567-890-1234'),
('U006', '1993-02-14', 'Fiona Gallagher', 'F', 30, '678-901-2345'),
('U007', '1980-12-20', 'George Miller', 'M', 42, '789-012-3456'),
('U008', '1999-04-18', 'Hannah Baker', 'F', 23, '890-123-4567'),
('U009', '1987-06-09', 'Ian Curtis', 'M', 36, '901-234-5678'),
('U010', '1991-08-12', 'Jane Doe', 'F', 33, '012-345-6789');

-- Insert Seats
INSERT INTO Seat (SeatId, Class, Price) VALUES
('S001', 'economy', 300),
('S002', 'economy', 320),
('S003', 'business', 600),
('S004', 'first class', 1200),
('S005', 'economy', 350),
('S006', 'business', 700),
('S007', 'first class', 1300),
('S008', 'economy', 280),
('S009', 'business', 620),
('S010', 'first class', 1250),
('S011', 'business', 650),
('S012', 'economy', 290),
('S013', 'business', 700),
('S014', 'economy', 300),
('S101', 'economy', 300),
('S102', 'economy', 300),
('S103', 'economy', 300),
('S104', 'economy', 300),
('S105', 'economy', 300),
('S106', 'economy', 300),
('S107', 'economy', 300),
('S108', 'economy', 300),
('S109', 'economy', 300),
('S110', 'economy', 300);


INSERT INTO FlightSeat (FlightId, SeatId, SeatStatus) VALUES
-- FL001
('FL001', 'S001', 'Available'),
('FL001', 'S002', 'Available'),
('FL001', 'S003', 'Available'),
('FL001', 'S004', 'Available'),
('FL001', 'S005', 'Available'),

-- FL002
('FL002', 'S002', 'Available'),
('FL002', 'S006', 'Available'),
('FL002', 'S007', 'Available'),
('FL002', 'S008', 'Available'),
('FL002', 'S009', 'Available'),

-- FL003
('FL003', 'S003', 'Available'),
('FL003', 'S010', 'Available'),
('FL003', 'S011', 'Available'),
('FL003', 'S012', 'Available'),
('FL003', 'S001', 'Available'),

-- FL004
('FL004', 'S002', 'Available'),
('FL004', 'S003', 'Available'),
('FL004', 'S004', 'Available'),
('FL004', 'S005', 'Available'),
('FL004', 'S006', 'Available'),

-- FL005
('FL005', 'S007', 'Unavailable'),
('FL005', 'S008', 'Unavailable'),

-- FL006
('FL006', 'S009', 'Available'),
('FL006', 'S010', 'Available'),
('FL006', 'S011', 'Available'),
('FL006', 'S012', 'Available'),
('FL006', 'S013', 'Available'),

-- FL007
('FL007', 'S001', 'Available'),
('FL007', 'S002', 'Available'),
('FL007', 'S014', 'Available'),

-- Assign seats to FL050 (outbound)
('FL050', 'S101', 'Available'),
('FL050', 'S102', 'Available'),
('FL050', 'S103', 'Available'),
('FL050', 'S104', 'Available'),
('FL050', 'S105', 'Available'),
('FL050', 'S106', 'Available'),
('FL050', 'S107', 'Available'),
('FL050', 'S108', 'Available'),
('FL050', 'S109', 'Available'),
('FL050', 'S110', 'Available'),

-- Assign seats to FL051 (return)
('FL051', 'S101', 'Available'),
('FL051', 'S102', 'Available'),
('FL051', 'S103', 'Available'),
('FL051', 'S104', 'Available'),
('FL051', 'S105', 'Available'),
('FL051', 'S106', 'Available'),
('FL051', 'S107', 'Available'),
('FL051', 'S108', 'Available'),
('FL051', 'S109', 'Available'),
('FL051', 'S110', 'Available');

-- Insert Tickets (last)
INSERT INTO Ticket (Id, UserId, FlightId, SeatId, `Time`) VALUES
('T001', 'U001', 'FL001', 'S001', '2025-05-20 10:00:00'),
('T002', 'U001', 'FL002', 'S003', '2025-05-21 11:00:00'),
('T003', 'U002', 'FL003', 'S004', '2025-05-22 09:30:00'),
('T004', 'U002', 'FL004', 'S002', '2025-05-23 14:00:00'),
('T005', 'U002', 'FL005', 'S006', '2025-05-24 12:00:00'),
('T006', 'U003', 'FL006', 'S005', '2025-05-25 16:00:00'),
('T007', 'U004', 'FL007', 'S007', '2025-05-26 10:00:00'),
('T008', 'U004', 'FL008', 'S008', '2025-05-27 13:00:00'),
('T009', 'U005', 'FL009', 'S009', '2025-05-28 09:00:00'),
('T010', 'U006', 'FL010', 'S010', '2025-05-29 14:00:00'),
('T011', 'U006', 'FL011', 'S010', '2025-05-30 06:00:00'),
('T012', 'U007', 'FL012', 'S011', '2025-05-31 16:00:00'),
('T013', 'U008', 'FL013', 'S012', '2025-06-01 07:00:00'),
('T014', 'U009', 'FL014', 'S013', '2025-06-02 12:00:00'),
('T015', 'U009', 'FL015', 'S014', '2025-06-03 08:00:00'),

-- Book 5 tickets for FL050 using existing users
('T101', 'U001', 'FL050', 'S101', '2025-06-10 10:00:00'),
('T102', 'U002', 'FL050', 'S102', '2025-06-10 10:01:00'),
('T103', 'U003', 'FL050', 'S103', '2025-06-10 10:02:00'),
('T104', 'U004', 'FL050', 'S104', '2025-06-10 10:03:00'),
('T105', 'U005', 'FL050', 'S105', '2025-06-10 10:04:00'),

-- Book 5 tickets for FL051 using same users for return
('T106', 'U001', 'FL051', 'S101', '2025-06-10 10:05:00'),
('T107', 'U002', 'FL051', 'S102', '2025-06-10 10:06:00'),
('T108', 'U003', 'FL051', 'S103', '2025-06-10 10:07:00'),
('T109', 'U004', 'FL051', 'S104', '2025-06-10 10:08:00'),
('T110', 'U005', 'FL051', 'S105', '2025-06-10 10:09:00');

