package org.ad13.denarius.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "budget_event")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BudgetEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "budget_event_id")
    private long budgetEventId;

    @Column(name = "description")
    private String description;

    @Column(name = "category")
    private String category;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    public BudgetEvent() {

    }

    public BudgetEvent(long budgetEventId, String description, String category, User owner) {
        super();
        this.budgetEventId = budgetEventId;
        this.description = description;
        this.category = category;
        this.owner = owner;
    }

    // Abstract methods

    public long getBudgetEventId() {
        return budgetEventId;
    }

    public void setBudgetEventId(long budgetEventId) {
        this.budgetEventId = budgetEventId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
