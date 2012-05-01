package org.ad13.denarius.dto;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

public class AccountEntryDTO {
    private long accountEntryId;
    private long accountId;
    private LocalDate date;
    private BigDecimal value;

    public AccountEntryDTO() {
        super();
    }

    public AccountEntryDTO(long accountEntryId, long accountId, LocalDate date, BigDecimal value) {
        super();
        this.accountEntryId = accountEntryId;
        this.accountId = accountId;
        this.date = date;
        this.value = value;
    }

    public long getAccountEntryId() {
        return accountEntryId;
    }

    public void setAccountEntryId(long accountEntryId) {
        this.accountEntryId = accountEntryId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
