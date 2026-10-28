package com.example.auth.controller;

import com.example.auth.dto.ApiResponse;
import com.example.auth.dto.StudentDto;
import com.example.auth.model.Student;
import com.example.auth.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@Valid @RequestBody StudentDto dto) {
        try {
            Student savedStudent = studentService.addStudent(dto);
            return ResponseEntity.ok(savedStudent);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok(new ApiResponse(true, "Student record deleted successfully"));
    }
}
