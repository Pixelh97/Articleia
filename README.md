# Posts & Comments Android App

A modern Android application for browsing posts, viewing comments, and managing favorites with offline-first architecture and background synchronization.

## 📱 Features

- **Browse Posts**: View a comprehensive list of posts with comment counts
- **Post Details**: Read full post content with associated comments
- **Favorites Management**: Mark posts as favorites with offline support
- **Offline-First**: All data cached locally for offline access
- **Background Sync**: Automatic synchronization of favorite changes using WorkManager
- **Pending Queue**: Failed favorite operations are queued and retried when network is available

## 🏗️ Architecture

The project follows **Clean Architecture** principles with clear separation of concerns:

```
┌─────────────────────────────────────────────────┐
│              Presentation Layer                 │
│  (ViewModels, UI States, Compose/XML Views)     │
└─────────────────────────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────────┐
│               Domain Layer                      │
│     (Entities, Repository Interfaces,           │
│         Use Cases, Exceptions)                  │
└─────────────────────────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────────┐
│                Data Layer                       │
│  (Repository Implementation, Data Sources,      │
│    Local DB, Remote API, Mappers, Workers)      │
└─────────────────────────────────────────────────┘
```

### Module Structure

- **presentation**: ViewModels and UI components
  - `PostsViewModel`: Manages posts list and filtering (All/Favorites)
  - `PostDetailsViewModel`: Handles post details and comments display
  
- **domain**: Business logic and interfaces
  - `Post`, `Comment`: Core entities
  - `PostRepository`: Repository contract
  - `ArticleiaException`: Custom exceptions
  
- **data**: Data management and persistence
  - `PostRepositoryImpl`: Repository implementation with caching
  - `PostLocalDataSource`: Local database operations
  - `PostRemoteDataSource`: API communication
  - `PostFavoriteSyncWorker`: Background sync worker

## 🛠️ Tech Stack

### Core Technologies
- **Kotlin**: Primary programming language
- **Coroutines & Flow**: Asynchronous programming and reactive streams
- **StateFlow**: State management in ViewModels

### Android Jetpack
- **ViewModel**: UI state management with lifecycle awareness
- **Room Database**: Local data persistence
- **WorkManager**: Background task scheduling and execution
- **SavedStateHandle**: ViewModel state preservation

### Dependency Injection
- Using **Koin**

### Testing
- **JUnit**: Unit testing framework
- **MockK**: Mocking library for Kotlin
- **Coroutines Test**: Testing coroutines with `runTest`

## 📂 Project Structure

```
com.example/
├── viewmodel/
│   ├── posts/
│   │   ├── PostsViewModel.kt
│   │   ├── PostsScreenUiState.kt
│   │   └── PostUiStateMapper.kt
│   └── postDetails/
│       ├── PostDetailsViewModel.kt
│       ├── PostDetailsUiState.kt
│       └── CommentUiStateMapper.kt
│
├── domain/
│   ├── entity/
│   │   ├── Post.kt
│   │   └── Comment.kt
│   ├── repository/
│   │   └── PostRepository.kt
│   └── exceptions/
│       └── ArticleiaException.kt
│
├── repository/
│   ├── PostRepositoryImpl.kt
│   ├── dataSource/
│   │   ├── local/
│   │   │   ├── PostLocalDataSource.kt
│   │   │   └── PostLocalDataSourceImpl.kt
│   │   └── remote/
│   │       └── PostRemoteDataSource.kt
│   ├── dto/
│   │   └── local/
│   │       ├── LocalPostDto.kt
│   │       └── LocalFavoriteQueueDto.kt
│   ├── mapper/
│   │   └── Mappers.kt
│   └── worker/
│       └── PostFavoriteSyncWorker.kt
│
└── localdatasource/
    └── roomDataBase/
        └── dao/
            ├── PostsDao.kt
            └── PostFavoriteQueueDao.kt
```

## 🔄 Data Flow

### Fetching Posts
1. ViewModel requests posts from Repository
2. Repository checks local cache (Room Database)
3. If cache is empty:
   - Fetch from Remote API
   - Fetch comment counts for each post
   - Store in local database
4. Return posts to ViewModel
5. ViewModel updates UI State

### Toggling Favorites
1. User toggles favorite on a post
2. ViewModel calls Repository
3. Repository updates local cache immediately (optimistic update)
4. Repository attempts to sync with Remote API
5. **If successful**: Operation complete
6. **If failed**: 
   - Add to pending favorites queue
   - Enqueue WorkManager sync task
   - Worker retries when network available

### Background Synchronization
- Uses WorkManager with network constraints
- Exponential backoff retry policy
- Processes pending favorites queue
- Keeps trying until all favorites synced

## 🚀 Key Features Implementation

### Offline-First Architecture
- All data is cached in Room Database
- App works fully offline after initial data load
- Network requests only for fresh data and sync

### Pending Operations Queue
- Failed favorite operations stored in `LocalFavoriteQueueDto`
- Automatically retried when network restored
- Prevents data loss on network failures

### State Management
- Reactive UI with StateFlow
- Immutable UI states using Kotlin data classes
- Clean separation between loading, success, and error states

## 🧪 Testing Strategy

The project includes comprehensive unit tests covering:

- **ViewModel Tests**: State changes, user interactions, error handling
- **Repository Tests**: Data flow, caching logic, network failures
- **Data Source Tests**: Database operations, DAO interactions

### Test Coverage
- ✅ Happy path scenarios
- ✅ Error handling and exceptions
- ✅ Edge cases (null values, empty lists)
- ✅ Network failure scenarios
- ✅ Pending queue operations
- ✅ State verification

## 📋 Requirements

- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)
- **Kotlin Version**: 1.9+
- **Gradle**: 8.0+

## 🔧 Setup & Installation

1. **Clone the repository**
```bash
git clone <repository-url>
cd posts-app
```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned repository

3. **Sync Gradle**
   - Wait for Gradle sync to complete
   - Resolve any dependency issues

4. **Run the app**
   - Connect an Android device or start an emulator
   - Click Run (▶️) or press Shift + F10

## 🧪 Running Tests

```bash
# Run all unit tests
./gradlew test

# Run tests with coverage
./gradlew testDebugUnitTestCoverage

# Run specific test class
./gradlew test --tests PostsViewModelTest
```

## 📱 App Screens

### Posts Screen
- Displays all posts with titles and comment counts
- Tab navigation: All Posts / Favorites
- Pull-to-refresh functionality
- Offline indicator when no internet

### Post Details Screen
- Full post content
- List of comments
- Favorite toggle button
- Loading and error states

```
