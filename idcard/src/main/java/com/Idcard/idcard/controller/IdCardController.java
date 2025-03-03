package com.Idcard.idcard.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Idcard.idcard.entity.IdCard;
import com.Idcard.idcard.service.IdCardService;


@RestController
@RequestMapping("/api/id-cards")
public class IdCardController {

    @Autowired
    private IdCardService idCardService;

    // ✅ Get all ID cards
    @GetMapping
    public List<IdCard> getAllIdCards() {
        return idCardService.getAllIdCards();
    }

    // ✅ Get an ID card by student ID
    @GetMapping("/{studentId}")
    public Optional<IdCard> getIdCardByStudentId(@PathVariable Long studentId) {
        return idCardService.getIdCardByStudentId(studentId);
    }

    // ✅ Create an ID card for a student
    @PostMapping("/{studentId}")
    public IdCard createIdCard(@PathVariable Long studentId) {
        return idCardService.createIdCard(studentId);
    }

    // ✅ Delete an ID card by ID
    @DeleteMapping("/{id}")
    public void deleteIdCard(@PathVariable Long id) {
        idCardService.deleteIdCard(id);
    }
}
