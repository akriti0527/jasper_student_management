package com.example.auth.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class StudentDto {

    @NotBlank(message = "Roll Number is required")
    private String rollNo;

    @NotBlank(message = "Student name is required")
    private String name;

    @NotBlank(message = "Course is required")
    private String course;

    @NotBlank(message = "Department is required")
    private String department;

    @DecimalMin(value = "0.0", message = "GPA cannot be less than 0.0")
    @DecimalMax(value = "10.0", message = "GPA cannot exceed 10.0")
    private Double gpa;

    @Email(message = "Valid email is required")
    private String email;

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
}
