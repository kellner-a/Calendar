package controller;

import calendar.ICalendarSuite;
import view.ISwingView;

/**
 * Acts as a controller for the calendar and the view.
 */
public class SwingController implements ISwingController {
  private final ISwingView view;

  public SwingController(ICalendarSuite cal, ISwingView view) {
    ICalendarSuite calendar = cal;
    this.view = view;
  }

  @Override
  public void goDisplay() {
    this.view.display();
  }
}
