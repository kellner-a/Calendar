import org.junit.Test;

import calendar.IEvent;
import calendar.SeriesEvent;

import static org.junit.Assert.*;

public class EventTest {
  private IEvent testEvent;

  @Test
  public void testSingleEvent() {

  }

  @Test
  public void testSeriesEvent() {
    SeriesEvent testSeriesEvent = new SeriesEvent("OOD", "2025-01-01T08:08",
            "2025-01-20T10:10",
            "WRS", "2025-01-25");
  }

}