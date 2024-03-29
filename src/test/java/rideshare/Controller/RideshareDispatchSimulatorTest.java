package rideshare.Controller;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rideshare.Event.RideFinishedEvent;
import rideshare.Event.RideRequestedEvent;
import rideshare.objects.Request;
import rideshare.objects.RideType;

class RideshareDispatchSimulatorTest {

  RideshareDispatchSimulator testSimulation;

  @BeforeEach
  void setUp() {
    testSimulation = new RideshareDispatchSimulator(1, 3);
  }

  @Test
  void testInitialization() {
    assertEquals(1, testSimulation.getNumberOfIdleDrivers());
    assertEquals(1, testSimulation.getAvailableDrivers().size());

    assertEquals(3, testSimulation.getEventQueue().size());
    // test if the first event is RideRequestedEvent
    assertEquals("RideRequestedEvent",
        testSimulation.getEventQueue().peek().getClass().getSimpleName());
  }

  @Test
  void run() {
    testSimulation.run();
    // check if the eventQueue is empty
    assertEquals(0, testSimulation.getEventQueue().size());
    // check if Requests is all finished
    assertEquals(3, testSimulation.getFinishedRequests().size());
  }

  @Test
  void handleRideRequested_OnlyOneRequest() {
    Request testRequest = new Request("Jane", "1", "1",
        60.00, LocalDateTime.of(2023, 10, 01, 06, 00, 00),
        RideType.EXPRESS);
    RideRequestedEvent testEvent = new RideRequestedEvent(testRequest.getRequestTime(),
        testSimulation, testRequest);

    assertTrue(testSimulation.handleRideRequested(testEvent));
    assertEquals(LocalDateTime.of(2023, 10, 01, 07, 00, 00),
        testRequest.getEstimatedArrivalTime());
    assertEquals(LocalDateTime.of(2023, 10, 01, 07, 00, 00),
        testRequest.getActualArrivalTime());

    assertEquals(0, testSimulation.getNumberOfIdleDrivers());
    assertEquals(0, testSimulation.getAvailableDrivers().size());
    assertEquals(1, testSimulation.getActiveRequests().size());
    assertEquals(testRequest, testSimulation.getActiveRequests().peek());
  }

  @Test
  void testfinishedEvent_Equal() {
    Request testRequest = new Request("Jane", "1", "1",
        60.00, LocalDateTime.of(2023, 10, 01, 06, 00, 00),
        RideType.EXPRESS);
    RideFinishedEvent testEvent = new RideFinishedEvent(testRequest.getRequestTime(),
        testSimulation, testRequest);

    testSimulation.handleRideFinished(testEvent);
    assertEquals(1, testSimulation.getFinishedRequests().size());

    assertTrue(testEvent.equals(testEvent));
    assertFalse(testEvent.equals(null));
    assertFalse(testEvent.equals(testRequest));
  }
}