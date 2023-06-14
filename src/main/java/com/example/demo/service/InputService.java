package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class InputService {

    private final Scanner scanner;

    public InputService() {
        scanner = new Scanner(System.in);
    }

    public Long nextLong() {
        return scanner.nextLong();
    }

    public Integer nextInt() {
        return scanner.nextInt();
    }

    public String nextLine() {
        return scanner.nextLine();
    }
}
