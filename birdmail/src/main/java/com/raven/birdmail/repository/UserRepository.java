package com.raven.birdmail.repository;

import com.raven.birdmail.models.User;

public interface UserRepository {
    User byId(Long id);
    User create (User user);
    boolean existsByEmail(String email);
    User findByEmail(String email);
}
