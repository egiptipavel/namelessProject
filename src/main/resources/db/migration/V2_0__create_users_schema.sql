CREATE TABLE users(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    name varchar(40),
    login varchar(40),
    password varchar(40),
    rating double precision
);