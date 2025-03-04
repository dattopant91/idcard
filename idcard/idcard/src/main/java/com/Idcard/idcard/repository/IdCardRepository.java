package com.Idcard.idcard.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.Idcard.idcard.entity.IdCard;

public interface IdCardRepository extends JpaRepository<IdCard, Long> {
    Optional<IdCard> findByStudentId(Long studentId);

    @Modifying
    @Query("DELETE FROM IdCard i WHERE i.student.id = :studentId")
    void deleteByStudentId(@Param("studentId") Long studentId);
}
