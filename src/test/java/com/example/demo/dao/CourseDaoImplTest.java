package com.example.demo.dao;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
class CourseDaoImplTest {

    @Container
    private static final JdbcDatabaseContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test")
            .withUsername("postgres")
            .withPassword("1111");

    private CourseDaoImpl courseDao;
    private JdbcTemplate jdbcTemplate;

    private void migrateDatabase() {
        Flyway flyway = Flyway.configure()
                .dataSource(postgresContainer.getJdbcUrl(), postgresContainer.getUsername(), postgresContainer.getPassword())
                .locations("classpath:db.migration")
                .load();
        flyway.migrate();
    }

        @BeforeEach
        public void setUp() {
            postgresContainer.start();
            migrateDatabase();

            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(postgresContainer.getDriverClassName());
            dataSource.setUrl(postgresContainer.getJdbcUrl());
            dataSource.setUsername(postgresContainer.getUsername());
            dataSource.setPassword(postgresContainer.getPassword());

            jdbcTemplate = new JdbcTemplate(dataSource);
            courseDao = new CourseDaoImpl(jdbcTemplate);
        }


    @Test
    public void addStudentToCourse_shouldInsertRecord() {
        long studentId = 1;
        long courseId = 2;

        courseDao.addStudentToCourse(studentId, courseId);

        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM student_courses WHERE student_id = ? AND course_id = ?", Integer.class, studentId, courseId);
        assertEquals(1, count);
    }

    @Test
    public void removeStudentFromCourse_shouldDeleteRecord() {
        long studentId = 1;
        long courseId = 2;

        courseDao.removeStudentFromCourse(studentId, courseId);

        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM student_courses WHERE student_id = ? AND course_id = ?", Integer.class, studentId, courseId);
        assertEquals(0, count);
    }


}
