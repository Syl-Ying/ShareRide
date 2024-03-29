package rideshare.Model;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class CommandLineParserTest {

  private InputStream originalInput;
  private PrintStream originalOutput;

  @BeforeEach
  public void setUpStreams(@TempDir Path tempDir) {
    originalInput = System.in;
    originalOutput = System.out;
  }

  @Test
  public void testParseArguments_ValidInput() {
    String input = "5 10\n";
    ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    int[] expected = {5, 10};
    int[] result = CommandLineParser.parseArguments();

    assertArrayEquals(expected, result);
  }

  @Test
  public void testParseArguments_InvalidInput() {
    String input = "abc\ndef\n-1 0\n4 7\n";
    ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    // Ensure it returns an array of zeros for invalid input
    int[] expected = {4, 7};
    int[] result = CommandLineParser.parseArguments();

    assertArrayEquals(expected, result);
  }
}
