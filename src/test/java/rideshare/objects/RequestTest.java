package rideshare.objects;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RequestTest {
  Request testRequest;

  @BeforeEach
  void setUp() {
    testRequest = new Request("Jane", "1", "2",
        120.00, LocalDateTime.of(2023, 10, 1, 6, 0, 0),
        RideType.EXPRESS);
  }

  @Test
  void getCustomerName() {
    assertEquals("Jane", testRequest.getCustomerName());
  }

  @Test
  void getStartLocation() {
    assertEquals("1", testRequest.getStartLocation());
  }

  @Test
  void getEndLocation() {
    assertEquals("2", testRequest.getEndLocation());
  }

  @Test
  void getRequestTime() {
    assertEquals(LocalDateTime.of(2023, 10, 1, 6, 0, 0), testRequest.getRequestTime());
  }

  @Test
  void getRideType() {
    assertEquals(RideType.EXPRESS, testRequest.getRideType());
  }

  @Test
  void setEstimatedArrivalTime() {
    testRequest.setEstimatedArrivalTime(LocalDateTime.of(2023, 10, 1, 8, 0, 0));
    assertEquals(LocalDateTime.of(2023, 10, 1, 8, 0, 0), testRequest.getEstimatedArrivalTime());
  }

  @Test
  void testEquals() {
    // test with null
    assertFalse(testRequest.equals(null));
    // test with itself
    assertTrue(testRequest.equals(testRequest));
    // test with different class
    assertFalse(testRequest.equals(new Object()));

    // test with same class and same object
    Request testRequest2 = new Request("Jane", "1", "2",
        120.00, LocalDateTime.of(2023, 10, 1, 6, 0, 0),
        RideType.EXPRESS);
    assertTrue(testRequest.equals(testRequest2));

    // test with same class but different object
    Request testRequest3 = new Request("Doe", "1", "2",
        120.00, LocalDateTime.of(2023, 10, 1, 6, 0, 0),
        RideType.EXPRESS);
    assertFalse(testRequest.equals(testRequest3));

    // test with same class but different startLocation
    Request testRequest4 = new Request("Jane", "2", "2",
        120.00, LocalDateTime.of(2023, 10, 1, 6, 0, 0),
        RideType.EXPRESS);
    assertFalse(testRequest.equals(testRequest4));

    // test with same class but different endLocation
    Request testRequest5 = new Request("Jane", "1", "3",
        120.00, LocalDateTime.of(2023, 10, 1, 6, 0, 0),
        RideType.EXPRESS);
    assertFalse(testRequest.equals(testRequest5));

    // test with same class but different distance
    Request testRequest6 = new Request("Jane", "1", "2",
        130.00, LocalDateTime.of(2023, 10, 1, 6, 0, 0),
        RideType.EXPRESS);
    assertFalse(testRequest.equals(testRequest6));

    // test with same class but different requestTime
    Request testRequest7 = new Request("Jane", "1", "2",
        120.00, LocalDateTime.of(2023, 10, 1, 7, 0, 0),
        RideType.EXPRESS);
    assertFalse(testRequest.equals(testRequest7));

    // test with same class but different rideType
    Request testRequest8 = new Request("Jane", "1", "2",
        120.00, LocalDateTime.of(2023, 10, 1, 6, 0, 0),
        RideType.ENVIRONMENTALLY_CONSCIOUS);
    assertFalse(testRequest.equals(testRequest8));

  }

  @Test
  void testHashCode() {
    Request testRequest2 = new Request("Jane", "1", "2",
        120.00, LocalDateTime.of(2023, 10, 1, 6, 0, 0),
        RideType.EXPRESS);
    assertEquals(testRequest.hashCode(), testRequest2.hashCode());
  }

  @Test
  void testToString() {
    String expectedString = "Request{" +
        "assignedDriver=" + testRequest.getAssignedDriver() +
        ", customerName='" + testRequest.getCustomerName() + '\'' +
        ", startLocation='" + testRequest.getStartLocation() + '\'' +
        ", endLocation='" + testRequest.getEndLocation() + '\'' +
        ", distance=" + testRequest.getDistance() +
        ", requestTime=" + testRequest.getRequestTime() +
        ", rideType=" + testRequest.getRideType() +
        ", priority=" + testRequest.getPriority() +
        ", ridingTime=" + testRequest.getRidingTime() +
        ", estimatedArrivalTime=" + testRequest.getEstimatedArrivalTime() +
        ", actualArrivalTime=" + testRequest.getActualArrivalTime() +
        '}';
    assertEquals(expectedString, testRequest.toString());
  }
}