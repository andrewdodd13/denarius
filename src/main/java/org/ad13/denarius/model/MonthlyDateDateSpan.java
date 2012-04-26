package org.ad13.denarius.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.joda.time.LocalDate;

@Entity
@Table(name = "monthly_date_datespan")
public class MonthlyDateDateSpan extends DateSpan {
    @Column(name = "date")
    private byte date;

    public MonthlyDateDateSpan() {
        super();
    }

    public MonthlyDateDateSpan(byte date) {
        this();
        this.date = date;
    }

    @Override
    public boolean isActiveOn(LocalDate date) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isActiveBetween(LocalDate startDate, LocalDate endDate) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Set<LocalDate> activeDatesBetween(LocalDate startDate, LocalDate endDate) {
        // TODO Auto-generated method stub
        return null;
    }

    public byte getDate() {
        return date;
    }

    public void setDate(byte date) {
        this.date = date;
    }
}
