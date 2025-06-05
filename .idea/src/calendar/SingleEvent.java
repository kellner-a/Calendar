package calendar;

/**
 * This class represents a single event. A single event can span multiple days.
 */
public class SingleEvent extends AbstractEvent {

  public SingleEvent(String subject, String startDateTTime) {
    super(subject, startDateTTime);
  }

  public SingleEvent(String subject, String startDateTTime, String endDateTTIme,
                     char[] weekdays, int numTimes) {
    super(subject, startDateTTIme, endDateTTIme );
  }

  private Date[] weekDaysToDates(char[] weekdays) {

  }
}
