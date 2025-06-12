package calendar;

import java.util.ArrayList;

/**
 * Interface for a Calendar Suite. A calendar suite allows for multiple unique calendars. A
 * calendar suite is able to create new calendars, edit existing calendars, and copy events
 * between calendars.
 */
public interface ICalendarSuite {

  /**
   * Creates a new Calendar with the given name and timezone. Throws an error if the given
   * timezone is not one of the supported timezones, or a calendar with the same name already
   * exists.
   *
   * @param name     String
   * @param timezone String --> FIND FORMAT
   * @throws IllegalArgumentException when timezone is not supported, or calendar name already
   *                                  exists within this suite
   */
  void createCalendar(String name, String timezone) throws IllegalArgumentException;

  /**
   * Returns the calendar matching the given name, case-sensitive. Throws and error if no
   * calendar with the given name exists.
   *
   * @param name String
   * @return ICalendar
   * @throws IllegalArgumentException when no calendar exists with given name
   */
  ICalendar getCalendar(String name) throws IllegalArgumentException;

  /**
   * Returns a list of all current calendar names in this suite.
   *
   * @return list of calendar names
   */
  ArrayList<String> getCalendarNames();

  /**
   * Edits a property of the given calendar's name. Valid prop inputs are "name" and
   * "timezone". Throws and error if no calendar with the given name exists or the given prop is
   * invalid.
   *
   * @param name         String
   * @param prop         "name" or "timezone"
   * @param newPropValue String
   * @throws IllegalArgumentException when no calendar exists with given name, or prop is not
   *                                  "name" or "timezone"
   */
  void editCalendar(String name, String prop, String newPropValue) throws IllegalArgumentException;

  /**
   * Marks the given calendar name as the calendar in use. Throws an error if the calendar
   * doesn't exist.
   *
   * @param name String
   * @throws IllegalArgumentException if the calendar doesn't exist
   */
  void useCalendar(String name) throws IllegalArgumentException;

  /**
   * Copies a single event matching the given eventName and startDateTtime from the calendar
   * currently marked as in-use to the target calendar at the given newDateTtime. The
   * newDateTtime is assumed to be in the target calendar's timezone.
   *
   * @param eventName          String
   * @param startDateTtime     "YYYY-MM-DDThh:mm"
   * @param targetCalendarName String
   * @param newDateTtime       "YYYY-MM-DDThh:mm"
   * @throws IllegalArgumentException when input isn't formatted, event doesn't exist, calendar
   *                                  doesn't exist, or no calendar is marked as in use
   */
  void copySingleEvent(String eventName, String startDateTtime, String targetCalendarName,
                       String newDateTtime) throws IllegalArgumentException;

  /**
   * Copies all events on the given date from the calendar marked as in use to the target
   * calendar on the targeted date. The times of the events are converted to match the the
   * calendar they are being copied to.
   *
   * @param date               "YYYY-MM-DD"
   * @param targetCalendarName String
   * @param targetDate         "YYYY-MM-DD"
   * @throws IllegalArgumentException when input isn't formatted, calendar doesn't exist, or no
   *                                  calendar is marked as in use
   */
  void copyDayEvents(String date, String targetCalendarName, String targetDate)
          throws IllegalArgumentException;

  /**
   * Copies all events within the range of the given startDate and endDate from the calendar
   * currently marked as in-use to the target calendar starting at the give targetStartDate.
   *
   * @param startDate          "YYYY-MM-DD"
   * @param endDate            "YYYY-MM-DD"
   * @param targetCalendarName String
   * @param targetStartDate    "YYYY-MM-DD"
   * @throws IllegalArgumentException when input isn't formatted, calendar doesn't exist, or no
   *                                  calendar is marked as in use
   */
  void copyEventsRange(String startDate, String endDate, String targetCalendarName,
                       String targetStartDate) throws IllegalArgumentException;
}
