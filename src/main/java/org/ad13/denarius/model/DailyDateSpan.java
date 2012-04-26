package org.ad13.denarius.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.joda.time.Days;
import org.joda.time.LocalDate;

@Entity
@Table(name = "daily_datespan")
public class DailyDateSpan extends DateSpan {
    public DailyDateSpan() {
        super();
    }

    @Override
    public boolean isActiveOn(LocalDate date) {
        // Check inside bounds, then check interval
        return date.compareTo(getStartDate()) >= 0 && date.compareTo(getEndDate()) <= 0
                && Days.daysBetween(getStartDate(), date).getDays() % getInterval() == 0;
    }

    @Override
    public boolean isActiveBetween(LocalDate startDate, LocalDate endDate) {
        if ((startDate.compareTo(getStartDate()) <= 0 && endDate.compareTo(getStartDate()) >= 0)
                || (startDate.compareTo(getStartDate()) >= 0 && endDate.compareTo(getEndDate()) <= 0)
                || (startDate.compareTo(getEndDate()) <= 0 && endDate.compareTo(getEndDate()) >= 0)) {

            // Do interval checking
            LocalDate intervalStart = (startDate.compareTo(getStartDate()) >= 0 ? startDate : getStartDate()), intervalEnd = (endDate
                    .compareTo(getEndDate()) <= 0 ? endDate : getEndDate());

            do {
                if (Days.daysBetween(getStartDate(), intervalStart).getDays() % getInterval() == 0) {
                    return true;
                }

                intervalStart = intervalStart.plusDays(1);
            } while (!intervalStart.equals(intervalEnd));

            // Check the end date
            if (Days.daysBetween(getStartDate(), intervalEnd).getDays() % getInterval() == 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Set<LocalDate> activeDatesBetween(LocalDate startDate, LocalDate endDate) {
        Set<LocalDate> result = new HashSet<LocalDate>();

        if (isActiveBetween(startDate, endDate)) {
            LocalDate intervalStart = (startDate.compareTo(getStartDate()) >= 0 ? startDate : getStartDate()), intervalEnd = (endDate
                    .compareTo(getEndDate()) <= 0 ? endDate : getEndDate());

            int days = Days.daysBetween(intervalStart, intervalEnd).getDays();

            for (int i = Days.daysBetween(getStartDate(), intervalStart).getDays() % getInterval(); i <= days; i += getInterval()) {
                result.add(intervalStart.plusDays(i));
            }
        }

        return result;
    }

}
