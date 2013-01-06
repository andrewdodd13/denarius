package org.ad13.denarius.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.util.HashSet;

import org.ad13.denarius.controller.ResourceNotFoundException;
import org.ad13.denarius.model.Account;
import org.ad13.denarius.model.AccountEntry;
import org.ad13.denarius.model.User;
import org.ad13.denarius.repository.AccountRepository;
import org.junit.Test;

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
}
