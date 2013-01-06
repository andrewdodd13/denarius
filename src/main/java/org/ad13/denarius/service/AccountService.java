package org.ad13.denarius.service;

import java.math.BigDecimal;
import java.util.List;

import org.ad13.denarius.controller.ResourceNotFoundException;
import org.ad13.denarius.model.Account;
import org.ad13.denarius.model.AccountEntry;
import org.ad13.denarius.model.User;
import org.ad13.denarius.repository.AccountRepository;
import org.ad13.denarius.repository.AccountValueRepository;
import org.apache.commons.lang.Validate;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountService {
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AccountValueRepository accountValueRepository;

	/**
	 * Returns the Account object for the given ID. If the Account object is
	 * null, then a {@link ResourceNotFoundException} is thrown.
	 * 
	 * @param accountId
	 * @return
	 */
	public Account getAccount(long accountId) {
		Account account = accountRepository.findOne(accountId);
		if (account == null) {
			throw new ResourceNotFoundException();
		}

		return account;
	}

	/**
	 * Create a new account called accountName owned by Owner.
	 * 
	 * @param accountName
	 * @param owner
	 * @return the persisted Account object that is created
	 */
	public Account createAccount(String accountName, User owner) {
		// Fundamental Validation
		Validate.notNull(accountName);
		Validate.notNull(owner);

		// Create account object
		Account account = new Account();
		account.setAccountName(accountName);
		account.setOwner(owner);

		// Persist it
		return accountRepository.save(account);
	}

	/**
	 * Update the details of an existing account.
	 * 
	 * @param accountId
	 *            the ID of the account to update
	 * @param accountName
	 * @return the persisted Account object that is updated
	 */
	public Account updateAccount(long accountId, String accountName) {
		// Fundamental Validation
		Validate.notNull(accountName);

		// Fetch the old account object
		Account account = accountRepository.findOne(accountId);

		// Check it's not null
		if (account == null) {
			throw new ResourceNotFoundException();
		}

		// Update the details
		account.setAccountName(accountName);

		// Persist
		return accountRepository.save(account);
	}

	/**
	 * Delete an account.
	 * 
	 * @param accountId
	 *            the ID of the account to delete
	 */
	public void deleteAccount(long accountId) {
		// Fetch the old account object
		Account account = accountRepository.findOne(accountId);

		// Check it's not null
		if (account == null) {
			throw new ResourceNotFoundException();
		}

		// Delete the account
		accountRepository.delete(account);
	}

	/**
	 * Returns a list of all accounts for the given user.
	 * 
	 * @param username
	 * @return
	 */
	public List<Account> getAccountsForUser(long accountId) {

	}

	/**
	 * Returns the value of an account today. If the account has never been
	 * valued, then null is returned.
	 * 
	 * @param accountId
	 * @return
	 */
	public AccountEntry getAccountValueToday(long accountId) {
		return getAccountValueOnDate(new LocalDate());
	}

	/**
	 * Returns the value of an account on a given date. If the account has never
	 * been valued, then null is returned.
	 * 
	 * @param date
	 * @return
	 */
	public AccountEntry getAccountValueOnDate(long accountId, LocalDate date) {

	}

	/**
	 * Returns a list of all the account entries for a given account in one
	 * month of the year.
	 * 
	 * @param accountId
	 * @param year
	 *            the year to get the entries for
	 * @param month
	 *            the month to get the entries for
	 * @return
	 */
	public List<AccountEntry> getAccountValuesForMonth(long accountId, short year, short month) {

	}
	
	public AccountEntry setAccountValue(long accountId, LocalDate date, BigDecimal value) {
		
	}

	public AccountRepository getAccountRepository() {
		return accountRepository;
	}

	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public AccountValueRepository getAccountValueRepository() {
		return accountValueRepository;
	}

	public void setAccountValueRepository(AccountValueRepository accountValueRepository) {
		this.accountValueRepository = accountValueRepository;
	}

}
