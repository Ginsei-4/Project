# Persons schema
 
# --- !Ups

CREATE SEQUENCE vehicle_id_seq;
CREATE TABLE vehicle (
    id integer NOT NULL DEFAULT nextval('vehicle_id_seq'),
    technumber varchar(255),
    vType varchar(255),
    producer varchar(255),
    vModel varchar(255),
    stolen bit,
    wanted bit

);
 
# --- !Downs
 
DROP TABLE vehicle;
DROP SEQUENCE vehicle_id_seq;
