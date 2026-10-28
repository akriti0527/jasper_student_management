function showAlert(alertElementId, message, isSuccess = true) {
    const alertBox = document.getElementById(alertElementId);
    if (!alertBox) return;
    alertBox.textContent = message;
    alertBox.className = `alert ${isSuccess ? 'alert-success' : 'alert-error'}`;
    alertBox.style.display = 'block';
    setTimeout(() => {
        alertBox.style.display = 'none';
    }, 5000);
}

// 1. REGISTER
const registerForm = document.getElementById('registerForm');
if (registerForm) {
    registerForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const fullName = document.getElementById('fullName').value.trim();
        const email = document.getElementById('email').value.trim();
        const password = document.getElementById('password').value;

        try {
            const res = await fetch('/api/auth/register', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ fullName, email, password })
            });

            const data = await res.json();
            if (res.ok) {
                showAlert('alertMessage', data.message + ' Redirecting to login...', true);
                setTimeout(() => window.location.href = '/login.html', 1500);
            } else {
                showAlert('alertMessage', data.message || 'Registration failed', false);
            }
        } catch (err) {
            showAlert('alertMessage', 'Unable to connect to the server', false);
        }
    });
}

// 2. LOGIN
const loginForm = document.getElementById('loginForm');
if (loginForm) {
    loginForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const email = document.getElementById('email').value.trim();
        const password = document.getElementById('password').value;

        try {
            const res = await fetch('/api/auth/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ email, password })
            });

            const data = await res.json();
            if (res.ok) {
                window.location.href = '/dashboard.html';
            } else {
                showAlert('alertMessage', data.message || 'Login failed', false);
            }
        } catch (err) {
            showAlert('alertMessage', 'Unable to connect to the server', false);
        }
    });
}

// 3. DASHBOARD OPERATIONS
if (window.location.pathname.endsWith('dashboard.html')) {

    async function loadCurrentUser() {
        try {
            const res = await fetch('/api/auth/current-user');
            if (!res.ok) throw new Error('Unauthorized');
            const user = await res.json();
            document.getElementById('userName').textContent = user.name;
            document.getElementById('userEmail').textContent = user.email;
        } catch (err) {
            window.location.href = '/login.html';
        }
    }

    async function fetchStudents() {
        try {
            const res = await fetch('/api/students');
            if (res.status === 401 || res.status === 403) {
                window.location.href = '/login.html';
                return;
            }
            const students = await res.json();
            renderStudentTable(students);
        } catch (err) {
            showAlert('tableAlert', 'Error loading students', false);
        }
    }

    function renderStudentTable(students) {
        const tbody = document.getElementById('studentTableBody');
        const countSpan = document.getElementById('studentCount');
        countSpan.textContent = students.length;

        if (!students || students.length === 0) {
            tbody.innerHTML = `<tr><td colspan="6" style="text-align: center; color: #94a3b8;">No students recorded yet. Use the form to add one!</td></tr>`;
            return;
        }

        tbody.innerHTML = students.map(student => `
            <tr>
                <td><strong>${student.rollNo}</strong></td>
                <td>
                    <div>${student.name}</div>
                    <small style="color: #94a3b8;">${student.email || 'N/A'}</small>
                </td>
                <td>${student.department}</td>
                <td>${student.course}</td>
                <td><span class="badge-gpa">${Number(student.gpa).toFixed(1)}</span></td>
                <td>
                    <button class="btn btn-danger btn-sm" onclick="deleteStudent(${student.id})">Delete</button>
                </td>
            </tr>
        `).join('');
    }

    const studentForm = document.getElementById('studentForm');
    if (studentForm) {
        studentForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const studentData = {
                rollNo: document.getElementById('rollNo').value.trim(),
                name: document.getElementById('name').value.trim(),
                email: document.getElementById('studentEmail').value.trim(),
                department: document.getElementById('department').value.trim(),
                course: document.getElementById('course').value.trim(),
                gpa: parseFloat(document.getElementById('gpa').value)
            };

            try {
                const res = await fetch('/api/students', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(studentData)
                });

                if (res.ok) {
                    showAlert('studentFormAlert', 'Student added successfully!', true);
                    studentForm.reset();
                    fetchStudents();
                } else {
                    const errData = await res.json();
                    showAlert('studentFormAlert', errData.message || 'Failed to add student', false);
                }
            } catch (err) {
                showAlert('studentFormAlert', 'Network error occurred', false);
            }
        });
    }

    window.deleteStudent = async function(id) {
        if (!confirm('Are you sure you want to delete this student record?')) return;
        try {
            const res = await fetch(`/api/students/${id}`, { method: 'DELETE' });
            if (res.ok) {
                fetchStudents();
            } else {
                showAlert('tableAlert', 'Failed to delete student', false);
            }
        } catch (err) {
            showAlert('tableAlert', 'Error connecting to server', false);
        }
    };

    // JasperReports PDF Download Trigger
    const downloadPdfBtn = document.getElementById('downloadPdfBtn');
    if (downloadPdfBtn) {
        downloadPdfBtn.addEventListener('click', async () => {
            try {
                downloadPdfBtn.disabled = true;
                downloadPdfBtn.textContent = 'Generating PDF...';

                const response = await fetch('/api/reports/students/pdf');
                if (!response.ok) throw new Error('PDF Generation failed');

                const blob = await response.blob();
                const downloadUrl = window.URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.href = downloadUrl;
                a.download = 'students_report.pdf';
                document.body.appendChild(a);
                a.click();
                a.remove();
                window.URL.revokeObjectURL(downloadUrl);
            } catch (err) {
                showAlert('tableAlert', 'Failed to generate Jasper PDF report', false);
            } finally {
                downloadPdfBtn.disabled = false;
                downloadPdfBtn.textContent = '📄 Export Jasper PDF';
            }
        });
    }

    const logoutBtn = document.getElementById('logoutBtn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', async () => {
            await fetch('/api/auth/logout', { method: 'POST' });
            window.location.href = '/login.html';
        });
    }

    loadCurrentUser();
    fetchStudents();
}
