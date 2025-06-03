package calendar;

/**
 * This class represents a Date. A Date has a year, month, day, and day of the week.
 * Its toString method returns a string of "YYYY-MM-DD".
 */
public class Date implements IDate {

    private int year;
    private int month;
    private int day;
    private Day dayOfWeek;

    public Date(String dateString) {

//      this.year = year;
//      this.month = month;
//      this.day = day;
//
//
//      // use formula to determine dayOfWeek based on the date
//      dayOfWeek =
    }

    private void validDate() throws IllegalArgumentException {
      if (year < 0) {
        throw new IllegalArgumentException("Year cannot be negative");
      } if (month < 1 || month > 12) {
        throw new IllegalArgumentException("Month must be between 1 and 12");
      } else if ()
    }

    @Override
    public int getYear() {
      return this.year;
    }

    @Override
    public int getMonth() {
      return this.month;
    }

    @Override
    public int getDay() {
      return this.day;
    }

    @Override
    public String toString() {
      return ""; // implement!
    }
}
