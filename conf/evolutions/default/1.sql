# Persons schema
 
# --- !Ups

CREATE SEQUENCE pers_id_seq;
CREATE TABLE person (
    id integer NOT NULL DEFAULT nextval('pers_id_seq'),
    name varchar(255),
    midname varchar(255),
    lastname varchar(255),
    passport varchar(255),
    wanted bit

);
 
# --- !Downs
 
DROP TABLE person;
DROP SEQUENCE pers_id_seq;
