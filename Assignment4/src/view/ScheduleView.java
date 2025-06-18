package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import calendar.ICalendarSuite;

/**
 * Represents the GUI for the Calendar. This ScheduleView allows a user to view the calendar
 * showing up to 10 events from a specific start date. The starting date can be updated by the
 * user to update their view.
 */
public class ScheduleView extends JFrame implements ActionListener {

  private JButton createEventButton;
  private JButton createCalendarButton;
  private JLabel display;
  private JPanel mainPanel;
  private JPanel topPanel;
  private JPanel bottomPanel;
  private JPanel leftPanel;
  private JPanel rightPanel;
  private JPanel centerPanel;

  /*
  ALISON'S POTENTIAL IDEAS:
  - we would disply the scheduled events in the center panel, and the right panel could hold the
  button to create more events
    - we only need to create single events with the GUI, so let's focus on that
    - clicking on create event should create a pop-up window that the user can then input the
    necessary information to create the event
      - i'm thinking they can select a button or smth for an all day event, so they don't need to
       input a start and end time
  - we need a text field where the user can input the desired start date to view the schedule
  (the user can see the next 10 events follow the given date)
    - probably not a text box cause of input errors, but you get the idea
  - make it look not terrible! (like everything is visible and makes sense without explanation)

  EXTRA CALENDARS: (we may not get to this and that's okay)
  - we make the left panel display the calendars available
  - the user could select them to make them in use

  THINGS I DON'T WANT TO DO:
  - we don't need to edit events, so don't worry about it
  - creating series events is optional, so only if we're feeling fancy


  HELPFUL JAVA SWING TUTS:
  https://www.youtube.com/watch?v=Kmgo00avvEw
  - i relied on this heavily in the fall and probably will again for this assignment
  - it's got examples and visuals so just jump around the video to figure out how to make certain
   components
  The swingdemo folder
  - contains the starter code they gave us to help with java swing
  - helpful reference, but i like the youtube video more as it gives more explanation that just
  showing code and having us interpret

  Have questions? text me
  Or, we can schedule a call for tuesday and crank out a portion of it

  KENNY:
  I'm hoping that you can implement a decent bit of the GUI
  - Displaying the events on the calendar
  - Handling the display when a new start date is entered
  - taking in the user's input for a new start date (howeveryou want to do it)

  ALISON:
  I'm hoping to
  - make pop up the window for creating events
  - rig the controller with the new view
  - potentially adding swtiching calendars and create calendar
   */

  public ScheduleView(ICalendarSuite calendar) {
    super();
    this.setTitle("CalendarApp");
    this.setSize(500, 500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout(2,2));

    this.topPanel = new JPanel();
    this.bottomPanel = new JPanel();
    this.leftPanel = new JPanel();
    this.rightPanel = new JPanel();
    this.centerPanel = new JPanel();

    this.topPanel.setBackground(Color.LIGHT_GRAY);
    this.bottomPanel.setBackground(Color.LIGHT_GRAY);
    this.leftPanel.setBackground(Color.LIGHT_GRAY);
    this.rightPanel.setBackground(Color.LIGHT_GRAY);
    this.centerPanel.setBackground(Color.WHITE);

    this.topPanel.setPreferredSize(new Dimension(100,50));
    this.bottomPanel.setPreferredSize(new Dimension(100,50));
    this.leftPanel.setPreferredSize(new Dimension(100,50));
    this.rightPanel.setPreferredSize(new Dimension(100,50));
    this.centerPanel.setPreferredSize(new Dimension(100,50));

    this.add(this.topPanel, BorderLayout.NORTH);
    this.add(this.bottomPanel, BorderLayout.SOUTH);
    this.add(this.leftPanel, BorderLayout.WEST);
    this.add(this.rightPanel, BorderLayout.EAST);
    this.add(this.centerPanel, BorderLayout.CENTER);

    this.topPanel.add(new JLabel(calendar.getCalendarInUseName()), BorderLayout.CENTER);

    for (String name: calendar.getCalendarNames()) {
      this.leftPanel.add(new JButton(name), BorderLayout.NORTH);
    }
    this.createCalendarButton = new JButton("Create Calendar");
    this.leftPanel.add(this.createCalendarButton, BorderLayout.SOUTH);

    this.createEventButton = new JButton("Create Event");
    this.rightPanel.add(this.createEventButton, BorderLayout.NORTH);

    //this.mainPanel = new JPanel();
    //this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.PAGE_AXIS));
  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }
}
