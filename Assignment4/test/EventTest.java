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

  }

  @Test
  public void testSeriesEvent() {
    // an event that starts 6/9/25 - 6/19 9:50am to 11:30am, recurrs on MTWR,
    SeriesEvent testSeriesEvent = new SeriesEvent("OOD Lecture", "2025-06-09T09:50",
            "2025-06-19T11:30", "MTWR", "2025-06-20");

    assertEquals("2025-06-09, 2025-06-10, 2025-06-11, 2025-06-12, 2025-06-16, 2025-06-17," +
            " 2025-06-18, 2025-06-19", testSeriesEvent.recurringDatesAsString());


  }

}