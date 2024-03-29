package rideshare;

import rideshare.Controller.RideshareDispatchSimulator;
import rideshare.Model.CommandLineParser;

/**
 * Main class for the rideshare dispatch simulator.
 */
public class Main {

  /**
   * Main method for the rideshare dispatch simulator.
   * @param args command line arguments
   */
  public static void main(String[] args) {
    int[] parsedArgs = CommandLineParser.parseArguments();
    RideshareDispatchSimulator theSimulator = new RideshareDispatchSimulator(parsedArgs[0], parsedArgs[1]);
    theSimulator.run();
    AnalysisReport.generateReport(theSimulator.getFinishedRequests(), parsedArgs[0]);
  }
}
