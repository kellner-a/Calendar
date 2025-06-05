package calendar;

/**
 * This class represents an event. All events have these variables and methods whether or not
 * they are a single event or a series. All events have at minimum a startDate, subject, and
 * start. Default status is public. If no start or end time is inputted, they default to an 8 am
 * start time and a 5 pm end time, also specified as an all day event.
 */
public abstract class AbstractEvent implements IEvent {

  protected Date startDate;
  protected Date endDate;
  protected int[] times = new int[4]; //array of start/end hours and minutes
  // {startHour, startMinute, endHour, endMinute}
  protected String location;
  protected String subject;
  protected String description;
  protected String status; // public or private


  /**
   * Constructs an all day event with a subject and a start date.
   *
   * @param subject subject of the event
   * @param startDateTTime the start date and start time as a string
   */
  public AbstractEvent(String subject, String startDateTTime) {
    this.subject = subject;
    String[] startString = startDateTTime.split("T");
    this.startDate = new Date(startString[0]); // makes a new date w the start time
    this.endDate = new Date(startString[0]);
    this.times = new int[] {8, 0 , 17, 0};
    initializeOtherProperties();
  }

  public AbstractEvent(String subject, String startDateTTime, String endDateTTime) {
    this.subject = subject;
    parseDateTtime(startDateTTime, true);
    parseDateTtime(endDateTTime, false);
    initializeOtherProperties();
  }

  protected void initializeOtherProperties() {
    this.location = "";
    this.description = "";
    this.status = "";
  }

  /**
   * Creates a copy of this event.
   * 
   * @param subject String
   * @param startDate Date
   * @param endDate Date
   * @param times int[4] 
   * @param location String
   * @param description String
   * @param status String
   * @return IEvent
   */
  protected abstract IEvent copy(String subject, Date startDate, Date endDate, int[] times,
                          String location, String description, String status);

  /**
   * Parses the given dateTtime and initializes the startDateTtime or endDateTtime based on the
   * isStart value. True initializes startDateTtime, false initiliazes endDateTtime.
   *
   * @param dateTtime "YYYY-MM-DDThh:mm"
   * @param isStart true or false
   */
  protected void parseDateTtime(String dateTtime, boolean isStart) {
    if (isStart) {
      String[] startString = dateTtime.split("T");
      String[] startTimeString = startString[1].split(":");
      this.startDate = new Date(startString[0]);
      this.times[0] = Integer.parseInt(startTimeString[0]);
      this.times[1] = Integer.parseInt(startTimeString[1]);
    } else {
      String[] endString = dateTtime.split("T");
      String[] endTimeString = endString[1].split(";");
      this.endDate = new Date(endString[0]);
      this.times[0] = Integer.parseInt(endTimeString[0]);
      this.times[1] = Integer.parseInt(endTimeString[1]);
    }
  }

  /**
   * Returns this events start or end dateTtime. True returns the start, false returns the end.
   *
   * @param getStart true or false
   * @return "YYYY-MM-DDThh:mm"
   */
  protected String getDateTtime(boolean getStart) {
    if (getStart) {
      return startDate +"T"+ String.format("%02d",this.times[0]) +":"+ String.format("%02d",
              this.times[1]);
    } else {
      return endDate +"T"+ String.format("%02d",this.times[2]) +":"+ String.format("%02d",
              this.times[3]);
    }
  }

  @Override
  public boolean match(String subject, String startDateTtime, String endDateTtime) {
    return this.subject.equals(subject) && getDateTtime(true).equals(startDateTtime)
            && (getDateTtime(false).equals(endDateTtime) || endDateTtime.isEmpty());
  }

  @Override
  public IEvent editEventProperty(String prop, String newPropvalue) {
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
    return copy(this.subject, startDate, endDate, this.times, this.location,
            this.description, this.status);
  }

  @Override
  public IEvent editEventsProperty(String prop, String dateTtime, String newPropvalue) {
    return editEventProperty(prop, newPropvalue);
  }

  @Override
  public IEvent editSeriesProperty(String prop, String newPropvalue) {
    return editEventProperty(prop, newPropvalue);
  }

  @Override
  public boolean isBusy(String dateTtime) {
    String[] dateTime = dateTtime.split("T");
    String[] timeString = dateTime[1].split(":");
    int[] time = new int[2];
    time[0] = Integer.parseInt(timeString[0]);
    time[1] = Integer.parseInt(timeString[1]);
    IDate date = new Date(dateTime[0]);
    if (date.compare(this.startDate) == 0 && date.compare(this.endDate) == 0) {
      return this.times[0] <= time[0] && time[0] <= this.times[2] && this.times[1] <= time[1]
              && time[1] <= this.times[3];
    }
    return date.compare(startDate) >= 0 && date.compare(endDate) <= 0;
  }

  @Override
  public abstract boolean isSeries();

  @Override
  public String toString() {
    String event =
            this.subject+": "+ String.valueOf(this.times[0])+":"+String.valueOf(this.times[1]) +
                    " - "+String.valueOf(this.times[2]) +":"+String.valueOf(this.times[3]);
    if (this.location.isEmpty()){
      return event;
    }
    return event +" @ "+ this.location;
  }
}
