package com.example.demo.generator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CourseDataGeneratorService {

    private static final Log log = LogFactory.getLog(CourseDataGeneratorService.class);

    private final JdbcTemplate jdbcTemplate;

    public CourseDataGeneratorService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createCourse() {
        try {
            addCourses();
        } catch (SQLException e) {
            log.error("Failed to create courses: " + e.getMessage());
        }
    }

    public void addCourses() throws SQLException {
        String sql = "INSERT INTO courses (course_name, course_description) SELECT ?, ? " +
                "WHERE NOT EXISTS (SELECT 1 FROM courses WHERE course_name = ?)";

        List<Object[]> batchArgs = new ArrayList<>();
        batchArgs.add(new Object[]{"Math", "Mathematics course", "Math"});
        batchArgs.add(new Object[]{"Biology", "Biology course", "Biology"});
        batchArgs.add(new Object[]{"History", "History course", "History"});
        batchArgs.add(new Object[]{"Chemistry", "Chemistry course", "Chemistry"});
        batchArgs.add(new Object[]{"Physics", "Physics course", "Physics"});
        batchArgs.add(new Object[]{"English", "English course", "English"});
        batchArgs.add(new Object[]{"Computer Science", "Computer Science course", "Computer Science"});
        batchArgs.add(new Object[]{"Art", "Art course", "Art"});
        batchArgs.add(new Object[]{"Music", "Music course", "Music"});
        batchArgs.add(new Object[]{"Physical Education", "Physical Education course", "Physical Education"});

        try {
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Object[] args = batchArgs.get(i);
                    ps.setString(1, (String) args[0]);
                    ps.setString(2, (String) args[1]);
                    ps.setString(3, (String) args[2]);
                }

                @Override
                public int getBatchSize() {
                    return batchArgs.size();
                }
            });

            log.info("Courses created successfully.");
        } catch (Exception e) {
            log.error("Failed to add courses: " + e.getMessage());
        }
    }

}

