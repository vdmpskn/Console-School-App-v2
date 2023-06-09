CREATE TABLE courses (
    course_id SERIAL PRIMARY KEY,
    course_name VARCHAR(50),
    course_description VARCHAR(255)
);

INSERT INTO courses (course_name, course_description)
VALUES ('Math', 'Mathematic'),
       ('Physics', 'Physics and its applications'),
       ('History', 'World history'),
       ('Literature', 'Classic literature'),
       ('Biology', 'Study of living organisms');
