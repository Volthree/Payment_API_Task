--liquibase formatted sql

--changeset vladislav:1
CREATE TABLE users
(
    id   serial PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    usd  numeric      default 0,
    role varchar(32),
    pass varchar(128) default '123'
);