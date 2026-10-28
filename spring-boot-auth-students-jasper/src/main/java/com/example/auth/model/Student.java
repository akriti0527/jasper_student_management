package com.example.auth.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String rollNo;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String course;

    @Column(nullable = false)
    private String department;

    private Double gpa;

    private String email;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Student() {}

    public Student(String rollNo, String name, String course, String department, Double gpa, String email) {
        this.rollNo = rollNo;
        this.name = name;
        this.course = course;
        this.department = department;
        this.gpa = gpa;
        this.email = email;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getRollNo() { return rollNo; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public Double getGpa() { return gpa; }
    public void setGpa(Double gpa) { this.gpa = gpa; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
