package rideshare.objects;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import rideshare.objects.Driver;
import rideshare.objects.RideType;

/**
 * Request class represents a request from a customer.
 */
public class Request {

  private static final Integer SPEED_MILE_PER_HOUR = 60;
  private static final Integer HOUR_TO_SECONDS = 3600;
  private Driver assignedDriver;
  private String customerName;
  private String startLocation;
  private String endLocation;
  private Double distance;
  private LocalDateTime requestTime;
  private RideType rideType;
  private Integer priority;
  private double ridingTime;
  private LocalDateTime estimatedArrivalTime;
  private LocalDateTime actualArrivalTime;

  /**
   * Constructor for Request class.
   *
   * @param customerName the name of the customer, as a String
   * @param startLocation the starting location of the ride, as a String
   * @param endLocation the ending location of the ride, as a String
   * @param distance the distance of the ride, as a Double
   * @param requestTime the time of the request, as a LocalDateTime
   * @param rideType the type of the ride, as a RideType
   */
  public Request(String customerName, String startLocation, String endLocation,
      Double distance, LocalDateTime requestTime, RideType rideType) {
    this.assignedDriver = null;
    this.customerName = customerName;
    this.startLocation = startLocation;
    this.endLocation = endLocation;

    this.distance = distance;
    this.rideType = rideType;
    this.priority = this.calculatePriority(distance, rideType);

    this.requestTime = requestTime;
    this.ridingTime = this.calculateRidingSeconds();
    this.estimatedArrivalTime = this.estimateArrivalTime();
  }

  /**
   * Calculates the priority of the request based on the distance and ride type.
   * The smaller the distance, the higher the priority.
   * @param distance the distance of the ride, as a Double
   * @param rideType the type of the ride, as a RideType
   * @return the priority of the request, as an Integer
   */
  private Integer calculatePriority(Double distance, RideType rideType) {
    // Set base priority based on ride type
    switch (rideType) {
      case EXPRESS:
        priority = 3;
        break;
      case STANDARD:
        priority = 2;
        break;
      case WAIT_AND_SAVE:
        priority = 1;
        break;
      case ENVIRONMENTALLY_CONSCIOUS:
        priority = 0;
        break;
    }

    // Adjust priority based on distance
    int distanceHash = Double.valueOf(distance).hashCode();
    priority -= Math.abs(distanceHash % 3); // limits the adjustment to a range between 0 and 2

    return priority;
  }

  /**
   * Calculates the riding time of the request based on the distance and speed.
   *
   * @return the riding time of the request, as a double
   */
  private double calculateRidingSeconds() {
    return (distance / SPEED_MILE_PER_HOUR) * HOUR_TO_SECONDS;
  }

  /**
   * Estimates the arrival time of the request based on the request time and riding time.
   *
   * @return the estimated arrival time of the request, as a LocalDateTime
   */
  public LocalDateTime estimateArrivalTime() {
    return requestTime.plusSeconds((long)(this.ridingTime));
  }

  /**
   * Calculates the waiting time of the request based on the estimated arrival time and actual
   * arrival time.
   *
   * @return the waiting seconds of the request, as an Integer
   */
  public Integer calculateWaitingSeconds() {
    return (int) (ChronoUnit.SECONDS.between(estimatedArrivalTime, actualArrivalTime));
  }

  /**
   * Gets the assigned driver of the request.
   *
   * @return the assigned driver of the request, as a Driver
   */
  public Driver getAssignedDriver() {
    return assignedDriver;
  }

  /**
   * Gets the customer name of the request.
   *
   * @return the customer name of the request, as a String
   */
  public String getCustomerName() {
    return customerName;
  }

  /**
   * Gets the start location of the request.
   *
   * @return the start location of the request, as a String
   */
  public String getStartLocation() {
    return startLocation;
  }

  /**
   * Gets the end location of the request.
   *
   * @return the end location of the request, as a String
   */
  public String getEndLocation() {
    return endLocation;
  }

  /**
   * Gets the distance of the request.
   *
   * @return the distance of the request, as a Double
   */
  public Double getDistance() {
    return distance;
  }

  /**
   * Gets the request time of the request.
   *
   * @return the request time of the request, as a LocalDateTime
   */
  public LocalDateTime getRequestTime() {
    return requestTime;
  }

  /**
   * Gets the ride type of the request.
   *
   * @return the ride type of the request, as a RideType
   */
  public RideType getRideType() {
    return rideType;
  }

  /**
   * Gets the priority of the request.
   *
   * @return the priority of the request, as an Integer
   */
  public Integer getPriority() {
    return priority;
  }

  /**
   * Gets the riding time of the request.
   *
   * @return the riding time of the request, as a double
   */
  public double getRidingSeconds() {
    return ridingTime;
  }

  /**
   * Gets the estimated arrival time of the request.
   *
   * @return the estimated arrival time of the request, as a LocalDateTime
   */
  public LocalDateTime getEstimatedArrivalTime() {
    return estimatedArrivalTime;
  }

  /**
   *  Sets the assigned driver of the request.
   *
   * @param assignedDriver the assigned driver of the request, as a Driver
   */
  public void setAssignedDriver(Driver assignedDriver) {
    this.assignedDriver = assignedDriver;
  }

  /**
   * Sets the actual arrival time of the request.
   *
   * @param actualArrivalTime the actual arrival time of the request, as a LocalDateTime
   */
  public void setActualArrivalTime(LocalDateTime actualArrivalTime) {
    this.actualArrivalTime = actualArrivalTime;
  }

  /**
   * Sets the estimated arrival time of the request.
   *
   * @param estimatedArrivalTime the estimated arrival time of the request, as a LocalDateTime
   */
  public void setEstimatedArrivalTime(LocalDateTime estimatedArrivalTime) {
    this.estimatedArrivalTime = estimatedArrivalTime;
  }

  /**
   * Gets the riding time of the request.
   *
   * @return the riding time of the request, as a double
   */
  public double getRidingTime() {
    return ridingTime;
  }

  /**
   * Gets the actual arrival time of the request.
   *
   * @return the actual arrival time of the request, as a LocalDateTime
   */
  public LocalDateTime getActualArrivalTime() {
    return actualArrivalTime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Request request = (Request) o;
    return Double.compare(ridingTime, request.ridingTime) == 0 && Objects.equals(
        assignedDriver, request.assignedDriver) && Objects.equals(customerName,
        request.customerName) && Objects.equals(startLocation, request.startLocation)
        && Objects.equals(endLocation, request.endLocation) && Objects.equals(
        distance, request.distance) && Objects.equals(requestTime, request.requestTime)
        && rideType == request.rideType && Objects.equals(priority, request.priority)
        && Objects.equals(estimatedArrivalTime, request.estimatedArrivalTime)
        && Objects.equals(actualArrivalTime, request.actualArrivalTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(assignedDriver, customerName, startLocation, endLocation, distance,
        requestTime, rideType, priority, ridingTime, estimatedArrivalTime, actualArrivalTime);
  }

  @Override
  public String toString() {
    return "Request{" +
        "assignedDriver=" + assignedDriver +
        ", customerName='" + customerName + '\'' +
        ", startLocation='" + startLocation + '\'' +
        ", endLocation='" + endLocation + '\'' +
        ", distance=" + distance +
        ", requestTime=" + requestTime +
        ", rideType=" + rideType +
        ", priority=" + priority +
        ", ridingTime=" + ridingTime +
        ", estimatedArrivalTime=" + estimatedArrivalTime +
        ", actualArrivalTime=" + actualArrivalTime +
        '}';
  }
}
