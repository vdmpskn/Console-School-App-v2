package com.example.demo.front;

import com.example.demo.model.Group;
import com.example.demo.model.Student;
import com.example.demo.service.CourseService;
import com.example.demo.service.GroupService;
import com.example.demo.service.StudentService;

import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
    private final GroupService groupService;
    private final StudentService studentService;
    private final CourseService courseService;
    Scanner scanner = new Scanner(System.in);

    public ConsoleMenu(GroupService groupService, StudentService studentService, CourseService courseService) {
        this.groupService = groupService;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public void start() {

        int choice = 0;

        while (choice != 7) {
            displayMenu();
            choice = scanner.nextInt();
            scanner.nextLine();

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
                    System.out.println("Exiting the program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private void displayMenu() {
        System.out.println("---------- Console Menu ----------");
        System.out.println("1. Find all groups with less or equal students' number");
        System.out.println("2. Find all students related to a course");
        System.out.println("3. Add a new student");
        System.out.println("4. Delete a student by STUDENT_ID");
        System.out.println("5. Add a student to a course");
        System.out.println("6. Remove a student from one of their courses");
        System.out.println("7. Exit");
        System.out.println("----------------------------------");
        System.out.print("Enter your choice: ");
    }

    private void findAllGroupsWithLessOrEqualStudents() {
        System.out.println("Write maxStudents: ");
        int maxStudents = scanner.nextInt();
        List<Group> groups = groupService.findGroupsWithMaxStudents(maxStudents);
        for(Group group : groups){
            System.out.println(group);
        }
    }

    private void findStudentsRelatedToCourse() {
        System.out.println("Write course's name: ");
        String courseName = scanner.nextLine();
        List<Student> students = studentService.findStudentsByCourseName(courseName);
        for (Student student : students){
            System.out.println(student);
        }
    }

    private void addNewStudent() {
        System.out.print("Write student id: ");
        long studentId = scanner.nextLong();
        System.out.print("Write group id: ");
        long groupId = scanner.nextLong();
        System.out.print("Write student's name: ");
        String studentName = scanner.nextLine();
        System.out.print("Write student's last name: ");
        String studentLastName = scanner.nextLine();
        studentService.save(new Student(studentId,groupId,studentName,studentLastName ));
        System.out.println("Student added!!");
    }

    private void deleteStudent() {
        System.out.println("Write id");
        long id = scanner.nextLong();
       studentService.deleteById(id);
    }

    private void addStudentToCourse() {
        System.out.println("Write student id: ");
        long studentId = scanner.nextLong();
        System.out.println("Write course id: ");
        long courseId = scanner.nextLong();
        courseService.addStudentToCourse(studentId, courseId);
    }

    private void removeStudentFromCourse() {
        System.out.println("Write student id: ");
        long studentId = scanner.nextLong();
        System.out.println("Write course id: ");
        long courseId = scanner.nextLong();
        courseService.removeStudentFromCourse(studentId, courseId);
    }
}
