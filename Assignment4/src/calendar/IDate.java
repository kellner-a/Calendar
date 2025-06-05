package calendar;

/**
 * Represents a date in terms of a day, month, year, and day of the week.
 */
public interface IDate {

  /**
   * Returns the year as an int.
   *
   * @return int
   */
  public int getYear();

  /**
   * Returns the month as an int.
   *
   * @return int, 1-12 inclusive
   */
  public int getMonth();

  /**
   * Returns the day as an int.
   *
   * @return int, 1-31 inclusive, month dependent
   */
  public int getDay();

  /**
   * Compares otherDate to this date. Returns 1 when this date is after otherDate, -1 when this
   * date is before otherDate, and 0 if they are the same date.
   *
   * @param date IDate
   * @return -1, 0, 1
   */
  public int compare(IDate otherDate);

  /**
   * Returns a new Date x days after this current date. Given 5 when this current date is
   * 2025-06-02 Monday, the new Date returned would be 2025-06-07 Saturday.
   *
   * @param days
   * @return new Date
   */
  public IDate getNextDate(int days);

  /**
   * Returns a string of the date in "YYYY-MM-DD" format.
   *
   * @return String, YYYY-MM-DD
   */
  public String toString();

}
