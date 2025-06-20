package calendar;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface for all Calendars. All calendars can have single events and even series. This class
 * holds functionality that all Calendars are capable of. A dateString is formatted "YYYY-MM-DD".
 * A timeString is formatted "hh:mm". A dateStringTtimestring is formatted "YYYY-MM-DDThh:mm".
 * Weekdays are a set of characters where 'M' is Monday, 'T' is Tuesday, 'W' is Wednesday, 'R' is
 * Thursday, 'F' is Friday, 'S' is Saturday, and 'U' is Sunday. A weekday input can be "MWR",
 * "TFS", "FSU". A weekday is order specific, so "FUS" is not valid.
 */
public interface ICalendar {

  /**
   * Adds the given event to this calendar.
   *
   * @param event IEvent
   */
  void addEvent(IEvent event);

  /**
   * Creates a single event in this calendar.
   *
   * @param subject        String
   * @param startDateTtime "YYYY-MM-DDThh:mm"
   * @param endDateTtime   "YYYY-MM-DDThh:mm"
   * @throws IllegalArgumentException when input doesn't match specified pattern, endDateTtime is
   *                                  before startDateTtime
   */
  void createSingleEvent(String subject, String startDateTtime,
                         String endDateTtime) throws IllegalArgumentException;

  /**
   * Creates an event series that repeats N times on specific weekdays.
   *
   * @param subject        String
   * @param startDateTtime "YYYY-MM-DDThh:mm"
   * @param endDateTtime   "YYYY-MM-DDThh:mm"
   * @param weekdays       MTWRFSU
   * @param timesRepeated  non-zero positive integer
   * @throws IllegalArgumentException when input doesn't match specified pattern, endDateTtime is
   *                                  before startDateTtime
   */
  void createEventSeriesTimesRepeated(String subject, String startDateTtime, String endDateTtime,
                                      String weekdays, int timesRepeated)
          throws IllegalArgumentException;

  /**
   * Creates an event series that repeats on specific weekdays until the given stopDate.
   *
   * @param subject        String
   * @param startDateTtime "YYYY-MM-DDThh:mm"
   * @param endDateTtime   "YYYY-MM-DDThh:mm"
   * @param weekdays       MTWRFSU
   * @param stopDate       "YYYY-MM-DD"
   * @throws IllegalArgumentException when input doesn't match specified pattern, endDateTtime is
   *                                  before startDateTtime, stopDate is before startDateTtime
   */
  void createEventSeriesStopDate(String subject, String startDateTtime, String endDateTtime,
                                 String weekdays, String stopDate)
          throws IllegalArgumentException;

  /**
   * Creates a single all day event.
   *
   * @param subject String
   * @param date    "YYYY-MM-DD"
   * @throws IllegalArgumentException when input doesn't match specified pattern
   */
  void createSingleAllDayEvent(String subject, String date) throws IllegalArgumentException;

  /**
   * Creates an event series of all day events that repeat N times on specific weekdays.
   *
   * @param subject       String
   * @param startDate     "YYYY-MM-DD"
   * @param weekdays      MTWRFSU
   * @param timesRepeated non-zero positive integer
   * @throws IllegalArgumentException when input doesn't match specified pattern
   */
  void createAllDayEventSeriesTimesRepeated(String subject, String startDate, String weekdays,
                                            int timesRepeated) throws IllegalArgumentException;

  /**
   * Creates an event series of all day events that repeat on specific weekdays until the given
   * stopDate.
   *
   * @param subject   String
   * @param startDate "YYYY-MM-DD"
   * @param weekdays  MTWRFSU
   * @param stopDate  "YYYY-MM-DD"
   * @throws IllegalArgumentException when input doesn't match specified pattern, stopDate is
   *                                  before startDate
   */
  void createAllDayEventSeriesStopDate(String subject, String startDate, String weekdays,
                                       String stopDate)
          throws IllegalArgumentException;


  /**
   * Updates the property of the event matching the given subject and dateTtimes with the new
   * property value.
   *
   * @param prop           "subject", start", "end", "description", "location", or "status"
   * @param eventSubject   String
   * @param startDateTtime "YYYY-MM-DDThh:mm"
   * @param endDateTtime   "YYYY-MM-DDThh:mm"
   * @param newPropvalue   String
   * @throws IllegalArgumentException when input doesn't match specified pattern, startDateTtime
   *                                  is before endDateTtime, the event doesn't exist
   */
  void editEventProperty(String prop, String eventSubject, String startDateTtime,
                         String endDateTtime, String newPropvalue) throws IllegalArgumentException;

  /**
   * Updates the property of the event matching the given subject and dateTtime with the new
   * property value. If this event is part of a series, then the properties of all events in that
   * series starting at or after the given dateTtime will be updated; otherwise, this method
   * works the same as editEventProperty.
   *
   * @param prop         "subject", start", "end", "description", "location", or "status"
   * @param eventSubject String
   * @param dateTtime    "YYYY-MM-DDThh:mm"
   * @param newPropvalue String
   * @throws IllegalArgumentException when input doesn't match specified pattern, startDateTtime is
   *                                  before endDateTtime, the event doesn't exist
   */
  void editEventsProperty(String prop, String eventSubject, String dateTtime,
                          String newPropvalue) throws IllegalArgumentException;

  /**
   * Updates the property of the event matching the given subject and dateTtime with the new
   * property value. The properties of all events in the given series; otherwise, this method
   * works the same as editEventProperty.
   *
   * @param prop         "subject", start", "end", "description", "location", or "status"
   * @param eventSubject String
   * @param dateTtime    "YYYY-MM-DDThh:mm"
   * @param newPropvalue String
   * @throws IllegalArgumentException when input doesn't match specified pattern, startDateTtime
   *                                  is before endDateTtime, the event doesn't exist
   */
  void editSeriesProperty(String prop, String eventSubject, String dateTtime,
                          String newPropvalue) throws IllegalArgumentException;


  /**
   * Returns a list of events on the given day.
   *
   * @param date "YYYY-MM-DD"
   * @return a list of events
   * @throws IllegalArgumentException when input doesn't match specified pattern
   */
  ArrayList<IEvent> getEvents(String date) throws IllegalArgumentException;

  /**
   * Returns a list of events planned between two dateTtimes.
   *
   * @param startDateTtime "YYYY-MM-DDThh:mm"
   * @param endDateTtime   "YYYY-MM-DDThh:mm"
   * @return a list of events
   * @throws IllegalArgumentException when input doesn't match specified pattern
   */
  ArrayList<IEvent> getEvents(String startDateTtime, String endDateTtime)
          throws IllegalArgumentException;

  /**
   * Returns the true if this calendar has an event during the given dateTtime.
   *
   * @param dateTtime "YYYY-MM-DD"
   * @return boolean
   * @throws IllegalArgumentException when input doesn't match specified pattern
   */
  boolean showStatus(String dateTtime) throws IllegalArgumentException;

  /**
   * Returns the list of events from this calendar.
   *
   * @return list of events
   */

  List<IEvent> getCalendarEvents();

  /**
   * Returns all events between two dates from the calendar formatted as a string.
   *
   * @param startDateTtime "YYYY-MM-DDThh:mm"
   * @param endDateTtime   "YYYY-MM-DDThh:mm"
   * @return string
   */

  String getEventsToString(String startDateTtime, String endDateTtime);

  /**
   * Finds the first 10 events from this calendar that occur on or after a given
   * start date.
   *
   * @param startDateString a string that represents a date YYYY-MM-DD
   * @return list of events
   */

  ArrayList<IEvent> getFirstTen(String startDateString);

  /**
   * Sorts the events in this calendar by their date.
   */

  void sortEvents();
}
