package calendar;

/**
 * This class represents a single event. A single event can span multiple days.
 */
public class SingleEvent extends AbstractEvent {

  public SingleEvent(String subject, String startDateTTime) {
    super(subject, startDateTTime);
  }

  public SingleEvent(String subject, String startDateTtime, String endDateTTIme) {
    super(subject, startDateTtime, endDateTTIme );
  }

  @Override
  protected IEvent copy(String subject, Date startDateTtime, Date endDateTtime, String location, String description, String status) {
    return null;
  }

  @Override
  public boolean match(String subject, String startDateTtime) {
    return false;
  }

  @Override
  public boolean isSeries() {
    return false;
  }

  private Date[] weekDaysToDates(char[] weekdays) {

  }
}
