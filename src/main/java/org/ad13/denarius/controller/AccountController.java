package org.ad13.denarius.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ad13.denarius.dto.AccountDTO;
import org.ad13.denarius.dto.AccountEntryDTO;
import org.ad13.denarius.dto.ValuedAccountDTO;
import org.ad13.denarius.model.Account;
import org.ad13.denarius.model.AccountEntry;
import org.ad13.denarius.model.User;
import org.ad13.denarius.repository.AccountRepository;
import org.ad13.denarius.service.AccountService;
import org.ad13.denarius.service.DenariusUserDetailsService;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles Account REST requests, including basic CRUD and valuations.
 * 
 * @author Andrew Dodd
 */
@Controller
@Transactional
@RequestMapping("/account")
public class AccountController {
	@Autowired
	private DenariusUserDetailsService userDetailsService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountRepository accountRepository;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public long createAccount(@RequestBody AccountDTO account) {
		// If this user isn't an admin, force the Owner ID to the current user
		User user = userDetailsService.currentUser();
		if (!userDetailsService.isAdministrator(user)) {
			account.setOwnerId(user.getUserId());
		}

		Account result = accountService.createAccount(account.getAccountName(), user);
		return result.getAccountId();
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public void updateAccount(@RequestBody AccountDTO account) {
		assertOwnership(account.getAccountId());

		accountService.updateAccount(account.getAccountId(), account.getAccountName());
	}

	@RequestMapping(value = "/delete/{accountId}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteAccount(@PathVariable Long accountId) {
		assertOwnership(accountId);

		accountService.deleteAccount(accountId);
	}

	@RequestMapping(value = "/get/{accountId}", method = RequestMethod.GET)
	@ResponseBody
	public AccountDTO getAccount(@PathVariable Long accountId) {
		assertOwnership(accountId);

		Account account = accountService.getAccount(accountId);

		return constructDTOFromAccount(account);
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public List<AccountDTO> getAccounts() {
		List<Account> accounts = accountService.getAccountsForUser(userDetailsService.currentUser().getUserId());

		List<AccountDTO> results = new ArrayList<AccountDTO>();
		for (Account account : accounts) {
			results.add(constructDTOFromAccount(account));
		}

		return results;
	}

	@RequestMapping(value = "/list-values/{year}/{month}", method = RequestMethod.GET)
	@ResponseBody
	public List<ValuedAccountDTO> getAccountsWithValues(@PathVariable Short year, @PathVariable Short month) {
		List<AccountDTO> accounts = getAccounts();

		List<ValuedAccountDTO> valuedAccounts = new ArrayList<ValuedAccountDTO>();
		for (AccountDTO account : accounts) {
			Map<LocalDate, BigDecimal> entriesResult = new HashMap<LocalDate, BigDecimal>();
			List<AccountEntry> entries = accountService.getAccountValuesForMonth(account.getAccountId(), year, month);
			for (AccountEntry entry : entries) {
				entriesResult.put(entry.getEntryDate(), entry.getValue());
			}

			valuedAccounts.add(new ValuedAccountDTO(account.getAccountId(), account.getAccountName(), account.getOwnerId(), entriesResult));
		}

		return valuedAccounts;
	}

	@RequestMapping(value = "/value/{accountId}", method = RequestMethod.GET)
	@ResponseBody
	public AccountEntryDTO getAccountValueToday(@PathVariable Long accountId) {
		assertOwnership(accountId);

		AccountEntry entry = accountService.getAccountValueToday(accountId);
		if (entry == null) {
			return null;
		}
		return constructDTOFromAccountEntry(entry);
	}

	@RequestMapping(value = "/value/{accountId}/{date}", method = RequestMethod.GET)
	@ResponseBody
	public AccountEntryDTO getAccountValue(@PathVariable Long accountId, @PathVariable String date) {
		assertOwnership(accountId);

		LocalDate jodaDate = LocalDate.parse(date);
		AccountEntry entry = accountService.getAccountValueOnDate(accountId, jodaDate);
		if (entry == null) {
			return null;
		}
		return constructDTOFromAccountEntry(entry);
	}

	@RequestMapping(value = "/value", method = RequestMethod.POST)
	@ResponseBody
	public AccountEntryDTO setAccountValue(@RequestBody AccountEntryDTO accountEntry) {
		assertOwnership(accountEntry.getAccountEntryId());

		AccountEntry result = accountService.setAccountValue(accountEntry.getAccountId(), accountEntry.getDate(), accountEntry.getValue());
		
		return constructDTOFromAccountEntry(result);
	}

	private void assertOwnership(long accountId) throws ResourceNotFoundException {
		Account account = accountRepository.findOne(accountId);
		if (account == null) {
			throw new ResourceNotFoundException();
		}

		User user = userDetailsService.currentUser();
		if (userDetailsService.isAdministrator(user)) {
			return;
		}

		if (!userDetailsService.isAdministrator(user) && user.getUserId() != account.getOwner().getUserId()) {
			throw new ResourceNotFoundException();
		}
	}

	private AccountDTO constructDTOFromAccount(Account account) {
		return new AccountDTO(account.getAccountId(), account.getAccountName(), account.getOwner().getUserId());
	}

	private AccountEntryDTO constructDTOFromAccountEntry(AccountEntry accountEntry) {
		return new AccountEntryDTO(accountEntry.getAccountEntryId(), accountEntry.getAccount().getAccountId(), accountEntry.getEntryDate(), accountEntry.getValue());
	}
}
