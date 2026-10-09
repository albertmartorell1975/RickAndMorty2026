# Technical Guide & Project Summary: RickAndMorty2026

This document summarizes the architecture, technical decisions, and testing strategy for the **RickAndMorty2026** Android application, prepared for technical interview defense and project review.

---

## 1. Overview & Architecture

The application is built in **Kotlin** following **Clean Architecture** principles and the **MVVM (Model-View-ViewModel)** presentation pattern using **Jetpack Compose**.

### Module Structure

```
RickAndMorty2026 /
├── :domain      (Pure Kotlin module) -> Domain models and Repository interfaces. Zero Android dependencies.
├── :usecases    (Pure Kotlin module) -> Business logic and Use Cases.
├── :data        (Android Library)    -> Data layer implementation (Room DB + Retrofit API).
└── :app         (Android App)        -> Presentation (Compose UI), ViewModels, DI (Hilt), and Navigation.
```

- **Layer Isolation:** The domain and use case modules have zero framework dependencies on Android.
- **Dependency Inversion:** Repository contracts are defined in `:domain` and implemented in `:data`.

---

## 2. Tech Stack & Key Libraries

| Area | Technology / Library |
| :--- | :--- |
| **UI / Design System** | Jetpack Compose, Material 3, Coil (Image loading) |
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

---

## 4. Testing Strategy & Key Technical Defense Points

All unit tests run as **pure JVM local tests** (located in `app/src/test/`), executing in milliseconds without emulator overhead.

### A. Fakes over Mocks
- **Decision:** Implemented a `FakeCharacterRepository` rather than relying heavily on mocking frameworks (e.g., MockK or Mockito).
- **Defense Point:** "Fakes maintain real in-memory state and validate end-to-end integration between ViewModels and UseCases in a clean, readable, and highly maintainable way."

### B. Pure JVM Unit Tests over Robolectric / Emulators
- **Decision:** Avoided Robolectric and Android emulators for unit tests.
- **Defense Point:** "To keep unit tests executing in milliseconds, we avoid Robolectric's heavy Android framework loading. We bypassed Navigation Compose's `toRoute()` JVM crash (caused by Android's `Bundle` dependency) by extracting the character ID directly from `SavedStateHandle`."

### C. Coroutines Management via `MainDispatcherRule`
- **Decision:** Utilized `kotlinx-coroutines-test` with an `UnconfinedTestDispatcher`.
- **Defense Point:** "We replace `Dispatchers.Main` with a `TestDispatcher` via a custom JUnit Rule. This ensures coroutines launched in `viewModelScope` execute synchronously during tests, eliminating flakiness and race conditions."

---

## 5. Summary of Implemented Tests

- **`CharacterDetailViewModelTest`**: Covers initial loading, error handling, favorite toggling, and toast dismissal.
- **`CharacterListViewModelTest`**: Covers catalog initialization, status filtering, and favorite toggling.
- **`GetCharacterDetailUseCaseTest`**: Validates character and episode data composition.
- **`ToggleFavoriteUseCaseTest`**: Validates favorite status state changes.

---

## 6. How to Run Tests

To execute the test suite from the terminal:
```bash
./gradlew :app:testDebugUnitTest
```
