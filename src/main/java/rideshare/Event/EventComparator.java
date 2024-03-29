package rideshare.Event;

import java.util.Comparator;

/**
 * EventComparator is a comparator for events , which compares the time of the events.
 */
public class EventComparator implements Comparator<Event> {

  @Override
  public int compare(Event o1, Event o2) {
    if (o1.time.isBefore(o2.time)) {
      return -1;
    } else if (!o1.time.isBefore(o2.time)) {
      return 1;
    }
    return 0;
  }
}
