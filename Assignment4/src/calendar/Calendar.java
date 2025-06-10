package calendar;

import java.util.ArrayList;
import java.util.List;
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

  private final List<IEvent> events;

  /**
   * Constructor for a calendar. Takes in a dateString as the current date and initializes its
   * list of events.
   *
   * @param date "YYYY-M(M)-D(D)"
   * @throws IllegalArgumentException when date format isn't matched
   */
  public Calendar(String date) throws IllegalArgumentException {
    validateDate(date);
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
   * @param prop String
   */
  private void validateProperty(String prop) throws IllegalArgumentException {
    if (!prop.equals("subject") && !prop.equals("start") && !prop.equals("end") && !prop.equals(
            "description") && !prop.equals("location") && !prop.equals("status")) {
      throw new IllegalArgumentException("Invalid property");
    }
  }

  /**
   * Returns the event matching the subject, startDateTtime, and endDateTtime. Throws an error if
   * no such event exists.
   *
   * @param subject        String
   * @param startDateTtime dateTtime
   * @param endDateTtime   dateTtime
   * @return IEvent, Single or Series
   * @throws IllegalArgumentException when no event matches the given input
   */
  private IEvent findEvent(String subject, String startDateTtime, String endDateTtime)
          throws IllegalArgumentException {
    IEvent temp = null;
    for (IEvent event : this.events) {
      if (event.match(subject, startDateTtime, endDateTtime)) {
        temp = event;
        break;
      }
    }
    if (temp == null) {
      throw new IllegalArgumentException("Event does not exist");
    }
    return temp;
  }

  @Override
  public void createSingleEvent(String subject, String startDateTtime, String endDateTtime)
          throws IllegalArgumentException {
    validateDateTtime(startDateTtime);
    validateDateTtime(endDateTtime);
    this.events.add(new SingleEvent(subject, startDateTtime, endDateTtime));
  }

  @Override
  public void createEventSeriesTimesRepeated(String subject, String startDateTtime,
                                             String endDateTtime, String weekdays,
                                             int timesRepeated)
          throws IllegalArgumentException {
    if (timesRepeated <= 0) {
      throw new IllegalArgumentException("Invalid times to repeat");
    }
    validateDateTtime(startDateTtime);
    validateDateTtime(endDateTtime);
    validateWeekdays(weekdays);
    this.events.add(new SeriesEvent(subject, startDateTtime, endDateTtime, weekdays,
            timesRepeated));
  }

  @Override
  public void createEventSeriesStopDate(String subject, String startDateTtime,
                                        String endDateTtime, String weekdays, String stopDate)
          throws IllegalArgumentException {
    validateDateTtime(startDateTtime);
    validateDateTtime(endDateTtime);
    validateWeekdays(weekdays);
    validateDate(stopDate);
    this.events.add(new SeriesEvent(subject, startDateTtime, endDateTtime, weekdays, stopDate));
  }

  @Override
  public void createSingleAllDayEvent(String subject, String date)
          throws IllegalArgumentException {
    validateDate(date);
    this.events.add(new SingleEvent(subject, date));
  }

  @Override
  public void createAllDayEventSeriesTimesRepeated(String subject, String startDate,
                                                   String weekdays, int timesRepeated)
          throws IllegalArgumentException {
    if (timesRepeated <= 0) {
      throw new IllegalArgumentException("Invalid times to repeat");
    }
    validateDate(startDate);
    validateWeekdays(weekdays);
    this.events.add(new SeriesEvent(subject, startDate, weekdays, timesRepeated));
  }

  @Override
  public void createAllDayEventSeriesStopDate(String subject, String startDate, String weekdays,
                                              String stopDate)
          throws IllegalArgumentException {
    validateDate(startDate);
    validateWeekdays(weekdays);
    validateDate(stopDate);
    this.events.add(new SeriesEvent(subject, startDate, weekdays, stopDate));
  }

  @Override
  public void editEventProperty(String prop, String eventSubject, String startDateTtime,
                                String endDateTtime, String newPropvalue)
          throws IllegalArgumentException {
    validateDateTtime(startDateTtime);
    validateDateTtime(endDateTtime);
    validateProperty(prop);
    IEvent event = findEvent(eventSubject, startDateTtime, endDateTtime);
    IEvent edited = event.editEventProperty(prop, startDateTtime, newPropvalue);
    if (!event.equals(edited)) {
      this.events.add(edited);
    }
  }

  @Override
  public void editEventsProperty(String prop, String eventSubject, String dateTtime,
                                 String newPropvalue) throws IllegalArgumentException {
    validateDateTtime(dateTtime);
    validateProperty(prop);
    IEvent event = findEvent(eventSubject, dateTtime, "");
    IEvent edited = event.editEventsProperty(prop, dateTtime, newPropvalue);
    if (!event.equals(edited)) {
      this.events.add(edited);
    }
  }

  @Override
  public void editSeriesProperty(String prop, String eventSubject, String dateTtime,
                                 String newPropvalue) throws IllegalArgumentException {
    validateDateTtime(dateTtime);
    validateProperty(prop);
    IEvent event = findEvent(eventSubject, dateTtime, "");
    IEvent edited = event.editSeriesProperty(prop, newPropvalue);
    if (!event.equals(edited)) {
      this.events.add(edited);
    }
  }

  @Override
  public ArrayList<IEvent> getEvents(String date) throws IllegalArgumentException {
    validateDate(date);
    ArrayList<IEvent> events = new ArrayList<>();
    IDate day = new Date(date);
    for (IEvent event : this.events) {
      IEvent temp = event.sameDay(day);
      if (temp != null) {
        events.add(temp);
      }
    }
    return events;
  }

  @Override
  public ArrayList<IEvent> getEvents(String startDateTtime, String endDateTtime)
          throws IllegalArgumentException {
    validateDateTtime(startDateTtime);
    validateDateTtime(endDateTtime);
    String[] startDateTime = startDateTtime.split("T");
    IDate start = new Date(startDateTime[0]);
    int startTime = Integer.parseInt(startDateTime[1].replace(":", ""));
    String[] endDateTime = endDateTtime.split("T");
    IDate end = new Date(endDateTime[0]);
    int endTime = Integer.parseInt(endDateTime[1].replace(":", ""));

    IEvent temp;
    IDate tracker = new Date(startDateTime[0]);
    ArrayList<IEvent> events = new ArrayList<>();
    while (tracker.compare(end) <= 0) {
      for (IEvent event : this.events) {
        temp = event.sameDay(tracker);
        if (temp != null) {
          if (tracker.compare(start) == 0) {
            int tempStartTime = Integer.parseInt(temp.getStartTime().replace(":", ""));
            int tempEndTime = Integer.parseInt(temp.getEndTime().replace(":", ""));
            if (tempStartTime >= startTime || tempEndTime >= startTime) {
              events.add(temp);
            }
          } else if (tracker.compare(end) == 0) {
            int tempStartTime = Integer.parseInt(temp.getStartTime().replace(":", ""));
            int tempEndTime = Integer.parseInt(temp.getEndTime().replace(":", ""));
            if (tempStartTime <= endTime || tempEndTime <= endTime) {
              events.add(temp);
            }
          } else {
            events.add(temp);
          }
        }
      }
      tracker = tracker.getNextDate(1);
    }
    return events;
  }

  @Override
  public boolean showStatus(String dateTtime) throws IllegalArgumentException {
    validateDateTtime(dateTtime);
    for (IEvent event : this.events) {
      if (event.isBusy(dateTtime)) {
        return true;
      }
    }
    return false;
  }
}
