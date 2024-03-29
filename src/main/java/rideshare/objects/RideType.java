package rideshare.objects;

/**
 * Enum for different types of rides
 */
public enum RideType {

  EXPRESS, // highest priority
  STANDARD,
  WAIT_AND_SAVE,
  ENVIRONMENTALLY_CONSCIOUS; // lowest priority
}
