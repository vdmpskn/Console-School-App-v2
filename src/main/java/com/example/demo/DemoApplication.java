package com.example.demo;

import com.example.demo.front.ConsoleMenu;
import com.example.demo.service.CourseService;
import com.example.demo.service.GroupService;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




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
    public void run(String... args)  {
    ConsoleMenu consoleMenu = new ConsoleMenu(groupService,studentService,courseService);
    consoleMenu.start();
    }
}
