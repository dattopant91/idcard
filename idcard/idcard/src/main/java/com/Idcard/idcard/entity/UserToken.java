package com.Idcard.idcard.entity;

import jakarta.persistence.*;
// import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_tokens")
public class UserToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    // @Column(nullable = false)
    // private Long userId;

    @OneToOne // ✅ Foreign key relationship (Each user has one token)
    @JoinColumn(name = "user_id", nullable = false) // ✅ Links to User table
    private User user;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();


    public UserToken(Long id, String token, User user, LocalDateTime createdAt) {
        this.id = id;
        this.token = token;
        this.user = user;
        this.createdAt = createdAt;
    }

    public UserToken() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


}
