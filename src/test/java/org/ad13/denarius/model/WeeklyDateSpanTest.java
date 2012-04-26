package org.ad13.denarius.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.joda.time.LocalDate;
import org.junit.Test;

public class WeeklyDateSpanTest {

	@Test
	public void test_isActiveOnDate() {
		WeeklyDateSpan wds = new WeeklyDateSpan();
		// This sets Tuesday, Thursday and Friday to be active
		wds.setActiveDays((byte) 0x1A);

		// Single-day date-range
		// Not active day
		wds.setStartDate(new LocalDate(2012, 4, 23));
		wds.setEndDate(new LocalDate(2012, 4, 23));

		assertFalse(wds.isActiveOn(new LocalDate(2012, 4, 22)));
		assertFalse(wds.isActiveOn(new LocalDate(2012, 4, 23)));
		assertFalse(wds.isActiveOn(new LocalDate(2012, 4, 24)));

		// Active day
		wds.setStartDate(new LocalDate(2012, 4, 24));
		wds.setEndDate(new LocalDate(2012, 4, 24));

		assertFalse(wds.isActiveOn(new LocalDate(2012, 4, 23)));
		assertTrue(wds.isActiveOn(new LocalDate(2012, 4, 24)));
		assertFalse(wds.isActiveOn(new LocalDate(2012, 4, 25)));

		// Single-week date-range
		wds.setStartDate(new LocalDate(2012, 4, 23));
		wds.setEndDate(new LocalDate(2012, 4, 29));

		assertFalse(wds.isActiveOn(new LocalDate(2012, 4, 20))); // Friday
		assertFalse(wds.isActiveOn(new LocalDate(2012, 4, 23)));
		assertTrue(wds.isActiveOn(new LocalDate(2012, 4, 24)));
		assertFalse(wds.isActiveOn(new LocalDate(2012, 4, 25)));
		assertTrue(wds.isActiveOn(new LocalDate(2012, 4, 26)));
		assertTrue(wds.isActiveOn(new LocalDate(2012, 4, 27)));
		assertFalse(wds.isActiveOn(new LocalDate(2012, 4, 28)));
		assertFalse(wds.isActiveOn(new LocalDate(2012, 4, 29)));
		assertFalse(wds.isActiveOn(new LocalDate(2012, 5, 1))); // Tuesday

		// Multi-week date-range
		wds.setStartDate(new LocalDate(2012, 4, 16));
		wds.setEndDate(new LocalDate(2012, 4, 29));

		assertFalse(wds.isActiveOn(new LocalDate(2012, 4, 13))); // Friday
		assertFalse(wds.isActiveOn(new LocalDate(2012, 4, 16)));
		assertTrue(wds.isActiveOn(new LocalDate(2012, 4, 17)));
		assertFalse(wds.isActiveOn(new LocalDate(2012, 4, 18)));
		assertTrue(wds.isActiveOn(new LocalDate(2012, 4, 19)));
		assertTrue(wds.isActiveOn(new LocalDate(2012, 4, 20)));
		assertFalse(wds.isActiveOn(new LocalDate(2012, 4, 21)));
		assertFalse(wds.isActiveOn(new LocalDate(2012, 4, 22)));
		assertFalse(wds.isActiveOn(new LocalDate(2012, 4, 23)));
		assertTrue(wds.isActiveOn(new LocalDate(2012, 4, 24)));
		assertFalse(wds.isActiveOn(new LocalDate(2012, 4, 25)));
		assertTrue(wds.isActiveOn(new LocalDate(2012, 4, 26)));
		assertTrue(wds.isActiveOn(new LocalDate(2012, 4, 27)));
		assertFalse(wds.isActiveOn(new LocalDate(2012, 4, 28)));
		assertFalse(wds.isActiveOn(new LocalDate(2012, 4, 29)));
		assertFalse(wds.isActiveOn(new LocalDate(2012, 5, 1))); // Tuesday
	}

	@Test
	public void test_isActiveBetweenDates() {
		WeeklyDateSpan wds = new WeeklyDateSpan();
		// This sets Tuesday, Thursday and Friday to be active
		wds.setActiveDays((byte) 0x1A);
		
		// Single-day
		// Not-active
		wds.setStartDate(new LocalDate(2012, 4, 23));
		wds.setEndDate(new LocalDate(2012, 4, 23));

		assertFalse(wds.isActiveBetween(new LocalDate(2012, 4, 22), new LocalDate(2012, 4, 24)));

		// Active
		wds.setStartDate(new LocalDate(2012, 4, 24));
		wds.setEndDate(new LocalDate(2012, 4, 24));

		assertTrue(wds.isActiveBetween(new LocalDate(2012, 4, 22), new LocalDate(2012, 4, 25)));
		assertTrue(wds.isActiveBetween(new LocalDate(2012, 4, 24), new LocalDate(2012, 4, 24)));

		// Multi-day
		// Not-active
		wds.setStartDate(new LocalDate(2012, 4, 28));
		wds.setEndDate(new LocalDate(2012, 4, 29));

		assertFalse(wds.isActiveBetween(new LocalDate(2012, 4, 27), new LocalDate(2012, 4, 30)));

		// Active / mixed
		wds.setStartDate(new LocalDate(2012, 4, 23));
		wds.setEndDate(new LocalDate(2012, 4, 29));

		assertTrue(wds.isActiveBetween(new LocalDate(2012, 4, 23), new LocalDate(2012, 4, 29)));
		assertTrue(wds.isActiveBetween(new LocalDate(2012, 4, 20), new LocalDate(2012, 4, 30)));
	}

	@Test
	public void test_activeDaysBetween() {
		WeeklyDateSpan wds = new WeeklyDateSpan();
		// This sets Tuesday, Thursday and Friday to be active
		wds.setActiveDays((byte) 0x1A);

		// Single-day
		// Not-active
		wds.setStartDate(new LocalDate(2012, 4, 23));
		wds.setEndDate(new LocalDate(2012, 4, 23));

		Set<LocalDate> dates = wds.activeDatesBetween(new LocalDate(2012, 4, 22), new LocalDate(2012, 4, 24));
		assertEquals(0, dates.size());

		// Active
		wds.setStartDate(new LocalDate(2012, 4, 24));
		wds.setEndDate(new LocalDate(2012, 4, 24));

		dates = wds.activeDatesBetween(new LocalDate(2012, 4, 22), new LocalDate(2012, 4, 25));
		assertEquals(1, dates.size());
		assertTrue(dates.contains(new LocalDate(2012, 4, 24)));

		dates = wds.activeDatesBetween(new LocalDate(2012, 4, 24), new LocalDate(2012, 4, 24));
		assertEquals(1, dates.size());
		assertTrue(dates.contains(new LocalDate(2012, 4, 24)));

		// Multi-day
		// Not-active
		wds.setStartDate(new LocalDate(2012, 4, 28));
		wds.setEndDate(new LocalDate(2012, 4, 29));

		dates = wds.activeDatesBetween(new LocalDate(2012, 4, 27), new LocalDate(2012, 4, 30));
		assertEquals(0, dates.size());

		// Active / mixed
		wds.setStartDate(new LocalDate(2012, 4, 23));
		wds.setEndDate(new LocalDate(2012, 4, 29));

		dates = wds.activeDatesBetween(new LocalDate(2012, 4, 23), new LocalDate(2012, 4, 29));
		assertEquals(3, dates.size());
		assertTrue(dates.contains(new LocalDate(2012, 4, 24)));
		assertTrue(dates.contains(new LocalDate(2012, 4, 26)));
		assertTrue(dates.contains(new LocalDate(2012, 4, 27)));

		dates = wds.activeDatesBetween(new LocalDate(2012, 4, 20), new LocalDate(2012, 4, 30));
		assertEquals(3, dates.size());
		assertTrue(dates.contains(new LocalDate(2012, 4, 24)));
		assertTrue(dates.contains(new LocalDate(2012, 4, 26)));
		assertTrue(dates.contains(new LocalDate(2012, 4, 27)));
	}
}
