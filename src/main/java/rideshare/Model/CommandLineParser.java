package rideshare.Model;

import java.util.Scanner;

public class CommandLineParser {

  /**
   * Parses command line arguments
   * @return parsed arguments, driver numbers and ride request numbers, in an array
   */
  public static int[] parseArguments() {
    Scanner scanner = new Scanner(System.in);
    int[] parsedArgs = new int[2];

    while (true) {
      System.out.println("Please input driver numbers and ride request numbers: ");
      String[] command = scanner.nextLine().toString().split("\\s+");

      // Checks if args length is 2
      if (command.length != 2) {
        System.out.println("Usage: java RideShareSimulation <number_of_drivers> <number_of_rides>"
            + System.lineSeparator());
        continue;
      }

      // Checks if args are positive integers
      try {
        parsedArgs[0] = Integer.parseInt(command[0]);
        parsedArgs[1] = Integer.parseInt(command[1]);

        if (parsedArgs[0] <= 0 || parsedArgs[1] <= 0) {
          System.out.println("Please provide positive numbers!");
          continue;
        }
      } catch (NumberFormatException e) {
        System.out.println("Please provide valid integer format. Null, words etc are not accepted!");
        continue;
      }
      break;
    }

    return parsedArgs;
  }
}
