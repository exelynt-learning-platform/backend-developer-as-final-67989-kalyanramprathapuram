package com.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "resources")
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private boolean available;


    // =====================================================
    // DEFAULT CONSTRUCTOR
    // =====================================================

    public Resource() {
    }


    // =====================================================
    // PARAMETERIZED CONSTRUCTOR
    // =====================================================

    public Resource(
            String name,
            String type,
            String description,
            boolean available) {

        this.name = name;
        this.type = type;
        this.description = description;
        this.available = available;
    }


    // =====================================================
    // GET ID
    // =====================================================

    public Long getId() {
        return id;
    }


    // =====================================================
    // SET ID
    // =====================================================

    public void setId(Long id) {
        this.id = id;
    }


    // =====================================================
    // GET NAME
    // =====================================================

    public String getName() {
        return name;
    }


    // =====================================================
    // SET NAME
    // =====================================================

    public void setName(String name) {
        this.name = name;
    }


    // =====================================================
    // GET TYPE
    // =====================================================

    public String getType() {
        return type;
    }


    // =====================================================
    // SET TYPE
    // =====================================================

    public void setType(String type) {
        this.type = type;
    }


    // =====================================================
    // GET DESCRIPTION
    // =====================================================

    public String getDescription() {
        return description;
    }


    // =====================================================
    // SET DESCRIPTION
    // =====================================================

    public void setDescription(String description) {
        this.description = description;
    }


    // =====================================================
    // GET AVAILABLE
    // =====================================================

    public boolean isAvailable() {
        return available;
    }


    // =====================================================
    // SET AVAILABLE
    // =====================================================

    public void setAvailable(boolean available) {
        this.available = available;
    }
}