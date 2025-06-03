package calendar;

/**
 * This class represents an event. All events have these variables and methods whether or not
 * they are a single event or a series. All events have at minimum a startDate, subject, and
 * start. Default status is public. If no start or end time is inputted, they default to an 8 am
 * start time and a 5 pm end time, also specified as an all day event.
 */
public class AbstractEvent implements IEvent {

  protected Date startDateString;
  protected Date endDateString;
  protected String location;
  protected String subject;
  protected String description;
  protected boolean status; // public or private

  public AbstractEvent(String subject, String startDateString) {
    String[] dateString = startDateString.split("T");
    this.startDateString = new Date(dateString[0]);
    if (dateString.length > 1) {
      this.endDateString = new Date(dateString[1]);
    }
  }

  public AbstractEvent(String subject, String startDateString, String endDateString) {

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
