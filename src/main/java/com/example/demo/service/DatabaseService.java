package com.example.demo.service;

import com.example.demo.assigner.StudentsToCourseAssigner;
import com.example.demo.generator.CourseDataGeneratorService;
import com.example.demo.generator.GroupDataGeneratorService;
import com.example.demo.generator.StudentDataGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseService {
    private final Logger LOGGER = LogManager.getLogger(DatabaseService.class);

    private final CourseDataGeneratorService courseDataGeneratorService;
    private final GroupDataGeneratorService groupDataGeneratorService;
    private final StudentDataGenerator studentDataGenerator;
    private final StudentsToCourseAssigner studentsToCourseAssigner;
    private final JdbcTemplate jdbcTemplate;

    public DatabaseService(CourseDataGeneratorService courseDataGeneratorService,
                           GroupDataGeneratorService groupDataGeneratorService,
                           StudentDataGenerator studentDataGenerator, StudentsToCourseAssigner studentsToCourseAssigner, JdbcTemplate jdbcTemplate) {
        this.courseDataGeneratorService = courseDataGeneratorService;
        this.groupDataGeneratorService = groupDataGeneratorService;
        this.studentDataGenerator = studentDataGenerator;
        this.studentsToCourseAssigner = studentsToCourseAssigner;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createDatabase() {
        if (isDatabaseEmpty()) {
            courseDataGeneratorService.createCourse();
            groupDataGeneratorService.generateAndInsertGroups();
            studentDataGenerator.generateStudents();
            try{
                studentsToCourseAssigner.assignCoursesToStudents();
            } catch (RuntimeException ex){
                LOGGER.error("Exception with asign students to course" + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            LOGGER.info("Database is not empty. Skip data generation!");
        }
    }

    public boolean isDatabaseEmpty() {
        try {
            String groupsCountQuery = "SELECT COUNT(*) FROM groups";
            int groupsCount = jdbcTemplate.queryForObject(groupsCountQuery, Integer.class);
            if (groupsCount > 0) {
                return false;
            }
            String coursesCountQuery = "SELECT COUNT(*) FROM courses";
            int coursesCount = jdbcTemplate.queryForObject(coursesCountQuery, Integer.class);
            if (coursesCount > 0) {
                return false;
            }
            String studentsCountQuery = "SELECT COUNT(*) FROM students";
            int studentsCount = jdbcTemplate.queryForObject(studentsCountQuery, Integer.class);
            if (studentsCount > 0) {
                return false;
            }
            String studentCoursesCountQuery = "SELECT COUNT(*) FROM student_courses";
            int studentCoursesCount = jdbcTemplate.queryForObject(studentCoursesCountQuery, Integer.class);
            if (studentCoursesCount > 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            LOGGER.error("Couldn't check is empty becouse of: " + e.getMessage());
            e.printStackTrace();
            return true;
        }
    }
}
