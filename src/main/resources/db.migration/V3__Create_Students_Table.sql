CREATE TABLE students
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(50),
    last_name  VARCHAR(50)
);

ALTER TABLE students
    ADD COLUMN group_id INTEGER;

CREATE TABLE student_courses
(
    student_id INTEGER,
    course_id  INTEGER,
    CONSTRAINT fk_student_id FOREIGN KEY (student_id) REFERENCES students (id),
    CONSTRAINT fk_course_id FOREIGN KEY (course_id) REFERENCES courses (course_id)
);


INSERT INTO students (first_name, last_name)
VALUES ('John', 'Doe'),
       ('Jane', 'Smith'),
       ('Michael', 'Johnson'),
       ('Emily', 'Williams'),
       ('Christopher', 'Brown'),
       ('Jessica', 'Jones'),
       ('Matthew', 'Davis'),
       ('Olivia', 'Miller'),
       ('Andrew', 'Taylor'),
       ('Sophia', 'Anderson'),
       ('Daniel', 'Wilson'),
       ('Ava', 'Martinez'),
       ('William', 'Hernandez'),
       ('Isabella', 'Garcia'),
       ('Joseph', 'Lopez');


INSERT INTO student_courses (student_id, course_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 2),
       (2, 3),
       (2, 4),
       (3, 3),
       (3, 4),
       (3, 5),
       (4, 4),
       (4, 5),
       (4, 1),
       (5, 5),
       (5, 1),
       (5, 2),
       (6, 1),
       (6, 2),
       (6, 3),
       (7, 2),
       (7, 3),
       (7, 4),
       (8, 3),
       (8, 4),
       (8, 5),
       (9, 4),
       (9, 5),
       (9, 1),
       (10, 5),
       (10, 1),
       (10, 2),
       (11, 1),
       (11, 2),
       (11, 3),
       (12, 2),
       (12, 3),
       (12, 4),
       (13, 3),
       (13, 4),
       (13, 5),
       (14, 4),
       (14, 5),
       (14, 1),
       (15, 5),
       (15, 1),
       (15, 2);

UPDATE students
SET group_id = 1
WHERE id IN (1, 2, 7, 8, 13,14);

UPDATE students
SET group_id = 2
WHERE id IN (3, 4, 9, 10, 15);

UPDATE students
SET group_id = 3
WHERE id IN (5, 6, 11, 12);
