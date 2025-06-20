import calendar.CalendarSuite;
import calendar.ICalendarSuite;
import controller.Controller;
import controller.IController;
import controller.ISwingController;
import controller.SwingController;
import view.IView;
import view.ScheduleView;
import view.TextView;

/**
 * Class to run calendar from.
 */
public class CalendarApp {

  /**
   * Runs the calendar program.
   *
   * @param args String[] from terminal
   */
  public static void main(String[] args) {
    ICalendarSuite suite = new CalendarSuite();
    if (args.length == 0) {
      ScheduleView scheduleView = new ScheduleView(suite);
      ISwingController swingController = new SwingController(suite, scheduleView);
      swingController.goDisplay();
    } else {
      IView view = new TextView();
      IController controller = new Controller(suite, view);

      if (args[1].equals("interactive")) {
        controller.goInteractiveCalendar();
      } else if (args[1].equals("headless")) {
        if (args.length != 3) {
          throw new IllegalArgumentException("Invalid number of arguments");
        }
        controller.goHeadlessCalendar(args[2]);
      }
    }
  }
}
