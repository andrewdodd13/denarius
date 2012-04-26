package org.ad13.denarius.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.joda.time.LocalDate;

@Entity
@Table(name = "account_entry")
public class AccountEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_entry_id", nullable = false)
    private long accountEntryId;

    @Column(name = "entry_date", nullable = false)
    private LocalDate entryDate;

    @Column(name = "entry_value", nullable = false)
    private BigDecimal value;

    public AccountEntry() {

    }

    public AccountEntry(long accountEntryId, LocalDate entryDate, BigDecimal value) {
        super();
        this.accountEntryId = accountEntryId;
        this.entryDate = entryDate;
        this.value = value;
    }

    public long getAccountEntryId() {
        return accountEntryId;
    }

    public void setAccountEntryId(long accountEntryId) {
        this.accountEntryId = accountEntryId;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
