package rideshare.objects;

import java.util.Comparator;
import rideshare.objects.Request;

/**
 * Comparator for requests in waitingRequests.
 * Compare by priority first, then by expected order of arrivals to the destination if priority is the same.
 */
public class WaitingComparator implements Comparator<Request> {

  @Override
  public int compare(Request o1, Request o2) {
    // compare priority
    int priorityCompareResult = Integer.compare(o1.getPriority(), o2.getPriority());
    // compare expected order of arrivals to the destination if priority is the same
    if (priorityCompareResult == 0) {
      if (o1.getEstimatedArrivalTime().isBefore(o2.getEstimatedArrivalTime())) {
        return -1;
      } else if (!o1.getEstimatedArrivalTime().isBefore(o2.getEstimatedArrivalTime())) {
        return 1;
      }
      return 0;
    }

    return priorityCompareResult * (-1);
  }
}
