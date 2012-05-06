package org.ad13.denarius.dto;

import java.math.BigDecimal;
import java.util.Map;

import org.joda.time.LocalDate;

public class ValuedAccountDTO extends AccountDTO {
    private Map<LocalDate, BigDecimal> entries;

    public ValuedAccountDTO() {

    }

    public ValuedAccountDTO(long accountId, String accountName, long ownerId, Map<LocalDate, BigDecimal> entries) {
        super(accountId, accountName, ownerId);
        this.entries = entries;
    }

    public Map<LocalDate, BigDecimal> getEntries() {
        return entries;
    }

    public void setEntries(Map<LocalDate, BigDecimal> entries) {
        this.entries = entries;
    }
}
