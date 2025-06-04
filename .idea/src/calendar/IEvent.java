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
   * Updates the property of the event with the new property value.
   *
   * @param prop         String
   * @param newPropvalue String
   * @return IEvent
   */
  public void editProperty(String prop, String newPropvalue);

  /**
   * Returns true if there is an event during the given day and time.
   *
   * @param dateTtime String
   * @return boolean
   */
  public boolean isBusy(String dateTtime);

  /**
   * Returns true when the event is an event series.
   *
   * @return boolean
   */
  public boolean isSeries();

  /**
   * Returns a string with this events subject, start and end time, and location (if any).
   *
   * @return String, "subject: startTime-endTime @ location"
   */
  @Override
  public String toString();

}
