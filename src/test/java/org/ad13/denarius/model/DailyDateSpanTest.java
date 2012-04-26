package org.ad13.denarius.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.joda.time.LocalDate;
import org.junit.Test;

public class DailyDateSpanTest {

	/**
	 * Tests the isActiveOnDate method with an interval of 1
	 */
	@Test
	public void test_isActiveOnDate() {
		DailyDateSpan dds = new DailyDateSpan();

		// Check start date = end date
		dds.setStartDate(new LocalDate(2012, 4, 23));
		dds.setEndDate(new LocalDate(2012, 4, 23));

		assertFalse(dds.isActiveOn(new LocalDate(2012, 4, 22)));
		assertTrue(dds.isActiveOn(new LocalDate(2012, 4, 23)));
		assertFalse(dds.isActiveOn(new LocalDate(2012, 4, 24)));

		// Check start date != end date
		dds.setStartDate(new LocalDate(2012, 4, 23));
		dds.setEndDate(new LocalDate(2012, 4, 26));

		assertFalse(dds.isActiveOn(new LocalDate(2012, 4, 22)));
		assertTrue(dds.isActiveOn(new LocalDate(2012, 4, 23)));
		assertTrue(dds.isActiveOn(new LocalDate(2012, 4, 25)));
		assertTrue(dds.isActiveOn(new LocalDate(2012, 4, 26)));
		assertFalse(dds.isActiveOn(new LocalDate(2012, 4, 27)));
	}

	/**
	 * Tests the isActiveOnDate method with an interval of 2.
	 */
	@Test
	public void test_isActiveOnDate_withInterval() {
		DailyDateSpan dds = new DailyDateSpan();
		dds.setInterval(2);

		// Check start date = end date
		dds.setStartDate(new LocalDate(2012, 4, 23));
		dds.setEndDate(new LocalDate(2012, 4, 23));

		assertFalse(dds.isActiveOn(new LocalDate(2012, 4, 22)));
		assertTrue(dds.isActiveOn(new LocalDate(2012, 4, 23)));
		assertFalse(dds.isActiveOn(new LocalDate(2012, 4, 24)));

		// Check start date != end date
		dds.setStartDate(new LocalDate(2012, 4, 23));
		dds.setEndDate(new LocalDate(2012, 4, 26));

		assertFalse(dds.isActiveOn(new LocalDate(2012, 4, 22)));
		assertTrue(dds.isActiveOn(new LocalDate(2012, 4, 23)));
		assertFalse(dds.isActiveOn(new LocalDate(2012, 4, 24)));
		assertTrue(dds.isActiveOn(new LocalDate(2012, 4, 25)));
		assertFalse(dds.isActiveOn(new LocalDate(2012, 4, 26)));
		assertFalse(dds.isActiveOn(new LocalDate(2012, 4, 27)));
	}

	@Test
	public void test_isActiveBetweenDates() {
		DailyDateSpan dds = new DailyDateSpan();

		// Check start date = end date
		dds.setStartDate(new LocalDate(2012, 4, 23));
		dds.setEndDate(new LocalDate(2012, 4, 23));

		assertFalse(dds.isActiveBetween(new LocalDate(2012, 4, 22), new LocalDate(2012, 4, 22)));
		assertTrue(dds.isActiveBetween(new LocalDate(2012, 4, 23), new LocalDate(2012, 4, 23)));
		assertFalse(dds.isActiveBetween(new LocalDate(2012, 4, 24), new LocalDate(2012, 4, 24)));

		assertTrue(dds.isActiveBetween(new LocalDate(2012, 4, 22), new LocalDate(2012, 4, 23)));
		assertTrue(dds.isActiveBetween(new LocalDate(2012, 4, 22), new LocalDate(2012, 4, 24)));
		assertTrue(dds.isActiveBetween(new LocalDate(2012, 4, 23), new LocalDate(2012, 4, 24)));

		// Check start date != end date
		dds.setStartDate(new LocalDate(2012, 4, 23));
		dds.setEndDate(new LocalDate(2012, 4, 26));

		assertFalse(dds.isActiveBetween(new LocalDate(2012, 4, 20), new LocalDate(2012, 4, 22)));
		assertTrue(dds.isActiveBetween(new LocalDate(2012, 4, 20), new LocalDate(2012, 4, 23)));
		assertTrue(dds.isActiveBetween(new LocalDate(2012, 4, 20), new LocalDate(2012, 4, 30)));
		assertTrue(dds.isActiveBetween(new LocalDate(2012, 4, 23), new LocalDate(2012, 4, 26)));
		assertTrue(dds.isActiveBetween(new LocalDate(2012, 4, 24), new LocalDate(2012, 4, 25)));
		assertTrue(dds.isActiveBetween(new LocalDate(2012, 4, 26), new LocalDate(2012, 4, 30)));
		assertFalse(dds.isActiveBetween(new LocalDate(2012, 4, 27), new LocalDate(2012, 4, 30)));
	}
	
	@Test
	public void test_isActiveBetweenDates_withInterval() {
		DailyDateSpan dds = new DailyDateSpan();
		dds.setInterval(2);

		// Check start date = end date
		dds.setStartDate(new LocalDate(2012, 4, 23));
		dds.setEndDate(new LocalDate(2012, 4, 23));

		assertFalse(dds.isActiveBetween(new LocalDate(2012, 4, 22), new LocalDate(2012, 4, 22)));
		assertTrue(dds.isActiveBetween(new LocalDate(2012, 4, 23), new LocalDate(2012, 4, 23)));
		assertFalse(dds.isActiveBetween(new LocalDate(2012, 4, 24), new LocalDate(2012, 4, 24)));

		// Check start date != end date
		dds.setStartDate(new LocalDate(2012, 4, 23));
		dds.setEndDate(new LocalDate(2012, 4, 26));

		assertFalse(dds.isActiveBetween(new LocalDate(2012, 4, 20), new LocalDate(2012, 4, 22)));
		assertTrue(dds.isActiveBetween(new LocalDate(2012, 4, 20), new LocalDate(2012, 4, 23)));
		assertTrue(dds.isActiveBetween(new LocalDate(2012, 4, 20), new LocalDate(2012, 4, 30)));
		assertTrue(dds.isActiveBetween(new LocalDate(2012, 4, 23), new LocalDate(2012, 4, 26)));
		assertTrue(dds.isActiveBetween(new LocalDate(2012, 4, 24), new LocalDate(2012, 4, 25)));
		assertTrue(dds.isActiveBetween(new LocalDate(2012, 4, 26), new LocalDate(2012, 4, 30)));
		assertFalse(dds.isActiveBetween(new LocalDate(2012, 4, 27), new LocalDate(2012, 4, 30)));
	}	

	@Test
	public void test_activeDaysBetween() {
		DailyDateSpan dds = new DailyDateSpan();

		// Check start date = end date
		dds.setStartDate(new LocalDate(2012, 4, 23));
		dds.setEndDate(new LocalDate(2012, 4, 23));

		// Outer case
		Set<LocalDate> dates = dds.activeDatesBetween(new LocalDate(2012, 4, 22), new LocalDate(2012, 4, 24));
		assertEquals(1, dates.size());
		assertTrue(dates.contains(new LocalDate(2012, 4, 23)));

		// Left-inner case
		dates = dds.activeDatesBetween(new LocalDate(2012, 4, 22), new LocalDate(2012, 4, 23));
		assertEquals(1, dates.size());
		assertTrue(dates.contains(new LocalDate(2012, 4, 23)));

		// Right-inner case
		dates = dds.activeDatesBetween(new LocalDate(2012, 4, 23), new LocalDate(2012, 4, 24));
		assertEquals(1, dates.size());
		assertTrue(dates.contains(new LocalDate(2012, 4, 23)));

		// Full-inner case
		dates = dds.activeDatesBetween(new LocalDate(2012, 4, 23), new LocalDate(2012, 4, 23));
		assertEquals(1, dates.size());
		assertTrue(dates.contains(new LocalDate(2012, 4, 23)));

		// Left-outer fail-case
		dates = dds.activeDatesBetween(new LocalDate(2012, 4, 21), new LocalDate(2012, 4, 22));
		assertEquals(0, dates.size());

		// Right-outer fail-case
		dates = dds.activeDatesBetween(new LocalDate(2012, 4, 24), new LocalDate(2012, 4, 25));
		assertEquals(0, dates.size());

		// Check date span
		dds.setStartDate(new LocalDate(2012, 4, 23));
		dds.setEndDate(new LocalDate(2012, 4, 26));

		// Outer case
		dates = dds.activeDatesBetween(new LocalDate(2012, 4, 22), new LocalDate(2012, 4, 27));
		assertEquals(4, dates.size());
		assertTrue(dates.contains(new LocalDate(2012, 4, 23)));
		assertTrue(dates.contains(new LocalDate(2012, 4, 24)));
		assertTrue(dates.contains(new LocalDate(2012, 4, 25)));
		assertTrue(dates.contains(new LocalDate(2012, 4, 26)));

		// Left-inner case
		dates = dds.activeDatesBetween(new LocalDate(2012, 4, 22), new LocalDate(2012, 4, 24));
		assertEquals(2, dates.size());
		assertTrue(dates.contains(new LocalDate(2012, 4, 23)));
		assertTrue(dates.contains(new LocalDate(2012, 4, 24)));

		// Right-inner case
		dates = dds.activeDatesBetween(new LocalDate(2012, 4, 25), new LocalDate(2012, 4, 27));
		assertEquals(2, dates.size());
		assertTrue(dates.contains(new LocalDate(2012, 4, 25)));
		assertTrue(dates.contains(new LocalDate(2012, 4, 26)));

		// Full-inner case
		dates = dds.activeDatesBetween(new LocalDate(2012, 4, 23), new LocalDate(2012, 4, 26));
		assertEquals(4, dates.size());
		assertTrue(dates.contains(new LocalDate(2012, 4, 23)));
		assertTrue(dates.contains(new LocalDate(2012, 4, 24)));
		assertTrue(dates.contains(new LocalDate(2012, 4, 25)));
		assertTrue(dates.contains(new LocalDate(2012, 4, 26)));

		// Partial-inner case
		dates = dds.activeDatesBetween(new LocalDate(2012, 4, 24), new LocalDate(2012, 4, 25));
		assertEquals(2, dates.size());
		assertTrue(dates.contains(new LocalDate(2012, 4, 24)));
		assertTrue(dates.contains(new LocalDate(2012, 4, 25)));

		// Left-outer fail-case
		dates = dds.activeDatesBetween(new LocalDate(2012, 4, 21), new LocalDate(2012, 4, 22));
		assertEquals(0, dates.size());

		// Right-outer fail-case
		dates = dds.activeDatesBetween(new LocalDate(2012, 4, 27), new LocalDate(2012, 4, 28));
		assertEquals(0, dates.size());
	}
	
	@Test
	public void test_activeDaysBetween_withInterval() {
		DailyDateSpan dds = new DailyDateSpan();
		dds.setInterval(2);

		// Check start date = end date
		dds.setStartDate(new LocalDate(2012, 4, 23));
		dds.setEndDate(new LocalDate(2012, 4, 23));

		// Outer case
		Set<LocalDate> dates = dds.activeDatesBetween(new LocalDate(2012, 4, 22), new LocalDate(2012, 4, 24));
		assertEquals(1, dates.size());
		assertTrue(dates.contains(new LocalDate(2012, 4, 23)));

		// Left-inner case
		dates = dds.activeDatesBetween(new LocalDate(2012, 4, 22), new LocalDate(2012, 4, 23));
		assertEquals(1, dates.size());
		assertTrue(dates.contains(new LocalDate(2012, 4, 23)));

		// Right-inner case
		dates = dds.activeDatesBetween(new LocalDate(2012, 4, 23), new LocalDate(2012, 4, 24));
		assertEquals(1, dates.size());
		assertTrue(dates.contains(new LocalDate(2012, 4, 23)));

		// Full-inner case
		dates = dds.activeDatesBetween(new LocalDate(2012, 4, 23), new LocalDate(2012, 4, 23));
		assertEquals(1, dates.size());
		assertTrue(dates.contains(new LocalDate(2012, 4, 23)));

		// Left-outer fail-case
		dates = dds.activeDatesBetween(new LocalDate(2012, 4, 21), new LocalDate(2012, 4, 22));
		assertEquals(0, dates.size());

		// Right-outer fail-case
		dates = dds.activeDatesBetween(new LocalDate(2012, 4, 24), new LocalDate(2012, 4, 25));
		assertEquals(0, dates.size());

		// Check date span
		dds.setStartDate(new LocalDate(2012, 4, 23));
		dds.setEndDate(new LocalDate(2012, 4, 26));

		// Outer case
		dates = dds.activeDatesBetween(new LocalDate(2012, 4, 22), new LocalDate(2012, 4, 27));
		assertEquals(2, dates.size());
		assertTrue(dates.contains(new LocalDate(2012, 4, 23)));
		assertTrue(dates.contains(new LocalDate(2012, 4, 25)));

		// Left-inner case
		dates = dds.activeDatesBetween(new LocalDate(2012, 4, 22), new LocalDate(2012, 4, 24));
		assertEquals(1, dates.size());
		assertTrue(dates.contains(new LocalDate(2012, 4, 23)));

		// Right-inner case
		dates = dds.activeDatesBetween(new LocalDate(2012, 4, 25), new LocalDate(2012, 4, 27));
		assertEquals(1, dates.size());
		assertTrue(dates.contains(new LocalDate(2012, 4, 25)));

		// Full-inner case
		dates = dds.activeDatesBetween(new LocalDate(2012, 4, 23), new LocalDate(2012, 4, 26));
		assertEquals(2, dates.size());
		assertTrue(dates.contains(new LocalDate(2012, 4, 23)));
		assertTrue(dates.contains(new LocalDate(2012, 4, 25)));

		// Partial-inner case
		dates = dds.activeDatesBetween(new LocalDate(2012, 4, 24), new LocalDate(2012, 4, 25));
		assertEquals(1, dates.size());
		assertTrue(dates.contains(new LocalDate(2012, 4, 25)));

		// Left-outer fail-case
		dates = dds.activeDatesBetween(new LocalDate(2012, 4, 21), new LocalDate(2012, 4, 22));
		assertEquals(0, dates.size());

		// Right-outer fail-case
		dates = dds.activeDatesBetween(new LocalDate(2012, 4, 27), new LocalDate(2012, 4, 28));
		assertEquals(0, dates.size());
	}	
}
