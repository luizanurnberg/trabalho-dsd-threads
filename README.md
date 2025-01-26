# Threads  
Project for the DSD subject involving threads

Team: Eduardo Schork, Luíza Nurnberg, and Maria Cecilia Holler

### Functional Requirements

| ID | Description |
|------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------|
| RF01 | Each vehicle moves one position at a time, respecting the direction of flow of the track. |
| RF02 | The vehicle moves one position at a time, respecting the flow of the track. |
| RF03 | The vehicle can only move if the position is free. |
| RF04 | When encountering an intersection, the vehicle randomly chooses one of the exit routes before entering. |
| RF05 | The vehicle checks if all positions in the intersection it will pass through are completely free. |
| RF06 | The vehicle must not block the intersection for other vehicles. |
| RF07 | New vehicles are inserted at the entry points of the network. |
| RF08 | The vehicle is terminated when it reaches an exit point. |
| RF09 | Vehicles have different movement speeds. |
| RF10 | The system must allow specifying the maximum number of vehicles that will be simultaneously in the network. |
| RF11 | The tracks will always be horizontal or vertical. |
| RF12 | The tracks are two-way. |
| RF13 | On the edges, there will only be perpendicular tracks. |
| RF14 | A graphical interface for visualization is required. |
| RF15 | The system must support three mutual exclusion mechanisms (semaphores, monitors, and message passing). |

### Non-Functional Requirements

| ID | Category | Description |
|--------|------------------|-------------------------------------------------------------------------------------------------|
| RNF01 | Security | Ensure that vehicles do not collide with each other in the network. |
| RNF02 | Efficiency | The system must be efficient, maintaining real-time simulation whenever possible. |
| RNF03 | Scalability | Ability to handle a large number of vehicles and a network of variable size. |
| RNF04 | Robustness | Handle unexpected situations, such as vehicle failures or network failures. |

### Business Rules

| Business Rules | Description |
|-------------------------------------|-------------------------------------------------------------------------------------------------|
| RN01 | Vehicles must move continuously through the network, respecting traffic rules. |
| RN02 | The choice of exit routes at intersections must be random for each vehicle. |
| RN03 | Ensure that the positions a vehicle will pass through at an intersection are completely free. |
| RN04 | Vehicles must respect the flow direction of the tracks as they move. |
| RN05 | The user can choose whether to use semaphores, monitors, or message passing as mutual exclusion. |

---
