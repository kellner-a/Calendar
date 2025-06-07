import java.io.File;

import calendar.Calendar;
import calendar.ICalendar;
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
    ICalendar calendar = new Calendar("2025-06-06");
    IView view = new TextView();
    IController controller = new Controller(calendar, view);
    if (args[2].equals("interactive")) {
      controller.goInteractiveCalendar();
    } else if (args[2].equals("headless")) {
      if (args.length != 4) {
        throw new IllegalArgumentException("Invalid number of arguments");
      }
      controller.goHeadlessCalendar(new File(args[3]));
    }
  }
}
