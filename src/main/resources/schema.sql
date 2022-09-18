
ALTER TABLE IF EXISTS position DROP CONSTRAINT IF EXISTS position_specification_id_fkey;

DROP TABLE IF EXISTS position;
DROP TABLE IF EXISTS document;

CREATE TABLE document(
id SERIAL PRIMARY KEY,
number INTEGER,
creation_date DATE,
final_price integer,
note VARCHAR(255));

CREATE TABLE position(
id SERIAL PRIMARY KEY,
number INTEGER,
name VARCHAR(255),
price INTEGER,
amount INTEGER,
document_id INTEGER REFERENCES document(id) ON DELETE RESTRICT ON UPDATE CASCADE);


DROP TABLE logging IF EXISTS; 

CREATE TABLE logging (
--ID varchar(50) primary key,
ID SERIAL PRIMARY KEY,
DATE_TIME timestamp,
CLASS varchar(100),
LEVEL varchar(10),
MESSAGE TEXT,
EXCEPTION TEXT);