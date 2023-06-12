package com.example.demo.dao;

import com.example.demo.model.Student;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Testcontainers
public class StudentDaoImplTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test")
            .withUsername("postgres")
            .withPassword("1111");

    @Mock
    private JdbcTemplate jdbcTemplate;

    private StudentDaoImpl studentDao;

    private void migrateDatabase() {
        Flyway flyway = Flyway.configure()
                .dataSource(postgresContainer.getJdbcUrl(), postgresContainer.getUsername(), postgresContainer.getPassword())
                .locations("classpath:db.migration")
                .load();
        flyway.migrate();
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        MockitoAnnotations.initMocks(this);
        postgresContainer.start();
        migrateDatabase();

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(postgresContainer.getDriverClassName());
        dataSource.setUrl(postgresContainer.getJdbcUrl());
        dataSource.setUsername(postgresContainer.getUsername());
        dataSource.setPassword(postgresContainer.getPassword());

        jdbcTemplate = new JdbcTemplate(dataSource);
        studentDao = new StudentDaoImpl(jdbcTemplate);
    }

    @Test
    public void save_shouldReturnSavedStudent() {
        Student student = new Student(1L, 1L, "John", "Doe");

        Student savedStudent = studentDao.save(student);

        assertEquals(student, savedStudent);
    }

    @Test
    public void findAll_shouldReturnListOfStudents() {
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student(1L, 1L, "John", "Doe"));
        expectedStudents.add(new Student(2L, 1L, "Jane", "Smith"));
        expectedStudents.add(new Student(7L, 1L, "Matthew", "Davis"));
        expectedStudents.add(new Student(8L, 1L, "Olivia", "Miller"));
        expectedStudents.add(new Student(13L, 1L, "William", "Hernandez"));
        expectedStudents.add(new Student(14L, 1L, "Isabella", "Garcia"));
        expectedStudents.add(new Student(3L, 2L, "Michael", "Johnson"));
        expectedStudents.add(new Student(4L, 2L, "Emily", "Williams"));
        expectedStudents.add(new Student(9L, 2L, "Andrew", "Taylor"));
        expectedStudents.add(new Student(10L, 2L, "Sophia", "Anderson"));
        expectedStudents.add(new Student(15L, 2L, "Joseph", "Lopez"));
        expectedStudents.add(new Student(5L, 3L, "Christopher", "Brown"));
        expectedStudents.add(new Student(6L, 3L, "Jessica", "Jones"));
        expectedStudents.add(new Student(11L, 3L, "Daniel", "Wilson"));
        expectedStudents.add(new Student(12L, 3L, "Ava", "Martinez"));
        List<Student> actualStudents = studentDao.findAll();

        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    public void findById_shouldReturnStudentWithGivenId() {
        Long studentId = 1L;
        Student expectedStudent = new Student(studentId, 1L, "John", "Doe");

        Student actualStudent = studentDao.findById(studentId);

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    public void deleteById_shouldDeleteStudentWithGivenId() {
        Long studentId = 1L;

        JdbcTemplate jdbcTemplateMock = Mockito.mock(JdbcTemplate.class);
        StudentDao studentDao = new StudentDaoImpl(jdbcTemplateMock);

        studentDao.deleteById(studentId);

        Mockito.verify(jdbcTemplateMock).update(Mockito.anyString(), Mockito.eq(studentId), Mockito.eq(studentId));
    }

    @Test
    public void findStudentsByCourseName_shouldReturnListOfStudents() {
        String courseName = "Math";
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student(1L, 1L, "John", "Doe"));
        expectedStudents.add(new Student(4L, 2L, "Emily", "Williams"));
        expectedStudents.add(new Student(5L, 3L, "Christopher", "Brown"));
        expectedStudents.add(new Student(6L, 3L, "Jessica", "Jones"));
        expectedStudents.add(new Student(9L, 2L, "Andrew", "Taylor"));
        expectedStudents.add(new Student(10L, 2L, "Sophia", "Anderson"));
        expectedStudents.add(new Student(11L, 3L, "Daniel", "Wilson"));
        expectedStudents.add(new Student(14L, 1L, "Isabella", "Garcia"));
        expectedStudents.add(new Student(15L, 2L, "Joseph", "Lopez"));

        List<Student> actualStudents = studentDao.findStudentsByCourseName(courseName);

        assertEquals(expectedStudents, actualStudents);
    }
}
