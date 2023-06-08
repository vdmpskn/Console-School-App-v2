package com.example.demo;

import com.example.demo.front.ConsoleMenu;
import com.example.demo.model.Group;
import com.example.demo.model.Student;
import com.example.demo.service.CourseService;
import com.example.demo.service.GroupService;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;


@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Autowired
    private StudentService studentService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private CourseService courseService;


    @Override
    public void run(String... args) throws Exception {

        ConsoleMenu consoleMenu = new ConsoleMenu(groupService, studentService, courseService);
        consoleMenu.start();

//        List<Student> students = studentService.findStudentsByCourseName("History");
//
//        for(Student student: students){
//            System.out.println(student);
//        }
//
//        courseService.addStudentToCourse(50,2);
//
//        System.out.println("---------------------------");
//        List<Group> groups = groupService.findGroupsWithMaxStudents(4);
//        for (Group group : groups) {
//            System.out.println(group);
//        }
//
//        courseService.removeStudentFromCourse(50,3);



    }
}
