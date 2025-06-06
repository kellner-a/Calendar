import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.Assert.*;
import calendar.IEvent;
import calendar.SingleEvent;
import calendar.SeriesEvent;
import calendar.Date;

public class EventTest {
  private IEvent testEvent;
  private ArrayList<Date> testList;

  @Test
  public void testSingleEvent() {
    testEvent = new SingleEvent("OOD Lecture", "2000-05-08T12:34",
            "2000-05-20T23:45");

    assertTrue(testEvent.match("OOD Lecture", "2000-05-08T12:34",
            "2000-05-20T23:45"));
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

}