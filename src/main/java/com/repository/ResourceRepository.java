package com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Resource;

public interface ResourceRepository
        extends JpaRepository<Resource, Long> {

    Optional<Resource> findByName(String name);
}