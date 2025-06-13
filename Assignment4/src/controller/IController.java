package controller;

import java.util.Scanner;

/**
 * Interface for controllers for Calendar. Handles user interaction with the calendar packag (our
 * model).
 */
public interface IController {

  /**
   * Activates the calendar. Hands off user input to the calendar.
   */
  void goInteractiveCalendar();

  /**
   * Activates the calendar. Hands off user input to the calendar.
   *
   * @param filePath File containing commands for the calendar
   */
  void goHeadlessCalendar(String filePath);

  /**
   * Effectively executes the command, given it matches one of the valid commands. Returns true
   * when the user wants to quit, returns false otherwise.
   *
   * @param input Scanner
   */
  void commandPattern(Scanner input);

  /**
   * Creates a single event.
   *
   * @param command String
   */
  void createSingleEvent(String command);


  /**
   * Creates an event series.
   *
   * @param command String
   */
  void createEventSeriesTimesRepeated(String command);

  /**
   * Creates an event series.
   *
   * @param command String
   */
  void createEventSeriesStopDate(String command);

  /**
   * Creates a single all day event.
   *
   * @param command String
   */
  void createSingleEventAllDay(String command);

  /**
   * Creates an event series of all day events.
   *
   * @param command String
   */
  void createEventSeriesAllDayTimesRepeated(String command);

  /**
   * Creates an event series of all day events.
   *
   * @param command String
   */
  void createEventSeriesAllDayStopDate(String command);

  /**
   * Edits an events property.
   *
   * @param command String
   */
  void editEventProperty(String command);

  /**
   * Edits an events property.
   *
   * @param command String
   */
  void editEventsProperty(String command);

  /**
   * Edits an events property.
   *
   * @param command String
   */
  void editSeriesProperty(String command);

  /**
   * Returns a string listing events.
   *
   * @param command String
   * @return String
   */
  String printEventsDay(String command);

  /**
   * Returns a string listing events.
   *
   * @param command String
   * @return String
   */
  String printEventsRange(String command);

  /**
   * Returns the status of a certain time.
   *
   * @param command String
   * @return String
   */
  String showStatus(String command);
}
