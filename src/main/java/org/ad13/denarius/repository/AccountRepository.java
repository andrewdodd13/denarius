package org.ad13.denarius.repository;

import java.util.List;

import org.ad13.denarius.dto.AccountDTO;
import org.ad13.denarius.dto.AccountEntryDTO;
import org.ad13.denarius.model.Account;
import org.ad13.denarius.model.AccountEntry;
import org.joda.time.LocalDate;

public interface AccountRepository {
    public Account getAccount(long accountId);

    public void deleteAccount(long accountId);

    public long create(AccountDTO accountDto);

    public void update(AccountDTO accountDto);

    public List<Account> getAccountsForUser(long ownerId);

    public AccountEntry getAccountValue(long accountId);

    public AccountEntry getAccountValue(long accountId, LocalDate date);

    public List<AccountEntry> getAccountValues(long accountId, short year, short month);

    public long setAccountValue(AccountEntryDTO accountEntry);
}
