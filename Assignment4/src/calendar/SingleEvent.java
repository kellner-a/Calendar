package calendar;

/**
 * This class represents a single event. A single event can span multiple days.
 */
public class SingleEvent extends AbstractEvent {

  public SingleEvent(String subject, String startDateTTime) {
    super(subject, startDateTTime);
  }

  public SingleEvent(String subject, String startDateTtime, String endDateTTIme) {
    super(subject, startDateTtime, endDateTTIme);
  }


  @Override
  public IEvent deepCopy(int timeAdjustment) {
    SingleEvent copy = (SingleEvent) super.copy(this.subject,
            this.getStartDate() + "T" + this.getStartTime(),
            this.getEndDate() + "T" + this.getEndTime(), this.location,
            this.description, this.description);
    if (timeAdjustment > 0) {
      copy.times[0] += timeAdjustment;
      copy.times[2] += timeAdjustment;
      if (copy.times[0] > 23) {
        copy.times[0] -= 23;
        copy.startDate = copy.startDate.getNextDate(1);
      } if (copy.times[2] > 23) {
        copy.times[2] -= 23;
        copy.endDate = copy.endDate.getNextDate(1);
      }
      return copy;
    } else if (timeAdjustment < 0) {
      copy.times[0] += timeAdjustment;
      copy.times[2] += timeAdjustment;
      /*
      if (copy.times[0] < 0) {
        copy.times[0] += 23;
        copy.startDate = copy.startDate.getNextDate(-1);
      } if (copy.times[2] < 0) {
        copy.times[2] += 23;
        copy.endDate = copy.endDate.getNextDate(-1);
      }
       */
      return copy;
    } else {
      return copy;
    }
  }

  SingleEvent(String subject, String startDateTtime, String endDateTtime, String location,
              String description, String status) {
    super(subject, startDateTtime, endDateTtime);
    this.location = location;
    this.description = description;
    this.status = status;
  }

  @Override
  public boolean isSeries() {
    return false;
  }
}
