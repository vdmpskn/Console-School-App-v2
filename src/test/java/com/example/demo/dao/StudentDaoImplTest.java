package com.example.demo.dao;

import com.example.demo.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@ActiveProfiles("test-containers")
@TestPropertySource(locations = "classpath:application-test-containers.yml")
@Sql(
        scripts = {"classpath:clear_tables.sql",
                "classpath:db.migration/V1__Create_Random_Group_Name_Function.sql",
                "classpath:db.migration/V2__Create_Courses_Table.sql",
                "classpath:db.migration/V3__Create_Students_Table.sql"
        },
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class StudentDaoImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private StudentDaoImpl studentDao;

    @BeforeEach
    public void setUp() {
        studentDao = new StudentDaoImpl(jdbcTemplate);
    }

    @Test
    void save_shouldReturnSavedStudent() {
        Student student = new Student(100L, 1L, "John", "Doe");

        Student savedStudent = studentDao.save(student);

        assertEquals(student, savedStudent);
    }

    @Test
    void findAll_shouldReturnListOfStudents() {
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
    void findById_shouldReturnStudentWithGivenId() {
        Long studentId = 1L;
        Student expectedStudent = new Student(studentId, 1L, "John", "Doe");

        Student actualStudent = studentDao.findById(studentId);

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void deleteById_shouldDeleteStudentWithGivenId() {
        Long studentId = 1L;

        JdbcTemplate jdbcTemplateMock = Mockito.mock(JdbcTemplate.class);
        StudentDao studentDao = new StudentDaoImpl(jdbcTemplateMock);

        studentDao.deleteById(studentId);

        Mockito.verify(jdbcTemplateMock).update(Mockito.anyString(), Mockito.eq(studentId), Mockito.eq(studentId));
    }

    @Test
    void findStudentsByCourseName_shouldReturnListOfStudents() {
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
