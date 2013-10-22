/*

    file: gameDB.sql

    How to run: open sqlite3 ($ sqlite3)
        Read in the tables: .read gameDB.sql 
            (the drop tables will probably throw an error and shoot you out of sqlite3, just go back in and try again)
        Read in Data: .read gameData.sql (this may take a while)

*/


/* DROP TABLES */
DROP TABLE firstName;
DROP TABLE lastName;
DROP TABLE dob;
DROP TABLE address;
DROP TABLE photo;
DROP TABLE driversID;
DROP TABLE passportID;
DROP TABLE occupation;
DROP TABLE state;
DROP TABLE misc;

/* CREATE TABLES */
CREATE TABLE firstName( /*NOTE: first name data will also be used to get middle name */
   ID integer PRIMARY KEY AUTOINCREMENT,
	firstName varchar(40) NOT NULL,
	gender varchar(1), /*can be F || M */
	CONSTRAINT gender_ok CHECK (gender in ('M', 'F'))
);

CREATE TABLE lastName(
   ID integer PRIMARY KEY AUTOINCREMENT,
	lastName varchar(50) NOT NULL
);

CREATE TABLE address(
   ID integer PRIMARY KEY AUTOINCREMENT,
	address varchar(40),
	city varchar(40),
	postal varchar(7),
	country varchar(40)
);

CREATE TABLE dob(
   ID integer PRIMARY KEY AUTOINCREMENT,
   dob varchar(20)
);

CREATE TABLE photo(
   ID integer PRIMARY KEY AUTOINCREMENT,
	photo varchar(50) /* path */
);

CREATE TABLE driversID(
   ID integer PRIMARY KEY AUTOINCREMENT,
   driversID number(7,0) NOT NULL
);

CREATE TABLE passportID(
   ID integer PRIMARY KEY AUTOINCREMENT,
   passportID  varchar(8) NOT NULL
);

CREATE TABLE occupation(
   ID integer PRIMARY KEY AUTOINCREMENT,
   occupation varchar(50) NOT NULL
);

CREATE TABLE state(
   state integer PRIMARY KEY
);

CREATE TABLE misc(
   ID integer PRIMARY KEY AUTOINCREMENT,
   misc varchar(255)
);
