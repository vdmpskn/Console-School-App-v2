-- V1__create_students_table.sql
CREATE TABLE students
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(50),
    last_name  VARCHAR(50)
);

CREATE TABLE student_courses
(
    student_id INTEGER,
    course_id  INTEGER,
    CONSTRAINT fk_student_id FOREIGN KEY (student_id) REFERENCES students (id),
    CONSTRAINT fk_course_id FOREIGN KEY (course_id) REFERENCES courses (course_id)
);

ALTER TABLE students
    ADD COLUMN group_id INTEGER,
ADD CONSTRAINT fk_group_id FOREIGN KEY (group_id) REFERENCES groups (id);

-- Вставка 50 случайных студентов
INSERT INTO students (first_name, last_name)
SELECT first_names.name, last_names.name
FROM (SELECT 'John' AS name
      UNION ALL
      SELECT 'Emily'
      UNION ALL
      SELECT 'Michael'
      UNION ALL
      SELECT 'Vadym'
      UNION ALL
      SELECT 'Kyrylo'
      UNION ALL
      SELECT 'Maxym'
      UNION ALL
      SELECT 'Boris'
      UNION ALL
      SELECT 'Joseph'
      UNION ALL
      SELECT 'Valery') AS first_names
         CROSS JOIN (SELECT 'Smith' AS name
                     UNION ALL
                     SELECT 'Johnson'
                     UNION ALL
                     SELECT 'Williams'
                     UNION ALL
                     SELECT 'Parker'
                     UNION ALL
                     SELECT 'Brown'
                     UNION ALL
                     SELECT 'Piskun'
                     UNION ALL
                     SELECT 'Baden'-- добавьте здесь другие фамилии
) AS last_names
ORDER BY RANDOM() LIMIT 50;

UPDATE students
SET group_id = subquery.group_id FROM (
    SELECT student_id, id AS group_id
    FROM (
        SELECT students.id AS student_id, groups.id, ROW_NUMBER() OVER (PARTITION BY students.id ORDER BY RANDOM()) AS rn
        FROM students, groups
    ) AS sub
    WHERE sub.rn = 1
) AS subquery
WHERE students.id = subquery.student_id;


INSERT INTO student_courses (student_id, course_id)
SELECT students.id, subquery.course_id
FROM students
         CROSS JOIN (SELECT course_id
                     FROM courses
                     ORDER BY random() LIMIT 3) AS subquery;





