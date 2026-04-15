package org.example.student;

public record Student (
    int id,
    String name,
    String email,
    int batch,
    String gender
) {};
