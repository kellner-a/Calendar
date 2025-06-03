package calendar;

import java.util.ArrayList;

public interface ICalendar {

  /**
   * Returns a list of events on the given day with their subject, start and end time, and
   * location (if any).
   *
   * @param dateString
   * @return a list of events
   */
  public ArrayList<IEvent> getEvents(String dateString);

  /**
   * Returns a list of events planned between two date times.
   *
   * @param dateString1 the beginning date time
   * @param dateString2 the ending date time
   * @return a list of events
   */

  public ArrayList<IEvent> getEvents(String dateString1, String dateString2);

  /**
   * Returns the true if this calendar has an event during the given dateStringTtimeString.
   *
   * @param dateStringTtimeString
   * @return boolean
   */
  public boolean showStatus(String dateStringTtimeString);
}
