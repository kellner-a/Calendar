1. We made a class above Calendar that has a hashmap of calendars and a hashmap of timezones. 
We chose to approach it this way because all previous implementation continued to work with the new
implementation. In other words it was  backwards compatible. Using the hashmaps also ensured that
there couldn't be duplicate calendar names and reduces collisions. 
2. Running the program on command line takes in 2-3 arguments(apart from the command line to run the program)
Example command line input, java -jar Assignment4.jar ./CalendarApp.java --mode interactive
 java -jar Assignment4.jar ./CalendarApp.java --mode headless <file-of-commands>
3. All features work except for when copy events in different timezones results in the date going backwards.
4. Kenny did testing and debugging and worked on methods in the CalendarSuite class.
Alison worked on the CalendarSuite class and debugging.
5. The feature regarding copy dates into different timezones doesn't work when the date has to go backwards.
The features are functional, but they weren't thoroughly tested because we ran out of time.