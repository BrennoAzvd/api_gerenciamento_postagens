CREATE TABLE users (
                       id uuid primary key,
                       username varchar not null unique,
                       email varchar not null unique,
                       password varchar not null,
                       first_name varchar not null,
                       last_name varchar,
                       role varchar not null
);