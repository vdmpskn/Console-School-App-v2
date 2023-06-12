package com.example.demo.model;

public record Student(
        Long student_id,
        Long group_id,
        String first_name,
        String last_name
) {
    @Override
    public String toString() {
        return "Student{" +
                "student_id=" + student_id +
                ", group_id=" + group_id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                '}';
    }
}
