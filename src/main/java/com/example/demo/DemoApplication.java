package com.example.demo;

import com.example.demo.model.Student;
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

    @Override
    public void run(String... args) throws Exception {
        Student student = studentService.save(new Student(210L, 5L, "YEhor", "Kokicj"));
        System.out.println("Student name: " + student.first_name() + " " + student.last_name() + " id: " + student.student_id());

        List<Student> allStudents = studentService.findAll();
        for (Student temp : allStudents) {
            System.out.println(temp);
        }


        Student studentById = studentService.findById(2L);
        System.out.println("Student by id: " + studentById);

        studentService.deleteById(2L);
        System.out.println("Student was successfully deleted!");
        System.out.println(studentService.findAll());
    }
}
