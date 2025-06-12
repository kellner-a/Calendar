import org.junit.Before;
import org.junit.Test;

import calendar.Calendar;
import calendar.ICalendar;
import controller.Controller;
import controller.IController;
import view.IView;
import view.TextView;

import static org.junit.Assert.*;

/**
 * Tests for the calendar class.
 */

public class CalendarTest {
  private ICalendar testCalendar;
  private IController testController;
  private IView testView;

  @Before
  public void setUp() {
    this.testCalendar = new Calendar();
    this.testView = new TextView();
    this.testController = new Controller(this.testCalendar, this.testView);

    // single event 6/12/25 9:50am -> 6/12/25 11:30am
    this.testCalendar.createSingleEvent("Lecture", "2025-06-12T09:50",
            "2025-06-12T11:30");
    // series event starts 6/23/25 7am, repeats MTWRF 4x
    this.testCalendar.createEventSeriesTimesRepeated("Exercise",
            "2025-06-23T07:00",
            "2025-07-14T09:00",
            "MTWRF", 4);
    // series event starts 7/8/25 5pm, repeats MTWR, ends 7/31/25
    this.testCalendar.createEventSeriesStopDate("homework",
            "2025-07-08T17:00",
            "2025-07-31T19:00",
            "MTWR",
            "2025-07-31");
    // all day single event 12/4/25
    this.testCalendar.createSingleAllDayEvent("Meeting", "2025-12-04");
    // all day series event starts 8/1/25, repeats FSU 2x
    this.testCalendar.createAllDayEventSeriesTimesRepeated("Meeting",
            "2025-08-01",
            "FSU",
            2);
    // all day series event starts 1/1/25, repeats MWR, ends 2/20/25
    this.testCalendar.createAllDayEventSeriesStopDate("running",
            "2025-01-01",
            "MWR",
            "2025-02-20");
  }

  @Test
  public void testEditProperties() {
    // editing the location of all events with subject "Meeting" that come after 8/1/25 6am to
    // "coffee shop"
    this.testCalendar.editEventsProperty("location",
            "Meeting",
            "2025-12-04T08:00",
            "coffeeshop");

    assertEquals("coffeeshop", this.testCalendar.getEvents().get(3).getLocation());
//    assertEquals("coffee shop", this.testCalendar.getEvents().get(4).getLocation());

    // attempts to edit a property that doesn't exists
    try {
      setUp();
      this.testCalendar.editEventProperty("participants",
              "Meeting",
              "2025-12-04T08:00",
              "2025-12-04T17:00",
              "bob and dave");
    } catch (IllegalArgumentException e) {
      // catches the exception
    }
  }

  @Test
  public void testIsBusy() {
    setUp();
    // tests if a calendar has events on a given dateTTime
    // there is a single event from dateTTime - dateTTIme
    assertTrue(this.testCalendar.showStatus("2025-06-12T09:50"));

    // tests if a calendar has events on a given dateTTime
    // there are no events on this day
    assertFalse(this.testCalendar.showStatus("2025-11-20T07:50"));

    // tests if a calendar has events on a given dateTTime
    // there is a series event on this date
    assertTrue(this.testCalendar.showStatus("2025-01-01T08:00"));
    assertTrue(this.testCalendar.showStatus("2025-01-02T08:00"));
    assertFalse(this.testCalendar.showStatus("2025-01-03T08:00"));
  }

  @Test
  public void testQuery() {
    setUp();

    assertEquals("Lecture: 2025-06-12 09:50 - 11:30 Exercise: 2025-06-23 07:00 - 09:00 " +
            "Exercise: 2025-06-24 07:00 - 09:00 " +
            "Exercise: 2025-06-25 07:00 - 09:00",
            this.testCalendar.getEventsToString("2025-06-12T09:50", "2025-06-25T09:00"));
  }

  @Test
  public void testCommands() {
    setUp();
    // adds an event into a calendar with the controller using a command
    this.testController.createSingleEvent(
            "create event dinner from 2025-06-12T20:00 to 2025-06-12T21:00");

    assertEquals("dinner: 2025-06-12 20:00 - 21:00", this.testCalendar.getEvents().get(6).toString());

    assertEquals("busy",
            this.testController.showStatus("show status on 2025-12-04T8:00"));

    assertEquals("available",
            this.testController.showStatus("show status on 2025-01-04T8:00"));

    assertEquals("•Meeting: 2025-12-04 08:00 - 17:00\n", this.testController.printEventsDay("print events on 2025-12-04"));

    assertEquals("•running: 2025-01-01 08:00 - 17:00\n" +
                    "•running: 2025-01-02 08:00 - 17:00\n" +
                    "•running: 2025-01-06 08:00 - 17:00\n",
            this.testController.printEventsRange(
                    "print events from 2025-01-01T01:00 to 2025-01-07T18:00"));

    // attempts to use a command with a from date that comes after the to date
    try {
      this.testController.printEventsRange("print events from 2025-01-07T18:00 to 2025-01-01T01:00");
      fail("the above shouldn't be allowed");
    } catch(IllegalArgumentException e) {
      // a from date can not be a date that comes after a to date
    }

    // attempts to use an invalid weekdays string in the command
    try {
      setUp();
      this.testCalendar.createEventSeriesTimesRepeated("breakfast",
              "2025-01-01T01:00", "2025-01-07T18:00", "FWM", 3);
      fail("there is an invalid weekdays string and an error should be thrown");
    }catch (IllegalArgumentException e) {
      // the exception is caught
    }
  }
}