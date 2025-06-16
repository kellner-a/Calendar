package calendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * Represents a Calendar Suite. A calendar suite allows for multiple unique calendars. A
 * calendar suite is able to create new calendars, edit existing calendars, and copy events
 * between calendars. Calendars are stored in a hashmap using their name and the calendar created
 * under that name.
 */
public class CalendarSuite implements ICalendarSuite {

  private final Map<String, ICalendar> calendars;
  private final Map<String, TimeZone> timezones;
  private String calendarInUse;

  /**
   * Creates a CalendarSuite. Initializes the calendars and timezones to empty hashmaps and
   * calendarInUse to an empty.
   */
  public CalendarSuite() {
    calendars = new HashMap<>();
    timezones = new HashMap<>();
    this.createCalendar("Default", "America/New_York");
    calendarInUse = "Default";
  }

  /**
   * Validates that the given timezone is formatted correctly. Throws an error if it's invalid.
   *
   * @param timezone String - "place/place"
   * @throws IllegalArgumentException if not a valid timezone
   */
  private void validateTimezone(String timezone) throws IllegalArgumentException {
    if (!Pattern.matches("^\\S+/\\S+", timezone)) {
      throw new IllegalArgumentException("Invalid time zone");
    }
  }

  /**
   * Validates that the given calendarName exists within this suite. Throws an error if the
   * calendar doesn't exist or the given name is an empty string.
   *
   * @param calendarName String
   * @throws IllegalArgumentException if no calendar exists, or empty string
   */
  private void validateCalendarName(String calendarName) throws IllegalArgumentException {
    if (calendarName == null || calendarName.isEmpty()) {
      throw new IllegalArgumentException("Calendar name cannot be null or empty");
    }
    if (!calendars.containsKey(calendarName)) {
      throw new IllegalArgumentException("No such calendar: " + calendarName);
    }
  }

  /**
   * Validates that a calendar has been marked as in use. Throws and error if there is no
   * calendar marked as in use.
   */
  private void validateInUse() {
    if (calendarInUse.isEmpty()) {
      throw new IllegalArgumentException("No calendar in use");
    }
  }

  /**
   * Throws an exception if date is formatted incorrectly.
   *
   * @param date String
   * @throws IllegalArgumentException if not "YYYY-M(M)-D(D)"
   */
  private void validateDate(String date) throws IllegalArgumentException {
    if (!Pattern.matches("^\\d\\d\\d\\d-\\d\\d?-\\d\\d?$", date)) {
      throw new IllegalArgumentException("Invalid date");
    }
  }

  /**
   * Throws an exception if dateTtime is formatted incorrectly.
   *
   * @param dateTtime String
   * @throws IllegalArgumentException if not "YYYY-M(M)-D(D)Th(h):m(m)"
   */
  private void validateDateTtime(String dateTtime) throws IllegalArgumentException {
    if (!Pattern.matches("^\\d\\d\\d\\d-\\d\\d?-\\d\\d?T\\d\\d?:\\d\\d?$", dateTtime)
            || dateTtime.isEmpty()) {
      throw new IllegalArgumentException("Invalid dateTtime");
    }
  }

  @Override
  public void createCalendar(String name, String timezone) throws IllegalArgumentException {
    validateTimezone(timezone);
    if (calendars.containsKey(name)) {
      throw new IllegalArgumentException("Calendar with name " + name + " already exists");
    }
    this.calendars.put(name, new Calendar());
    TimeZone tz;
    try {
      tz = TimeZone.getTimeZone(timezone);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid time zone");
    }
    this.timezones.put(name, tz);
  }

  @Override
  public ICalendar getCalendar() throws IllegalArgumentException {
    if (!this.calendarInUse.isEmpty()) {
      return this.calendars.get(this.calendarInUse);
    }
    throw new IllegalArgumentException("No calendar in use");
  }

  @Override
  public String getCalendarInUseName() throws IllegalArgumentException {
    if (!this.calendarInUse.isEmpty()) {
      return this.calendarInUse;
    }
    throw new IllegalArgumentException("No calendar in use");
  }

  @Override
  public ArrayList<String> getCalendarNames() {
    ArrayList<String> calendarNames = new ArrayList<>();
    for (Map.Entry<String, ICalendar> entry : this.calendars.entrySet()) {
      calendarNames.add(entry.getKey());
    }
    return calendarNames;
  }

  @Override
  public void editCalendar(String name, String prop, String newPropValue)
          throws IllegalArgumentException {
    validateCalendarName(name);
    if (!prop.equals("name") && !prop.equals("timezone")) {
      throw new IllegalArgumentException("Invalid property");
    }
    if (prop.equals("name")) {
      this.calendars.put(newPropValue, this.calendars.remove(name));
      this.timezones.put(newPropValue, this.timezones.remove(name));
    } else {
      validateTimezone(newPropValue);
      this.timezones.remove(name);
      TimeZone tz;
      try {
        tz = TimeZone.getTimeZone(newPropValue);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Invalid time zone");
      }
      this.timezones.put(name, tz);
    }
  }

  @Override
  public void useCalendar(String name) throws IllegalArgumentException {
    validateCalendarName(name);
    this.calendarInUse = name;
  }

  @Override
  public void copySingleEvent(String eventName, String startDateTtime, String targetCalendarName,
                              String newDateTtime) throws IllegalArgumentException {
    validateInUse();
    validateCalendarName(targetCalendarName);
    validateDateTtime(startDateTtime);
    validateDateTtime(newDateTtime);

    ArrayList<IEvent> events = this.calendars.get(calendarInUse).getEvents(startDateTtime,
            startDateTtime);
    int timeDifference =
            (this.timezones.get(targetCalendarName).getRawOffset()
                    - this.timezones.get(this.calendarInUse).getRawOffset()) / 3600000;

    IEvent toCopy = null;
    for (IEvent event : events) {
      if (event.getSubject().equals(eventName)) {
        toCopy = event;
      }
    }
    if (toCopy == null) {
      throw new IllegalArgumentException("No such event: " + eventName);
    }
    ICalendar target = this.calendars.get(targetCalendarName);
    target.addEvent(toCopy.deepCopy(timeDifference));
  }

  @Override
  public void copyDayEvents(String date, String targetCalendarName, String targetDate)
          throws IllegalArgumentException {
    validateInUse();
    validateCalendarName(targetCalendarName);
    validateDate(date);
    validateDate(targetDate);

    ICalendar source = this.calendars.get(calendarInUse);
    TimeZone sourceTZ = this.timezones.get(calendarInUse);
    ICalendar target = this.calendars.get(targetCalendarName);
    TimeZone targetTZ = this.timezones.get(targetCalendarName);

    ArrayList<IEvent> events = source.getEvents(date);

    // postive moves timezone forward
    int timeDifference = (targetTZ.getRawOffset() - sourceTZ.getRawOffset()) / 3600000;

    for (IEvent event : events) {
      target.addEvent(event.deepCopy(timeDifference));
    }

  }

  @Override
  public void copyEventsRange(String startDate, String endDate, String targetCalendarName,
                              String targetStartDate) throws IllegalArgumentException {
    validateInUse();
    validateCalendarName(targetCalendarName);
    validateDate(startDate);
    validateDate(endDate);
    validateDate(targetStartDate);

    ICalendar source = this.calendars.get(calendarInUse);
    TimeZone sourceTZ = this.timezones.get(calendarInUse);
    ICalendar target = this.calendars.get(targetCalendarName);
    TimeZone targetTZ = this.timezones.get(targetCalendarName);

    ArrayList<IEvent> events = source.getEvents(startDate + "T00:00", endDate + "T23:59");

    // postive moves timezone forward
    int timeDifference = (targetTZ.getRawOffset() - sourceTZ.getRawOffset()) / 3600000;

    for (IEvent event : events) {
      target.addEvent(event.deepCopy(timeDifference));
    }
  }
}
