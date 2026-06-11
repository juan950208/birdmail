package com.raven.birdmail.Repository;

import com.raven.birdmail.Models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class UserRepositoryImpl implements CrudRepository<User> {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public User byId(Long id) {
        return entityManager.find(User.class, id);
    }

    private boolean existsByUsername(String username) {
        return entityManager.find(User.class, username) != null;
    }

    @Override
    public void create(User user) {

        if (existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("The username is already in use");
        }

        entityManager.merge(user);
    }
}
