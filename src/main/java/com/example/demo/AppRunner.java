package com.example.demo;

import com.example.demo.front.ConsoleMenu;
import com.example.demo.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements ApplicationRunner {

    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private ConsoleMenu consoleMenu;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        databaseService.createDatabase();
        consoleMenu.start();
    }
}
