package com.Idcard.idcard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Idcard.idcard.entity.IdCard;
import com.Idcard.idcard.entity.Student;
import com.Idcard.idcard.repository.IdCardRepository;
import com.Idcard.idcard.repository.StudentRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class IdCardService {

    @Autowired
    private IdCardRepository idCardRepository;

    @Autowired
    private StudentRepository studentRepository;

    // ✅ Get all ID cards
    public List<IdCard> getAllIdCards() {
        return idCardRepository.findAll();
    }

    // ✅ Get ID card by student ID
    public Optional<IdCard> getIdCardByStudentId(Long studentId) {
        return idCardRepository.findByStudentId(studentId);
    }

    // ✅ Create an ID card for a student
    public IdCard createIdCard(Long studentId) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();

            // Check if an ID card already exists
            if (idCardRepository.findByStudentId(studentId).isPresent()) {
                throw new RuntimeException("ID Card already exists for this student!");
            }

            IdCard idCard = new IdCard();
            idCard.setStudent(student);
            idCard.setIssueDate(LocalDate.now());
            idCard.setExpiryDate(LocalDate.now().plusYears(1)); // 1-year validity
            idCard.setQrCode("QR-" + student.getId()); // Generate a simple QR Code

            return idCardRepository.save(idCard);
        } else {
            throw new RuntimeException("Student not found!");
        }
    }

    // ✅ Delete ID card by ID
    public void deleteIdCard(Long id) {
        idCardRepository.deleteById(id);
    }
}
