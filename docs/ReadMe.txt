This branch adds some traffic lights the car should check before continue driving.

UPDATES
Car.java
  - Delete nextDirection lack of use
  - Delete nextState lack of use
  - Changes in the switch at the method 'step'
Utils.java
  - Rename from 'moveIntoDirection' to 'getFuturePosition' because the previous name led to misunderstandings
GlobalConstants.java
  - Delete 'WAITING' from CarState because of the lack of use
  - Add the enumeration LightState
Road.java
  - Rename the class from 'Street' to 'Road'
NetworkBuilder.java
  - Create a new endpoint
  - Create new traffic lights
TrafficLight.java
  - New class
