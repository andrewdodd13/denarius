package org.ad13.denarius.service;

import org.ad13.denarius.model.Account;
import org.ad13.denarius.repository.AccountRepository;
import org.ad13.denarius.repository.AccountValueRepository;
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
    
    public Account createAccount(Account account) {
    	return null;
    }
}
