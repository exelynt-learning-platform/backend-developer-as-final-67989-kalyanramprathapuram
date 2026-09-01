package com.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    // =========================================================
    // ID
    // =========================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // =========================================================
    // USERNAME
    // =========================================================

    @Column(nullable = false, unique = true)
    private String username;

    // =========================================================
    // PASSWORD
    // =========================================================

    @Column(nullable = false)
    private String password;

    // =========================================================
    // ROLE
    // =========================================================

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // =========================================================
    // DEFAULT CONSTRUCTOR
    // =========================================================

    public User() {
    }

    // =========================================================
    // PARAMETERIZED CONSTRUCTOR
    // =========================================================

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // =========================================================
    // GET ID
    // =========================================================

    public Long getId() {
        return id;
    }

    // =========================================================
    // SET ID
    // =========================================================

    public void setId(Long id) {
        this.id = id;
    }

    // =========================================================
    // GET USERNAME
    // =========================================================

    public String getUsername() {
        return username;
    }

    // =========================================================
    // SET USERNAME
    // =========================================================

    public void setUsername(String username) {
        this.username = username;
    }

    // =========================================================
    // GET PASSWORD
    // =========================================================

    public String getPassword() {
        return password;
    }

    // =========================================================
    // SET PASSWORD
    // =========================================================

    public void setPassword(String password) {
        this.password = password;
    }

    // =========================================================
    // GET ROLE
    // =========================================================

    public Role getRole() {
        return role;
    }

    // =========================================================
    // SET ROLE
    // =========================================================

    public void setRole(Role role) {
        this.role = role;
    }
}