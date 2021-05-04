DROP TABLE IF EXISTS studentdata;
DROP TABLE IF EXISTS classdata;

CREATE TABLE classdata (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL
);

CREATE TABLE studentdata (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  class_id INT NOT NULL,
  name VARCHAR(250) NOT NULL,
  age INT NOT NULL,
  registration_number VARCHAR(250) NOT NULL,
  FOREIGN KEY (class_id) REFERENCES classdata(id)
);

INSERT INTO classdata (name) VALUES
  ('Maths'),
  ('Science'),
  ('Hindi'),
  ('English');

INSERT INTO studentdata (class_id, name, age, registration_number) VALUES
   (1, 'vikas pandya', 34, 'AXCVF234'),
   (1, 'arpita pandya', 31, 'ASDCV234'),
   (2, 'avira pandya', 12, 'MBHNJ789'),
   (2, 'aarvi pandya', 11, 'FGNJ789'),
   (3, 'rahul pandya', 37, 'LOKNH567'),
   (3, 'sourabh pandya', 39, 'OPJNL694'),
   (4, 'kalki pandya', 56, 'KNHVH896'),
   (4, 'mohan pandya', 67, 'ECGHJ601');