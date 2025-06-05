package calendar;

import java.util.ArrayList;
import java.util.Arrays;

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
    super.initializeOtherProperties();
  }

  /**
   * Constructs an event that repeats on given weekdays until a given stop date.
   *
   * @param subject        name of the event
   * @param startDateTtime start date and start time
   * @param endDateTtime   end date and end time
   * @param weekdays       the days of the week that the event repeats on
   * @param stopDate       the date that the event stops repeating
   */
  public SeriesEvent(String subject, String startDateTtime, String endDateTtime, String weekdays,
                     String stopDate) {
    super(subject, startDateTtime, endDateTtime);
    this.weekdays = weekdays;
    this.timesRepeated = -1;
    this.stopDate = new Date(stopDate);
    super.initializeOtherProperties();
  }

  /**
   * Constructs an all day event that repeats on given weekdays a given number of times.
   *
   * @param subject       name of the event
   * @param startDate     the date that the event starts
   * @param weekdays      the days of the week that the event repeats on
   * @param timesRepeated number of times the events repeat for the weekdays
   */
  public SeriesEvent(String subject, String startDate, String weekdays, int timesRepeated) {
    super(subject, startDate);
    this.weekdays = weekdays;
    this.timesRepeated = timesRepeated;
    this.stopDate = null;
    super.initializeOtherProperties();
  }

  /**
   * Constructs an all day event that repeats on given weekdays until a given stop date.
   *
   * @param subject
   * @param startDate
   * @param weekdays
   * @param stopDate
   */
  public SeriesEvent(String subject, String startDate, String weekdays, String stopDate) {
    super(subject, startDate);
    this.weekdays = weekdays;
    this.timesRepeated = -1;
    this.stopDate = new Date(startDate);
    super.initializeOtherProperties();
  }

  private SeriesEvent(String subject, String startDateTtime, String endDateTtime, String weekdays
          , int timesRepeated, String stopDate, String location, String description,
                      String status) {
    super(subject, startDateTtime, endDateTtime);
    this.weekdays = weekdays;
    this.timesRepeated = timesRepeated;
    this.stopDate = new Date(stopDate);
    this.location = location;
    this.description = description;
    this.status = status;
  }

  private void reccuringDates() {
    ArrayList<String> week = new ArrayList<String>(Arrays.asList("M", "T", "W", "R", "F", "S", "U"));
    String[] daysThatRecur = this.weekdays.split("(?!^)");
    IDate result = this.startDate;
    int k = 0;

    if (this.timesRepeated > 0) {
      for (int i = 0; i < this.timesRepeated; i++) {
        for (int j = 0; j < daysThatRecur.length; j++) {
          if (week.indexOf(daysThatRecur[j]) < week.indexOf(result.dayOfWeek)) {
            // does nothing because the day that the event should occur is before the startdate
          }
          else if (week.indexOf(result.dayOfWeek) == week.indexOf(daysThatRecur[j])) {
            this.recurringDates.add(result);
          } else if (week.indexOf(daysThatRecur[j]) > week.indexOf(result.dayOfWeek)) {
            result = result.getNextDate(week.indexOf(daysThatRecur[0]) - week.indexOf(result.dayOfWeek));
            this.recurringDates.add(result);
          }
        }
      }
    }
    else {
      while(result.compare(this.stopDate) <= 0) {
        if(k == daysThatRecur.length) {
          k = 0;
        }
        if (week.indexOf(daysThatRecur[k]) < week.indexOf(result.dayOfWeek)) {
          // does nothing because the day that the event should occur is before the startdate
        }
        else if (week.indexOf(result.dayOfWeek) == week.indexOf(daysThatRecur[j])) {
          this.recurringDates.add(result);
        } else if (week.indexOf(daysThatRecur[k]) > week.indexOf(result.dayOfWeek)) {
          result = result.getNextDate(week.indexOf(daysThatRecur[0]) - week.indexOf(result.dayOfWeek));
          this.recurringDates.add(result);
        }
      }
      k++;
    }


  }

  /**
   * Returns a dateTtime string of the event at the given index.
   *
   * @param eventIndex positive integer
   * @param isStart    boolean, true if getting startDateTtime,
   * @return String
   */
  private String getDateTtime(int eventIndex, boolean isStart) {
    if (isStart) {
      return this.recurringDates.get(eventIndex).toString() + "T" +  String.format("%02d",
              this.times[0]) +":"+ String.format("%02d", this.times[1]);
    } else {
      return this.recurringDates.get(eventIndex).toString() +  "T" + String.format("%02d",
              this.times[2]) +":"+ String.format("%02d", this.times[3]);
    }
  }

  @Override
  protected IEvent copy(String subject, String startDateTtime, String endDateTtime,
                        String location, String description, String status) {

    return null;
  }

  @Override
  public boolean match(String subject, String startDateTtime, String endDateTtime) {
    if (!this.subject.equals(subject)) {
      return false;
    }
    for (int i = 0; i < this.recurringDates.size(); i++) {
      if (getDateTtime(i, true).equals(startDateTtime)
              && (getDateTtime(i, false).equals(endDateTtime) || endDateTtime.isEmpty())) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean match(String subject, String startDateTtime) {
    return false;
  }

  @Override
  public IEvent editEventProperty(String prop, String dateTtime, String newPropvalue) {
    String[] dateTime = dateTtime.split("T");
    int eventIndex = -1;
    for (int i = 0; i < this.recurringDates.size(); i++) {
      if (this.recurringDates.get(i).compare(new Date(dateTime[0])) == 0) {
        eventIndex = i;
      }
    }
    IEvent temp;
    if (prop.equals("subject")) {
      temp = super.copy(newPropvalue, this.getDateTtime(eventIndex, true),
              this.getDateTtime(eventIndex, false),
              this.location, this.description, this.status);
      this.recurringDates.remove(eventIndex);
      return temp;
    } else if (prop.equals("start")) {
      temp =  super.copy(this.subject, this.getDateTtime(eventIndex, true),
              this.getDateTtime(eventIndex, false), this.location,
              this.description, this.status);
      this.recurringDates.remove(eventIndex);
      return temp;
    } else if (prop.equals("end")) {
      temp = super.copy(this.subject, this.getDateTtime(eventIndex, true),
              this.getDateTtime(eventIndex, false), this.location,
              this.description, this.status);
      this.recurringDates.remove(eventIndex);
      return temp;
    } else if (prop.equals("description")) {
      temp =  super.copy(this.subject, this.getDateTtime(eventIndex, true),
              this.getDateTtime(eventIndex, false), this.location,
              newPropvalue, this.status);
      this.recurringDates.remove(eventIndex);
      return temp;
    } else if (prop.equals("location")) {
      temp = super.copy(this.subject, this.getDateTtime(eventIndex, true),
              this.getDateTtime(eventIndex, false), newPropvalue,
              this.description, this.status);
      this.recurringDates.remove(eventIndex);
      return temp;
    } else if (prop.equals("status")) {
      temp = super.copy(this.subject, this.getDateTtime(eventIndex, true),
              this.getDateTtime(eventIndex, false), this.location,
              this.description, newPropvalue);
      this.recurringDates.remove(eventIndex);
      return temp;
    }
    throw new IllegalArgumentException("Invalid property");
  }

  @Override
  public IEvent editEventsProperty(String prop, String dateTtime, String newPropvalue) {

    return copy();
  }

  @Override
  public IEvent editSeriesProperty(String prop, String newPropvalue) {

    return copy();
  }

  @Override
  public boolean isSeries() {
    return true;
  }

}
