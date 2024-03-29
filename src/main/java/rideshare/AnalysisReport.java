package rideshare;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import rideshare.objects.Request;

/**
 * Generate an analysis report, including average waiting time, average riding time,
 * average number of handled rides for a driver, and the optimal number of drivers.
 */
public class AnalysisReport {

  /**
   * calculate average waiting time and riding time for all requests
   * @param requests a list of requests, as a List<Request>
   * @return an array of average waiting time and riding time, as an Integer[]
   */
  public static Integer[] calculateAverageWaitingAndRidingTime(List<Request> requests) {
    Integer size = requests.size();
    Integer[] averageTime = new Integer[2];
    Integer totalWaitingSeconds = 0;
    Integer totalRidingSeconds = 0;
    for (Request request: requests) {
      totalWaitingSeconds += request.calculateWaitingSeconds();
      totalRidingSeconds += ((int) (request.getRidingSeconds()));
    }
    averageTime[0] = totalWaitingSeconds / size;
    averageTime[1] =  totalRidingSeconds / size;
    return averageTime;
  }

  /**
   * Calculate the optimal number of drivers required to make all requests' waiting time to be 0
   * @param requestsHistory a list of requests, as a List<Request>
   * @return the minimum number of drivers required to make all requests' waiting time to be 0, as an Integer
   */
  public static Integer calculateOptimalDriverNumber(List<Request> requestsHistory) {
    // copy the original requestsHistory's request time and estimated time
    // to avoid alter its estimatedArrivalTime
    List<List<LocalDateTime>> intervals = new ArrayList<>();
    for (Request request: requestsHistory) {
      List<LocalDateTime> interval = new ArrayList<>();
      interval.add(request.getRequestTime());
      interval.add(request.getEstimatedArrivalTime());
      intervals.add(interval);
    }

    Integer size = intervals.size();

    if (intervals == null || size == 0) {
      return 0;
    }

    // sort the request by requesting time
    Collections.sort(intervals, new Comparator<List<LocalDateTime>>() {
      @Override
      public int compare(List<LocalDateTime> a, List<LocalDateTime> b) {
        return a.get(0).compareTo(b.get(0));
      }
    });

    // use a min heap to track the min estimated arrival time of merged requests
    Queue<List<LocalDateTime>> heap = new PriorityQueue<>(size, new Comparator<List<LocalDateTime>>() {
      @Override
      public int compare(List<LocalDateTime> o1, List<LocalDateTime> o2) {
        return o1.get(1).compareTo(o2.get(1));
      }
    });

    heap.offer(intervals.get(0));

    for (int i = 1; i < size; i++) {
      List<LocalDateTime> currentHead = heap.poll();
      List<LocalDateTime> interval = intervals.get(i);
      // no crossed time range between two intervals
      if (interval.get(0).compareTo(currentHead.get(1)) >= 0) {
        currentHead.set(1, (interval.get(1)));
      } else {
        heap.offer(interval);
      }
      heap.offer(currentHead);
    }
    return heap.size();
  }

  /**
   * print the request history for debugging
   * @param requestsHistory a list of requests, as a List<Request>
   */
  private static void printRequestHistory(List<Request> requestsHistory) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formattedDistance;
    String formattedMinutes;
    String formmatedWaitingTime;

    System.out.println("Requests finished within 24 hours: \n");

    for (Request request: requestsHistory) {
      formattedDistance = String.format("%.2f", request.getDistance());
      formattedMinutes = String.format("%.2f", request.getRidingSeconds() / 60);

      Integer waitingSeconds = request.calculateWaitingSeconds();
      int hours = waitingSeconds / 3600;
      int minutes = (waitingSeconds % 3600) / 60;
      int seconds = waitingSeconds % 60;
      formmatedWaitingTime = String.format("%d hours %d minutes %d seconds",
          hours, minutes, seconds);

      System.out.println(
          "Request: " + request.getRequestTime().format(formatter)
              + " EstimatedArrival: " + request.getEstimatedArrivalTime().format(formatter)
              + " ActualArrival: " + request.getActualArrivalTime().format(formatter)
              + " Distance (miles): " + formattedDistance
              + " RidingMinutes: " + formattedMinutes
              + " Waiting: " + formmatedWaitingTime
              + System.lineSeparator()
      );
    }
  }

  /**
   * generate an analysis report
   * @param requestsHistory a list of requests, as a List<Request>
   * @param numberOfDrivers number of drivers, as an Integer
   */
  public static void generateReport(List<Request> requestsHistory , Integer numberOfDrivers) {
    System.out.println("=========================== Simulation Analysis Report ==========================="
        + System.lineSeparator());
    Integer totalWaitingSeconds = calculateAverageWaitingAndRidingTime(requestsHistory)[0];
    int hours = totalWaitingSeconds / 3600;
    int minutes = (totalWaitingSeconds % 3600) / 60;
    int seconds = totalWaitingSeconds % 60;

    String averageWaitingTime = String.format("Average waiting time: %d hours %d minutes %d seconds"
            + System.lineSeparator(), hours, minutes, seconds);
    System.out.println(averageWaitingTime);

    Integer totalRidingSeconds = calculateAverageWaitingAndRidingTime(requestsHistory)[1];
    hours = totalRidingSeconds / 3600;
    minutes = (totalRidingSeconds % 3600) / 60;
    seconds = totalRidingSeconds % 60;
    String averageRidingTime = String.format("Average riding time: %d hours %d minutes %d seconds"
            + System.lineSeparator(), hours, minutes, seconds);
    System.out.println(averageRidingTime);


    String averageNumberOfHandledRides = String.format("Average number of handled rides for a driver: %d"
            + System.lineSeparator(), requestsHistory.size() / numberOfDrivers);
    System.out.println(averageNumberOfHandledRides);

    String optimalNumberOfDrivers = String.format("To balance the business operating costs with the "
        + "customer convenience, the optimal number of drivers is %d" +
        System.lineSeparator(), calculateOptimalDriverNumber(requestsHistory));
    System.out.println(optimalNumberOfDrivers);

    printRequestHistory(requestsHistory);

    System.out.println("================================= End Of Report ==================================="
        + System.lineSeparator());
  }
}
