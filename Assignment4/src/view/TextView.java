package view;

import calendar.ICalendar;

/**
 * Displays the text for Calendar output to terminal.
 */
public class TextView implements IView {

  private ICalendar calendar;

  /**
   * Constructor for a textview. Takes in a ICalendar object.
   *
   * @param calendar ICalendar
   */
  public TextView(ICalendar calendar) {
    this.calendar = calendar;
  }

  @Override
  public String display(String message) {
    return String.valueOf(message);
  }
}
