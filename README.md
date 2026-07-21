# Mobile Attendance App

A modern Android attendance management application built with
**Kotlin**, **Jetpack Compose**, **Clean Architecture**, and
**WebSocket-based communication**.

## Features

-   Admin Authentication
-   Employee Management
-   Add Employee
-   Face Registration
-   Attendance Verification
-   Profile Management
-   WebSocket Communication
-   Room Offline Storage
-   Offline Synchronization
# 🏗️ Architecture

The application follows **Clean Architecture** combined with the **MVVM (Model–View–ViewModel)** design pattern to achieve a modular, scalable, and maintainable codebase.

Business logic is isolated from the presentation layer through **Use Cases**, while data access is abstracted using **Repository Interfaces**. Communication with the backend is performed through persistent **WebSocket** connections, while **Room Database** provides offline persistence and synchronization.

## High-Level Architecture

```text
                            Mobile Attendance App
┌──────────────────────────────────────────────────────────────────┐
│                    Jetpack Compose Presentation                  │
│                                                                  │
│ Login • Dashboard • Attendance • Employee • Profile             │
└──────────────────────────────┬───────────────────────────────────┘
                               │
                               ▼
┌──────────────────────────────────────────────────────────────────┐
│                          ViewModels                              │
│                                                                  │
│ LoginViewModel                                                   │
│ AttendanceViewModel                                              │
│ EmployeeViewModel                                                │
│ AddEmployeeViewModel                                             │
│ ProfileViewModel                                                 │
│ EditProfileViewModel                                             │
└──────────────────────────────┬───────────────────────────────────┘
                               │
                               ▼
┌──────────────────────────────────────────────────────────────────┐
│                           Use Cases                              │
│                                                                  │
│ Login                                                            │
│ Register Employee                                                 │
│ Verify Face                                                      │
│ Get Profile                                                      │
│ Update Profile                                                   │
│ Offline Synchronization                                          │
└──────────────────────────────┬───────────────────────────────────┘
                               │
                               ▼
┌──────────────────────────────────────────────────────────────────┐
│                         Repository Layer                         │
│                                                                  │
│ Login Repository                                                 │
│ Face Repository                                                  │
│ User Repository                                                  │
│ Profile Repository                                               │
└──────────────────────────────┬───────────────────────────────────┘
                               │
             ┌─────────────────┴──────────────────┐
             ▼                                    ▼
      Local Storage                        Remote Service
      Room Database                        WebSocket API
             │                                    │
             └─────────────────┬──────────────────┘
                               ▼
                        Node.js Backend
                               │
                               ▼
                             MySQL
```

---

# Package Structure

```text
com.example.mobilesurapp
│
├── UIApp/                 → Jetpack Compose screens and ViewModels
│
├── navigation/            → Navigation Graph
│
├── domain/
│   ├── usecase/           → Business Logic
│   └── utils/             → Shared Utilities
│
├── repository/            → Repository Pattern
│
├── api/                   → WebSocket Client & Request Models
│
├── database/
│   ├── dao/
│   └── converters/
│
├── background/            → Offline Synchronization Worker
│
├── model/                 → Application Models
│
├── face/                  → FaceNet Embedding
│
├── modelload/             → MediaPipe & TensorFlow Models
│
├── di/                    → Dependency Injection (Hilt)
│
└── ui/theme/              → Material Theme
```

---

# MVVM Architecture

```text
Jetpack Compose Screen
          │
          ▼
     ViewModel
          │
          ▼
      Use Case
          │
          ▼
     Repository
     ┌───────────────┐
     ▼               ▼
Room Database   WebSocket API
```

---

# Face Registration Pipeline

```text
CameraX
   │
   ▼
ImageAnalysis
   │
   ▼
MediaPipe Face Detection
   │
   ▼
Face Crop
   │
   ▼
FaceNet Embedding
   │
   ▼
128-Dimensional Face Vector
   │
   ▼
RegisterUserWithFaceUseCase
   │
   ▼
WebSocket
   │
   ▼
Backend Database
```

---

# Attendance Verification Pipeline

```text
CameraX
   │
   ▼
Face Detection
   │
   ▼
FaceNet Embedding
   │
   ▼
VerifyFaceUseCase
   │
   ▼
FaceRepository
   │
   ▼
WebSocket
   │
   ▼
Node.js Backend
   │
   ▼
Attendance Recorded
```

---

# Offline Synchronization

```text
Network Unavailable
        │
        ▼
PendingSyncDao
        │
        ▼
Room Database
        │
Internet Available
        │
        ▼
WorkManager
        │
        ▼
SyncOfflineFacesUseCase
        │
        ▼
WebSocket
        │
        ▼
Backend Database
```

---

# Dependency Injection

```text
AppModule (Hilt)
       │
       ▼
Repositories
       │
       ▼
UseCases
       │
       ▼
ViewModels
       │
       ▼
Jetpack Compose UI
```

---

# Main Technologies

| Layer | Technology |
|--------|------------|
| Language | Kotlin |
| UI | Jetpack Compose |
| Architecture | Clean Architecture + MVVM |
| Dependency Injection | Hilt |
| Database | Room |
| Networking | WebSocket |
| Background Tasks | WorkManager |
| Camera | CameraX |
| Face Detection | MediaPipe |
| Face Recognition | FaceNet |
| Concurrency | Kotlin Coroutines |
| State Management | StateFlow |

## Tech Stack

-   Kotlin
-   Jetpack Compose
-   MVVM
-   Clean Architecture
-   Hilt
-   Room
-   CameraX
-   MediaPipe
-   Gson
-   Coroutines
-   StateFlow
-   WebSocket

## Application Flow

``` text
Login
  ↓
Dashboard
├── Attendance
├── Employee
│   └── Add Employee
└── Settings
    └── Profile
        └── Edit Profile
```

## Companion Backend

https://github.com/ary3141/API_KPMobile

## Installation

``` bash
git clone https://github.com/ary3141/MobileAttendanceApp.git
cd MobileAttendanceApp
```

Open in Android Studio and run.

## Author

**M Dwiva Arya Erlangga**
