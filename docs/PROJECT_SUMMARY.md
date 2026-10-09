# Technical Guide & Project Summary: RickAndMorty2026

This document summarizes the architecture, technical decisions, and testing strategy for the **RickAndMorty2026** Android application, prepared for technical interview defense and project review.

> **Development Methodology Note:**
> This project was developed utilizing **Agentic Programming** (Programació Agèntica) leveraging the [android-ai-workflow-foundation](https://github.com/albertmartorell1975/android-ai-workflow-foundation) framework. This approach enabled structured AI-assisted development with strict local governance, enforcing Git Flow conventions, Clean Architecture validation, and mandatory pre-commit verification (local tests and compilation) directly within the IDE.

---

## 1. Overview & Architecture

The application is built in **Kotlin** following strict **Clean Architecture** and **SOLID** principles, utilizing the **MVVM (Model-View-ViewModel)** presentation pattern with **Jetpack Compose**.

### Module Structure & Clean Architecture Mapping

The project is divided into **4 distinct modules** to enforce boundaries:

```
RickAndMorty2026 /
├── :domain      -> Pure Kotlin. Models and Repository interfaces. Zero framework dependencies.
├── :usecases    -> Pure Kotlin. Business logic orchestrating the domain.
├── :data        -> Android Library. Implements domain contracts (Room + Retrofit).
└── :app         -> Android App. Compose UI, ViewModels, Hilt DI, and Navigation.
```

### Key SOLID Principles Applied:
- **Single Responsibility Principle (SRP):** Clear delegation of duties. ViewModels handle UI state, UseCases manage specific business rules, Repositories orchestrate data sources, and DAOs/APIs handle exact data fetches.
- **Dependency Inversion Principle (DIP):** The core of the architecture. The `:data` and `:app` modules depend on the `:domain` module interfaces. The business logic never knows *how* data is fetched, allowing seamless swapping of implementations (e.g., swapping `CharacterRepositoryImpl` for `FakeCharacterRepository` in tests).

---

## 2. Tech Stack & Key Libraries

| Area | Technology / Library |
| :--- | :--- |
| **UI / Design System** | Jetpack Compose, Material 3, Coil (Image loading) |
| **UI Tooling** | UI mockups created via [Google Stitch](https://stitch.withgoogle.com/projects/7916621784280215790?pli=1) |
| **Navigation** | Navigation Compose 2.8.x (Type-Safe destinations) |
| **Dependency Injection** | Hilt (Dagger) |
| **Local Persistence** | Room Database (DAOs & Entities) |
| **Network / API** | Retrofit + OkHttp + Gson |
| **Concurrency & Streams** | Kotlin Coroutines & Flow (`StateFlow`, `collectAsStateWithLifecycle`) |
| **Testing** | JUnit 4, `kotlinx-coroutines-test`, Fakes (`FakeCharacterRepository`) |

---

## 3. Implemented Features

1. **Character Catalog (`CharacterListScreen` / `CharacterListViewModel`)**:
   - Paginated character catalog from the Rick and Morty API.
   - Search by name and filter by status (Alive, Dead, Unknown).
   - Reactive favorite toggling with instant local cache sync.
   - Robust UI states: Initial loading, error state with retry mechanism, pagination, and refresh.

2. **Character Detail Screen (`CharacterDetailScreen` / `CharacterDetailViewModel`)**:
   - High-res character header image, status badge, origin, last known location, and episode list.
   - Favorite heart toggle with visual feedback.
   - Asynchronous state handling for loading and error states.

3. **Core App Experience (UX/UI)**:
   - Full **Dark Mode / Light Mode** native support mapped to a custom Material 3 color scheme.
   - Implementation of the official **AndroidX Core Splash Screen API** for a seamless, jank-free cold start experience.

---

## 4. Testing Strategy

All unit tests run as **pure JVM local tests** (located in `app/src/test/`), executing in milliseconds without emulator overhead.

- **Fakes over Mocks:** Implemented a `FakeCharacterRepository` to maintain real in-memory state and validate end-to-end integration between ViewModels and UseCases without relying heavily on mocking frameworks.
- **Pure JVM Unit Tests:** Bypassed framework dependencies (like Navigation Compose's `Bundle` requirement via `toRoute()`) to ensure tests run natively on the JVM without needing Robolectric.
- **Coroutines Management:** Utilized `kotlinx-coroutines-test` with a custom `MainDispatcherRule` to execute `viewModelScope` coroutines synchronously, eliminating flakiness.

---

## 5. How to Run Tests

To execute the entire unit test suite from the terminal:

```bash
./gradlew :app:testDebugUnitTest
```
