package com.Idcard.idcard.entity;

import jakarta.persistence.*;
// import lombok.*;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Ensure auto-increment
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String className;

    @Column(nullable = false)
    private String year;

    @Column(unique = true, nullable = false)
    private String rollNo;

    @Column(columnDefinition = "LONGTEXT") // ✅ Change to LONGTEXT
    private String photoUrl;



    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Student(Long id, String name, String className, String year, String rollNo,
            String photoUrl) {
        this.id = id;
        this.name = name;
        this.className = className;
        this.year = year;
        this.rollNo = rollNo;
        this.photoUrl = photoUrl;
    }

    public Student() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }


}
