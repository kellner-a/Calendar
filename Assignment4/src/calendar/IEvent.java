package calendar;

/**
 * This interface is for all events. All events can have their properties editted, and all event
 * objects have getters for their subject; start and end dateTtimes; busy status; and toString of
 * their subject, startTime, endTime, and potential location.
 */
public interface IEvent {

  /**
   * Returns a copy of this event if this event is during the given date. Returns null otherwise.
   *
   * @param date Date
   * @return IEvent or null
   */
  IEvent sameDay(IDate date);

  /**
   * Returns true if this event matches the given subject, startDateTtime, and endDateTtime.
   *
   * @param subject        String
   * @param startDateTtime "YYYY-MM-DDThh:mm"
   * @param endDateTtime   "YYYY-MM-DDThh:mm"
   * @return boolean
   */
  boolean match(String subject, String startDateTtime, String endDateTtime);

  /**
   * Updates the property of the event with the new property value.
   *
   * @param prop         String
   * @param dateTtime    "YYYY-MM-DDThh:mm"
   * @param newPropvalue String
   * @return IEvent
   */
  IEvent editEventProperty(String prop, String dateTtime, String newPropvalue);

  /**
   * Updates the property of events following the given dateTtime if this event is a series. If
   * this event is not a series, this method operates the same as editProperty.
   *
   * @param prop         String
   * @param dateTtime    "YYYY-MM-DDThh:mm"
   * @param newPropvalue String
   */
  IEvent editEventsProperty(String prop, String dateTtime, String newPropvalue);

  /**
   * Updates the property of all events if this event is a series. If this event is not a series,
   * this method operates the same as editProperty.
   *
   * @param prop         String
   * @param newPropvalue String
   */
  IEvent editSeriesProperty(String prop, String newPropvalue);

  /**
   * Returns true if there is an event during the given day and time.
   *
   * @param dateTtime String
   * @return boolean
   */
  boolean isBusy(String dateTtime);

  /**
   * Returns true when the event is an event series.
   *
   * @return boolean
   */
  boolean isSeries();

  /**
   * Returns true if the given object is the same as this event. They are the same when the given
   * object in an event with the same subject, startDateTtime, and EndDateTtime.
   *
   * @param obj Object
   * @return true or false
   */
  @Override
  boolean equals(Object obj);

  @Override
  int hashCode();

  /**
   * Returns a string with this events subject, start and end time, and location (if any).
   *
   * @return String, "subject: startTime-endTime @ location"
   */
  @Override
  String toString();

  /**
   * Returns the start date of an event.
   *
   * @return Date
   */

  Date getStartDate();

  /**
   * Returns the end date of an event.
   *
   * @return Date
   */

  Date getEndDate();

  /**
   * Returns the location of an event.
   *
   * @return String
   */

  String getLocation();

  /**
   * Returns the subject of an event.
   *
   * @return String
   */

  String getSubject();

  /**
   * Returns the description of an event.
   *
   * @return String
   */

  String getDescription();

  /**
   * Returns the status of an event.
   *
   * @return String
   */

  String getStatus();

  /**
   * Returns the start time of an event.
   *
   * @return String HH:MM
   */

  String getStartTime();

  /**
   * Returns the end time  of an event.
   *
   * @return String HH:MM
   */

  String getEndTime();

  String getDateTtime(boolean b);
}
