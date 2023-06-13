package com.example.demo.service;

import com.example.demo.dao.StudentDaoImpl;
import com.example.demo.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
@ActiveProfiles("mock")
@TestPropertySource(locations = "classpath:application-mock.yml")
@Sql(
        scripts = {"classpath:clear_tables.sql",
                "classpath:db.migration/V1__Create_Random_Group_Name_Function.sql",
                "classpath:db.migration/V2__Create_Courses_Table.sql",
                "classpath:db.migration/V3__Create_Students_Table.sql"
        },
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class StudentServiceImplTest {

    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private StudentDaoImpl studentDao;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        studentService = new StudentServiceImpl(studentDao);
    }

    @Test
    void testSaveStudent() {
        //given
        Student student = new Student(1L, 1L, "John", "Doe");
        //when
        when(studentDao.save(student)).thenReturn(student);

        Student savedStudent = studentService.save(student);
        //then
        assertNotNull(savedStudent);
        assertEquals(student, savedStudent);

        verify(studentDao, times(1)).save(student);
    }

    @Test
    void testFindAllStudents() {
        //given
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student(1L, 1L, "John", "Doe"));
        expectedStudents.add(new Student(2L, 1L, "Jane", "Smith"));

        //when
        when(studentDao.findAll()).thenReturn(expectedStudents);

        List<Student> actualStudents = studentService.findAll();
        //then
        assertNotNull(actualStudents);
        assertEquals(expectedStudents.size(), actualStudents.size());
        assertEquals(expectedStudents, actualStudents);

        verify(studentDao, times(1)).findAll();
    }

    @Test
    void testFindStudentsByCourseName() {
        //given
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student(1L, 1L, "John", "Doe"));
        expectedStudents.add(new Student(2L, 1L, "Jane", "Smith"));

        String courseName = "Math";
        //when
        when(studentDao.findStudentsByCourseName(courseName)).thenReturn(expectedStudents);

        List<Student> actualStudents = studentService.findStudentsByCourseName(courseName);
        //then
        assertNotNull(actualStudents);
        assertEquals(expectedStudents.size(), actualStudents.size());
        assertEquals(expectedStudents, actualStudents);

        verify(studentDao, times(1)).findStudentsByCourseName(courseName);
    }

    @Test
    void testFindStudentById() {
        //given
        Student expectedStudent = new Student(1L, 1L, "John", "Doe");
        Long studentId = 1L;

        //when
        when(studentDao.findById(studentId)).thenReturn(expectedStudent);

        Student actualStudent = studentService.findById(studentId);
        //then

        assertNotNull(actualStudent);
        assertEquals(expectedStudent, actualStudent);

        verify(studentDao, times(1)).findById(studentId);
    }

    @Test
    void testDeleteStudentById() {
        //given
        Long studentId = 1L;
        //when-then
        studentService.deleteById(studentId);

        verify(studentDao, times(1)).deleteById(studentId);
    }

    @Test
    void testSaveStudentThrowingException() {
        //given
        Student student = new Student(1L, 1L, "John", "Doe");
        //when
        when(studentDao.save(student)).thenReturn(student);
        Student savedStudent = studentService.save(student);
        //then
        doThrow(new IllegalArgumentException("")).when(studentDao).save(savedStudent);
        assertThrows(RuntimeException.class, () -> studentService.save(savedStudent));
    }

    @Test
    void testFindAllStudentsThrowingException() {

        doThrow(new IllegalArgumentException("")).when(studentDao).findAll();

        assertThrows(RuntimeException.class, () -> studentService.findAll());
    }

    @Test
    void testFindStudentsByCourseNameThrowingException() {
        //given
        String courseName = "Math";
        //then
        doThrow(new IllegalArgumentException("")).when(studentDao).findStudentsByCourseName(courseName);

        assertThrows(RuntimeException.class, () -> studentService.findStudentsByCourseName(courseName));
    }

    @Test
    void testFindStudentByIdThrowingException() {
        //given
        Long studentId = 1L;
        //then
        doThrow(new IllegalArgumentException("")).when(studentDao).findById(studentId);

        assertThrows(RuntimeException.class, () -> studentService.findById(studentId));
    }

    @Test
    void testDeleteStudentByIdThrowingException() {
        //given
        Long studentId = 1L;
        //then
        doThrow(new IllegalArgumentException("")).when(studentDao).deleteById(studentId);

        assertThrows(RuntimeException.class, () -> studentService.deleteById(studentId));
    }

}
