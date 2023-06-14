package com.example.demo.front;

import com.example.demo.model.Student;
import com.example.demo.service.CourseService;
import com.example.demo.service.GroupService;
import com.example.demo.service.InputService;
import com.example.demo.service.StudentService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;

@ActiveProfiles("mock")
class ConsoleMenuTest {
    private static final Log LOGGER = LogFactory.getLog(ConsoleMenuTest.class);
    @InjectMocks
    private ConsoleMenu consoleMenu;

    @Mock
    private GroupService groupService;

    @Mock
    private StudentService studentService;

    @Mock
    private CourseService courseService;

    @Mock
    private InputService inputService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testStartConsoleMenuLogic() {
        // Given
        when(inputService.nextInt())
                .thenReturn(1)//first method
                .thenReturn(10)//params for first method
                .thenReturn(2)//second method
                .thenReturn(3)//third method
                .thenReturn(4)//fourth method
                .thenReturn(100)//params for fourth method
                .thenReturn(5)//fifth
                .thenReturn(30)//params
                .thenReturn(29)//params
                .thenReturn(6)//sixth method
                .thenReturn(20)//params
                .thenReturn(21)//params
                .thenReturn(8)//exit
        ;



        // When
        consoleMenu.start();

        // Then
        verify(groupService, times(1)).findGroupsWithMaxStudents(anyInt());
        verify(studentService, times(1)).findStudentsByCourseName(inputService.nextLine());
        verify(studentService, times(1)).save(any(Student.class));
        verify(studentService, times(1)).deleteById(anyLong());
        verify(courseService, times(1)).addStudentToCourse(anyLong(), anyLong());
        verify(courseService, times(1)).removeStudentFromCourse(anyLong(), anyLong());

    }



}