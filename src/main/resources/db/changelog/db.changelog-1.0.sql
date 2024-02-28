--liquibase formatted sql

--changeset vladislav:1
CREATE TABLE users
(
    id   serial PRIMARY KEY,
    name VARCHAR(64) unique NOT NULL,
    role varchar(32),
    pass varchar(128)   default '123'
);
CREATE TABLE payments
(
    id           serial PRIMARY KEY,
    userid       serial,
    amount       DECIMAL(10, 2),
    payment_date date,
    FOREIGN KEY (userid)
        REFERENCES users (id)
);