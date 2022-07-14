CREATE TABLE IF NOT EXISTS person (
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255),
    password VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS message (
    id SERIAL PRIMARY KEY NOT NULL,
    body TEXT,
    person_id INT REFERENCES person(id)
);
