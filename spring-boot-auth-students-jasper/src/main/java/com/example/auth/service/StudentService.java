package com.example.auth.service;

import com.example.auth.dto.StudentDto;
import com.example.auth.model.Student;
import com.example.auth.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student addStudent(StudentDto dto) {
        if (studentRepository.existsByRollNo(dto.getRollNo())) {
            throw new IllegalArgumentException("Student with Roll No: " + dto.getRollNo() + " already exists!");
        }

        Student student = new Student(
                dto.getRollNo(),
                dto.getName(),
                dto.getCourse(),
                dto.getDepartment(),
                dto.getGpa(),
                dto.getEmail()
        );

        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
