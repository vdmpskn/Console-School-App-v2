package com.example.demo.front;

import com.example.demo.service.InputService;
import com.example.demo.model.Group;
import com.example.demo.model.Student;
import com.example.demo.service.CourseService;
import com.example.demo.service.GroupService;
import com.example.demo.service.StudentService;
import jakarta.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsoleMenu {
    private final GroupService groupService;
    private final StudentService studentService;
    private final CourseService courseService;
    private final InputService inputService;

    private static final Log LOGGER = LogFactory.getLog(ConsoleMenu.class);

    public ConsoleMenu(GroupService groupService, StudentService studentService, 
                       CourseService courseService, InputService inputService) {
        this.groupService = groupService;
        this.studentService = studentService;
        this.courseService = courseService;
        this.inputService = inputService;
    }

    @PostConstruct
    public void start() {
        int choice = 0;

        while (choice != 7) {
            displayMenu();
            choice = inputService.nextInt();
            inputService.nextLine();

            switch (choice) {
                case 1:
                    findAllGroupsWithLessOrEqualStudents();
                    break;
                case 2:
                    findStudentsRelatedToCourse();
                    break;
                case 3:
                    addNewStudent();
                    break;
                case 4:
                    deleteStudent();
                    break;
                case 5:
                    addStudentToCourse();
                    break;
                case 6:
                    removeStudentFromCourse();
                    break;
                case 7:
                    LOGGER.info("Exiting the program. Goodbye!");
                    break;
                default:
                    LOGGER.info("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private void displayMenu() {
        LOGGER.info("---------- Console Menu ----------");
        LOGGER.info("1. Find all groups with less or equal students' number");
        LOGGER.info("2. Find all students related to a course");
        LOGGER.info("3. Add a new student");
        LOGGER.info("4. Delete a student by STUDENT_ID");
        LOGGER.info("5. Add a student to a course");
        LOGGER.info("6. Remove a student from one of their courses");
        LOGGER.info("7. Exit");
        LOGGER.info("----------------------------------");
        LOGGER.info("Enter your choice: ");
    }

    private void findAllGroupsWithLessOrEqualStudents() {
        LOGGER.info("Write max students of group: ");
        int maxStudents = inputService.nextInt();
        try {
            List<Group> groups = groupService.findGroupsWithMaxStudents(maxStudents);
            for(Group group : groups){
                System.out.println(group);
            }
        } catch (RuntimeException ex){

        }

    }

    private void findStudentsRelatedToCourse() {
        LOGGER.info("Write course's name: ");
        String courseName = inputService.nextLine();
        try{
            List<Student> students = studentService.findStudentsByCourseName(courseName);
            for (Student student : students){
                System.out.println(student);
            }
        } catch (RuntimeException ex){

        }

    }

    private void addNewStudent() {
        LOGGER.info("Write student id: ");
        long studentId = inputService.nextLong();
        LOGGER.info("Write group id: ");
        long groupId = inputService.nextLong();
        LOGGER.info("Write student's name: ");
        String studentName = inputService.nextLine();
        LOGGER.info("Write student's last name: ");
        String studentLastName = inputService.nextLine();
        studentService.save(new Student(studentId,groupId,studentName,studentLastName ));
        LOGGER.info("Student added!!");
    }

    private void deleteStudent() {
        LOGGER.info("Write student's id");
        long id = inputService.nextLong();
       studentService.deleteById(id);
    }

    private void addStudentToCourse() {
        LOGGER.info("Write student's id: ");
        long studentId = inputService.nextLong();
        LOGGER.info("Write courses id: ");
        long courseId = inputService.nextLong();
        courseService.addStudentToCourse(studentId, courseId);
    }

    private void removeStudentFromCourse() {
        LOGGER.info("Write student's id: ");
        long studentId = inputService.nextLong();
        LOGGER.info("Write course's id: ");
        long courseId = inputService.nextLong();
        courseService.removeStudentFromCourse(studentId, courseId);
    }
}
