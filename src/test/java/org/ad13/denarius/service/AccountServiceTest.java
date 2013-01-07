package org.ad13.denarius.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.ad13.denarius.controller.ResourceNotFoundException;
import org.ad13.denarius.model.Account;
import org.ad13.denarius.model.AccountEntry;
import org.ad13.denarius.model.User;
import org.ad13.denarius.repository.AccountRepository;
import org.ad13.denarius.repository.AccountValueRepository;
import org.ad13.denarius.repository.UserRepository;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class AccountServiceTest {

	@Test(expected = ResourceNotFoundException.class)
	public void testGetAccount_NonExistingAccount() {
		// Arrange
		final long accountId = 1;

		AccountRepository accountRepository = mock(AccountRepository.class);
		when(accountRepository.findOne(accountId)).thenReturn(null);

		AccountService accountService = new AccountService();
		accountService.setAccountRepository(accountRepository);

		// Act
		accountService.getAccount(accountId);
	}

	@Test
	public void testGetAccount() {
		// Arrange
		final long accountId = 1;
		final String accountName = "test-account";
		final Account account = new Account();
		account.setAccountId(accountId);
		account.setAccountName(accountName);

		AccountRepository accountRepository = mock(AccountRepository.class);
		when(accountRepository.findOne(accountId)).thenReturn(account);

		AccountService accountService = new AccountService();
		accountService.setAccountRepository(accountRepository);

		// Act
		Account resultAccount = accountService.getAccount(accountId);

		// Assert
		assertEquals(accountId, resultAccount.getAccountId());
		assertEquals(accountName, resultAccount.getAccountName());
		verify(accountRepository).findOne(accountId);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateAccount_NullParameters() {
		AccountService accountService = new AccountService();

		accountService.createAccount(null, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateAccount_NullParameter_Name() {
		AccountService accountService = new AccountService();

		accountService.createAccount(null, new User());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateAccount_NullParameter_User() {
		AccountService accountService = new AccountService();

		accountService.createAccount("Test", null);
	}

	@Test
	public void testCreateAccount() {
		// Arrange
		User user = new User();
		user.setUsername("test-user");

		AccountRepository accountRepository = mock(AccountRepository.class);
		when(accountRepository.save(any(Account.class))).thenReturn(new Account(1, user, "test-account", new HashSet<AccountEntry>()));

		AccountService accountService = new AccountService();
		accountService.setAccountRepository(accountRepository);

		// Act
		Account account = accountService.createAccount("test-account", user);

		// Assert
		assertEquals("test-account", account.getAccountName());
		assertEquals("test-user", account.getOwner().getUsername());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateAccount_NullParameters_Name() {
		AccountService accountService = new AccountService();

		accountService.updateAccount(1, null);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testUpdateAccount_NonExistingAccount() {
		// Arrange
		final long accountId = 1;

		AccountRepository accountRepository = mock(AccountRepository.class);

		when(accountRepository.findOne(accountId)).thenReturn(null);

		AccountService service = new AccountService();
		service.setAccountRepository(accountRepository);

		// Act
		service.updateAccount(accountId, "test-name");
	}

	@Test
	public void testUpdateAccount() {
		// Arrange
		final Account testAccount = new Account();
		final long accountId = 1;
		final String accountNewName = "test-new-account";

		testAccount.setAccountId(accountId);
		testAccount.setAccountName("test-account");

		AccountRepository accountRepository = mock(AccountRepository.class);

		when(accountRepository.findOne(accountId)).thenReturn(testAccount);
		when(accountRepository.save(testAccount)).thenReturn(new Account(1, null, accountNewName, new HashSet<AccountEntry>()));

		AccountService service = new AccountService();
		service.setAccountRepository(accountRepository);

		// Act
		Account resultAccount = service.updateAccount(accountId, accountNewName);

		// Assert
		assertEquals(accountNewName, resultAccount.getAccountName());
		assertEquals(accountId, resultAccount.getAccountId());

		verify(accountRepository).findOne(accountId);
		verify(accountRepository).save(testAccount);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testDeleteAccount_NonExistingAccount() {
		// Arrange
		final long accountId = 1;

		AccountRepository accountRepository = mock(AccountRepository.class);

		when(accountRepository.findOne(accountId)).thenReturn(null);

		AccountService service = new AccountService();
		service.setAccountRepository(accountRepository);

		// Act
		service.deleteAccount(accountId);
	}

	@Test
	public void testDeleteAccount() {
		// Arrange
		final long accountId = 1;

		final Account testAccount = new Account();
		testAccount.setAccountId(accountId);
		testAccount.setAccountName("test-account");

		AccountRepository accountRepository = mock(AccountRepository.class);

		when(accountRepository.findOne(accountId)).thenReturn(testAccount);

		AccountService service = new AccountService();
		service.setAccountRepository(accountRepository);

		// Act
		service.deleteAccount(accountId);

		// Assert
		verify(accountRepository).findOne(accountId);
		verify(accountRepository).delete(testAccount);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testGetAccountsForUser_InvalidUser() {
		// Arrange
		final long userId = 1;

		UserRepository userRepository = mock(UserRepository.class);

		when(userRepository.findOne(userId)).thenReturn(null);

		AccountService service = new AccountService();
		service.setUserRepository(userRepository);

		// Act
		service.getAccountsForUser(userId);
	}

	@Test
	public void testGetAccountsForUser_NoAccounts() {
		// Arrange
		final long userId = 1;

		final User user = new User(userId, "test-user", "test-name", "test-email", "test-pass");

		UserRepository userRepository = mock(UserRepository.class);
		when(userRepository.findOne(userId)).thenReturn(user);

		AccountRepository accountRepository = mock(AccountRepository.class);
		when(accountRepository.findAllByOwner(user)).thenReturn(new ArrayList<Account>());

		AccountService service = new AccountService();
		service.setAccountRepository(accountRepository);
		service.setUserRepository(userRepository);

		// Act
		List<Account> results = service.getAccountsForUser(userId);

		// Assert
		assertEquals(0, results.size());
	}

	@Test
	public void testGetAccountsForUser_Accounts() {
		// Arrange
		final long userId = 1;

		final User user = new User(userId, "test-user", "test-name", "test-email", "test-pass");
		final Account account1 = new Account(1, user, "test-account", null);
		final Account account2 = new Account(2, user, "test-account-2", null);

		UserRepository userRepository = mock(UserRepository.class);
		when(userRepository.findOne(userId)).thenReturn(user);

		AccountRepository accountRepository = mock(AccountRepository.class);
		when(accountRepository.findAllByOwner(user)).thenReturn(Arrays.asList(account1, account2));

		AccountService service = new AccountService();
		service.setAccountRepository(accountRepository);
		service.setUserRepository(userRepository);

		// Act
		List<Account> results = service.getAccountsForUser(userId);

		// Assert
		assertEquals(2, results.size());
		assertEquals(account1, results.get(0));
		assertEquals(account2, results.get(1));
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testGetAccountValueToday_NonExistingAccount() {
		// Arrange
		final long accountId = 1;

		AccountRepository accountRepository = mock(AccountRepository.class);

		when(accountRepository.findOne(accountId)).thenReturn(null);

		AccountService service = new AccountService();
		service.setAccountRepository(accountRepository);

		// Act
		service.getAccountValueToday(accountId);
	}

	@Test
	public void testGetAccountValueToday_NoValuation() {
		// Arrange
		final Account account1 = new Account(1, null, "test-account", null);

		AccountRepository accountRepository = mock(AccountRepository.class);
		when(accountRepository.findOne(account1.getAccountId())).thenReturn(account1);

		AccountValueRepository accountValueRepository = mock(AccountValueRepository.class);
		when(accountValueRepository.findByAccountBeforeDate(any(Account.class), any(LocalDate.class))).thenReturn(new ArrayList<AccountEntry>());

		AccountService service = new AccountService();
		service.setAccountRepository(accountRepository);
		service.setAccountValueRepository(accountValueRepository);

		// Act
		AccountEntry result = service.getAccountValueToday(account1.getAccountId());

		// Assert
		assertNull(result);
	}

	@Test
	public void testGetAccountValueToday_WithValuation() {
		// Arrange
		final LocalDate todaysDate = new LocalDate();
		final Account account1 = new Account(1, null, "test-account", null);

		final AccountEntry entry1 = new AccountEntry(1L, account1, todaysDate, BigDecimal.valueOf(50));
		final AccountEntry entry2 = new AccountEntry(2L, account1, new LocalDate(2012, 1, 1), BigDecimal.valueOf(25));
		final AccountEntry entry3 = new AccountEntry(3L, account1, new LocalDate(2012, 6, 1), BigDecimal.valueOf(10));
		account1.setEntries(new HashSet<AccountEntry>(Arrays.asList(entry1, entry2, entry3)));

		AccountRepository accountRepository = mock(AccountRepository.class);
		when(accountRepository.findOne(account1.getAccountId())).thenReturn(account1);

		AccountValueRepository accountValueRepository = mock(AccountValueRepository.class);
		when(accountValueRepository.findByAccountBeforeDate(account1, todaysDate)).thenReturn(Arrays.asList(entry1, entry2, entry3));

		AccountService service = new AccountService();
		service.setAccountRepository(accountRepository);
		service.setAccountValueRepository(accountValueRepository);

		// Act
		AccountEntry result = service.getAccountValueToday(account1.getAccountId());

		// Assert
		assertEquals(entry1, result);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testGetAccountValueOnDate_NonExistingAccount() {
		// Arrange
		final long accountId = 1;

		AccountRepository accountRepository = mock(AccountRepository.class);

		when(accountRepository.findOne(accountId)).thenReturn(null);

		AccountService service = new AccountService();
		service.setAccountRepository(accountRepository);

		// Act
		service.getAccountValueOnDate(accountId, new LocalDate());
	}

	@Test
	public void testGetAccountValueOnDate_NoValuation() {
		// Arrange
		final Account account1 = new Account(1, null, "test-account", null);

		AccountRepository accountRepository = mock(AccountRepository.class);
		when(accountRepository.findOne(account1.getAccountId())).thenReturn(account1);

		AccountValueRepository accountValueRepository = mock(AccountValueRepository.class);
		when(accountValueRepository.findByAccountBeforeDate(any(Account.class), any(LocalDate.class))).thenReturn(new ArrayList<AccountEntry>());

		AccountService service = new AccountService();
		service.setAccountRepository(accountRepository);
		service.setAccountValueRepository(accountValueRepository);

		// Act
		AccountEntry result = service.getAccountValueOnDate(account1.getAccountId(), new LocalDate());

		// Assert
		assertNull(result);
	}

	@Test
	public void testGetAccountValueOnDate_WithValuation() {
		// Arrange
		final LocalDate todaysDate = new LocalDate();
		final LocalDate dateToTest = new LocalDate(2012, 6, 1);
		final Account account1 = new Account(1, null, "test-account", null);

		final AccountEntry entry1 = new AccountEntry(1L, account1, todaysDate, BigDecimal.valueOf(50));
		final AccountEntry entry2 = new AccountEntry(2L, account1, new LocalDate(2012, 1, 1), BigDecimal.valueOf(25));
		final AccountEntry entry3 = new AccountEntry(3L, account1, dateToTest, BigDecimal.valueOf(10));
		account1.setEntries(new HashSet<AccountEntry>(Arrays.asList(entry1, entry2, entry3)));

		AccountRepository accountRepository = mock(AccountRepository.class);
		when(accountRepository.findOne(account1.getAccountId())).thenReturn(account1);

		AccountValueRepository accountValueRepository = mock(AccountValueRepository.class);
		when(accountValueRepository.findByAccountBeforeDate(account1, dateToTest)).thenReturn(Arrays.asList(entry2, entry3));

		AccountService service = new AccountService();
		service.setAccountRepository(accountRepository);
		service.setAccountValueRepository(accountValueRepository);

		// Act
		AccountEntry result = service.getAccountValueOnDate(account1.getAccountId(), dateToTest);

		// Assert
		assertEquals(entry2, result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAccountValuesForMonth_InvalidMonth() {
		// Arrange
		AccountService accountService = new AccountService();

		// Act
		accountService.getAccountValuesForMonth(0L, (short) 2012, (short) -1);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testGetAccountValuesForMonth_InvalidAccount() {
		// Arrange
		final long accountId = 1;

		AccountRepository accountRepository = mock(AccountRepository.class);

		when(accountRepository.findOne(accountId)).thenReturn(null);

		AccountService service = new AccountService();
		service.setAccountRepository(accountRepository);

		// Act
		service.getAccountValuesForMonth(accountId, (short) 2012, (short) 6);
	}

	@Test
	public void testGetAccountValuesForMonth_NoEntries() {
		// Arrange
		final Account account1 = new Account(1, null, "test-account", null);

		AccountRepository accountRepository = mock(AccountRepository.class);
		when(accountRepository.findOne(account1.getAccountId())).thenReturn(account1);

		AccountValueRepository accountValueRepository = mock(AccountValueRepository.class);
		when(accountValueRepository.findByAccountBetweenTwoDates(any(Account.class), any(LocalDate.class), any(LocalDate.class))).thenReturn(new ArrayList<AccountEntry>());

		AccountService service = new AccountService();
		service.setAccountRepository(accountRepository);
		service.setAccountValueRepository(accountValueRepository);

		// Act
		List<AccountEntry> result = service.getAccountValuesForMonth(account1.getAccountId(), (short) 2012, (short) 1);

		// Assert
		assertEquals(0, result.size());
	}

	@Test
	public void testGetAccountValuesForMonth_Entries() {
		// Arrange
		final LocalDate dateToTest1 = new LocalDate(2012, 5, 31); // Not inc
		final LocalDate dateToTest2 = new LocalDate(2012, 6, 1); // Inc
		final LocalDate dateToTest3 = new LocalDate(2012, 6, 15); // Inc
		final LocalDate dateToTest4 = new LocalDate(2012, 6, 30); // Inc
		final LocalDate dateToTest5 = new LocalDate(2012, 7, 1); // Not inc

		final LocalDate startDate = new LocalDate(2012, 6, 1);
		final LocalDate endDate = new LocalDate(2012, 6, 30);

		final Account account1 = new Account(1, null, "test-account", null);

		final AccountEntry entry1 = new AccountEntry(1L, account1, dateToTest1, BigDecimal.valueOf(50));
		final AccountEntry entry2 = new AccountEntry(2L, account1, dateToTest2, BigDecimal.valueOf(51));
		final AccountEntry entry3 = new AccountEntry(3L, account1, dateToTest3, BigDecimal.valueOf(52));
		final AccountEntry entry4 = new AccountEntry(4L, account1, dateToTest4, BigDecimal.valueOf(53));
		final AccountEntry entry5 = new AccountEntry(5L, account1, dateToTest5, BigDecimal.valueOf(54));
		account1.setEntries(new HashSet<AccountEntry>(Arrays.asList(entry1, entry2, entry3, entry4, entry5)));

		AccountRepository accountRepository = mock(AccountRepository.class);
		when(accountRepository.findOne(account1.getAccountId())).thenReturn(account1);

		AccountValueRepository accountValueRepository = mock(AccountValueRepository.class);
		when(accountValueRepository.findByAccountBetweenTwoDates(account1, startDate, endDate)).thenReturn(Arrays.asList(entry2, entry3, entry4));

		AccountService service = new AccountService();
		service.setAccountRepository(accountRepository);
		service.setAccountValueRepository(accountValueRepository);

		// Act
		List<AccountEntry> result = service.getAccountValuesForMonth(account1.getAccountId(), (short) 2012, (short) 6);

		// Assert
		assertEquals(3, result.size());
		assertEquals(entry2, result.get(0));
		assertEquals(entry3, result.get(1));
		assertEquals(entry4, result.get(2));
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testSetAccountValue_NonExistingAccount() {
		// Arrange
		final long accountId = 1;

		AccountRepository account = mock(AccountRepository.class);
		when(account.findOne(accountId)).thenReturn(null);

		AccountService service = new AccountService();
		service.setAccountRepository(account);

		// Act
		service.setAccountValue(accountId, new LocalDate(), BigDecimal.TEN);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetAccountValue_NoDate() {
		// Arrange
		final long accountId = 1;

		AccountRepository account = mock(AccountRepository.class);
		when(account.findOne(accountId)).thenReturn(new Account(accountId, null, "test-account", null));

		AccountService service = new AccountService();
		service.setAccountRepository(account);

		// Act
		service.setAccountValue(accountId, null, BigDecimal.TEN);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetAccountValue_NoValue() {
		// Arrange
		final long accountId = 1;

		AccountRepository account = mock(AccountRepository.class);
		when(account.findOne(accountId)).thenReturn(new Account(accountId, null, "test-account", null));

		AccountService service = new AccountService();
		service.setAccountRepository(account);

		// Act
		service.setAccountValue(accountId, new LocalDate(), null);
	}

	@Test
	public void testSetAccountValue_ValuationExistsOnDay() {
		// Arrange
		final long accountId = 1;
		final LocalDate dateToSet = new LocalDate();
		final BigDecimal valueToSet = BigDecimal.valueOf(10);
		final Account testAccount = new Account(accountId, null, "test-account", null);
		
		final AccountEntry testAccountValue = new AccountEntry(1L, testAccount, dateToSet, BigDecimal.valueOf(50));
		testAccount.setEntries(new HashSet<AccountEntry>(Arrays.asList(testAccountValue)));

		AccountRepository accountRepository = mock(AccountRepository.class);
		when(accountRepository.findOne(accountId)).thenReturn(testAccount);
		
		AccountValueRepository accountValueRepository = mock(AccountValueRepository.class);
		when(accountValueRepository.findByAccountBetweenTwoDates(testAccount, dateToSet, dateToSet)).thenReturn(Arrays.asList(testAccountValue));

		AccountService service = new AccountService();
		service.setAccountRepository(accountRepository);
		service.setAccountValueRepository(accountValueRepository);

		// Act
		AccountEntry result = service.setAccountValue(accountId, new LocalDate(), valueToSet);
		
		// Assert
		ArgumentCaptor<AccountEntry> argument = ArgumentCaptor.forClass(AccountEntry.class);
		
		verify(accountValueRepository).save(argument.capture());
		AccountEntry resultEntry = argument.getValue();
		assertEquals(valueToSet, resultEntry.getValue());
		assertEquals(dateToSet, resultEntry.getEntryDate());
		
		assertEquals(resultEntry, result);
	}

	@Test
	public void testSetAccountValue_ValuationDoesNotExistOnDay() {
		// Arrange
		final long accountId = 1;
		final LocalDate dateToSet = new LocalDate();
		final BigDecimal valueToSet = BigDecimal.valueOf(10);
		final Account testAccount = new Account(accountId, null, "test-account", null);

		AccountRepository accountRepository = mock(AccountRepository.class);
		when(accountRepository.findOne(accountId)).thenReturn(testAccount);
		
		AccountValueRepository accountValueRepository = mock(AccountValueRepository.class);
		when(accountValueRepository.findByAccountBetweenTwoDates(testAccount, dateToSet, dateToSet)).thenReturn(new ArrayList<AccountEntry>());

		AccountService service = new AccountService();
		service.setAccountRepository(accountRepository);
		service.setAccountValueRepository(accountValueRepository);

		// Act
		AccountEntry result = service.setAccountValue(accountId, new LocalDate(), valueToSet);
		
		// Assert
		ArgumentCaptor<AccountEntry> argument = ArgumentCaptor.forClass(AccountEntry.class);
		
		verify(accountValueRepository).save(argument.capture());
		AccountEntry resultEntry = argument.getValue();
		assertEquals(valueToSet, resultEntry.getValue());
		assertEquals(dateToSet, resultEntry.getEntryDate());
		
		assertEquals(resultEntry, result);
	}
}
