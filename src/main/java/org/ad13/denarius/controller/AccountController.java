package org.ad13.denarius.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.ad13.denarius.dto.AccountDTO;
import org.ad13.denarius.model.Account;
import org.ad13.denarius.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles AJAX requests.
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
    public long create(@RequestBody AccountDTO account) {
        return accountsRepository.create(account);
    }

    @RequestMapping(value = "/delete/{accountId}", method = RequestMethod.DELETE)
    public String deleteAccount(@PathVariable Long accountId) {
        accountsRepository.deleteAccount(accountId);
        return "redirect:/";
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
    public @ResponseBody
    List<AccountDTO> getAccounts() {
        List<Account> accounts = accountsRepository.getAccountsForUser(1);

        List<AccountDTO> results = new ArrayList<AccountDTO>();
        for (Account account : accounts) {
            results.add(constructDTOFromAccount(account));
        }

        return results;
    }

    private AccountDTO constructDTOFromAccount(Account account) {
        return new AccountDTO(account.getAccountId(), account.getAccountName(), account.getOwner().getUserId());
    }
}
