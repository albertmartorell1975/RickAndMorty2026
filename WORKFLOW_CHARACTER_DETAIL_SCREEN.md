# Feature: Character Detail Screen

## 1. Idea Diagnosis & Assumptions
- **Objective:** Implement the UI and Presentation layer for the Character Detail screen based on provided designs.
- **Identified Ambiguities / Assumptions:** The Domain and Data layers (`GetCharacterDetailUseCase`, `ToggleFavoriteUseCase`, Repository, Dao) are already fully implemented. We only need to implement the ViewModel, the Jetpack Compose Screen, and wire them up in the Navigation host. The designs mention a Toast/Snackbar "Updated character state" when favoriting, which we will handle via a UI event from the ViewModel.
- **Testing Requirement:** `CharacterDetailViewModel` requires unit tests to ensure that loading character data and toggling favorites correctly updates the UI state.

## 2. Necessary Questions
No additional questions remain. All dependencies (Domain, Data, and Navigation Destinations) are already established.

## 3. Optimized Technical Workflow

## Technical Overview
Implementation of the Character Detail Screen using Jetpack Compose, connecting the existing `GetCharacterDetailUseCase` and `ToggleFavoriteUseCase` to the UI via a new `CharacterDetailViewModel`.

## Layer Breakdown

### UI/UX
- **CharacterDetailScreen**: Jetpack Compose UI following the designs.
- Top App Bar with back navigation and a Favorite icon (heart, filled/unfilled).
- Character image at the top.
- Section displaying core properties, Origin, Last Known Location, and Episodes.
- Snackbar or Toast for "Updated character state".

### API Design
No API changes required. The Data layer is already fully implemented.

### Persistence
No Persistence changes required. 

## Actionable Implementation Checklist

### Phase 1: Navigation Setup (Basic Skeleton)
- [x] **MANDATORY**: Consult `AGENTS.md` for role-specific constraints
- [x] **Basic Screen Composable**: Create `CharacterDetailScreen.kt` in `ui/detail/` with a simple text showing the passed `characterId`.
- [x] **Navigation Integration**: Update `Navigation.kt` to uncomment `Destination.CharacterDetail` and route to `CharacterDetailScreen`. Test that clicking a character in the list navigates successfully.
- [x] **MANDATORY**: Execute `compiler` skill verification suite.
- [x] **MANDATORY**: Request Commit & Push (Manual or via `git-governance` skill) before advancing.

### Phase 2: ViewModel & UI State
- [x] **MANDATORY**: Consult `AGENTS.md` for role-specific constraints
- [x] **UI State**: Define `CharacterDetailUiState` inside or alongside the ViewModel.
- [x] **ViewModel**: Create `CharacterDetailViewModel` in `app/src/main/java/com/martorell/albert/rickandmorty2026/ui/detail/`.
      - Inject `GetCharacterDetailUseCase` and `ToggleFavoriteUseCase`.
      - Expose a single `StateFlow` containing the UI state.
      - Add a method to toggle favorite status and update the state.
- [x] **MANDATORY**: Execute `compiler` skill verification suite.
- [x] **MANDATORY**: Request Commit & Push (Manual or via `git-governance` skill) before advancing.

### Phase 3: Screen UI Implementation
- [x] **MANDATORY**: Consult `AGENTS.md` for role-specific constraints
- [x] **UI Assets**: Ensure required design assets from `docs/ui/screens/detail_character_details/resources/` are converted to webp and available if needed.
- [x] **Screen Layout**: Implement the full Scaffold with TopAppBar, Character Image, Status, Species, Origin, Location, and Episodes based on the `code.html` reference and project Design System.
- [x] **Interactions**: Wire the screen to `CharacterDetailViewModel` events (favorite toggle, back navigation).
- [x] **MANDATORY**: Execute `compiler` skill verification suite.
- [x] **MANDATORY**: Request Commit & Push (Manual or via `git-governance` skill) before advancing.

### Phase 4: Testing & Finalization
- [x] **MANDATORY**: Consult `AGENTS.md` for role-specific constraints
- [x] **Testing**: Create `CharacterDetailViewModelTest` in `app/src/test/...` using mocking for the usecases.
- [x] **Documentation Sync (MANDATORY)**: Run `git status .agents/skills/` and update `.agents/skills/README.md` if there are any changes in the expert skills directory.
- [x] **Compiler Verification (MANDATORY)**: Execute the `compiler` skill verification suite.
- [x] **Commit & Push (MANDATORY)**: Request Commit & Push manually or via `git-governance` skill before advancing.
