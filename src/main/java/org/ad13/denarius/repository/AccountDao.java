package org.ad13.denarius.repository;

import java.util.List;

import org.ad13.denarius.model.Account;

public interface AccountDao {
    public Account getAccount(long accountId);
    
    public void deleteAccount(long accountId);
    
    public List<Account> getAccountsForUser(long ownerId);
}
