package calendar;

import java.util.ArrayList;

/**
 * This class represents a series of events. A series of events repeat for a specified number of
 * days--recurring on specific days of the week and/or a specific number of times. If a series is
 * set to repeat a specific number of times with no specific days, the event will recur on that
 * day every week for the specified number of times.
 */
public class SeriesEvent extends AbstractEvent {

  private ArrayList<IDate> recurringDates;
  private String weekdays;
  private int timesRepeated; // -1 when unused
  private IDate stopDate; // null when unused

  /**
   * Constructs an event that repeats on given weekdays a given number of times.
   *
   * @param subject
   * @param startDateTtime
   * @param endDateTtime
   * @param weekdays
   * @param timesRepeated
   */
  public SeriesEvent(String subject, String startDateTtime, String endDateTtime, String weekdays,
                     int timesRepeated) {
    super(subject, startDateTtime, endDateTtime);
    this.weekdays = weekdays;
    this.timesRepeated = timesRepeated;
    this.stopDate = null;
  }

  /**
   * Constructs an event that repeats on given weekdays until a given stop date.
   *
   * @param subject name of the event
   * @param startDateTtime start date and start time
   * @param endDateTtime end date and end time
   * @param weekdays the days of the week that the event repeats on
   * @param stopDate the date that the event stops repeating
   */
  public SeriesEvent(String subject, String startDateTtime, String endDateTtime, String weekdays,
                     String stopDate) {
    super(subject, startDateTtime, endDateTtime);
    this.weekdays = weekdays;
    this.timesRepeated = -1;
    this.stopDate = stopDate;
  }

  /**
   * Constructs an all day event that repeats on given weekdays a given number of times.
   *
   * @param subject name of the event
   * @param startDate the date that the event starts
   * @param weekdays the days of the week that the event repeats on
   * @param timesRepeated number of times the events repeat for the weekdays
   */
  public SeriesEvent(String subject, String startDate, String weekdays, int timesRepeated) {
    super(subject, startDate);
    this.weekdays = weekdays;
    this.timesRepeated = timesRepeated;
    this.stopDate = null;
  }

  /**
   * Constructs an all day event that repeats on given weekdays until a given stop date.
   * @param subject
   * @param startDateTtime
   * @param endDateTtime
   * @param weekdays
   * @param stopDate
   */
  public SeriesEvent(String subject, String startDate, String weekdays, String stopDate) {
    super(subject, startDate);
    this.weekdays = weekdays;
    this.timesRepeated = -1;
    this.stopDate = stopDate;
  }

  private void reccuringDates() {
    char[] daysOfWeek = new char[] {'M', 'T', 'W', 'R', 'F', 'S', 'U'};

    if(this.timesRepeated > 0) {
      for(int i = 0; i < this.timesRepeated; i++) {

      }
    }
  }

  /**
   * Returns a dateTtime string of the event at the given index.
   *
   * @param eventIndex positive integer
   * @param isStart boolean, true if getting startDateTtime,
   * @return String
   */
  private String getDateTtime(int eventIndex, boolean isStart) {
    if (isStart) {
      return this.recurringDates[eventIndex].toString() + "T" +  String.format("%02d",this.times[0])
              +":"+ String.format("%02d", this.times[1]);
    } else {
      return this.recurringDates[eventIndex].toString() +  "T" + String.format("%02d",this.times[2])
              +":"+ String.format("%02d", this.times[3]);
    }
  }

  @Override
  public boolean match(String subject, String startDateTtime, String endDateTtime) {
    if (!this.subject.equals(subject)) {
      return false;
    }
    for (int i = 0; i < this.recurringDates; i++) {
      if (getDateTtime(i, true).equals(startDateTtime)
              && getDateTtime(i, false).equals(endDateTtime)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean isSeries() {
    return true;
  }

}
