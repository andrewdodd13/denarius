package org.ad13.denarius.repository;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.ad13.denarius.model.Account;
import org.ad13.denarius.model.AccountEntry;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:beans/repository-context.xml")
@Transactional
public class AccountValueRepositoryTest {
	@Autowired
	AccountValueRepository accountValueRepository;

	@Autowired
	AccountRepository accountRepository;

	@Test
	public void testCreate() {
		AccountEntry accountEntry = new AccountEntry();
		accountEntry.setEntryDate(new LocalDate(2013, 1, 1));
		accountEntry.setValue(BigDecimal.TEN);

		accountEntry = accountValueRepository.save(accountEntry);

		AccountEntry persistedAccountEntry = accountValueRepository.findOne(accountEntry.getAccountEntryId());
		assertEquals(accountEntry, persistedAccountEntry);
	}

	@Test
	public void testFindLatestOnGivenDate_NoEntry() {
		// Arrange
		Account account = new Account();
		account.setAccountName("Test Account");
		account = accountRepository.save(account);

		// Act
		List<AccountEntry> accountEntries = accountValueRepository.findByAccountBeforeDate(account, new LocalDate(2013, 1, 1));

		// Assert
		assertEquals(0, accountEntries.size());
	}

	@Test
	public void testFindLatestOnGivenDate_OneEntry() {
		// Arrange
		Account account = new Account();
		account.setAccountName("Test Account");
		account = accountRepository.save(account);

		AccountEntry accountEntry = new AccountEntry();
		accountEntry.setAccount(account);
		accountEntry.setEntryDate(new LocalDate(2012, 12, 1));
		accountEntry.setValue(BigDecimal.TEN);
		accountEntry = accountValueRepository.save(accountEntry);

		// Act
		List<AccountEntry> accountEntries = accountValueRepository.findByAccountBeforeDate(account, new LocalDate(2013, 1, 1));

		// Assert
		assertEquals(1, accountEntries.size());
		assertEquals(accountEntry, accountEntries.get(0));
	}

	@Test
	public void testFindLatestOnGivenDate_MultiEntry() {
		// Arrange
		Account account = new Account();
		account.setAccountName("Test Account");
		account = accountRepository.save(account);

		AccountEntry accountEntry1 = new AccountEntry();
		accountEntry1.setAccount(account);
		accountEntry1.setEntryDate(new LocalDate(2012, 12, 1));
		accountEntry1.setValue(BigDecimal.TEN);
		accountEntry1 = accountValueRepository.save(accountEntry1);

		AccountEntry accountEntry2 = new AccountEntry();
		accountEntry2.setAccount(account);
		accountEntry2.setEntryDate(new LocalDate(2012, 12, 15));
		accountEntry2.setValue(BigDecimal.TEN);
		accountEntry2 = accountValueRepository.save(accountEntry2);

		AccountEntry accountEntry3 = new AccountEntry();
		accountEntry3.setAccount(account);
		accountEntry3.setEntryDate(new LocalDate(2013, 1, 2));
		accountEntry3.setValue(BigDecimal.TEN);
		accountEntry3 = accountValueRepository.save(accountEntry3);

		// Act
		List<AccountEntry> accountEntries = accountValueRepository.findByAccountBeforeDate(account, new LocalDate(2013, 1, 1));

		// Assert
		assertEquals(2, accountEntries.size());
		assertEquals(accountEntry2, accountEntries.get(0));
		assertEquals(accountEntry1, accountEntries.get(1));
	}

	@Test
	public void testFindBetweenTwoDates_NoEntries() {
		// Arrange
		Account account = new Account();
		account.setAccountName("Test Account");
		account = accountRepository.save(account);

		AccountEntry accountEntry1 = new AccountEntry();
		accountEntry1.setAccount(account);
		accountEntry1.setEntryDate(new LocalDate(2012, 12, 1));
		accountEntry1.setValue(BigDecimal.TEN);
		accountEntry1 = accountValueRepository.save(accountEntry1);

		AccountEntry accountEntry2 = new AccountEntry();
		accountEntry2.setAccount(account);
		accountEntry2.setEntryDate(new LocalDate(2012, 12, 15));
		accountEntry2.setValue(BigDecimal.TEN);
		accountEntry2 = accountValueRepository.save(accountEntry2);

		AccountEntry accountEntry3 = new AccountEntry();
		accountEntry3.setAccount(account);
		accountEntry3.setEntryDate(new LocalDate(2013, 1, 2));
		accountEntry3.setValue(BigDecimal.TEN);
		accountEntry3 = accountValueRepository.save(accountEntry3);

		// Act
		List<AccountEntry> accountEntries = accountValueRepository.findByAccountBetweenTwoDates(account, new LocalDate(2012, 6, 1), new LocalDate(2012, 6, 30));

		// Assert
		assertEquals(0, accountEntries.size());
	}

	@Test
	public void testFindBetweenTwoDates_BoundaryCheck() {
		// Arrange
		Account account = new Account();
		account.setAccountName("Test Account");
		account = accountRepository.save(account);

		AccountEntry accountEntry1 = new AccountEntry();
		accountEntry1.setAccount(account);
		accountEntry1.setEntryDate(new LocalDate(2012, 12, 1));
		accountEntry1.setValue(BigDecimal.TEN);
		accountEntry1 = accountValueRepository.save(accountEntry1);

		AccountEntry accountEntry2 = new AccountEntry();
		accountEntry2.setAccount(account);
		accountEntry2.setEntryDate(new LocalDate(2012, 12, 15));
		accountEntry2.setValue(BigDecimal.TEN);
		accountEntry2 = accountValueRepository.save(accountEntry2);

		AccountEntry accountEntry3 = new AccountEntry();
		accountEntry3.setAccount(account);
		accountEntry3.setEntryDate(new LocalDate(2013, 1, 2));
		accountEntry3.setValue(BigDecimal.TEN);
		accountEntry3 = accountValueRepository.save(accountEntry3);

		// Act
		List<AccountEntry> accountEntries = accountValueRepository.findByAccountBetweenTwoDates(account, new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 15));

		// Assert
		assertEquals(2, accountEntries.size());
		assertEquals(accountEntry1, accountEntries.get(0));
		assertEquals(accountEntry2, accountEntries.get(1));
	}
}
