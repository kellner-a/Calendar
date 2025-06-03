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
  protected int[] times; //array of start/end hours and minutes
  // {startHour, startMinute, endHour, endMinute}
  protected String location;
  protected String subject;
  protected String description;
  protected boolean status; // public or private


  /**
   * Constructs an all day event with a subject and a start date.
   * @param subject subject of the event
   * @param startDateTTime the start date and start time as a string
   */
  public AbstractEvent(String subject, String startDateTTime) {
    this.subject = subject;
    String[] startString = startDateTTime.split("T");
    this.startDate = new Date(startString[0]); // makes a new date w the start time
    this.times = new int[] {8, 0 , 17, 0};
  }

  public AbstractEvent(String subject, String startDateTTime, String endDateTTime) {
    this.subject = subject;
    String[] startString = startDateTTime.split("T");
    String[] endString = endDateTTime.split("T");

    String[] startTimeString = startString[1].split(":");
    String[] endTimeString = endString[1].split(";");

    this.startDate = new Date(startString[0]);
    this.endDate = new Date(endString[0]);

    this.times = new int[] {Integer.valueOf(startTimeString[0]),
            Integer.valueOf(startTimeString[1]),
            Integer.valueOf(endTimeString[0]),
            Integer.valueOf(endTimeString[1])};

  }

  @Override
  public IEvent editProperty(String prop, String eventSubject, String startDateStringTtimeString, String endDateStringTtimeString, String newPropvalue) {
    return null;
  }

  @Override
  public IEvent editProperty(String prop, String eventSubject, String dateStringTtimeString, String newPropvalue) {
    return null;
  }

  @Override
  public boolean isBusy(String dateStringTtimeString) {
    return false;
  }

  @Override
  public String toString() {
    return "";
  }
}
