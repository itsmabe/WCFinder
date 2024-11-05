# WCFinder 🚻



**WCFinder**  is an Android app designed to help users quickly locate nearby restrooms within a 5 km radius of their current position. Developed with Kotlin and following Clean Architecture (MVI pattern) and the Repository pattern, WCFinder provides a seamless and efficient user experience for finding restroom locations with real-time data.



## Architecture
The app is structured around **Clean Architecture** with the **Model-View-Intent (MVI)** pattern, ensuring clear separation of concerns and scalability. The **Repository pattern** is used to manage data sources efficiently, and **Paging3** handles paginated API data for smooth performance.



## Technologies Used
- **Jetpack Compose**: Modern UI toolkit for building native UIs.
- **Coroutines**: For asynchronous programming.
- **Ktor with Gson**: For networking and JSON serialization.
- **Hilt**: Dependency Injection framework.
- **Paging3**: Efficient handling of paginated data from the API.
- **Splashscreen**: Custom splash screen integration.
- **JUnit**: Testing framework for unit tests.
- **Mockito**: Library for creating mock objects in tests.



## Screenshots
![Screenshot_20241105-010126_WCFinder](https://github.com/user-attachments/assets/0249d0a6-b61d-4933-9f0a-a4a7d0db17d2)
![Screenshot_20241105-010047_WCFinder](https://github.com/user-attachments/assets/05027581-a87f-4e93-be7e-897c294d9e08)
![Screenshot_20241105-010059_WCFinder](https://github.com/user-attachments/assets/e52f212f-1fde-414d-924c-1af606b45e85)



## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/WCFinder.git
2. Open in Android Studio and sync Gradle files.
3. Run the app on an emulator or physical device.



## Usage
1. Open WCFinder and grant location permissions.
2. View nearby restrooms on the interactive map.
3. Tap on any restroom marker to see additional details, including accessibility features.


