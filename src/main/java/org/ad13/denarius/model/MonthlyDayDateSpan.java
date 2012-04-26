package org.ad13.denarius.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.joda.time.LocalDate;

@Entity
@Table(name = "monthly_day_datespan")
public class MonthlyDayDateSpan extends DateSpan {
    @Column(name = "day", nullable = false)
    private int day;
    @Column(name = "week", nullable = false)
    private byte week;

    public MonthlyDayDateSpan() {
        super();
    }

    public MonthlyDayDateSpan(int day, byte week) {
        this();
        this.day = day;
        this.week = week;
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

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public byte getWeek() {
        return week;
    }

    public void setWeek(byte week) {
        this.week = week;
    }
}
