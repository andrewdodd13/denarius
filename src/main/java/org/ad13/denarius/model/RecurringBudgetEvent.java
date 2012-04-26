package org.ad13.denarius.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "recurring_budget_event")
public class RecurringBudgetEvent extends BudgetEvent {
    @OneToOne
    @JoinColumn(name = "datespan_id")
    private DateSpan dateSpan;

    @OneToOne
    @JoinColumn(name = "default_valuation_id")
    private Valuation defaultValuation;

    @OneToMany
    private Set<Valuation> valuationOverrides;

    public RecurringBudgetEvent() {

    }

    public RecurringBudgetEvent(DateSpan dateSpan, Valuation defaultValuation, Set<Valuation> valuationOverrides) {
        super();
        this.dateSpan = dateSpan;
        this.defaultValuation = defaultValuation;
        this.valuationOverrides = valuationOverrides;
    }

    public DateSpan getDateSpan() {
        return dateSpan;
    }

    public void setDateSpan(DateSpan dateSpan) {
        this.dateSpan = dateSpan;
    }

    public Valuation getDefaultValuation() {
        return defaultValuation;
    }

    public void setDefaultValuation(Valuation defaultValuation) {
        this.defaultValuation = defaultValuation;
    }

    public Set<Valuation> getValuationOverrides() {
        return valuationOverrides;
    }

    public void setValuationOverrides(Set<Valuation> valuationOverrides) {
        this.valuationOverrides = valuationOverrides;
    }
}
