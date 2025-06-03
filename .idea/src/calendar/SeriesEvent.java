package calendar;

import java.util.ArrayList;

/**
 * This class represents a series of events. A series of events repeat for a specified number of
 * days--recurring on specific days of the week and/or a specific number of times. If a series is
 * set to repeat a specific number of times with no specific days, the event will recur on that
 * day every week for the specified number of times.
 */
public class SeriesEvent extends AbstractEvent {

  private ArrayList<Date> recurringDates;

  public SeriesEvent(String subject, String startDateTTime, String endDateTTIme, ...) {
    super(subject, startDateTTime, endDateTTIme);
  }

  public SeriesEvent(String subject, boolean status, Date start, Date end, int num, Day[] day) {
    super(subject, start, status);
    // create arraylist of days from inputted
  }
}
