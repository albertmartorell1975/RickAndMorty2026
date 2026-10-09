# Rick & Morty Character Explorer

A modern, native Android application built to explore the vast multiverse of Rick and Morty characters. Designed as a technical showcase of clean, scalable architecture and modern UI paradigms in the Android ecosystem.

<p align="center">
  <img src="app/src/main/res/drawable/ricksanchez.webp" width="150" alt="Rick Sanchez Icon">
</p>

## Features

*   **Character Catalog:** Browse all characters across the multiverse with infinite scrolling (pagination).
*   **Filtering:** Filter characters by their current vital status (Alive, Dead, Unknown).
*   **Detailed Profiles:** Dive deep into specific characters, viewing their spacetime origin, last known location, and exact episode appearances.
*   **Offline Favorites:** Mark characters as favorites. The state syncs immediately across the app and persists locally.
*   **Native Splash & Theme:** Features the official Android 12+ Splash Screen API and full support for dynamic Dark/Light modes inspired by the "Citadel" aesthetic.

## Tech Stack & Architecture

This project strictly adheres to **Clean Architecture** principles and the **SOLID** design pattern, isolating business rules from framework details across a 4-module multi-project setup (`:domain`, `:usecases`, `:data`, `:app`).

*   **UI Framework:** Jetpack Compose (Material 3)
*   **Architecture Pattern:** MVVM (Model-View-ViewModel)
*   **Navigation:** Jetpack Navigation Compose (Type-Safe with Kotlin Serialization)
*   **Concurrency:** Kotlin Coroutines & Flow (`StateFlow`)
*   **Dependency Injection:** Hilt (Dagger)
*   **Networking:** Retrofit + OkHttp
*   **Local Persistence:** Room Database
*   **Image Loading:** Coil

## Development Methodology

This project was developed using **Agentic Programming** with a strict **Human-in-the-loop** approach. Leveraging the [android-ai-workflow-foundation](https://github.com/albertmartorell1975/android-ai-workflow-foundation) framework, I orchestrated AI agents to assist with the development process. 

While the agents enforced Git Flow governance, architectural boundaries, and pre-commit verification checks, all technical decisions, architecture design, and final code reviews were supervised and validated by human oversight. Critical components and delicate refactors were hand-coded to ensure optimal performance and adherence to standard practices.

## Getting Started

### Prerequisites
*   Android Studio (Latest stable recommended)
*   JDK 17
*   Android SDK 34 (Minimum SDK 26 for execution)

### Building the Project
1. Clone the repository to your local machine.
2. Open the project in Android Studio.
3. Allow Gradle to sync and download all necessary dependencies.
4. Run the `:app` configuration on an emulator or physical device.

### Running Tests
The project prioritizes ultra-fast, JVM-based unit tests avoiding emulator overhead by using Repository Fakes.
To run the full unit test suite, execute:
```bash
./gradlew :app:testDebugUnitTest
```

## Documentation

For a deeper dive into the architectural decisions, SOLID application, and the testing strategy, please refer to the technical defense document:
[PROJECT_SUMMARY.md](docs/PROJECT_SUMMARY.md)