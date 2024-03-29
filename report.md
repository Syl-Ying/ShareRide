# Shujie Ying - Option 1

## How to Run the Program

1. Run `Main.java `.
2. Enter 2 positive integer with a space in between in the command line. `<numberOfDrivers> <numberOfRequests>`

## Overview
The Rideshare Dispatch Simulator simulates a ride-sharing service's operational aspects to gain insights into key metrics.
By inputting the number of drivers and ride requests, the simulator orchestrates two key events 
and generates outputs showcasing average ride waiting times, ride durations, and the ideal number of drivers for efficient operations.
#### Features:
- Driver and Request Simulation: Models driver availability and customer ride requests within the system.
- Event Simulation: Runs simulations for ride requests and ride completions, handling pending requests and assigning drivers to new ones.
- Metric Calculation: Determines average ride waiting times and durations and optimal driver numbers to provide insights into customer experiences.
#### Request Process Logic
- If there are currently drivers available to take on a new ride, Simulator assign the newly arrived ride request to a driver.
-  if no drivers are available, then a request needs to wait for one of the current rides to finish. Requests are being assigned to the freed-up drivers based on their priority. Requests with the same priority are served in order of arrival time.
#### Output Metrics:
The simulation generates insights by providing the following outputs for various scenarios:

- Average Wait Time: Duration customers wait for their rides.
- Average Riding Time: Average duration each ride from to destination.
- Average Rides per Driver: Number of rides handled by each driver on average.
- Optimal number of drivers on the roads: The min number of drivers needed for a set of given requests.

## High-level Overview of the Solution
Overall I followed event-driven simulation combined with the Model-View-Controller (MVC) archietecture:

Key Varaibles:
- RideRequestedEvent: time is requestTime.
- RideFinishedEvent: time is actualArrivalTime.
- `waitingRequests`: A min heap sorted by the request's disatance, rideType and arrivalTime.
- `activeRequests` : A min heap sorted by the request's actualArrivalTime.
- `finishedRequests`: An ArrayList to store finished requests, saved for later analysis.

1. Model:
   1. `CommandLineParser` takes the number of drivers and ride requests as user-defined parameters from command line.
   2. `RequestGenerator` generate random requests starting from system time within next 24 hours.
2. Control Event Simulation: `RideshareDispatchSimulator` manages the simulation flow.
   1. Use a Priority Queue `eventQueue` of events to decide process sequence by event time. 
      - Events include `RideRequestedEvent`and `RideFinishedEvent`representing a request was initialized or finished.
      - `eventQueue` was initialized with all pre-generated random `RideRequestedEvent`. 
      - `RideFinishedEvent`was added into `eventQueue` only when a request was pushed into `activeRequests`. Since only then, a request's actual arrivalTime can be determined.
   2. Use 3 queues `waitingRequests`, `activeRequests` and `finishedRequests` to store different states of events.
      - When a `RideRequestedEvent` was popped: 
         - If there's idle drivers, Simulator will allocate an idle driver to this request AND generate an according `RideFinishedEvent` into `eventQueue`, then push the request into `activeRequests`.
         - Else, this request will be pushed into `waitingRequests`.
      - When a `RideFinishedEvent` was popped: 
        - poll the request into `activeRequests` then push it into `finishedRequests`. 
        - Check if there's waiting requests. If so, pop a waiting request and assign the released driver.
3. View: 
   1. Computes average ride waiting times, average ride durations, and the optimal number of drivers.
   2. Format metrics and generate analysis report.

## Key Challenges
I was new to event-driven simulation, so I spent a lot of time learning its framework. 
I followed an event-driven simulation sample of Ice Shop from Oracle.
But as I designed the code, it was really hard to accurately manage time progression and synchronization among events. 
Especially the time when the finishing event should be pushed into event queue.
So I set the simulation starting time to be the system time when main() was run and give the simulator a time range to generate requests.
After constant debugging, I chose to generate a finish event only when a request was added into activeRequests. 

Another challenge I encountered is how to interpret the optimal driver numbers to balance system cost and customer convenience. 
Since It's an open question and the cost of system was undefined, I considered idle drivers during simulation as a human resource waste cost.
And analogy to interval questions like Meeting Room. So the optimal number is the minimum number of drivers needed to realize no waiting time for every request.
Note it only applies to the scenario that all requests' `requestingTime` and `estimatedArrivalTime` were determined. 

## Resources
I followed the event-driven simulation framework of an ice shop from Oracle:
https://docs.oracle.com/cd/E19205-01/819-3703/11_3.htm

## Answer Sheet
1. Please include a code snippet showing how have you used inheritance and composition in your code.
```java
// inheritance
public class RideFinishedEvent extends Event{}
// composition
public class Request {
  private Driver assignedDriver;
}
```
2. Please include a code snippet showing how have you used an interface or an abstract class in your code.
```java
public abstract class Event {}
```
3. Please include code example of a method overriding and method overloading from your code, or explain why you have not used any overloading or overriding.
```java
// overriding
@Override
public void processEvent() {
  if (this.theSimulator.handleRideRequested(this)) {
    // should be actual
    this.theSimulator.scheduleEvent(new RideFinishedEvent(request.getActualArrivalTime(), this.theSimulator, this.request));
  }
}
// overloading
public Driver(String name) {
  this.name = name;
}

public Driver() {
  this.name = "Jane Doe";
}
```
4. Please include a code example showing how have you used encapsulation, or explain why you did not need encapsulation in your code.
```java
// encapsulate the driver name into a Driver class and use getter to access name
public class Driver {

  private String name;

  public Driver(String name) {
    this.name = name;
  }
  ...
  
  public String getName() {
    return name;
  }
}
```
5. Please include a code example of subtype polymorphism from your code, or explain why you did not need subtype polymorphism.
```java
public class RideshareDispatchSimulator extends Simulation {
  ...
  public void run() {
  ...
    currentEvent.processEvent();
  ...
  }
}

public class RideFinishedEvent extends Event{
  ...
  @Override
  public void processEvent() {
    this.theSimulator.handleRideFinished(this);
  }
}

public class RideRequestedEvent extends Event {
  ...
  @Override
  public void processEvent() {
    if (this.theSimulator.handleRideRequested(this)) {
      // should be actual
      this.theSimulator.scheduleEvent(new RideFinishedEvent(request.getActualArrivalTime(), this.theSimulator, this.request));
    }
  }
}
```
6. Please include a code snippet of generics from your code.
```java
// The RideshareDispatchSimulator class itself does not rely on generics for its functionality. 
// The test cases focus on the behavior of specific methods 
// within this class that operate on concrete types 
// (Request, RideRequestedEvent, etc.), not on generic types.
```
7. Please include a code snippet showing how have you used some of the built-in data
   collections from the Java Collections Framework, or explain why you had no need for
   any data collections.
```java
private PriorityQueue<Request> waitingRequests = new PriorityQueue<>(new WaitingComparator());
```
8. Please include a code snippet showing how have you used interfaces Iterable and
   Iterator, or explain why you had no need for these two interfaces.
```java
// The methods exposed by the class (getAvailableDrivers(), getFinishedRequests(), etc.) 
// return standard Java collections. 
// These collections already have built-in methods that allow iteration 
// without explicitly implementing Iterable or Iterator.
```
9. Please include a code snippet showing how have you used interfaces Comparable and
   Comparator, or explain why you had no need for these two interfaces.
```java
public class EventComparator implements Comparator<Event> {

  @Override
  public int compare(Event o1, Event o2) {
    if (o1.time.isBefore(o2.time)) {
      return -1;
    } else if (!o1.time.isBefore(o2.time)) {
      return 1;
    }
    return 0;
  }
}

```
10. Please include a code snippet showing how have you used regular expressions, or
    explain why you had no need for it.
```java
String[] command = scanner.nextLine().toString().split("\\s+");
```
11. Please include a code snippet showing how have you used nested classes, or justify
    why you had no need for nested classes.
```java
public static Integer calculateOptimalDriverNumber(List<Request> requestsHistory) {
  ...
  Collections.sort(intervals, new Comparator<List<LocalDateTime>>() {
    @Override
    public int compare(List<LocalDateTime> a, List<LocalDateTime> b) {
      return a.get(0).compareTo(b.get(0));
    }
  });
  ...
}
```
12. Please include code example showing how have you used components of functional
    programming, such as lambdas and streams, or explain why you had no need for it in
    your code.
```java
// lambda
private PriorityQueue<Request> activeRequests = new PriorityQueue<>(
      Comparator.comparing(Request::getEstimatedArrivalTime));
```
13. Please include code snippet(s) showing how have you used creational, structural
    and/or behavioral design patterns. Please list which design patterns have you used,
    or explain why you had no need for design patterns in your solution.

- Built-in Language Features: Java has built-in features that naturally handle certain design pattern functionalities.
- Simulation-Specific Nature: Event-driven simulation has unique characteristics that doesn't conform directly to conventional design pattern usage. The custom nature of simulations might lead to ad-hoc solutions tailored specifically to the simulation's needs.

14. Please include code snippets showing examples of MVC architecture, or justify why
    you had no need for MVC architecture in your design.

- **Model**:
`CommandLineParser` handles parsing user input and processing command-line arguments. 
It deals with user input and encapsulates the logic of parsing and validating these inputs.
`RequestGenerator` generates instances of Request in the system.

- **Controller**:
`RideshareDispatchSimulator` orchestrates the simulation, handles ride requests, assigns drivers, processes events, and maintains the state of drivers, requests, and event queues. It manages the flow of the system based on user inputs or events.

- **View**:
`AnalysisReport`: Computes various statistics based on the Request objects received.
It generates an analysis report of  average waiting time, riding time, handles requests' historical data.
It also handles the presentation and rendering of data to the user. 


15. Please include code examples showing data and stamp coupling in your code.
```java
// stamp coupling
private void assignDriver(Request request, Driver driver) {}

// data coupling
AnalysisReport.generateReport(theSimulator.getFinishedRequests(), parsedArgs[0]);

public static void generateReport(List<Request> requestsHistory , Integer numberOfDrivers) {}

```