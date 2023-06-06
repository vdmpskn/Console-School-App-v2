package com.example.demo;

import com.example.demo.model.Group;
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

    @Override
    public void run(String... args) throws Exception {

        List<Group> groups = groupService.findGroupsWithMaxStudents(4);
        for (Group group : groups) {
            System.out.println(group);
        }

    }
}
