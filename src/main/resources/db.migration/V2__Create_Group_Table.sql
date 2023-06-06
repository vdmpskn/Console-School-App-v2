CREATE TABLE groups
(
    id         SERIAL PRIMARY KEY,
    group_name VARCHAR(10)
);

INSERT INTO groups (group_name)
SELECT random_group_name()
FROM generate_series(1, 10);
