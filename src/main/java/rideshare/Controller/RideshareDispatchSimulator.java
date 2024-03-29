package rideshare.Controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import rideshare.Model.RequestGenerator;
import rideshare.objects.Request;
import rideshare.Event.Event;
import rideshare.Event.RideFinishedEvent;
import rideshare.Event.RideRequestedEvent;
import rideshare.objects.Driver;
import rideshare.objects.WaitingComparator;

/**
 * RideshareDispatchSimulator is the main class of the simulation.
 */
public class RideshareDispatchSimulator extends Simulation {

  private Integer numberOfIdleDrivers;
  private List<Driver> availableDrivers = new ArrayList<>();
  private List<Request> finishedRequests = new ArrayList<>();
  private PriorityQueue<Request> waitingRequests = new PriorityQueue<>(new WaitingComparator()); // sort by priority
  private PriorityQueue<Request> activeRequests = new PriorityQueue<>(
      Comparator.comparing(Request::getEstimatedArrivalTime)); // sort by arrival time

  /**
   * Constructor of RideshareDispatchSimulator
   * @param numberOfIdleDrivers number of idle drivers, as an Integer
   * @param numberOfWaitingRequests number of waiting requests, as an Integer
   */
  public RideshareDispatchSimulator(Integer numberOfIdleDrivers, Integer numberOfWaitingRequests) {
    super();
    this.numberOfIdleDrivers = numberOfIdleDrivers;
    initializeSimulation(numberOfIdleDrivers, numberOfWaitingRequests);
  }

  /**
   * initialize drivers and requests
   * @param numberOfDrivers number of drivers, as an Integer
   * @param numberOfRequests number of requests, as an Integer
   */
  private void initializeSimulation(Integer numberOfDrivers, Integer numberOfRequests) {
    initializeDrivers(numberOfDrivers);
    initializeRequests(numberOfRequests);
  }

  /**
   * initialize drivers and assign their names
   * @param numberOfDrivers number of drivers, as an Integer
   */
  private void initializeDrivers(Integer numberOfDrivers) {
    for (int i = 0; i < numberOfDrivers; i++) {
      // generate random drivers
      String driverName = "Driver" + i;
      availableDrivers.add(new Driver(driverName));
    }
  }

  /**
   * generate random requests and assign their random request times, starting from system starting time
   * add the requests into eventQueue sorted by ascending request time
   * @param numberOfRequests number of requests, as an Integer
   */
  private void initializeRequests(Integer numberOfRequests) {
    for (int i = 0; i < numberOfRequests; i++) {
      // generate a random ride request i based on system starting time
      Request newRequest = RequestGenerator.generateRandomRequest(i, this.time);
      this.scheduleEvent(new RideRequestedEvent(newRequest.getRequestTime(), this, newRequest));
    }
  }

  /**
   * run the simulation. Process events in the eventQueue one by one until the eventQueue is empty.
   * Poll the eventQueue, update the time, and process the event.
   */
  public void run() {
    while (!eventQueue.isEmpty()) {
      Event currentEvent = eventQueue.poll();
      this.time = currentEvent.time;
      currentEvent.processEvent();
    }

    System.out.println("All events completes!");
  }

  /**
   * handle a ride requested event.
   * If there's idle drivers, update actual arrival time as request time plus riding seconds.
   * Then assign a driver to the request, decrease idle driver numbers, and push the request into activeRequests.
   *
   * If there's no idle driver, simply push the request to waitingRequests.
   *
   * @param event a RideRequestedEvent, as an RideRequestedEvent
   * @return true if there's idle drivers, false if there's no idle drivers
   */
  public Boolean handleRideRequested(RideRequestedEvent event) {
    Request currentRequest = event.getRequest();
    if (numberOfIdleDrivers != 0) {
      currentRequest.setActualArrivalTime(currentRequest.getRequestTime()
                                            .plusSeconds((long)(currentRequest.getRidingSeconds())));
      assignDriver(currentRequest, availableDrivers.get(0));
      return true;
    } else {
      waitingRequests.add(currentRequest);
      return false;
    }
  }

  /**
   * handle a ride finished event.
   * Poll finished requests from activeRequests, store into finished requests.22
   * @param event a RideFinishedEvent, as an RideFinishedEvent
   */
  public void handleRideFinished(RideFinishedEvent event) {
    // poll finished requests from activeRequests, store into finished requests
    Request newlyFinishedRequest = activeRequests.poll();
    finishedRequests.add(newlyFinishedRequest);
    Driver releaseDriver = event.getRequest().getAssignedDriver();
    availableDrivers.add(releaseDriver);
    numberOfIdleDrivers++;

    processWaitingRequests(newlyFinishedRequest, releaseDriver);
  }

  /**
   * assign a given driver for the given request. update driver numbers
   * add the request into activeRequests
   * @param request the given request, as a Request
   * @param driver  the given driver, as a Driver
   */
  private void assignDriver(Request request, Driver driver) {
    request.setAssignedDriver(driver);
    availableDrivers.remove(driver);
    numberOfIdleDrivers--;
    activeRequests.add(request);
  }

  /**
   * process waiting requests.
   * When there's requests waiting, assign a driver, poll from waitingRequests, add into activeRequests
   * @param lastFinishedRequest the last finished request, as a Request
   * @param driver the driver, as a Driver
   * @return the selected request, as a Request
   */
  private Request processWaitingRequests(Request lastFinishedRequest, Driver driver) {
    // When there's requests waiting, assign a driver, poll from waitingRequests, add into activeRequests
    Request selectedRequest = null;
    if (!waitingRequests.isEmpty()) {
      selectedRequest = waitingRequests.poll();
      selectedRequest.setActualArrivalTime(lastFinishedRequest.getActualArrivalTime()
          .plusSeconds((long) (selectedRequest.getRidingSeconds())));
      assignDriver(selectedRequest, driver);

      // generate new finishEvent
      RideFinishedEvent rideFinishedEvent = new RideFinishedEvent(time,this, selectedRequest);
      eventQueue.add(rideFinishedEvent);
    }
    return selectedRequest;
  }

  /**
   * get the number of idle drivers
   * @return the number of idle drivers, as an Integer
   */
  public Integer getNumberOfIdleDrivers() {
    return numberOfIdleDrivers;
  }

  /**
   * get the available drivers
   *
   * @return the available drivers, as a List of Driver
   */
  public List<Driver> getAvailableDrivers() {
    return availableDrivers;
  }

  /**
   * get the finished requests
   * @return the finished requests, as a List of Request
   */
  public List<Request> getFinishedRequests() {
    return finishedRequests;
  }

  /**
   * get the waiting requests
   * @return the waiting requests, as a PriorityQueue of Request
   */
  public PriorityQueue<Request> getWaitingRequests() {
    return waitingRequests;
  }

  /**
   * get the active requests
   * @return the active requests, as a PriorityQueue of Request
   */
  public PriorityQueue<Request> getActiveRequests() {
    return activeRequests;
  }
}
