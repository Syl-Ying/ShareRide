package rideshare.objects;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DriverTest {

  Driver testDriver;
  Driver testDriver1;

  @BeforeEach
  void setUp() {
    testDriver = new Driver("John Doe");
    testDriver1 = new Driver();
  }

  @Test
  void getName() {
    assertEquals("John Doe", testDriver.getName());
    assertEquals("Jane Doe", testDriver1.getName());
  }

  @Test
  void testEquals() {
    Driver testDriver2 = new Driver("John Doe");
    Driver testDriver3 = new Driver("Jane Doe");
    assertTrue(testDriver.equals(testDriver2));
    assertFalse(testDriver.equals(testDriver3));
    // test null
    assertFalse(testDriver.equals(null));
    // test different class
    assertFalse(testDriver.equals("John Doe"));
    // test with itself
    assertTrue(testDriver.equals(testDriver));
  }

  @Test
  void testHashCode() {
    Driver testDriver2 = new Driver("John Doe");
    Driver testDriver3 = new Driver("Jane Doe");
    assertEquals(testDriver.hashCode(), testDriver2.hashCode());
    assertNotEquals(testDriver.hashCode(), testDriver3.hashCode());
  }

  @Test
  void testToString() {
    assertEquals("Driver{name='John Doe'}", testDriver.toString());
  }
}