## Project Description:

![DEMO](https://raw.githubusercontent.com/DudeInTheWood/MyImage/refs/heads/main/test%20rover%20demo%20small.gif)

This project is a Mars Rover Navigation System that simulates the movement of a rover across the Martian surface. The rover follows a sequence of movement commands, adjusting its direction and position. The system detects obstacles on the rover's path and halts its movement if an obstacle is encountered, ensuring that the rover remains within its operational area and does not collide with obstacles.


Technologies Used

  - Kotlin
  - Jetpack Compose for UI development
  - MVVM for structured code organization
  - Clean Architecture for maintainability and scalability
  - JUnit 4 for testing
  - MockK for mocking dependencies

## Installation Instructions:
Required tools and versions
  - Android Studio (Recommended: 2024.2.2 Patch 2 or above)
  - Java 17 or above
  - Gradle 8.10.2 or above

How to install
  1 install and launch android studio
  2 Wait for Gradle Sync to complete (normally automatic).
  3 Ensure Required SDKs and Dependencies Are Installed
    - change gradle version(if can't build) by file -> Project Structure
    - Install necessary dependencies if prompted.
    - Sync Gradle Manually by file -> "Sync Project with Gradle File"
      
## Installation Instructions:
How to run the program
simply click run 'app' (▶️) on top right of the android studio or shortcut "shift + F10"
 - if has a problem or trying to rebuild by click Build -> Rebuild Project
 - if you don't have any virtual device follow this instruction [Managing AVDs](https://developer.android.com/studio/run/managing-avds) to create virtual device for running app on emulator
 - you have option to run on real device by follow this instructions [Run Device](https://developer.android.com/studio/run/device) 

## Testing Instructions:
1. Open Android Studio.
2. Navigate to app/src/test/java/your/package/name.
3. Right-click on the test class or method.
4. Select "Run 'TestName' with Coverage".
5. View the coverage results in the "Run" or "Coverage" window.

Expected Outputs for Key Scenarios
  ## 1. Success Case

  **Input:**
  - Grid size: 10
  - Obstacle: `[(1, 1)]`
  - Command: `mmmmmmmmmrmmmmmmmmrmmmmmmmmrmmmmmmmm`
  
  **Output:**
  - Rover position: `(0, 0)`
  - Rover direction: `W`
  - **Success**
  
  ---
  
  ## 2. Fail: Out of Bound
  
  **Input:**
  - Grid size: 10
  - Obstacle: `[(1, 1)]`
  - Command: `LM`
  
  **Output:**
  - Rover position: `(0, 0)`
  - Rover direction: `W`
  - **Out of Bound**
  
  ---
  
  ## 3. Fail: Encountered Obstacle
  
  **Input:**
  - Grid size: 10
  - Obstacle: `[(1, 1)]`
  - Command: `MRM`
  
  **Output:**
  - Rover position: `(0, 1)`
  - Rover direction: `E`
  - **Obstacle Encountered**

This project already provides 100% Code Coverage on core business logic (domain layer).
![Rover Code Coverage](https://raw.githubusercontent.com/DudeInTheWood/MyImage/refs/heads/main/business%20coverage.png)

## Additional Notes:
  - Implement a simple UI to visualize the rover's movement on the grid.
  - Ensure the UI is fully tested, including all functionalities, and provide test cases for the ViewModel to verify its correctness.
   
   
 


