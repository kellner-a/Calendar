package controller;

import calendar.*;

/**
 *
 */
public class Controller implements IController {

  private ICalendar calendar;

  /**
   * Creates a new controller from the given model.
   *
   * @param calendar ICalendar
   */
  public Controller(ICalendar calendar) {
    this.calendar = calendar;
  }

  public void go() {

  }
}
