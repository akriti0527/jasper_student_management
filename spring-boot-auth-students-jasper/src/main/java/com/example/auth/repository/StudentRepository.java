package com.example.auth.repository;

import com.example.auth.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByRollNo(String rollNo);
    Optional<Student> findByRollNo(String rollNo);
}
