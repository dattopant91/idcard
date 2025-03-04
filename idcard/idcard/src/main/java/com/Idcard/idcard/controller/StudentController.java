package com.Idcard.idcard.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Idcard.idcard.entity.IdCard;
import com.Idcard.idcard.entity.Student;
import com.Idcard.idcard.entity.User;
import com.Idcard.idcard.repository.IdCardRepository;
import com.Idcard.idcard.repository.StudentRepository;
import com.Idcard.idcard.repository.UserRepository;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
// @RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private IdCardRepository idCardRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/studentlist")
    public List<Student> getAllStudents(HttpSession session) {
        // if (session.getAttribute("username") == null) {
        // throw new RuntimeException("Unauthorized Access! Please log in.");
        // }
        return studentRepository.findAll();
    }

    @GetMapping("/getStudent/{id}")
    public Student testing(@PathVariable("id") Long id) {
        return studentRepository.findById(id).orElseThrow();
    }

    @PostMapping("/addStudent")
    public ResponseEntity<?> addStudent(@RequestBody Map<String, Object> requestData) {
        try {
            // ✅ Extract student & user details from request
            Map<String, Object> studentMap = (Map<String, Object>) requestData.get("student");
            Map<String, Object> userMap = (Map<String, Object>) requestData.get("user");

            if (studentMap == null || userMap == null) {
                return ResponseEntity.badRequest().body("Error: Missing student or user data");
            }

            // ✅ Convert studentMap to Student entity
            Student student = new Student();
            student.setName((String) studentMap.get("name"));
            student.setClassName((String) studentMap.get("className"));
            student.setYear((String) studentMap.get("year"));
            student.setRollNo((String) studentMap.get("rollNo"));
            student.setPhotoUrl((String) studentMap.get("photoUrl"));

            // ✅ Convert userMap to User entity
            User user = new User();
            user.setUsername(student.getRollNo()); // ✅ Set username as rollNo
            String pass = (String) userMap.get("password");
            user.setPassword(pass); // ✅ Encrypt password
            user.setRole("STUDENT");

            // ✅ Save student first
            Student newStudent = studentRepository.save(student);

            // ✅ Save user in the database
            userRepository.save(user);

            // ✅ Generate ID Card Automatically
            if (!idCardRepository.findByStudentId(newStudent.getId()).isPresent()) {
                IdCard idCard = new IdCard();
                idCard.setStudent(newStudent);
                idCard.setIssueDate(LocalDate.now());
                idCard.setExpiryDate(LocalDate.now().plusYears(1));
                idCard.setQrCode("QR-" + newStudent.getId());
                idCardRepository.save(idCard);
            }

            return ResponseEntity.ok(newStudent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }


    @PostMapping("/addStudent1")
    public ResponseEntity<Student> addStudent1(@RequestBody Student student) {
        try {
            // ✅ Check if the request contains a valid `photoUrl`
            if (student.getPhotoUrl() == null || student.getPhotoUrl().isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }

            // ✅ Save student details (including `photoUrl`)
            Student newStudent = studentRepository.save(student);

            // ✅ Generate ID Card Automatically
            if (!idCardRepository.findByStudentId(newStudent.getId()).isPresent()) {
                IdCard idCard = new IdCard();
                idCard.setStudent(newStudent);
                idCard.setIssueDate(LocalDate.now());
                idCard.setExpiryDate(LocalDate.now().plusYears(1));
                idCard.setQrCode("QR-" + newStudent.getId());
                idCardRepository.save(idCard);
            }

            return ResponseEntity.ok(newStudent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/updateStudent")
    public ResponseEntity<Student> updateStudent(@RequestBody Student stud) {
        Optional<Student> studentOpt = studentRepository.findById(stud.getId());

        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();

            // ✅ Update student details
            student.setName(stud.getName());
            student.setClassName(stud.getClassName());
            student.setYear(stud.getYear());
            student.setRollNo(stud.getRollNo());

            Student savedStudent = studentRepository.save(student);

            // ✅ Update ID Card details if exists
            Optional<IdCard> idCardOpt = idCardRepository.findByStudentId(stud.getId());
            if (idCardOpt.isPresent()) {
                IdCard idCard = idCardOpt.get();
                idCard.setExpiryDate(LocalDate.now().plusYears(1)); // Extend validity on update
                idCardRepository.save(idCard);
            }

            return ResponseEntity.ok(savedStudent);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/deleteStudent/{id}")
    @Transactional
    public ResponseEntity<?> deleteStudent(@PathVariable("id") Long id) {
        Optional<Student> studentOpt = studentRepository.findById(id);

        if (studentOpt.isPresent()) {
            // ✅ Delete associated ID card first
            idCardRepository.deleteByStudentId(id);

            // ✅ Now delete student
            studentRepository.deleteById(id);

            return ResponseEntity
                    .ok(Collections.singletonMap("message", "Student deleted successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Student not found");
        }
    }



}
