package org.ad13.denarius.repository;

import java.util.List;

import org.ad13.denarius.model.Account;
import org.ad13.denarius.model.AccountEntry;
import org.joda.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AccountValueRepository extends JpaRepository<AccountEntry, Long> {
	public List<AccountEntry> findAllByAccount(Account account);
	
	@Query(value="SELECT entry FROM AccountEntry entry WHERE entry.account = ?1 AND entry.entryDate <= ?2 ORDER BY entry.entryDate DESC")
	public List<AccountEntry> findByAccountBeforeDate(Account account, LocalDate date);
	
//    public List<AccountEntry> getAccountValues(long accountId, short year, short month) {
//        Account account = getAccount(accountId);
//        if (account == null) {
//            throw new ResourceNotFoundException();
//        }
//
//        LocalDate start = new LocalDate(year, month, 1);
//        LocalDate end = start.plusMonths(1);
//
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<AccountEntry> cq = cb.createQuery(AccountEntry.class);
//        Root<AccountEntry> accountEntry = cq.from(AccountEntry.class);
//        cq.where(cb.and(cb.greaterThanOrEqualTo(accountEntry.<LocalDate> get("entryDate"), start),
//                cb.lessThan(accountEntry.<LocalDate> get("entryDate"), end),
//                cb.equal(accountEntry.get("account"), account)));
//
//        cq.orderBy(cb.asc(accountEntry.get("entryDate")));
//
//        return entityManager.createQuery(cq).getResultList();
//    }
}
