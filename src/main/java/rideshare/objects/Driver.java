package rideshare.objects;

import java.util.Objects;

/**
 * Driver class represents a driver in the rideshare system.
 */
public class Driver {

  private String name;

  /**
   * Constructor for Driver class.
   *
   * @param name name of the driver, default to "Jane Doe" if not specified, as a String
   */
  public Driver(String name) {
    this.name = name;
  }

  /**
   * Default constructor for Driver class. default to "Jane Doe" if not specified
   */
  public Driver() {
    this.name = "Jane Doe";
  }

  /**
   * Getter for name of the driver.
   *
   * @return name of the driver, as a String
   */
  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Driver driver = (Driver) o;
    return Objects.equals(name, driver.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return "Driver{" +
        "name='" + name + '\'' +
        '}';
  }
}
