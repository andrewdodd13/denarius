package org.ad13.denarius.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.ad13.denarius.controller.ResourceNotFoundException;
import org.ad13.denarius.model.Account;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class AccountDaoImp implements AccountDao {
    private EntityManager entityManager;

    public Account getAccount(long accountId) {
        return entityManager.find(Account.class, accountId);
    }

    public void deleteAccount(long accountId) {
        Account account = entityManager.find(Account.class, accountId);
        if (account == null) {
            throw new ResourceNotFoundException();
        }

        entityManager.remove(account);
    }

    public List<Account> getAccountsForUser(long ownerId) {
        Query q = entityManager.createQuery("from Account where owner_id = 1");
        return (List<Account>) q.getResultList();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
