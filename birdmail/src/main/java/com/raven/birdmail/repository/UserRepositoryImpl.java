package com.raven.birdmail.repository;

import com.raven.birdmail.constant.GlobalConstants;
import com.raven.birdmail.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

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
        user.setEmail(user.getEmail() + GlobalConstants.DOMAIN);
        return entityManager.merge(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        String jpql = "SELECT COUNT(u) FROM User u where u.email = :email";

        Long count = entityManager
                .createQuery(jpql, Long.class)
                .setParameter("email", email)
                .getSingleResult();

        return count > 0;
    }

    @Override
    public User findByEmail(String email) {
        String sql = "FROM User WHERE email = :email";

        TypedQuery<User> query = entityManager.createQuery(sql, User.class)
                .setParameter("email", email);
        List<User> resultList = query.getResultList();

        if (resultList.isEmpty()) {
            return null;
        }

        return resultList.getFirst();
    }
}
