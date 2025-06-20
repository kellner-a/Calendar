import org.junit.Test;

import calendar.Date;
import calendar.IDate;

import static org.junit.Assert.assertEquals;

/**
 * Tests the date class.
 */
public class DateTest {

  @Test
  public void testDate() {
    // creates a date and tests its fields
    IDate testDate = new Date("2025-06-05");
    assertEquals(2025, testDate.getYear());
    assertEquals(6, testDate.getMonth());
    assertEquals(5, testDate.getDay());
    assertEquals("R", testDate.getDayOfWeek());

    // creates a date and tests its fields
    testDate = new Date("2023-05-04");
    assertEquals(2023, testDate.getYear());
    assertEquals(5, testDate.getMonth());
    assertEquals(4, testDate.getDay());
    assertEquals("R", testDate.getDayOfWeek());

    // tests getNextDate()
    assertEquals("2023-05-05", testDate.getNextDate(1).toString());
    assertEquals(0, testDate.compare(testDate));

    assertEquals(-1, new Date("2025-01-02").compare(new Date("2025-02-20")));
  }
}