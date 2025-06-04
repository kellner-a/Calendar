package calendar;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents a Calendar. A calendar can have single events and a series of events. A
 * dateString is formatted "YYYY-MM-DD". A timeString is formatted "hh:mm". A
 * dateStringTtimestring is formatted "YYYY-MM-DDThh:mm". Weekdays are a set of characters where
 * 'M' is Monday, 'T' is Tuesday, 'W' is Wednesday, 'R' is Thursday, 'F' is Friday, 'S' is
 * Saturday, and 'U' is Sunday. A weekday input can be "MWR", "TFS", "FSU". A weekday is order
 * specific, so "FUS" is not valid.
 */
public class Calendar implements ICalendar {

  private Date currentDate;
  private List<IEvent> events;

  /**
   * Constructor for a calendar. Takes in a dateString as the current date and initializes its
   * list of events.
   *
   * @param date "YYYY-M(M)-D(D)"
   * @throws IllegalArgumentException when date format isn't matched
   */
  public Calendar(String date) throws IllegalArgumentException {
    validateDate(date);
    this.currentDate = new Date(date);
    this.events = new ArrayList<>();
  }

  /**
   * Throws an exception if date is formatted incorrectly.
   *
   * @param date String
   * @throws IllegalArgumentException if not "YYYY-M(M)-D(D)?"
   */
  private void validateDate(String date) throws IllegalArgumentException {
    if (!Pattern.matches("^\\d\\d\\d\\d-\\d\\d?-\\d\\d?$", date)) {
      throw new IllegalArgumentException("Invalid date");
    }
  }

  /**
   * Throws an exception if time is formatted incorrectly.
   *
   * @param time String
   * @throws IllegalArgumentException if not "h(h):m(m)"
   */
  private void validateTime(String time) throws IllegalArgumentException {
    if (!Pattern.matches("^\\d\\d?:\\d\\d?$", time)) {
      throw new IllegalArgumentException("Invalid time");
    }
  }

  /**
   * Throws an exception if dateTtime is formatted incorrectly.
   *
   * @param dateTtime
   * @throws IllegalArgumentException if not "YYYY-M(M)-D(D)Th(h):m(m)"
   */
  private void validateDateTtime(String dateTtime) throws IllegalArgumentException {
    if (!Pattern.matches("^\\d\\d\\d\\d-\\d\\d?-\\d\\d?T\\d\\d?:\\d\\d?$", dateTtime)
            || dateTtime.isEmpty()) {
      throw new IllegalArgumentException("Invalid dateTtime");
    }
  }

  /**
   * Throws an exception if weekdays is formatted incorrectly.
   *
   * @param weekdays String
   * @throws IllegalArgumentException if not MTWRFSU
   */
  private void validateWeekdays(String weekdays) throws IllegalArgumentException {
    if (!Pattern.matches("^M?T?W?R?F?S?U?$", weekdays) || weekdays.isEmpty()) {
      throw new IllegalArgumentException("Invalid weekdays");
    }
  }

  /**
   * Throws an error if property is not "subject", start", "end", "description", "location",
   * "status".
   *
   * @param newPropvalue
   */
  private void validateProperty(String prop) throws IllegalArgumentException {
    if (!prop.equals("subject") && !prop.equals("start") && !prop.equals("end") && !prop.equals(
            "description") && !prop.equals("locations") && !prop.equals("status")) {
      throw new IllegalArgumentException("Invalid property");
    }
  }

  @Override
  public void createSingleEvent(String subject, String startDateTtime, String endDateTtime)
          throws IllegalArgumentException {
    validateDateTtime(startDateTtime);
    validateDateTtime(endDateTtime);
    this.events.add(new SingleEvent(subject, startDateTtime, endDateTtime));
  }

  @Override
  public void createEventSeries(String subject, String startDateTtime, String endDateTtime,
                                String weekdays, int timesRepeated)
          throws IllegalArgumentException {
    if (timesRepeated <= 0) throw new IllegalArgumentException("Invalid times to repeat");
    validateDateTtime(startDateTtime);
    validateDateTtime(endDateTtime);
    validateWeekdays(weekdays);
    this.events.add(new SeriesEvent(subject, startDateTtime, endDateTtime, weekdays,
            timesRepeated));
  }

  @Override
  public void createEventSeries(String subject, String startDateTtime, String endDateTtime,
                                String weekdays, String stopDate) throws IllegalArgumentException {
    validateDateTtime(startDateTtime);
    validateDateTtime(endDateTtime);
    validateWeekdays(weekdays);
    validateTime(stopDate);
    this.events.add(new SeriesEvent(subject, startDateTtime, endDateTtime, weekdays, stopDate));
  }

  @Override
  public void createSingleEvent(String subject, String date) throws IllegalArgumentException {
    validateDate(date);
    this.events.add(new SingleEvent(subject, date));
  }

  @Override
  public void createEventSeries(String subject, String startDate, String weekdays,
                                int timesRepeated) throws IllegalArgumentException {
    if (timesRepeated <= 0) throw new IllegalArgumentException("Invalid times to repeat");
    validateDate(startDate);
    validateDate(weekdays);
    this.events.add(new SeriesEvent(subject, startDate, weekdays, timesRepeated));
  }

  @Override
  public void createEventSeries(String subject, String startDate, String weekdays, String stopDate)
          throws IllegalArgumentException {
    validateDate(startDate);
    validateDate(weekdays);
    validateTime(stopDate);
    this.events.add(new SeriesEvent(subject, startDate, weekdays, stopDate));
  }

  @Override
  public void editEventProperty(String prop, String eventSubject, String startDateTtime,
                                String endDateTtime, String newPropvalue)
          throws IllegalArgumentException {
    validateDateTtime(startDateTtime);
    validateDateTtime(endDateTtime);
    validateProperty(prop);
    for (IEvent event: this.events) {
      if (event.getSubject().equals(eventSubject)
              && event.getDateTtime(true).equals(startDateTtime)
              && event.getDateTtime(false).equals(endDateTtime)) {

      }
    }

  }

  @Override
  public void editEventsProperty(String prop, String eventSubject, String dateTtime,
                                 String newPropvalue) throws IllegalArgumentException {
    validateDateTtime(dateTtime);
    validateProperty(prop);
  }

  @Override
  public void editSeriesProperty(String prop, String eventSubject, String dateTtime,
                                 String newPropvalue) throws IllegalArgumentException {
    validateDateTtime(dateTtime);
    validateProperty(prop);
  }

  @Override
  public ArrayList<IEvent> getEvents(String date) throws IllegalArgumentException {
    validateDate(date);

    return null;
  }

  @Override
  public ArrayList<IEvent> getEvents(String date1, String date2) throws IllegalArgumentException {
    validateDate(date1);
    validateDate(date2);

    return null;
  }

  @Override
  public boolean showStatus(String dateTtime) throws IllegalArgumentException {
    validateDateTtime(dateTtime);

    return false;
  }
}
