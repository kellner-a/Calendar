import org.junit.Test;

import calendar.Date;
import calendar.IDate;

import static org.junit.Assert.*;

public class DateTest {
  private IDate testDate;

  @Test
  public void testDate() {
    testDate = new Date("2025-06-05T08:08");

    assertEquals(2025, testDate.getYear());
    assertEquals(6, testDate.getMonth());
    assertEquals(5, testDate.getDay());
    assertEquals('R', testDate.getDayOfWeek());
  }
}