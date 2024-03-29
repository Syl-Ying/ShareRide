package rideshare.Controller;

import java.time.LocalDateTime;
import java.util.PriorityQueue;
import java.util.Queue;
import rideshare.Event.Event;
import rideshare.Event.EventComparator;

/**
 * Simulation class is the main class of the simulation. It contains the eventQueue and the current time.
 */
public class Simulation {

  protected Queue<Event> eventQueue;
  protected LocalDateTime time;

  /**
   * Constructor of the Simulation class.
   */
  public Simulation() {
    this.eventQueue = new PriorityQueue<>(new EventComparator());
    this.time = LocalDateTime.now();
  }

  /**
   * Add an event to the event queue.
   * @param event the event to be scheduled, as an Event
   */
  public void scheduleEvent(Event event) {
    eventQueue.add(event);
  }

  /**
   * Get the event queue in the eventQueue.
   * @return the  event queue, as a PriorityQueue
   */
  public Queue<Event> getEventQueue() {
    return eventQueue;
  }

  /**
   * Get the current time.
   * @return the current time
   */
  public LocalDateTime getTime() {
    return time;
  }
}
