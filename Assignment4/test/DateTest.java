import org.junit.Test;

import calendar.IDate;
import calendar.Date;

import static org.junit.Assert.*;
public class DateTest {
  private IDate testDate;

  @Test
  public void testDate() {
    testDate = new Date("2025-06-05");

    assertEquals(2025, testDate.getYear());
    assertEquals(6, testDate.getMonth());
    assertEquals(5, testDate.getDay());
    assertEquals("R", testDate.getDayOfWeek());

    testDate = new Date("2023-05-04");

    assertEquals(2023, testDate.getYear());
    assertEquals(5, testDate.getMonth());
    assertEquals(4, testDate.getDay());
    assertEquals("R", testDate.getDayOfWeek());

    assertEquals("2023-05-05", testDate.getNextDate(1).toString());
    assertEquals(0, testDate.compare(testDate));

  }
}