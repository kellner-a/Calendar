package controller;

import java.io.File;

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
   * @param commands File containing commands for the calendar
   */
  void goHeadlessCalendar(File commands);

}
