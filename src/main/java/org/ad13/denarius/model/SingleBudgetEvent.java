package org.ad13.denarius.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Entity
@Table(name = "single_budget_event")
public class SingleBudgetEvent extends BudgetEvent {
    @Column(name = "date")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate date;

    @OneToOne
    @JoinColumn(name = "valuation_id")
    private Valuation valuation;

    public SingleBudgetEvent() {

    }

    public SingleBudgetEvent(LocalDate date, Valuation valuation) {
        super();
        this.date = date;
        this.valuation = valuation;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Valuation getValuation() {
        return valuation;
    }

    public void setValuation(Valuation valuation) {
        this.valuation = valuation;
    }
}
