package com.raven.birdmail.repository;

import com.raven.birdmail.models.Email;
import com.raven.birdmail.models.EmailRecipient;
import com.raven.birdmail.models.RecipientType;
import com.raven.birdmail.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class EmailRecipientRepository {

    @PersistenceContext
    EntityManager entityManager;

    public List<EmailRecipient> getRecipients(Email email) {

        String jpql = "SELECT er FROM EmailRecipient er WHERE er.email.id = :emailId";

        return entityManager
                .createQuery(jpql, EmailRecipient.class)
                .setParameter("emailId", email.getId())
                .getResultList();

    }

    public boolean userHasAccessToEmail(User user, Email email) {

        if (email.getSender().getId().equals(user.getId())) {
            return true;
        }

        EmailRecipient result = getEmailRecipient(user, email);

        return result != null;
    }

    public EmailRecipient getEmailRecipient(User user, Email email) {
        String sql = "SELECT er FROM EmailRecipient er WHERE er.email.id = :emailId AND er.recipient.id = :recipientId";

        TypedQuery<EmailRecipient> query = entityManager.createQuery(sql, EmailRecipient.class)
                .setParameter("emailId", email.getId())
                .setParameter("recipientId", user.getId());

        return query.getResultList().getFirst();
    }

    public List<EmailRecipient> getEmailRecipientByUser(User user) {
        String sql = "SELECT er FROM EmailRecipient er WHERE er.recipient.id = :recipientId";
        TypedQuery<EmailRecipient> query = entityManager.createQuery(sql, EmailRecipient.class)
                .setParameter("recipientId", user.getId());

        List<EmailRecipient> resultList = query.getResultList();
        return resultList;
    }
}

