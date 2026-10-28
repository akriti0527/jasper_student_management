# Spring Boot + PostgreSQL + JasperReports Student Portal

Full-stack application featuring:
1. **User Authentication**: Login / Register with Spring Security session management and BCrypt hashing.
2. **PostgreSQL Integration**: Real-time CRUD storing students and registered users in PostgreSQL.
3. **JasperReports**: Dynamic PDF compilation and export from PostgreSQL table data.
4. **Frontend**: Clean dark dashboard with HTML5, CSS3, and modern Fetch API.

## JasperReports Integration Details
- **Report Template**: Located at `src/main/resources/reports/students_report.jrxml`.
- **Report Service**: `ReportService.java` compiles the JRXML on demand, populates it with a `JRBeanCollectionDataSource` from `StudentRepository`, and exports it directly to PDF bytes using `JasperExportManager`.
- **Controller Endpoint**: `GET /api/reports/students/pdf` provides an authenticated attachment download.
- **Frontend Button**: An "Export Jasper PDF" button is in the dashboard header.

## How to Run

1. Create database in PostgreSQL:
   ```sql
   CREATE DATABASE student_db;
   ```
2. Update `src/main/resources/application.properties` with your PostgreSQL username and password.
3. Start the project:
   ```bash
   mvn clean spring-boot:run
   ```
4. Access in your browser:
   - Login: `http://localhost:8080/login.html`
   - Register: `http://localhost:8080/register.html`
   - Dashboard: `http://localhost:8080/dashboard.html`
