package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentDaoImpl implements StudentDao {

    private static final String SAVE_QUERY = """
            WITH ROWS AS (
              INSERT INTO students (student_id, group_id, first_name, last_name)
              VALUES (?, ?, ?, ?)
              RETURNING student_id, group_id, first_name, last_name
            )
            SELECT student_id, group_id, first_name, last_name FROM ROWS;
            
                                                   """;


    private static final String FIND_ALL_QUERY = """
            SELECT student_id, group_id, first_name, last_name FROM students;
            """;

    private static final String FIND_BY_ID_QUERY = """
            SELECT student_id, group_id, first_name, last_name FROM students WHERE student_id = ?;
            """;

    private static final String DELETE_BY_ID_QUERY = """
            DELETE FROM students WHERE student_id = 
             """;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Student save(Student student) {
        return jdbcTemplate.queryForObject(SAVE_QUERY, (rs, rowNum) -> new Student(
                rs.getLong("student_id"),
                rs.getLong("group_id"),
                rs.getString("first_name"),
                rs.getString("last_name")
        ),student.student_id(), student.group_id(), student.first_name(), student.last_name());
    }

    @Override
    public List<Student> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, (rs, rowNum) -> new Student(
                rs.getLong("student_id"),
                rs.getLong("group_id"),
                rs.getString("first_name"),
                rs.getString("last_name")
        ));
    }

    @Override
    public Student findById(Long student_id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, (rs, rowNum) -> new Student(
                rs.getLong("student_id"),
                rs.getLong("group_id"),
                rs.getString("first_name"),
                rs.getString("last_name")
        ), student_id);
    }

    @Override
    public void deleteById(Long student_id) {
        jdbcTemplate.execute("DELETE FROM student_courses WHERE student_id = " + student_id + ";");
        jdbcTemplate.execute(DELETE_BY_ID_QUERY + student_id + ";");

    }
}
