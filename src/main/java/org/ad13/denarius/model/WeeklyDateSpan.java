package org.ad13.denarius.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.joda.time.DateTimeConstants;
import org.joda.time.Days;
import org.joda.time.LocalDate;

@Entity
@Table(name = "weekly_datespan")
public class WeeklyDateSpan extends DateSpan {
    // TODO: We could use an EnumSet + Enum here, but I'm not keen on the way
    // Hibernate persists that.
    public static final int DAY_MONDAY = 1 << DateTimeConstants.MONDAY, DAY_TUESDAY = 1 << DateTimeConstants.TUESDAY,
            DAY_WEDNESDAY = 1 << DateTimeConstants.WEDNESDAY, DAY_THURSDAY = 1 << DateTimeConstants.THURSDAY,
            DAY_FRIDAY = 1 << DateTimeConstants.FRIDAY, DAY_SATURDAY = 1 << DateTimeConstants.SATURDAY,
            DAY_SUNDAY = 1 << DateTimeConstants.SUNDAY;

    @Column(name = "days")
    private byte activeDays;

    public WeeklyDateSpan() {
        super();
    }

    public WeeklyDateSpan(byte activeDays) {
        this();
        this.activeDays = activeDays;
    }

    @Override
    public boolean isActiveOn(LocalDate date) {
        // First test the date is within the boundaries, then that the date
        // matches
        return (activeDays > 0 && (date.compareTo(getStartDate()) >= 0 && date.compareTo(getEndDate()) <= 0) && isDayInMask(date
                .getDayOfWeek()));
    }

    @Override
    public boolean isActiveBetween(LocalDate startDate, LocalDate endDate) {
        if (activeDays == 0)
            return false;

        if ((startDate.compareTo(getStartDate()) <= 0 && endDate.compareTo(getStartDate()) >= 0)
                || (startDate.compareTo(getStartDate()) >= 0 && endDate.compareTo(getEndDate()) <= 0)
                || (startDate.compareTo(getEndDate()) <= 0 && endDate.compareTo(getEndDate()) >= 0)) {

            LocalDate intervalStart = (startDate.compareTo(getStartDate()) >= 0 ? startDate : getStartDate()), intervalEnd = (endDate
                    .compareTo(getEndDate()) <= 0 ? endDate : getEndDate());

            int days = Days.daysBetween(intervalStart, intervalEnd).getDays();

            for (int i = 0; i <= days; i++) {
                if (isDayInMask(intervalStart.plusDays(i).getDayOfWeek())) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public Set<LocalDate> activeDatesBetween(LocalDate startDate, LocalDate endDate) {
        Set<LocalDate> result = new HashSet<LocalDate>();

        if ((startDate.compareTo(getStartDate()) <= 0 && endDate.compareTo(getStartDate()) >= 0)
                || (startDate.compareTo(getStartDate()) >= 0 && endDate.compareTo(getEndDate()) <= 0)
                || (startDate.compareTo(getEndDate()) <= 0 && endDate.compareTo(getEndDate()) >= 0)) {

            LocalDate intervalStart = (startDate.compareTo(getStartDate()) >= 0 ? startDate : getStartDate()), intervalEnd = (endDate
                    .compareTo(getEndDate()) <= 0 ? endDate : getEndDate());

            int days = Days.daysBetween(intervalStart, intervalEnd).getDays();

            for (int i = 0; i <= days; i++) {
                LocalDate postDate = intervalStart.plusDays(i);
                if (isDayInMask(postDate.getDayOfWeek())) {
                    result.add(postDate);
                }
            }
        }

        return result;
    }

    /**
     * Returns true if the given day is set in the bitmask of days for this
     * {@link WeeklyDateSpan}.
     * 
     * @param day
     *            the int assigned to the day, see {@link DateTimeConstants} for
     *            constants.
     * @return
     */
    private boolean isDayInMask(int day) {
        return (1 << (day - 1) & activeDays) > 0;
    }

    public byte getActiveDays() {
        return activeDays;
    }

    /**
     * Set the bitmask representing the active days in the week.
     * 
     * @param activeDays
     *            the active days bitmask, a seven-bit mask for which the fields
     *            are defined as constants in this class.
     */
    public void setActiveDays(byte activeDays) {
        this.activeDays = activeDays;
    }
}
