package org.ad13.denarius.controller;

import java.util.ArrayList;
import java.util.List;

import org.ad13.denarius.dto.AccountDTO;
import org.ad13.denarius.dto.AccountEntryDTO;
import org.ad13.denarius.model.Account;
import org.ad13.denarius.model.AccountEntry;
import org.ad13.denarius.model.User;
import org.ad13.denarius.repository.AccountRepository;
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
    private AccountRepository accountsRepository;

    @Autowired
    private DenariusUserDetailsService userDetailsService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public long createAccount(@RequestBody AccountDTO account) {
        // If this user isn't an admin, force the Owner ID to the current user
        User user = userDetailsService.currentUser();
        if (!userDetailsService.isAdministrator(user)) {
            account.setOwnerId(user.getUserId());
        }

        return accountsRepository.create(account);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String updateAccount(@RequestBody AccountDTO account) {
        // Retrieve the existing record
        Account targetAccount = accountsRepository.getAccount(account.getAccountId());
        if (targetAccount == null) {
            throw new ResourceNotFoundException();
        }

        // If the user isn't an admin, do some security checks
        User user = userDetailsService.currentUser();
        if (!userDetailsService.isAdministrator(user)) {
            // If the user doesn't own the account, pretend it doesn't exist
            if (user.getUserId() != targetAccount.getOwner().getUserId()) {
                throw new ResourceNotFoundException();
            } else {
                // Override the DTO's owner ID with the current user's
                account.setOwnerId(targetAccount.getOwner().getUserId());
            }
        }

        accountsRepository.update(account);
        return "OK";
    }

    @RequestMapping(value = "/delete/{accountId}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteAccount(@PathVariable Long accountId) {
        // Retrieve the existing record
        Account targetAccount = accountsRepository.getAccount(accountId);
        if (targetAccount == null) {
            throw new ResourceNotFoundException();
        }

        // If the user isn't an admin, do some security checks
        User user = userDetailsService.currentUser();
        if (!userDetailsService.isAdministrator(user)) {
            // If the user doesn't own the account, pretend it doesn't exist
            if (user.getUserId() != targetAccount.getOwner().getUserId()) {
                throw new ResourceNotFoundException();
            }
        }

        accountsRepository.deleteAccount(accountId);
        return "OK";
    }

    @RequestMapping(value = "/get/{accountId}", method = RequestMethod.GET)
    @ResponseBody
    public AccountDTO getAccount(@PathVariable Long accountId) {
        Account account = accountsRepository.getAccount(accountId);
        if (account == null) {
            throw new ResourceNotFoundException();
        }

        // Check ownership
        User user = userDetailsService.currentUser();
        if (!userDetailsService.isAdministrator(user) && user.getUserId() != account.getOwner().getUserId()) {
            throw new ResourceNotFoundException();
        }

        return constructDTOFromAccount(account);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<AccountDTO> getAccounts() {
        List<Account> accounts = accountsRepository.getAccountsForUser(userDetailsService.currentUser().getUserId());

        List<AccountDTO> results = new ArrayList<AccountDTO>();
        for (Account account : accounts) {
            results.add(constructDTOFromAccount(account));
        }

        return results;
    }

    @RequestMapping(value = "/value/{accountId}", method = RequestMethod.GET)
    @ResponseBody
    public AccountEntryDTO getAccountValueToday(@PathVariable Long accountId) {
        Account account = accountsRepository.getAccount(accountId);
        if (account == null) {
            throw new ResourceNotFoundException();
        }

        // Check ownership
        User user = userDetailsService.currentUser();
        if (!userDetailsService.isAdministrator(user) && user.getUserId() != account.getOwner().getUserId()) {
            throw new ResourceNotFoundException();
        }

        AccountEntry entry = accountsRepository.getAccountValue(accountId);
        if(entry == null) {
            return null;
        }
        return constructDTOFromAccountEntry(entry);
    }

    @RequestMapping(value = "/value/{accountId}/{date}", method = RequestMethod.GET)
    @ResponseBody
    public AccountEntryDTO getAccountValue(@PathVariable Long accountId, @PathVariable String date) {
        Account account = accountsRepository.getAccount(accountId);
        if (account == null) {
            throw new ResourceNotFoundException();
        }

        // Check ownership
        User user = userDetailsService.currentUser();
        if (!userDetailsService.isAdministrator(user) && user.getUserId() != account.getOwner().getUserId()) {
            throw new ResourceNotFoundException();
        }

        LocalDate jodaDate = LocalDate.parse(date);
        AccountEntry entry = accountsRepository.getAccountValue(accountId, jodaDate);
        if(entry == null) {
            return null;
        }
        return constructDTOFromAccountEntry(entry);
    }

    @RequestMapping(value = "/value", method = RequestMethod.POST)
    @ResponseBody
    public long setAccountValue(@RequestBody AccountEntryDTO accountEntry) {
        Account account = accountsRepository.getAccount(accountEntry.getAccountId());
        if (account == null) {
            throw new ResourceNotFoundException();
        }

        // Check ownership
        User user = userDetailsService.currentUser();
        if (!userDetailsService.isAdministrator(user) && user.getUserId() != account.getOwner().getUserId()) {
            throw new ResourceNotFoundException();
        }

        return accountsRepository.setAccountValue(accountEntry);
    }

    private AccountDTO constructDTOFromAccount(Account account) {
        return new AccountDTO(account.getAccountId(), account.getAccountName(), account.getOwner().getUserId());
    }

    private AccountEntryDTO constructDTOFromAccountEntry(AccountEntry accountEntry) {
        return new AccountEntryDTO(accountEntry.getAccountEntryId(), accountEntry.getAccount().getAccountId(), accountEntry.getEntryDate(),
                accountEntry.getValue());
    }
}
