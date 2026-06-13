package com.raven.birdmail.Repository;

import com.raven.birdmail.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public User byId(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User create(User user) {

//        if (exists(user.getUsername())) {
//            throw new IllegalArgumentException("The username is already in use");
//        }

        return entityManager.merge(user);
    }

    @Override
    public boolean exists(String username) {
//        String jpql = "SELECT COUNT FROM User u where u.username = :username";
//
//        Long count = entityManager
//                .createQuery(jpql, Long.class)
//                .setParameter("username", username)
//                .getSingleResult();
//
//        return count > 0;
        return false;
    }
}
