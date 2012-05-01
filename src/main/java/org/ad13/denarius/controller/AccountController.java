package org.ad13.denarius.controller;

import java.util.ArrayList;
import java.util.List;

import org.ad13.denarius.dto.AccountDTO;
import org.ad13.denarius.dto.AccountEntryDTO;
import org.ad13.denarius.model.Account;
import org.ad13.denarius.model.AccountEntry;
import org.ad13.denarius.repository.AccountRepository;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountRepository accountsRepository;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public long createAccount(@RequestBody AccountDTO account) {
        if (true) { // User isn't admin
            account.setOwnerId(1);
        }
        return accountsRepository.create(account);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String updateAccount(@RequestBody AccountDTO account) {
        if (true) { // User isn't admin
            account.setOwnerId(1);
        }
        accountsRepository.update(account);
        return "OK";
    }

    @RequestMapping(value = "/delete/{accountId}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteAccount(@PathVariable Long accountId) {
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

        return constructDTOFromAccount(account);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<AccountDTO> getAccounts() {
        List<Account> accounts = accountsRepository.getAccountsForUser(1);

        List<AccountDTO> results = new ArrayList<AccountDTO>();
        for (Account account : accounts) {
            results.add(constructDTOFromAccount(account));
        }

        return results;
    }

    @RequestMapping(value = "/value/{accountId}", method = RequestMethod.GET)
    @ResponseBody
    public AccountEntryDTO getAccountValueToday(@PathVariable Long accountId) {
        return constructDTOFromAccountEntry(accountsRepository.getAccountValue(accountId));
    }

    @RequestMapping(value = "/value/{accountId}/{date}", method = RequestMethod.GET)
    @ResponseBody
    public AccountEntryDTO getAccountValue(@PathVariable Long accountId, @PathVariable String date) {
        LocalDate jodaDate = LocalDate.parse(date);
        return constructDTOFromAccountEntry(accountsRepository.getAccountValue(accountId, jodaDate));
    }

    @RequestMapping(value = "/value", method = RequestMethod.POST)
    @ResponseBody
    public long setAccountValue(@RequestBody AccountEntryDTO accountEntry) {
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
