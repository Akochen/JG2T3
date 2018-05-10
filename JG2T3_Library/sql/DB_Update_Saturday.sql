-- copy/paste everything including the inserts at the bottom...

-- Enable zero dates to make zero defaults work
SET GLOBAL sql_mode = 'IGNORE_SPACE,ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';

DROP SCHEMA IF EXISTS db_library;

CREATE SCHEMA db_library;

USE db_library;

CREATE TABLE accountid
(
 accountId VARCHAR(7) NOT NULL
   PRIMARY KEY
)
 ENGINE = InnoDB;

CREATE INDEX accountid_accountId_index
 ON accountid (accountId);

CREATE TABLE rentable
(
 rentableId  VARCHAR(7)                  NOT NULL
   PRIMARY KEY,
 upc         VARCHAR(7)                  NOT NULL,
 title       VARCHAR(100) DEFAULT 'NULL' NOT NULL,
 isbn        VARCHAR(15) DEFAULT 'NULL'  NOT NULL,
 `condition` VARCHAR(15) DEFAULT 'NULL'  NOT NULL,
 genre       VARCHAR(20) DEFAULT 'NULL'  NOT NULL,
 type        VARCHAR(10) DEFAULT 'NULL'  NOT NULL,
 isAvailable TINYINT(1) DEFAULT '1'      NOT NULL
)
 ENGINE = InnoDB;

CREATE INDEX rentable_upc_index
 ON rentable (upc);

CREATE TABLE rental
(
 rentableId    VARCHAR(7) NOT NULL
   PRIMARY KEY,
 start_date    DATETIME   NOT NULL,
 end_date      DATETIME   NOT NULL,
 userId        VARCHAR(7) NOT NULL,
 times_renewed INT        NOT NULL,
 CONSTRAINT rentableId
 UNIQUE (rentableId),
 CONSTRAINT rental_ibfk_1
 FOREIGN KEY (rentableId) REFERENCES rentable (rentableId),
 CONSTRAINT rental_accountid_accountId_fk
 FOREIGN KEY (userId) REFERENCES accountid (accountId)
)
 ENGINE = InnoDB;

CREATE INDEX rental_useraccount_UserID_fk
 ON rental (userId);

CREATE TABLE reservation
(
 reservationId         VARCHAR(7)                              NOT NULL
   PRIMARY KEY,
 upc                   VARCHAR(7)                              NOT NULL,
 userId                VARCHAR(7)                              NOT NULL,
 reservationDate       TIMESTAMP DEFAULT '0000-00-00 00:00:00' NOT NULL,
 reservationExpireDate TIMESTAMP DEFAULT '0000-00-00 00:00:00' NOT NULL,
 isActive              TINYINT(1)                              NOT NULL,
 reservationType       VARCHAR(4)                              NOT NULL,
 roomNumber            VARCHAR(7) DEFAULT 'NO-ROOM'            NOT NULL,
 CONSTRAINT reservation_rentable_upc_fk
 FOREIGN KEY (upc) REFERENCES rentable (upc),
 CONSTRAINT reservation_accountid_accountId_fk
 FOREIGN KEY (userId) REFERENCES accountid (accountId)
)
 ENGINE = InnoDB;

CREATE INDEX reservation_rentable_upc_fk
 ON reservation (upc);

CREATE INDEX reservation_accountid_accountId_fk
 ON reservation (userId);

CREATE INDEX reservation_room_roomNumber_fk
 ON reservation (roomNumber);

CREATE TABLE room
(
 roomNumber VARCHAR(7)             NOT NULL
   PRIMARY KEY,
 isReserved TINYINT(1) DEFAULT '0' NOT NULL
)
 ENGINE = InnoDB;

ALTER TABLE reservation
 ADD CONSTRAINT reservation_room_rentalroomNumber_fk
FOREIGN KEY (roomNumber) REFERENCES room (roomNumber);

CREATE TABLE staffaccount
(
 StaffID    VARCHAR(7)  NOT NULL
   PRIMARY KEY,
 FirstName  VARCHAR(30) NOT NULL,
 LastName   VARCHAR(30) NOT NULL,
 Email      VARCHAR(30) NOT NULL,
 Username   VARCHAR(30) NOT NULL,
 Password   VARCHAR(30) NOT NULL,
 StreetNum  VARCHAR(30) NOT NULL,
 StreetName VARCHAR(30) NOT NULL,
 City       VARCHAR(30) NOT NULL,
 State      VARCHAR(2)  NOT NULL,
 Zip        VARCHAR(5)  NOT NULL,
 CONSTRAINT staffaccount_accountid_accountId_fk
 FOREIGN KEY (StaffID) REFERENCES accountid (accountId)
)
 ENGINE = InnoDB;

CREATE TABLE useraccount
(
 UserID     VARCHAR(7)  NOT NULL
   PRIMARY KEY,
 FirstName  VARCHAR(30) NOT NULL,
 LastName   VARCHAR(30) NOT NULL,
 Email      VARCHAR(30) NOT NULL,
 Username   VARCHAR(30) NOT NULL,
 Password   VARCHAR(30) NOT NULL,
 StreetNum  VARCHAR(30) NOT NULL,
 StreetName VARCHAR(30) NOT NULL,
 City       VARCHAR(30) NOT NULL,
 State      VARCHAR(2)  NOT NULL,
 Zip        VARCHAR(5)  NOT NULL,
 Balance    VARCHAR(12) NOT NULL,
 CONSTRAINT useraccount_accountid_accountId_fk
 FOREIGN KEY (UserID) REFERENCES accountid (accountId)
)
 ENGINE = InnoDB;


-- test accounts
INSERT INTO accountid (accountId) VALUES ('S000001');
INSERT INTO accountid (accountId) VALUES ('U000001');

INSERT INTO staffaccount (StaffID, FirstName, LastName, Email, Username,
                     	Password, StreetNum, StreetName, City, State, Zip)
VALUES ('S000001', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x');
INSERT INTO useraccount (UserID, FirstName, LastName, Email, Username,
                    	Password, StreetNum, StreetName, City, State, Zip, Balance)
VALUES ('U000001', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', '');

-- test rooms
INSERT INTO room (roomNumber, isReserved) VALUES ('NO-ROOM', TRUE);

INSERT INTO room (roomNumber, isReserved) VALUES ('ROOM101', FALSE);
INSERT INTO room (roomNumber, isReserved) VALUES ('ROOM102', FALSE);
INSERT INTO room (roomNumber, isReserved) VALUES ('ROOM103', FALSE);
INSERT INTO room (roomNumber, isReserved) VALUES ('ROOM104', FALSE);
INSERT INTO room (roomNumber, isReserved) VALUES ('ROOM105', FALSE);
INSERT INTO room (roomNumber, isReserved) VALUES ('ROOM106', FALSE);
INSERT INTO room (roomNumber, isReserved) VALUES ('ROOM107', FALSE);
INSERT INTO room (roomNumber, isReserved) VALUES ('ROOM108', FALSE);
INSERT INTO room (roomNumber, isReserved) VALUES ('ROOM109', FALSE);
INSERT INTO room (roomNumber, isReserved) VALUES ('ROOM110', FALSE);

-- test rentables
INSERT INTO rentable VALUES ('1111116', '1111110', 'Very Cool Movie', ' ', 'grossish', 'History', 'dvd', 1);
INSERT INTO rentable VALUES ('1111117', '1111110', 'Very Cool Movie', ' ', 'good', 'History', 'dvd', 1);
INSERT INTO rentable VALUES ('1111118', '1111110', 'Very Cool Movie', ' ', 'scratched', 'History', 'dvd', 1);
INSERT INTO rentable VALUES ('1111119', '1111110', 'Very Cool Movie', ' ', 'good', 'History', 'dvd', 1);
INSERT INTO rentable VALUES ('1111110', '1111110', 'Very Cool Movie', ' ', 'new', 'History', 'dvd', 1);

INSERT INTO rentable VALUES ('2222226', '2222220', 'Not Cool Movie', ' ', 'good', 'Sci-Fi', 'dvd', 1);
INSERT INTO rentable VALUES ('2222227', '2222220', 'Not Cool Movie', ' ', 'gross', 'Sci-Fi', 'dvd', 1);
INSERT INTO rentable VALUES ('2222228', '2222220', 'Not Cool Movie', ' ', 'scratched', 'Sci-Fi', 'dvd', 1);
INSERT INTO rentable VALUES ('2222229', '2222220', 'Not Cool Movie', ' ', 'good', 'Sci-Fi', 'dvd', 1);
INSERT INTO rentable VALUES ('2222220', '2222220', 'Not Cool Movie', ' ', 'bad', 'Sci-Fi', 'dvd', 1);

INSERT INTO rentable VALUES ('3333336', '3333330', 'Best Book NA', '899876', 'good', 'Fantasy', 'book', 1);
INSERT INTO rentable VALUES ('3333337', '3333330', 'Best Book NA', '899876', 'good', 'Fantasy', 'book', 1);
INSERT INTO rentable VALUES ('3333338', '3333330', 'Best Book NA', '899876', 'torn', 'Fantasy', 'book', 1);
INSERT INTO rentable VALUES ('3333339', '3333330', 'Best Book NA', '899876', 'good', 'Fantasy', 'book', 1);
INSERT INTO rentable VALUES ('3333330', '3333330', 'Best Book NA', '899876', 'bad', 'Fantasy', 'book', 1);

INSERT INTO rentable VALUES ('4444446', '4444440', 'Best Book NA', '775655', 'bad', 'Fantasy', 'book', 1);
INSERT INTO rentable VALUES ('4444447', '4444440', 'Best Book NA', '775655', 'bad', 'Fantasy', 'book', 1);
INSERT INTO rentable VALUES ('4444448', '4444440', 'Best Book NA', '775655', 'awful', 'Fantasy', 'book', 1);
INSERT INTO rentable VALUES ('4444449', '4444440', 'Best Book NA', '775655', 'good', 'Fantasy', 'book', 1);
INSERT INTO rentable VALUES ('4444440', '4444440', 'Best Book NA', '775655', 'good', 'Fantasy', 'book', 1);

INSERT INTO rentable VALUES ('5555556', '5555550', 'The Book', '121212121212121', 'good', 'fantasy', 'book', 1);
INSERT INTO rentable VALUES ('5555557', '5555550', 'The Book', '121212121212121', 'good', 'fantasy', 'book', 1);
INSERT INTO rentable VALUES ('5555558', '5555551', 'The Book vol. 2', '121212121212122', 'good', 'Horror', 'book', 0);
INSERT INTO rentable VALUES ('5555559', '5555551', 'The Book vol. 2', '121212121212122', 'good', 'Horror', 'book', 0);


INSERT INTO rental Values(5555556, NOW(), NOW() + INTERVAL 1 DAY, 'U000001', 1);
INSERT INTO rental Values(4444448, NOW() - INTERVAL 4 DAY, NOW(), 'U000001', 3);
INSERT INTO rental Values(3333339, NOW() + INTERVAL 2 DAY, NOW() + INTERVAL 1 WEEK, 'U000001', 0);
INSERT INTO rental Values(1111118, NOW(), NOW() + INTERVAL 5 DAY, 'U000001', 1);
INSERT INTO rental Values(2222229, NOW() - INTERVAL 1 WEEK, NOW() + INTERVAL 3 DAY, 'U000001', 1);

-- Sample rental for unit test
-- test data should have fixed date instead of NOW(), which would break test code if test DB is re-initiated
INSERT INTO rental VALUES('5555557', '2018-5-10 12:00:00', '2018-5-14 12:00:00', 'U000001', 0);






