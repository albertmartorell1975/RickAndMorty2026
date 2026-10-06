# Feature: Rick and Morty Character Explorer MVP

## Technical Overview

The **Rick and Morty Character Explorer** MVP enables users to browse a paginated catalog of characters, search/filter by name/status/species, view detailed character profiles (origin, current location, episodes), and bookmark favorites locally for offline access.

The implementation strictly follows **Clean Architecture** across the multi-module project structure:
- **`:domain`**: Core domain entities (`Character`, `Episode`, `Location`, `LocationRef`) and repository interface (`CharacterRepository`). Zero framework dependencies.
- **`:data`**: Remote REST API client (Retrofit/OkHttp), Room Local Database (DAOs & Entities), and `CharacterRepositoryImpl` coordinating network fetching with local caching.
- **`:usecases`**: Pure Kotlin interactor use cases (`GetCharactersUseCase`, `SearchCharactersUseCase`, `GetCharacterDetailUseCase`, `ToggleFavoriteUseCase`).
- **`:app`**: Presentation layer using **Jetpack Compose (Material 3 - Citadel Portal Theme)**, StateFlow/ViewModel (`CharacterListViewModel`, `CharacterDetailViewModel`), Navigation 3 routing, and Hilt DI wiring.

---

## Layer Breakdown

### UI/UX
- **Design System**: Material 3 *Citadel Portal* theme defined in `DESIGN.md` (Portal Green `#97CE4C`, Dimension Cyan `#11B0C8`, Dark Cosmic `#0F131E`).
- **Catalog Screen**: Search bar, status filters (Alive, Dead, Unknown), responsive grid/list of character cards, infinite scroll pagination, and loading/error feedback.
- **Detail Screen**: Hero character image, survival status badge, origin/location cards, episode list, and favorite heart toggle with micro-animations.
- **Resource Policy**: Screen mockups in `docs/ui/screens/` serve as visual layout guides. Raw graphic assets in `docs/ui/resources/` are converted to `.webp` in `app/src/main/res/drawable/`.

### API Design
- **Base URL**: `https://rickandmortyapi.com/api/`
- **Endpoints**:
  - `GET /character/?page={page}&name={name}&status={status}&species={species}`
  - `GET /character/{id}`
  - `GET /episode/{ids}`

### Persistence
- **Room Database**: `RickAndMortyDatabase` caching characters, favorites, and paging metadata.
- **DAOs**: `CharacterDao` for local CRUD and cache retrieval.

---

## Actionable Implementation Checklist

### Phase 1: Domain Layer (`:domain`)
- [x] **MANDATORY**: Consult `AGENTS.md` for role-specific constraints
- [x] **Domain Models**: Create `Character`, `Episode`, `Location`, `LocationRef` domain models under `com.martorell.albert.rickandmorty2026.domain.model`.
- [x] **Repository Interface**: Define `CharacterRepository` contract returning Kotlin `Flow` and `Result` types under `com.martorell.albert.rickandmorty2026.domain.repository`.
- [x] **MANDATORY**: Execute `compiler` skill verification suite.
- [x] **MANDATORY**: Request Commit & Push (Manual or via `git-governance` skill) before advancing.

### Phase 2: Data Layer (`:data`)
- [x] **MANDATORY**: Consult `AGENTS.md` for role-specific constraints
- [x] **Remote Data Source**: Setup Retrofit service API interface and DTO models under `com.martorell.albert.rickandmorty2026.data.remote`.
- [x] **Local Data Source**: Create Room database `RickAndMortyDatabase`, `CharacterEntity`, and `CharacterDao` under `com.martorell.albert.rickandmorty2026.data.local`.
- [x] **Repository Implementation**: Implement `CharacterRepositoryImpl` coordinating remote fetch + Room cache in `com.martorell.albert.rickandmorty2026.data.repository`.
- [x] **Hilt Data Module**: Configure Hilt DI module for API and Room binding under `com.martorell.albert.rickandmorty2026.data.di`.
- [x] **MANDATORY**: Execute `compiler` skill verification suite.
- [x] **MANDATORY**: Request Commit & Push (Manual or via `git-governance` skill) before advancing.

### Phase 3: UseCases Layer (`:usecases`)
- [x] **MANDATORY**: Consult `AGENTS.md` for role-specific constraints
- [x] **UseCases Implementation**:
  - `GetCharactersUseCase`
  - `SearchCharactersUseCase`
  - `GetCharacterDetailUseCase`
  - `ToggleFavoriteUseCase`
- [x] **Hilt UseCases Module**: Configure Hilt DI module under `com.martorell.albert.rickandmorty2026.usecases.di`.
- [x] **MANDATORY**: Execute `compiler` skill verification suite.
- [ ] **MANDATORY**: Request Commit & Push (Manual or via `git-governance` skill) before advancing.

### Phase 4: Presentation & UI Layer (`:app`)
- [ ] **MANDATORY**: Consult `AGENTS.md` for role-specific constraints
- [ ] **Design System Theme**: Implement *Citadel Portal* M3 theme (Color, Type, Shapes) under `com.martorell.albert.rickandmorty2026.ui.theme`.
- [ ] **ViewModels**: Implement `CharacterListViewModel` and `CharacterDetailViewModel` with `UiState` and `UiEvent` under `com.martorell.albert.rickandmorty2026.ui`.
- [ ] **Compose Screens**:
  - `CharacterListScreen`: Search, status chips, grid catalog.
  - `CharacterDetailScreen`: Profile, details, episodes, favorite button.
- [ ] **App Navigation**: Configure Navigation 3 routes in `com.martorell.albert.rickandmorty2026.ui.navigation`.
- [ ] **MANDATORY**: Execute `compiler` skill verification suite.
- [ ] **MANDATORY**: Request Commit & Push (Manual or via `git-governance` skill) before advancing.

### Phase 5: Testing & Final Verification
- [ ] **MANDATORY**: Consult `AGENTS.md` for role-specific constraints
- [ ] **Unit Tests**: Add tests for UseCases, Repository, and ViewModels.
- [ ] **Compose UI Tests**: Add UI test coverage for character browsing and favorite toggling.
- [ ] **Documentation Sync (MANDATORY)**: Run `git status .agents/skills/` and update `.agents/skills/README.md` if skill files change.
- [ ] **MANDATORY**: Execute `compiler` skill verification suite.
- [ ] **MANDATORY**: Request Commit & Push (Manual or via `git-governance` skill) before advancing.
