package controller;

import java.io.File;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.text.View;

import calendar.ICalendar;
import calendar.IEvent;
import view.IView;
import view.TextView;

/**
 * Represent a controller for a Calendar. Handles user interaction with the calendar package (our
 * model).
 */
public class Controller implements IController {

  private ICalendar calendar;
  private IView view;

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
    while (true) {
      String command = scanner.nextLine();
      if (Pattern.matches("create event \\S+ from \\S+ to \\S+", command)){
        this.createSingleEvent(command);
      } else if (Pattern.matches("create event \\S+ from \\S+ to \\S+ repeats \\S+ for \\d+ " +
              "times", command)) {
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
      } else if (Pattern.matches("edit event \\S+ \\S+ from \\S+ to \\S+ with \\S+",  command)) {
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

  @Override
  public void goHeadlessCalendar(File commands) {
    /*
    To be implemented.
    Will parse the given file similarily to the method above.
    Most likely will pull above command design out into a helper method to be used for both the
    headless and interactive methods.
     */
  }

  /**
   * Creates a single event.
   *
   * @param command String
   * @throws IllegalArgumentException
   */
  private void createSingleEvent(String command) throws IllegalArgumentException {
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
   * @throws IllegalArgumentException
   */
  private void createEventSeriesTimesRepeated(String command) throws IllegalArgumentException {
    String[] input = command.split(" ");
    try {
      this.calendar.createEventSeries(input[2], input[4], input[6], input[8],
              Integer.parseInt(input[10]));
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Creates an event series.
   *
   * @param command
   * @throws IllegalArgumentException
   */
  private void createEventSeriesStopDate(String command) throws IllegalArgumentException {
    String[] input = command.split(" ");
    try {
      this.calendar.createEventSeries(input[2], input[4], input[6], input[8], input[10]);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Creates a single all day event.
   *
   * @param command String
   * @throws IllegalArgumentException
   */
  public void createSingleEventAllDay(String command) throws IllegalArgumentException {
    String[] input = command.split(" ");
    try {
      this.calendar.createSingleEvent(input[2], input[4]);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Creats an event series of all day events.
   *
   * @param command String
   * @throws IllegalArgumentException
   */
  public void createEventSeriesAllDayTimesRepeated(String command)
          throws IllegalArgumentException {
    String[] input = command.split(" ");
    try {
      this.calendar.createEventSeries(input[2], input[4], input[6], Integer.parseInt(input[8]));
    } catch (IllegalArgumentException e) {

    }
  }

  /**
   * Creates an event series of all day events.
   *
   * @param command String
   * @throws IllegalArgumentException
   */
  public void createEventSeriesAllDayStopDate(String command) throws IllegalArgumentException {
    String[] input = command.split(" ");
    try {
      this.calendar.createEventSeries(input[2], input[4], input[6], input[8]);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Edits an events property.
   *
   * @param command String
   * @throws IllegalArgumentException
   */
  public void editEventProperty(String command) throws IllegalArgumentException {
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
   * @throws IllegalArgumentException
   */
  public void editEventsProperty(String command) throws IllegalArgumentException {
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
   * @throws IllegalArgumentException
   */
  public void editSeriesProperty(String command) throws IllegalArgumentException {
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
   * @throws IllegalArgumentException
   */
  private String printEventsDay(String command) throws IllegalArgumentException {
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
    for (IEvent event : this.calendar.getEvents(start, end)) {
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
