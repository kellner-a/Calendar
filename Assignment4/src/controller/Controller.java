package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

import calendar.ICalendar;
import calendar.IEvent;
import view.IView;

/**
 * Represent a controller for a Calendar. Handles user interaction with the calendar package (our
 * model).
 */
public class Controller implements IController {

  private final ICalendar calendar;
  private final IView view;

  /**
   * Constructs a new controller with the given calendar.
   *
   * @param calendar ICalendar
   */
  public Controller(ICalendar calendar, IView view) {
    Objects.requireNonNull(calendar);
    Objects.requireNonNull(view);
    this.calendar = calendar;
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
  private void commandPattern(Scanner input) {
    while (true) {
      String command = input.nextLine();
      if (Pattern.matches("create event \\S+ from \\S+ to \\S+", command)) {
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
        System.out.println("Invalid command");
      }
    }
  }

  /**
   * Creates a single event.
   *
   * @param command String
   */
  private void createSingleEvent(String command) {
    String[] input = command.split(" ");
    try {
      this.calendar.createSingleEvent(input[2], input[4], input[6]);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Creates an event series.
   *
   * @param command String
   */
  private void createEventSeriesTimesRepeated(String command) {
    String[] input = command.split(" ");
    try {
      this.calendar.createEventSeriesTimesRepeated(input[2], input[4], input[6], input[8],
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
  private void createEventSeriesStopDate(String command) {
    String[] input = command.split(" ");
    try {
      this.calendar.createEventSeriesStopDate(input[2], input[4], input[6], input[8], input[10]);
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
      this.calendar.createSingleAllDayEvent(input[2], input[4]);
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
      this.calendar.createAllDayEventSeriesTimesRepeated(input[2], input[4], input[6],
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
      this.calendar.createAllDayEventSeriesStopDate(input[2], input[4], input[6], input[8]);
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
      this.calendar.editEventProperty(input[2], input[3], input[5], input[7], input[9]);
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
      this.calendar.editEventsProperty(input[2], input[3], input[5], input[7]);
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
      this.calendar.editSeriesProperty(input[2], input[3], input[5], input[7]);
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
  private String printEventsDay(String command) {
    String[] input = command.split(" ");
    String date = input[3];
    StringBuilder builder = new StringBuilder();
    for (IEvent event : this.calendar.getEvents(date)) {
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
  private String printEventsRange(String command) {
    String[] input = command.split(" ");
    String start = input[3];
    String end = input[5];
    StringBuilder builder = new StringBuilder();
    ArrayList<IEvent> events = this.calendar.getEvents(start, end);
    if (events.isEmpty()) {
      builder.append("No events found");
    }
    for (IEvent event : this.calendar.getEvents(start, end)) {
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
  private String showStatus(String command) {
    String[] input = command.split(" ");
    if (this.calendar.showStatus(input[3])) {
      return "busy";
    }
    return "available";
  }

}
