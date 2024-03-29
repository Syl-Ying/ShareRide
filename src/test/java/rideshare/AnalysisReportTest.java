package rideshare;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rideshare.objects.Request;
import rideshare.objects.RideType;

class AnalysisReportTest {
  List<Request> finishedRequests;

  @BeforeEach
  void setUp() {
    Request testRequest = new Request("Jane", "1", "1",
        120.00, LocalDateTime.of(2023, 10, 1, 6, 0, 0),
        RideType.EXPRESS);
    testRequest.setActualArrivalTime(LocalDateTime.of(2023, 10, 1, 8, 0, 0));

    Request testRequest2 = new Request("Jane", "1", "1",
        120.00, LocalDateTime.of(2023, 10, 1, 7, 0, 0),
        RideType.EXPRESS);
    testRequest2.setActualArrivalTime(LocalDateTime.of(2023, 10, 1, 10, 0, 0));

    finishedRequests = new ArrayList<>();
    finishedRequests.add(testRequest);
    finishedRequests.add(testRequest2);
  }

  @Test
  void calculateAverageWaitingAndRidingTime() {
    Integer[] averageTime = AnalysisReport.calculateAverageWaitingAndRidingTime(finishedRequests);
    assertEquals(1800, averageTime[0]); // seconds
    assertEquals(7200, averageTime[1]);
  }

  @Test
  void calculateOptimalDriverNumber() {
    Integer optimalDriverNumber = AnalysisReport.calculateOptimalDriverNumber(finishedRequests);
    assertEquals(2, optimalDriverNumber);
  }

  @Test
  void printReport() {
    AnalysisReport.generateReport(finishedRequests, 2);
    String expectedString = "Average waiting time: 30 minutes\n"
        + "Average riding time: 120 minutes\n"
        + "Average number of handled rides for a driver: 2\n"
        + "Optimal number of drivers: 2\n";
    /*assertEquals(expectedString, AnalysisReport.getReport());*/
  }
}