# Persons schema
 
# --- !Ups

CREATE SEQUENCE weapon_id_seq;
CREATE TABLE weapon (
    id integer NOT NULL DEFAULT nextval('weapon_id_seq'),
    wType varchar(255),
    producer varchar(255),
    wModel varchar(255),
    status varchar(255)

);
 
# --- !Downs
 
DROP TABLE weapon;
DROP SEQUENCE weapon_id_seq;
