Changes in design of our program:
1. addition of a Swing controller - to accomodate the swing view as it gets input form the view
2. addition of a Swing view - to make the gui

How to run the jar:
- java -jar Assignment4.jar --mode headless path-of-script-file
- java -jar Assignment4.jar --mode interactive
- java -jar Assignment4.jar

Features that work:
- creating a single all day event (GUI)
- creating a single event (GUI)
- changing the start date for the schedule view (GUI)
- creating multilpe calendars with varying timezones (non-GUI)
- creating any type of event in a calendar (non-GUI)
- editing events in a calendar (non-GUI)
- copying events in a calendar (non-GUI)
- chaning the timezone of a calendar (non-GUI)

Distribution of work:
- Alison made the ScheduleView and SwingController
- Kenny made methods within the calendar class to help build the GUI

Additional comments:
- When working the GUI, it doesn't display right away every time. You may need to press the create 
  event button twice for the events to display, and the change start date button has a similar 
  issue.