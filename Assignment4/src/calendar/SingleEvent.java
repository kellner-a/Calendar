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

  SingleEvent(String subject, String startDateTtime, String endDateTtime, String location,
              String description, String status) {
    super(subject, startDateTtime, endDateTtime);
    this.location = location;
    this.description = description;
    this.status = status;
  }

  @Override
  public boolean isSeries() {
    return false;
  }
}
