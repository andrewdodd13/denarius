package org.ad13.denarius.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.joda.time.LocalDate;

@Entity
@Table(name = "account_entry", uniqueConstraints = @UniqueConstraint(columnNames = { "account_id", "entry_date" }))
public class AccountEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_entry_id", nullable = false)
    private Long accountEntryId;

    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "entry_date", nullable = false)
    private LocalDate entryDate;

    @Column(name = "entry_value", nullable = false)
    private BigDecimal value;

    public AccountEntry() {

    }

    public AccountEntry(Long accountEntryId, Account account, LocalDate entryDate, BigDecimal value) {
        super();
        this.accountEntryId = accountEntryId;
        this.account = account;
        this.entryDate = entryDate;
        this.value = value;
    }

    public Long getAccountEntryId() {
        return accountEntryId;
    }

    public void setAccountEntryId(Long accountEntryId) {
        this.accountEntryId = accountEntryId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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
