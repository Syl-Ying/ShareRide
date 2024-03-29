package rideshare.Event;

import java.time.LocalDateTime;
import rideshare.objects.Request;
import rideshare.Controller.RideshareDispatchSimulator;

/**
 * RideRequestedEvent represents an incoming request
 * Initially, it will be scheduled when a request is added to waitingRequests
 */
public class RideRequestedEvent extends Event {

  /**
   * Constructor for RideRequestedEvent
   * @param time time of the event as a LocalDateTime
   * @param theSimulator the simulator, as a RideshareDispatchSimulator
   * @param request the incoming request, as a Request
   */
  public RideRequestedEvent(LocalDateTime time, RideshareDispatchSimulator theSimulator, Request request) {
    super(time, theSimulator, request);
  }

  /**
   * When there's idle drivers, assign a driver to the request, decrease idle driver numbers,
   * then push the request into activeRequests, and calculate request finishing time
   * Then schedule a RideFinishedEvent in the eventQueue
   *
   * When there's no idle driver, simply push the request to waitingRequests.
   */
  @Override
  public void processEvent() {
    if (this.theSimulator.handleRideRequested(this)) {
      // should be actual arrival time
      this.theSimulator.scheduleEvent(new RideFinishedEvent(request.getActualArrivalTime(), this.theSimulator, this.request));
    }
  }

  @Override
  public String toString() {
    return "RideRequestedEvent{" +
        "request=" + request +
        ", time=" + time +
        ", theSimulator=" + theSimulator +
        "} " + super.toString();
  }
}
