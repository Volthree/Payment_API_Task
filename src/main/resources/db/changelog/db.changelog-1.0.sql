--liquibase formatted sql

--changeset vladislav:1
CREATE TABLE users
(
    id   serial PRIMARY KEY,
    name VARCHAR(64) unique not null ,
    role varchar(32),
    pass varchar(128) default '123',
    islocked boolean,
    lockdate timestamp,
    lockcount int

);
CREATE TABLE payments
(
    id           serial PRIMARY KEY,
    username      VARCHAR(64),
    amount       DECIMAL(10, 2),
    payment_date timestamp,
    FOREIGN KEY (username)
        REFERENCES users (name)
);
CREATE TABLE jwttokenblacklist
(
    id    serial PRIMARY KEY,
    token varchar(128),
    date  date
);