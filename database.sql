-- Run this script in your PostgreSQL database to set up the required tables.

CREATE TABLE students (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    grade VARCHAR(50) NOT NULL
);