package com.Idcard.idcard.repository;

import org.springframework.stereotype.Repository;
import com.Idcard.idcard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
// import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    public Optional<User> findByUsernameAndPassword(String username, String password);

    @Query("SELECT u FROM User u JOIN u.userToken ut WHERE ut.token = :token")
    public User findByToken(@Param("token") String token);
}
