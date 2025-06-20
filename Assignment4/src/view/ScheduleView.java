package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import javax.swing.BoxLayout;

import calendar.ICalendarSuite;
import calendar.IEvent;

/**
 * Represents the GUI for the Calendar. This ScheduleView allows a user to view the calendar
 * showing up to 10 events from a specific start date. The starting date can be updated by the
 * user to update their view.
 */
public class ScheduleView extends JFrame implements ActionListener, ISwingView {

  private final ICalendarSuite calendarSuite;

  private JComboBox<String> startDay;
  private JComboBox<String> startMonth;
  private JComboBox<String> startYear;
  private JButton changeStartDateButton;

  private JButton createEventButton;
  private JTextField eventName;
  private JToggleButton eventAllDay;
  private JTextField eventStartDate;
  private JTextField eventEndDate;
  private JTextField eventStartTime;
  private JTextField eventEndTime;
  private JLabel message;

  private final JPanel leftPanel;
  private final JPanel rightPanel;
  private final JPanel centerPanel;

  /**
   * Creates a schedule view for a calendar suite.
   *
   * @param calendar ICalendar
   */
  public ScheduleView(ICalendarSuite calendar) {
    super();
    this.calendarSuite = calendar;

    this.setTitle("CalendarApp");
    this.setMinimumSize(new Dimension(800, 600));
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout(2, 2));

    this.leftPanel = new JPanel();
    this.rightPanel = new JPanel();
    this.centerPanel = new JPanel();

    this.leftPanel.setLayout(new FlowLayout());
    this.rightPanel.setLayout(new FlowLayout());
    this.centerPanel.setLayout(new BoxLayout(this.centerPanel, BoxLayout.Y_AXIS));

    this.leftPanel.setBackground(Color.LIGHT_GRAY);
    this.rightPanel.setBackground(Color.LIGHT_GRAY);
    this.centerPanel.setBackground(Color.WHITE);

    this.leftPanel.setPreferredSize(new Dimension(150, 50));
    this.rightPanel.setPreferredSize(new Dimension(150, 50));
    this.centerPanel.setPreferredSize(new Dimension(100, 50));

    this.add(this.leftPanel, BorderLayout.WEST);
    this.add(this.rightPanel, BorderLayout.EAST);
    this.add(this.centerPanel, BorderLayout.CENTER);

    this.setUpRightPanel();
    this.setUpLeftPanel();
  }

  private void setUpRightPanel() {
    this.rightPanel.add(new JLabel("Current Calendar:"));
    this.rightPanel.add(new JLabel("  " + this.calendarSuite.getCalendarInUseName() + "  "));

    this.startDay = new JComboBox<>();
    for (int i = 1; i <= 31; i++) {
      this.startDay.addItem(i + "");
    }
    this.startDay.setSelectedIndex(0);
    this.startMonth = new JComboBox<>();
    for (int i = 1; i <= 12; i++) {
      this.startMonth.addItem(i + "");
    }
    this.startMonth.setSelectedIndex(0);
    this.startYear = new JComboBox<>();
    for (int i = 2025; i <= 2050; i++) {
      this.startYear.addItem(i + "");
    }
    this.startYear.setSelectedIndex(0);

    JPanel yearPanel = new JPanel();
    yearPanel.add(new JLabel("Year:"));
    yearPanel.add(this.startYear);
    JPanel monthPanel = new JPanel();
    monthPanel.add(new JLabel("Month:"));
    monthPanel.add(this.startMonth);
    JPanel dayPanel = new JPanel();
    dayPanel.add(new JLabel("Day:"));
    dayPanel.add(this.startDay);

    this.rightPanel.add(yearPanel);
    this.rightPanel.add(monthPanel);
    this.rightPanel.add(dayPanel);
    this.changeStartDateButton = new JButton("Change Start Date");
    this.changeStartDateButton.addActionListener(this);
    this.rightPanel.add(this.changeStartDateButton);
  }

  private void setUpLeftPanel() {
    this.leftPanel.add(new JLabel("Enter event name:"));
    this.leftPanel.add(this.eventName = new JTextField("EventName"));
    this.leftPanel.add(this.eventAllDay = new JToggleButton("All day event"));
    this.leftPanel.add(new JLabel("Event start date and time:"));
    this.leftPanel.add(this.eventStartDate = new JFormattedTextField("YYYY-MM-DD"));
    this.leftPanel.add(this.eventStartTime = new JFormattedTextField("hh:mm"));
    this.leftPanel.add(new JLabel("Event start date and time:"));
    this.leftPanel.add(this.eventEndDate = new JFormattedTextField("YYYY-MM-DD"));
    this.leftPanel.add(this.eventEndTime = new JFormattedTextField("hh:mm"));

    this.createEventButton = new JButton("Create Event");
    this.createEventButton.addActionListener(this);
    this.leftPanel.add(this.createEventButton);
    this.leftPanel.add(this.message = new JLabel());
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.message.setText("");
    if (e.getSource() == this.createEventButton) {
      if (this.eventAllDay.isSelected()) {
        try {
          this.calendarSuite.getCalendar().createSingleAllDayEvent(this.eventName.getText(),
                  this.eventStartDate.getText());
        } catch (Exception ex) {
          this.message.setText(ex.getMessage());
        }
      } else {
        try {
          this.calendarSuite.getCalendar().createSingleEvent(this.eventName.getText(),
                  this.eventStartDate.getText() + "T" + this.eventStartTime.getText(),
                  this.eventEndDate.getText() + "T" + this.eventEndTime.getText());
        } catch (Exception ex) {
          this.message.setText(ex.getMessage());
        }
      }
      this.placeTen();
    } else if (e.getSource() == this.changeStartDateButton) {
      //pop up window
      // or add text field to enter info
      this.placeTen();
      this.repaint();
    } else if (e.getSource() == this.eventAllDay) {
      if (this.eventAllDay.isSelected()) {
        this.eventEndDate.setEnabled(false);
        this.eventEndTime.setEnabled(false);
        this.eventStartTime.setEnabled(false);
      } else {
        this.eventEndDate.setEnabled(true);
        this.eventEndTime.setEnabled(true);
        this.eventStartTime.setEnabled(true);
      }
    }
    this.repaint();
  }

  private void placeTen() {
    this.centerPanel.removeAll();
    ArrayList<IEvent> events = this.calendarSuite.getCalendar().getFirstTen(
            this.startYear.getSelectedItem().toString() + "-"
                    + this.startMonth.getSelectedItem().toString() + "-"
                    + this.startDay.getSelectedItem().toString());

    for (IEvent event : events) {
      this.centerPanel.add(new JLabel(event.toString()));
    }
    this.centerPanel.setVisible(true);
  }

  @Override
  public void display() {
    this.setVisible(true);
  }
}
