-- INSERT INTO rentable VALUES(null, null, null, null, null, 'Type', 'Room Number'); -- room
-- INSERT INTO rentable VALUES(null, 'Title', null, 'Condition', 'Genre', 'Type', null); -- DVD
-- INSERT INTO rentable VALUES(null, 'TItle', 'ISBN', 'Condition', 'Genre', 'Type', null); -- book
INSERT INTO rentable VALUES(null, 'Book 1', '111111111', 'New', 'Fantasy', 'Book', null);
INSERT INTO rentable VALUES(null, 'Book 2', '222222222', 'Damaged', 'Mystery', 'Book', null);
INSERT INTO rentable VALUES(null, 'Book 3', '333333333', 'New', 'Instructional', 'Book', null);
INSERT INTO rentable VALUES(null, 'DVD 1', null, 'New', 'Drama', 'DVD', null);
INSERT INTO rentable VALUES(null, 'DVD 2', null, 'Scratched', 'Drama', 'DVD', null); -- DVD
INSERT INTO rentable VALUES(null, 'DVD 3', null, 'Scratched', 'Fantasy', 'DVD', null); -- DVD
INSERT INTO rentable VALUES(null, null, null, null, null, 'Room', '1A');
INSERT INTO rentable VALUES(null, null, null, null, null, 'Room', '1B');
INSERT INTO rentable VALUES(null, null, null, null, null, 'Room', '32C');

INSERT INTO rental VALUES(14, '2018-05-03 12:00:00', '2018-06-03 12:00:00', 21, 0);
INSERT INTO rental VALUES(15, '2018-05-03 12:00:00', '2018-06-03 12:00:00', 7, 0);
INSERT INTO rental VALUES(16, '2018-04-03 12:00:00', '2018-06-03 12:00:00', 9, 1);

