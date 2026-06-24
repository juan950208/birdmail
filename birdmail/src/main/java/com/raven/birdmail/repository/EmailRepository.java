package com.raven.birdmail.repository;

import com.raven.birdmail.models.Email;
import com.raven.birdmail.models.EmailRecipient;
import com.raven.birdmail.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class EmailRepository {

    @PersistenceContext
    EntityManager entityManager;

    public Email saveEmail(Email email) {
        return entityManager.merge(email);
    }

    public void saveEmailRecipientRelation(EmailRecipient emailRecipient) {
            entityManager.merge(emailRecipient);
    }

    public Email findById(Long id) {
        return entityManager.find(Email.class, id);
    }

    public List<Email> getAllReceivedEmails(User user) {
        String sql = "SELECT e FROM Email e WHERE e.recipient.id = :recipientId";

        TypedQuery<Email> query = entityManager.createQuery(sql, Email.class)
                .setParameter("recipientId", user.getId());

        return query.getResultList();
    }

    public List<EmailRecipient> getEmailRecipientByUser(User user) {
        String sql = "SELECT er FROM EmailRecipient er WHERE er.recipient.id = :recipientId";
        TypedQuery<EmailRecipient> query = entityManager.createQuery(sql, EmailRecipient.class)
                .setParameter("recipientId", user.getId());

        List<EmailRecipient> resultList = query.getResultList();
        return resultList;
    }

    public List<Email> getAllSentEmails(User user) {
        String sql = "SELECT e FROM Email e WHERE e.sender.id = :senderId";

        TypedQuery<Email> query = entityManager.createQuery(sql, Email.class)
                .setParameter("senderId", user.getId());

        return query.getResultList();
    }

}
