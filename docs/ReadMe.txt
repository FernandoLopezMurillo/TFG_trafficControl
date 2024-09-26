This branch introduces two-way roads, roundabouts, and a new type of traffic light with an offset feature.

UPDATES
Car.java
  - Change method "getTurningDirections" because with the two-way streets the opposite direction is no longer added to the list "possibleDirections"
  - Create method "isDrivingPossible" 
Endpoint.java
  - Change the scheduledMethod 
TrafficLight.java
  - Change to abstract class so we can create different types of traffic lights
TrafficLightOffset.java
  - Create new class
TrafficLightWithoutOffset.java
  - Refactor of the old class TrafficLight
