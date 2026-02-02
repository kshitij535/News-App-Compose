# 📰 News Aggregator App

**NewsAggregator** is a robust Android application designed to deliver real-time global news. Built using **Kotlin** and **Jetpack Architecture Components**, it demonstrates high-level engineering principles like background synchronization, persistent preference management, and efficient network handling.

---

## 🚀 Features

- **Real-time News Feed**: Fetches the latest global headlines via **Currents API**.
- **Category Filtering**: Seamlessly toggle between General, Technology, Sports, and Business news.
- **Offline Bookmarking**: Save articles locally using `SharedPreferences` (Set-based persistence).
- **Background Sync**: Integrated **WorkManager** to handle periodic news data synchronization in the background.
- **Social Sharing**: One-tap sharing of news URLs to other platforms.
- **Smart State Management**: Remembers your last selected category even after the app is closed.

---

## 🛠 Tech Stack & Tools

- **Language**: Kotlin
- **UI Framework**: XML with `ConstraintLayout`, `RecyclerView`, and `Material Design 3`.
- **Networking**: [Retrofit 2](https://square.github.io/retrofit/) + [Gson](https://github.com/google/gson) for JSON parsing.
- **Image Loading**: [Glide](https://github.com/bumptech/glide) for efficient image caching and memory management.
- **Background Processing**: [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager) for deferrable, guaranteed background tasks.
- **Local Persistence**: `SharedPreferences` for user preferences and saved articles.

---

## 🏗 Architecture
The project follows the **MVVM (Model-View-ViewModel)** pattern (conceptually implemented) to ensure clean separation of concerns:
- **Data Layer**: Retrofit instance and Currents API service.
- **UI Layer**: `MainActivity` with category-based logic.
- **Adapter**: `NewsAdapter` with complex view binding and interaction logic.

---

### 📸 App Screenshots

| Home Feed | Category Selection | Saved News |
| :---: | :---: | :---: |
| ![Home Feed](./screenshots/General_page.png) | ![Categories](./screenshots/User_saved.png) | ![Saved](./screenshots/Tech_page.png) |

---

## ⚙️ Installation & Setup

1. **Clone the repository**:
   ```bash
   git clone [https://github.com/kshitij535/News-App-Compose.git](https://github.com/kshitij535/News-App-Compose.git)
