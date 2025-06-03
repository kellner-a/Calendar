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
   * @param date "YYYY-MM-DD" or "YYYY-M-D"
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
   * @throws IllegalArgumentException
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
   * @throws IllegalArgumentException
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
   * @throws IllegalArgumentException
   */
  private void validateDateTtime(String dateTtime) throws IllegalArgumentException {
    if (!Pattern.matches("^\\d\\d\\d\\d-\\d\\d?-\\d\\d?T\\d\\d?:\\d\\d?$", dateTtime)
            || dateTtime.isEmpty()) {
      throw new IllegalArgumentException("Invalid dateTtime");
    }
  }

  private void validateWeekdays(String weekdays) throws IllegalArgumentException {
    if (!Pattern.matches("^M?T?W?R?F?S?U?$", weekdays) || weekdays.isEmpty()) {
      throw new IllegalArgumentException("Invalid weekdays");
    }
  }

  @Override
  public void createSingleEvent(String subject, String startDateTtime, String endDateTtime) throws IllegalArgumentException {
    validateDateTtime(startDateTtime);
    validateDateTtime(endDateTtime);
    this.events.add(new SingleEvent(subject, startDateTtime, endDateTtime));
  }

  @Override
  public void createEventSeries(String subject, String startDateTtime, String endDateTtime, String weekdays, int timesRepeated) throws IllegalArgumentException {
    validateDateTtime(startDateTtime);
    validateDateTtime(endDateTtime);
  }

  @Override
  public void createEventSeries(String subject, String startDateTtime, String endDateTtime, String weekdays, String stopDate) throws IllegalArgumentException {

  }

  @Override
  public void createSingleEvent(String subject, String date) throws IllegalArgumentException {

  }

  @Override
  public void createEventSeries(String subject, String startDate, String weekdays, int timesRepeated) throws IllegalArgumentException {

  }

  @Override
  public void createEvent(String subject, String startDate, String weekdays, String stopDate) throws IllegalArgumentException {

  }

  @Override
  public void editEventProperty(String prop, String eventSubject, String startDateTtime, String endDateTtime, String newPropvalue) throws IllegalArgumentException {

  }

  @Override
  public void editEventsProperty(String prop, String eventSubject, String dateTtime, String newPropvalue) throws IllegalArgumentException {

  }

  @Override
  public void editSeriesProperty(String prop, String eventSubject, String dateTtime, String newPropvalue) throws IllegalArgumentException {

  }

  @Override
  public ArrayList<calendar.IEvent> getEvents(String date) throws IllegalArgumentException {
    return null;
  }

  @Override
  public ArrayList<calendar.IEvent> getEvents(String date1, String date2) throws IllegalArgumentException {
    return null;
  }

  @Override
  public boolean showStatus(String dateTtime) throws IllegalArgumentException {
    return false;
  }
}
