import org.junit.Test;

import java.util.ArrayList;

import calendar.Date;
import calendar.IEvent;
import calendar.SeriesEvent;
import calendar.SingleEvent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests the Event class.
 */
public class EventTest {
  private IEvent testEvent;
  private ArrayList<Date> testList;

  @Test
  public void testSingleEvent() {
    // creates a single event from a dateTime to another dateTime
    testEvent = new SingleEvent("OOD Lecture", "2000-05-08T12:34",
            "2000-05-20T23:45");

    assertTrue(testEvent.match("OOD Lecture", "2000-05-08T12:34",
            "2000-05-20T23:45"));

    // creates a single all day event
    testEvent = new SingleEvent("Coffee Chat", "2025-06-10T13:00");
    assertEquals("Coffee Chat", testEvent.getSubject());
    assertEquals("2025-06-10T08:00", testEvent.getDateTtime(true));
    assertEquals("2025-06-10T17:00", testEvent.getDateTtime(false));
  }

  @Test
  public void testEditProperty() {
    testEvent = new SingleEvent("OOD Lecture", "2000-05-08T12:34",
            "2000-05-20T23:45");

    assertEquals("lecture", testEvent.editEventProperty("subject", "2000-05-08T12:34",
            "lecture").getSubject());

  }

  @Test
  public void testSeriesEvent() {
    // a series event that starts on 2025-06-09 and repeats on Tuesdays, wednesday, and Thursdays
    // and this repeats twice
    SeriesEvent testSeriesEvent = new SeriesEvent("OOD Lecture",
            "2025-06-09T09:50", "2025-06-19T11:30",
            "TWR", 2);

    assertEquals("2025-06-10, 2025-06-11, 2025-06-12, 2025-06-17," +
            " 2025-06-18, 2025-06-19", testSeriesEvent.recurringDatesAsString());

    // an event that starts 6/9/25 - 6/19 9:50am to 11:30am, recurrs on MTWR,
    testSeriesEvent = new SeriesEvent("OOD Lecture", "2025-06-09T09:50",
            "2025-06-19T11:30", "TWR", "2025-06-20");

    assertEquals("2025-06-10, 2025-06-11, 2025-06-12, 2025-06-17," +
            " 2025-06-18, 2025-06-19", testSeriesEvent.recurringDatesAsString());

  }

  @Test
  public void testCopyEvent() {
    testEvent = new SingleEvent("study", "2025-01-10T10:00",
            "2025-01-10T12:00");

    assertEquals("study: 2025-01-10 10:00 - 12:00",
            testEvent.deepCopy(0).toString());

    assertEquals("study: 2025-01-10 12:00 - 14:00",
            testEvent.deepCopy(2).toString());
  }

}