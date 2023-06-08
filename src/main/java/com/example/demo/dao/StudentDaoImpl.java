package com.example.demo.dao;


import com.example.demo.model.Student;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentDaoImpl implements StudentDao {

    private static final String SAVE_QUERY = """
            WITH ROWS AS (
              INSERT INTO students (id, group_id, first_name, last_name)
              VALUES (?, ?, ?, ?)
              RETURNING id, group_id, first_name, last_name
            )
            SELECT id, group_id, first_name, last_name FROM ROWS;
            
                                                   """;

    private static final String FIND_ALL_QUERY = """
            SELECT id, group_id, first_name, last_name FROM students;
            """;

    private static final String FIND_BY_ID_QUERY = """
            SELECT id, group_id, first_name, last_name FROM students WHERE id = ?;
            """;

    private static final String DELETE_BY_ID_QUERY = """
            DELETE FROM student_courses WHERE student_id = " + student_id + ;
            DELETE FROM students WHERE id = 
             """;

    private static final String FIND_BY_COURSE_NAME_QUERY = "SELECT s.id, s.first_name, s.last_name, s.group_id " +
            "FROM students s " +
            "JOIN student_courses sc ON s.id = sc.student_id " +
            "JOIN courses c ON sc.course_id = c.course_id " +
            "WHERE c.course_name = ?";

    private final JdbcTemplate jdbcTemplate;

    public StudentDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Student save(Student student) {
        return jdbcTemplate.queryForObject(SAVE_QUERY, (rs, rowNum) -> new Student(
                rs.getLong("id"),
                rs.getLong("group_id"),
                rs.getString("first_name"),
                rs.getString("last_name")
        ),student.student_id(), student.group_id(), student.first_name(), student.last_name());
    }


    @Override
    public List<Student> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, (rs, rowNum) -> new Student(
                rs.getLong("id"),
                rs.getLong("group_id"),
                rs.getString("first_name"),
                rs.getString("last_name")
        ));
    }

    @Override
    public Student findById(Long student_id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, (rs, rowNum) -> new Student(
                rs.getLong("id"),
                rs.getLong("group_id"),
                rs.getString("first_name"),
                rs.getString("last_name")
        ), student_id);
    }

    @Override
    public void deleteById(Long student_id) {
        jdbcTemplate.execute(DELETE_BY_ID_QUERY + student_id + ";");
    }
    @Override
    public List<Student> findStudentsByCourseName(String courseName) {

        return jdbcTemplate.query(FIND_BY_COURSE_NAME_QUERY, (rs, rowNum) -> new Student(
            rs.getLong("id"),
            rs.getLong("group_id"),
            rs.getString("first_name"),
            rs.getString("last_name")
        ), courseName);

    }
}
