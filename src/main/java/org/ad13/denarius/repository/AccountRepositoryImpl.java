package org.ad13.denarius.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.ad13.denarius.controller.ResourceNotFoundException;
import org.ad13.denarius.dto.AccountDTO;
import org.ad13.denarius.dto.AccountEntryDTO;
import org.ad13.denarius.model.Account;
import org.ad13.denarius.model.AccountEntry;
import org.ad13.denarius.model.User;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class AccountRepositoryImpl implements AccountRepository {
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
        User owner = entityManager.find(User.class, ownerId);
        if (owner == null) {
            throw new ResourceNotFoundException();
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> cq = cb.createQuery(Account.class);
        Root<Account> account = cq.from(Account.class);
        cq.where(cb.equal(account.get("owner"), owner));

        return entityManager.createQuery(cq).getResultList();
    }

    public long create(AccountDTO accountDto) {
        Account account = new Account();
        account.setAccountName(accountDto.getAccountName());

        User owner = entityManager.find(User.class, accountDto.getOwnerId());
        if (owner == null) {
            throw new ResourceNotFoundException();
        }
        account.setOwner(owner);

        account = entityManager.merge(account);
        return account.getAccountId();
    }

    public void update(AccountDTO accountDto) {
        Account account = entityManager.find(Account.class, accountDto.getAccountId());
        if (account == null) {
            throw new ResourceNotFoundException();
        }

        account.setAccountName(accountDto.getAccountName());

        User owner = entityManager.find(User.class, accountDto.getOwnerId());
        if (owner == null) {
            throw new ResourceNotFoundException();
        }
        account.setOwner(owner);

        entityManager.merge(account);
    }

    public AccountEntry getAccountValue(long accountId) {
        return getAccountValue(accountId, LocalDate.now());
    }

    public AccountEntry getAccountValue(long accountId, LocalDate date) {
        Account account = getAccount(accountId);
        if (account == null) {
            throw new ResourceNotFoundException();
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AccountEntry> cq = cb.createQuery(AccountEntry.class);
        Root<AccountEntry> accountEntry = cq.from(AccountEntry.class);
        cq.where(cb.and(cb.lessThanOrEqualTo(accountEntry.<LocalDate> get("entryDate"), date),
                cb.equal(accountEntry.get("account"), account)));

        cq.orderBy(cb.desc(accountEntry.get("entryDate")));

        TypedQuery<AccountEntry> q = entityManager.createQuery(cq);
        q.setMaxResults(1);

        try {
            return q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<AccountEntry> getAccountValues(long accountId, short year, short month) {
        Account account = getAccount(accountId);
        if (account == null) {
            throw new ResourceNotFoundException();
        }

        LocalDate start = new LocalDate(year, month, 1);
        LocalDate end = start.plusMonths(1);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AccountEntry> cq = cb.createQuery(AccountEntry.class);
        Root<AccountEntry> accountEntry = cq.from(AccountEntry.class);
        cq.where(cb.and(cb.greaterThanOrEqualTo(accountEntry.<LocalDate> get("entryDate"), start),
                cb.lessThan(accountEntry.<LocalDate> get("entryDate"), end),
                cb.equal(accountEntry.get("account"), account)));

        cq.orderBy(cb.asc(accountEntry.get("entryDate")));

        return entityManager.createQuery(cq).getResultList();
    }

    public long setAccountValue(AccountEntryDTO accountEntryDTO) {
        AccountEntry accountEntry = new AccountEntry();
        accountEntry.setEntryDate(accountEntryDTO.getDate());
        accountEntry.setValue(accountEntryDTO.getValue());

        Account account = getAccount(accountEntryDTO.getAccountId());
        if (account == null) {
            throw new ResourceNotFoundException();
        }
        accountEntry.setAccount(account);

        accountEntry = entityManager.merge(accountEntry);
        return accountEntry.getAccountEntryId();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
