package org.ad13.denarius.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ad13.denarius.model.Account;
import org.ad13.denarius.repository.AccountDao;
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
 * Handles AJAX requests.
 * 
 * @author Andrew Dodd
 */
@Controller
@Transactional
@RequestMapping("/account")
public class AjaxResponseController {
    private static final Logger logger = LoggerFactory.getLogger(AjaxResponseController.class);

    @Autowired
    private AccountDao accountsRepository;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, ? extends Object> create(@RequestBody Account account) {
        return null;
    }

    @RequestMapping(value = "/delete/{accountId}", method = RequestMethod.DELETE)
    public String deleteAccount(@PathVariable String accountId) {
        long parsedAccountId = parseId(accountId);
        accountsRepository.deleteAccount(parsedAccountId);
        return "redirect:/";
    }

    @RequestMapping(value = "/get/{accountId}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getAccount(@PathVariable String accountId) {
        // Try to parse the account id
        long parsedAccountId = parseId(accountId);
        Account account = accountsRepository.getAccount(parsedAccountId);
        if (account == null) {
            throw new ResourceNotFoundException();
        }
        
        return account.serializeForWeb();
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody
    List<Map<String, Object>> getAccounts() {
        List<Account> accounts = accountsRepository.getAccountsForUser(1);

        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        for (Account account : accounts) {
            results.add(account.serializeForWeb());
        }

        return results;
    }

    private long parseId(String id) {
        try {
            return Long.valueOf(id);
        } catch (NumberFormatException e) {
            throw new ResourceNotFoundException();
        }
    }

}
