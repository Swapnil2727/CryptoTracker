# CryptoTracker

A modern Android app for tracking real-time cryptocurrency prices and market data.

Built with **Jetpack Compose**, **Clean Architecture**, and **Kotlin** — following modern Android development best practices.

---

## Features

- Browse the top 50 cryptocurrencies ranked by market cap
- Live price, 24h change, market cap, volume, and circulating supply
- Color-coded price change indicators (green / red)
- Pull-to-refresh for live updates
- Offline support — cached data shown instantly while network refreshes in background
- Detailed coin view with ATH, ATL, 24h high/low, and extended market stats

---

## Screenshots

| Crypto List | Crypto Detail |
|---|---|
| _(coming soon)_ | _(coming soon)_ |

---

## Tech Stack

| Category | Library / Tool |
|---|---|
| Language | Kotlin 2.3 |
| UI | Jetpack Compose, Material 3 |
| Architecture | MVVM + Clean Architecture |
| Dependency Injection | Koin 4.2 |
| Networking | Retrofit 3, OkHttp 5, Moshi |
| Local Database | Room 2.8 |
| Image Loading | Coil 2.7 |
| Navigation | Jetpack Navigation 3 |
| Async | Coroutines, StateFlow |
| Unit Testing | JUnit 4, MockK, Turbine |
| Screenshot Testing | Android Compose Screenshot Testing |
| CI/CD | GitHub Actions |

---

## Architecture

The project is structured into four Gradle modules following Clean Architecture principles:

```
app/            Entry point — Activity, Application class, DI wiring
domain/         Business logic — Models, Repository interfaces (pure Kotlin, no Android deps)
data/           Data layer — Retrofit API, Room database, Repository implementation
presentation/   UI layer — Compose screens, ViewModels, UI state models
```

### Data Flow

```
CoinGecko API
     ↓ (Retrofit)
   DTO
     ↓ (Mapper)
 Room Entity  ──→  Local Cache (Single source of truth)
     ↓ (Mapper)
Domain Model
     ↓
  ViewModel  (StateFlow)
     ↓
 Compose UI
```

Room acts as the single source of truth. On launch, cached data is emitted immediately while fresh data is fetched in the background.

---

## Getting Started

### Prerequisites

- Android Studio Meerkat or later
- JDK 17
- A [CoinGecko API key](https://www.coingecko.com/en/api)

### 1. Clone the repo

```bash
git clone https://github.com/Swapnil2727/CryptoTracker.git
cd CryptoTracker
```

### 2. Add your API key

Create a `local.properties` file in the project root (if it doesn't exist) and add:

```properties
COINGECKO_API_KEY=your_api_key_here
```

> `local.properties` is gitignored and never committed.

### 3. Build and run

```bash
./gradlew assembleDebug
```

Or open the project in Android Studio and press **Run**.

---

## Testing

### Unit Tests

```bash
./gradlew testDebugUnitTest
```

Covers ViewModel state transitions (`Loading` → `Success` / `Error`) and retry logic.
Uses **MockK** for mocking and **Turbine** for Flow assertions.

### Screenshot Tests

Validate the current UI against recorded baselines:

```bash
./gradlew validateDebugScreenshotTest
```

Record or update baselines locally:

```bash
./gradlew updateDebugScreenshotTest
```

---

## CI/CD

GitHub Actions runs automatically on every push and pull request to `main`.

| Job | Trigger | What it does |
|---|---|---|
| Build | push / PR | Compiles the debug APK |
| Unit Tests | push / PR | Runs all unit tests, uploads report |
| Lint | push / PR | Runs Android Lint, uploads report |
| Validate Screenshots | push / PR | Fails the PR if UI differs from baselines |
| Record Screenshots | Manual only | Re-records baselines and commits them to the branch |

### Updating screenshot baselines via CI

When a UI change intentionally affects the look of a screen, trigger the recording job manually:

```bash
gh workflow run "Android CI" --ref your-branch --field branch=your-branch
```

The bot will commit updated baseline images directly to your branch with `[skip ci]`, then the next PR run will pass.

---

## Project Structure

```
CryptoTracker/
├── app/
│   └── src/main/java/com/app/cryptotracker/
│       ├── MainActivity.kt
│       ├── CryptoTrackerApp.kt
│       └── di/AppModule.kt
├── domain/
│   └── src/main/java/com/app/domain/
│       ├── model/          # CryptoCurrency, Result
│       └── repository/     # CryptoRepository (interface)
├── data/
│   └── src/main/java/com/app/data/
│       ├── remote/         # Retrofit API, DTOs, interceptors
│       ├── local/          # Room database, DAO, entities
│       ├── mappers/        # DTO → Entity → Domain conversions
│       ├── repo/           # CryptoRepositoryImpl
│       └── di/             # Network, database, repository modules
├── presentation/
│   └── src/main/java/com/app/presentation/
│       ├── ui/             # Compose screens (list + detail)
│       ├── viewmodel/      # CryptoListViewModel, CryptoDetailViewModel
│       ├── model/          # UI state sealed classes
│       ├── navigation/     # Navigator, Destinations
│       └── di/             # Presentation DI module
└── .github/
    └── workflows/
        └── build.yml       # GitHub Actions CI pipeline
```
