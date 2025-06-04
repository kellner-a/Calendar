package calendar;

/**
 * This interface is for all events. All events can have their properties editted, and all event
 * objects have getters for their subject; start and end dateTtimes; busy status; and toString of
 * their subject, startTime, endTime, and potential location.
 */
public interface IEvent {

  /**
   * Returns the event's subject.
   *
   * @return String
   */
  public String getSubject();

  /**
   * Returns a dateTtime for either the events startDateTtime or endDateTtime.
   *
   * @param getStart true for getting the startDateTtime, false for getting the endDateTtime
   * @return dateTtime, "YYYY-MM-DDThh:mm"
   */
  public String getDateTtime(boolean getStart);

  /**
   * Updates the property of the event matching the given subject and dateStringTtimeString with
   * the new property value.
   *
   * @param prop                       String
   * @param eventSubject               String
   * @param startDateStringTtimeString String
   * @param endDateStringTtimeString   String
   * @param newPropvalue               String
   * @return IEvent
   */
  public IEvent editProperty(String prop, String eventSubject, String startDateStringTtimeString,
                             String endDateStringTtimeString, String newPropvalue);

  /**
   * Updates the property of the event matching the given subject and dateStringTtimeString with
   * the new property value.
   *
   * @param prop                  String
   * @param eventSubject          String
   * @param dateStringTtimeString String
   * @param newPropvalue          String
   * @return IEvent
   */
  public IEvent editProperty(String prop, String eventSubject, String dateStringTtimeString,
                             String newPropvalue);

  /**
   * Returns true if there is an event during the given day and time.
   *
   * @param dateStringTtimeString String
   * @return boolean
   */
  public boolean isBusy(String dateStringTtimeString);

  /**
   * Returns a string with this events subject, start and end time, and location (if any).
   *
   * @return String, "subject: startTime-endTime @ location"
   */
  @Override
  public String toString();

}
