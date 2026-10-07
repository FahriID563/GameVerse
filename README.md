# 🎮 GameVerse - Next-Gen Video Game Catalog

**GameVerse** is a modern Android application built using **Jetpack Compose** and **Kotlin**, powered by the **RAWG Video Games Database API**. It features a futuristic **Cyberpunk/Neon** design system, real-time search, multi-criteria filtering, and detailed video game insights.

---

## ✨ Features

- 🌌 **Futuristic Cyberpunk UI**: Sleek neon-themed design system (`CyberColors`) with glowing cards, custom radial gradients, and fluid animations.
- 🔍 **Real-Time Search Bar**: Dynamic game title search with non-intrusive content loading indicators and keyboard state preservation.
- 🎛️ **Multi-Criteria Filtering (`Search by filters`)**:
  - **Genre Filter**: Action, RPG, Shooter, Adventure, Indie, Strategy, Racing, Sports, etc.
  - **Minimum Rating**: ★ 4.5+, ★ 4.0+, ★ 3.5+.
  - **Release Year**: Filter games by launch year (2024, 2023, 2022, 2021, 2020, 2019).
  - **Reset Filters**: One-tap action to restore full catalog parameters.
- 📱 **Interactive Game Details**: View full game descriptions, ratings, release dates, platforms (PC, PlayStation, Xbox, Switch, etc.), genres, and Metacritic scores.
- ⚡ **Asynchronous State Management**: Powered by Kotlin Coroutines and `StateFlow` for smooth state rendering (`Loading`, `Success`, `Error`).

---

## 🛠️ Tech Stack & Architecture

- **Language**: [Kotlin](https://kotlinlang.org/)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3)
- **Architecture Pattern**: MVVM (Model-View-ViewModel)
- **Networking**: [Retrofit 2](https://square.github.io/retrofit/) & Gson Converter
- **Image Loading**: [Coil](https://coil-kt.github.io/coil/) for Compose
- **Async & Reactive Data**: Kotlin Coroutines & `StateFlow`
- **Navigation**: Jetpack Navigation Compose (`NavHost`)
- **API Source**: [RAWG Video Games Database API](https://rawg.io/apidocs)

---

## 📂 Project Structure

```text
com.pemmob.videogame/
├── data/
│   ├── model/         # DTO Data Classes (GameDto, GameDetailDto, GenreDto, etc.)
│   ├── remote/        # Retrofit API Service & ApiClient
│   └── repository/    # GameRepository for fetching, filtering, and API mapping
├── ui/
│   ├── components/    # Reusable UI views (LoadingView, ErrorView, LoadingGameCard)
│   ├── detail/        # DetailScreen & DetailViewModel
│   ├── home/          # HomeScreen, HomeViewModel, HomeUiState, FilterState
│   ├── navigation/    # AppNavHost navigation setup
│   └── theme/         # CyberColors, Color, Theme, Type
└── MainActivity.kt    # Application entry point
```

---

## 🚀 Getting Started

### Prerequisites

- **Android Studio**: Ladybug / Hedgehog or newer (2024+)
- **JDK Version**: Java 17 / JDK 17+
- **Minimum SDK**: Android 7.0 (API level 24)
- **Target SDK**: Android 14 (API level 34+)

### Installation & Setup

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/FahriID563/GameVerse.git
   cd GameVerse
   ```

2. **Obtain a RAWG API Key**:
   - Register for a free API key at [RAWG.io API Docs](https://rawg.io/apidocs).

3. **Configure API Key**:
   - Open `local.properties` in the root folder and add:
     ```properties
     RAWG_API_KEY="your_actual_rawg_api_key_here"
     ```

4. **Build and Run**:
   - Sync the Gradle project in Android Studio.
   - Run the app on an Android Emulator or connected physical device.

---

## 📜 License

Distributed under the MIT License. See `LICENSE` for more information.
