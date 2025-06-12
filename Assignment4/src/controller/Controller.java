package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

import calendar.ICalendarSuite;
import calendar.IEvent;
import view.IView;

/**
 * Represent a controller for a Calendar. Handles user interaction with the calendar package (our
 * model).
 */
public class Controller implements IController {

  private final ICalendarSuite suite;
  private final IView view;

  /**
   * Constructs a new controller with the given calendar.
   *
   * @param suite ICalendarSuite
   */
  public Controller(ICalendarSuite suite, IView view) {
    Objects.requireNonNull(suite);
    Objects.requireNonNull(view);
    this.suite = suite;
    this.view = view;
  }

  @Override
  public void goInteractiveCalendar() {
    Scanner scanner = new Scanner(System.in);
    this.commandPattern(scanner);
  }

  @Override
  public void goHeadlessCalendar(String filePath) {
    try {
      File commands = new File(filePath);
      Scanner scanner = new Scanner(commands);
      this.commandPattern(scanner);
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }

  /**
   * Effectively executes the command, given it matches one of the valid commands. Returns true
   * when the user wants to quit, returns false otherwise.
   *
   * @param input Scanner
   */
  @Override
  public void commandPattern(Scanner input) {
    while (true) {
      String command = input.nextLine();
      if (Pattern.matches("create calendar --name \\S+ --timezone \\S+/\\S+", command)) {
        this.createCalendar(command);
      } else if (Pattern.matches("edit calendar --name \\S+ --property \\S+ \\S+", command)) {
        this.editCalendar(command);
      } else if (Pattern.matches("use calendar --name \\S+", command)) {
        this.useCalendar(command);
      } else if (Pattern.matches("copy event \\S+ on \\S+ --target \\S+ to \\S+", command)) {
        this.copySingleEvent(command);
      } else if (Pattern.matches("copy events on \\S+ --target \\S+ to \\S+", command)) {
        this.copyDayEvent(command);
      } else if (Pattern.matches("copy events between \\S+ and \\S+ --target \\S+ to \\S+",
              command)) {
        this.copyEventsRange(command);
      } else if (Pattern.matches("create event \\S+ from \\S+ to \\S+", command)) {
        this.createSingleEvent(command);
      } else if (Pattern.matches("create event \\S+ from \\S+ to \\S+ repeats \\S+ for \\d+ "
              + "times", command)) {
        this.createEventSeriesTimesRepeated(command);
      } else if (Pattern.matches("create event \\S+ from \\S+ to \\S+ repeats \\S+ until \\S+",
              command)) {
        this.createEventSeriesStopDate(command);
      } else if (Pattern.matches("create event \\S+ on \\S+", command)) {
        this.createSingleEventAllDay(command);
      } else if (Pattern.matches("create event \\S+ on \\S+ repeats \\S+ for \\d+ times",
              command)) {
        this.createEventSeriesAllDayTimesRepeated(command);
      } else if (Pattern.matches("create event \\S+ on \\S+ repeats \\S+ until \\S+", command)) {
        this.createEventSeriesAllDayStopDate(command);
      } else if (Pattern.matches("edit event \\S+ \\S+ from \\S+ to \\S+ with \\S+", command)) {
        this.editEventProperty(command);
      } else if (Pattern.matches("edit events \\S+ \\S+ from \\S+ with \\S+", command)) {
        this.editEventsProperty(command);
      } else if (Pattern.matches("edit series \\S+ \\S+ from \\S+ with \\S+", command)) {
        this.editSeriesProperty(command);
      } else if (Pattern.matches("print events on \\S+", command)) {
        System.out.println(this.view.display(this.printEventsDay(command)));
      } else if (Pattern.matches("print events from \\S+ to \\S+", command)) {
        System.out.println(this.view.display(this.printEventsRange(command)));
      } else if (Pattern.matches("show status on \\S+", command)) {
        System.out.println(this.view.display(this.showStatus(command)));
      } else if (command.equalsIgnoreCase("quit")) {
        break;
      } else {
        System.out.println("Invalid command: " + command);
      }
    }
  }

  /**
   * Creates a calendar in this calendar suite.
   *
   * @param command String
   */
  private void createCalendar(String command) {
    String[] input = command.split(" ");
    try {
      this.suite.createCalendar(input[3], input[5]);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Edits a calendar in this calendar suite.
   *
   * @param command String
   */
  private void editCalendar(String command) {
    String[] input = command.split(" ");
    try {
      this.suite.editCalendar(input[3], input[5], input[6]);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Marks a calendar in this suite as in use.
   *
   * @param command String
   */
  private void useCalendar(String command) {
    String[] input = command.split(" ");
    try {
      this.suite.useCalendar(input[3]);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Copies an event from the calendar marked in use to the target calendar.
   *
   * @param command String
   */
  private void copySingleEvent(String command) {
    String[] input = command.split(" ");
    try {
      this.suite.copySingleEvent(input[2], input[4], input[6], input[8]);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Copies events on one day from the calendar marked in use to the target calendar. Accounts
   * for change in timezones.
   *
   * @param command String
   */
  private void copyDayEvent(String command) {
    String[] input = command.split(" ");
    try {
      this.suite.copyDayEvents(input[3], input[5], input[7]);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Copies events in a range from the calendar marked in use to the target calendar. Accounts
   * for change in timezones.
   *
   * @param command String
   */
  private void copyEventsRange(String command) {
    String[] input = command.split(" ");
    try {
      this.suite.copyEventsRange(input[3], input[5], input[7], input[9]);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Creates a single event.
   *
   * @param command String
   */
  @Override
  public void createSingleEvent(String command) {
    String[] input = command.split(" ");
    try {
      this.suite.getCalendar().createSingleEvent(input[2], input[4], input[6]);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Creates an event series.
   *
   * @param command String
   */
  @Override
  public void createEventSeriesTimesRepeated(String command) {
    String[] input = command.split(" ");
    try {
      this.suite.getCalendar().createEventSeriesTimesRepeated(input[2], input[4], input[6], input[8],
              Integer.parseInt(input[10]));
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Creates an event series.
   *
   * @param command String
   */
  @Override
  public void createEventSeriesStopDate(String command) {
    String[] input = command.split(" ");
    try {
      this.suite.getCalendar().createEventSeriesStopDate(input[2], input[4], input[6], input[8], input[10]);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Creates a single all day event.
   *
   * @param command String
   */
  public void createSingleEventAllDay(String command) {
    String[] input = command.split(" ");
    try {
      this.suite.getCalendar().createSingleAllDayEvent(input[2], input[4]);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Creats an event series of all day events.
   *
   * @param command String
   */
  public void createEventSeriesAllDayTimesRepeated(String command) {
    String[] input = command.split(" ");
    try {
      this.suite.getCalendar().createAllDayEventSeriesTimesRepeated(input[2], input[4], input[6],
              Integer.parseInt(input[8]));
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Creates an event series of all day events.
   *
   * @param command String
   */
  public void createEventSeriesAllDayStopDate(String command) {
    String[] input = command.split(" ");
    try {
      this.suite.getCalendar().createAllDayEventSeriesStopDate(input[2], input[4], input[6], input[8]);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Edits an events property.
   *
   * @param command String
   */
  public void editEventProperty(String command) {
    String[] input = command.split(" ");
    try {
      this.suite.getCalendar().editEventProperty(input[2], input[3], input[5], input[7], input[9]);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Edits an events property.
   *
   * @param command String
   */
  public void editEventsProperty(String command) {
    String[] input = command.split(" ");
    try {
      this.suite.getCalendar().editEventsProperty(input[2], input[3], input[5], input[7]);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Edits an events property.
   *
   * @param command String
   */
  public void editSeriesProperty(String command) {
    String[] input = command.split(" ");
    try {
      this.suite.getCalendar().editSeriesProperty(input[2], input[3], input[5], input[7]);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Returns a string listing events.
   *
   * @param command String
   * @return String
   */
  @Override
  public String printEventsDay(String command) {
    String[] input = command.split(" ");
    String date = input[3];
    StringBuilder builder = new StringBuilder();
    for (IEvent event : this.suite.getCalendar().getEvents(date)) {
      builder.append("•");
      builder.append(event.toString());
      builder.append("\n");
    }
    return builder.toString();
  }

  /**
   * Returns a string listing events.
   *
   * @param command String
   * @return String
   */
  @Override
  public String printEventsRange(String command) {
    String[] input = command.split(" ");
    String start = input[3];
    String end = input[5];
    StringBuilder builder = new StringBuilder();
    ArrayList<IEvent> events = this.suite.getCalendar().getEvents(start, end);
    if (events.isEmpty()) {
      builder.append("No events found");
    }
    for (IEvent event : this.suite.getCalendar().getEvents(start, end)) {
      // get Events only gets events on the start date
      //System.out.println("hit");
      builder.append("•");
      builder.append(event.toString());
      builder.append("\n");
    }
    return builder.toString();
  }

  /**
   * Returns the status of a certain time.
   *
   * @param command String
   * @return String
   */
  @Override
  public String showStatus(String command) {
    String[] input = command.split(" ");
    if (this.suite.getCalendar().showStatus(input[3])) {
      return "busy";
    }
    return "available";
  }

}
