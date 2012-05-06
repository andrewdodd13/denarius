package org.ad13.denarius.dto;

import java.util.List;

public class ValuedAccountDTO extends AccountDTO {
    private List<AccountEntryDTO> entries;

    public ValuedAccountDTO() {

    }

    public ValuedAccountDTO(long accountId, String accountName, long ownerId, List<AccountEntryDTO> entries) {
        super(accountId, accountName, ownerId);
        this.entries = entries;
    }

    public List<AccountEntryDTO> getEntries() {
        return entries;
    }

    public void setEntries(List<AccountEntryDTO> entries) {
        this.entries = entries;
    }

}
