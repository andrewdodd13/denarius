package org.ad13.denarius.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id", nullable = false)
    private long accountId;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(name = "account_name", nullable = false)
    private String accountName;

    @OneToMany
    private Set<AccountEntry> entries;

    public Account() {
        super();
    }

    public Account(long accountId, User owner, String accountName, Set<AccountEntry> entries) {
        super();
        this.accountId = accountId;
        this.owner = owner;
        this.accountName = accountName;
        this.entries = entries;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Set<AccountEntry> getEntries() {
        return entries;
    }

    public void setEntries(Set<AccountEntry> entries) {
        this.entries = entries;
    }
}
