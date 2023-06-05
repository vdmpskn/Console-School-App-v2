package com.example.demo.model;

public record Student(
        Long student_id,
        Long group_id,
        String first_name,
        String last_name
) {
}
