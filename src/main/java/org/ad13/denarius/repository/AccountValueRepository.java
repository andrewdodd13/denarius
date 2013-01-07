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

	@Query(value = "SELECT entry FROM AccountEntry entry WHERE entry.account = ?1 AND entry.entryDate <= ?2 ORDER BY entry.entryDate DESC")
	public List<AccountEntry> findByAccountBeforeDate(Account account, LocalDate date);

	/**
	 * Retrieves all the {@link AccountEntry}s between two dates, inclusive, and
	 * ordered by date in chronological order.
	 * 
	 * @param account
	 *            the account to get the entries for
	 * @param startDate
	 *            the first date to include (inclusive)
	 * @param endDate
	 *            the last date to include (inclusive)
	 * @return
	 */
	@Query(value = "SELECT entry FROM AccountEntry entry WHERE entry.account = ?1 AND entry.entryDate >= ?2 AND entry.entryDate <= ?3 ORDER BY entry.entryDate")
	public List<AccountEntry> findByAccountBetweenTwoDates(Account account, LocalDate startDate, LocalDate endDate);
}
