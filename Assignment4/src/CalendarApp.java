import java.io.File;

import calendar.CalendarSuite;
import calendar.ICalendarSuite;
import controller.Controller;
import controller.IController;
import view.IView;
import view.TextView;

/**
 * Class to run calendar from.
 */
public class CalendarApp {
  /**
   * Runs calendar from the terminal.
   *
   * @param args String[] from terminal
   */
  public static void main(String[] args) {
    if (args.length < 3) {
      throw new IllegalArgumentException("Invalid number of arguments");
    }
    ICalendarSuite suite = new CalendarSuite();
    IView view = new TextView();
    IController controller = new Controller(suite, view);
    if (args[2].equals("interactive")) {
      controller.goInteractiveCalendar();
    } else if (args[2].equals("headless")) {
      if (args.length != 4) {
        throw new IllegalArgumentException("Invalid number of arguments");
      }
      controller.goHeadlessCalendar(args[3]);
    }
  }
}
