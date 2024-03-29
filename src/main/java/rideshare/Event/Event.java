package rideshare.Event;

import java.time.LocalDateTime;
import java.util.Objects;
import rideshare.objects.Request;
import rideshare.Controller.RideshareDispatchSimulator;

/**
 * Event represents an event in the simulation
 * It is the parent class of RideRequestedEvent and RideFinishedEvent
 */
public abstract class Event {

  protected Request request;
  public LocalDateTime time;

  public RideshareDispatchSimulator theSimulator;

  /**
   * Constructor for Event
   * @param time time when the event will happen as a LocalDateTime
   * @param theSimulator the simulator, as a RideshareDispatchSimulator
   * @param request the request, as a Request
   */
  public Event(LocalDateTime time, RideshareDispatchSimulator theSimulator, Request request) {
    this.time = time;
    this.theSimulator = theSimulator;
    this.request = request;
  }

  /**
   * process the event
   */
  public abstract void processEvent();

  /**
   * get the request of the event
   * @return the request, as a Request
   */
  public Request getRequest() {
    return request;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Event event = (Event) o;
    return Objects.equals(request, event.request) && Objects.equals(time,
        event.time) && Objects.equals(theSimulator, event.theSimulator);
  }

  @Override
  public int hashCode() {
    return Objects.hash(request, time, theSimulator);
  }
}
