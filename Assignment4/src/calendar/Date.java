package calendar;

/**
 * This class represents a Date. A Date has a year, month, day, and day of the week.
 * Its toString method returns a string of "YYYY-MM-DD".
 */
public class Date implements IDate {

  protected int year;
  protected int month;
  protected int day;
  char dayOfWeek;

  public Date(String dateString) {
    String[] strings = dateString.split("-");
    this.year = Integer.valueOf(strings[0]);
    this.month = Integer.valueOf(strings[1]);
    this.day = Integer.valueOf(strings[2]);
    this.dayOfWeek = findDayOfWeek();

    boolean valid = verify();
    if (!valid) {
      throw new IllegalArgumentException("Invalid date");
    }
  }

  private Date(int year, int month, int day) {
    this.year = year;
    this.month = month;
    this.day = day;
    this.dayOfWeek = findDayOfWeek();

    boolean valid = verify();
    if (!valid) {
      throw new IllegalArgumentException("Invalid date");
    }
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
  public char getDayOfWeek() {
    return this.dayOfWeek;
  }

  @Override
  public int compare(IDate otherDate) {
    if (this.year > otherDate.getYear()) {
      return 1;
    } else if (this.year < otherDate.getYear()) {
      return -1;
    } else if (this.month > otherDate.getMonth()) {
      return 1;
    } else if (this.month < otherDate.getMonth()) {
      return -1;
    } else if (this.day > otherDate.getDay()) {
      return 1;
    } else if (this.day < otherDate.getDay()) {
      return -1;
    } else {
      return 0;
    }
  }

  @Override
  public Date getNextDate(int days) {
    int nextday = this.day;
    int nextmonth = this.month;
    int nextyear = this.year;
    nextday += days;
    while (days > 0) {
      if (nextmonth == 2 && leap() && nextday > 29) {
        nextday -= 29;
        nextmonth++;
      } else if (nextmonth == 2 && !leap() && nextday > 28) {
        nextday -= 28;
        nextmonth++;
      } else if ((nextmonth == 4 || nextmonth == 6 || nextmonth == 9
              || nextmonth == 11) && nextday > 30) {
        nextday -= 30;
        nextmonth++;
      } else if (nextday > 31) {
        nextday -= 31;
        nextmonth++;
      } else {
        days = 0;
      }
      if (nextmonth == 13) {
        nextyear++;
        nextmonth = 1;
      }
    }
    return new Date(nextyear, nextmonth, nextday);
  }

  @Override
  public String toString() {
    String y = Integer.toString(this.year);
    String m = Integer.toString(this.month);
    String d = Integer.toString(this.day);
    while (y.length() < 4) {
      y = "0" + y;
    }
    if (m.length() < 2) {
      m = "0" + m;
    }
    if (d.length() < 2) {
      d = "0" + d;
    }
    return y + "-" + m + "-" + d;
  }

  /**
   * Returns true if date is valid.
   *
   * @return true or false
   */
  private boolean verify() {
    boolean bool = true;
    if (month > 12 || month < 1 || day < 1 || year < 0) {
      bool = false;
    }
    if ((month == 4 || month == 6 || month == 9 || month == 11) && day >= 31) {
      bool = false;
    }
    if (month == 2 && day >= 29) {
      bool = false;
      if (leap() && day == 29) {
        bool = true;
      }
    }
    if (day > 31) {
      bool = false;
    }
    return bool;
  }

  /**
   * Returns true if the year is a leap year.
   *
   * @return true or false
   */
  private boolean leap() {
    return this.year % 4 == 0 && (this.year % 100 != 0
            || (this.year % 400 == 0 && this.year % 100 == 0));
  }

  private char findDayOfWeek() {
    int q = this.day;
    int m = this.month;
    int year = this.year;
    if (this.month == 1 || this.month == 2) {
      m += 12;
      year -= 1;
    }
    int k = year % 100;
    int j = year / 100;

    int h = (q + (13 * (m + 1)) / 5 + k + k / 4 + j / 4 + 5 * j) % 7;

    char[] daysOfWeek = new char[]{'S', 'U', 'M', 'T', 'W', 'R', 'F'};
    return daysOfWeek[h];
  }
}
