package com.Idcard.idcard.repository;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.Idcard.idcard.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByRollNo(String rollNo);

    @Modifying
    @Query("DELETE FROM Student s WHERE s.id = :id")
    int deleteStudentbyId(@Param("id") Integer id);
}
