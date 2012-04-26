package org.ad13.denarius.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * A Valuation is the amount and account of a monetary transfer.
 * 
 * @author Andrew Dodd
 */
@Entity
@Table(name = "valuation")
public class Valuation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "valuation_id", nullable = false)
    private long valuationId;

    @Column(name = "value", nullable = false)
    private BigDecimal value;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    public Valuation() {
        super();
    }

    public Valuation(long valuationId, BigDecimal value, Account account) {
        super();
        this.valuationId = valuationId;
        this.value = value;
        this.account = account;
    }

    public long getValuationId() {
        return valuationId;
    }

    public void setValuationId(long valuationId) {
        this.valuationId = valuationId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
