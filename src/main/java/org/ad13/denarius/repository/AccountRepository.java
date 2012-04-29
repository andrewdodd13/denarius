package org.ad13.denarius.repository;

import java.util.List;

import org.ad13.denarius.dto.AccountDTO;
import org.ad13.denarius.model.Account;

public interface AccountRepository {
    public Account getAccount(long accountId);
    
    public void deleteAccount(long accountId);
    
    public long create(AccountDTO accountDto);
    
    public List<Account> getAccountsForUser(long ownerId);
}
