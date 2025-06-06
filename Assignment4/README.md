1. Running the program on command line takes in 2-3 arguments (apart from the command to run the 
   program). Example command line input, ./CalendarApp.java --mode interactive, ./CalendarApp.
   java --mode headless <file-of-commands>. Though, headless mode is not available currently.
2. All features of a Calendar work with the provided valid commands list. Headless mode, which 
   takes in a file of input commands is not available. 
3. Kenny worked on the SeriesEvent class and Date class and methods in AbstractEvent and testing.
Alison worked on methods in the view and controller and Calendar.
4. The calendar package is our model package. Getting events within a dateTtime range does not 
   check time specifically and will return events on the dates irrespectable of the time. We ran 
   out of time to test our calendar as throughly as we wanted, so our testing is lacking. :(