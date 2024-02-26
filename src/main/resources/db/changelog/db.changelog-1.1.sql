--liquibase formatted sql

--changeset vladislav:1
insert into users (name, usd, role, pass)
values
('Ivan', 8, 'user', 'qw'),
('Petr', 8, 'user', 'we'),
('Bob', 8, 'user', 'er'),
('Dan', 8, 'user', 'rt'),
('Rob', 8, 'user', 'as'),
('John', 8, 'user', 'sd');
