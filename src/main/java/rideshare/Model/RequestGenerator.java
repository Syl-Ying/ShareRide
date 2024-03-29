package rideshare.Model;

import java.time.LocalDateTime;
import java.util.Random;
import rideshare.objects.Request;
import rideshare.objects.RideType;

/**
 * RequestGenerator is a class that generates random requests
 */
public class RequestGenerator {

  private static Integer rangeOfSeconds = 3600;

  /**
   * Generate a random request.
   * The requestTime starts from systemTime and is generated randomly within rangeOfSeconds,
   * default to 3600 seconds, ie 1 hour.
   * distance is randomly generated and is at least 10 miles.
   * The other attributes like name, location are generated randomly according to given i.
   * ride type is randomly selected from the enum RideType.
   * @param i The index of the request, as an Integer
   * @param systemTime The current time of the system, as a LocalDateTime
   * @return A random Request
   */
  public static Request generateRandomRequest(int i, LocalDateTime systemTime) {
    Random random = new Random();
    String customerName = "Customer" + i;
    String startLocation = "Start location" + i;
    String endLocation = "Ending Location" + i;
    Double distance = random.nextDouble() * 50 + 10;
    LocalDateTime requestTime = generateRandomTimeWithinRange(systemTime, rangeOfSeconds);
    RideType rideType = getRandomRideType();

    Request request = new Request(customerName, startLocation, endLocation, distance,
        requestTime, rideType);

    return request;
  }

  /**
   * Generate a random LocalDateTime starting from systemTime within the given range
   * @param systemTime The current time of the system, as a LocalDateTime
   * @param rangeOfSeconds The range of seconds to generate a random time within, as an Integer
   * @return A random LocalDateTime within the given range
   */
  private static LocalDateTime generateRandomTimeWithinRange(LocalDateTime systemTime, Integer rangeOfSeconds) {
    Random random = new Random();
    return systemTime.plusSeconds(random.nextInt(rangeOfSeconds));
  }

  /**
   * Get a random RideType
   * @return A random RideType, as a RideType
   */
  private static RideType getRandomRideType() {
    RideType[] rideTypes = RideType.values();
    Random random = new Random();
    return rideTypes[random.nextInt(rideTypes.length)];
  }
}

