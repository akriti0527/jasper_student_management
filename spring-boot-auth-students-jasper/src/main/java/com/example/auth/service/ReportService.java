package com.example.auth.service;

import com.example.auth.model.Student;
import com.example.auth.repository.StudentRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    private final StudentRepository studentRepository;

    public ReportService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public byte[] generateStudentReportPdf(String generatedBy) throws Exception {
        List<Student> students = studentRepository.findAll();

        // Load JRXML report template from classpath
        InputStream reportStream = new ClassPathResource("reports/students_report.jrxml").getInputStream();
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        // Bind student list data source
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(students);

        // Add report parameters
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("generatedBy", (generatedBy != null && !generatedBy.isBlank()) ? generatedBy : "System Admin");
        parameters.put("totalStudents", students.size());

        // Fill and export to PDF byte array
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
