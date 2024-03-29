package rideshare.Event;

import java.time.LocalDateTime;
import rideshare.objects.Request;
import rideshare.Controller.RideshareDispatchSimulator;

/**
 * RideFinishedEvent represents an active ride's finishing event
 * It will only be generated when a request is added to activeRequests
 */
public class RideFinishedEvent extends Event{

  /**
   * Constructor for RideFinishedEvent
   * @param time time of the event
   * @param theSimulator the simulator, as a RideshareDispatchSimulator
   * @param request the active request, as a Request
   */
  public RideFinishedEvent(LocalDateTime time, RideshareDispatchSimulator theSimulator, Request request) {
    super(time, theSimulator, request);
  }

  /**
   * When a ride is finished, add the request to finishedRequests, remove the request from activeRequests,
   * increase idle driver numbers
   * Then check if there's waiting requests, if so, assign the released driver to the request,
   * and decrease idle driver numbers.
   */
  @Override
  public void processEvent() {
    this.theSimulator.handleRideFinished(this);
  }

  @Override
  public String toString() {
    return "RideFinishedEvent{" +
        "request=" + request +
        ", time=" + time +
        ", theSimulator=" + theSimulator +
        "} " + super.toString();
  }
}
