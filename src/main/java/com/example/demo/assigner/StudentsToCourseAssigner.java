package com.example.demo.assigner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Component
public class StudentsToCourseAssigner {
    private static final Log log = LogFactory.getLog(StudentsToCourseAssigner.class);

    private final JdbcTemplate jdbcTemplate;

    public StudentsToCourseAssigner(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void assignCoursesToStudents() {

        try {
            Random random = new Random();


            String selectStudentsSQL = "SELECT id FROM students";
            jdbcTemplate.query(selectStudentsSQL, (resultSet) -> {
                while (resultSet.next()) {
                    int studentId = resultSet.getInt("id");

                    int numCourses = random.nextInt(3) + 1;

                    String selectCoursesSQL = "SELECT course_id FROM courses ORDER BY RANDOM() LIMIT " + numCourses;
                    Set<Integer> existingCourseIds = new HashSet<>();
                    String selectExistingCoursesSQL = "SELECT course_id FROM student_courses WHERE student_id = ?";
                    jdbcTemplate.query(selectExistingCoursesSQL, new Object[]{studentId}, (existingCoursesResultSet) -> {
                        while (existingCoursesResultSet.next()) {
                            existingCourseIds.add(existingCoursesResultSet.getInt("course_id"));
                        }
                    });

                    jdbcTemplate.query(selectCoursesSQL, (courseIdsResultSet) -> {
                        while (courseIdsResultSet.next()) {
                            int courseId = courseIdsResultSet.getInt("course_id");

                            if (!existingCourseIds.contains(courseId)) {
                                String insertCourseSQL = "INSERT INTO student_courses (student_id, course_id) VALUES (?, ?)";
                                jdbcTemplate.update(insertCourseSQL, studentId, courseId);

                                existingCourseIds.add(courseId);
                            }
                        }
                    });
                }
            });

            log.info("Courses assigned to students successfully.");
        } catch (Exception e){
            log.error("Failed to assign courses to students: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
