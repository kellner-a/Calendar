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
   * @param subject String
   * @param startDateTtime "YYYY-MM-DDThh:mm"
   * @param endDateTtime "YYYY-MM-DDThh:mm"
   * @param weekdays MTWRFSU
   * @param timesRepeated non-zero positive integer
   */
  public SeriesEvent(String subject, String startDateTtime, String endDateTtime, String weekdays,
                     int timesRepeated) {
    super(subject, startDateTtime, endDateTtime);
    this.weekdays = weekdays;
    this.timesRepeated = timesRepeated;
    this.stopDate = null;
    super.initializeOtherProperties();
    this.recurringDates();
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
    this.recurringDates();
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
    this.recurringDates();
  }

  /**
   * Constructs an all day event that repeats on given weekdays until a given stop date.
   *
   * @param subject String
   * @param startDate "YYYY-MM-DD"
   * @param weekdays MTWRFSU
   * @param stopDate "YYYY-MM-DD"
   */
  public SeriesEvent(String subject, String startDate, String weekdays, String stopDate) {
    super(subject, startDate);
    this.weekdays = weekdays;
    this.timesRepeated = -1;
    this.stopDate = new Date(startDate);
    super.initializeOtherProperties();
    this.recurringDates();
  }

  /**
   * Constructs a new Series Event.
   *
   * @param subject String
   * @param startDateTtime "YYYY-MM-DDThh:mm"
   * @param endDateTtime "YYYY-MM-DDThh:mm"
   * @param weekdays MTWRFSU
   * @param timesRepeated integer
   * @param stopDate Date
   * @param location String
   * @param description String
   * @param status String
   * @param recurringDates ArrayList of Dates
   */
  private SeriesEvent(String subject, String startDateTtime, String endDateTtime, String weekdays,
                      int timesRepeated, IDate stopDate, String location, String description,
                      String status, ArrayList<IDate> recurringDates) {
    super(subject, startDateTtime, endDateTtime);
    this.recurringDates = new ArrayList<IDate>(recurringDates);
    this.weekdays = weekdays;
    this.timesRepeated = timesRepeated;
    this.stopDate = stopDate;
    this.location = location;
    this.description = description;
    this.status = status;
  }

  private void recurringDates() {
    this.recurringDates = new ArrayList<IDate>();
    ArrayList<String> week = new ArrayList<String>(Arrays.asList("M", "T", "W", "R", "F", "S", "U"));
    String[] daysThatRecur = this.weekdays.split("(?!^)");
    Date result = this.startDate;
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
        else if (week.indexOf(result.dayOfWeek) == week.indexOf(daysThatRecur[k])) {
          this.recurringDates.add(result);
        } else if (week.indexOf(daysThatRecur[k]) > week.indexOf(result.dayOfWeek)) {
          result =
                  result.getNextDate(week.indexOf(daysThatRecur[0]) - week.indexOf(result.dayOfWeek) );
          this.recurringDates.add(result);
        }
        k++;
      }
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
  public IEvent sameDay(IDate date) {
    for (int i = 0; i < this.recurringDates.size(); i++) {
      if (this.recurringDates.get(i).compare(date) == 0) {
        return copy(this.subject, this.getDateTtime(i, true), this.getDateTtime(i, false),
                this.location, this.description, this.status);
      }
    }
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
  public IEvent editEventProperty(String prop, String dateTtime, String newPropvalue) {
    String[] dateTime = dateTtime.split("T");
    int eventIndex = -1;
    for (int i = 0; i < this.recurringDates.size(); i++) {
      if (this.recurringDates.get(i).compare(new Date(dateTime[0])) == 0) {
        eventIndex = i;
      }
    }
    IEvent temp;
    switch (prop) {
      case "subject":
        temp = super.copy(newPropvalue, this.getDateTtime(eventIndex, true),
                this.getDateTtime(eventIndex, false),
                this.location, this.description, this.status);
        this.recurringDates.remove(eventIndex);
        break;
      case "start":
        temp = super.copy(this.subject, this.getDateTtime(eventIndex, true),
                this.getDateTtime(eventIndex, false), this.location,
                this.description, this.status);
        this.recurringDates.remove(eventIndex);
        break;
      case "end":
        temp = super.copy(this.subject, this.getDateTtime(eventIndex, true),
                this.getDateTtime(eventIndex, false), this.location,
                this.description, this.status);
        this.recurringDates.remove(eventIndex);
        break;
      case "description":
        temp = super.copy(this.subject, this.getDateTtime(eventIndex, true),
                this.getDateTtime(eventIndex, false), this.location,
                newPropvalue, this.status);
        this.recurringDates.remove(eventIndex);
        break;
      case "location":
        temp = super.copy(this.subject, this.getDateTtime(eventIndex, true),
                this.getDateTtime(eventIndex, false), newPropvalue,
                this.description, this.status);
        this.recurringDates.remove(eventIndex);
        break;
      case "status":
        temp = super.copy(this.subject, this.getDateTtime(eventIndex, true),
                this.getDateTtime(eventIndex, false), this.location,
                this.description, newPropvalue);
        this.recurringDates.remove(eventIndex);
        break;
      default:
        throw new IllegalArgumentException("Invalid property");
    }
    return temp;
  }

  /**
   * Removes all the dates in this series following the given index, inclusively, and adds them
   * to a new ArrayList to be returned.
   *
   * @param eventIndex integer
   * @return ArrayList of Dates
   */
  private ArrayList<IDate> removeDatesAfter(int eventIndex) {
    ArrayList<IDate> temp = new ArrayList<>();
    for (int i = eventIndex; i < this.recurringDates.size(); i++) {
      temp.add(this.recurringDates.get(eventIndex));
      this.recurringDates.remove(eventIndex);
    }
    return temp;
  }

  @Override
  public IEvent editEventsProperty(String prop, String dateTtime, String newPropvalue) {
    String[] dateTime = dateTtime.split("T");
    int eventIndex = -1;
    for (int i = 0; i < this.recurringDates.size(); i++) {
      if (this.recurringDates.get(i).compare(new Date(dateTime[0])) == 0) {
        eventIndex = i;
      }
    }
    IEvent temp;
    switch (prop) {
      case "subject":
        temp = new SeriesEvent(newPropvalue, this.getDateTtime(eventIndex, true),
                this.getDateTtime(eventIndex, false), this.weekdays, this.timesRepeated,
                this.stopDate, this.location, this.description, this.status,
                this.removeDatesAfter(eventIndex));
        break;
      case "start":
        temp = new SeriesEvent(this.subject, newPropvalue,
                this.getDateTtime(eventIndex, false), this.weekdays, this.timesRepeated,
                this.stopDate, this.location, this.description, this.status,
                this.removeDatesAfter(eventIndex));
        break;
      case "end":
        temp = new SeriesEvent(this.subject, this.getDateTtime(eventIndex, true),
                newPropvalue, this.weekdays, this.timesRepeated,
                this.stopDate, this.location, this.description, this.status,
                this.removeDatesAfter(eventIndex));
        break;
      case "description":
        temp = new SeriesEvent(this.subject, this.getDateTtime(eventIndex, true),
                this.getDateTtime(eventIndex, false), this.weekdays, this.timesRepeated,
                this.stopDate, this.location, newPropvalue, this.status,
                this.removeDatesAfter(eventIndex));
        break;
      case "location":
        temp = new SeriesEvent(this.subject, this.getDateTtime(eventIndex, true),
                this.getDateTtime(eventIndex, false), this.weekdays, this.timesRepeated,
                this.stopDate, newPropvalue, this.description, this.status,
                this.removeDatesAfter(eventIndex));
        break;
      case "status":
        temp = new SeriesEvent(this.subject, this.getDateTtime(eventIndex, true),
                this.getDateTtime(eventIndex, false), this.weekdays, this.timesRepeated,
                this.stopDate, this.location, this.description, newPropvalue,
                this.removeDatesAfter(eventIndex));
        break;
      default:
        throw new IllegalArgumentException("Invalid property");
    }
    return temp;
  }

  @Override
  public IEvent editSeriesProperty(String prop, String newPropvalue) {
    switch (prop) {
      case "subject":
        this.subject = newPropvalue;
        break;
      case "start":
        parseDateTtime(newPropvalue, true);
        break;
      case "end":
        parseDateTtime(newPropvalue, false);
        break;
      case "description":
        this.description = newPropvalue;
        break;
      case "location":
        this.location = newPropvalue;
        break;
      case "status":
        this.status = newPropvalue;
        break;
    }
    return new SeriesEvent(this.subject, this.getDateTtime(0, true),
            this.getDateTtime(0, false), this.weekdays, this.timesRepeated,
            this.stopDate, this.location, this.description, this.status,
            this.recurringDates);
  }


  @Override
  public boolean isBusy(String dateTtime) {
    String[] dateTime = dateTtime.split("T");
    String[] timeString = dateTime[1].split(":");
    int[] time = new int[2];
    time[0] = Integer.parseInt(timeString[0]);
    time[1] = Integer.parseInt(timeString[1]);
    IDate date = new Date(dateTime[0]);
    for (int i = 0; i < this.recurringDates.size(); i++) {
      if (date.compare(this.recurringDates.get(i)) == 0
              && date.compare(this.recurringDates.get(i)) == 0) {
        if (this.times[0] <= time[0] && time[0] <= this.times[2] && this.times[1] <= time[1]
                && time[1] <= this.times[3]) {
          return true;
        }
      }
      if (date.compare(this.recurringDates.get(i)) >= 0
              && date.compare(this.recurringDates.get(i)) <= 0) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean isSeries() {
    return true;
  }

  public String recurringDatesAsString() {
    String result = "";
    for(int i = 0; i < this.recurringDates.size(); i++) {
      if(i == this.recurringDates.size() - 1) {
        result += this.recurringDates.get(i).toString();
      }
      else {
        result += this.recurringDates.get(i).toString() + ", ";
      }
    }
    return result;
  }

}
