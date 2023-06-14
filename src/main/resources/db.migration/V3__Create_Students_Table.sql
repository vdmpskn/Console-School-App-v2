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


