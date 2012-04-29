package org.ad13.denarius.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class AccountDTO {
    private long accountId;
    private long ownerId;
    
    @NotEmpty
    private String accountName;

    public AccountDTO() {
        super();
    }

    public AccountDTO(long accountId, String accountName, long ownerId) {
        super();
        this.accountId = accountId;
        this.accountName = accountName;
        this.ownerId = ownerId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }
}
