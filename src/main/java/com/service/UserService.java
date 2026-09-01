package com.service;

import com.entity.User;

public interface UserService {

    User findByUsername(String username);

    User findById(Long id);

    User save(User user);
}