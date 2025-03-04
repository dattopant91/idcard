package com.Idcard.idcard.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
// import com.Idcard.idcard.entity.User;
import com.Idcard.idcard.entity.UserToken;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

    // Optional<UserToken> findByToken(String token);
    @Query("SELECT ut FROM UserToken ut WHERE ut.token = :token")
    Optional<UserToken> findByToken(@Param("token") String token);

    // Optional<UserToken> findByUserId(User userId);
    Optional<UserToken> findByUserId(Long userId);

    // void deleteByToken(String token);

    @Modifying
    @Query("DELETE FROM UserToken ut WHERE ut.token = :token")
    int deleteByToken(@Param("token") String token);

}
