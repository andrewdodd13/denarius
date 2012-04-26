package org.ad13.denarius.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Entity
@Table(name = "datespan")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class DateSpan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "datespan_id", nullable = false)
    private long datespanId;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "occurrence_interval", nullable = false)
    private int interval = 1;

    public DateSpan() {
    }

    public DateSpan(long datespanId, LocalDate startDate, LocalDate endDate, int interval) {
        super();
        this.datespanId = datespanId;
        this.startDate = startDate;
        this.endDate = endDate;
        setInterval(interval);
    }

    /**
     * Returns true if this Date Span object is active on the given date.
     * 
     * @param date
     * @return
     */
    public abstract boolean isActiveOn(LocalDate date);

    /**
     * Returns true if this Date Span object is active between the two given
     * dates. Both dates are inclusive, e.g. if start date is 23/04/12 and end
     * date is 25/04/12 then this function should return true if the Date Span
     * is active on the 23rd, 24th and 25th.
     * <p />
     * It is possible to just call activeDatesBetween(...).empty() but this
     * function should try and perform slightly better than that.
     * 
     * @param startDate
     * @param endDate
     * @return
     */
    public abstract boolean isActiveBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Returns a Set containing all the dates on which a DateSpan occurs between
     * two dates. Start and End date are inclusive, as in
     * {@link DateSpan#isActiveBetween(Date, Date)}
     * 
     * @param startDate
     * @param endDate
     * @return
     */
    public abstract Set<LocalDate> activeDatesBetween(LocalDate startDate, LocalDate endDate);

    public long getDatespanId() {
        return datespanId;
    }

    public void setDatespanId(long datespanId) {
        this.datespanId = datespanId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        // If the interval is set to zero or less than zero, set it to 1 to
        // prevent issues.
        if (interval <= 0) {
            interval = 1;
        }
        this.interval = interval;
    }
}
